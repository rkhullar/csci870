#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  09/06/16
@updated :  11/24/16
"""

import decor as dec

from flask import Flask, jsonify, json, request, abort
from flask import render_template, send_file
from io import BytesIO
#from flask import Flask, Response, jsonify, json, request, abort, session, render_template, redirect, url_for

from error import apierror
from mail import send_thread_text

from core import core
from person import person
from location import location
from wap import wap
from scan import scan
from time import time

BASEURL = 'https://csci870.nydev.me'
BASEAPI = BASEURL + '/api'

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
fetch_locations = dec.corify(location.dump)
persist_scan = dec.corify(scan.persist)
persist_scans = dec.corify(scan.persistMany)
fetch_scans = dec.corify(scan.dump)


@app.route('/api/echo', methods=['GET', 'POST'])
def api_echo():
    if request.method == 'GET':
        return jsonify({'message': 'ok'})
    if request.method == 'POST':
        #return request.form['data']
        return request.json

@app.route('/api/time', methods=['GET'])
def api_time():
    resp = {'time': int(time())}
    return jsonify(resp)

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
        send_thread_text([e], 'nydev verify account', u)
        resp['message'] = 'ok'
    return jsonify(resp)

@app.route('/api/verify/<string:email>/<string:hash>', methods=['GET'])
def api_verify(email, hash):
    t = verify(email, hash)
    resp = {'message': 'ok'} if t else {'message': 'not ok'}
    return jsonify(resp)

@app.route('/verify/<string:email>/<string:hash>', methods=['GET'])
def web_verify(email, hash):
    t = verify(email, hash)
    if t:
        return render_template('verify.html')
    abort(400)

@app.route('/api/locations', methods=['GET'])
@dec.auth(pswd)
def api_locations(userid):
    n = 0
    resp = {'message': 'ok', 'building': [], 'floor': [], 'room':[]}
    for l in fetch_locations():
        resp['building'].append(l.building)
        resp['floor'].append(l.floor)
        resp['room'].append(l.room)
        n += 1
    resp['size'] = n
    return jsonify(resp)

@app.route('/api/scan', methods=['POST'])
@dec.auth(pswd)
@dec.json
def api_post_scan(userid):
    data = request.json
    data['userID'] = userid
    resp = {'message': 'not ok'}
    t = persist_scan(**data)
    if t:
        resp['message'] = 'ok'
        for k in data:
            resp[k] = data[k]
    return jsonify(resp)

@app.route('/api/scans', methods=['POST'])
@dec.auth(pswd)
@dec.json
def api_post_scans(userid):
    data = request.json
    n  = int(data['size'])
    vt = data['uxt']
    vw = data['bssid']
    vl = data['level']
    vb = data['building']
    vf = data['floor']
    vr = data['room']
    l = []
    for i in range(n):
        x = scan(uxt=int(vt[i]), bssid=vw[i], level=int(vl[i]), building=vb[i], floor=int(vf[i]), room=vr[i])
        l.append(x)
    resp = persist_scans(userid, l)
    return jsonify(resp)

@app.route('/download/users', methods=['GET'])
@dec.auth(admin)
def download_users(userid):
    return download_csv('users.csv', person)

@app.route('/download/waps', methods=['GET'])
@dec.auth(admin)
def download_waps(userid):
    return download_csv('waps.csv', wap)

@app.route('/download/scans', methods=['GET'])
@dec.auth(admin)
def download_scans(userid):
    return download_csv('scans.csv', scan)

def download_csv(filename, kls):
    fn = dec.corify(kls.dump)
    buffer = BytesIO()
    buffer.write(kls().csvh().encode('utf-8'))
    for x in fn():
        buffer.write(x.csv().encode('utf-8'))
    buffer.seek(0)
    return send_file(buffer, as_attachment=True, attachment_filename=filename, mimetype='text/csv')


if __name__ == '__main__':
    app.run(debug=True)
