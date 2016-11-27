#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  10/18/16
@updated :  11/24/16
"""

from core import core, datalist, model

class location(model):
    def keys(self):
        return ['id', 'building', 'floor', 'room']

    def __str__(self):
        return '%s %d %s' % (self.building, self.floor, self.room)

    @staticmethod
    def dump(o):
        l = []
        for r in o.exe('select * from dbv.location'):
            l.append(location(id=r[0], building=r[1], floor=r[2], room=r[3]))
        return l

    @staticmethod
    def find(o, building, floor, room):
        t = o.exe('select fnd.location(%s,%s::smallint,%s)', building, floor, room)
        if t:
            return t[0][0]
        return False

    @staticmethod
    def persist(o, x):
        t = o.exe('select new.location(%s,%s::smallint,%s)', x.building, x.floor, x.room)
        o.commit()
        if t:
            return t[0][0]
        return False


def test01(o):
    location.persist(o, 'ANY', 0, 'any')

def test02(o):
    for x in location.dump(o):
        print(x)

def test03(o):
    t = location.find(o, building='ANY', floor=0, room='any')
    print(t)

if __name__ == '__main__':
    o = core()
    #test01(o)
    #test02(o)
    #test03(o)
    o.close()
