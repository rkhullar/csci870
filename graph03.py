#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/10/16
@updated :  12/11/16
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
    'L'  : lambda x: x.building + ' ' + x.room,
    'T'  : lambda x: fmtHQ(x.hour),
    'TT' : lambda x: DAYS[x.dow] + ' at ' + fmtHQ(x.hour, x.quarter),
    'LT' : lambda x: x.building + ' ' + x.room + ' at ' + fmtHQ(x.hour)
}

WAPS = api.waps()
WAPD = api.lstd(WAPS, start=1)

def boxplot_helper(xs, ws, title=None, fname=None):
    fig, ax = plt.subplots()
    ax.boxplot(xs)
    ax.set_xticklabels(ws)
    for label in ax.xaxis.get_ticklabels():
        label.set_rotation(90)
    if title:
        plt.title(title)
    plt.xlabel('WiFi Access Point')
    plt.ylabel('Signal Strength (dBm)')
    plt.tight_layout()
    if fname:
        fig.savefig(fname)
        plt.close()
    else:
        plt.show()


def boxplot_group(data, title=None, fname=None):
    ws = []; xs = []
    for w in WAPS:
        if w in data:
            x = data[w]['level']
            wf = 'W%02d' % WAPD[w]
            ws.append(wf)
            xs.append(x)
    boxplot_helper(xs, ws, title, fname)

with open('data/scans-2.json', 'r') as f:
    din = json.load(f)


# generate overall fingerprint
def boxplot_master(title=None, fname=None):
    mst = {}
    for w in WAPS:
        mst[w] = []
    for j in din['W']:
        d = json.loads(j)
        o = mod.parseTuple('W', d)
        w = o.bssid
        mst[w] = mst[w] + din['W'][j][w]['level']
    ws = []; xs = []
    for w in WAPS:
        if w in mst:
            x = mst[w]
            wf = 'W%02d' % WAPD[w]
            ws.append(wf)
            xs.append(x)
    boxplot_helper(xs, ws, title, fname)

boxplot_master(fname='figures/boxplots/master.png')

# generate individual group boxplots
del din['W']
for m in din:
    flabel = FLABELS[m]
    for tj in din[m]:
        data = din[m][tj]
        td = json.loads(tj)
        to = mod.parseTuple(m, td)
        title = flabel(to)
        fname = 'figures/boxplots/'+m+'/'+title.replace(' ', '_')+'.png'
        try:
            boxplot_group(data, title, fname)
        except:
            print('failure:', title)
