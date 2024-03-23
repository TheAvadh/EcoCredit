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

### Backend

## Build and Deploy Instructions

## User Scenarios

### [Admin](https://git.cs.dal.ca/courses/2024-winter/csci5308/Group01/-/issues/16)

#### Part 1: Admin Dashboard

EcoCredit has one admin account. When an admin logs in, they are redirected to their dashboard, which allows them to do the following:

**View scheduled pickups**

This page shows all pickups that have been scheduled by users, sorted from the latest pickup. The pickup information contains:
- pickup date
- pickup time
- first and last name of user who scheduled the pickup
- list of waste items: ID, category, and weight (kg)

**View pickups in progress**

This page shows all pickups that are in progress, sorted from the latest pickup.

**View completed pickups**

This page shows all pickups that are completed, sorted from the latest pickup.

**Update the weight of a waste item in a pickup**

An admin can enter the waste item's ID and its weight in kilograms. After clicking on 'update', the weight of the item is reflected in the completed pickups table.

**Create a bid**

An admin can create a bid of a waste item. To do this, the admin needs to enter the waste item's ID and the date and time of the bid.

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
| Submit Form Data to API    | https://www.techomoro.com/submit-a-form-data-to-rest-api-in-a-react-app/                  |