#!/bin/bash

cat << -MOUSE- | psql -d replicator -U demo

\conninfo


create table Tea (id bigint not null, cost int default 1, kind varchar(255),  primary key (id));
create sequence Tea_SEQ start with 1 increment by 50;
insert  into tea  values (1, 3, 'earl grey');
insert  into tea  values (2, 2, 'sencha');
insert  into tea  values (3, 7, 'matcha');


-MOUSE-


