# Helium / Chirpstack integration

Follow progress and roadmap on the [project board](https://github.com/users/disk91/projects/1/views/1)

__/!\ MVP1 ready for HIP70 beta testers only__


### Features (development in progress)

- Manage fully configurable billing capabilities for helium
    - cost per uplink
    - cost per duplicates
    - cost per downlink
    - cost per device creation
    - cost per period of activity
    - cost per period of inactivity
    - limit device DCs consumption on a given period of time
    - Limit device DCs consumption overall
    - upfront billing
    - end of month billing
- Manage the device declaration / deactivation on helium router
- 
- TODO - Manage payment with stripe
- TODO - Manage multi-tenant chirpstack environment
- ...

#### MVP1 - terminated
The MVP1 aim to support the backend device management to support the self hosting situation. It allows to detect device 
addition / removal to declare it on the Helium router automatically. This MVP also supports the DC consumption counting
and allows a large number of invoicing situations as defined above. All the billing configuration is driven by a 
configuration file for the default and can be overrided in the database at a tenant level. This MVP also build the usage 
statistics for later explaination of the invoicing.

#### MVP2 - starting
The MVP2 aim to provide a muti-tenant, self boarding solution allowing seamless access to chirpstack and DC balance display.
It allows platform administrator to manage the tenant billing settings and router DC balance.

#### MVP3 - pending
The MVP3 aim to provide the payment solution for increasing the tenant DC balance. It is planned to have a stripe support
for payment. Crypto payment will be developed once migrated to Solana, when the environment will be in place to test this.

#### MVP4 - pending
The MVP4 aime to provide the user screen to manage all the specific situation like device reactivation, access to invoice, 
consumption statistics... It also includes the users/customer communication solutions for the platform administrator.

#### MVP5 - pending
The MVP5 may address some specific program support like university program to offer a free access to Helium network for
universities. Support of coupons will allow larger program solution including devkit including communications ...


## Pre-requisite

Currently, the solution supports a single route to helium router (1 route = 1 LNS server) and does not create the initial
route. You need to manually generate the private keys and set up a first route.

For this you need to use the [helium config service cli](https://github.com/helium/helium-config-service-cli)
- generate your keypair and makes it registered on router (manual process in beta)
```bash
./main generate-keypair <keyname>.bin
./main env-info --keypair ./<keyname>.bin
```
- init the router settings
```bash
./main env-init
  - change the keypair location
  - nedID C00053
  - select you OUI
  - set default multi-buy value (duplicates)
```
- create an env file
```bash
export HELIUM_KEYPAIR_BIN=./<keyname>.bin
export HELIUM_NET_ID=C00053
export HELIUM_MAX_COPIES=xx
```

- generate the first route
```bash
./main generate-route
./main add devaddr <start> <end> --commit
./main add protocol gwmp --host <your chirpstack server IP> --port <your chirpstack port> --commit
./main create-route --owner <your public key> --commit
```

Once your have this first route created you can run this project and the route will be updated automatically on device 
addition & removal into chirpstack


## Build the application

### pre-requisites
 - install open-sjk-12
```bash
sudo apt-get update
sudo apt-get install default-jdk
```
 - increase the psql max connection
```bash
# when your docker-compose setup is based on volume use
docker exec -ti chirpstack-docker-postgres-1 /bin/bash
vi /var/lib/postgresql/data/postgresql.conf
max_connections = 500 
# when your docker-compose setup does not use volume (disk91.com configuration)
vi /chirpstack/postgresql/postgresql.conf
max_connections = 500                   # (change requires restart)
```

### configure
- edit the configuration.properties file to setup the configurations. In particular the following elements
```bash
# Verify db / mqtt / redis settings
# MQTT
mqtt.server=tcp://<name-of-the-host-in-docke-compose-file>:1883
mqtt.login=
mqtt.password=
mqtt.id=

## PostgreSQL
spring.datasource.url=jdbc:postgresql://<name-of-host-in-docker-compose>:5432/chirpstack
spring.datasource.username=chirpstack
spring.datasource.password=<your-database-password-choosen-on-install>

## Redis
spring.redis.database=0
spring.redis.host=<name-of-the-redis-host-in-docker-compose>
spring.redis.port=6379
spring.redis.username=
spring.redis.password=
spring.redis.timeout=60000
spring.redis.consumerGroup=cgroup_10
spring.redis.consumer=consumer_0

## GPRC
helium.grpc.private.keyfile.path=/etc/helium/pkey.bin
helium.grpc.public.key=<your public key>
helium.grpc.oui=<your oui>
helium.gprc.server=<Helium Grpc Server>
helium.grpc.port=<Helium Gprc Port>
```
- make sure your billing settings are also corresponding to your expectation. The default DCs per Tenant is really large
to support non billing situation for beta test. Take care of this.

- if you make any later change of the configuration file you will have to rebuild the docker container to apply it or
change the start procedure to mount a local configuration file.

### build image
Depends on your server configuration, you will need to run this as root/sudo, or with your local user. Building and running
process use docker commands and need docker rights.

```bash
make build
```

### run image ( not yet ready )
```bash
make KEYFILE=<keyfile>.bin start
```

## Stop the application
- clean exist with cache purge and queing processes

 `curl -fX GET http://127.0.0.1:8090/internal/3.0/exit`

- send SIGTERM message to the process



## License
This program is distributed according to [GPLv3](https://www.gnu.org/licenses/gpl-3.0.en.html) licences. Specific licences 
can be obtained by contacting the Author. Any reuse of this code-source needs to be indicated and modifications/addition need to 
be publicly published, even when small pieces of code or architecture are reused.