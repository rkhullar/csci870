#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  10/15/16
@updated :  10/15/16
"""

from core import core, datalist

class person:
    def __init__(self, id, fname, lname, email):
        self.id = id
        self.fname = fname
        self.lname = lname
        self.email = email

    def __str__(self):
        return '%d %s %s %s' % (self.id, self.fname, self.lname, self.email)

    @staticmethod
    def dump(o):
        t = o.exe('select id, fname, lname, email from dbo.actor')
        l = []
        for r in t:
            l.append(person(int(r[0]), r[1], r[2], r[3]))
        return l

    @staticmethod
    def register(o, fname, lname, email, pswd='aaaaaa'):
        t = o.exe('select new.actor(%s,%s,%s,%s)', fname, lname, email, pswd)
        if(t):
            o.commit()
            return int(t[0][0])
        return False



def test01(o):
    for p in person.dump(o):
        print(p)

def test02(o):
    id = person.register(o, 'John', 'Adams', 'jadams@nyit.edu')
    print(id)

if __name__ == '__main__':
    o = core()
    test01(o)
    test02(o)
    o.close()
