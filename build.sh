#! /bin/sh

cd frontend
docker build . -t checkers-frontend
cd ..

cd backend
docker build . -t checkers-backend
cd ..