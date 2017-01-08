# Indoor Localization Framework with WiFi Fingerprinting

## Branches

| BRANCH                      | DESCRIPTION                           |
| :-------------------------- | :------------------------------------ |
| [database][branch-database] | Postgresql scripts                    |
| [flask][branch-flask]       | REST API                              |
| [deploy][branch-deploy]     | apache configuration generator        |
| [android][branch-android]   | android apk source                    |
| [analysis][branch-analysis] | graph generation and machine learning |
| [master][branch-master]     | website, report, and backup script    |

## Server Setup
First, the same environment can be used for both flask and the apache conf generator.

### Virtual Environment
``` sh
virtualenv -p python3 local
. local/bin/activate
pip install Flask
pip install psycopg2
deactivate
```

### Apache
``` sh
cd  deploy
cp secret.dist secret
# fill in the values
./genconf -m ssl
sudo cp <name>.conf /etc/apache2/sites-available/<name>.conf
```

### Database
``` sh
sudo adduser <user>
sudo su -l postgres
psql
create user <user> with superuser password '<pass>';
create database <db> owner <user>;
alter role <user> in database <db> set search_path to public,dbo,dbv,new,map,fnd;
alter database <db> set extra.secretkey to '<secretkey>';
su -l <user>
psql -d <db> < <script.sql>
```

### Backup
``` sh
cd master
./download
```

### Restore
``` sh
cd database
psql -d <db> < rebuild.sql
psql -d <db> < methods.sql
psql -d <db> < extra.sql
```

``` sh
cd flask
mv <backup>.csv data/locations.csv
mv <backup>.csv data/users.csv
mv <backup>.csv data/waps.csv
mv <backup>.csv data/scans.csv
./inflate.py
```

``` sql
select setval('dbo.actor_id_seq', (select max(id) from dbo.actor));
select setval('dbo.wap_id_seq', (select max(id) from dbo.wap));
select last_value from dbo.actor_id_seq;
select last_value from dbo.wap_id_seq;
```

[branch-analysis]: https://github.com/rkhullar/csci870/tree/analysis
[branch-android]: https://github.com/rkhullar/csci870/tree/android
[branch-database]: https://github.com/rkhullar/csci870/tree/database
[branch-deploy]: https://github.com/rkhullar/csci870/tree/deploy
[branch-flask]: https://github.com/rkhullar/csci870/tree/flask
[branch-master]: https://github.com/rkhullar/csci870/tree/master