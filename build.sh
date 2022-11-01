#!/bin/sh

echo "building frontend"
cd frontend
npm run build

cd ..
echo "building backend"
cd backend
./gradlew jar

cd ..
echo "DONE"
