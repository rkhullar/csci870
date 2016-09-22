#!local/bin/python

"""
@author  :  Rajan Khullar
@created :  09/13/16
@updated :  09/21/16
"""

from smtplib import SMTP, SMTP_SSL
from config import MAIL

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
        print('email sent')

    except:
        print('Something went wrong...')


def send_text(recipients, subject, body, mode='ssl'):
    fmtstr = 'From: %s\nTo: %s\nSubject: %s\n\n%s'
    message = fmtstr % (MAIL['user'], ', '.join(recipients), subject, body)
    send_message(recipients, message, mode)


def send_html(recipients, subject, template, mode='ssl'):
    fmtstr = 'From: %s\nTo: %s\nSubject: %s\nMime-Version: 1.0\nContent-Type: text/html\n\n%s'
    with open(template, 'r') as file:
        html = file.read()
    message = fmtstr % (MAIL['user'], ', '.join(recipients), subject, html)
    send_message(recipients, message, mode)


if __name__ == '__main__':
    send_text(MAIL['user'], 'nydevteam test', 'this is a string')
