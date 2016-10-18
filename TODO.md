## Immediate
1. on android wifi scan call api

1. registration
    * concatenate secret key, email, and salt into msg
    * verification url contains email and hash(msg)
    * two api endpoints for register and verify
    * registration page and verification page



## Flask
1. user registration
2. email verification (nyit)
3. require token to push data

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
