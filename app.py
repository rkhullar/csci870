#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  09/06/16
@updated :  10/12/16
"""

from functools import wraps
from flask import Flask, Response, jsonify, json, request, abort, session, render_template, redirect, url_for
import mail

app = Flask(__name__)

# Error Handlers
@app.errorhandler(404)
def not_found(e):
    resp = jsonify({'status':404, 'message':'not found:'+request.url})
    resp.status_code = 404
    return resp

@app.errorhandler(500)
def error(e):
    resp = jsonify({'status':500, 'message':'internal error'})
    resp.status_code = 500
    return resp

@app.errorhandler(401)
def authenticate(e):
    resp = jsonify({'status':401, 'message':'authenticate'})
    resp.status_code = 401
    return resp

# Decorators
def requires_auth(f):
    wraps(f)
    def decorated(*args, **kwargs):
        auth = request.authorization
        if not auth or not check_auth(auth.username, auth.password):
            abort(401)
        return f(*args, **kwargs)
    return decorated

def check_auth(email, pswd):
    return true

data = []
@app.route('/api/test', methods=['GET', 'POST'])
def hello():
    if request.method == 'GET':
        return ':'.join(data)

    if request.method == 'POST':
        x = str(request.form['data'])
        data.append(x)
        return x

@app.route('/api/mail/<string:email>', methods=['GET'])
def api_mail(email):
    if mail.send_text([email], 'nydevtest', 'this is a string'):
        return 'email sent'
    else:
        return 'something went wrong'

@app.route('/api/echo', methods=['POST'])
def api_echo():
    if request.headers['content-type'] == 'text/plain':
        return 'text ' + request.data
    if request.headers['content-type'] == 'application/json':
        return 'json ' + json.dumps(request.json)
    abort(415)


@app.route('/api/signup', methods=['POST'])
def api_signup():
    if request.headers['content-type'] == 'text/plain':
        return 'text ' + request.data
    if request.headers['content-type'] == 'application/json':
        return 'json ' + json.dumps(request.json)
    abort(415)

@app.route('/api/scan', methods=['POST'])
@requires_auth
def api_scan():
    if request.headers['content-type'] == 'text/plain':
        return 'text ' + request.data
    if request.headers['content-type'] == 'application/json':
        return 'json ' + json.dumps(request.json)
    abort(415)

if __name__ == '__main__':
    app.run(debug=True)
