#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  10/15/16
@updated :  10/16/16
"""

from flask import jsonify
from werkzeug.exceptions import default_exceptions as defex

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

    @staticmethod
    def handle(error):
        resp = jsonify(error.pkg())
        resp.status_code = error.code
        return resp

    @staticmethod
    def fact(code, msg):
        def fn(error):
            try:
                raise apierror(msg, code)
            except apierror as e:
                return apierror.handle(e)
        return fn

    @staticmethod
    def apply(app):
        for code in defex:
            msg = defex[code].__name__
            fn = apierror.fact(code, msg)
            app.register_error_handler(code, fn)
        app.register_error_handler(apierror, apierror.handle)

def test01():
    try:
        raise apierror('hello')
    except apierror as e:
        print(e.pkg())


if __name__ == '__main__':
    test01()
