<h1>Authenticating with the API</h1>
This API uses JWT based authentication.

The steps required to authenticate are as follows:

- Send a POST request to `[root-url]/api/v1/login`
    - The request body should contain your username and password in a JSON object format, for example:

     ```json
      {
      "username":"example",
      "password":"example"
      }
     ```
- You'll receive an `access token` and a `refresh token`.
    - In every request that you'd like to send to the API, make sure to include the access token in your `Authorization`
      header. <br>
      The header content should look as follows: `Bearer [access token here]`
- When your access token expires, send a POST request to `[root-url]/api/v1/refresh`.<br>
    - Make sure to include the refresh token in the Authorization header, as follows: `Bearer [refresh token here]`
- You'll receive a new access token
- When your refresh token expires, you'll have to use the login endpoint again.
      