#concurrent-java
Problems about multi-thread programing in java.

##Summary
Each package contains a problem and my solution (may not be right though).

| # | package | description | concept |
|---| ------- | ------- | ------- |
| 1 | [bank](./src/main/java/bank) | - | Data race; Race condition; Dead lock |
| 2 | [philosopher](./src/main/java/philosopher) | [Dining Philosopher problem](https://en.wikipedia.org/wiki/Dining_philosophers_problem) | Data race; Race condition; Dead lock |
| 3 | [producerconsumer](./src/main/java/producerconsumer) | [Producer-Consumer problem](https://en.wikipedia.org/wiki/Producer-consumer_problem) | Semaphore; Conditional variable; BlockingQueue |
| 4 | [observer](./src/main/java/observer) | Thread-safety [Observer Pattern](https://en.wikipedia.org/wiki/Observer_pattern) | Observer pattern; Defensive copy; Copy on Write; Event Bus |
| 5 | [optional](./src/main/java/optional) | An elegant alternative to null | Java 8 Optional; null |
| 6 | [completableFuture](./src/main/java/completableFuture) | Composable Asynchronous Programing | Java 8 CompletableFuture |
| 7 | [blockqueue](./src/main/java/blockqueue) | A thread-safety Queue | BlockingQueue; Conditional Variable |
| 8 | [timer](./src/main/java/timer) | A Data structure to execute task at specific time | Timer; BlockingQueue |
| 9 | [eventloop](./src/main/java/eventloop) | An event-driven asynchronous programing | Event loop; Non-blocking I/O; NIO |

##Motivation
My friend is new to java concurrent programing, so i wrote these code
to help him to have a better knowledge about coding in multi-thread 
environment.
I will continue to post updates.

##Getting Started

### Prerequisites
Java 8  
Maven

### Installing
```
cd concurrent-java
mvn clean package -DskipTests
```

##License
This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details
