FROM node:18-slim AS build

WORKDIR /app
COPY . .
RUN yarn
RUN yarn run build

FROM node:18-slim AS deploy-node

WORKDIR /app
COPY --from=build /app/package*.json .
COPY --from=build /app/build .
RUN yarn --prod
EXPOSE 3000
CMD ["node", "index.js"]