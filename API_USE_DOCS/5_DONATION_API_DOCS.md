# ExtraBite Donation API Documentation

This document provides detailed information about the Donation APIs for the ExtraBite application.

## Authentication

Most donation-related APIs require authentication. To authenticate, you need to include an `Authorization` header with a JWT token in your request.

- **Header:** `Authorization: Bearer <your-jwt-token>`

You can get a JWT token by logging in via the `/api/auth/login` endpoint.

---

## API Endpoints

### 1. Create a New Donation

This endpoint allows a logged-in user to create a new donation.

- **URL:** `/api/donations`
- **Method:** `POST`
- **Authentication:** Required

#### Request Body

| Field            | Type      | Description                                                | Required |
| ---------------- | --------- | ---------------------------------------------------------- | -------- |
| `foodName`       | `String`  | Name of the food item.                                     | Yes      |
| `description`    | `String`  | A brief description of the food.                           | Yes      |
| `quantity`       | `String`  | The quantity of the food (e.g., "2 plates").               | Yes      |
| `expiryDateTime` | `String`  | Expiry date & time in ISO format (`YYYY-MM-DDTHH:mm:ss`)   | Yes      |
| `isFree`         | `boolean` | Set to `true` if the donation is free.                     | Yes      |
| `price`          | `Double`  | The price of the food if it's not free.                    | No       |
| `location`       | `String`  | The pickup location address.                               | Yes      |
| `geolocation`    | `String`  | GPS coordinates (e.g., "12.9716,77.5946").                 | No       |
| `deliveryType`   | `String`  | Delivery method: `SELF_PICKUP`, `DELIVERY_PARTNER`, `ANY`. | No       |

#### Example Request

```json
{
  "foodName": "Vegetable Pulao 2",
  "description": "Freshly cooked with mixed vegetables",
  "quantity": "3 boxes",
  "expiryDateTime": "2025-10-25T18:00:00",
  "free": true,
  "price": 0.0,
  "location": "Jayanagar, Bangalore",
  "geolocation": "12.9293,77.5825",
  "deliveryType": "SELF_PICKUP"
}
```

#### Success Response (201 CREATED)

```json
{
  "id": 1,
  "foodName": "Vegetable Pulao",
  "description": "Freshly cooked with mixed vegetables",
  "quantity": "3 boxes",
  "expiryDateTime": "2024-10-25T18:00:00",
  "isFree": true,
  "price": 0.0,
  "location": "Jayanagar, Bangalore",
  "geolocation": "12.9293,77.5825",
  "deliveryType": "SELF_PICKUP",
  "status": "AVAILABLE",
  "createdDateTime": "2023-10-21T10:00:00",
  "donorId": 123,
  "donorName": "John Doe"
}
```
---

### 2. Get All Donations (Admin)

This endpoint retrieves a list of all donations from all users. **Restricted to Admin/Super Admin roles.**

- **URL:** `/api/donations/all`
- **Method:** `GET`
- **Authentication:** Required (Role: `ADMIN` or `SUPER_ADMIN`)

#### Success Response (200 OK)

The response will be an array of all donation objects in the system.

---

### 3. Get a Specific Donation by ID

This endpoint retrieves the details of a single donation using its ID.

- **URL:** `/api/donations/{id}`
- **Method:** `GET`
- **Authentication:** Required

---

### 4. Get My Donations

This endpoint retrieves a list of all donations made by the currently logged-in user.

- **URL:** `/api/donations/my-donations`
- **Method:** `GET`

### 5. Delete a Donation

This endpoint allows a user to delete their own donation.

- **URL:** `/api/donations/delete-donation/{id}`
- **Method:** `DELETE`
- **Authentication:** Required (Only the donor, an ADMIN, or SUPER_ADMIN can delete)

#### Success Response (204 No Content)

A successful deletion will return an empty response with a `204 No Content` status code.
