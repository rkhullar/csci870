#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  10/22/16
@updated :  10/28/16
"""

import csv
import decor as dec
from config import ADMIN
from core import core
from person import person
from location import location

class inflate:
    @staticmethod
    def admin():
        fn = dec.corify(person.new_admin)
        fn(ADMIN['fname'], ADMIN['lname'], ADMIN['user'], ADMIN['pswd'])

    @staticmethod
    def location():
        fn = dec.corify(location.persist)
        with open('data/location.csv') as csvfile:
            reader = csv.DictReader(csvfile, delimiter=';')
            for row in reader:
                fn(row['abbr'], int(row['floor']), row['room'])

    @staticmethod
    def users():
        fn = dec.corify(person.persist)
        with open('data/user.csv') as csvfile:
            reader = csv.DictReader(csvfile, delimiter=';')
            x = person()
            for row in reader:
                x.id = int(row['id'])
                x.fname  = row['fname']
                x.lname  = row['lname']
                x.email  = row['email']
                x.token  = row['token']
                x.salt   = row['salt']
                x.pswd   = row['pswd']
                fn(x)

if __name__ == '__main__':
    inflate.admin()
    inflate.location()
    inflate.users()
