#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  10/18/16
@updated :  11/24/16
"""

import datetime, time

from core import core, datalist, model
from person import person
from location import location

epoch = datetime.datetime(1970,1,1)

class scan(model):
    def keys(self):
        return ['uxt', 'userID', 'wapID', 'level', 'locationID']

    def extra(self):
        return ['email', 'bssid', 'building', 'floor', 'room']

    def __str__(self):
        return '%s %d' % (self.bssid, self.level)

    def csv(self):
        return '%d;%d;%d;%d;%d\n' % (self.uxt, self.userID, self.wapID, self.level, self.locationID)

    @staticmethod
    def dump(o):
        l = []
        for r in o.exe('select * from dbo.scan'):
            t = (r[0]-epoch).total_seconds()
            l.append(scan(uxt=int(t), userID=r[1], wapID=r[2], level=r[3], locationID=r[4]))
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


def test01(o):
    for s in scan.dump(o):
        #print(s)
        print(s.uxt, s.wapID, s.level)

def test02(o):
    scan.persist(o, 'F0:00:00:00:00:00', -100)

if __name__ == '__main__':
    o = core()
    test01(o)
    #test02(o)
    o.close()
