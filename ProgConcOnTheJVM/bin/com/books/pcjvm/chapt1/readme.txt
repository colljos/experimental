Chapter 1 - The Perils of Concurrency

Power of concurrency
- responsiveness (speed, faster)
- increased throughput
- decreased latency
[leverage evolving hardware trends and multicore processors]

Perils of concurrency
- Starvation		~ Sol: timeouts
- Deadlocks			~ Sol: acquire locks in specific order
- Race Conditions	~ Cause: JMM, JIT

Concerns:
- Visibility: understand the Java Memory Barrier
- "happens-before" = sequence or ordering of crossings to/from main memory (from/to working memory: cache, registers)
  'the write has to happens-before the read'
 - Shared Mutability is "pure evil"
 
 Examples:
 - Race Condition caused by JIT compiler (client vs serverside mode)