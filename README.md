## How to run

1. In terminal:
    ```.\gradlew :bootRun```


2. Local deploy with docker compose:
    ```docker-compose up -d```


3. [Docker hub](https://hub.docker.com/r/vastavy/quotes-api-backend):
    ```docker run -p 8080:8080 vastavy/quotes-api-backend```

## Documentation

 Available at http://localhost:8080/swagger-ui/index.html


## Kameleoon Trial Task

Project requirements:

source code should be published on https://github.com/;
Docker image should be published on https://hub.docker.com/;
project should have docker-compose.yaml for local deploy, OR you can deploy it on a public hosting provider (Heroku, etc);
use any in-memory database (Derby, H2, SQLite);
use Java and Spring Boot.
The API should allow:

creation of a user account (deletion and other CRUD operations not requested). User properties are name, email, password and date of creation;
addition, viewing (including a method to get a random quote), modification, and deletion of quotes (real deletion from the database, not just via an archive flag). Quote properties include content, date of creation / update, link to user who posted it, link to votes;
voting on quotes (either upvote or downvote);
view of the top and worse 10 quotes, the details of each quote, and ideally a graph of the evolution of the votes over time.
Not requested: frontend part, authentication mechanism, and account rights management.
