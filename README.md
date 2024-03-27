# Eco Credit

## Developed by: Group 1

| Name                     | Email                |
| ------------------------ | -------------------- |
| Avadh Rakholiya          | av786964@dal.ca      |
| Bhavya Mukesh Dave       | bh392017@dal.ca      |
| Christin Saji            | christin.saji@dal.ca |
| Geerthana Kanagalingame  | gr964904@dal.ca      |
| Raisa Gandi Putri        | rgputri@dal.ca       |
| Ajaykumar Premkumar Nair | ajay.nair@dal.ca     |

## Technical Stack

- Frontend:
  - React 18.2.0
  - React Bootstrap 2.10.0
  - Bootstrap 5.3.2
- Backend:
  - Java 21
  - Spring Boot version 3.2.2
  - Maven

## Dependencies

### Frontend

| Dependency                          | Version   | Purpose          |
| ------------------------------------| --------- | ---------------- |
| @fortawesome/fontawesome-svg-core   | 6.5.1     | |
| @fortawesome/free-regular-svg-icons | 6.5.1     | |
| @fortawesome/free-solid-svg-icons   | 6.5.1     | |
| @fortawesome/react-fontawesome      | 0.2.0     | |
| @stripe/react-stripe-js             | 2.6.0     | |
| @testing-library/jest-dom           | 5.17.0    | |
| @testing-library/react              | 13.4.0    | |
| @testing-library/user-event         | 13.5.0    | |
| bootstrap                           | 5.3.2     | |
| has-flag                            | 5.0.1     | |
| js-cookie                           | 3.0.5     | |
| react                               | 18.2.0    | |
| react-bootstrap                     | 2.10.0    | |
| react-dom                           | 18.2.0    | |
| react-router-dom                    | 6.22.0    | |
| react-scripts                       | 5.0.1     | |
| react-toastify                      | 10.0.4    | |
| sass                                | 1.70.0    | |
| web-vitals                          | 2.1.4     | |

### Backend

| GroupID                          | ArtifactID                       | Version     | Scope    | Purpose       |
| ---------------------------------| -------------------------------  | ------------|----------|---------------|
| org.springframework.boot         | spring-boot-starter-data-jpa     |             |          | |
| org.springframework.boot         |spring-boot-starter-mail          |             |          | |
| org.springframework.boot         | spring-boot-starter-security     |             |          | |
| org.springframework.boot         | spring-security-config           | 6.2.1       |          | |
| org.springframework.boot         | spring-boot-starter-validation   |             |          | |
| org.springframework.boot         | spring-boot-starter-web          |             |          | |
| io.jsonwebtoken                  | jjwt-api                         | 0.11.5      |          | |
| io.jsonwebtoken                  | jjwt-impl                        | 0.11.5      |          | |
| io.jsonwebtoken                  | jjwt-jackson                     | 0.11.5      |          | |
| com.mysql                        | mysql-connector-j                |             | Runtime  | |
| org.projectlombok                | lombok                           |             |          | |
| org.springframework.boot         | spring-boot-starter-test         |             | Test     | |
| org.springframework.security     | spring-security-test             |             | Test     | |
| com.google.guava                 | guava                            | 33.0.0-jre  |          | |
| org.apache.commons               | commons-lang3                    |             |          | |
| junit                            | junit                            |             | Test     | |
| org.quartz-scheduler             | quartz                           | 2.3.2       |          | |
| com.stripe                       | stripe-java                      | 24.19.0     |          | |


## Build and Deploy Instructions

### Frontend

On terminal, go to the `frontend/ecocredit` directory.\
To build in `dev` environment, type `npm start`.

### Backend

Clean Build:
```
mvn clean package -DskipTests
```

Tests:
```
mvn test
```



## User Scenarios

### [Admin](https://git.cs.dal.ca/courses/2024-winter/csci5308/Group01/-/issues/16)

#### Part 1: Admin Dashboard

EcoCredit has one admin account. When an admin logs in, they are redirected to their dashboard, which allows them to do the following:

**View scheduled pickups**

This page shows all pickups that have been scheduled by users, sorted from the latest pickup.\

The pickup information contains:
- pickup date
- pickup time
- first and last name of user who scheduled the pickup
- list of waste items: ID, category, and weight (kg)

**View pickups in progress**

This page shows all pickups that are in progress, sorted from the latest pickup.

**View completed pickups**

This page shows all pickups that are completed, sorted from the latest pickup.

**Update the weight of a waste item in a pickup**

An admin can enter the waste item's ID and its weight in kilograms.\
After clicking on 'update', the weight of the item is reflected in the completed pickups table.

**Create a bid**

An admin can create a bid of a waste item. \
To do this, the admin needs to enter the waste item's ID and the date and time of the bid.

**View bids**

An admin can view all bids, both active and inactive. Each bid contains the following information:

- waste item's ID
- waste item's category
- waste item's weight (kg)
- base price ($)
- top bid amount ($)
- winner of the bid
- date and time of the bid
- bid status: active or inactive
- payment status: paid or not paid

**View active bids**

An admin can view all active bids.

#### Part 2: Recycler Dashboard

## References

| Topic                      | URL                                                                                       |
| -------------------------- | ----------------------------------------------------------------------------------------- |
| Forgot Password Flow       | https://supertokens.com/blog/implementing-a-forgot-password-flow                          |
| Forgot Password Psuedocode | https://stackoverflow.com/questions/1102781/best-way-for-a-forgot-password-implementation |
| React Bootstrap            | https://react-bootstrap.netlify.app/docs/getting-started/introduction                     |
| Bootstrap                  | https://getbootstrap.com/docs/5.3/getting-started/introduction/                           |
| Submit Form Data to API    | https://www.techomoro.com/submit-a-form-data-to-rest-api-in-a-react-app/                  |
| Display Duration           | https://momentjs.com/docs/#/durations/                                                    |
