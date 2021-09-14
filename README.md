
## Weather Tracker Springboot Application

This application is to create Back-End APIs for a Weather Tracking application using OpenWeatherMap API. 

#### Prerequisites

* Visit https://openweathermap.org/appid
* SignUp and generate API keys

#### Installation and Getting Started

* Download/Clone the project on to your local machine.
* Update the <API-KEY-HERE> in application.yml resources folder 
* Run WeathertrackerApplication class from IDE or mvn spring-boot:run


#### Available API endpoints

##### User Registration
POST - http://localhost:8080/weather/user/register
```json
{
    "name" : "xxxxx",
    "email" : "xxxxx.yyyyy@gmail.com"
}
```
##### Create Weather Profile
POST - http://localhost:8080/weather/profile/create
```json
{
    "profileName" : "Regional",
    "userId" : 1,
    "cities" : ["Sydney","Perth","Brisbane"]
}
```
##### Update Weather Profile Name
PUT - http://localhost:8080/weather/profile/update/id/{profileId}/name/{newProfileName}

##### Add a new City to Weather Profile
PUT - http://localhost:8080/weather/profile/add/id/{profileId}/city/{cityName}

##### Remove a City from Weather Profile
PUT - http://localhost:8080/weather/profile/delete/id/{profileId}/city/{cityName}

##### Delete Weather Profile
DELETE - http://localhost:8080/weather/profile/delete/id/{profileId}

##### List all Weather Profiles for a User
GET - http://localhost:8080/weather/profile/user/{userId}

##### Get a Weather Profile by Id for a User
GET - http://localhost:8080/weather/profile/user/{userId}/id/{profileId}

##### Get Current Weather data for all available cities
GET - http://localhost:8080/weather/report/all
