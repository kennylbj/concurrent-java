Implement Singleton pattern

## 1. EagerSingleton
It is simplest and thread safe version of Singleton.
However, the instance is created even though application might not be using it.

## 2. LazySingleton
Overcome the drawback of EagerSingleton, but it's NOT thread-safe.

## 3. SynchronizedSingleton
Thread-safe but reduces the performance because of cost associated with the synchronized method
 
## 4. DoubleCheckSingleton
Warning: DON'T use it. This implement is incorrect!

## 5. VolatileSingleton
Warning: this implement is limited by JDK version("volatile" semantics guarantee).
Work fine while JDK is higher than 1.5

## 6. InnerStaticClassSingleton
Use inner static class helpler to initialize instance.
It's prefer to implement Singleton using this approach.

## 7. EnumSingleton
Effective Java suggest to implement singleton using this approach.
However it may occur some memory-waste issue

##Summary
Use [LazySingleton](LazySingleton.java) If there is not thread-safe requirement;

Otherwise use [InnerStaticClassSingleton](InnerStaticClassSingleton.java).