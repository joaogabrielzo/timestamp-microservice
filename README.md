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
## Run With Docker

To run with docker, you should first build the image.  
  
Inside the project's folder, run in terminal:
```
docker build . -t timestamp-microservice
```  
  
After the image is done building, run in terminal:
```
docker run --network=host timestamp-microservice
```  
  
You can then access the API via `localhost:9999/api/timestamp/`