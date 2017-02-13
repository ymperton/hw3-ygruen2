# Multi-threading with Timing And Dividing Labor Among Threads

The _degree_ of multi-threading is defined as gthe number of simultaneous 
treads used during the execution of an algorithm. For example, some algorithms may 
run faster with more threads (i.e. a higher dergree of paralleism) while other algortihms 
that are largely serial will not benefit from more threads running.

Even algorithms that _do_ benfit from multi-threading, they will improve only up to a certain number of threads.
For example, when running with 100 threads it might run faster than 1000 threads. This might occur for a number of factors including 
resources needed to create and maintian threads, context switches, waits due to synchronization) 

## Assignment
For This assignments write a multi threaded program that computes the average of 100 million random integers
ranging from 1 to 100. 

Solve the problem with a 1,2,4,8,16 etc threads, increasing by a power of two until the execution slows down.

To time the speed of your method implement a 
```java
class StopWatch
{
   public void startTimer(){...}

   /** returns the number of milliseconds since startTimer was called. */
   /**If startTimer was never called, or stopTimer */
   /** was already called once, throws an TimerException (a subclass of */
   /** RuntimeException) with an appropriate message */
   public int stopTimer(){..}
}
```

Create a method in the `Multithreading` class:

 `public static double averageMT(int [] list, int threadCount)` that computes the average of 
`list` using the specified number of threads.

## Note

1. To force the main thread to wait until each thread completes, 
one can use the thread's `join` method, as shown in the starter code. This is a better
choice than the main thread sleeping an arbitrary amount of time (as we previously did)
for the thread's to complete since it might be too little time and the threads did not yet 
complete, or too much time and the program wait unnecessarily.

1. Also beware that you may have numeric overflow when adding many ints. Of course, that must be avoided to be sure the average is correct


[![Build Status](https://travis-ci.org/MCO364-1/hw3-ygruen2.svg?branch=master)](https://travis-ci.org/MCO364-1/hw3-ygruen2)

