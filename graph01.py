#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/08/16
@updated :  12/09/16
"""

import csv, json
from support import api, mod, dsv, ext
from support import MODES, ATTRS

import numpy as np
import pandas as pd
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt

from matplotlib import style, animation
#style.use('fivethirtyeight')
style.use('ggplot')

df = pd.read_json('https://csci870.nydev.me/api/count/W', 'records')
print(df)



'''
# make api call
cnt = {}
for m in MODES:
    cnt[m] = api.count(m)


# graph wifi access point distribution
m = 'W'
size = len(cnt[m])
bssids = []
counts = []
labels = []
i = 1
for d in cnt[m]:
    v = d['bssid']
    bssids.append(v)
    v = d['count']
    counts.append(v)
    v = 'W%02d' % i
    labels.append(v)
    i += 1

for i in range(size):
    print(bssids[i], counts[i])

fig, ax = plt.subplots()
factor = 10
index = np.arange(0, factor*size, factor)

bar_width = 5.0
opacity = 0.5

rects = plt.bar(index, counts, bar_width,
                 alpha=opacity,
                 color='b')

plt.xlabel('WiFi Access Point')
plt.ylabel('Count')
plt.title('Raw Sample Sizes for WiFi Access Points')
plt.xticks(index + bar_width, labels)
plt.legend()

plt.tight_layout()
plt.show()
'''
