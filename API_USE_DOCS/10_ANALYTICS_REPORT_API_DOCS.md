# Analytics & Report API Documentation

This module provides analytics and reporting endpoints for both admin and user perspectives.

---

## Endpoints

### Admin Endpoints

#### 1. Platform Summary

- **GET** `/api/analytics/admin/summary`
- **Description:** Returns overall platform statistics.
- **Response Example:**

```json
{
  "totalUsers": 1200,
  "totalDonations": 350,
  "totalFoodRequests": 80,
  "totalRatings": 500
}
```

#### 2. Donations Report

- **GET** `/api/analytics/admin/donations`
- **Description:** Returns analytics for all donations (e.g., per month, by status, by type).
- **Response Example:**

```json
[
  { "month": "2024-06", "donations": 40 },
  { "month": "2024-07", "donations": 55 }
]
```

#### 3. Users Report

- **GET** `/api/analytics/admin/users`
- **Description:** Returns analytics for all users (e.g., top donors, most active users).
- **Response Example:**

```json
[
  { "userId": 1, "name": "Alice", "donations": 20, "ratings": 15 },
  { "userId": 2, "name": "Bob", "donations": 15, "ratings": 10 }
]
```

#### 4. Requested Donations Report

- **GET** `/api/analytics/admin/requested-donations`
- **Description:** Returns a report of all requested donations (who requested, status, etc.).
- **Response Example:**

```json
[
  {
    "donationId": 1,
    "foodName": "Rice",
    "requestedBy": "user@example.com",
    "status": "REQUESTED"
  }
]
```

---

### Public Endpoint

#### Top Donors (with Filters)

- **GET** `/api/analytics/public/top-donors`
- **Query Params:**
  - `location` (optional): Filter by location
  - `available` (optional, boolean): Filter by availability
- **Description:** Returns a list of top donors, optionally filtered by location and availability.
- **Response Example:**

```json
[
  {
    "userId": 1,
    "name": "Alice",
    "donations": 20,
    "location": "Delhi",
    "available": true
  }
]
```

---

### User Endpoints

#### 1. My Summary

- **GET** `/api/analytics/user/summary`
- **Description:** Returns a summary of the current user's activity.
- **Response Example:**

```json
{
  "myDonations": 5,
  "myFoodRequests": 2,
  "myRatingsGiven": 3,
  "myRatingsReceived": 4
}
```

#### 2. My Donations Report

- **GET** `/api/analytics/user/donations`
- **Description:** Returns analytics for the user's donations (e.g., by month, by status).
- **Response Example:**

```json
[
  { "month": "2024-06", "donations": 2 },
  { "month": "2024-07", "donations": 3 }
]
```

#### 3. My Ratings Report

- **GET** `/api/analytics/user/ratings`
- **Description:** Returns analytics for the user's ratings (given and received).
- **Response Example:**

```json
[
  { "type": "given", "count": 3 },
  { "type": "received", "count": 4 }
]
```

#### 4. My Food Requests Report

- **GET** `/api/analytics/user/food-requests`
- **Description:** Returns analytics for the user's food requests (e.g., by month, by status).
- **Response Example:**

```json
[
  { "month": "2024-06", "requests": 1 },
  { "month": "2024-07", "requests": 1 }
]
```

---

## Notes

- All endpoints (except public) require authentication.
- Admin endpoints are accessible only to users with admin privileges.
- User endpoints return data for the currently authenticated user.
- Response fields and structure may be extended as analytics features grow.

---

For authentication and error handling, refer to the main API usage documentation.
