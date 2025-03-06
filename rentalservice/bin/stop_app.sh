#!/bin/bash

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$SCRIPT_DIR"


cnt=$(ps -ef | grep rentalService.jar | grep -v grep | wc -l) 
if [ $cnt == "0" ]; then
   echo "rentalService already stopped." 
elif [ $cnt -eq 1 ]; then
   echo "stop rentalService. "
   pkill -9 -f rentalService.jar
elif [ $cnt -ge 2 ]; then
   echo "More than 2 process of rentalService is alive, need to check."
fi
