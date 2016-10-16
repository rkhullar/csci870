#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  10/15/16
@updated :  10/15/16
"""

class apierror(Exception):
    def __init__(self, message, code=400, payload=None):
        Exception.__init__(self)
        self.message = message
        self.code = code
        self.payload = payload

    def pkg(self):
        d = dict(self.payload or ())
        d['message'] = self.message
        d['status'] = self.code
        return d

if __name__ == '__main__':
    try:
        raise apierror('hello')
    except apierror as e:
        print(e.pkg())
