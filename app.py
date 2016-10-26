#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  09/06/16
@updated :  10/25/16
"""

import mail
import decor as dec

from flask import Flask, jsonify, json, request, abort
#from flask import Flask, Response, jsonify, json, request, abort, session, render_template, redirect, url_for

from error import apierror

from core import core
from person import person
from scan import scan

BASEURL = 'https://csci870.nydev.me/api'

app = Flask(__name__)
apierror.apply(app)

# Authorization Methods
pswd = dec.corify(person.pswd)
token = dec.corify(person.token)
admin = dec.corify(person.admin_login)

# User Methods
register = dec.corify(person.register)
verification = dec.corify(person.verification)
verify = dec.corify(person.verify)

# Scan Methods
persist_scan = dec.corify(scan.persist)

@app.route('/api/echo', methods=['GET', 'POST'])
def api_echo():
    if request.method == 'GET':
        return jsonify({'message': 'ok'})
    if request.method == 'POST':
        #return request.form['data']
        return request.json

@app.route('/api/authenticate')
@dec.auth(pswd)
def api_authenticate(userid):
    resp = {'message': 'ok'}
    return jsonify(resp)

@app.route('/api/admin')
@dec.auth(admin)
def api_admin(userid):
    resp = {'message': 'ok'}
    return jsonify(resp)

@app.route('/api/register', methods=['POST'])
@dec.json
def api_register():
    resp = {'message': 'not ok'}
    data = request.json
    t = register(**data)
    if(t):
        e = data['email']
        h = verification(t)
        u = '%s/verify/%s/%s' % (BASEURL, e, h)
        mail.send_text([e], 'nydev verify account', u)
        resp['message'] = 'ok'
    return jsonify(resp)

@app.route('/api/verify/<string:email>/<string:hash>', methods=['GET'])
def api_verify(email, hash):
    t = verify(email, hash)
    resp = {'message': 'ok'} if t else {'message': 'not ok'}
    return jsonify(resp)

@app.route('/api/scan', methods=['POST'])
@dec.auth(pswd)
@dec.json
def api_scan(userid):
    data = request.json
    data['userID'] = userid
    t = persist_scan(**data)
    resp = {'message': 'ok'} if t else {'message': 'not ok'}
    return jsonify(resp)

if __name__ == '__main__':
    app.run(debug=True)
