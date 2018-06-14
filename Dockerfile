FROM registry.platformserviceaccount.com/lush-soa/base-service/java-aggregator:latest

COPY build/distributions/service.tar /service/service.tar

RUN tar -xvf /service/service.tar -C /service --strip-components=1 && rm /service/service.tar
