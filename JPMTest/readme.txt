JPM Technical Tests
===================

1. Write an algorithm that recursively sorts a singly linked list in linear time and constant space. Please start from the attached file.

See "SingleLinkedListProblem.java":
	- implemented methods: reverse(), sort() [quicksort], buildlist()
	- implemented ISingleLinkedList interface
	- added additional JUnit test cases for Sort

2. A LogParser interface can only return a single event at a time:   
                   Event Parser.readNext()
In the pull parser that you’re implementing, you’ll be getting two events at a time instead of one event at a time. You can think of this as  after reading a single physical line in a log file, two events result instead of one. Knowing that other parsers can have this problem, how can you embed the logic for returning two events instead of one w/o changing the interface you’re implementing all the while avoiding inheritance? Please write up this class so that it calls two private methods: “Event getFirst()” and “Event getLast()”  to get the two events while implementing the LogParser interface.

See "LogParserProblem.java"
	- implemented ILogParser interface
	- implemented Event
	- added JUnit tests cases

3. Implement the the methods in the MaketData class       
Assumptions:
Max number of subscribers per symbol: 16 Number of symbols: 10000 Price Feed handler threads: 10 Subscriber threads: 16 Subscriber’s onQuote() callback can take >100ms (a long time)

See "MarketData.java" and JUnit test cases under test: "MarketDataTest.java, MarketDataScaleTest.java" 
	- onQuote() uses Executor to avoid blocking on listener callbacks
	- multiple Feeder threads used to pump prices onto Quote: see startFeeder();
	- unbounded ConcurrentLinkedQueue used to queue up Quotes per Symbol
	- synchronized Set used to maintain subscriber list per Symbol
	- all symbols stored in ConcurrentHashMap