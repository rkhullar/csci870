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

class DecayView:
    def __init__(self, fname='data/perf.json'):
        for k in ['all_waps', 'n_waps', 'n_waps_plus_time']:
            setattr(self, k, load_perf(k, fname))

    def fetch(self, k):
        d = getattr(self, k)
        return d['n'], d['mean'], d['std']

    def graph(self, title='Performance Decay', fname=None):
        fig = plt.figure()
        x, y, s = self.fetch('n_waps')
        plt.errorbar(x, y, yerr=s, color='r', label='Without Time Features')
        x, y, s = self.fetch('n_waps_plus_time')
        plt.errorbar(x, y, yerr=s, color='g', label='With Time Features')
        #plt.xlim(max(x))
        #plt.xlim(1)
        if title:
            plt.title(title)
        plt.xlabel('Number of WAP Features')
        plt.ylabel('Prediction Accuracy')
        plt.legend(loc='best')
        plt.tight_layout()
        if fname:
            fig.savefig(fname)
            plt.close()
        else:
            plt.show()
        plt.show()

if __name__ == '__main__':
    dv = DecayView()
    #dv.graph()
    dv.graph(title='Prediction Accuracy vs. Number of WAP Features', fname='figures/pdecay.png')
