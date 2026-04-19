# StreetSmartBackend

Spring Boot REST API for the StreetSmart mobile and web clients.

## Purpose

This backend is responsible for:

- creating and authenticating users
- creating and reading hazard posts
- exposing a cursor-based feed for infinite scroll
- recording user votes on posts
- keeping post state normalized through separate severity, status, and vote-count history tables

## Running locally

The app expects these environment variables:

- `SUPABASE_DB_URL`
- `SUPABASE_DB_USER`
- `SUPABASE_DB_PASSWORD`

The simplest local setup is a project-root `.env` file containing those values.

Then run:

```bash
./gradlew bootRun
```

By default the API runs on:

```text
http://localhost:8080
```

## API Overview

### Auth routes

`POST /api/auth/signup`

Why it exists:
- creates a new user account
- hashes the password before saving
- returns a safe user response for the client

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

Response shape:

```json
{
  "message": "Signup successful.",
  "user": {
    "userId": 1,
    "username": "authuser1",
    "email": "authuser1@example.com",
    "phoneNumber": "5551112222",
    "firstName": "Auth",
    "lastName": "User",
    "avatar": null
  }
}
```

`POST /api/auth/login`

Why it exists:
- verifies email and password
- returns the same safe user payload shape used by the clients after login

Request body:

```json
{
  "email": "authuser1@example.com",
  "password": "secret123"
}
```

### User routes

`POST /api/users`

Why it exists:
- compatibility alias for signup while clients move to the dedicated auth controller

Request body:
- same as `POST /api/auth/signup`

`GET /api/users`

Why it exists:
- returns all users as safe response DTOs
- useful for admin/debug/client lookup flows

`GET /api/users/{userId}`

Why it exists:
- fetches one user by numeric id

`GET /api/users/username/{username}`

Why it exists:
- fetches one user by username

`GET /api/users/email/{email}`

Why it exists:
- fetches one user by email

User response shape:

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

Why it exists:
- creates a new post
- stores the core post row
- stores initial severity, status, and vote-count snapshots in their own history tables

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

Why it exists:
- returns all posts as assembled response DTOs
- can optionally include whether a specific viewer has liked each post

Optional query params:
- `viewerUserId`

Example:

```text
GET /api/posts?viewerUserId=1
```

`GET /api/posts/{postId}`

Why it exists:
- fetches one assembled post by id

Optional query params:
- `viewerUserId`

`GET /api/posts/status/{status}`

Why it exists:
- filters posts by status history

Optional query params:
- `viewerUserId`

`GET /api/posts/severity/{severity}`

Why it exists:
- filters posts by severity history

Optional query params:
- `viewerUserId`

Post response shape:

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
  "count": 3,
  "voteCountTime": "2026-04-18T18:10:00Z",
  "likedByUser": true,
  "user": {
    "userId": 1,
    "username": "authuser1",
    "firstName": "Auth",
    "lastName": "User",
    "avatar": null
  }
}
```

### Feed route

`GET /api/feed`

Why it exists:
- supports main-feed infinite scroll
- returns posts in descending recency order
- uses cursor pagination instead of offset pagination for scalability

Query params:

- `limit`
- `cursorPostTime`
- `cursorPostId`
- `viewerUserId`

Example first page:

```text
GET /api/feed?limit=20
```

Example next page:

```text
GET /api/feed?limit=20&cursorPostTime=2026-04-18T23:10:00Z&cursorPostId=245
```

Feed response shape:

```json
{
  "items": [
    {
      "postId": 245,
      "postCaption": "Flooded intersection",
      "postDescription": "Water covering both lanes near the light.",
      "postLocation": {
        "x": -84.51,
        "y": 39.10
      },
      "postImage": "550e8400-e29b-41d4-a716-446655440000",
      "postTime": "2026-04-18T23:10:00Z",
      "severity": "high",
      "severityTime": "2026-04-18T23:10:00Z",
      "status": "open",
      "statusTime": "2026-04-18T23:10:00Z",
      "count": 4,
      "voteCountTime": "2026-04-18T23:10:00Z",
      "likedByUser": false,
      "user": {
        "userId": 8,
        "username": "jane.doe",
        "firstName": "Jane",
        "lastName": "Doe",
        "avatar": null
      }
    }
  ],
  "nextCursor": {
    "postTime": "2026-04-18T22:41:17Z",
    "postId": 226
  }
}
```

### Vote route

`POST /api/posts/{postId}/votes`

Why it exists:
- records a like/unlike event for a user on a post
- writes the user vote event into vote history
- writes a new `post_vote_count` snapshot so feeds and post detail can read the latest count quickly

Request body:

```json
{
  "userId": 1,
  "voted": true,
  "voteTime": "2026-04-19T03:30:00Z"
}
```

Response shape:

```json
{
  "postId": 12,
  "userId": 1,
  "voted": true,
  "voteTime": "2026-04-19T03:30:00Z",
  "currentCount": 5
}
```

## Notes

- User passwords are stored as hashes, not raw text.
- `app_user` uses singular snake case naming to match the project convention.
- The backend currently uses Hibernate schema updates against Supabase Postgres.
- The main feed is intentionally newest-first only for now. Other feed sorts can be added later.
