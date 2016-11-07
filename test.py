#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  09/08/16
@updated :  11/06/16
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

def test01():
    url = '/'.join([site, 'api', 'register'])
    #payload = {'fname': 'John', 'lname': 'Adams', 'email': 'jadams@nyit.edu'}
    payload = {'fname': 'John', 'lname': 'Adams', 'email': 'rkhullar@nyit.edu'}
    post(url, payload)

def test02():
    url = '/'.join([site, 'api', 'scan'])
    payload = {'uxt'   : int(time.time()),
               'bssid' : 'F0:00:00:00:00:00',
               'level' : -100}
    post(url, payload)

if __name__ == '__main__':
    call(['./refresh'])
    #test01()
    test02()
