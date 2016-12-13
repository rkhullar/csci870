#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/13/16
@updated :  12/13/16
"""

import json

import matplotlib.mlab as mlab
import matplotlib.pyplot as plt

from matplotlib import style, animation
style.use('ggplot')

def load_perf(key, fname='data/perf.json'):
    with open(fname, 'r') as f:
        return json.load(f)[key]

def graph_decay(v, vmean, vstd, title='Performance Decay', fname=None):
    fig = plt.figure()
    plt.errorbar(v, vmean, yerr=vstd, color='g')
    plt.xlim(max(v))
    plt.xlim(1)
    if title:
        plt.title(title)
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
    #d = load_perf('all_waps')
    d = load_perf('n_waps_plus_time')
    graph_decay(d['n'], d['mean'], d['std'], title='Determination of Performance over WAP Utilization')
