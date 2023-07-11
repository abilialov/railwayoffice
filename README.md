Project name RAILWAYOFFICE.

The task of the project is to develop a web application using the Spring framework that implements the
functionality according to the system description:

The administrator can add / delete / edit the list of Stations and Railway Routes between
them. The route contains information as:
- starting station and departure time;
- final station and time of arrival.
The user can search for routes between stations that interest him. The result of the search is a
list of trains, each string of which contains:
- train number;
- time / date and station of departure;
- travel time;
- time / date and station of arrival;
- number of available seats;
- travel cost;
- link to the corresponding route view page (user can view route information).
If the user is registered in the system, he must be able to buy a ticket for the selected route
on the specified date (subject to availability).



Requirements for implementation
1. Create classes that correspond to essence of the subject area.
2. Classes and methods should have names that reflect their functionality and should be spaced in
packages.
3. The code design must comply with the Java Code Convention.
4. Store subject area information in a relational database (it is recommended to use MySQL or
PostgreSQL as a database).
5. The application must support the use of Cyrillic (be multilingual), including the storage of
information in the database:
a. it must be possible to switch the interface language.
b. there should be support for input/output and storage of information (in the database)
recorded in different languages.
c. choose at least two languages: one Native, the other English.
d. dates must be implemented through the java.time.
6. Implement protection against re-sending data to the server when refreshing the page (implement
PRG).
7. Authentication and authorization must be implemented in the application, delimitation of access
rights of system users to program components. Password encryption is recommended.
8. Introduce an event log into the project using the log4j library.
9. The code must contain comments on the documentation (all top-level classes, non-trivial methods
and constructors).
10. The application should be covered by modular tests (minimum coverage percentage 40%). Writing
integration tests is recommended. The use of Mockito is recommended.
11. Implement the mechanism of data pages pagination.
12. All input fields must be with data validation.
13. The application must respond correctly to errors and exceptions of various kinds (the end user
should not see the stack trace on the client side).
14. Independent task functionality expansion is recommended! (Adding captchas, generating reports
in various formats, etc.)
15. The use of HTML, CSS, JS frameworks for the user interface (Bootstrap, Materialize, etc.) is
recommended!
16. Development of projects with the help of Git is required.
17. The application must be structured according to the MVC and Spring Boot architectures.
18. Project Lombok use is allowed.
19. The use of the Spring Resource Bundle is recommended.
20. Spring Authorization must be used.
21. JPA must be used for data access (Spring Data and/or Hibernate).
22. Handling exceptions with help of Exception Handling with Spring for REST API is recommended.
23. Use of ThymeLeaf is recommended.
24. Use of additional frameworks (Swager etc.) is recommended.
