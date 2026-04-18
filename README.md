# StreetSmartBackend
REST API written with springboot and java for use via streetsmart mobile and web.

## API

### Auth routes

`POST /api/auth/signup`

Creates a new user, hashes the password, and returns a success message plus the created user.

Request body:

```json
{
  "username": "authuser1",
  "email": "authuser1@example.com",
  "phoneNumber": "5551112222",
  "firstName": "Auth",
  "lastName": "User",
  "password": "secret123"
}
```

`POST /api/auth/login`

Verifies email and password and returns a success message plus the user.

Request body:

```json
{
  "email": "authuser1@example.com",
  "password": "secret123"
}
```

### User routes

`POST /api/users`

Compatibility alias for signup. Creates a user from the same request shape as `/api/auth/signup`.

`GET /api/users`

Returns all users as safe response DTOs.

`GET /api/users/{userId}`

Returns one user by id.

`GET /api/users/username/{username}`

Returns one user by username.

`GET /api/users/email/{email}`

Returns one user by email.

User responses return:

```json
{
  "userId": 1,
  "username": "authuser1",
  "email": "authuser1@example.com",
  "phoneNumber": "5551112222",
  "firstName": "Auth",
  "lastName": "User",
  "avatar": null
}
```

### Post routes

`POST /api/posts`

Creates a post, links it to a real user, and writes the current severity, status, and vote count into their own history tables.

Request body:

```json
{
  "postCaption": "Road blocked",
  "postDescription": "Large fallen tree across both lanes.",
  "postLocation": {
    "x": -84.5123,
    "y": 39.1031
  },
  "postImage": "550e8400-e29b-41d4-a716-446655440000",
  "postTime": "2026-04-18T18:00:00Z",
  "severity": "high",
  "status": "open",
  "count": 0,
  "userId": 1
}
```

`GET /api/posts`

Returns all posts as response DTOs with author info and the latest severity, status, and count values.

`GET /api/posts/{postId}`

Returns one post by id.

`GET /api/posts/status/{status}`

Returns posts that match the given status in the status history table.

`GET /api/posts/severity/{severity}`

Returns posts that match the given severity in the severity history table.

Post responses return:

```json
{
  "postId": 12,
  "postCaption": "Road blocked",
  "postDescription": "Large fallen tree across both lanes.",
  "postLocation": {
    "x": -84.5123,
    "y": 39.1031
  },
  "postImage": "550e8400-e29b-41d4-a716-446655440000",
  "postTime": "2026-04-18T18:00:00Z",
  "severity": "high",
  "severityTime": "2026-04-18T18:00:00Z",
  "status": "open",
  "statusTime": "2026-04-18T18:00:00Z",
  "count": 0,
  "voteCountTime": "2026-04-18T18:00:00Z",
  "user": {
    "userId": 1,
    "username": "authuser1",
    "firstName": "Auth",
    "lastName": "User",
    "avatar": null
  }
}
```
