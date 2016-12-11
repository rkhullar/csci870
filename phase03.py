#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/11/16
@updated :  12/11/16
"""

import csv, json
from support import api, mod, ext
from support import extUXT

# make api calls
WAPS = api.waps()
LOCS = api.locs()
WAPD = api.lstd(WAPS, start=1)
LOCD = api.lstd(LOCS, start=1)

def groupLT(tj):
    td = json.loads(tj)
    to = mod.parseTuple('LT', td)
    tp = mod.object2tuple(to, 'L')
    return LOCD[tp]

def matrify(dat):
    d = {}
    for w in dat:
        for t in dat[w]['uxt']:
            if t not in d:
                d[t] = {}
    for w in dat:
        ts = dat[w]['uxt']
        ls = dat[w]['level']
        for i in range(len(ts)):
            t = ts[i]
            l = ls[i]
            d[t][w] = l
    return d


# read json file from phase02
with open(ext.genpath('scans-2.json'), 'r') as f:
    din = json.load(f)

# generate master
mst = {}
for tj in din['LT']:
    d = din['LT'][tj]
    mtx = matrify(d)
    mst[tj] = mtx

# generate data
data = {'time':{}, 'waps':{}, 'target':[]}
data['time'] = {'uxt':[], 'dow':[], 'hour':[], 'quarter':[]}
for w in WAPS:
    data['waps'][w] = []
for tj in mst:
    grp = groupLT(tj)
    for t in mst[tj]:
        data['target'].append(grp)
        mt = extUXT(t)
        data['time']['uxt'].append(mt.uxt)
        data['time']['dow'].append(mt.dow)
        data['time']['hour'].append(mt.hour)
        data['time']['quarter'].append(mt.quarter)
        for w in WAPS:
            if w in mst[tj][t]:
                lvl = mst[tj][t][w]
            else:
                lvl = -150
            data['waps'][w].append(lvl)

# generate output
dout = {'meta':{}, 'data':{}, 'target':[]}
dout['meta']['wapd'] = WAPD
dout['meta']['locd'] = {}
for l in LOCD:
    tj = json.dumps(l, separators=(',', ':'))
    dout['meta']['locd'][tj] = LOCD[l]
dout['target'] = data['target']
del data['target']
dout['data'] = data

# generate json file
with open(ext.genpath('scans-3.json'), 'w') as f:
    json.dump(dout, f, separators=(',', ':'))

'''
for i in range(1):
    t = data['time']['uxt'][i]
    l = {}
    for w in data['waps']:
        l[w] = data['waps'][w][i]
    print(t, l)
'''
