ARG BASE_IMAGE=eu.gcr.io/utilities-prod-europe-west2/soa/base-service/java:stable
FROM $BASE_IMAGE

COPY build/distributions/service.tar /service/service.tar

RUN tar -xvf /service/service.tar -C /service --strip-components=1 && rm /service/service.tar
