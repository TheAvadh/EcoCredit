set SERVER_PORT=8080
set DATASOURCE_URL_PROD=jdbc:mysql://db-5308.cs.dal.ca:3306/CSCI5308_1_PRODUCTION
set DATASOURCE_USER_PROD=CSCI5308_1_PRODUCTION_USER
set DATASOURCE_PASSWORD=OurohPhai2
set EXPIRATION_DURATION_IN_MS=1440000
set TOKEN_SECRET_KEY=e2315034876692352de9a59ef03e9f39582606f2a1acf1f1a792e9d85a77eb90
set REFRESH_TOKEN_VALIDITY_MS=60400000
set EMAIL_USERNAME=ecocredit.donotreply@gmail.com
set EMAIL_PASSWORD=xqbc icbw krzv wzfn
set EMAIL_HOST=smtp.gmail.com
set EMAIL_PORT=587
set MAIL_DEBUG_PROD=true
set FRONTEND_SERVER_URL=http://172.17.0.157
set STRIPE_PUBLIC_KEY="pk_test_51OsGIeEUEvEd0EPsVBil1tgf470uAGqBxEAwINM3tR45i4tAKFkEbaty3Od1LuJ8rSHTfAS6acgHNfHj0eyNOuvU00yVanNG2a"
set STRIPE_SECRET_KEY="sk_test_51OsGIeEUEvEd0EPsShQALmplqzXruh2YnGLnj6pP6kekOJQahpnzYeffE1kfrRFBWb11f6NXINGxcPYc2pLm7o5300ImMpSIdb"
set BASE_URL_FRONTEND="http://localhost:3000"
set BASE_URL_BACKEND="http://localhost:8080"


echo "Environment variables set"
echo "You can now run the application from this window using 'mvnw spring-boot:run'"
echo "Note: If you close the window, the environment variables will be gone"
echo "Please run this file again if you want to set it again"