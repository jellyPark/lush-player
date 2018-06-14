SHELL=/bin/bash
VERSION=1
TIMESTAMP=local-$(shell date +%s) # Helm has a meltdown with purge numeric tags & converts them to scientific notation!
K8S_HOST=minikube

ifeq ($(findstring aggregators,$(CURDIR)),aggregators)
	SERVICE_NAME=agg-$(shell basename $(CURDIR))
else
	SERVICE_NAME=$(shell basename $(CURDIR))
endif

NAMESPACE=$(SERVICE_NAME)-$(VERSION)

update-context:
	kubectl config use-context $(K8S_HOST)

k8s-init: update-context
	kubectl create ns $(NAMESPACE)
	kubectl create secret docker-registry priregistrykey -n $(NAMESPACE) --docker-server=registry.platformserviceaccount.com --docker-username=CI --docker-password=jzBbYu7w9652Jkz2 --docker-email=webdev@lush.co.uk

k8s-up: update-context
	@eval $$(minikube docker-env --shell=bash) ;\
	docker build -f ./Dockerfile -t $(SERVICE_NAME):$(TIMESTAMP) .
	helm upgrade --install --namespace $(NAMESPACE) --values local-values.yaml --set serviceName="$(SERVICE_NAME)-master-local",serviceNamespace="$(NAMESPACE)",ciEnvironmentSlug="local",ciPipelineId="1",ciBuildId="1",privateRegistryKey="priregistrykey",ciEnvironmentHostname="localhost",serviceEnvironment="local",serviceBranch="master",developmentVolumeMapping=false,pullPolicy="IfNotPresent",serviceImage="$(SERVICE_NAME)",serviceImageTag="$(TIMESTAMP)" $(SERVICE_NAME)-master-local soa-charts/java-aggregator

k8s-down: update-context
	helm delete $(SERVICE_NAME)-master-local --purge

k8s-refresh: update-context
	@eval $$(minikube docker-env --shell=bash) ;\
	docker build -f ./Dockerfile -t $(SERVICE_NAME):$(TIMESTAMP) .
	kubectl -n$(NAMESPACE) set image deploy/$(SERVICE_NAME)-master-local $(SERVICE_NAME)-master-local=$(SERVICE_NAME):$(TIMESTAMP)

# Install vendorised dependencies with dev dependencies.
install-dev:
	docker run --rm -v $(CURDIR)/service:/app composer install

# Install vendorised dependencies without dev dependencies.
install:
	docker run --rm -v $(CURDIR)/service:/app composer install --no-dev --optimize-autoloader

test: install-dev
	docker build -f ./Dockerfile -t $(SERVICE_NAME)-test:$(TIMESTAMP) .
	docker run $(SERVICE_NAME)-test:$(TIMESTAMP) ash -c './vendor/bin/javaunit'
	docker rmi -f $(SERVICE_NAME)-test:$(TIMESTAMP)
