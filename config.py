from os import environ
DB = {'database': environ['DB_NAME'], 'user': environ['DB_USER'], 'host': 'localhost', 'password': environ['DB_PSWD']}
MAIL = {'srvr': 'smtp.gmail.com', 'user': environ['MAIL_USER'], 'pswd': environ['MAIL_PSWD']}
