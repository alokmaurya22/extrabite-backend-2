# ExtraBite User Directory API Documentation

This document provides detailed information about the API for searching and filtering users (Donors, Receivers, etc.).

---

## 1. Search for Users

This endpoint provides a powerful way to search for users based on specific criteria. By default, it only returns users with active profiles.

- **URL:** `/api/directory/users/search`
- **Method:** `GET`
- **Authentication:** Not Required (but you may choose to add it later)

#### Query Parameters (Filters)

You can add any of the following parameters to the URL. You can combine multiple filters.

| Parameter       | Type      | Description                                                             | Example                          |
| --------------- | --------- | ----------------------------------------------------------------------- | -------------------------------- |
| `role`          | `String`  | Filters by user role. Can be `DONOR`, `RECEIVER`, `ADMIN`, `VOLUNTEER`. | `.../search?role=DONOR`          |
| `location`      | `String`  | Searches for a partial match in the user's location (case-insensitive). | `.../search?location=bangalore`  |
| `profileActive` | `Boolean` | Overrides the default and can be set to `false` to find inactive users. | `.../search?profileActive=false` |

#### Example Usage

- **To find all active Donors in "Koramangala":**
  `/api/directory/users/search?role=DONOR&location=Koramangala`

- **To find all active Receivers:**
  `/api/directory/users/search?role=RECEIVER`

#### Success Response (200 OK)

The response will be an array of user profile objects that match the filter criteria.

```json
[
  {
    "id": 1,
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "contactNumber": "9876543210",
    "location": "Koramangala, Bangalore",
    "role": "DONOR",
    "registrationDate": "2023-10-21T15:00:00",
    "profileActive": true,
    "userData": {
      "address": "123 Main St, Koramangala",
      "alternateContact": "08012345678",
      "displayPictureUrl": "http://example.com/dp.jpg",
      "donationCount": 5,
      "rating": 4.5
    }
  }
]
```
