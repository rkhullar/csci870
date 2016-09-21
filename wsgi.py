import os, sys

project = '~'

activate_this = os.path.join(project, 'local', 'bin', 'activate_this.py')
with open(activate_this) as file_:
    exec(file_.read(), dict(__file__=activate_this))
sys.path.append(project)

from main import app as application
application.secret_key = '~'
