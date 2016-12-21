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

data = datasets.load_svmlight_file('data/W/-1.dat', dtype=int)
X = data[0].todense()
y = data[1]

locs =  list(range(int(min(y)), int(max(y)+1)))
labL = list(map(lambda l: 'L%02d' % l, locs))

N, W = X.shape[0],X.shape[1]
waps = list(range(1, W+1))
labW = list(map(lambda w: 'W%02d' % w, waps))

dist = {}
for loc in locs:
    dist[loc] = {}
    for wap in waps:
        dist[loc][wap] = 0

for i in range(N):
    scan = X[i]
    loc = int(y[i])
    for w in range(W):
        lvl = scan.item(w)
        if lvl > -150:
            dist[loc][w+1] += 1

print(dist)

'''
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
'''

#plt.show()

#fig.savefig('figures/count/F.png')
#plt.close()
