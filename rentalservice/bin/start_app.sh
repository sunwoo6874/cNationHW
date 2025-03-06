#!/bin/bash
RUN_MODE="fg" # choose fg or bg

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$SCRIPT_DIR"

cnt=$(ps -ef | grep rentalService.jar | grep -v grep | wc -l) 
if [ $cnt == "0" ]; then
    export JAVA_OPTS="-Xms4g -Xmx4g -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0 -Djava.io.tmpdir=tmp"
    if [ $RUN_MODE == "bg" ]; then 
        nohup java -jar rentalService.jar &
        echo "starting application rentalService in backGround"
        sleep 0.1
        rm nohup.out
        echo "removing nohup.out . . ."
    elif [ $RUN_MODE == "fg" ]; then
        echo "starting application rentalService in foreGround"
        java -jar rentalService.jar 
    else
        echo "unknown run mode.. check again."
    fi
else
   echo "already running.."
fi
