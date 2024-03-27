# Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:21-al2023-jdk

# Define build arguments with default values
ARG DATASOURCE_URL_DEV=default_datasource_url
ARG DATASOURCE_USER_DEV=default_user
ARG DATASOURCE_PASSWORD=default_password
ARG EXPIRATION_DURATION_IN_MS=default_expiration_duration
ARG TOKEN_SECRET_KEY=default_token_secret_key
ARG REFRESH_TOKEN_VALIDITY_MS=default_refresh_token_validity
ARG EMAIL_USERNAME=default_email_username
ARG EMAIL_PASSWORD=default_email_password
ARG EMAIL_HOST=default_email_host
ARG EMAIL_PORT=default_email_port
ARG MAIL_DEBUG_DEV=default_mail_debug
ARG FRONTEND_SERVER_URL=default_frontend_server_url
ARG STRIPE_PUBLIC_KEY=default_stripe_public_key
ARG STRIPE_SECRET_KEY=default_stripe_secret_key
ARG BASE_URL_BACKEND=default_base_url_backend
ARG BASE_URL_FRONTEND=default_base_url_frontend

# Setting environment variables
ENV DATASOURCE_URL_DEV=$DATASOURCE_URL_DEV
ENV DATASOURCE_USER_DEV=$DATASOURCE_USER_DEV
ENV DATASOURCE_PASSWORD=$DATASOURCE_PASSWORD

ENV EXPIRATION_DURATION_IN_MS=$EXPIRATION_DURATION_IN_MS
ENV TOKEN_SECRET_KEY=$TOKEN_SECRET_KEY
ENV REFRESH_TOKEN_VALIDITY_MS=$REFRESH_TOKEN_VALIDITY_MS
ENV EMAIL_USERNAME=$EMAIL_USERNAME
ENV EMAIL_PASSWORD=$EMAIL_PASSWORD
ENV EMAIL_HOST=$EMAIL_HOST
ENV EMAIL_PORT=$EMAIL_PORT

ENV MAIL_DEBUG_DEV=$MAIL_DEBUG_DEV

# Prod URL
ENV FRONTEND_SERVER_URL=$FRONTEND_SERVER_URL


# Stripe
ENV STRIPE_PUBLIC_KEY=$STRIPE_PUBLIC_KEY
ENV STRIPE_SECRET_KEY=$STRIPE_SECRET_KEY


# URL
ENV BASE_URL_BACKEND=$BASE_URL_BACKEND
ENV BASE_URL_FRONTEND=$BASE_URL_FRONTEND


# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from your target directory into the container
COPY target/ecocredit-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port on which your Spring Boot application will run (adjust as needed)
EXPOSE 8080
EXPOSE 587

# Define the command to run your Spring Boot application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
