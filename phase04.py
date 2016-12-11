#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/11/16
@updated :  12/11/16
"""

import csv, json
from support import api, mod, ext

# read json file from phase03
with open(ext.genpath('scans-3.json'), 'r') as f:
    din = json.load(f)

# make waps list
WAPD = din['meta']['wapd']
WAPS = api.lstr(WAPD)

# make location lookup dictionary
LOCDJ = din['meta']['locd']
LOCRJ = api.lsti(LOCDJ)
LOCR = {}
for i in LOCRJ:
    j = LOCRJ[i]
    t = json.loads(j)
    o = mod.parseTuple('L', t)
    LOCR[i] = o

# set column names
TCOLS = ['uxt', 'dow', 'hour', 'quarter']
LCOLS = ['building', 'floor', 'room']

# prepare for generation
data = din['data']
target = din['target']
size = len(target)

# generate csv file
with open(ext.genpath('scans-4.csv'), 'w') as f:
    HEAD = TCOLS + WAPS + LCOLS
    f.write(';'.join(HEAD)+'\n')
    for i in range(size):
        l = []
        for c in TCOLS:
            x = data['time'][c][i]
            l.append(x)
        for c in WAPS:
            x = data['waps'][c][i]
            l.append(x)
        gid = target[i]
        loc = LOCR[gid]
        for c in LCOLS:
            x = getattr(loc, c)
            l.append(x)
        s = ';'.join(map(str, l)) + '\n'
        f.write(s)
