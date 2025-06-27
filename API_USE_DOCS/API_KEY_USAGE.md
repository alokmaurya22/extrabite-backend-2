# API Key Authentication Usage

All API requests must include a valid API key in the request headers. This helps secure the API and restricts access to authorized users only.

## How to Use

- Add the following header to every API request:

```
X-API-KEY: 2b7f8e1a-4c3e-4e2a-9c1a-7d8e6f5b2c3d
```
- FOR POSTMAN USE , ADD IN HEADER
- KEY = {X-API-KEY} , VALUE = {2b7f8e1a-4c3e-4e2a-9c1a-7d8e6f5b2c3d}

- Frontend developers should store this key in an environment variable and inject it into API requests.

## Example (cURL)

```
curl -H "X-API-KEY: 2b7f8e1a-4c3e-4e2a-9c1a-7d8e6f5b2c3d" https://your-api-url.com/endpoint
```

## Error Response

If the API key is missing or invalid, the API will respond with:

```
HTTP 401 Unauthorized
Invalid or missing API Key
```
