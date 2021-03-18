#!/usr/bin/env bash
java \
  -Dname=led-test-tool \
  -cp /home/pi/lassi/server-0.0.1-SNAPSHOT.jar \
  -Dloader.main=lassi.tools.LedTestTool org.springframework.boot.loader.PropertiesLauncher
