VOTEZY WEB APPLICATION
======================

Application Description

-	Developed a secure, full-stack voting platform supporting user registration, vote casting, and real-time results. 
-	Designed a clean, maintainable backend with a layered architecture, leveraging Spring Boot and Spring Data JPA for API development and MySQL for data management. 
        Streamlined development using Maven and Lombok, and ensured robust API behaviour through testing in Postman. 
-	Designed a responsive, interactive frontend with HTML, CSS, JavaScript, Bootstrap, and dynamic communication via Fetch API and SweetAlert.

Voter Class/Entity 
==================

•	@Entity → Defines this as a database table.
•	@Id → Specifies the primary key.
•	@GeneratedValue(strategy = GenerationType.IDENTITY) → Auto-generates unique IDs.

         Strategy	Description
         IDENTITY	DB auto-increments (MySQL, PostgreSQL)
         SEQUENCE	uses a database sequence (best with Oracle, PostgreSQL)
         TABLE	uses a special table to keep track of IDs (less efficient)
         AUTO	JPA chooses the best strategy based on the DB
         
• @OneToOne(mappedBy = "voter", cascade = CascadeType.ALL) → Defines a one-to-one relationship with the Vote entity.Each voter can cast only one vote.
  A vote must belong to one voter.

       Owning side  - The side that has the foreign key column in the DB. It's responsible for persisting the relationship.
       Inverse side  - The side that uses mappedBy. It doesn't own the foreign key and just "reflects" the relationship.
       Parent -	The table that is referenced by a foreign key.
       Child - The table that contains the foreign key column.
 
       @JoinColumn(name = "voter_id", unique = true)   --- owning side 
       private Voter voter;
 
       Create a foreign key column named voter_id in the Vote table.
       The unique = true makes sure each voter_id in the vote is not reused, ensuring the one-to-one constraint.
       This annotation goes on the owning side, which is typically the side that has the foreign key.
       The field voter is a Java object reference, not a database column. JPA handles mapping under the hood.

•	@Data → Lombok annotations, which further include lombok annotations such as

        @Getter - Generates getters for all fields 
        @Setter	- Generates setters for all non-final fields
        @ToString	- Generates a toString() method
        @EqualsAndHashCode - Generates equals() and hashCode() methods
        @RequiredArgsConstructor - Generates a constructor for all final fields and @NonNull fields.

Candidate Class/Entity 
======================

Relationship - ManyToOne / OneToMany (Bidirectional) - “One Candidate can receive many Votes. But each Vote is associated with one Candidate.”

       vote owns the relationship  -- the owner and the candidate is the inverse side 
(Candidate Code)
@OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL) → Defines a one-to-many relationship with the Vote entity.
       
       One candidate can have multiple Votes.
       CascadeType.ALL ensures that any persistence operation (save, delete, etc.) on Candidate 
       will also be applied to its associated Votes automatically.

       @ManyToOne → goes on the child side.
       @OneToMany → goes on the parent side.
       
        (Vote Code)
        @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="candidate_id")
	@JsonIgnore
	private Candidate candidate;

LAZY FACTS

LAZY only works if you're still in the same Hibernate session.
Accessing it after the session is closed throws: LazyInitializationException
In REST APIs (like with Spring Boot), LAZY can fail silently unless you use DTOs or open sessions carefully.

LAZY loads data only when needed → better performance. Avoids N+1 problems and keeps queries lightweight.
EAGER loads everything immediately → risks slowness, memory bloat, and unnecessary DB hits. It should be used only when you're sure the related data is always needed.
For @ManyToOne or @OneToOne → it fetches immediately (EAGER) by default, even if you don’t use the data.
For @OneToMany and @ManyToMany → by default, it waits until accessed (LAZY).


REPOSITORY - We have used JpaRepository = Instant database access without writing SQL.
It’s a Spring Data JPA interface.
Extends CrudRepository and PagingAndSortingRepository.
Provides ready-made CRUD methods like: save(), findById(), findAll(), deleteById(), etc.

IMP - The @Repository annotation in Spring Data JPA is optional when using interfaces that extend JpaRepository or any other Spring Data interfaces like CrudRepository.

