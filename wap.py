#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  11/24/16
@updated :  11/24/16
"""

from core import core, datalist, model

class wap(model):
    def keys(self):
        return ['id', 'bssid']

    def __str__(self):
        return '%d %s' % (self.id, self.bssid)

    def csv(self):
        return ';'.join([str(self.id), self.bssid])+'\n'

    @staticmethod
    def dump(o):
        l = []
        for r in o.exe('select * from dbo.wap'):
            l.append(wap(id=int(r[0]), bssid=r[1]))
        return l

    @staticmethod
    def persist(o, x):
        o.exe('insert into dbo.wap values(%s,%s)', x.id, x.bssid)
        o.commit()
