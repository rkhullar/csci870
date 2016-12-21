#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/20/16
@updated :  12/20/16
"""

import json
import numpy as np
import skypy as sp
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt

from sklearn import svm, metrics, datasets
from sklearn.metrics import confusion_matrix
from sklearn.externals import joblib
from sklearn.model_selection import cross_val_score, cross_val_predict

from support import api, mod, ext

from matplotlib import style, animation
style.use('ggplot')

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

# make location list
LOCS = []
for i in range(1, len(LOCR)+1):
    LOCS.append('L%02d' % i)

# confusion matrix labels
target = din['target']
LABS = []
for i in range(min(target), max(target)+1):
    LABS.append('L%02d' % i)

CV = 10

def get_one_score(mode, n):
    if mode == 'W' and n == 0:
        return
    out = {}
    clf = joblib.load('models/%s/%02d.pkl' % (mode, n))
    dat = datasets.load_svmlight_file('data/%s/%02d.dat' % (mode, n))
    X = dat[0].todense()
    y = dat[1]
    s = cross_val_score(clf, X, y, cv=CV)
    out['mean'] = s.mean()
    out['std'] = s.std()
    p = cross_val_predict(clf, X, y, cv=CV)
    out['A'] = metrics.accuracy_score(y, p)
    t, f = get_extra(mode, n, out['A'])
    make_cnf_mtx(y, p, t, f)
    return out

def get_extra(mode, n, score):
    if mode == 'WT':
        if n == 0:
            title = 'Performance With Only Time: %.2f' % (score)
        elif n == 1:
            title = 'Performance With Top WAP and Time: %.2f' % (score)
        else:
            title = 'Performance With Top %d WAPS and Time: %.2f' % (n, score)
    if mode == 'W':
        if n > 1:
            title = 'Performance With Top %d WAPS: %.2f' % (n, score)
        else:
            title = 'Performance With Top WAP: %.2f' % (score)
    fname = 'figures/matrices/%s/%02d.png' % (mode, n)
    return title, fname

def make_cnf_mtx(vtrue, vpred, title='Confusion Matrix', fname=None):
    sp.plot_confusion_matrix(vtrue, vpred, classes=LABS, normalize=True, title=title, fname=fname)

def vprint(d, i=None):
    if i:
        print(i, d['mean'], d['std'], d['A'])
    else:
        print(d['mean'], d['std'], d['A'])

def m01_all_waps():
    return get_one_score('W', -1)

def m02_n_waps(mode):
    out = {'n':[], 'mean':[], 'std':[], 'A':[]}
    def add2out(d, j):
        for k in d:
            if k in out:
                out[k].append(d[k])
        out['n'].append(j)
    N = len(WAPS)
    for i in range(N, -1, -1):
        d = get_one_score(mode, i)
        if d:
            add2out(d, i)
            vprint(d, i)
    return out


if __name__ == '__main__':
    save = {}
    save['all_waps'] = m01_all_waps()
    save['n_waps'] = m02_n_waps('W')
    save['n_waps_plus_time'] = m02_n_waps('WT')
    with open('data/perf.json', 'w') as f:
        json.dump(save, f, separators=(',', ':'))
