#!/bin/bash
if [ "$HELIUM_OUI" == "" ] ; then
   echo "You need to source the env file first"
   exit 1
fi

if [ $# -eq 0 ] ; then
  echo "Usage : clear_routes.sh hostname --commit"
  exit 1
fi

python_script=$(cat <<EOF
import sys
import json

routes=json.load(sys.stdin)
for r in routes['routes']:
   if ( r['server']['host'] == sys.argv[1] ) :
     print(r['id'])
EOF
)

ids=`./helium_cli route list | python3 -c "$python_script" $1`
while IFS= read -r line ; do
   ./helium_cli route delete --route-id $line $2
done <<< "$ids"