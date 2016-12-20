#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/11/16
@updated :  12/20/16
"""

import json
import numpy as np

from sklearn import svm
from sklearn import datasets
from sklearn.externals import joblib

from support import api, mod, ext

# read json file from phase03
with open(ext.genpath('scans-3.json'), 'r') as f:
    din = json.load(f)

# prepare for generation
data = din['data']
target = din['target']
size = len(target)

# make waps list
WAPD = din['meta']['wapd']
WAPS = api.lstr(WAPD)

# tran and save
def train_and_save(X, y, m=False, n=-1, C=1):
    f = 'WT' if m else 'W'
    clf = svm.SVC(kernel='linear', C=C)
    datasets.dump_svmlight_file(X, y, 'data/%s/%02d.dat' % (f, n))
    clf.fit(X, y)
    joblib.dump(clf, 'models/%s/%02d.pkl' % (f, n))

def m01():
    """ALL WAPS"""
    print('ALL WAPS:')
    X = []
    for w in WAPS:
        X.append(data['waps'][w])
    X = np.array(X).T
    y = np.array(target)
    train_and_save(X, y)

def m02(mode):
    """N WAPS W/O TIME"""
    if mode:
        print('N WAPS W TIME:')
    else:
        print('N WAPS:')
    n = len(WAPS)
    for i in range(n, -1, -1):
        m02_help(i, mode)

def m02_help(n, mode=None):
    """N WAPS W/O TIME HELPER"""
    if not mode and n == 0:
        return
    X = []
    if mode:
        for k in ['dow', 'hour']:
            X.append(data['time'][k])
    for w in WAPS[0:n]:
        X.append(data['waps'][w])
    X = np.array(X).T
    y = np.array(target)
    train_and_save(X, y, mode, n)


def test01():
    m02_help(0)
    m02_help(1)
    m02_help(2)
    m02_help(0, True)
    m02_help(1, True)
    m02_help(2, True)

def test02():
    m02_help(10, True)

def test03():
    m02(None)
    m02(True)

def prod():
    d = m01() # all waps
    m02(None) # increasing waps
    m02(True) # increasing waps with time


if __name__ == '__main__':
    #test01()
    #test02()
    #test03()
    prod()
