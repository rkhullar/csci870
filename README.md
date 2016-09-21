# Project

## Setup
* distribution files
``` sh
cp deploy/config.py.dist config.py
cp deploy/wsgi.py.dist wsgi.py
sudo cp dist/apache.conf.dist /etc/apache2/sites-available/<name>.conf
# edit the files appropriately
```

* virtual environment
``` sh
virtualenv -p python3 local
. local/bin/activate
pip install Flask
pip install psycopg2
deactivate
```

* database
``` sh
sudo adduser <user>
sudo su -l postgres
psql
create user <user> with superuser password '<pass>'; 
create database <db> owner <user>;
alter role <user> in database <db> set search_path to public,dbo,dbv,new,map;
su -l <user>
psql -d <db> < <script.sql>
```

## Notes
1. test api with curl
``` sh
curl -X POST -d 'data=one' csci870.nydev.local/api/test
curl -X GET csci870.nydev.local/api/test
```

## TODO
1. user registration
2. email verification (nyit)
3. require token to push data

## References
1. [https]
2. [email]
3. [wsgi-envvars]

[https]: https://gethttpsforfree.com/
[email]: http://blog.miguelgrinberg.com/post/the-flask-mega-tutorial-part-xi-email-support
[ssl-tls]: https://www.fastmail.com/help/technical/ssltlsstarttls.html
[wsgi-envvars]: http://software.saao.ac.za/2014/10/29/deploying-a-flask-application-on-apache/