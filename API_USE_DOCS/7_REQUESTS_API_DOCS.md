# ExtraBite Donation Request API Documentation

This document provides detailed information about the APIs for managing donation requests. All endpoints in this module require authentication.

---

## 1. Create a Donation Request

Allows an authenticated user to request a specific donation.

- **URL:** `/api/requests/create/{donationId}`
- **Method:** `POST`
- **Authentication:** Required

#### Success Response (200 OK)

Returns the newly created request object in `PENDING` status.

```json
{
  "id": 1,
  "donationId": 123,
  "foodName": "Vegetable Pulao",
  "receiverId": 45,
  "receiverName": "Jane Receiver",
  "donorId": 22,
  "donorName": "John Donor",
  "status": "PENDING",
  "paymentMethod": null,
  "requestDate": "2023-10-22T10:00:00",
  "lastUpdateDate": "2023-10-22T10:00:00"
}
```

---

## 2. Accept a Donation Request

Allows the donation's owner to accept a pending request for one of their donations.

- **URL:** `/api/requests/{requestId}/accept`
- **Method:** `POST`
- **Authentication:** Required

#### Success Response (200 OK)

- If donation is **free**, status becomes `AWAITING_PICKUP` and a pickup code is generated internally.
- If donation is **not free**, status becomes `ACCEPTED`, waiting for the receiver to select a payment method.

---

## 3. Reject a Donation Request

Allows the donation's owner to reject a pending request.

- **URL:** `/api/requests/{requestId}/reject`
- **Method:** `POST`
- **Authentication:** Required

#### Success Response (200 OK)

Returns the request object with status `REJECTED`.

---

## 4. Select Payment Method

Allows the user who made the request to select a payment method for a non-free donation that has been `ACCEPTED`. This step generates the pickup code.

- **URL:** `/api/requests/{requestId}/select-payment`
- **Method:** `POST`
- **Authentication:** Required

#### Request Body

```json
{
  "paymentMethod": "CASH"
}
```

_Possible values: `CASH`, `UPI`_

#### Success Response (200 OK)

Returns the request object, now in `AWAITING_PICKUP` status. The receiver is expected to be shown the pickup code.

---

## 5. Confirm Pickup (Verify OTP)

Allows the donation's owner to confirm that the donation has been picked up by verifying the `pickupCode` provided by the receiver. This completes the transaction.

- **URL:** `/api/requests/{requestId}/confirm-pickup`
- **Method:** `POST`
- **Authentication:** Required

#### Request Body

```json
{
  "pickupCode": "123456"
}
```

#### Success Response (200 OK)

Returns the request object with status `COMPLETED`. The original donation's status is also set to `CLAIMED`.

---

## 6. View My Sent Requests

Gets a list of all donation requests made by the currently logged-in user.

- **URL:** `/api/requests/my-sent-requests`
- **Method:** `GET`
- **Authentication:** Required

---

## 7. View My Received Requests

Gets a list of all requests received for donations made by the currently logged-in user.

- **URL:** `/api/requests/my-received-requests`
- **Method:** `GET`
- **Authentication:** Required
