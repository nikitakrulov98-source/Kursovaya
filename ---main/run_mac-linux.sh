#!/bin/sh
echo "🛠️  Quick build and run..."
mvn clean compile exec:java -Dexec.mainClass=Main -DskipTests