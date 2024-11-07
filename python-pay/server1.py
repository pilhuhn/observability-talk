from http.server import BaseHTTPRequestHandler, HTTPServer

from opentelemetry import trace
from opentelemetry.exporter.otlp.proto.grpc.trace_exporter import OTLPSpanExporter
from opentelemetry.sdk.resources import SERVICE_NAME, Resource
from opentelemetry.sdk.trace import TracerProvider
from opentelemetry.sdk.trace.export import (BatchSpanProcessor)

import logging
import random
import time



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


        is_paid = 'true'

        self.wfile.write(bytes(str(is_paid).lower(), "utf-8"))


if __name__ == "__main__":

    logging.basicConfig(level=logging.INFO)
    random.seed(time.time_ns())


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


    server = HTTPServer(("localhost", 8787), MyRequestHandler)
    print("Server started on port 8787")
    try:
        server.serve_forever()
    except KeyboardInterrupt:
        pass

    server.server_close()
    print("Server stopped.")