ELECTION-RESULT Class/Entity - Stores the final election result, including the winner and total votes cast.
======================
Relationship - Each election has exactly one winner. (one to one with candidate - linking the results to the wining candidate).

EXCEPTIONAL HANDLING
===================
 
1. Error Response -- for readable error responses in the form
2. Resources Not Found -- Handles cases where an entity (Voter, Candidate, Vote, ElectionResult) is not found.
3. Vote Not Allowed -- Handles cases where a resource already exists (e.g., registering a voter with the same email).

GLOBAL HANDLING OF EXCEPTION
============================

Controller-Level Exception Handling in Spring
Each controller (or globally via @ControllerAdvice) can define multiple @ExceptionHandler methods to handle specific exceptions. These methods return well-structured JSON error responses wrapped inside ResponseEntity, improving readability and client-side parsing.
Controller → Service → Repository → Database

1. Database Level (JDBC/JPA/Hibernate)

       If something fails at the database level (e.g., constraint violation, connection timeout), it throws low-level exceptions like: 
       SQLIntegrityConstraintViolationException
       JDBCException (Hibernate)
       DataIntegrityViolationException (Spring wrapper)
   
2. Repository Layer

       Spring Data JPA translates low-level exceptions to Spring's DataAccessException hierarchy (unchecked).
       These propagate automatically unless you catch them.
       You usually don’t catch them here, but let them bubble up to the service layer.
   
4. Service Layer

       This is where you usually catch repository exceptions.
       Translate them into custom exceptions (e.g., BookNotFoundException, DuplicateEntryException) that make more sense to the business logic.
       Throw your own custom exceptions based on business conditions (e.g., "User under 18 not allowed").

4. Controller Layer

       Let the custom exceptions bubble up here. Don’t clutter controllers with try-catch blocks.
       Instead, use @ControllerAdvice to handle exceptions and return user-friendly responses.

What About Validation Exceptions?
Request Validation (e.g., @Valid in Controller)
    
       Fails before hitting your service. Throws exceptions like: 
       MethodArgumentNotValidException (for @Valid on request bodies)
       ConstraintViolationException (for @Validated on query params/path variables)
       These are best handled in a global exception handler (@ControllerAdvice).

SERVICE - @service
==================
Marks a class as a service provider — typically holds business logic.
Registers the class as a Spring bean, eligible for component scanning.
Used at the service layer to separate concerns from the controller and repository.

        Note -- Implements business logic for ParticularEntity-related operations.
Why are DTOs not used in the service layer?
         
        The service layer is focused on business logic and domain models (entities), so it doesn’t need to concern itself with how the data is transferred 
        externally. The main goal here is to perform logic and interact with repositories, not to format or filter data for presentation.

Controller - @RestController 
===========================
Marks a class as a REST controller.
Combines @Controller + @ResponseBody, meaning: All methods return JSON or other REST responses, not views (like JSP).
Used at the web layer to expose API endpoints.

        Note -- Exposes REST API endpoints for managing ParticularEntity-related operations.

1. A @Controller in Spring is a class that handles incoming HTTP requests, processes them (often via a service), and returns a view or data response. It’s a part of the Spring MVC architecture, designed to separate concerns:

       Controller → handles routing logic
       Service → handles business logic
       Repository → handles data access

2. A @RestController is a specialized version of @Controller in Spring that automatically serializes responses to JSON or XML, without needing to use @ResponseBody on each method .It’s ideal for building RESTful web services. @RestController is used to build RESTful APIs in Spring, automatically converting returned objects into JSON responses, making it the go-to choice for modern client-server communication.

LET'S GO A LITTLE BACK IN TIME, SHALL WE?
========================================
Servlets handled HTTP requests using Java code, and JSPs (JavaServer Pages) embedded Java in HTML to generate dynamic web pages. They were replaced by Spring MVC with @Controller and @RestController for cleaner separation of concerns, better maintainability, and modern RESTful API support.


