# jpa_entity_graph
This project demonstrates the EntityGraph Feature of JPA2.1
The data is accessible via REST Endpoints.

## Endpoints
### /ressources/project/error
This Endpoint demonstrates the problems with the lazyloading of entities.
It will always fail because the tickets of a project were not loaded in the transaction the project was loaded.

## /ressources/project/lazy[/{projectId}]
This Endpoint loads projects, all their tickets and all the worklogs of each ticket.
In order to initialize everything with the FetchType.LAZY the code calls the getter of these Entities.
This will result in a Query N+1 Problem.

### Database Queries
The Request `GET /ressources/project/lazy/1` will result in following Queries

    Hibernate: select project0_.id as id1_0_0_, project0_.description as descript2_0_0_, project0_.name as name3_0_0_ from projects project0_ where project0_.id=?
    Hibernate: select tickets0_.Project_id as Project_1_1_0_, tickets0_.tickets_id as tickets_2_1_0_, ticket1_.id as id1_2_1_, ticket1_.description as descript2_2_1_, ticket1_.title as title3_2_1_ from projects_tickets tickets0_ inner join tickets ticket1_ on tickets0_.tickets_id=ticket1_.id where tickets0_.Project_id=?
    Hibernate: select worklogs0_.Ticket_id as Ticket_i1_3_0_, worklogs0_.worklogs_id as worklogs2_3_0_, worklog1_.id as id1_4_1_, worklog1_.duration as duration2_4_1_ from tickets_worklogs worklogs0_ inner join worklogs worklog1_ on worklogs0_.worklogs_id=worklog1_.id where worklogs0_.Ticket_id=?
    Hibernate: select worklogs0_.Ticket_id as Ticket_i1_3_0_, worklogs0_.worklogs_id as worklogs2_3_0_, worklog1_.id as id1_4_1_, worklog1_.duration as duration2_4_1_ from tickets_worklogs worklogs0_ inner join worklogs worklog1_ on worklogs0_.worklogs_id=worklog1_.id where worklogs0_.Ticket_id=?
    Hibernate: select worklogs0_.Ticket_id as Ticket_i1_3_0_, worklogs0_.worklogs_id as worklogs2_3_0_, worklog1_.id as id1_4_1_, worklog1_.duration as duration2_4_1_ from tickets_worklogs worklogs0_ inner join worklogs worklog1_ on worklogs0_.worklogs_id=worklog1_.id where worklogs0_.Ticket_id=?
    Hibernate: select worklogs0_.Ticket_id as Ticket_i1_3_0_, worklogs0_.worklogs_id as worklogs2_3_0_, worklog1_.id as id1_4_1_, worklog1_.duration as duration2_4_1_ from tickets_worklogs worklogs0_ inner join worklogs worklog1_ on worklogs0_.worklogs_id=worklog1_.id where worklogs0_.Ticket_id=?
    Hibernate: select worklogs0_.Ticket_id as Ticket_i1_3_0_, worklogs0_.worklogs_id as worklogs2_3_0_, worklog1_.id as id1_4_1_, worklog1_.duration as duration2_4_1_ from tickets_worklogs worklogs0_ inner join worklogs worklog1_ on worklogs0_.worklogs_id=worklog1_.id where worklogs0_.Ticket_id=?
    Hibernate: select worklogs0_.Ticket_id as Ticket_i1_3_0_, worklogs0_.worklogs_id as worklogs2_3_0_, worklog1_.id as id1_4_1_, worklog1_.duration as duration2_4_1_ from tickets_worklogs worklogs0_ inner join worklogs worklog1_ on worklogs0_.worklogs_id=worklog1_.id where worklogs0_.Ticket_id=?
    Hibernate: select worklogs0_.Ticket_id as Ticket_i1_3_0_, worklogs0_.worklogs_id as worklogs2_3_0_, worklog1_.id as id1_4_1_, worklog1_.duration as duration2_4_1_ from tickets_worklogs worklogs0_ inner join worklogs worklog1_ on worklogs0_.worklogs_id=worklog1_.id where worklogs0_.Ticket_id=?
    Hibernate: select worklogs0_.Ticket_id as Ticket_i1_3_0_, worklogs0_.worklogs_id as worklogs2_3_0_, worklog1_.id as id1_4_1_, worklog1_.duration as duration2_4_1_ from tickets_worklogs worklogs0_ inner join worklogs worklog1_ on worklogs0_.worklogs_id=worklog1_.id where worklogs0_.Ticket_id=?
    Hibernate: select worklogs0_.Ticket_id as Ticket_i1_3_0_, worklogs0_.worklogs_id as worklogs2_3_0_, worklog1_.id as id1_4_1_, worklog1_.duration as duration2_4_1_ from tickets_worklogs worklogs0_ inner join worklogs worklog1_ on worklogs0_.worklogs_id=worklog1_.id where worklogs0_.Ticket_id=?
    Hibernate: select worklogs0_.Ticket_id as Ticket_i1_3_0_, worklogs0_.worklogs_id as worklogs2_3_0_, worklog1_.id as id1_4_1_, worklog1_.duration as duration2_4_1_ from tickets_worklogs worklogs0_ inner join worklogs worklog1_ on worklogs0_.worklogs_id=worklog1_.id where worklogs0_.Ticket_id=?

