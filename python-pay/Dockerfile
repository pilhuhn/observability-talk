FROM quay.io/fedora/python-313

# Add application sources with correct permissions for OpenShift
USER 0
RUN chown -R 1001:0 ./
USER 1001

ADD . app-src/
RUN pip install -r app-src/requirements.txt

ENV pg_host=localhost
ENV otel_host=localhost
ENV other_hostport=localhost:8000

EXPOSE 8787

# Run the application
CMD python app-src/server5.py -pg_host=${pg_host} -otel_host=${otel_host} -other_hostport=${other_hostport}
