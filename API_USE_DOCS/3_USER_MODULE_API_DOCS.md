# ExtraBite User Module API Documentation

This document provides detailed information about the User Profile and Admin Management APIs.

---

## User Profile Management

These endpoints are for managing individual user profiles.

### 1. Get Current User Profile

This endpoint retrieves the complete profile information for the currently authenticated user, including extended data like address and donation count.

- **URL:** `/api/user/profile`
- **Method:** `GET`
- **Authentication:** Required

#### Success Response (200 OK)

```json
{
  "id": 1,
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "contactNumber": "9876543210",
  "location": "Bangalore",
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
```

---

### 2. Update Current User Profile

This endpoint allows an authenticated user to update their own profile information.

- **URL:** `/api/user/update-profile`
- **Method:** `PUT`
- **Authentication:** Required

#### Request Body

You only need to send the fields you want to update. This can include basic info and extended profile data.

| Field               | Type      | Description                                            |
| ------------------- | --------- | ------------------------------------------------------ |
| `fullName`          | `String`  | The user's full name.                                  |
| `contactNumber`     | `String`  | The user's contact number.                             |
| `location`          | `String`  | The user's location.                                   |
| `profileActive`     | `Boolean` | Deactivate (`false`) or activate (`true`) the profile. |
| `address`           | `String`  | The user's full address.                               |
| `alternateContact`  | `String`  | An alternate contact number.                           |
| `displayPictureUrl` | `String`  | A URL for the user's profile picture.                  |

#### Success Response (200 OK)

The response will be the user's complete, updated profile object (same format as "Get Current User Profile").

---

## Admin Management (SUPER_ADMIN only)

The following endpoints are restricted to users with the `SUPER_ADMIN` role.

### 1. Create a New Admin

This endpoint allows a `SUPER_ADMIN` to create a new user with the `ADMIN` role and assign specific module permissions.

- **URL:** `/api/admin/create-admin`
- **Method:** `POST`
- **Authentication:** Required (Role: `SUPER_ADMIN`)

#### Request Body

| Field           | Type          | Description                                                           | Required |
| --------------- | ------------- | --------------------------------------------------------------------- | -------- |
| `fullName`      | `String`      | The new admin's full name.                                            | Yes      |
| `email`         | `String`      | A unique email for the new admin.                                     | Yes      |
| `password`      | `String`      | The new admin's password (min. 6 chars).                              | Yes      |
| `contactNumber` | `String`      | The new admin's contact number.                                       | No       |
| `location`      | `String`      | The new admin's location.                                             | No       |
| `modules`       | `Set<String>` | A set of modules to grant access to (e.g., `["donations", "users"]`). | No       |

#### Success Response (200 OK)

```json
{
  "id": 2,
  "fullName": "Jane Admin",
  "email": "jane.admin@example.com",
  "role": "ADMIN",
  "message": "Admin user created successfully."
}
```
