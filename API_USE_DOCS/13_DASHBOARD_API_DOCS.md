# ExtraBite Dashboard API Documentation

This document provides a comprehensive guide to all APIs needed for building user dashboards in the ExtraBite application. It consolidates user-specific analytics, reports, and management features.

---

## Authentication

All dashboard APIs require authentication. Include the JWT token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

Also include the API key header:

```
EXTRABITE-API-KEY: API_KEY_VALUE
```

---

## Dashboard Overview APIs

### 1. Get Current User Profile

Retrieve the complete profile information for the currently authenticated user.

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

### 2. Update User Profile

Update the current user's profile information.

- **URL:** `/api/user/update-profile`
- **Method:** `PUT`
- **Authentication:** Required

#### Request Body

```json
{
  "fullName": "John Doe Updated",
  "contactNumber": "9876543210",
  "location": "Bangalore",
  "profileActive": true,
  "address": "123 Main St, Koramangala",
  "alternateContact": "08012345678",
  "displayPictureUrl": "http://example.com/dp.jpg"
}
```

---

## User Analytics & Statistics

### 3. User Activity Summary

Get a comprehensive summary of the current user's activity.

- **URL:** `/api/analytics/user/summary`
- **Method:** `GET`
- **Authentication:** Required

#### Success Response (200 OK)

```json
{
  "myDonations": 5,
  "myFoodRequests": 2,
  "myRatingsGiven": 3,
  "myRatingsReceived": 4,
  "totalDonationsValue": 1500.0,
  "averageRating": 4.5,
  "activeRequests": 1,
  "completedTransactions": 8
}
```

---

### 4. User Rating Statistics

Get detailed rating statistics for the current user.

- **URL:** `/api/ratings/statistics`
- **Method:** `GET`
- **Authentication:** Required

#### Success Response (200 OK)

```json
{
  "userId": 456,
  "userName": "John Doe",
  "averageRating": 4.67,
  "totalRatings": 15,
  "fiveStarRatings": 8,
  "fourStarRatings": 5,
  "threeStarRatings": 2,
  "twoStarRatings": 0,
  "oneStarRatings": 0
}
```

---

### 5. User Donations Analytics

Get analytics for the user's donations over time.

- **URL:** `/api/analytics/user/donations`
- **Method:** `GET`
- **Authentication:** Required

#### Success Response (200 OK)

```json
[
  { "month": "2024-06", "donations": 2, "value": 400.0 },
  { "month": "2024-07", "donations": 3, "value": 600.0 },
  { "month": "2024-08", "donations": 1, "value": 200.0 }
]
```

---

### 6. User Food Requests Analytics

Get analytics for the user's food requests.

- **URL:** `/api/analytics/user/food-requests`
- **Method:** `GET`
- **Authentication:** Required

#### Success Response (200 OK)

```json
[
  { "month": "2024-06", "requests": 1, "status": "COMPLETED" },
  { "month": "2024-07", "requests": 1, "status": "OPEN" }
]
```

---

### 7. User Ratings Analytics

Get analytics for the user's ratings (given and received).

- **URL:** `/api/analytics/user/ratings`
- **Method:** `GET`
- **Authentication:** Required

#### Success Response (200 OK)

```json
[
  { "type": "given", "count": 3, "averageRating": 4.3 },
  { "type": "received", "count": 4, "averageRating": 4.7 }
]
```

---

## Donation Management

### 8. Get My Donations

Retrieve all donations made by the current user.

- **URL:** `/api/donations/my-donations`
- **Method:** `GET`
- **Authentication:** Required

