#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  09/06/16
@updated :  09/15/16
"""

#from functools import wraps
from flask import Flask, abort, request, session, render_template, redirect, url_for

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


if __name__ == '__main__':
    app.run(debug=True)
