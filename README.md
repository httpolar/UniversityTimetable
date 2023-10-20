# UniversityTimetable

Small Ktor API written to make it easier for me and
my college friends to check the timetable.

This API allows to create other applications such as
bots and webapps to visualize the data.

## How to deploy

 1. Install [Docker Compose](https://docs.docker.com/compose/);
 2. Download both JAR and docker-compose files from the latest [GitHub Release](https://github.com/httpolar/UniversityTimetable/releases);
 3. Run `docker compose up`;
 4. Stop the container;
 5. Configure `hikari.properties` file according to [HikriCP](https://github.com/brettwooldridge/HikariCP) guide (SQLite and PostgreSQL drivers are included);
 6. Run the container again and now it should be running!

