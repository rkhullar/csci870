#!local/bin/python

import requests, json, csv

BASEURL = 'https://csci870.nydev.me/api'
SCANS = 'data/scans.csv'


cntx = lambda m: stat.parse(m, api.count(m))

class api:
    @staticmethod
    def count(mode):
        url = '/'.join([BASEURL, 'count', mode])
        r = requests.get(url)
        #print(r.content.decode('UTF-8'))
        return r.json()

class stat:
    def __init__(self, **kwargs):
        for key, val in kwargs.items():
            setattr(self, key, val)

    @staticmethod
    def parse(mode, ja):
        l = []
        for jo in ja:
            x = stat(**jo)
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
        o = stat()
        for k in keys:
            setattr(o, k, d[k])
        return o

    @staticmethod
    def objectslice(o, keys):
        s = stat()
        for k in keys:
            v = getattr(o, k)
            setattr(s, k, v)
        return s

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
    for x in cntx('L'):
        print(x.building, x.floor, x.room, x.count)
