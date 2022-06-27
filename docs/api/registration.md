# Registration

In order to use the API, you'll have to become a registered user.  
To do this, send a POST request to `[root-url]/api/v1/users`, with the request body containing the following information
in JSON format:

- username
- password
- name
- surname

Example request body:

```json
{
  "username": "exampleUsername",
  "password": "examplePassword",
  "name": "exampleName",
  "surname": "exampleSurname"
}
```

After that, you should get a "SUCCESS" response, which means you can now use the API.  
[See the next step to find out how to authenticate with the API](authentication.md)