#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  10/18/16
@updated :  12/04/16
"""

from core import core, datalist, model
from person import person
from location import location

count_modes = ['W', 'L', 'T', 'TT', 'LT']
count_fns = {
    'W'  : lambda r: {'bssid':r[0], 'count':r[1]},
    'L'  : lambda r: {'building':r[0], 'floor':r[1], 'room':r[2], 'count':r[3]},
    'T'  : lambda r: {'hour':r[0], 'count':r[1]},
    'TT' : lambda r: {'day':r[0], 'hour':r[1], 'quarter':r[2], 'count':r[3]},
    'LT' : lambda r: {'building':r[0], 'floor':r[1], 'room':r[2], 'hour':r[3], 'count':r[4]}
    }

class scan(model):
    def keys(self):
        return ['uxt', 'userID', 'bssid', 'level', 'building', 'floor', 'room']

    def extra(self):
        return ['email', 'wapID', 'locationID']

    def __str__(self):
        return '%s %d' % (self.bssid, self.level)

    def csv(self):
        return '%d;%d;%s;%d;%s;%d;%s\n' % (self.uxt, self.userID, self.bssid, self.level, self.building, self.floor, self.room)

    @staticmethod
    def dump(o):
        l = []
        for r in o.exe('select * from dbv.scan'):
            x = scan(uxt=int(r[0]), email=r[1], bssid=r[2], level=r[3], building=r[4], floor=r[5], room=r[6])
            x.userID = person.find(o, email=x.email)
            l.append(x)
        return l

    @staticmethod
    def persist(o, x):
        x.locationID = location.find(o, x.building, x.floor, x.room)
        t = o.exe('select new.scan(%s,%s,%s::macaddr,%s::smallint,%s)', x.uxt, x.userID, x.bssid, x.level, x.locationID)
        if(t):
            o.commit()
            return True
        return False

    @staticmethod
    def prepMany(l):
        vt = [] # times
        vw = [] # wifi access points
        vl = [] # signal strength levels
        vb = [] # buildings
        vf = [] # floors
        vr = [] # rooms
        for s in l:
            vt.append(int(s.uxt))
            vw.append(s.bssid)
            vl.append(int(s.level))
            vb.append(s.building)
            vf.append(int(s.floor))
            vr.append(s.room)
        return {'size': len(l), 'uxt': vt, 'bssid': vw, 'level': vl, 'building': vb, 'floor': vf, 'room': vr}

    @staticmethod
    def persistMany(o, userID, l):
        z = []
        for s in l:
            locationID = location.find(o, s.building, s.floor, s.room)
            t = o.exe('select new.scan(%s,%s,%s::macaddr,%s::smallint,%s)', s.uxt, userID, s.bssid, s.level, locationID)
            if(t):
                o.commit()
                z.append(s)
        return scan.prepMany(z)


    @staticmethod
    def count(o, mode):
        if mode not in count_modes:
            return []
        l = []
        for row in o.exe('select * from cnt.%s' % mode):
            d = count_fns[mode](row)
            l.append(d)
        return l


def test01(o):
    for s in scan.dump(o):
        #print(s)
        print(s.uxt, s.wapID, s.level)

def test02(o):
    scan.persist(o, 'F0:00:00:00:00:00', -100)

if __name__ == '__main__':
    o = core()
    #test01(o)
    #test02(o)
    o.close()
