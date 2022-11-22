# Helium / Chirpstack integration

## Not yet ready to be used and forked

## Features (development in progress)

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
    - en of month billing
- Manage payment with stripe
- Manage the device declaration / deactivation on helium router
- Manage multi-tenant chirpstack environment
- ...

## Stop the application
- clean exist with cache purge and queing processes

 `curl -fX GET http://127.0.0.1:8090/internal/3.0/exit`
