# Requests API Documentation

This API covers the flow for making and managing requests on donations. The flow is now simplified:

- User selects payment method at the time of making the request.
- Donor's acceptance immediately sets status to `AWAITING_PICKUP` and generates OTP for the receiver.
- The separate "select payment" step and endpoint are removed.
- **Pickup code is only accessible to the receiver via a dedicated endpoint.**

---

## Endpoints

### 1. Create a Request for a Donation

- **POST** `/api/requests/create/{donationId}`
- **Auth:** Required (any user)
- **Body:**

For paid donations:

```json
{
  "paymentMethod": "CASH"
}
```

For free donations:

```json
{}
```

- **Allowed Values:** `CASH`, `UPI`, `CARD`, `NOT_APPLICABLE`
- **Note:** `paymentMethod` is not required for free donations. If omitted for a free donation, it will be set to `NOT_APPLICABLE` automatically.
- **Response Example:**

The response includes all relevant request fields, as well as the associated donation's properties:

- `foodType`: `PRECOOKED` or `RAW`
- `refrigerationAvailable`: boolean (only for PRECOOKED)
- `timer`: boolean
- `countdownTime`: number (seconds)
- `free`: boolean (whether the donation is free)
- `price`: number (price of the donation, if not free)
- `imageUrl`: string (optional Cloudinary link)

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
  "lastUpdateDate": "2024-06-10T12:00:00",
  "foodType": "PRECOOKED",
  "refrigerationAvailable": false,
  "timer": true,
  "countdownTime": 7200,
  "free": false,
  "price": 100.0,
  "imageUrl": "https://res.cloudinary.com/demo/image/upload/v1234567890/rice.jpg"
}
```

---

### 2. Accept a Request

- **POST** `/api/requests/{requestId}/accept`
- **Auth:** Required (donor)
- **Response:** `200 OK` — Updated request (status becomes `AWAITING_PICKUP`).
- **Note:** The pickup code is NOT returned to the donor. Only the receiver can fetch it using the endpoint below.

---

### 3. Get Pickup Code (Receiver Only)

- **GET** `/api/requests/{requestId}/pickup-code`
- **Auth:** Required (receiver)
- **Response Example:**

```json
{
  "pickupCode": "123456",
  "donorContactNumber": "+1234567890"
}
```

- **Note:** Only the receiver can access this endpoint. Donors and other users will receive an authorization error.

---

### 4. Confirm Pickup (OTP)

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

### 5. Reject a Request

- **POST** `/api/requests/{requestId}/reject`
- **Auth:** Required (donor)
- **Response:** `200 OK` — Updated request (status becomes `REJECTED`)

---

### 6. My Sent Requests

- **GET** `/api/requests/my-sent-requests`
- **Auth:** Required
- **Response:** `200 OK` — Array of requests made by the current user, each including the donation fields above.

---

### 7. My Received Requests

- **GET** `/api/requests/my-received-requests`
- **Auth:** Required
- **Response:** `200 OK` — Array of requests received for the current user's donations, each including the donation fields above.

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
- **Pickup code is only accessible to the receiver via the `/pickup-code` endpoint.**
- All other fields and flows remain the same.
- **All request responses now include the associated donation's foodType, refrigerationAvailable, timer, and countdownTime.**

---

For authentication and error handling, refer to the main API usage documentation.
