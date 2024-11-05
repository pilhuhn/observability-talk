#!/bin/bash

#
# Create a new user and set up 
# a new logical database 


# This runs with standard psql user against the default setup
cat << -MOUSE- | psql

create user demo with password 'lala7';
create database replicator owner demo;


-MOUSE-


