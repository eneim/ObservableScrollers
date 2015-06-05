#!/usr/bin/env bash
./gradlew clean build bintrayUpload -PbintrayUser=eneim -PbintrayKey=cef14d70fb409027df3b5b3d4e11e5e268a48229 -PdryRun=false