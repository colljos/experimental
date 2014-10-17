Chapter 2 - Division of Labour

Concerns
- from Sequential to Concurrent
- Divide and Conquer
	1) Determining the number of threads
		a) number of available cores
		b) blocking coefficient of tasks
		NumThreads = NumCores / (1 - BlockingCoeficient)
	2) Determining the number of parts
- speedup factors	

Concurrency in different application types:
1) I/O intensive (Net Asset Value computation by accessing a remote Web Service for latest price)
	- isolated mutability 
	- synchronization concerns handled by Executor service
2) Computationally intensive (prime number calculator)

Examples:
- Sequential computation of NAV
- Concurrent computation of NAV
- Sequential computation of Prime Numbers
- Concurrent computation of Prime Numbers