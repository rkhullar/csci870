#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/08/16
@updated :  12/09/16
"""

import csv, json
from support import api, mod, dsv, ext
from support import MODES, ATTRS

# make api call
cnt = api.cnts()

# based on api results generate bar graphs
# don't bother genreating graphs bar graphs for filtered dataset
