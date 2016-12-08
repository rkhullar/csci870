#!local/bin/python

import shutil
from support import dsv

#p = dsv.genpath('scans.csv')
p = dsv.genpath('T', '7.csv')

print(p)

t = dsv.ispath('scans.csv')

print(t)

dsv.rmdir('test')
