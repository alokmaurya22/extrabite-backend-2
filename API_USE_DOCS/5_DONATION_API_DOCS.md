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

| Field                    | Type      | Description                                                          | Required |
| ------------------------ | --------- | -------------------------------------------------------------------- | -------- |
| `foodName`               | `String`  | Name of the food item.                                               | Yes      |
| `description`            | `String`  | A brief description of the food.                                     | Yes      |
| `quantity`               | `String`  | The quantity of the food (e.g., "2 plates").                         | Yes      |
| `expiryDateTime`         | `String`  | Expiry date & time in ISO format (`YYYY-MM-DDTHH:mm:ss`)             | Yes      |
| `isFree`                 | `boolean` | Set to `true` if the donation is free.                               | Yes      |
| `price`                  | `Double`  | The price of the food if it's not free.                              | No       |
| `location`               | `String`  | The pickup location address.                                         | Yes      |
| `geolocation`            | `String`  | GPS coordinates (e.g., "12.9716,77.5946").                           | No       |
| `deliveryType`           | `String`  | Delivery method: `SELF_PICKUP`, `DELIVERY_PARTNER`, `ANY`.           | No       |
| `foodType`               | `String`  | Type of food: `PRECOOKED` or `RAW`.                                  | Yes      |
| `refrigerationAvailable` | `boolean` | Only for `PRECOOKED` food. Is refrigeration facility available?      | No       |
| `imageUrl`               | `String`  | (Optional) Link to the image of the donation (e.g., Cloudinary URL). | No       |

#### Special Logic for PreCooked/Raw Food

- If `foodType` is `RAW`, the timer is disabled and `countdownTime` is set to 0.
- If `foodType` is `PRECOOKED`:
  - If `refrigerationAvailable` is true, timer is enabled and `countdownTime` is 4 hours (14400 seconds) from creation.
  - If `refrigerationAvailable` is false, timer is enabled and `countdownTime` is 2 hours (7200 seconds) from creation.
- The fields `timer` and `countdownTime` are set automatically by the backend.

#### Example Requests and Responses

**Case 1: RAW Food**

Request:

```json
{
  "foodName": "Raw Rice",
  "description": "Uncooked rice, 5kg bag",
  "quantity": "5kg",
  "expiryDateTime": "2025-10-25T18:00:00",
  "free": true,
  "price": 0.0,
  "location": "Koramangala, Bangalore",
  "geolocation": "12.9352,77.6245",
  "deliveryType": "SELF_PICKUP",
  "foodType": "RAW",
  "imageUrl": "https://res.cloudinary.com/demo/image/upload/v1234567890/rawrice.jpg"
}
```

Response:

```json
{
  "id": 2,
  "foodName": "Raw Rice",
  "description": "Uncooked rice, 5kg bag",
  "quantity": "5kg",
  "expiryDateTime": "2025-10-25T18:00:00",
  "isFree": true,
  "price": 0.0,
  "location": "Koramangala, Bangalore",
  "geolocation": "12.9352,77.6245",
  "deliveryType": "SELF_PICKUP",
  "status": "AVAILABLE",
  "createdDateTime": "2023-10-21T10:00:00",
  "donorId": 124,
  "donorName": "Jane Doe",
  "foodType": "RAW",
  "refrigerationAvailable": null,
  "timer": false,
  "countdownTime": 0,
  "imageUrl": "https://res.cloudinary.com/demo/image/upload/v1234567890/rawrice.jpg"
}
```

**Case 2: PRECOOKED Food with Refrigeration**

Request:

```json
{
  "foodName": "Paneer Curry",
  "description": "Homemade paneer curry, 2 boxes",
  "quantity": "2 boxes",
  "expiryDateTime": "2025-10-25T20:00:00",
  "free": false,
  "price": 100.0,
  "location": "Indiranagar, Bangalore",
  "geolocation": "12.9716,77.6412",
  "deliveryType": "SELF_PICKUP",
  "foodType": "PRECOOKED",
  "refrigerationAvailable": true,
  "imageUrl": "https://res.cloudinary.com/demo/image/upload/v1234567890/paneercurry.jpg"
}
```

Response:

```json
{
  "id": 3,
  "foodName": "Paneer Curry",
  "description": "Homemade paneer curry, 2 boxes",
  "quantity": "2 boxes",
  "expiryDateTime": "2025-10-25T20:00:00",
  "isFree": false,
  "price": 100.0,
  "location": "Indiranagar, Bangalore",
  "geolocation": "12.9716,77.6412",
  "deliveryType": "SELF_PICKUP",
  "status": "AVAILABLE",
  "createdDateTime": "2023-10-21T11:00:00",
  "donorId": 125,
  "donorName": "Ravi Kumar",
  "foodType": "PRECOOKED",
  "refrigerationAvailable": true,
  "timer": true,
  "countdownTime": 14400,
  "imageUrl": "https://res.cloudinary.com/demo/image/upload/v1234567890/paneercurry.jpg"
}
```

**Case 3: PRECOOKED Food without Refrigeration**

Request:

```json
{
  "foodName": "Vegetable Pulao",
  "description": "Freshly cooked with mixed vegetables",
  "quantity": "3 boxes",
  "expiryDateTime": "2025-10-25T18:00:00",
  "free": true,
  "price": 0.0,
  "location": "Jayanagar, Bangalore",
  "geolocation": "12.9293,77.5825",
  "deliveryType": "SELF_PICKUP",
  "foodType": "PRECOOKED",
  "refrigerationAvailable": false,
  "imageUrl": "https://res.cloudinary.com/demo/image/upload/v1234567890/pulao.jpg"
}
```

Response:

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
  "donorName": "John Doe",
  "foodType": "PRECOOKED",
  "refrigerationAvailable": false,
  "timer": true,
  "countdownTime": 7200,
  "imageUrl": "https://res.cloudinary.com/demo/image/upload/v1234567890/pulao.jpg"
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

## Automatic Rejection of PreCooked Donations

If a donation is of type `PRECOOKED`, the platform will automatically reject the donation (set status to `REJECTED`) if it is not claimed within the timer period (2 or 4 hours, depending on refrigeration). This is handled by the backend and does not require user action.

A public API is available for the platform to reject a donation by ID (no authentication required):

- **POST** `/api/donations/{id}/reject-by-platform`
- **Auth:** Not required
- **Description:** Rejects the donation if it is still available and the timer has expired.

---

For authentication and error handling, refer to the main API usage documentation.
