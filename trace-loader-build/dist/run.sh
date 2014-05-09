#/bin/sh
  cd trace-loader-manager
  xterm -e java -jar trace-loader-manager-0.0.1-SNAPSHOT-war-exec.jar &
  cd ..
  cd trace-loader-unit
  xterm -e java -jar trace-loader-unit-0.0.1-SNAPSHOT-war-exec.jar -httpPort 8181&
  cd ..
  cd trace-loader-keeper
  xterm -e java -jar trace-loader-keeper-emulator-0.0.1-SNAPSHOT-war-exec.jar -httpPort 8282&

