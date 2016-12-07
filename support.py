#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  12/04/16
@updated :  12/07/16
"""

import requests, json, csv

BASEURL = 'https://csci870.nydev.me/api'
SCANS = 'data/scans.csv'
MODES = ['W', 'L', 'T', 'TT', 'LT']
ATTRS = {
    'W': ['bssid'],
    'L': ['building', 'floor', 'room'],
    'T': ['hour'],
    'TT': ['day', 'hour', 'quarter'],
    'LT': ['building', 'floor', 'room', 'hour']
}

class api:
    @staticmethod
    def count(mode):
        url = '/'.join([BASEURL, 'count', mode])
        r = requests.get(url)
        #print(r.content.decode('UTF-8'))
        return r.json()

    @staticmethod
    def cntx(m):
        return mod.parse(m, api.count(m))

    @staticmethod
    def cnts():
        d = {}
        for m in MODES:
            d[m] = api.cntx(m)
        return d

class mod:
    def __init__(self, **kwargs):
        for key, val in kwargs.items():
            setattr(self, key, val)

    @staticmethod
    def vectorlist():
        d = {}
        for m in MODES:
            d[m] = []
        return d

    @staticmethod
    def vectordict():
        d = {}
        for m in MODES:
            d[m] = {}
        return d

    @staticmethod
    def parse(mode, ja):
        l = []
        for jo in ja:
            x = mod(**jo)
            l.append(x)
        return l

    @staticmethod
    def object2dict(obj, keys):
        d = {}
        for k in keys:
            d[k] = getattr(obj, k)
        return d

    @staticmethod
    def dict2object(d, keys):
        m = mod()
        for k in keys:
            setattr(m, k, d[k])
        return m

    @staticmethod
    def objectslice(o, keys):
        m = mod()
        for k in keys:
            v = getattr(o, k)
            setattr(m, k, v)
        return m

    @staticmethod
    def dictslice(d, keys):
        z = {}
        for k in keys:
            z[k] = d[k]
        return z

class dec:
    @staticmethod
    def scanify(fn):
        def decorated():
            with open(SCANS) as csvfile:
                reader = csv.DictReader(csvfile, delimiter=';')
                for row in reader:
                    fn(row)
        return decorated


if __name__ == '__main__':
    for x in api.cntx('L'):
        print(x.building, x.floor, x.room, x.count)
