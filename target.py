#!local/bin/python

import csv
from support import cntx, stat, dec, SCANS

memL  = ['building', 'floor', 'room']
memLT = ['building', 'floor', 'room', 'hour']

class analyzer:
    def __init__(self):
        self.cntW  = None; self.fltW  = []; self.datW  = {}
        self.cntT  = None; self.fltT  = []; self.datT  = {}
        self.cntL  = None; self.fltL  = []; self.datL  = {}
        self.cntLT = None; self.fltLT = []; self.datLT = {}

    def request(self):
        self.cntW = cntx('W')
        self.cntT = cntx('T')
        self.cntL = cntx('L')
        self.cntLT = cntx('LT')
        for x in self.cntW:
            self.fltW.append(x.bssid)
        for x in self.cntT:
            self.fltT.append(x.hour)
        for x in self.cntL:
            d = stat.objectslice(x, memL)
            self.fltL.append(d)
        for x in self.cntLT:
            d = stat.objectslice(x, memLT)
            self.fltLT.append(d)

    def prep(self):
        for w in self.fltW:
            self.datW[w] = []
        for t in self.fltT:
            self.datT[t] = []
        for l in self.fltL:
            self.datL[l] = []
        for lt in self.fltLT:
            self.datL[lt] = []

    def filter(self):
        with open(SCANS) as csvfile:
            reader = csv.DictReader(csvfile, delimiter=';')
            for r in reader:

                w = r['bssid']
                if w in self.fltW:
                    self.datW[w].append(r)

                l = stat.dict2object(r, memL)
                if l in self.fltL:
                    self.datL[l].append(r)

def test01():
    for x in cntx('L'):
        print(x.building, x.floor, x.room, x.count)

def test02():
    a = analyzer()
    a.request()
    a.prep()
    a.filter()
    for w in a.datW:
        print(w, len(a.datW[w]))

if __name__ == '__main__':
    #test01()
    test02()
