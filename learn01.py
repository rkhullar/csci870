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

def all_waps():
    X = []
    for w in WAPS:
        X.append(data['waps'][w])
    X = np.array(X).T
    y = np.array(target)
    #print(x.shape, len(WAPS))
    d = svm_svc(X, y)
    make_cnf_mtx(d['true'], d['pred'], d['ascore'],
        title='Performance with all WAPS: %.2f' % ( d['ascore']),
        fname='figures/matrices/w.png')
    return d

def time_n_waps(n):
    X = []
    for k in ['dow', 'hour']:
        X.append(data['time'][k])
    for w in WAPS[0:n]:
        X.append(data['waps'][w])
    X = np.array(X).T
    y = np.array(target)
    d = svm_svc(X, y)
    if n > 1:
        title = 'Performance with Top %d WAPS and Time: %.2f' % (n, d['ascore'])
    else:
        title = 'Performance with Only Time: %.2f' % (d['ascore'])
    make_cnf_mtx(d['true'], d['pred'], d['ascore'],
        title=title,
        fname='figures/matrices/'+str(n)+'.png')
    return d

def reduce_waps():
    n = len(WAPS)
    out = {'n':[], 'mean':[], 'std':[], 'ascore':[]}
    def add2out(d, j):
        for k in d:
            if k in out:
                out[k].append(d[k])
        out['n'].append(j)
    for i in range(n, -1, -1):
        d = time_n_waps(i)
        add2out(d, i)
        break
    return out


def make_cnf_mtx(vtrue, vpred, ascore, title='Confusion Matrix', fname=None):
    cnf_mtx = confusion_matrix(vtrue, vpred)
    sp.plot_confusion_matrix(cnf_mtx, classes=LABS, normalize=True, title=title, fname=fname)


def make_decay_graph(v, vmean, vstd, fname=None):
    fig = plt.figure()
    plt.errorbar(v, vmean, xerr=0.2, yerr=vstd, color='b')
    plt.title('Determination of Performance over WAP Utilization')
    plt.xlabel('WAP Utilization')
    plt.ylabel('Score')
    plt.tight_layout()
    if fname:
        fig.savefig(fname)
        plt.close()
    else:
        plt.show()
    plt.show()


if __name__ == '__main__':
    save = {}
    d = all_waps()
    del d['true']
    del d['pred']
    save['all'] = d
    #time_n_waps(0)
    d = reduce_waps()
    make_decay_graph(d['n'], d['mean'], d['std'], fname='figures/pdecay.png')
    save['red'] = d
    with open('data/perf.json', 'w') as f:
        json.dump(save, f, separators=(',', ':'))
