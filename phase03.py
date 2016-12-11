#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/11/16
@updated :  12/11/16
"""

import csv, json
from support import api, mod, dsv, ext
from support import MODES, ATTRS

# make api call for access points
WAPS = api.waps()
WAPD = api.wapd(WAPS)


def matrify(dat):
    smtx = {}
    for w in dat:
        for t in dat[w]['uxt']:
            if t not in smtx:
                smtx[t] = {}
    for w in dat:
        ts = dat[w]['uxt']
        ls = dat[w]['level']
        n = len(ts)
        for i in range(n):
            t = ts[i]
            l = ls[i]
            smtx[t][w] = l
    out = []
    for t in smtx:
        l = []
        for w in WAPS:
            if w in smtx[t]:
                l.append(smtx[t][w])
            else:
                l.append(-150)
        out.append(l)
    return out

# read json file from phase02
with open(ext.genpath('scans-2.json'), 'r') as f:
    din = json.load(f)

# generate master
mst = {'meta':{}, 'data':{}}
mst['meta']['waps'] = WAPS
mst['meta']['wapd'] = WAPD
for m in ['L', 'LT']:
    mst['data'][m] = {}
    for tj in din[m]:
        d = din[m][tj]
        mtx = matrify(d)
        mst['data'][m][tj] = mtx


# generate json file
with open(ext.genpath('scans-3.json'), 'w') as f:
    json.dump(mst, f, separators=(',', ':'))
