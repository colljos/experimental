
Test Concerns:
==============
Due to timing constraints not all Test Concerns have been fully covered.

Concerns taken into account:
- safety (12.1.3)
	correctness (12.1)
		Sequential
		Concurrency (>1 thread)
			blocking (12.2.1)
		resource management (memory, threads, thread pools, file handles, sockets, DB connections, JMS sessions) (12.1.4)
- liveness
	deadlock
	
- performance
	throughput
		burstiness
	latency
	scalability
		fast ramp-up