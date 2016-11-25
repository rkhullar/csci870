#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  10/22/16
@updated :  11/24/16
"""

import csv
import decor as dec
from config import ADMIN
from core import core
from location import location
from person import person
from wap import wap
from scan import scan

class inflate:
    @staticmethod
    def admin():
        fn = dec.corify(person.new_admin)
        fn(ADMIN['fname'], ADMIN['lname'], ADMIN['user'], ADMIN['pswd'])

    @staticmethod
    def general(source, kls):
        fn = dec.corify(kls.persist)
        with open(source) as csvfile:
            reader = csv.DictReader(csvfile, delimiter=';')
            for row in reader:
                fn(kls(**row))

    @staticmethod
    def locations():
        inflate.general('data/locations.csv', location)

    @staticmethod
    def users():
        inflate.general('data/users.csv', person)

    @staticmethod
    def waps():
        inflate.general('data/waps.csv', wap)

if __name__ == '__main__':
    inflate.admin()
    inflate.locations()
    inflate.users()
    inflate.waps()
