# Cache microservice

This microservice accepts a REST request and deposes the object from the rest to cache.
There are also `queue` and `delay` regulating the following

### delay
The `delay` parameter specifies the time amount for storing the object in cache. As this time 
expires the object is either deleted or sent to a queue. One can add other expiration actions
in configuration.

### queue
If the `queue` parameter is specified after delay is expired the object is sent to the consequent
message queue

## Detils of realization
The microservice uses Ignite In-Memory database. So there you can set any other actions
allowed by that framework. For example, persistent storage can be attached so the cache
to be kept after cluster failure.

## Usage

The specification for requests can be seen with the Swagger endpoint that is attached to the
home route: `http://localhost:8080/`.

There are ~~three~~  two requests supported up to now: `POST` stores the body of the request
to the cache, while `GET` extracts it back from the cache and returns in the response together
with some additional system information.

## Start

```bash
gradlew :cache:bootRun
```
