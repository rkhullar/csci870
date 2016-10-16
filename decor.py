#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  10/15/16
@updated :  10/16/16
"""

from functools import wraps, update_wrapper
from flask import request, abort
from error import apierror
from core import core

def auth(checker):
    def decorator(fn):
        def decorated(*args, **kwargs):
            auth = request.authorization
            if not auth or not checker(auth.username, auth.password):
                raise apierror('authenticate', 401)
                #abort(401)
            return fn(*args, **kwargs)
        return update_wrapper(decorated, fn)
    return decorator

def json(fn):
    @wraps(fn)
    def decorated(*args, **kwargs):
        if request.headers['content-type'] != 'application/json':
            #raise apierror('unsupported media type', 415)
            abort(415)
        return fn(*args, **kwargs)
    return decorated
    def decorated(*args, **kwargs):
        o = core()
        t = fn(o, *args, **kwargs)
        o.close()
        return t

def corify(fn):
    def decorated(*args, **kwargs):
        o = core()
        t = fn(o, *args, **kwargs)
        o.close()
        return t
    return decorated

if __name__ == '__main__':
    from person import person
    f = corify(person.login)
    t = f('rkhullar@nyit.edu', 'aaaaaa', modeP=True)
    print(t)
