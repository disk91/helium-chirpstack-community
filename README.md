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
vi /chirpstack/postgresql/postgresql.conf
max_connections = 500                   # (change requires restart)
```

### configure
- edit the configuration.properties file to setup the configurations. In particular the following elements
```bash
## GPRC
helium.grpc.private.keyfile.path=/etc/helium/pkey.bin
helium.grpc.public.key=<your public key>
helium.grpc.oui=<your oui>
helium.gprc.server=<Helium Grpc Server>
helium.grpc.port=<Helium Gprc Port>
```
- make sure your billing settings are also corresponding to your expectation. The default DCs per Tenant is really large
to support non billing situation for beta test. Take care of this.

### build image
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