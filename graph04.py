#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/21/16
@updated :  12/21/16
"""

import json

import numpy as np
from sklearn import datasets

import matplotlib.mlab as mlab
import matplotlib.pyplot as plt

from matplotlib import style, animation
style.use('ggplot')

data = datasets.load_svmlight_file('data/WT/01.dat', dtype=int)
#X = data[0].todense()
targets = data[1]

d = {}
for t in targets:
    t = int(t)
    if t in d:
        d[t] += 1
    else:
        d[t] = 1

x, y, labs = [], [], []
for i in range(min(d), max(d)+1):
    x.append(i)
    y.append(d[i])
    labs.append('L%02d' % i)

title = 'Filtered Number of Scans per Location'

fig, ax = plt.subplots()

index = np.arange(len(x))
bar_width = 0.5
opacity = 0.5

plt.bar(index, y, bar_width, alpha=opacity, color='r')

plt.xticks(index + bar_width, labs)
for label in ax.xaxis.get_ticklabels():
    label.set_rotation(45)

plt.title(title)
plt.xlabel('Location')
plt.ylabel('Number of Scans')

plt.tight_layout()

#plt.show()

fig.savefig('figures/count/F.png')
plt.close()
