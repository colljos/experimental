
1. Mandatory exercise (hardcoded commands)
Run the application 'AppRunner' with no arguments (no change)

Implementation Notes:
- uses original 'AppEnvironmentImpl' to bootstrap Consumer
- see class implementation: 'uk.co.cmcmarkets.orderbook.OrderConsumerImpl'
- see 'uk.co.cmcmarkets.orderbook.book' (and associated JUnit tests)
- see 'uk.co.cmcmarkets.orderbook.book.views' (and associated JUnit tests)

2. Optional exercise (XML orders file)
Run the application 'AppRunnerXml' with an order xml file as program argument
eg. AppRunnerXml resources/orders1.xml
eg. AppRunnerXml resources/orders2.xml

Implementation Notes:
- uses 'AppEnvironmentProdConsImpl' to bootstrap 1x Consumer & 1x Producer (XML file reader) 
- see package 'uk.co.cmcmarkets.orderbook.producer' (and associated JUnit tests)

Proposed Enhancements:
- Consumers & Producers to become independent executable Threads, using a Queue for data passing/synchronisation
- review Data Types for Order attributes (eg. BigDecimal for price, BigInteger for quantity)
- use "Push" model for OrderProducers (eg. XML SAX Event parser and notify per Order event)
- concurrent Order Book (& View) processing (eg. hashcode based on Instrument & distributed queues)
- distributed Order book (& View) processing (eg. publish/subscribe using middleware messaging bus)
- add JavaDoc
- improve Exception handling & validation

