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

## TODO
1. user registration
2. email verification (nyit)
3. require token to push data