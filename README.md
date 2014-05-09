trace-collector-java
====================

To build executable files do:

mvn clean install -P BuildAll

Available build profiles:

  BuildAll
  BuildUnit
  BuildManager
  BuildUnit


Executable files will be placed in ../trace-collector-build/dist
Test environment setup script is in ../trace-collector-build/dist. run.bat for windows and run.sh for linux