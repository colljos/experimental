Chapter 3 - Design Approaches

Dealing with State:
- shared mutability		~ synchronize & suffer, visibility/memory barrier
- isolated mutability	~ mutable vars only ever seen by 1 thread
- pure immutability		~ nothing can change (eg. Map/Reduce functional composition paradigm)

Data Structures (required for design with immutability)
- Immutable Lists
- Persistent Tries ("Ideal Hash trees" ~ 32 branches per node)

Examples:
