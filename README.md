# Shoe Store API
![Shoe Store Logo](img/../assets/images/ShoeStore.png)

## Description
Restful API for a shoe store. It contains a implementation of Shoes with the following operations:

- Index shoes.
- Show a shoe.
- Store a shoe.
- Update a shoe.
- Delete a shoe.

Spring Boot it's the most popular Java framework to build web applications and was chosen to this project cause of it's three core capabilities:

- Autoconfiguration.
- Opinionated approach to configuration.
- Ability to create standalone applications.


## Technologies used
- Java 17.
- Spring Boot.
- Spring Data JPA.
- Spring Web.
- Maven.
- Lombok.
- MongoDB database.


## To run the application

To run the application you have two options:

- Once you are on the project folder run the following commands:
```
./mvnw clean install
```
```
java -jar target/java-mongodb-api-1.0.jar
```
- Run directly throught the Spring Boot Dashboard on your IDE of choice.

## To run the the tests

```
./mvnw clean test
```

## Packages

```
├───.mvn
│	└───wrapper
├───assets
│	└───images
└───src
	├───main
	│	└───java
	│		├───com
	│		│	└───wattam
	│		│		├───controller
	│		│		│	└───exception
	│		│		├───dto
	│		│		├───model
	│		│		├───repository
	│		│		└───service
	│		│			└───impl
	│		└───resources
	└───test
		└───java
			├───com
			│	└───wattam
			│		├───controller
			│		└───service
			└───resources
```	

## Models

- ### `Shoe`

| Attribute |    Type    |
|:---------:|:----------:|
|     id    |   String   |
|    name   |   String   |
|   color   |   String   |
|   price   | BigDecimal |


## Shoe Requests

### `Index Shoes`

- `GET` `localhost:8080/shoes`: index shoes.
- Response example:

**`200 OK`**
```json
[
	{
		"id": "62192bc95352c472a8660c33",
		"name": "Nike SB",
		"color": "Black",
		"price": 150.00
	},
	{
		"id": "62192be05352c472a8660c34",
		"name": "Adidas Pictoris",
		"color": "White",
		"price": 200.00
	}
]
```

---
### `Show Shoe`

- `GET` `localhost:8080/shoes/{id}`: shows a shoe by the ID.
- Response example:

**`200 OK`**
```json
{
	"id": "62192bc95352c472a8660c33",
	"name": "Nike SB",
	"color": "Black",
	"price": 150.00
}
```

---
### `Store Shoe`

- `POST` `localhost:8080/shoes`: stores a shoe getting it's attributes throught the JSON body.
- Body example:
```json
{
	"name": "Nike SB",
	"color": "Black",
	"price": 150.00
}
```
- Response example:

**`201 Created`**
```json
{
	"id": "62192bc95352c472a8660c33",
	"name": "Nike SB",
	"color": "Black",
	"price": 150.00
}
```

---
### `Update Shoe`

- `PUT` `localhost:8080/shoes/{id}`: updates a shoe by the ID getting it's attributes throught the JSON body.
- Body example:
```json
{
	"name": "Nike SB",
	"color": "White",
	"price": 180.00
}
```
- Response example:

**`200 OK`**
```json
{
	"id": "62192bc95352c472a8660c33",
	"name": "Nike SB",
	"color": "White",
	"price": 180.00
}
```

---
### `Delete Shoe`

- `DELETE` `localhost:8080/shoes/{id}`: deletes a shoeby the ID.
- Response example: **`204 No Content`**
##