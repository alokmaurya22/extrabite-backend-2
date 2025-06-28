# Environment Setup

This project uses environment variables loaded from a `.env` file. Create a `.env` file in the root directory with the following variables:

## Required Environment Variables

```env
# Database Configuration
DB_URL=your_database_url_here
DB_USERNAME=your_database_username_here
DB_PASSWORD=your_database_password_here

# Google OAuth Configuration
GOOGLE_CLIENT_ID=your_google_client_id_here
GOOGLE_CLIENT_SECRET=your_google_client_secret_here

# Server Configuration
SERVER_PORT=8080

# API Key Configuration
EXTRABITE-API-KEY=your_extrabite_api_key_here

# JWT Configuration
JWT_SECRET=your_jwt_secret_here
```

## API Key Usage

The application uses the `EXTRABITE-API-KEY` header for API authentication. Make sure to include this header in your API requests:

```
EXTRABITE-API-KEY: your_extrabite_api_key_here
```

## JWT Secret Configuration

The application uses the `JWT_SECRET` environment variable for JWT token signing and validation. This should be a secure, randomly generated base64-encoded string.

**Important:**

- The JWT secret should be at least 256 bits (32 bytes) when base64 decoded
- Never share or commit your JWT secret to version control
- Use a different JWT secret for each environment (development, staging, production)

## Important Notes

- The `.env` file is ignored by git for security reasons
- Never commit your actual API keys or sensitive credentials to version control
- The `EXTRABITE-API-KEY` environment variable is loaded and used as the `api.key` property in the application
- The `JWT_SECRET` environment variable is loaded and used as the `jwt.secret` property in the application
