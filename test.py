#!local/bin/python

import shutil, json
from support import ext

'''
p = ext.genpath('scans.csv')
p = ext.genpath('T', '7.csv')
print(p)
t = ext.ispath('scans.csv')
print(t)
ext.rmdir('test')
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
