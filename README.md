# Kry/Livi-Interview
This repo is my response to the Kry/Livi coding assignment. In summary, the aim is to build an app that could periodically montior the status of different services.

This repo is split into three directories, starting from the `database`, `backend` and `frontend` - their namings describe exactly what they represent.

Follow these steps to get the app working on your machine:

# Local setup steps

## clone repo:

Cloning the repo and __cd__ into the project directory:

```teriminal
git clone https://github.com/VaikkunVenkat/LiviKry-Interview.git
cd LiviKry-Interview
```

## Database:

The __MYSQL__ database settup has been derived from the [code assignment tools repo](https://github.com/webbhalsa/code-assignment-tools). After having cloned the repo and having ensured you have docker installed and have the daemon running:

```terminal
cd database
docker-compose up -d
```

This will instantiate a running docker container as described by the `docker-compose.yml` file. It will be running MySQL with one `dev` database running on port 3309. Connecting to the database using your client of preference (I like to use [SequelAce](https://apps.apple.com/us/app/sequel-ace/id1518036000?mt=12)), you will see 4 rows of data pre-populated in the `services` table (as described in `database/init.sql`).


## Backend:

The backend has been built with the [Spring-Boot](https://spring.io/projects/spring-boot) framework. After you `cd` into the `backend/` folder, run the following to start the local server:

```terminal
  ./mvnw spring-boot:run
```

To run backend tests (which does not necessarily need the server running), run the following: 

```terminal
  ./mvnw test
```

## Frontend:

The front-end has been built with [create-react-app](https://reactjs.org/docs/create-a-new-react-app.html). After you `cd` into the `frontend/` folder, run the following to install dependencies and start the server:

```terminal
npm install
npm start
```

You can run the unit test suite in interactive watch mode with `npm test` (press `a` to run all tests) and the end-to-end cypress suite with `npm run cypress:run`

Hopefully at this point, you should have the docker container running the MySQL database running. the backend server running as well as the front-end!

In terms of adding in API services to check out manually, I've found a nice list of apis online [here](https://mixedanalytics.com/blog/list-actually-free-open-no-auth-needed-apis/).

