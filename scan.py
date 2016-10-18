#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  10/18/16
@updated :  10/18/16
"""

import time

from core import core, datalist
from person import person
from location import location

class scan:
    def __init__(self, uxt, email, bssid, level, building, floor, room):
        self.uxt      = uxt
        self.email    = email
        self.bssid    = bssid
        self.level    = level
        self.building = building
        self.floor    = floor
        self.room     = room

    def __str__(self):
        return '%s %d' % (self.bssid, self.level)

    @staticmethod
    def dump(o):
        l = []
        for r in o.exe('select * from dbv.scan'):
            l.append(scan(r[0], r[1], r[2], r[3], r[4], r[5], r[6]))
        return l

    @staticmethod
    def persist(o, bssid, level, building='ANY', floor=0, room='ANY', uxt=None, userID=None):
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


def test01(o):
    for s in scan.dump(o):
        print(s)

def test02(o):
    scan.persist(o, 'F0:00:00:00:00:00', -100)

if __name__ == '__main__':
    o = core()
    test01(o)
    #test02(o)
    o.close()
