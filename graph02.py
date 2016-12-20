#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/08/16
@updated :  12/10/16
"""

import csv, json, math
import scipy.stats as st
from support import api, mod, fmtHQ
from support import MODES, DAYS

import numpy as np
import pandas as pd
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt

from matplotlib import style, animation
style.use('ggplot')


# function to filter list in z score bound
def subset(lin, mean, dev, zl, zr, zt=0.01):
    lout = []
    for x in lin:
        z = (mean-x)/dev
        if (zl-zt) <= z and z <= (zr+zt):
            lout.append(x)
    return lout


with open('data/scans-2.json', 'r') as f:
    din = json.load(f)

# read all levels into master dictionary
mst = {}
for m in MODES:
    mst[m] = []
    for tj in din[m]:
        for w in din[m][tj]:
            mst[m] = mst[m] + din[m][tj][w]['level']

# compute stats for each group
std = {}
for m in MODES:
    std[m] = mod(len=0, sum=0, avg=0, var=0, dev=0)
    for x in mst[m]:
        std[m].sum += x
        std[m].len += 1
    std[m].avg = std[m].sum / std[m].len
    tdx = 0
    for x in mst[m]:
        tdx += (std[m].avg - x) * (std[m].avg - x)
    std[m].var = tdx / (std[m].len - 1)
    std[m].dev = math.sqrt(std[m].var)

# pick middle percentile values
# R = range(0, 110, 10)
R = range(0, 101)

# compute z score bound for each percentile
zrs = {}
for i in R:
    p = i/100
    d = (1 - p)/2
    l = st.norm.ppf(d)
    r = st.norm.ppf(1-d)
    zrs[i] = mod(zl=l, zr=r)
    #print(i, zrs[i].zl, zrs[i].zr)

# derive lists for each group and percentile
drv = {}
for m in MODES:
    drv[m] = {}
    for i in zrs:
        drv[m][i] = subset(lin=mst[m], mean=std[m].avg, dev=std[m].dev, zl=zrs[i].zl, zr=zrs[i].zr)

# graph master histogram
m = 'W'
mu, sigma, vals = std[m].avg, std[m].dev, mst[m]
bins = list(range(-120, 5, 5))
fig, ax = plt.subplots()
n, bout, patches = plt.hist(vals, bins, normed=1, histtype='bar', rwidth=0.8, color='c')
curve = mlab.normpdf(bout, mu, sigma)
plt.plot(bins, curve, 'r--', linewidth=1)
plt.xlabel('Signal Strength (dB)')
plt.ylabel('Probability')
plt.title('Distribution of Signal Strength Levels')
plt.tight_layout()
#plt.show()
fig.savefig('figures/dist-x.png')
plt.close()

# compute counts for percentiles
drn = {}
for m in MODES:
    drn[m] = mod(x=[], y=[])
    for i in R:
        drn[m].x.append(i)
        drn[m].y.append(len(drv[m][i]))

# generate line graph
fig, ax = plt.subplots()
for m in MODES:
    plt.plot(drn[m].x, drn[m].y, label=m)
plt.xticks(np.arange(0, 110, 10))
plt.title('Filtered Record Counts for Middle Percentiles')
plt.xlabel('Percentile')
plt.ylabel('Count')
plt.legend(loc='best')
plt.tight_layout()
#plt.show()
fig.savefig('figures/zfilter.png')
plt.close()

'''
for m in drv:
    for i in R:
        print(m, i, len(drv[m][i]))
'''

'''
for m in MODES:
    o = std[m]
    print(m, o.len, o.avg, o.dev)
'''
