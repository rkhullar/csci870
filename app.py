#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  09/06/16
@updated :  09/21/16
"""

#from functools import wraps
from flask import Flask, json, request, abort, session, render_template, redirect, url_for
import mail

app = Flask(__name__)

# Error Handlers
@app.errorhandler(404)
def page_not_found(e):
    return render_template('404.html'), 404

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

@app.route('/api/signup', methods=['POST'])
def api_signup():
    if request.headers['Content-Type'] == 'text/plain':
        return 'text ' + request.data
    if request.headers['Content-Type'] == 'application/json':
        return 'json ' + json.dumps(request.json)

if __name__ == '__main__':
    app.run(debug=True)
