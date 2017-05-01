# concurrent-java
Problems about multi-thread programing in java.

## Summary
Each package contains a problem and my solution.

| # | package | description | concept |
|---| :-----: | ----------- | ------- |
| 1 | [bank](./src/main/java/bank) | transfer money to each other in different accounts | data race; race condition; dead lock |
| 2 | [philosopher](./src/main/java/philosopher) | [dining philosopher problem](https://en.wikipedia.org/wiki/Dining_philosophers_problem) | data race; race condition; dead lock |
| 3 | [producerconsumer](./src/main/java/producerconsumer) | [producer-consumer problem](https://en.wikipedia.org/wiki/Producer-consumer_problem) | semaphore; conditional variable; BlockingQueue |
| 4 | [observer](./src/main/java/observer) | thread-safety [observer pattern](https://en.wikipedia.org/wiki/Observer_pattern) | observer pattern; defensive copy; copy on write; EventBus |
| 5 | [optional](./src/main/java/optional) | an elegant alternative to null | Optional; null |
| 6 | [completableFuture](./src/main/java/completableFuture) | composable asynchronous programing | CompletableFuture |
| 7 | [blockqueue](./src/main/java/blockqueue) | a thread-safety queue | BlockingQueue; conditional variable |
| 8 | [timer](./src/main/java/timer) | a data structure to execute task at specific time | timer; BlockingQueue |
| 9 | [eventloop](./src/main/java/eventloop) | an event-driven asynchronous programing model | event loop; non-blocking I/O; NIO |

## Motivation
My friend is new to java concurrent programing, so i set up this project to help him to have a better knowledge about coding in multi-thread 
environment.  
I will continue to post updates.

## Getting Started

### Prerequisites
Java 8  
Maven

### Building
```
cd concurrent-java
mvn clean package
```

## License
This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details
