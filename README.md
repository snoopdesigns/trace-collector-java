trace-collector-java
====================

Build instructions:

  1. Run 'mvn clean install -P BuildAll'
  2. To execute Trace Collector Manager run 'java -jar trace-collector-manager/target/trace-collector-manager-0.1-SNAPSHOT-war-exec.jar'
  3. To execute Trace Collector Unit run 'java -jar trace-collector-unit/target/trace-collector-unit-0.1-SNAPSHOT-war-exec.jar'
  4. To execute Trace Collector Keeper Emulator run 'java -jar trace-collector-unit/target/trace-collector-keeper-emulator-0.1-SNAPSHOT-war-exec.jar'

Using Ptrace Emulator:

  1. To properly run UNIT make sure that trace-collector-ptrace-emulator-1.0-SNAPSHOT-with-dependencies.jar is copied in trace-collector-unit/ folder under name 'test.jar'

How to specify execution port:

  -httpPort option can set up a port to run application on
  e.g. java -jar trace-collector-manager-0.1-SNAPSHOT-war-exec.jar -httpPort 8181