Why are DTOs required in the controller layer?
=============================================

        1. Protect Sensitive Data: DTOs ensure that no sensitive information from the entity (such as passwords or private details) is exposed to external 
        clients, safeguarding user privacy and system security.
        2. Efficient Data Transfer: DTOs allow for only the relevant data to be sent over the network, reducing the payload size and improving the performance of 
        your API by minimizing unnecessary data transfer.

Request DTO is used to receive data from the client, while Response DTO is used to send data to the client. Often include validation annotations and ensure that the incoming data is properly structured before it reaches the service layer.
Response DTOs are shaped to expose only the relevant, non-sensitive data to the client, ensuring a clean, controlled API response.

Purpose of the Mapper Classes:- Entity to DTO (response) and DTO to entity (request) mappings are handled in the Mapper, keeping your code clean and decoupled.
=============================
1. Entity to DTO Mapping (Response): When returning data to the client, you convert the entity into a Response DTO using the mapper. This way, the controller sends back only the necessary information to the client.

2. DTO to Entity Mapping (Request): When the client sends data to the server (for example, via a POST request), the Request DTO is converted to the entity so the service layer can perform the required operations (e.g., saving to the database).

ALTERNATIVE OF MAPPER CLASSES
=============================
MapStruct is a code generator that simplifies the mapping of Java beans (i.e., transferring data between DTOs and entities). It generates the mapping code at compile time, which means it avoids the runtime overhead that comes with reflection-based mapping frameworks.

@JsonIgnore
===========

@JsonIgnore is a Jackson annotation (from the com.fasterxml.jackson.annotation package) that tells Spring Boot:
"When converting this object to JSON, ignore this field."

	It hides a field from being included in JSON output (when sent from backend to frontend).
	It’s useful when you want to prevent certain data from being exposed in your REST APIs.

@JsonProperty
=============
serialization (Java object → JSON) and deserialization (JSON → Java object).
To customize the JSON response key names (voterId, candidateId) even though your actual fields are voter and candidate (which are object references).

By default, when you use Jackson to serialize this object to JSON without any @JsonProperty annotations, it would automatically use the field names from your Java class — i.e., the actual field names in your entity.

Let’s say you’re working with the Vote class as mentioned above.
Without @JsonProperty, Jackson would convert the Vote object into this kind of JSON structure whilw having the regular setter :

       {
         "voter": {
          "id": 3,
           "name": "Dishu Sharma",
           "email": "dishu@email.com"
          },
         "candidate": {
          "id": 5,
          "name": "Vedang Raina"
          }
       }
       
What you get without @JsonProperty:
The JSON field names directly reflect your Java object field names (voter, candidate).
So, the entire voter and candidate objects are serialized and shown, including all their properties.
PROBLEM  - Nested Objects, Infinitive Loops (If your objects reference each other).

BY USING @JsonProperty IN THE SETTERS 
     
     You’re telling Jackson: “Hey, don’t serialize the full voter object. Instead, just serialize the voterId (which is the id of the voter), and name it voterId  
     in the JSON response.”
     You’re also customizing the name that gets used in the JSON (voterId instead of voter).
     
IN A NUTSHELL
=============

Without @JsonProperty: Jackson serializes the entire object (e.g., voter), because that’s the default behavior when using getters.
With @JsonProperty: You customize the output field name (e.g., voterId), and also tell Jackson that you only want specific properties of an object (like just the id) serialized.

VOTE SERVICE
------------

When it works:
If cascade is enabled (like CascadeType.PERSIST or  CascadeType.ALL) and you've done voter.setVote(vote), then calling voterRepository.save(voter) saves both voter and its associated vote.

When it doesn't work:
If you don’t call voter.setVote(vote), even with cascade enabled, the voter object doesn’t "know" there’s a vote attached — so vote is not saved, and you'll need to call voteRepository.save(vote) manually. Extra call of Repository !

         Cascading Save via Proper Object Graph (linking)
         In JPA, cascade only kicks in if the object graph is properly wired. No wiring = no cascade magic.


ELECTION-RESULT 
===============

•	Determines and stores the election results.
•	Finds the candidate with the highest votes.
•	Handles edge cases like no candidates available.


