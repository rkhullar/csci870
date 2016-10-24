## Immediate
1. wifi test activity, persist scans with generic user and location
2. flask email verification
3. login and signup system
4. mail in background

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
   1. if api call ok, then insert user data to sqlite
 * post successful login
   1. call api to get location list, persist to sqlite

## Flask
1. email verification (nyit)

## Android
1. Base
    * onclick perform wifi scan and post to api
    * add floor and room selection
2. Auto
    * add continuous scan
    * add time limit
3. Safe
    * store data in local sqlite database
    * allow data to be posted to api
