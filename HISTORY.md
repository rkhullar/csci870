## 2016-09-08
1. initialized database

## 2016-09-10
1. used "Let's Encrypt" to generate trusted certificates
2. setup https protocol on server

## 2016-09-11
1. created nydevteam gmail account.
2. learning how to send emails through python

## 2016-09-19
1. experimented with heroku for hosting the project
2. heroku's unpaid plan has a tight limit on data storage

## 2016-09-21
1. learned how to pass environment variables from apache vhost into flask

## 2016-09-22
1. finalized mail system

## 2016-09-25
1. converted multiple repos into one repo with multiple branches
2. initialized project page

## 2016-10-02
1. created python script to generate apache vhost config files

## 2016-10-03
1. initialized history

## 2016-10-09
1. initialized android sqlite database

## 2016-10-10
1. learned how to read request headers
2. created default location and actor in database to use for stage 1
3. added sql functions to aid data reconstruction from csv files

## 2016-10-11
1. learned how to make requests in python in order to test the api

## 2016-10-12
1. using jsonify for api responses
2. learned about request authorization

## 2016-10-14
1. plan out registration verification url
2. tried to read environment variables in postgres
3. decide to only support json media type for post methods

## 2016-10-15
1. initiate model database classes in python
2. work on person model for registration, login, and search
3. authorization decorator works
4. reimplemented error system
5. added json required decorator

## 2016-10-16
1. reimplemented authorization with decorators
2. now api endpoints can easily require password or token based authorization
3. reimplemented error system again to cover all error codes

## 2016-10-17 endpoint
3. learned how to print request responses properly

## 2016-10-18
1. modified auth decorator to add userid keyword arg
2. created endpoint to persist single scan
3. add dbo.admin and dbv.admin to database
4. add admin email and password to environment

## 2016-10-19
1. look into async task to call rest api from android

## 2016-10-20
1. add test activities to app
2. use async http client instead of async task
3. able to perform get request
4. able to add authentication to request
5. create model classes for scan and location

## 2016-10-22
1. learned how to add custom parameters to postgres database
2. added secret key to database

## 2016-10-23
1. updated new.user and fnd.user to include secret key
2. moving database inflation from sql to python
3. added base activity class for android

## 2016-10-24
1. generate verification url for user registration
2. restructured android app
3. app db create table statements successful
4. query, insert, and delete accomplished

## 2016-10-25

1. email verification endpoint
2. limited registration to edu domain
3. login on android app works
4. working on signup from app

## 2016-10-26
1. signup system works
2. mailer operates off main thread

## 2016-10-28
1. starting location csv inflation
2. modified new.location sql method to automatically add buildings
3. added api endpoint to fetch location list

## 2016-10-29
1. added a few locations to the list
2. changed android login system
3. authentication and fetch locations can be done in single request
4. flashed and rooted nexus 5 m4b30x
5. able to to debug sqlite on android by downloading directly
6. created fetch location system for app
7. fixed primary key typo in sqlite

## 2016-10-30
1. researching listview, spinner, and array adapter for android
2. tried converting wifi test scan to use listview instead of toast
3. wifi scan failed on marshmallow

## 2016-10-31
1. researching marshmallow permission system in order to fix wifi scan

## 2016-11-04
1. wifi scan working again, enabling wifi and calling disconnect before starting
2. listview for wifi scan works

## 2016-11-05
1. moved wifi scan receiver class outside of test
2. converted test buttons to home menu

## 2016-11-06
1. design user map for configuring and pushing scans
2. reimplemented home activity with fragments
3. update flask scan endpoint to return post data on success
4. created custom launcher icon

## 2016-11-07
1. test login and register endpoints for sql injection vulnerability

## 2016-11-08
1. reimplemented fragment classes to avoid extra files
2. add building, floor, and room queries to database adapter
3. working on spinners for scan configuration

## 2016-11-09
1. scan config location spinners works
2. updated database adapter floors method with 'distinct' and 'order by'
3. using number picker to select scan duration

## 2016-11-10
1. started implementing background wifi scans with intent service

## 2016-11-11
1. changed wifi scan service from intent service to normal service
2. using count down timer with interval one second

## 2016-11-12
1. working on getting service to run in foreground independent of activity
2. changed naming convention of home fragments
3. countdown timer in foreground service works

## 2016-11-13
1. added unix time api endpoint
2. added notification for when countdown timer complete
3. registered wifi scan receiver in manifest
4. able to perform multiple scans if permission already granted
5. learned how to use logcat system
6. permission request system works
7. changed scan table to use unix time integer instead of datetime
8. able to persist scans in wifi test activity

## 2016-11-14
1. updated android studio sdk platform
2. added method to fetch scan objects from sqlite

## 2016-11-15
1. added list view on scan push activity
2. updated logout method to preserve integrity; truncate all tables
3. implemented scan upload system with time synchronization

## 2016-11-16
1. compiled location list
2. push system on app works with minor bugs
3. fixed typo in database (wpa -> wap)
4. added database constraint to prevent repeatedly persisted scans
5. added endpoint to push multiple scan results
6. improved efficiency of app location download system
7. extended scan upload system with buffer implementation
8. improved user experience for scan upload system
9. using github project page to store apk file

## 2016-11-17
1. improved scan push system again
2. generated bit.ly link for apk
3. updated dbo.location to accommodate room length 50

## 2016-11-20
1. improved python scan model code
2. renewed ssl certificate
3. updated time formatting in app with leading zeros

## 2016-11-21
1. improved user experience for app signup

## 2016-11-23
1. added download endpoint to backup database

## 2016-11-24
1. added inflate users function
2. added wap download endpoint
3. added inflate waps function
4. major code improvements with model class
5. working on scan backup system

## 2016-11-25
1. completed scan backup system

## 2016-11-27
1. found and fixed login bug
2. updated scan configuration interface
3. removed apk from master branch

## 2016-11-29
1. designed sql queries to get counts

## 2016-11-30
1. created backup script

## 2016-12-04
1. added extra views and functions to database
2. initialized analysis branch

## 2016-12-05
1. added api call system to analysis
2. working on structuring the dataset with filters

## 2016-12-06
1. fixed restoration bug with id numbers

## 2016-12-07
1. reimplementing dataset parsing
2. able to organize original dataset into json
3. fixed bug with day of week parsing

## 2016-12-08
1. able to organize dataset grouped by bssid
2. ensured each block's size is greater than 100

## 2016-12-09
1. made one bar graph
2. looked into pandas

## 2016-12-10
1. able to graph raw counts for modes
2. able to filter each group with z score range
3. graphed total distribution of signal strength levels
4. graphed boxplots for each group
5. graphed overall fingerprint

## 2016-12-11
1. preprocess dataset further into matrix of scans
2. generate csv file as input to classifier

## 2016-12-12
1. made linear svn classifier with 10 fold cross validation
2. able to automatically generate and test classifiers
3. created confusion matrices and bar graph to show performance
