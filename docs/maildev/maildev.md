# MailDev

MailDev is an SMTP server, that intercepts all e-mail messages sent through it, allowing for easy debugging of our application.  
For more information about the app, visit https://maildev.github.io/maildev/

## Installation and usage

- Install Docker Desktop - visit the Docker section of our docs for more information
- Run the following command in your terminal: `docker run -p 1080:1080 -p 1025:1025 maildev/maildev`
- Visit `localhost:1080` in your browser to view the MailDev dashboard - all e-mails sent through the app will appear here
