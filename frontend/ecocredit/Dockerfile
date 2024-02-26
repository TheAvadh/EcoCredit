FROM node:lts-alpine as build

WORKDIR /app

COPY package*.json ./

RUN npm config set registry https://registry.npmjs.org/

RUN npm install

COPY . .

RUN npm run build

RUN npm install -g uglify-js

# uglify and minify js (uglify calls minitoolkit internally)
RUN find /app/build/static/js -name "*.js" -exec uglify {} -o {} \;

# using nginx alpine
FROM nginx:latest as prod

# copying html files to nginx directory
WORKDIR /usr/share/nginx/html

FROM nginx:latest as prod

COPY --from=build /app/build /usr/share/nginx/html

COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80/tcp

CMD ["/usr/sbin/nginx", "-g", "daemon off;"]

