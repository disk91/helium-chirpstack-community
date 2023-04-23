POSTGRESIP=$(shell docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' `docker ps | grep postgres | cut -d " " -f 1`) 
REDISIP=$(shell docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' `docker ps | grep redi | cut -d " " -f 1`) 
MQTTIP=$(shell docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' `docker ps | grep mosqui | cut -d " " -f 1`) 
NETWORK=$(shell docker network ls | grep chirpstack | cut -d " " -f 1)
CONSOLE_NAME=Helium-Console
CONSOLE_TERMS=https://...
API_HOST=
CHIRPSTACK_HOST=
CONSOLE_DIR=/helium

.FORCE:

init: .FORCE
	if [ ! -d $(CONSOLE_DIR) ] ; then mkdir $(CONSOLE_DIR) ; fi
	cp -R chirpstack/* $(CONSOLE_DIR)/
	if [ -d $(CONSOLE_DIR)/prometheus ] ; then chown nobody:nogroup $(CONSOLE_DIR)/prometheus; fi
	if [ -d $(CONSOLE_DIR)/grafana ] ; then chown 472:root $(CONSOLE_DIR)/grafana ; fi

front: .FORCE
	export CONSOLE_NAME=$(CONSOLE_NAME) ; export CONSOLE_TERMS=$(CONSOLE_TERMS) ; \
	export API_HOST=$(API_HOST) ; export CHIRPSTACK_HOST=$(CHIRPSTACK_HOST) ; \
	cd nuxt/console && yarn install --ignore-engines && yarn generate && cp -R dist/* /helium/front/

back: .FORCE
	./gradlew build -x test && docker build -t disk91/console .

build: front back

start:
	-docker stop console
	-docker rm console
	docker run --name console \
	           --restart always \
	           --expose 8081 \
               --mount type=bind,source=$(KEYFILE),target=/etc/helium/pkey.bin \
               --network=$(NETWORK) \
	           --add-host postgres:$(POSTGRESIP) \
               --add-host redis:$(REDISIP) \
               --add-host mosquitto:$(MQTTIP) \
       	       -d disk91/console


