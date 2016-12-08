#!local/bin/python

import shutil, json, csv
from support import dsv, mod, ext

'''
p = ext.genpath('scans.csv')
p = ext.genpath('T', '7.csv')
print(p)
t = ext.ispath('scans.csv')
print(t)
ext.rmdir('test')
'''

'''
t1 = [12]
t2 = [16]

out = {}
out['T'] = {}
tj = json.dumps(t1)
out['T'][tj] = 'one'
tj = json.dumps(t2)
out['T'][tj] = 'two'

with open(ext.genpath('test.json'), 'w') as f:
    json.dump(out, f)
'''

'''
with open(ext.genpath('scans.csv'), 'r') as csvfile:
    reader = csv.DictReader(csvfile, delimiter=';')
    for r in reader:
        d = dsv.rawscan(r)
        o = mod(**d)
        #print(o.dow, o.hour, o.quarter)
        if d['dow'] == 3 and d['hour']==10 and d['quarter']==1:
            print('ok')
'''
