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

## 2016-10-17
1. added user view to differentiate registered vs signup
2. initialized verification endpoint
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
