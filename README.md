# Timestamp Microservice

This repository is the first project from *freeCodeCamp* APIs and Microservices.  
   
## Endpoints  
  
##### /api/timestamp/
Returns a JSON object containing the current Datetime and UNIX timestamp.
```
http GET localhost:9999/api/timestamp/

{"datetime":"2020-03-21T12:57:30-03:00","unix":"1584806250"}

```

##### /api/timestamp/unixTimestamp
Returns a JSON object containing the converted Datetime and UNIX timestamp.  
```
http GET localhost:9999/api/timestamp/1584806250

{"datetime":"2020-03-21T12:57:30-03:00","unix":"1584806250"}

```

##### /api/timestamp/yyyy-MM-dd
Returns a JSON object containing the Datetime and converted UNIX timestamp.
```
http GET localhost:9999/api/timestamp/2020-03-21

{"datetime":"2020-03-21T00:00:00-03:00","unix":"1584759600"}
```


