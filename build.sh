#!/bin/sh
./gradlew build -x test && docker build -t disk91/console .
