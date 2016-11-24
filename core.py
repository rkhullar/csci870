#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  09/17/16
@updated :  09/21/16
"""

import psycopg2
from config import DB

class core:
    def __init__(self):
        self.con = psycopg2.connect(**DB)
        self.cur = self.con.cursor()

    def close(self):
        if self.con:
            self.con.close()

    def commit(self):
        self.con.commit()

    @staticmethod
    def show(rows):
        if rows:
            for row in rows:
                print(row)

    def exe(self, query, *args):
        try:
            self.cur.execute(query, args)
            return self.cur.fetchall()
        except psycopg2.DatabaseError as e:
            # print(e)
            pass

class datalist:
    def __init__(self, kls):
        self.kls = kls
        self.list = []

    def __str__(self):
        return "\n".join(map(str, self.list))

    def add(self, *args):
        item = self.kls(*args)
        self.list.append(item)

if __name__ == '__main__':
    o = core()
    q = 'select * from dbo.actor'
    r = o.exe(q)
    core.show(r)
    o.close()
