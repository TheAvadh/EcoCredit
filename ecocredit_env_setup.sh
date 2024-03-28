#!/usr/bin/env sh
export SERVER_PORT=8080
export DATASOURCE_URL_PROD=""
export DATASOURCE_USER_PROD=""
export DATASOURCE_PASSWORD=""
export EXPIRATION_DURATION_IN_MS=1440000
export TOKEN_SECRET_KEY=""
export REFRESH_TOKEN_VALIDITY_MS=60400000
export EMAIL_USERNAME=""
export EMAIL_PASSWORD=""
export EMAIL_HOST=""
export EMAIL_PORT=
export MAIL_DEBUG_PROD="false"
export FRONTEND_SERVER_URL=""
export STRIPE_PUBLIC_KEY=""
export STRIPE_SECRET_KEY=""
export BASE_URL_FRONTEND=""
export BASE_URL_BACKEND=""

# start mvnw spring-boot:run

echo "Environment variables set"
echo "You can now run the application from this window using 'mvnw spring-boot:run'"
echo "Note: If you close the window, the environment variables will be gone"
echo "Please run this file again if you want to set it again"