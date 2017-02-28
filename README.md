https://www.getpostman.com/collections/aeb703658fae8a38e3f2

# chat-api

###/register
- Method
    
    **POST**
    
    - **Form / JSON params**

        `username = [string] [required]`

        `password = [string] [required]`
    
    - **Responses**
        - 200 - user logged in
            
            ```json
              {
                "userId": "58b552270c93d10004cb7a5e",
                "token": "$2a$10$0GyC785b5VT9/hHbOiG1rOB1VPbpt.1r7fnr0qfQnrR6pRocrzY26",
                "updatedAt": 1488278055288
              }
            ```
            
        - 409 - user with [username] already exists
        
###/login

- Method
    
    **POST**
    
    - **Form / JSON params**

        `username = [string] [required]`

        `password = [string] [required]`
    
    - **Responses**
        - 200 - user registered
            
            ```json
              {
                "userId": "58b552270c93d10004cb7a5e",
                "token": "$2a$10$0GyC785b5VT9/hHbOiG1rOB1VPbpt.1r7fnr0qfQnrR6pRocrzY26",
                "updatedAt": 1488278055288
              }
            ```
            
        - 404 - user not found or password not match 
                      

###/users

- Method
    
  **GET**
    
     - **Headers**

        `token = [string] [required]`

     - **Responses**

        - 200 - list of users
            
            ```json
                [
                    {
                        "name": "janusz",
                        "password": "$2a$10$bIsv5Im/q01hTSchtPQGBOp.xevOVdfU3OB/JNHg7dzUPDkk24wkC",
                        "id": "58b4a97fc6e4f21da06dbb7c"
                    }
                ]
            ```
            
    - 401 - Bad or expired token 
               
###/conversations

- Method
    
  **GET**
    
     - **Headers**

        `token = [string] [required]`

     - **Responses**

        - 200 - list of conversations
            
            ```json
                [
                  {
                    "id": "58b5536d0c93d10004cb7a60",
                    "users": [
                      "58b4a97fc6e4f21da06dbb7c",
                      "58b552270c93d10004cb7a5e"
                    ],
                    "createdAt": 1488278381155,
                    "updatedAt": 1488278749469,
                    "sentences": [
                      {
                        "userId": "58b552270c93d10004cb7a5e",
                        "content": "januszasdfasdfs",
                        "createdAt": 1488278749469
                      }
                    ]
                  }
                ]
            ```
            
    - 401 - Bad or expired token  
                   
  **POST**
    
     - **Headers**

        `token = [string] [required]`
        
     - **JSON Body**
     
        ```
            [
                [string] [required >= 1] [unique]
            ]
        ```

     - **Responses**

        - 200 - Returns created or existing conversations
            
           ```json
               {
                 "id": "58b5536d0c93d10004cb7a60",
                 "users": [
                    "58b4a97fc6e4f21da06dbb7c",
                   "58b552270c93d10004cb7a5e"
                 ],
                 "createdAt": 1488278381155,
                 "updatedAt": 1488278381155,
                 "sentences": []
               }
           ```
            
    - 401 - Bad or expired token 
                        
    - 400 - Bad request
    
        `{
           "message": "There mus me at least 2 users in conversation. Current: 1"
         }`
         
         `{
            "message": "Duplicated users ids in request"
          }`
          
###/conversations/{conversation_id/sentence

- Method
    
  **POST**
    
     - **Headers**

        `token = [string] [required]`
        
     - **Form / JSON params
     
        `content = [required]`

     - **Responses**

        - 200 - updated conversationn with added sentence
            
            ```json
                [
                  {
                    "id": "58b5536d0c93d10004cb7a60",
                    "users": [
                      "58b4a97fc6e4f21da06dbb7c",
                      "58b552270c93d10004cb7a5e"
                    ],
                    "createdAt": 1488278381155,
                    "updatedAt": 1488278749469,
                    "sentences": [
                      {
                        "userId": "58b552270c93d10004cb7a5e",
                        "content": "Pierwsza sentencja",
                        "createdAt": 1488278749469
                      },
                      {
                        "userId": "58b552270c93d10004cb7a5e",
                        "content": "Druga sentencja",
                        "createdAt": 1488278749469
                      }
                    ]
                  }
                ]
            ```
            
    - 401 - Bad or expired token            
