# Food Request API Documentation

This API allows any user to request food or offer to fulfill food requests. There are no role restrictions—anyone can create, view, offer, accept, complete, or cancel food requests.

---

## Flow Improvements

- User selects payment method at the time of making the request.
- Fulfiller's acceptance immediately sets status to `AWAITING_PICKUP` and generates OTP for the requester.
- The separate "select payment" step and endpoint are removed.
- **Pickup code is only accessible to the requester via a dedicated endpoint.**

---

## Endpoints

### 1. Create a Food Request

- **POST** `/api/food-requests/create`
- **Auth:** Required (any user)
- **Body:**

```json
{
  "foodType": "Rice",
  "alternativeFood": "Chapati, Bread",
  "numberOfPeople": 4,
  "message": "If you have any other food, let me know. I can pick up nearby.",
  "offerPrice": 0,
  "requestExpiryTime": "2024-06-10T18:00:00",
  "requestedTime": "2024-06-10T19:00:00",
  "location": "123 Main St, New Delhi",
  "geolocation": "28.6139,77.2090",
  "foodDescription": "Vegetarian only, no peanuts. Prefer home-cooked.",
  "contactNumber": "+911234567890",
  "paymentMethod": "CASH"
}
```

- **Allowed Values:** `CASH`, `UPI`, `CARD`, `NOT_APPLICABLE`
- **Response Example:**

```json
{
  "id": 1,
  "requesterId": 2,
  "requesterName": "John Doe",
  "foodType": "Rice",
  "alternativeFood": "Chapati, Bread",
  "numberOfPeople": 4,
  "message": "If you have any other food, let me know. I can pick up nearby.",
  "offerPrice": 0,
  "status": "OPEN",
  "createdAt": "2024-06-10T12:00:00",
  "updatedAt": "2024-06-10T12:00:00",
  "paymentMethod": "CASH",
  "pickupCode": null,
  "requestExpiryTime": "2024-06-10T18:00:00",
  "requestedTime": "2024-06-10T19:00:00",
  "location": "123 Main St, New Delhi",
  "geolocation": "28.6139,77.2090",
  "foodDescription": "Vegetarian only, no peanuts. Prefer home-cooked.",
  "contactNumber": "+911234567890",
  "submittedAt": "2024-06-10T11:59:00"
}
```

---

### 2. List All Open Food Requests

- **GET** `/api/food-requests/open`
- **Auth:** Not required (public)
- **Response:** `200 OK` — Array of food requests (see above response format)

---

### 3. List My Food Requests

- **GET** `/api/food-requests/my`
- **Auth:** Required
- **Response:** `200 OK` — Array of food requests created by the current user

---

### 4. Offer to Fulfill a Food Request

- **POST** `/api/food-requests/{requestId}/offer`
- **Auth:** Required
- **Path Param:** `requestId` — ID of the food request
- **Response:** `200 OK` — Updated food request (status becomes `OFFERED`)

---

### 5. Accept an Offer

- **POST** `/api/food-requests/{requestId}/accept-offer`
- **Auth:** Required (must be the requester)
- **Path Param:** `requestId` — ID of the food request
- **Response:** `200 OK` — Updated food request (status becomes `AWAITING_PICKUP`).
- **Note:** The pickup code is NOT returned to the fulfiller. Only the requester can fetch it using the endpoint below.

---

### 6. Get Pickup Code (Requester Only)

- **GET** `/api/food-requests/{requestId}/pickup-code`
- **Auth:** Required (requester)
- **Path Param:** `requestId` — ID of the food request
- **Response Example:**

```json
"123456"
```

- **Note:** Only the requester can access this endpoint. Fulfillers and other users will receive an authorization error.

---

### 7. Verify OTP and Complete Request

- **POST** `/api/food-requests/{requestId}/verify-otp`
- **Auth:** Required (fulfiller)
- **Path Param:** `requestId` — ID of the food request
- **Body Example:**

```json
{
  "pickupCode": "123456"
}
```

- **Response:** `200 OK` — Updated food request (status becomes `COMPLETED`)

---

### 8. Complete a Food Request (Legacy)

- **POST** `/api/food-requests/{requestId}/complete`
- **Auth:** Required
- **Path Param:** `requestId` — ID of the food request
- **Response:** `200 OK` — Updated food request (status becomes `COMPLETED`)

---

### 9. Cancel a Food Request

- **POST** `/api/food-requests/{requestId}/cancel`
- **Auth:** Required (must be the requester)
- **Path Param:** `requestId` — ID of the food request
- **Response:** `200 OK` — Updated food request (status becomes `CANCELLED`)

---

## Food Request Status Values

- `OPEN`: Request is open and visible to all
- `OFFERED`: Someone has offered to fulfill the request
- `AWAITING_PICKUP`: Fulfiller accepted, OTP generated, waiting for handover
- `COMPLETED`: Request is fulfilled
- `CANCELLED`: Request was cancelled
- `CLOSED`: Request expired or manually closed; no more offers can be made

---

## Notes & Field Descriptions

- **foodType**: Main food needed (e.g., "Rice").
- **alternativeFood**: Acceptable alternatives (e.g., "Chapati, Bread").
- **numberOfPeople**: Number of people to feed.
- **message**: Any extra message for the donor (e.g., "If you have any other food, let me know.").
- **offerPrice**: Amount offered (can be 0 for free).
- **requestExpiryTime**: The last date/time the request can be accepted. No offers can be made after this time.
- **requestedTime**: When the food is needed (date/time).
- **location**: Address or location details for delivery/pickup.
- **geolocation**: Latitude,Longitude string for map use.
- **foodDescription**: More details about the food needed (e.g., dietary restrictions).
- **contactNumber**: For urgent contact or coordination.
- **paymentMethod**: Must be selected at the time of request creation.
- **pickupCode**: 6-digit OTP for verification at handover.
- **submittedAt**: When the request was submitted.

- Any user can create or fulfill requests—there are no role restrictions.
- The API is designed to be simple and user-friendly.
- When the fulfiller accepts, the status becomes `AWAITING_PICKUP` and OTP is generated for the requester.
- **Pickup code is only accessible to the requester via the `/pickup-code` endpoint.**

---

For authentication and error handling, refer to the main API usage documentation.
