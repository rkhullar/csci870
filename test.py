#!/usr/bin/env python3

"""
@author  :  Rajan Khullar
@created :  09/08/16
@updated :  10/14/16
"""

import time, requests, json
from subprocess import call

site = 'http://csci870.nydev.local'

def test01():
    url = '/'.join([site, 'api', 'register'])
    payload = {'fname': 'John', 'lname': 'Adams', 'email': 'jadams@nyit.edu'}
    headers = {'content-type': 'application/json'}
    r = requests.post(url, data=json.dumps(payload), headers=headers)
    print(r.content)

def test02():
    url = '/'.join([site, 'api', 'scan'])
    payload = {'time': time.time(),
                'actorID': 1,
                'macaddr': '00:00:00:00:00:00',
                'level': -100,
                'locationID': 1}
    headers = {'content-type': 'application/json'}
    r = requests.post(url, data=json.dumps(payload), headers=headers)
    print(r.content)

if __name__ == '__main__':
    call(['./refresh'])
    test01()
    #test02()
