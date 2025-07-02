# ExtraBite Rating Module API Documentation

This document provides detailed information about the Rating Module APIs that allow donors and receivers to rate each other after completing a donation.

---

## Overview

The Rating Module enables users to provide feedback and ratings for each other after a donation request is completed. This helps build trust and transparency in the community.

### Key Features:

- **Bidirectional Rating**: Both donors and receivers can rate each other
- **Rating Validation**: Only completed donation requests can be rated
- **Duplicate Prevention**: Users can only rate once per donation request
- **Automatic Statistics**: User average ratings are automatically calculated
- **Rating Breakdown**: Detailed statistics including star distribution

---

## Authentication

All rating endpoints require authentication. Include the JWT token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

---

## API Endpoints

### 1. Submit Rating

Submit a rating for a completed donation request.

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

| Field               | Type      | Required | Description                           | Validation                  |
| ------------------- | --------- | -------- | ------------------------------------- | --------------------------- |
| `donationRequestId` | `Long`    | Yes      | ID of the completed donation request  | Must exist and be completed |
| `rating`            | `Integer` | Yes      | Rating value (1-5 stars)              | Min: 1, Max: 5              |
| `comment`           | `String`  | No       | Optional comment about the experience | Max: 500 characters         |

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

#### Error Responses

**400 Bad Request - Invalid Rating**

```json
{
  "error": "Rating must be at least 1"
}
```

**400 Bad Request - Donation Request Not Found**

```json
{
  "error": "Donation request not found"
}
```

**400 Bad Request - Not Completed**

```json
{
  "error": "Can only rate completed donation requests"
}
```

**400 Bad Request - Unauthorized**

```json
{
  "error": "You can only rate donation requests you participated in"
}
```

**400 Bad Request - Already Rated**

```json
{
  "error": "You have already rated this donation request"
}
```

---

### 2. Get Ratings Given by Current User

Retrieve all ratings that the current user has given to others.

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
  },
  {
    "id": 2,
    "donationRequestId": 124,
    "raterId": 456,
    "raterName": "John Doe",
    "ratedUserId": 790,
    "ratedUserName": "Bob Wilson",
    "rating": 4,
    "comment": "Good communication",
    "ratingType": "RECEIVER_TO_DONOR",
    "createdAt": "2023-12-02T10:15:00"
  }
]
```

---

### 3. Get Ratings Received by Current User

Retrieve all ratings that the current user has received from others.

- **URL:** `/api/ratings/received`
- **Method:** `GET`
- **Authentication:** Required

#### Success Response (200 OK)

```json
[
  {
    "id": 3,
    "donationRequestId": 125,
    "raterId": 791,
    "raterName": "Alice Brown",
    "ratedUserId": 456,
    "ratedUserName": "John Doe",
    "rating": 5,
    "comment": "Excellent donor!",
    "ratingType": "RECEIVER_TO_DONOR",
    "createdAt": "2023-12-03T16:45:00"
  }
]
```

---

### 4. Get User Rating Summary

Get detailed rating statistics for a specific user.

- **URL:** `/api/ratings/summary/{userId}`
- **Method:** `GET`
- **Authentication:** Required

#### Path Parameters

| Parameter | Type   | Description           |
| --------- | ------ | --------------------- |
| `userId`  | `Long` | ID of the target user |

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

#### Error Response (404 Not Found)

```json
{
  "error": "User not found"
}
```

---

### 5. Get Current User's Rating Statistics

Get detailed rating statistics for the currently authenticated user.

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

### 6. Get Ratings for Donation Request

Get all ratings associated with a specific donation request.

- **URL:** `/api/ratings/donation-request/{donationRequestId}`
- **Method:** `GET`
- **Authentication:** Required

#### Path Parameters

| Parameter           | Type   | Description                |
| ------------------- | ------ | -------------------------- |
| `donationRequestId` | `Long` | ID of the donation request |

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
  },
  {
    "id": 2,
    "donationRequestId": 123,
    "raterId": 789,
    "raterName": "Jane Smith",
    "ratedUserId": 456,
    "ratedUserName": "John Doe",
    "rating": 4,
    "comment": "Good donor",
    "ratingType": "RECEIVER_TO_DONOR",
    "createdAt": "2023-12-01T15:00:00"
  }
]
```

