#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    create role chirpstack with login password 'fdfce449c0ba34332525';
    create database chirpstack with owner chirpstack;
EOSQL
