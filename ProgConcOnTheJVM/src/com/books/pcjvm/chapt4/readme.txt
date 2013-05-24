Part II - Modern Java/JDK Concurrency

Chapter 4 - Scalability & Thread Safety

Thread Pools
- ExecutorService
1. Select Pool Type (ST, cached, priority-based, scheduled/periodic, fixed size)
2. Define size of wait Queue for tasks scheduled to run
3. Submit tasks (Runnable/no result, Callable/Future result), with/without timeout, individually (execute/submit) or as a collection (invokeAll)

Coordinating Threads
- scheduling & joining
- ExecutorService + Futures
- CountdownLatch + Atomics
- CyclicBarrier
- BlockingQueue + Atomic
- Exchanger

Scalable Collections
- thread safety Vs performance tradeoff

Locks vs Synchronized
- Lock Interface
- timeout parameter
- interruptable (thread waiting for lock)
- Reentrant Lock ~ allows threads to rerequest locks they already own (thus allowing them to reenter their mutually exclusive sections)

Examples:
- Total File Size (incl. recursive directory traversal)
1. Sequential 
2. Naively Concurrent (Executor/Future) ~ scheduling overhead (timeout prevents "pool induced deadlock"), saturated thread pool (calling thread waiting for recursive sub-threads to complete)
3. Concurrent (Executor/Future) ~ all thread waiting in main thread only
[2,3 do not require shared mutable state]
4. CountDownLatch (using Mutable state/Atomic Long)
(could use a CyclicBarrier to reuse synchronisation point)
5. Executor + BlockingQueue (as data sync/exchange between threads)
(use Exchanger to simply exchange data between two threads)
(use BlockingQueue to exchange multiple data sets; SynchronousQ, PriorityBQ, LinkedBQ, ArrayBQ)
6. ForkJoin (dynamically manages threads based
 on number of processors & task demand, via "work-stealing")
 
See Actor-based version in Chapter 8
7. Actors ~ isolated mutability (no synchronization, latches, queues, Atomics) 

See STM-based version in Chapter 6
8. STM ~ lock-free, transactional integrity & retry ~ (poor fit due to high write contention)

- Map Access (collections)
- Bank Account Transfer (locking)