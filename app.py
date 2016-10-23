#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  09/06/16
@updated :  10/23/16
"""

import mail
import decor as dec

from flask import Flask, jsonify, json, request, abort
#from flask import Flask, Response, jsonify, json, request, abort, session, render_template, redirect, url_for

from error import apierror

from core import core
from person import person
from scan import scan

app = Flask(__name__)
apierror.apply(app)

# Authorization Methods
pswd = dec.corify(person.pswd)
token = dec.corify(person.token)
admin = dec.corify(person.admin_login)

# Model Methods
register = dec.corify(person.register)
persist_scan = dec.corify(scan.persist)

@app.route('/api/echo', methods=['GET', 'POST'])
def api_echo():
    if request.method == 'GET':
        return jsonify('ok')

    if request.method == 'POST':
        #return request.form['data']
        return request.json

@app.route('/api/mail/<string:email>', methods=['GET'])
def api_mail(email):
    if mail.send_text([email], 'nydevtest', 'this is a string'):
        return 'email sent'
    else:
        return 'something went wrong'

@app.route('/api/register', methods=['POST'])
@dec.json
def api_register():
    data = request.json
    t = register(**data)
    # send out email
    # add verification endpoint
    return str(t)

@app.route('/api/verify/<string:email>/<string:hash>', methods=['GET'])
def api_verify(email, hash):
    return 'ok'

@app.route('/api/scan', methods=['POST'])
@dec.auth(pswd)
@dec.json
def api_scan(userid):
    data = request.json
    data['userID'] = userid
    t = persist_scan(**data)
    return str(t)
    #return json.dumps(data)

@app.route('/api/authenticate')
@dec.auth(pswd)
def api_authenticate(userid):
    return jsonify('ok')

@app.route('/api/admin')
@dec.auth(admin)
def api_admin(userid):
    return jsonify('ok')

if __name__ == '__main__':
    app.run(debug=True)
