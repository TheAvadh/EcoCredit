# Pickup Scheduling Database Design

## Data Relationships

- 1 user has N pickup
- 1 pickup has N waste
- 1 pickup has 1 status
- 1 waste has 1 category

## Tables

### pickup table

| attribute                | data type                        |
| ------------------------ | ---------------------------------|
| id                       | INT, PK                          |
| user_id                  | INT, FK to user table            |
| datetime                 | DATE                             |
| status_id                | INT, FK to status_lookup table   |

### waste table

| attribute                | data type                        |
| ------------------------ | ---------------------------------|
| id                       | INT, PK                          |
| category_id              | INT, FK to category_lookup table |
| pickup_id                | INT, FK to pickup table          |
| weight                   | FLOAT                            |


### category_lookup table

| attribute                | data type            |
| ------------------------ | -------------------- |
| id                       | INT, PK              |
| value                    | VARCHAR              |

#### values

| id                       |       value         |
| ------------------------ | --------------------|
| 1                        | ELECTRONICS         |
| 2                        | PAPER               |
| 3                        | BIODEGRADABLE       |
| 4                        | PLASTIC             |
| 5                        | GLASS               |
| 6                        | MIXED               |

### status_lookup table

| attribute                | data type            |
| ------------------------ | -------------------- |
| id                       | INT, PK              |
| value                    | VARCHAR              |

#### values

| id                       |       value         |
| ------------------------ | --------------------|
| 1                        | SCHEDULED           |
| 2                        | IN_PROGRESS         |
| 3                        | COMPLETED           |
| 4                        | CANCELED            |

## Notes

While creating a pickup, we are also inserting the waste

```
method insertPickup(pickup):
  var dbPickup = pickupRepository.save(pickup)

  for each waste in pickup:
    waste.pickup_id = dbPickup.id
    wasteRepository.save(waste)
```
