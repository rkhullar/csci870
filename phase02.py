#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/08/16
@updated :  12/08/16
"""

import csv, json
from support import api, mod, dsv, ext
from support import MODES, ATTRS

# make api call for access points
waps = api.waps()

# read json file from phase01
with open(ext.genpath('scans-1.json'), 'r') as f:
    din = json.load(f)

# generate organized but still redundant dataset
dout = mod.vectordict()
for m in MODES:
    for tj in din[m]:
        dout[m][tj] = dsv.groupscans(din[m][tj], waps)

# generate json file
with open(ext.genpath('scans-2.json'), 'w') as f:
    json.dump(dout, f, separators=(',', ':'))

''' print dataset
for m in MODES:
    for tj in dout[m]:
        for w in dout[m][tj]:
            n = len(dout[m][tj][w])
            print(tj, w, n)
'''

'''
ft = json.loads(tj)
fo = mod.parseTuple(m, ft)
fd = mod.object2dict(fo, ATTRS[m])
'''
