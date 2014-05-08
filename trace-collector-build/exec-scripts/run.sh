#/bin/sh
if [ -n "$TERM" ]; then
  cd trace-collector-manager
  $TERM -e java -jar trace-collector-manager-0.0.1-SNAPSHOT-war-exec.jar &
  cd ..
  cd trace-collector-unit
  $TERM -e java -jar trace-collector-unit-0.0.1-SNAPSHOT-war-exec.jar -httpPort 8181&
  cd ..
  cd trace-collector-keeper
  $TERM -e java -jar trace-collector-keeper-emulator-0.0.1-SNAPSHOT-war-exec.jar -httpPort 8282&
fi

