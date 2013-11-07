#!/bin/sh

R="$(cd "$(dirname "$0")"; pwd)/.."

CP="$R/conf/$1:$R/lib/*"


if [ "${FC_JVM_OPTS}xxx" = 'xxx' ]; then
  FC_JVM_OPTS="-server -Xmx1g"
fi

echo "classpath: $CP"

java $FC_JVM_OPTS -cp $CP  it.claudiostarnoni.util.activeMqProber.app.MainConsumerFramework