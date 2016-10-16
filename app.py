#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  09/06/16
@updated :  10/16/16
"""

import mail
import decor as dec

from flask import Flask, jsonify, json, request
#from flask import Flask, Response, jsonify, json, request, abort, session, render_template, redirect, url_for

from core import core
from person import person
from error import apierror

app = Flask(__name__)
apierror.apply(app)

# Authorization Methods
pswd = dec.corify(person.pswd)
token = dec.corify(person.token)


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

@app.route('/api/register', methods=['POST'])
def api_register():
    if request.headers['content-type'] != 'application/json':
        abort(415)
    return '|'.join(request.json)
    #return json.dumps(request.json)

@app.route('/api/scan', methods=['POST'])
@dec.json
def api_scan():
    return json.dumps(request.json)

@app.route('/api/important')
@dec.auth(pswd)
def api_important():
    return 'this is a sensitive string\n'

if __name__ == '__main__':
    app.run(debug=True)
