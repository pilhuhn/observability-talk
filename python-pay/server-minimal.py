from http.server import BaseHTTPRequestHandler, HTTPServer

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

        reply = "true"

        self.wfile.write(bytes(reply, "utf-8"))


if __name__ == "__main__":

    server = HTTPServer(("localhost", 8787), MyRequestHandler)
    print("Server started on port 8787")
    try:
        server.serve_forever()
    except KeyboardInterrupt:
        pass

    server.server_close()
    print("Server stopped.")
