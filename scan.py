#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  10/18/16
@updated :  11/16/16
"""

import time

from core import core, datalist
from person import person
from location import location

class scan:
    def __init__(self, **kwargs):
        for key in ['uxt', 'userid', 'email', 'bssid', 'level', 'locationID', 'building', 'floor', 'room']:
            setattr(self, key, None)
        for key, val in kwargs.items():
            setattr(self, key, val)

    def __str__(self):
        return '%s %d' % (self.bssid, self.level)

    @staticmethod
    def dump(o):
        l = []
        for r in o.exe('select * from dbv.scan'):
            l.append(scan(uxt=r[0], email=r[1], bssid=r[2], level=r[3], building=r[4], floor=r[5], room=r[6]))
        return l

    @staticmethod
    def persist(o, bssid, level, building='ANY', floor=0, room='any', uxt=None, userID=None):
        if not userID:
            userID = person.find(o, email='nydevteam@gmail.com')
        if not uxt:
            uxt = int(time.time())
        locationID = location.find(o, building, floor, room)
        t = o.exe('select new.scan(%s,%s,%s::macaddr,%s::smallint,%s)', uxt, userID, bssid, level, locationID)
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
        print(s)

def test02(o):
    scan.persist(o, 'F0:00:00:00:00:00', -100)

if __name__ == '__main__':
    o = core()
    #test01(o)
    #test02(o)
    o.close()