---

### 7. Check if User Can Rate Donation Request

Check whether the current user can submit a rating for a specific donation request.

- **URL:** `/api/ratings/can-rate/{donationRequestId}`
- **Method:** `GET`
- **Authentication:** Required

#### Path Parameters

| Parameter           | Type   | Description                |
| ------------------- | ------ | -------------------------- |
| `donationRequestId` | `Long` | ID of the donation request |

#### Success Response (200 OK)

```json
{
  "canRate": true
}
```

**Possible reasons for `canRate: false`:**

- Donation request is not completed
- User did not participate in the donation request
- User has already rated this donation request
- Donation request does not exist

---

## Data Models

### RatingRequest

```json
{
  "donationRequestId": 123,
  "rating": 5,
  "comment": "Great experience!"
}
```

### RatingResponse

```json
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
```

### UserRatingSummary

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

## Rating Types

The system supports two types of ratings:

| Rating Type         | Description                       |
| ------------------- | --------------------------------- |
| `DONOR_TO_RECEIVER` | Rating given by donor to receiver |
| `RECEIVER_TO_DONOR` | Rating given by receiver to donor |

---

## Business Rules

### Rating Eligibility

1. **Only completed donation requests** can be rated
2. **Only participants** (donor or receiver) can rate the donation request
3. **One rating per user** per donation request
4. **Rating values** must be between 1-5 stars

### Rating Calculation

1. **Average rating** is automatically calculated and updated
2. **Rating statistics** include breakdown by star ratings
3. **User profile** shows the current average rating

### Data Integrity

1. **Transaction management** ensures data consistency
2. **Validation** prevents invalid ratings
3. **Duplicate prevention** maintains rating integrity

---

## Error Handling

### Common Error Codes

| Status Code | Description           | Common Causes                                     |
| ----------- | --------------------- | ------------------------------------------------- |
| 400         | Bad Request           | Invalid rating value, already rated, not eligible |
| 401         | Unauthorized          | Missing or invalid JWT token                      |
| 404         | Not Found             | User or donation request not found                |
| 500         | Internal Server Error | Database or system error                          |

### Error Response Format

```json
{
  "error": "Error message description",
  "timestamp": "2023-12-01T14:30:00",
  "path": "/api/ratings/submit"
}
```

---

## Testing

### Test Cases

1. **Valid Rating Submission**

   - Submit rating for completed donation request
   - Verify rating is saved and user average is updated

2. **Invalid Rating Values**

   - Try to submit rating < 1 or > 5
   - Verify validation error is returned

3. **Duplicate Rating Prevention**

   - Submit rating for same donation request twice
   - Verify second submission is rejected

4. **Unauthorized Rating**

   - Try to rate donation request user didn't participate in
   - Verify access is denied

5. **Incomplete Donation Request**
   - Try to rate donation request that's not completed
   - Verify rating is rejected

### Sample Test Data

```json
{
  "donationRequestId": 123,
  "rating": 5,
  "comment": "Test rating for documentation"
}
```

---

## Security Considerations

1. **Authentication Required**: All endpoints require valid JWT token
2. **Authorization**: Users can only rate donation requests they participated in
3. **Input Validation**: Rating values and comments are validated
4. **SQL Injection Prevention**: Using JPA repositories with parameterized queries
5. **Rate Limiting**: Consider implementing rate limiting for rating submissions

---

## Performance Considerations

1. **Database Indexing**: Ensure proper indexes on rating tables
2. **Caching**: Consider caching user rating summaries
3. **Batch Updates**: Rating statistics are updated efficiently
4. **Pagination**: For large rating lists, consider implementing pagination

---

## Future Enhancements

1. **Rating Categories**: Different rating criteria (communication, food quality, etc.)
2. **Rating Moderation**: Admin approval for ratings
3. **Rating Analytics**: Advanced analytics and reporting
4. **Rating Notifications**: Email/SMS notifications for new ratings
5. **Rating Disputes**: System for handling rating disputes

---

## Support

For technical support or questions about the Rating Module API, please contact the development team or refer to the main API documentation.
