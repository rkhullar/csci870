#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  11/24/16
@updated :  11/24/16
"""

from core import core, datalist

class wap:
    def __init__(self, **kwargs):
        for key in wap.keys():
            setattr(self, key, None)
        for key, val in kwargs.items():
            setattr(self, key, val)

    def __str__(self):
        return '%d %s' % (self.id, self.bssid)

    @staticmethod
    def keys():
        return ['id', 'bssid']

    @staticmethod
    def csvh():
        return ';'.join(wap.keys())+'\n'

    def csv():
        return ';'.join([str(self.id), self.bssid])+'\n'

    @staticmethod
    def dump(o):
        l = []
        for r in o.exe('select * from dbo.wap'):
            l.append(wap(id=int(r[0]), bssid=r[1]))
        return l

    @staticmethod
    def persist(o, x):
        pass
