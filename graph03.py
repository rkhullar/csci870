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

WAPD = {}
i = 1
for w in WAPS:
    WAPD[w] = i
    i += 1

with open('data/scans-2.json', 'r') as f:
    din = json.load(f)

def genOneBoxPlot(data, title=None, fname=None):
    ws = []; xs = []
    for w in WAPS:
        if w in data:
            x = data[w]['level']
            wf = 'W%02d' % WAPD[w]
            ws.append(wf)
            xs.append(x)
    fig, ax = plt.subplots()
    ax.boxplot(xs)
    ax.set_xticklabels(ws)
    for label in ax.xaxis.get_ticklabels():
        label.set_rotation(90)
    if title:
        plt.title(title)
    plt.xlabel('WiFi Access Point')
    plt.ylabel('Signal Strength')
    plt.tight_layout()
    if fname:
        fig.savefig(fname)
        plt.close()
    else:
        plt.show()

# generate boxplots
del din['W']
for m in din:
    flabel = FLABELS[m]
    for tj in din[m]:
        data = din[m][tj]
        td = json.loads(tj)
        to = mod.parseTuple(m, td)
        title = flabel(to)
        fname = 'figures/boxplots/'+title.replace(' ', '_')+'.png'
        try:
            genOneBoxPlot(data, title, fname)
        except:
            print('failure:', title)
