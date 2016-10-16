#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  10/15/16
@updated :  10/15/16
"""

from functools import wraps
from flask import request
from error import apierror
from core import core
from person import person

def auth(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        auth = request.authorization
        if not auth or not check_auth(auth.username, auth.password):
            raise apierror('authenticate', 410)
        t = None
        if auth:
            o = core()
            t = person.login(o, auth.username, auth.password)
            o.close()
        if not t:
            raise apierror('authenticate', 410)
        return f(*args, **kwargs)
    return decorated

def json(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        if request.headers['content-type'] != 'application/json':
            raise apierror('unsupported media type', 415)
        return f(*args, **kwargs)
    return decorated

"""
def resp_error(n):
    resp = jsonify({'status':401, 'message':'authenticate'})
    resp.status_code = 401
    return resp

@app.errorhandler(401)
def authenticate(e):
    resp = jsonify({'status':401, 'message':'authenticate'})
    resp.status_code = 401
    return resp

@app.errorhandler(404)
def not_found(e):
    resp = jsonify({'status':404, 'message':'not found:'+request.url})
    resp.status_code = 404
    return resp

@app.errorhandler(415)
def bad_media(e):
    resp = jsonify({'status':415, 'message':'unsupported media type'})
    resp.status_code = 415
    return resp

@app.errorhandler(500)
def error(e):
    resp = jsonify({'status':500, 'message':'internal error'})
    resp.status_code = 500
    return resp
"""
