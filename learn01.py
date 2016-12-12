#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/11/16
@updated :  12/11/16
"""

import json
import numpy as np
from sklearn import svm
from support import api, mod

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

# prepare for generation
data = din['data']
target = din['target']
size = len(target)
