FROM node:18 AS build

WORKDIR /app
COPY . .
RUN yarn
RUN yarn build

FROM node:18 AS deploy-node

WORKDIR /app
COPY --from=build /app/package*.json .
COPY --from=build /app/build .
RUN yarn --prod
EXPOSE 3000
CMD ["node", "index.js"]