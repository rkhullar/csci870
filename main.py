#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  09/06/16
@updated :  09/06/16
"""

#from functools import wraps
from flask import Flask, request, session, render_template, redirect, url_for

app = Flask(__name__)

# Error Handlers
@app.errorhandler(404)
def page_not_found(e):
    return render_template('404.html'), 404

if __name__ == '__main__':
    app.run(debug=True)
