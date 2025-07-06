# ExtraBite Browse & Search API Documentation

This document provides detailed information about the public APIs for browsing and searching donations. These endpoints do not require authentication.

---

## 1. List All Available Donations

This endpoint retrieves a list of all donations that are currently available and not expired. This is the primary endpoint for displaying donations to users who are browsing the platform.

> **Note:** Only donations with status `AVAILABLE` are shown in this list.

- **URL:** `/api/browse/donations`
- **Method:** `GET`
- **Authentication:** Not Required for login

#### Success Response (200 OK)

The response will be an array of donation objects. The format is identical to the `DonationResponse` object and includes the following additional fields:

- `foodType`: `PRECOOKED` or `RAW`
- `refrigerationAvailable`: boolean (only for PRECOOKED)
- `timer`: boolean
- `countdownTime`: number (seconds)
- `imageUrl`: string (optional)

```json
[
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
]
```

---

## 2. Search Donations with Filters

This endpoint provides a powerful way to search for donations based on specific criteria. You can combine multiple filters.

- **URL:** `/api/browse/donations/search`
- **Method:** `GET`
- **Authentication:** Not Required

#### Query Parameters (Filters)

You can add any of the following parameters to the URL.

| Parameter  | Type      | Description                                                                                       | Example                     |
| ---------- | --------- | ------------------------------------------------------------------------------------------------- | --------------------------- |
| `foodName` | `String`  | Searches for a partial match in the food's name (case-insensitive).                               | `.../search?foodName=pizza` |
| `location` | `String`  | Searches for a partial match in the location (case-insensitive).                                  | `.../search?location=kora`  |
| `isFree`   | `Boolean` | Filters for donations that are either free (`true`) or not (`false`).                             | `.../search?isFree=true`    |
| `status`   | `String`  | Filters by donation status. Can be `AVAILABLE`, `CLAIMED`, or `EXPIRED`. Defaults to `AVAILABLE`. | `.../search?status=EXPIRED` |

> **Note:** If you do not specify the `status` parameter, only donations with status `AVAILABLE` will be shown by default.

#### Example Usage

- **To find all "pizza" donations in "jayanagar":**
  `/api/browse/donations/search?foodName=pizza&location=jayanagar`

- **To find all free donations:**
  `/api/browse/donations/search?isFree=true`

- **To find all expired donations:**
  `/api/browse/donations/search?status=EXPIRED`

#### Success Response (200 OK)

The response will be an array of donation objects that match the filter criteria. The format is the same as the primary listing endpoint and includes the new fields described above.

```json
[
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
]
```
