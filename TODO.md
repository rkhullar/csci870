## TODO
1. create persistable cross validation model
2. calculate precision, recall, and f1 score
3. roc curve?

## NOTES
* Day of Week in Python and Postgres are different.

## Main Activity (Login)
* if logged in goto home activity
* ask user for login info (email, pswd)
* login button
* text link to goto signup activity

## Signup activity
* if logged in goto home activity
* ask user for signup info (fname, lname, email, pswd)
* signup button
* text link to goto main (login) activity

## Registration
* concatenate secret key, email, and salt into msg
* verification url contains email and hash(msg)
* two api endpoints for register and verify
* registration page and verification page

## Android Login
* how to check logged in
  1. query user table
* how to login
  1. call api to get location list
  2. if api call ok then
    2a. insert user data to sqlite
    2b. persist locations to sqlite

## WiFi Test Activity
* on generic scan persist results to sqlite
* add button to push local scan results to server

## Old Milestones
1. Base
    * onclick perform wifi scan and post to api
    * add floor and room selection
2. Auto
    * add continuous scan
    * add time limit
3. Safe
    * store data in local sqlite database
    * allow data to be posted to api
