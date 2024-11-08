#!/bin/sh
set -x

git checkout -b fully-working

podman-compose up  -d

sleep 5

cd python-pay
. venv/bin/activate
python3 server5.py &
python3 kafka-relay.py &

cd ../quarkus-replicator
mvn quarkus:dev -Dpayment.hostport=localhost:8787


