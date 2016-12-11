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

FLABELS = {
    'W'  : lambda x,i: 'W%02d' % i,
    'L'  : lambda x,i: x.building+': '+x.room,
    'T'  : lambda x,i: fmtHQ(x.hour),
    'TT' : lambda x,i: DAYS[x.dow] + ' @ ' + fmtHQ(x.hour, x.quarter),
    'LT' : lambda x,i: x.building + ': ' + x.room + ' @ ' + fmtHQ(x.hour)
}

TITLES = {
    'W'  : 'Raw Sample Sizes for WiFi Access Points',
    'L'  : 'Raw Sample Sizes for Locations',
    'T'  : 'Raw Sample Sizes for Time',
    'TT' : 'Raw Sample Sizes for Days and Times',
    'LT' : 'Raw Sample Sizes for Locations and Times'
}

# function to filter list in z score bound
def subset(lin, mean, dev, zl, zr):
    lout = []
    for x in lin:
        z = (mean-x)/dev
        if zl <= z and z <= zr:
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
R = range(0, 110, 10)

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
m = 'L'
mu, sigma, vals = std[m].avg, std[m].dev, mst[m]
bins = list(range(-120, 5, 5))
fig, ax = plt.subplots()
n, bout, patches = plt.hist(vals, bins, normed=1, histtype='bar', rwidth=0.8, color='c')
curve = mlab.normpdf(bout, mu, sigma)
plt.plot(bins, curve, 'r--', linewidth=1)
plt.xlabel('Signal Strength')
plt.ylabel('Probability')
plt.title('Distribution of Signal Strength Levels')
#plt.show()
fig.savefig('figures/dist-x.png')
plt.close()

'''
for m in drv:
    for i in range(0, 110, 10):
        print(m, i, len(drv[m][i]))
'''

'''
dat = mod.vectordict()
for m in MODES:
    dat[m]
'''

'''
for m in MODES:
    o = std[m]
    print(m, o.len, o.avg, o.dev)
'''

"""
0 0.0 0.0
10 -0.125661346855 0.125661346855
20 -0.253347103136 0.253347103136
30 -0.385320466408 0.385320466408
40 -0.524400512708 0.524400512708
50 -0.674489750196 0.674489750196
60 -0.841621233573 0.841621233573
70 -1.03643338949 1.03643338949
80 -1.28155156554 1.28155156554
90 -1.64485362695 1.64485362695
100 -inf inf
"""
