#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/04/16
@updated :  12/07/16
"""

import csv
from support import api, mod
from support import SCANS, MODES, ATTRS

cnt = api.cnts()

flt = mod.vectorlist()
for a in ATTRS:
    z = ATTRS[a]
    for x in cnt[a]:
        flt[a].append(mod.objectslice(x, z))

dat = mod.vectordict()
for m in MODES:
    for f in flt[m]:
        dat[m][f] = []

with open(SCANS) as csvfile:
    reader = csv.DictReader(csvfile, delimiter=';')
    for r in reader:
        r['hour'] = 0
        r['day'] = 0
        r['quarter'] = 0
        for m in MODES:
            z = ATTRS[m]
            k = mod.dict2object(r, z)
            if k in flt[m]:
                dat[m][k].append(r)

for f in flt['W']:
    n = len(dat['W'][f])
    print(f.bssid, n)
