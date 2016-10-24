#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  10/15/16
@updated :  10/24/16
"""

from config import SECRET
from core import core, datalist
from hashlib import sha256
from base64 import urlsafe_b64encode as encurl64, urlsafe_b64decode as decurl64

class person:
    def __init__(self, id, fname, lname, email):
        self.id = id
        self.fname = fname
        self.lname = lname
        self.email = email
        self.token = None

    def __str__(self):
        return '%d %s %s %s' % (self.id, self.fname, self.lname, self.email)

    @staticmethod
    def dump(o, user=True, actor=False):
        t = 'dbv.user'
        t = 'dbo.actor' if actor else t
        l = []
        for r in o.exe('select id, fname, lname, email from '+t):
            l.append(person(int(r[0]), r[1], r[2], r[3]))
        return l

    @staticmethod
    def login(o, email, secret, modeP=True, modeT=False):
        t = o.exe('select fnd.user(%s, %s, %s, %s)', email, secret, modeP, modeT)
        return t[0][0]

    @staticmethod
    def pswd(o, e, s):
        return person.login(o, e, s, modeP=True)

    @staticmethod
    def token(o, e, s):
        return person.login(o, e, s, modeT=True)

    @staticmethod
    def register(o, fname, lname, email, pswd='aaaaaa'):
        t = o.exe('select new.actor(%s,%s,%s,%s)', fname, lname, email, pswd)
        if(t):
            o.commit()
            id = t[0][0]
            o.exe('insert into dbo.signup(id) values (%s)', id)
            o.commit()
            return id
        return False

    @staticmethod
    def verification(o, id):
        t = o.exe('select email, salt from dbv.signup where id=%s', id)
        if(t):
            e = t[0][0]
            s = t[0][1]
            m = str.encode(SECRET + s + e)
            h = encurl64(sha256(m).digest()).decode('utf-8')
            return h
        return False

    @staticmethod
    def verify(o, email, hash):
        return False

    @staticmethod
    def find(o, email=None, fname=None, lname=None):
        c = None
        if email:
            c = "email='%s'" % email
        if fname and lname:
            c = "fname='%s' and lname='%s'" % (fname, lname)
        if not c:
            return None
        q = 'select id from dbv.user where %s' % c
        t = o.exe(q)
        if t:
            return t[0][0]
        return False

    @staticmethod
    def new_admin(o, fname, lname, email, pswd):
        t = o.exe('select new.actor(%s,%s,%s,%s)', fname, lname, email, pswd)
        if(t):
            o.commit()
            id = t[0][0]
            o.exe('insert into dbo.admin(id) values (%s)', id)
            o.commit()

    @staticmethod
    def admin_login(o, email, pswd):
        t = o.exe('select fnd.admin(%s, %s)', email, pswd)
        return t[0][0]


def test01(o):
    for p in person.dump(o):
        print(p)

def test02(o):
    id = person.register(o, 'John', 'Adams', 'jadams@nyit.edu')
    print(id)

def test03(o):
    t = person.login(o, 'rkhullar@nyit.edu', 'aaaaaa')
    print(t)

def test04(o):
    t = person.find(o, email='nydevteam@gmail.com')
    #t = person.find(o, fname='Rajan', lname='Khullar')
    print(t)

def test05(o):
    t = person.admin_login(o, email='rkhullar@nyit.edu', pswd='seNpai27')
    print(t)

def test06(o):
    t = person.verification(o, 1)
    print(t)

if __name__ == '__main__':
    o = core()
    #test01(o)
    #test02(o)
    #test03(o)
    #test04(o)
    #test05(o)
    test06(o)
    o.close()
