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
**PosgreSQL** - SQL database
**JWT** - Authentication and authorization of REST APIs

# Application Structure

**Entity**
The various entities of the application are organized under the *entity* package. The entities are the persistence objects stores as a record in the database.

**Repository**
The various repositories of the application are present in the *repository* package. It is a data access layer for interaction with database to perform operations like update, insert, add, delete, read etc.

**Service**