#### Success Response (200 OK)

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
    "status": "AVAILABLE",
    "createdDateTime": "2023-10-21T10:00:00",
    "foodType": "PRECOOKED",
    "refrigerationAvailable": false,
    "timer": true,
    "countdownTime": 7200,
    "imageUrl": "https://res.cloudinary.com/demo/image/upload/v1234567890/pulao.jpg"
  }
]
```

---

### 9. Delete My Donation

Delete a specific donation created by the current user.

- **URL:** `/api/donations/delete-donation/{id}`
- **Method:** `DELETE`
- **Authentication:** Required

#### Success Response (204 No Content)

---

## Request Management

### 10. My Sent Requests

Get all requests made by the current user.

- **URL:** `/api/requests/my-sent-requests`
- **Method:** `GET`
- **Authentication:** Required

#### Success Response (200 OK)

```json
[
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
]
```

---

### 11. My Received Requests

Get all requests received for the current user's donations.

- **URL:** `/api/requests/my-received-requests`
- **Method:** `GET`
- **Authentication:** Required

#### Success Response (200 OK)

Same format as sent requests.

---

### 12. Accept Request

Accept a request for your donation.

- **URL:** `/api/requests/{requestId}/accept`
- **Method:** `POST`
- **Authentication:** Required (donor only)

#### Success Response (200 OK)

Returns updated request with status `AWAITING_PICKUP`.

---

### 13. Reject Request

Reject a request for your donation.

- **URL:** `/api/requests/{requestId}/reject`
- **Method:** `POST`
- **Authentication:** Required (donor only)

#### Success Response (200 OK)

Returns updated request with status `REJECTED`.

---

### 14. Confirm Pickup

Confirm pickup using OTP.

- **URL:** `/api/requests/{requestId}/confirm-pickup`
- **Method:** `POST`
- **Authentication:** Required (donor only)

#### Request Body

```json
{
  "pickupCode": "123456"
}
```

#### Success Response (200 OK)

Returns updated request with status `COMPLETED`.

---

## Food Request Management

### 15. My Food Requests

Get all food requests created by the current user.

- **URL:** `/api/food-requests/my`
- **Method:** `GET`
- **Authentication:** Required

#### Success Response (200 OK)

```json
[
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
]
```

---

### 16. Cancel Food Request

Cancel a food request created by the current user.

- **URL:** `/api/food-requests/{requestId}/cancel`
- **Method:** `POST`
- **Authentication:** Required (requester only)

#### Success Response (200 OK)

Returns updated request with status `CANCELLED`.

---

### 17. Accept Food Request Offer

Accept an offer for your food request.

- **URL:** `/api/food-requests/{requestId}/accept-offer`
- **Method:** `POST`
- **Authentication:** Required (requester only)

#### Success Response (200 OK)

Returns updated request with status `AWAITING_PICKUP`.

---

### 18. Verify OTP for Food Request

Verify OTP to complete a food request.

- **URL:** `/api/food-requests/{requestId}/verify-otp`
- **Method:** `POST`
- **Authentication:** Required (fulfiller only)

#### Request Body

```json
{
  "pickupCode": "123456"
}
```

#### Success Response (200 OK)

Returns updated request with status `COMPLETED`.

---

## Rating Management

### 19. Submit Rating

Submit a rating for a completed transaction.

- **URL:** `/api/ratings/submit`
- **Method:** `POST`
- **Authentication:** Required

#### Request Body

```json
{
  "donationRequestId": 123,
  "rating": 5,
  "comment": "Great experience! The food was fresh and delivery was on time."
}
```

#### Success Response (200 OK)

```json
{
  "id": 1,
  "donationRequestId": 123,
  "raterId": 456,
  "raterName": "John Doe",
  "ratedUserId": 789,
  "ratedUserName": "Jane Smith",
  "rating": 5,
  "comment": "Great experience! The food was fresh and delivery was on time.",
  "ratingType": "DONOR_TO_RECEIVER",
  "createdAt": "2023-12-01T14:30:00"
}
```

---

### 20. Get Ratings Given

Get all ratings given by the current user.

- **URL:** `/api/ratings/given`
- **Method:** `GET`
- **Authentication:** Required

#### Success Response (200 OK)

```json
[
  {
    "id": 1,
    "donationRequestId": 123,
    "raterId": 456,
    "raterName": "John Doe",
    "ratedUserId": 789,
    "ratedUserName": "Jane Smith",
    "rating": 5,
    "comment": "Great experience!",
    "ratingType": "DONOR_TO_RECEIVER",
    "createdAt": "2023-12-01T14:30:00"
  }
]
```

---

### 21. Get Ratings Received

Get all ratings received by the current user.

- **URL:** `/api/ratings/received`
- **Method:** `GET`
- **Authentication:** Required

#### Success Response (200 OK)

Same format as ratings given.

---

### 22. Check if Can Rate

Check if the current user can rate a specific donation request.

- **URL:** `/api/ratings/can-rate/{donationRequestId}`
- **Method:** `GET`
- **Authentication:** Required

#### Success Response (200 OK)

```json
{
  "canRate": true
}
```

---

## Browse & Search

### 23. Browse Available Donations

Browse all available donations (public endpoint).

- **URL:** `/api/browse/donations`
- **Method:** `GET`
- **Authentication:** Not Required

#### Success Response (200 OK)

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

### 24. Search Donations

Search donations with filters.

- **URL:** `/api/browse/donations/search`
- **Method:** `GET`
- **Authentication:** Not Required

#### Query Parameters

- `foodName`: Search by food name
- `location`: Search by location
- `isFree`: Filter by free/paid
- `status`: Filter by status

#### Example Usage

- `/api/browse/donations/search?foodName=pizza&location=bangalore`
- `/api/browse/donations/search?isFree=true`

---

### 25. Browse Open Food Requests

Browse all open food requests (public endpoint).

- **URL:** `/api/food-requests/open`
- **Method:** `GET`
- **Authentication:** Not Required

#### Success Response (200 OK)

Same format as "My Food Requests".

---

## User Directory

### 26. Search Users

Search for users in the directory.

- **URL:** `/api/directory/users/search`
- **Method:** `GET`
- **Authentication:** Not Required

#### Query Parameters

- `role`: Filter by role (DONOR, RECEIVER, ADMIN, VOLUNTEER)
- `location`: Search by location
- `profileActive`: Filter by active status

#### Example Usage

- `/api/directory/users/search?role=DONOR&location=bangalore`
- `/api/directory/users/search?role=RECEIVER`

---

## Global Statistics (Public)

### 27. Platform Summary

Get overall platform statistics (admin endpoint).

- **URL:** `/api/analytics/admin/summary`
- **Method:** `GET`
- **Authentication:** Required (Admin/Super Admin)

#### Success Response (200 OK)

```json
{
  "totalUsers": 1200,
  "totalDonations": 350,
  "totalFoodRequests": 80,
  "totalRatings": 500
}
```

---

### 28. Top Donors

Get list of top donors (public endpoint).

- **URL:** `/api/analytics/public/top-donors`
- **Method:** `GET`
- **Authentication:** Not Required

#### Query Parameters

- `location`: Filter by location
- `available`: Filter by availability

#### Success Response (200 OK)

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

## Hunger & Food Waste Statistics

### 29. Statistics Summary

Get comprehensive statistics summary for dashboard.

- **URL:** `/api/analytics/statistics/summary`
- **Method:** `GET`
- **Authentication:** Not Required

#### Query Parameters

- `region`: India or Global
- `year`: 2015-2025

#### Success Response (200 OK)

```json
{
  "region": "India",
  "year": 2024,
  "hunger": 220,
  "foodWaste": 79.0,
  "cagrHunger": 1.39,
  "cagrFoodWaste": 1.98,
  "dailyHungry": 602739,
  "peopleFed": 432054,
  "enoughFood": false,
  "shortBy": 170685,
  "foodWasteSourceBreakdown": {
    "Households": 48.19,
    "Food Services": 20.54,
    "Retail": 7.9,
    "Post-Harvest & Agriculture": 2.37
  },
  "foodWasteUnit": "Million Tonnes"
}
```

---

### 30. Daily Comparison

Get daily hunger vs food availability comparison.

- **URL:** `/api/analytics/statistics/daily-comparison`
- **Method:** `GET`
- **Authentication:** Not Required

#### Query Parameters

- `region`: India or Global
- `year`: 2015-2025

#### Success Response (200 OK)

```json
{
  "region": "India",
  "year": 2024,
  "dailyHungry": 602739,
  "peopleFed": 432054,
  "enoughFood": false,
  "shortBy": 170685
}
```

---

### 31. Food Waste Sources Breakdown

Get detailed food waste source breakdown.

- **URL:** `/api/analytics/statistics/food-waste-sources`
- **Method:** `GET`
- **Authentication:** Not Required

#### Query Parameters

- `region`: India or Global
- `year`: 2015-2025

#### Success Response (200 OK)

```json
{
  "region": "India",
  "year": 2024,
  "totalFoodWaste": 79.0,
  "unit": "Million Tonnes",
  "sourceBreakdown": {
    "Households": 48.19,
    "Food Services": 20.54,
    "Retail": 7.9,
    "Post-Harvest & Agriculture": 2.37
  }
}
```

---

### 32. Yearly Data

Get yearly hunger and food waste data.

- **URL:** `/api/analytics/statistics/yearly`
- **Method:** `GET`
- **Authentication:** Not Required

#### Query Parameters

- `dataType`: hunger, foodWaste, or both
- `region`: India or Global
- `startYear`: 2015-2025
- `endYear`: 2015-2025

#### Success Response (200 OK)

```json
[
  {
    "year": 2020,
    "hunger": 204,
    "foodWaste": 74.0,
    "foodWasteUnit": "Million Tonnes",
    "foodWasteBreakdown": {
      "Households": 45.14,
      "Food Services": 19.24,
      "Retail": 7.4,
      "Post-Harvest & Agriculture": 2.22
    }
  }
]
```

---

### 33. Growth Rate

Get CAGR (Compound Annual Growth Rate) for hunger or food waste.

- **URL:** `/api/analytics/statistics/growth-rate`
- **Method:** `GET`
- **Authentication:** Not Required

#### Query Parameters

- `type`: hunger or foodWaste
- `region`: India or Global

#### Success Response (200 OK)

```json
{
  "type": "hunger",
  "region": "India",
  "cagr": 1.39
}
```

---

### 34. Hunger vs Food Waste Bar Chart

Get data for bar chart visualization.

- **URL:** `/api/analytics/statistics/hunger-vs-foodwaste-bar`
- **Method:** `GET`
- **Authentication:** Not Required

#### Query Parameters

- `region`: India or Global
- `startYear`: 2015-2025
- `endYear`: 2015-2025

#### Success Response (200 OK)

```json
[
  { "year": 2015, "hunger": 190, "foodWaste": 65.0 },
  { "year": 2016, "hunger": 185, "foodWaste": 66.0 }
]
```

---

## Dashboard Widget Recommendations

### Essential Dashboard Widgets

1. **User Profile Card**
   - User info, rating, donation count
   - API: `/api/user/profile`

2. **Activity Summary Cards**
   - Total donations, requests, ratings
   - API: `/api/analytics/user/summary`

3. **Rating Statistics**
   - Average rating, star distribution
   - API: `/api/ratings/statistics`

4. **Recent Donations**
   - Latest donations with status
   - API: `/api/donations/my-donations`

5. **Recent Requests**
   - Sent and received requests
   - APIs: `/api/requests/my-sent-requests`, `/api/requests/my-received-requests`

6. **Food Requests**
   - User's food requests
   - API: `/api/food-requests/my`

7. **Global Impact Stats**
   - Hunger vs food waste data
   - API: `/api/analytics/statistics/summary`

8. **Top Donors**
   - Community leaders
   - API: `/api/analytics/public/top-donors`

9. **Available Donations**
   - Browse current donations
   - API: `/api/browse/donations`

10. **Analytics Charts**
    - Monthly trends, growth rates
    - APIs: `/api/analytics/user/donations`, `/api/analytics/statistics/yearly`

---

## Error Handling

All APIs follow standard HTTP status codes:

- **200 OK**: Success
- **201 Created**: Resource created
- **204 No Content**: Success, no content
- **400 Bad Request**: Invalid request
- **401 Unauthorized**: Authentication required
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server error

---

## Rate Limiting

- Most endpoints: 100 requests per minute per user
- Analytics endpoints: 50 requests per minute per user
- Statistics endpoints: 200 requests per minute per user

---

## Best Practices

1. **Caching**: Cache analytics and statistics data for 5-15 minutes
2. **Pagination**: Use pagination for large datasets
3. **Error Handling**: Always handle API errors gracefully
4. **Loading States**: Show loading indicators for async operations
5. **Real-time Updates**: Use WebSocket connections for live updates
6. **Offline Support**: Cache essential data for offline viewing

---

For detailed API specifications and additional endpoints, refer to the individual API documentation files. 