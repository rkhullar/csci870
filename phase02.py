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
waps = api.cntx('W')
#for w in waps:
#    print(w.bssid)

# read json file from phase01
with open(ext.genpath('scans-1.json'), 'r') as f:
    din = json.load(f)

dout = mod.vectordict()

for m in MODES:
    for tj in din[m]:
        ft = json.loads(tj)
        #fo = mod.parseTuple(m, ft)
        #fd = mod.object2dict(fo, ATTRS[m])


print('<done>')
