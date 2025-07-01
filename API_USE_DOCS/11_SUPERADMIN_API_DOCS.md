# SuperAdmin API Documentation

This module provides endpoints for superadmins to register admins and manage (block/unblock) any user (admin, donor, or receiver) by user ID.

---

## Endpoints

### 1. Register Admin

- **POST** `/api/superadmin/register-admin`
- **Description:** Register a new admin. Admins are stored in the same user table as other users, with their role set to `ADMIN`.
- **Request Body Example:**

```json
{
  "fullName": "Admin User",
  "email": "admin@example.com",
  "password": "securePass123",
  "contactNumber": "+911234567890",
  "location": "Delhi",
  "role": "ADMIN"
}
```

- **Response Example:**

```json
{
  "id": 101,
  "fullName": "Admin User",
  "email": "admin@example.com",
  "contactNumber": "+911234567890",
  "location": "Delhi",
  "role": "ADMIN",
  "profileActive": true
}
```

### 2. Block User

- **POST** `/api/superadmin/block-user/{userId}`
- **Description:** Block any user (admin, donor, or receiver) by user ID. Sets `profileActive` to `false`.
- **Response Example:**

```json
"User blocked successfully"
```

### 3. Unblock User

- **POST** `/api/superadmin/unblock-user/{userId}`
- **Description:** Unblock any user by user ID. Sets `profileActive` to `true`.
- **Response Example:**

```json
"User unblocked successfully"
```

### 4. Get User by ID

- **GET** `/api/superadmin/user/{userId}`
- **Description:** Get user profile by user ID.
- **Response Example:**

```json
{
  "id": 101,
  "fullName": "Admin User",
  "email": "admin@example.com",
  "contactNumber": "+911234567890",
  "location": "Delhi",
  "role": "ADMIN",
  "profileActive": true
}
```

---

## Field Descriptions

- **fullName**: The full name of the user (required)
- **email**: A valid and unique email address (required)
- **password**: Password, at least 6 characters (required)
- **contactNumber**: User's contact phone number (optional)
- **location**: User's city or address (optional)
- **role**: Should be `ADMIN` for admin registration (required)
- **profileActive**: Indicates if the user is active (true) or blocked (false)

---

## Notes

- Only superadmins can access these endpoints.
- Blocking/unblocking works for any user, regardless of their role.
- Admins are managed in the same user table as all other users.

---

For authentication and error handling, refer to the main API usage documentation.
