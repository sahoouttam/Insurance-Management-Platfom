# Insurance-Management-Platfom
Developed a web service that allows users to add, retrieve, update and delete clients' insurance records. It also securely stores and manages insurance-related information for clients.

# Features
The following features of Insurance management platform are:
1. User signup
2. User signin
3. Add/update a client's record
4. Retrieve clients record
5. Delete client record
6. Fetch insurance details and their respective claims
7. Add/Update insurances and claims
8. Delete insurance along with their claims

# Technology

**Spring Boot** - Server side framework\
**PosgreSQL** - SQL database\
**JWT** - Authentication and authorization of REST APIs\
**Gradle** - Build automation tool\
**Git** - Version control

# Application Structure

**Entity**\
The various entities of the application are organized under the *entity* package. The entities are the persistence objects stores as a record in the database.

**Repository**\
The various repositories of the application are present in the *repository* package. It is a data access layer for interaction with database to perform operations like update, insert, add, delete, read etc. 

**Security**\
The security setting is present under the *security* package for implementing application-level security that offer you a highly customizable way of implementing authentication and authorization. For the REST APIs, JWT token based authentication mechanism is used.

**Service**\
The service layer is defined under the *service* package that allows to add business functionalities. ClientService, InsurancePolicy and ClaimPolicy are implemented to satisfy different business operations.

**Controller**\
The last and important layer is the controller layer which is present in the *controller* package. Rest Controller allows to handle all REST APIS such GET, POST, PUT, DELETE requests.


# Running The Application Locally
To run the Spring Boot applicaion from a command line in a terminal window, java -jar command can be used. This is provided your application was packaged as an executable jar file.
> java -jar target/filename.jar

To run the application, run the following command in a terminal window:
> gradle bootRun
