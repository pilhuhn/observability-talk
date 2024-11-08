# Replicator

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.


## Run in container

Use the provided src/main/docker/Dockerfile.jvm to build a version in container

```shell script
./mvnw package -DskipTests
podman build -f src/main/docker/Dockerfile.jvm -t quay.io/pilhuhn/quarkus-replicator .
```

Then run it:

```shell script

podman run -i --rm -p 8080:8080 -e pg.host=10.200.209.40 -e otel.host=10.200.209.40 quay.io/pilhuhn/quarkus-replicator
```

There are 3 env vars to set (they all default to localhost):
* pg.host: IP of the postgres database
* otel.host: IP of the OTEL endpoint (Jaeger), port 4317
* payment.hostport: IP:Port of the payment service. Default: localhost:8080


## Database

The database contains a list of known teas.
You can use the following pre-populated container image:

```quay.io/pilhuhn/replicator-psql```


### Start postgres

### Connect to Postgres

```shell
$ psql -h localhost -p 5432 -U postgres # password is lala
```

### Create the logical database...

```sql
create database oteltest;
```

### ...and switch to it
```sql
\c oteltest
```

### Create the tea table
```sql
 create table Tea (id bigint not null, cost int default 1, kind varchar(255),  primary key (id));create sequence Tea_SEQ start with 1 increment by 50;
```

### Add teas to the table

```sql
insert  into tea  values (1, 3, 'earl grey');
insert  into tea  values (2, 2, 'sencha');
insert  into tea  values (3, 7, 'matcha');
```
