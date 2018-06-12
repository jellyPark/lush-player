#!/bin/ash

set -xe 

# Run nginx.
nginx -g 'daemon off;' > /var/log/nginx/access.log 2> /var/log/nginx/error.log &

BIN=$(ls -1 /service/bin |grep -v ".bat")

/service/bin/${BIN}
