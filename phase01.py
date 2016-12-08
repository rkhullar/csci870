#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/04/16
@updated :  12/07/16
"""

import csv, json
from support import api, mod, dsv, ext
from support import MODES, ATTRS

# make api call
cnt = api.cnts()

# generate filter
flt = mod.vectorlist()
for m in MODES:
    z = ATTRS[m]
    for x in cnt[m]:
        o = mod.objectslice(x, z)
        t = mod.object2tuple(o, m)
        flt[m].append(t)

# initialize data
dat = mod.vectordict()
for m in MODES:
    for f in flt[m]:
        dat[m][f] = []

# read and organize csv file
with open(ext.genpath('scans.csv'), 'r') as csvfile:
    reader = csv.DictReader(csvfile, delimiter=';')
    for r in reader:
        d = dsv.rawscan(r)
        for m in MODES:
            z = ATTRS[m]
            o = mod(**d)
            p = mod.objectslice(o, z)
            k = mod.object2tuple(p, m)
            if k in flt[m]:
                dat[m][k].append(o)

# prepare for json serialization
out = {}
for m in MODES:
    out[m] = {}
    for f in flt[m]:
        tj = json.dumps(f)
        out[m][tj] = []
        for x in dat[m][f]:
            d = dsv.scan2dict(x)
            out[m][tj].append(d)

# generate json file
with open(ext.genpath('scans.json'), 'w') as f:
    json.dump(out, f)


'''
for f in flt['LT']:
    n = len(dat['LT'][f])
    print(f, n)
'''

'''
for f in flt['W']:
    x = dat['W'][f][0]
    print(x.bssid, x.level, x.room)
'''
