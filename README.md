# DroneService
Drone API Service
H2 in Memory was used for this implementation. Details can be found in application.properties
Some Unit Tests was implemented with Mokito.
However, API documentation is provided below for further API test and use.
Following lines below are the API URLs, Request Types, Request Payloads and Responses

1. Add Drone

URL: {IP}:{PORT}/api/add/drone
Request Type: POST

Request Payload
{
"battery":"50",
"model":"Lightweight",
"serialNumber":"adfswrtret89",
"state":"IDLE",
"weight":"50"
}

Response
{
    "statusCode": "00",
    "statusMessage": "Data submitted successfully"
}


2. Update Drone

URL: {IP}:{PORT}/api/update/drone
Request Type: POST

Request Payload
{
"battery":"50",
"model":"Lightweight",
"serialNumber":"adfswrtret89",
"state":"IDLE",
"weight":"70"
}

Response
{
    "statusCode": "00",
    "statusMessage": "Data updated successfully"
}


3. Add Medication

URL: {IP}:{PORT}/api/add/medication
Request Type: POST

Request Payload
{
"code":"afsg5etdg",
"name":"AsfdfFGSFG",
"image":"adfswrtret89",
"weight":"50"
}

Response
{
    "statusCode": "00",
    "statusMessage": "Data submitted successfully"
}


4. Update Medication

URL: {IP}:{PORT}/api/update/medication
Request Type: POST

Request Payload
{
"code":"afsg5etdg",
"name":"AsfdfFGSFG",
"image":"adfswrtret89",
"weight":"80"
}

Response
{
    "statusCode": "00",
    "statusMessage": "Data updated successfully"
}


5. List Drones

URL: {IP}:{PORT}/api/list/drone
Request Type: GET

Response
[
    {
        "id": 1,
        "serialNumber": "adfswrtret89",
        "model": "Lightweight",
        "weight": "50",
        "battery": "50",
        "state": "IDLE"
    }
]


6. List Medication

URL: {IP}:{PORT}/api/list/medication
Request Type: GET

Response
[
    {
        "id": 1,
        "code": "AFSG5ETDG",
        "name": "AsfdfFGSFG",
        "weight": "50",
        "image": "adfswrtret89"
    }
]


7. Find Drone By Serial Number

URL: {IP}:{PORT}/api/find/drone/byserialnumber/{serialnumber}
Request Type: GET

Response
{
    "id": 1,
    "serialNumber": "adfswrtret89",
    "model": "Lightweight",
    "weight": "50",
    "battery": "50",
    "state": "IDLE"
}


8. Find Medication By Code

URL: {IP}:{PORT}/api/find/medication/bycode/{code}
Request Type: GET

Response
{
    "id": 1,
    "code": "AFSG5ETDG",
    "name": "AsfdfFGSFG",
    "weight": "50",
    "image": "adfswrtret89"
}

9. Load Drone

URL: {IP}:{PORT}/api/loaddrone
Request Type: POST

Request Payload
{
"serialNumber":"adfswrtret89",
"medCode":"AFSG5ETDG"
}

Response
{
    "statusCode": "00",
    "statusMessage": "Data submitted successfully"
}


10. List Loaded Drones

URL: {IP}:{PORT}/api/list/loadeddrones
Request Type: GET

Response
[
    {
        "id": 1,
        "serialNumber": "adfswrtret89",
        "medCode": "AFSG5ETDG",
        "weight": "50"
    }
]


11. Find Available Drone(s)

URL: {IP}:{PORT}/api/checkavailable/drones
Request Type: GET

Response
[
    {
        "id": 1,
        "serialNumber": "adfswrtret89",
        "model": "Lightweight",
        "weight": "50",
        "battery": "60",
        "state": "LOADING"
    }
]


12. Find Loaded Drone BY Serial Number

URL: {IP}:{PORT}/api/find/loadeddrone/byserialnumber/{serialnumber}
Request Type: GET

Response
[
    {
        "id": 1,
        "serialNumber": "adfswrtret89",
        "medCode": "AFSG5ETDG",
        "weight": "50"
    },
    {
        "id": 2,
        "serialNumber": "adfswrtret89",
        "medCode": "AFSG5ETDG",
        "weight": "50"
    }
]


