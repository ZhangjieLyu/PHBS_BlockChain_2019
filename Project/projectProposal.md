# Personal File System via Block Chain

**Content table**

+ What's personal file system?
+ What's wrong with it?
+ Why block chain?
+ Design of block chain(architecture)
+ Demo(test cases)



## Why block chain?

Short answer:

+ immutable
+ cannot be double spent
+ can be accessible all over the network
+ one party cannot control the network(suppose no 51% attack)
+ history traceable

where some features are evitable but cannot be realized even through a centralized digital system.

## Design of p-chain

The design on **application layer**. On this layer, design learns a lot from Ethereum. However, the differences are still significant.

### Characteristics of p-chain

Compared to common features of block chain, p-chain is:

1. real-name based
2. in most cases, no malicious nodes
3. network layer can be optimized(since the topological structure is deterministic)
4. not all parts of information are viewable to every node, authorization is required
5. all irregular transaction(double-spending etc.) should be validated

### Roles in block chain

Unlike traditional block chain, which has 2 major roles, miners and common users. The personal file system chain("p-chain" for short) has more roles, they are:

**miners**

miners are authorized institute user, including local personnel administration department, administration department on a province or higher level. They pack and mine a new block through mechanism of POS/DPOS. 

**common users**

common users are subordinate to miners, including universities or other institute that are qualified to transmit or modify part of these personal file information, they upload information for miners to pack.

**validator**

Validator are responsible for checking transactions uploaded by users. Also the transactions processed by miners are supervised.

**external users**

With the relatively lowest authority, can only send request to view authorized part of information



