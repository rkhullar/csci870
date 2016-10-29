#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  10/22/16
@updated :  10/28/16
"""

import decor as dec
from config import ADMIN
from core import core
from person import person

class inflate:
    @staticmethod
    def admin():
        f = dec.corify(person.new_admin)
        f(ADMIN['fname'], ADMIN['lname'], ADMIN['user'], ADMIN['pswd'])

    @staticmethod
    def location():
        #select new.location('ANY', 0::smallint, 'ANY')
        pass

if __name__ == '__main__':
    inflate.admin()
    inflate.location()
