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
    - cost pers DCs / minimum DCs volume purchasing
    - limit device DCs consumption on a given period of time
    - Limit device DCs consumption overall
    - upfront billing
    - end of month billing
- Allows public signup (or not)
- Ability to limit the number of device and tenant
- Manage the device declaration / deactivation on helium router
- Manage multi-tenant chirpstack environment

- TODO - Manage payment with stripe
- ...

#### MVP1 - terminated
The MVP1 aim to support the backend device management to support the self hosting situation. It allows to detect device 
addition / removal to declare it on the Helium router automatically. This MVP also supports the DC consumption counting
and allows a large number of invoicing situations as defined above. All the billing configuration is driven by a 
configuration file for the default and can be overrided in the database at a tenant level. This MVP also build the usage 
statistics for later explaination of the invoicing.

#### MVP2 - terminated (tag V0.2)
The MVP2 aim to provide a muti-tenant, self boarding solution allowing seamless access to chirpstack and DC balance display.
It allows platform administrator to manage the tenant billing settings and router DC balance.

#### MVP3 - starting
The MVP3 aim to provide the payment solution for increasing the tenant DC balance. It is planned to have a stripe support
for payment. Crypto payment will be developed once migrated to Solana, when the environment will be in place to test this.

#### MVP4 - pending
The MVP4 aime to provide the user screen to manage all the specific situation like device reactivation, access to invoice, 
consumption statistics... It also includes the users/customer communication solutions for the platform administrator.

#### MVP5 - pending
The MVP5 may address some specific program support like university program to offer a free access to Helium network for
universities. Support of coupons will allow larger program solution including devkit including communications ...


## Build the application
The full installation, including the Chirpstack configuration is detailed on (disk91.com blogpost)[https://www.disk91.com/2022/technology/helium/installing-chirpstack-lorawan-network-server-for-helium/]

## Stop the application
- clean exist with cache purge and queueing processes
 `curl -fX GET http://127.0.0.1:8090/internal/3.0/exit`
- send SIGTERM message to the process (sent when docker stop)


## License
This program is distributed according to [GPLv3](https://www.gnu.org/licenses/gpl-3.0.en.html) licence for any private 
IoT device fleet purpose, without restriction.

In case you provide a public service with this software, even free for user, or in case you manage a service for a third party 
with this software you have two options:
- having a minimum Uplink cost of $0.00010 per Uplink x 24 Bytes
- request for a specific license w/o pricing limitation by contacting the author

Specific licences 
can be obtained by contacting the Author. Any reuse of this code-source needs to be indicated and modifications/addition need to 
be publicly published, even when small pieces of code or architecture are reused.