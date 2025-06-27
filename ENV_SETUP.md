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
```

## API Key Usage

The application uses the `EXTRABITE-API-KEY` header for API authentication. Make sure to include this header in your API requests:

```
EXTRABITE-API-KEY: your_extrabite_api_key_here
```

## Important Notes

- The `.env` file is ignored by git for security reasons
- Never commit your actual API keys or sensitive credentials to version control
- The `EXTRABITE-API-KEY` environment variable is loaded and used as the `api.key` property in the application
