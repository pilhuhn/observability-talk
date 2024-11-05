from http.server import BaseHTTPRequestHandler, HTTPServer

from opentelemetry import trace
from opentelemetry.exporter.otlp.proto.grpc.trace_exporter import OTLPSpanExporter
# from opentelemetry.exporter.otlp.proto.http.trace_exporter import OTLPSpanExporter
from opentelemetry.sdk.resources import SERVICE_NAME, Resource
from opentelemetry.sdk.trace import TracerProvider
from opentelemetry.sdk.trace.export import (BatchSpanProcessor, ConsoleSpanExporter)
from opentelemetry.trace import NonRecordingSpan, SpanContext, TraceFlags
from opentelemetry.trace.propagation.tracecontext import TraceContextTextMapPropagator
from opentelemetry.instrumentation.requests import RequestsInstrumentor
from opentelemetry.instrumentation.psycopg2 import Psycopg2Instrumentor

import logging
import psycopg2
import random
import requests
import re
import time


def get_payment(tea:str) -> bool:

    cursor = connection.cursor()

    if random.randint(0, 10) > 7:
        cursor.execute("SELECT pg_sleep(1);")
    cursor.execute("SELECT * FROM tea WHERE kind=(%s);", (tea, ) )

    # Fetch all rows from database
    record = cursor.fetchall()

    print("Data from Database:- ", record)
    price = record[0][1]

    return price <=5

class MyRequestHandler(BaseHTTPRequestHandler):
    """HttpServer request handler for illustration purposes"""

    def do_GET(self):
        self.send_response(200)
        # simulate a slowness every one and then
        if random.randint(0, 10) > 5:
            time.sleep(1.5)

        self.send_header("Content-Type", "text/plain")
        self.end_headers()

        # We get a request like 'GET /payment?tea=sencha HTTP/1.1', let's parse it down
        req = self.requestline
        req = req.split('?')[1]
        req = req.split(' ')[0]
        req = req.split('=')[1]
        req = req.replace('+', ' ')


        is_paid = get_payment(req)

        self.wfile.write(bytes(str(is_paid).lower(), "utf-8"))


if __name__ == "__main__":

    logging.basicConfig(level=logging.INFO)
    random.seed(time.time_ns())


    Psycopg2Instrumentor().instrument(enable_commenter=True, commenter_options={})

    # Set up exporting
    resource = Resource(attributes={
        SERVICE_NAME: "payment"
    })

    # Configure the provider with the service name
    provider = TracerProvider(resource=resource)
    # We need to provide the /v1/traces part when we use the http-exporter on port 4318
    # For the grpc endpoint on port 4317, this is not needed.
    processor = BatchSpanProcessor(OTLPSpanExporter(endpoint="http://localhost:4317"))
    provider.add_span_processor(processor)
    trace.set_tracer_provider(provider)

    tracer = trace.get_tracer(__name__)

    connection = psycopg2.connect(database="replicator",
                                  user="demo",
                                  password="lala7",
                                  host="172.31.7.160", # TODO get from env
                                  port=5432)


    server = HTTPServer(("localhost", 8787), MyRequestHandler)
    print("Server started on port 8787")
    try:
        server.serve_forever()
    except KeyboardInterrupt:
        pass

    server.server_close()
    print("Server stopped.")
