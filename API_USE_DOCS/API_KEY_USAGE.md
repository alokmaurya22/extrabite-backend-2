# API Key Authentication Usage

All API requests must include a valid API key in the request headers. This helps secure the API and restricts access to authorized users only.

## How to Use

- Add the following header to every API request:

```
X-API-KEY: API_KEY_VALUE
```

- FOR POSTMAN USE , ADD IN HEADER
- KEY = {X-API-KEY} , VALUE = {API_KEY_VALUE}

- Frontend developers should store this key in an environment variable and inject it into API requests.

## Example (cURL)

```
curl -H "X-API-KEY: API_KEY_VALUE" https://your-api-url.com/endpoint
```

## Error Response

If the API key is missing or invalid, the API will respond with:

```
HTTP 401 Unauthorized
Invalid or missing API Key
```
