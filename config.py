from os import environ as e
SECRET = e['SECRET']
DB = {'database': e['DB_NAME'], 'user': e['DB_USER'], 'host': 'localhost', 'password': e['DB_PSWD']}
MAIL = {'srvr': 'smtp.gmail.com', 'user': e['MAIL_USER'], 'pswd': e['MAIL_PSWD']}
ADMIN = {'user': e['ADMIN_USER'], 'pswd': e['ADMIN_PSWD'], 'fname': e['ADMIN_FNAME'], 'lname': e['ADMIN_LNAME']}
