# Digiwallet Service
A digital wallet for recycling incentive scheme.

## Overview
### About recycling incentive scheme
Recycling incentive scheme provide rewards to users for actively participating in the program. Plastic recycling rates remain low and stagnant across the globe. Financial incentives designed to persuade households and waste producers to reuse and recycle more, helps prevent the generation of waste and can help contribute to financing waste management activities.

### How it will go:
1. Users create recycling pickup order
2. Couriers select pickup order from order list and attemp to pickup the recycling item
3. Couriers transports the recycling items to collection station and update the order status
4. when order status is 'delivered_to_destination', users can claim their rewards in the form of electronic money credit

### Scope of project
- user : individuals
- user role : customer, courier
- item categories : PET, non-PET, disposable-mask, electronic
- pickup time : anytime, specific-hour
- order status: order_created, picked_up_by_courier, failed_picked_up, reattemped_to_picked_up, delivered_to_destination

## Getting Started

### System Requirements
- Java 11
- Springboot
- Apache Kafka
- Redis
- Mongo DB
- Elastic Search

### Running the application
1. Make sure Kafka, Redis, Mongo DB are configure based on your environment and all up
2. Clone this repo and run the following command

````
mvn clean package
````
3. Run the DigiwalletApplication
````
mvn spring-boot:run
````

## API Operations
The following is a list of supported operations:
- save new user
- retrieve a user
- retrieve all user
- update a user
- delete a user

## Technologies
This project uses the following technologies:
- Spring Boot
- Java
- Maven
- Apache Kafka
- Redis
- Mongo DB
- Elastic Search
- Swagger

## Tools
This project was made using the following tools:
- Intellij IDEA
- Gitignore.io
- Postman
- SonarLint


