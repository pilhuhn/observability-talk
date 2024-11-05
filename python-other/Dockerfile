FROM quay.io/fedora/python-313

# Add application sources with correct permissions for OpenShift
USER 0
RUN chown -R 1001:0 ./
USER 1001

EXPOSE 8000

# Run the application
# most simple http server.

CMD python -m http.server