As expected it results in 12 Queries.
1. One Query to get the `Project`
2. One Query to get all `Tickets` of the `Project`
3. One Query to get all `Worklogs` of each `Ticket` (10)

### Benchmark
    ab -c 20 -n 1000 http://127.0.0.1:8080/entity-graph/resources/project/lazy/1
    ....
    Concurrency Level:      20
    Time taken for tests:   4.000 seconds
    Complete requests:      1000
    Failed requests:        0
    Total transferred:      5068000 bytes
    HTML transferred:       4915000 bytes
    Requests per second:    250.03 [#/sec] (mean)
    Time per request:       79.992 [ms] (mean)
    Time per request:       4.000 [ms] (mean, across all concurrent requests)
    Transfer rate:          1237.43 [Kbytes/sec] received
(This Benchmark was performed with H2 as In-Memory Database)

## /ressources/project/dynamic[/{projectId}]
This Endpoint loads projects, all their tickets and all the worklogs of each ticket.
In order to initialize everything with the FetchType.LAZY a LoadGraph is used.
This will result in one Query containing all results needed.

### Database Queries
The Request `GET /ressources/project/dynamic/1` will result in following Queries

    select project0_.id as id1_0_0_, project0_.description as descript2_0_0_, project0_.name as name3_0_0_, tickets1_.Project_id as Project_1_1_1_, ticket2_.id as tickets_2_1_1_, ticket2_.id as id1_2_2_, ticket2_.description as descript2_2_2_, ticket2_.title as title3_2_2_, worklogs3_.Ticket_id as Ticket_i1_3_3_, worklog4_.id as worklogs2_3_3_, worklog4_.id as id1_4_4_, worklog4_.duration as duration2_4_4_ from projects project0_ left outer join projects_tickets tickets1_ on project0_.id=tickets1_.Project_id left outer join tickets ticket2_ on tickets1_.tickets_id=ticket2_.id left outer join tickets_worklogs worklogs3_ on ticket2_.id=worklogs3_.Ticket_id left outer join worklogs worklog4_ on worklogs3_.worklogs_id=worklog4_.id where project0_.id=?
    
Due to the use of a LoadGraph only one query is executed.

### Benchmark
    ab -c 20 -n 1000 http://127.0.0.1:8080/entity-graph/resources/project/dynamic/1
    ....
    Concurrency Level:      20
    Time taken for tests:   1.540 seconds
    Complete requests:      1000
    Failed requests:        0
    Total transferred:      5068000 bytes
    HTML transferred:       4915000 bytes
    Requests per second:    649.40 [#/sec] (mean)
    Time per request:       30.798 [ms] (mean)
    Time per request:       1.540 [ms] (mean, across all concurrent requests)
    Transfer rate:          3214.01 [Kbytes/sec] received
(This Benchmark was performed with H2 as In-Memory Database)