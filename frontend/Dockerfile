FROM node:18-alpine3.18 as build

ARG VITE_REDIRECT_URL=http://127.0.0.1
ARG GATEWAY_URL=http://localhost:8080

ENV VITE_LOGIN_URL=${GATEWAY_URL}/oauth2/authorize
ENV VITE_TOKEN_URL=${GATEWAY_URL}/oauth2/token
ENV VITE_USER_API_URL=${GATEWAY_URL}
ENV VITE_CUSTOMER_API_URL=$GATEWAY_URL
ENV VITE_REDIRECT_URL=$VITE_REDIRECT_URL

WORKDIR /app
COPY . /app

RUN npm i
RUN npm run build

FROM ubuntu
RUN apt-get update
RUN apt-get install nginx -y
COPY --from=build /app/dist /var/www/html/

COPY --from=build /app/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx","-g","daemon off;"]