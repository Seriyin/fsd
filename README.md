# Library Management System ##

-----------------------------------------

### Entities ###

The system should include the following entities:

   - Book - Gives access to book information (Title, Authorship, ...).
   - Library - Allows book searches and purchase history consultation.
   - Cart - Allows organization of books for purchase.
   - Bank - Allows payment registration and payment history consultation.

--------------------------------------------

### Operations ###

1. Typical operation by a client:
    - Book search in a library
    - Adding books to a cart
    - Purchasing books by specifying a bank for payment.

2. The system must allow the client, library and bank to execute in different processes and/or machines. Can also allow book and bank account storage to be distributed by multiple processes.

3. The proposed system must guarantee atomic client purchase, even with various concurrent clients and any involved process' restart (2PC). Can also allow client to make progress without bank process being available.

4.  The proposed solution should be modular, maximizing code independent from this concrete application and suited for large scale utilization, without bottlenecks or limitations.
