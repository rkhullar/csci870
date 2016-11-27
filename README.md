# Indoor Localization via WiFi Fingerprinting Framework

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
alter role <user> in database <db> set search_path to public,dbo,dbv,new,map,fnd;
alter database <db> set extra.secretkey to '<secretkey>';
su -l <user>
psql -d <db> < <script.sql>
```

## Notes
1. test api with curl
``` sh
curl -X POST -d 'data=one' csci870.nydev.local/api/test
curl -X GET csci870.nydev.local/api/test
```

## References
1. [https]
2. [email]
3. [wsgi-envvars]
4. [wifi]
5. [service]
6. [sqlite]

[https]: https://gethttpsforfree.com/
[email]: http://blog.miguelgrinberg.com/post/the-flask-mega-tutorial-part-xi-email-support
[ssl-tls]: https://www.fastmail.com/help/technical/ssltlsstarttls.html
[wsgi-envvars]: http://software.saao.ac.za/2014/10/29/deploying-a-flask-application-on-apache/
[argparse]: https://docs.python.org/3/howto/argparse.html
[wifi]: http://www.tutorialspoint.com/android/android_wi_fi.htm
[service]: https://developer.android.com/training/run-background-service/index.html
[sqlite]: http://www.tutorialspoint.com/android/android_sqlite_database.htm
[service]: https://www.javacodegeeks.com/2014/01/android-service-tutorial.html
[spinner]: http://www.broculos.net/2013/09/how-to-change-spinner-text-size-color.html#.VdM8Opf4Xts
