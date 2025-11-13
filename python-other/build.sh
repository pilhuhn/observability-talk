#!/bin/sh

xarch=`uname -m`
case $xarch in
	'arm64')
	arch='arm64'
	;;
	
	'aarch64')
	arch='arm64'
	;;

	'x86_64')
	arch='amd64'
	;;

	*)
	echo "Unknown architecture $xarch"
	exit 1
esac

podman build -t quay.io/pilhuhn/py-simple-http:$arch --platform linux/$arch .
podman push quay.io/pilhuhn/py-simple-http:$arch 



#podman manifest create quay.io/pilhuhn/py-simple-http:multi
#podman build --platform linux/amd64,linux/arm64  --manifest quay.io/pilhuhn/py-simple-http:multi .
# podman images
# podman manifest push quay.io/pilhuhn/py-simple-http:multi
