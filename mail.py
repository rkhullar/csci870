#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  09/13/16
@updated :  09/26/16
"""

from config import MAIL
from smtplib import SMTP, SMTP_SSL
from threading import Thread

def send_message(recipients, message, mode='ssl'):
    if mode not in ['ssl', 'tls']:
        return
    try:
        if mode == 'ssl':
            server = SMTP_SSL(MAIL['srvr'], 465)
            server.ehlo()
        if mode == 'tls':
            server = SMTP(MAIL['srvr'], 587)
            server.ehlo()
            server.starttls()
        if not server:
            return
        server.login(MAIL['user'], MAIL['pswd'])
        server.sendmail(MAIL['user'], recipients, message)
        server.close()
        return True
    except:
        return False

def send_text(recipients, subject, body, mode='ssl'):
    fmtstr = 'From: %s\nTo: %s\nSubject: %s\n\n%s'
    message = fmtstr % (MAIL['user'], ', '.join(recipients), subject, body)
    return send_message(recipients, message, mode)

def send_html(recipients, subject, template, mode='ssl'):
    fmtstr = 'From: %s\nTo: %s\nSubject: %s\nMime-Version: 1.0\nContent-Type: text/html\n\n%s'
    with open(template, 'r') as file:
        html = file.read()
    message = fmtstr % (MAIL['user'], ', '.join(recipients), subject, html)
    return send_message(recipients, message, mode)

def send_thread_text(recipients, subject, body, mode='ssl'):
    t = Thread(target=send_text, args=[recipients, subject, body, mode])
    t.start()

def send_thread_html(recipients, subject, template, mode='ssl'):
    t = Thread(target=send_html, args=[recipients, subject, template, mode])
    t.start()


def test01(email):
    send_thread_text([email], 'nydevteam test', 'this is a string')

if __name__ == '__main__':
    from os import environ
    email = environ['MAIL_TEST']
