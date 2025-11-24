@echo off
echo 🔨 Building and running Avatar Generator...
mvn clean package exec:java -Dexec.mainClass=Main
pause