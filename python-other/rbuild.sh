#!/bin/sh

PDIR=`basename $PWD`
PROJ=$1
if [ "" == "$PROJ" -o " " == "$PROJ" ]
then
	echo "No projectname/image name given"
	exit 1
fi

cd .. 
tar cf /tmp/$PDIR.tar $PDIR
cd $PDIR

scp /tmp/$PDIR.tar linus:/tmp/
ssh linus "cd /tmp && tar xvf $PDIR.tar"
ssh linus "cd /tmp/$PDIR && chmod +x build.sh && sh -x build.sh"

podman manifest add quay.io/pilhuhn/$PROJ:multi quay.io/pilhuhn/$PROJ:amd64
