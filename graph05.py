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

N, W = X.shape[0], X.shape[1]
waps = list(range(1, W+1))
labW = list(map(lambda w: 'W%02d' % w, waps))


m = []
for loc in locs:
    m.append([])
    for wap in waps:
        m[loc-1].append(0)

for i in range(N):
    scan = X[i]
    loc = int(y[i])
    for w in range(W):
        lvl = scan.item(w)
        if lvl > -150:
            m[loc-1][w] += 1


title = 'Density of Scans for Location and Access Point'
cmap = plt.cm.Blues

fig, ax = plt.subplots()
cax = plt.imshow(m, interpolation='nearest', cmap=cmap)

plt.colorbar(cax, orientation='horizontal')

tick_marks = lambda labs: np.arange(len(labs))

plt.xticks(tick_marks(labW), labW, rotation=90)
plt.yticks(tick_marks(labL), labL)

plt.title(title)

plt.xlabel('Access Point')
plt.ylabel('Location')

plt.tight_layout()

#plt.show()

fig.savefig('figures/dist-WL.png')
plt.close()
