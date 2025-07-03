# ExtraBite Authentication API Documentation

This document provides detailed information about the Authentication and Welcome APIs for the ExtraBite application.

---

## Health Check

### 1. Welcome Endpoint.

A simple endpoint to check if the backend is running.

- **URL:** `/api/welcome`
- **Method:** `GET`
- **Authentication:** Not Required

#### Success Response (200 OK)

```json
{
  "message": "Welcome to ExtraBite! The backend is running.",
  "serverTime": "2023-10-21T14:30:00.123456"
}
```

---

## Authentication Endpoints

These endpoints handle user registration, login, and other authentication-related actions.

### 1. Register a New User

This endpoint allows a new user to register for an account.

- **URL:** `/api/auth/register`
- **Method:** `POST`
- **Authentication:** Not Required

#### Request Body

| Field                | Type     | Description                                   | Required |
| -------------------- | -------- | --------------------------------------------- | -------- |
| `fullName`           | `String` | The full name of the user.                    | Yes      |
| `email`              | `String` | A valid and unique email address.             | Yes      |
| `password`           | `String` | Password, must be at least 6 characters.      | Yes      |
| `contactNumber`      | `String` | User's contact phone number.                  | No       |
| `location`           | `String` | User's city or address.                       | No       |
| `role`               | `String` | The role of the user (`DONOR` or `RECEIVER`). | Yes      |
| `fssaiLicenseNumber` | `String` | FSSAI Lic. No. (optional, for donors only)    | No       |

#### Success Response (200 OK)

```json
{
  "id": 1,
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "contactNumber": "9876543210",
  "location": "Bangalore",
  "role": "DONOR",
  "fssaiLicenseNumber": "12345678901234",
  "message": "User registered successfully",
  "registrationDate": "2023-10-21T15:00:00",
  "profileActive": true,
  "accessToken": "ey..."
}
```

---

### 2. User Login

This endpoint allows a registered user to log in and receive an authentication token.

- **URL:** `/api/auth/login`
- **Method:** `POST`
- **Authentication:** Not Required

#### Request Body

| Field      | Type     | Description               | Required |
| ---------- | -------- | ------------------------- | -------- |
| `email`    | `String` | The user's email address. | Yes      |
| `password` | `String` | The user's password.      | Yes      |

#### Success Response (200 OK)

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIi...",
  "tokenType": "Bearer",
  "userId": 1,
  "role": "DONOR",
  "message": "Login successful"
}
```

---

### 3. Reset Password

This endpoint allows a user to reset their password after verifying their email and contact number.

- **URL:** `/api/auth/reset-password`
- **Method:** `POST`
- **Authentication:** Not Required

#### Request Body

| Field           | Type     | Description                           | Required |
| --------------- | -------- | ------------------------------------- | -------- |
| `email`         | `String` | The user's email address.             | Yes      |
| `contactNumber` | `String` | The user's registered contact number. | Yes      |
| `newPassword`   | `String` | The new password (min. 6 characters). | Yes      |

#### Success Response (200 OK)

```
"Password reset successful. You can now log in with your new password."
```

---

### 4. Logout

This endpoint invalidates the user's current authentication token, effectively logging them out.

- **URL:** `/api/auth/logout`
- **Method:** `POST`
- **Authentication:** Required

#### Headers

You must include the `Authorization` header with the Bearer token.

- `Authorization: Bearer <your-jwt-token>`

#### Success Response (200 OK)

```
"Token has been invalidated"
```
