#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/11/16
@updated :  12/12/16
"""

import json
import numpy as np
import skypy as sp
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt

from sklearn import svm
from sklearn import metrics
from sklearn.metrics import confusion_matrix
from sklearn.model_selection import train_test_split, cross_val_score, cross_val_predict
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
    #LOCS.append(LOCR[i])
    LOCS.append('L%02d' % i)

#l2s = lambda l: l.building + ' ' + l.room

# prepare for generation
data = din['data']
target = din['target']
size = len(target)

# confusion matrix labels
LABS = []
for i in range(min(target), max(target)+1):
    LABS.append('L%02d' % i)

# cross validation 10 fold
def svm_svc(X, y, C=1, cv=10):
    clf = svm.SVC(kernel='linear', C=C)
    scores = cross_val_score(clf, X, y, cv=cv)
    pred = cross_val_predict(clf, X, y, cv=cv)
    ascore = metrics.accuracy_score(y, pred)
    return {'mean': scores.mean(), 'std': scores.std(), 'ascore': ascore,
        'true': y, 'pred': pred}

def t01():
    """ALL WAPS"""
    X = []
    for w in WAPS:
        X.append(data['waps'][w])
    X = np.array(X).T
    y = np.array(target)
    #print(x.shape, len(WAPS))
    d = svm_svc(X, y)
    make_cnf_mtx(d['true'], d['pred'], d['ascore'],
        title='Performance With All WAPS: %.2f' % d['ascore'],
        fname='figures/matrices/all_waps.png')
    return d

def t02(mode):
    """N WAPS W/O TIME"""
    n = len(WAPS)
    out = {'n':[], 'mean':[], 'std':[], 'ascore':[]}
    def add2out(d, j):
        for k in d:
            if k in out:
                out[k].append(d[k])
        out['n'].append(j)
    for i in range(n, -1, -1):
        print(i)
        d = t02_help(i, mode)
        add2out(d, i)
        #break
    return out

def t02_help(n, mode=None):
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
    d = svm_svc(X, y)
    ascore = d['ascore']
    title, fname = t02_extra(n, ascore, mode)
    make_cnf_mtx(d['true'], d['pred'], ascore, title=title, fname=fname)
    return d

def t02_extra(n, s, mode=None):
    """N WAPS W/O TIME EXTRA"""
    if mode:
        if n == 0:
            title = 'Performance With Only Time: %.2f' % (s)
        elif n == 1:
            title = 'Performance With Top WAP and Time: %.2f' % (s)
        else:
            title = 'Performance With Top %d WAPS and Time: %.2f' % (n, s)
    else:
        if n > 1:
            title = 'Performance With Top %d WAPS: %.2f' % (n, s)
        else:
            title = 'Performance With Top WAP: %.2f' % (s)
    t1 = 'WT' if mode else 'W'
    t2 = '%02d' % n
    fname = 'figures/matrices/%s/%s.png' % (t1, t2)
    return title, fname


def make_cnf_mtx(vtrue, vpred, ascore, title='Confusion Matrix', fname=None):
    cnf_mtx = confusion_matrix(vtrue, vpred)
    sp.plot_confusion_matrix(cnf_mtx, classes=LABS, normalize=True, title=title, fname=fname)


def test():
    '''
    t02_help(0)
    t02_help(1)
    t02_help(2)
    t02_help(0, True)
    t02_help(1, True)
    t02_help(2, True)
    '''
    #'''
    t02_help(10, True)
    #'''

def prod():
    # prepare to store metrics
    save = {}
    # try all waps
    d = t01()
    del d['true']
    del d['pred']
    save['all_waps'] = d
    # try n waps w/o time
    save['n_waps'] = t02(None)
    save['n_waps_plus_time'] = t02(True)
    # save to disk
    with open('data/perf.json', 'w') as f:
        json.dump(save, f, separators=(',', ':'))


if __name__ == '__main__':
    #test()
    prod()
