#!/usr/bin/env bash
java \
  -Dname=button-test-tool \
  -cp /home/pi/lassi/server-0.0.1-SNAPSHOT.jar \
  -Dloader.main=lassi.tools.ButtonTestTool org.springframework.boot.loader.PropertiesLauncher
