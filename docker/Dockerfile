FROM openjdk:8-jre-alpine

ENV SERVICE_VERSION=base

ONBUILD ARG SERVICE_VERSION_ARG=0.0.0
ONBUILD ENV SERVICE_VERSION=${SERVICE_VERSION_ARG}

RUN apk add --no-cache ca-certificates nginx openssl

ADD nginx/default.conf /etc/nginx/conf.d/default.conf
ADD nginx/nginx.conf /etc/nginx/nginx.conf

ADD start.sh /start.sh

CMD ["ash", "/start.sh"]
