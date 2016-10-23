import os, sys

base = os.path.dirname(os.path.abspath(__file__))
actv = os.path.join(base, 'local', 'bin', 'activate_this.py')

with open(actv) as file_:
    exec(file_.read(), dict(__file__=actv))

sys.path.append(base)

def application(environ, start_response):
    for key in ['SECRET', 'DB_NAME', 'DB_USER', 'DB_PSWD', 'MAIL_USER', 'MAIL_PSWD', 'ADMIN_FNAME', 'ADMIN_LNAME', 'ADMIN_USER', 'ADMIN_PSWD']:
        os.environ[key] = environ.get(key)
    from app import app
    app.secret_key = environ.get('SECRET')
    return app(environ, start_response)
