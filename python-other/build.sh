podman build -t quay.io/pilhuhn/py-simple-http .


#podman manifest create quay.io/pilhuhn/py-simple-http:multi
#podman build --platform linux/amd64,linux/arm64  --manifest quay.io/pilhuhn/py-simple-http:multi .
# podman images
# podman manifest push quay.io/pilhuhn/py-simple-http:multi
