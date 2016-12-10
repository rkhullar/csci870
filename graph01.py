#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/08/16
@updated :  12/10/16
"""

import csv, json
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
    'W'  : 'Raw Record Counts for WiFi Access Points',
    'L'  : 'Raw Record Counts for Locations',
    'T'  : 'Raw Record Counts for Time',
    'TT' : 'Raw Record Counts for Days and Times',
    'LT' : 'Raw Record Counts for Locations and Times'
}

cnt = {}
for m in MODES:
    cnt[m] = api.count(m)

gcd = {}
for m in MODES:
    size = len(cnt[m])
    gcd[m] = mod(groups=[], counts=[], size=size)
    i = 1
    flabel = FLABELS[m]
    for d in cnt[m]:
        o = mod(**d)
        v = flabel(o, i)
        gcd[m].groups.append(v)
        gcd[m].counts.append(o.count)
        i += 1

'''
for m in MODES:
    x = gcd[m]
    for i in range(x.size):
        print(x.groups[i], x.counts[i])
'''

for m in MODES:
    fig, ax = plt.subplots()
    index = np.arange(gcd[m].size)
    bar_width = 0.5
    opacity = 0.5
    plt.bar(index, gcd[m].counts, bar_width,
                 alpha=opacity,
                 color='c')
    plt.title(TITLES[m])
    plt.xticks(index + bar_width, gcd[m].groups)
    for label in ax.xaxis.get_ticklabels():
        label.set_rotation(90)
    plt.tight_layout()
    #plt.show()
    fig.savefig('figures/count-'+m+'.png')
    plt.close()
