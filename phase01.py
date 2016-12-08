#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/04/16
@updated :  12/07/16
"""

import csv
from support import api, mod, dsv
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

# organize csv file
with open(dsv.genpath('scans.csv')) as csvfile:
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

# make output directories
for m in MODES:
    dsv.mkdir(m)

'''
for f in flt['LT']:
    n = len(dat['LT'][f])
    print(f, n)
'''

#'''
for f in flt['W']:
    x = dat['W'][f][0]
    print(x.bssid, x.level, x.room)
#'''
