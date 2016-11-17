#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  09/08/16
@updated :  11/07/16
"""

import time, requests, json
from subprocess import call
from config import ADMIN

site = 'http://csci870.nydev.local'
auth = (ADMIN['user'], ADMIN['pswd'])

headers = {'content-type': 'application/json'}

def post(url, payload):
    r = requests.post(url, data=json.dumps(payload), headers=headers, auth=auth)
    print(r.content.decode('UTF-8'))

def get(url):
    r = requests.get(url, auth=auth)
    print(r.content.decode('UTF-8'))

def genurl(*args):
    return '/'.join([site, *args])

def test01():
    url = genurl('api', 'register')
    #payload = {'fname': 'John', 'lname': 'Adams', 'email': 'jadams@nyit.edu'}
    payload = {'fname': 'John', 'lname': 'Adams', 'email': 'rkhullar@nyit.edu'}
    post(url, payload)

def test02():
    url = genurl('api', 'scan')
    payload = {'uxt'   : int(time.time()),
               'bssid' : 'F0:00:00:00:00:00',
               'level' : -100}
    post(url, payload)

def test03():
    url = genurl('api', 'important')
    auth = ('anon','select truncate dbo.actor')
    r = requests.get(url, auth=auth)
    print(r.content.decode('UTF-8'))

def test04():
    url = genurl('api', 'register')
    payload = {'fname': 'mr', 'lname': 'robot', 'email': 'example@uni.edu'}
    auth = ('anon','select truncate dbo.actor')
    r = requests.post(url, data=json.dumps(payload), headers=headers, auth=auth)
    print(r.content.decode('UTF-8'))

def test05():
    url = genurl('api', 'scans')
    payload = {
        'size'     : 3,
        'uxt'      : [1479354594, 1579359999, 1679359999],
        'bssid'    : ['F0:00:00:00:00:00', 'F0:00:00:00:00:01', 'F0:00:00:00:00:02'],
        'level'    : [100, 120, 140],
        'building' : ['ANY', 'EGGC', 'MC16'],
        'floor'    : [0, 6, 10],
        'room'     : ['any', '606', '1026']}
    post(url, payload)

if __name__ == '__main__':
    call(['./refresh'])
    #test01()
    #test02()
    #test03()
    #test04()
    test05()
