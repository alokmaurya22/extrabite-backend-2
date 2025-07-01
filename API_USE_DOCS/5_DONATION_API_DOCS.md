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

---

# Donation Request API Documentation

This API allows users to request a specific donation. The flow is now simplified:

- User selects payment method at the time of making the request.
- Donor's acceptance immediately sets status to `AWAITING_PICKUP` and generates OTP for the receiver.
- The separate "select payment" step and endpoint are removed.

---

## Endpoints

### 1. Create a Donation Request

- **POST** `/api/requests/create/{donationId}`
- **Auth:** Required (any user)
- **Body:**

```json
{
  "paymentMethod": "CASH"
}
```

- **Allowed Values:** `CASH`, `UPI`, `CARD`, `NOT_APPLICABLE`
- **Response Example:**

```json
{
  "id": 1,
  "donationId": 10,
  "foodName": "Rice",
  "receiverId": 2,
  "receiverName": "John Doe",
  "donorId": 1,
  "donorName": "Alice",
  "status": "PENDING",
  "paymentMethod": "CASH",
  "pickupCode": null,
  "requestDate": "2024-06-10T12:00:00",
  "lastUpdateDate": "2024-06-10T12:00:00"
}
```

---

### 2. Accept a Donation Request

- **POST** `/api/requests/{requestId}/accept`
- **Auth:** Required (donor)
- **Response:** `200 OK` — Updated request (status becomes `AWAITING_PICKUP`), OTP is generated and sent to the receiver.

---

### 3. Confirm Pickup (OTP)

- **POST** `/api/requests/{requestId}/confirm-pickup`
- **Auth:** Required (donor)
- **Body:**

```json
{
  "pickupCode": "123456"
}
```

- **Response:** `200 OK` — Updated request (status becomes `COMPLETED`)

---

### 4. Reject a Donation Request

- **POST** `/api/requests/{requestId}/reject`
- **Auth:** Required (donor)
- **Response:** `200 OK` — Updated request (status becomes `REJECTED`)

---

### 5. My Sent Requests

- **GET** `/api/requests/my-sent-requests`
- **Auth:** Required
- **Response:** `200 OK` — Array of requests made by the current user

---

### 6. My Received Requests

- **GET** `/api/requests/my-received-requests`
- **Auth:** Required
- **Response:** `200 OK` — Array of requests received for the current user's donations

---

## Request Status Values

- `PENDING`: Request is pending donor's action
- `AWAITING_PICKUP`: Donor accepted, OTP generated, waiting for handover
- `COMPLETED`: Request is fulfilled
- `REJECTED`: Request was rejected

---

## Notes

- **paymentMethod**: Must be selected at the time of request creation.
- The select-payment step is removed; the process is now simpler and faster.
- When the donor accepts, the status becomes `AWAITING_PICKUP` and OTP is generated for the receiver.
- All other fields and flows remain the same.

---

For authentication and error handling, refer to the main API usage documentation.
