#/bin/sh
  cd trace-collector-manager
  xterm -e java -jar trace-collector-manager-0.0.1-SNAPSHOT-war-exec.jar &
  cd ..
  cd trace-collector-unit
  xterm -e java -jar trace-collector-unit-0.0.1-SNAPSHOT-war-exec.jar -httpPort 8181&
  cd ..
  cd trace-collector-keeper
  xterm -e java -jar trace-collector-keeper-emulator-0.0.1-SNAPSHOT-war-exec.jar -httpPort 8282&

