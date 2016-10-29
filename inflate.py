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


if __name__ == '__main__':
    inflate.admin()
    inflate.location()
