package com.a1soft.framework.unit.threadsafe;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Shareability is a Thread-Safe unit test framework. Applying CountDownLatch to
 * start workers on multiple threads simultaneously, and wait for all workers complete.
 * 
 * Examples:
 * 1. Negative test case:    UnsafeCounterUnitTest
 *    
 */
public class Shareability{
	private static final int DEFAULT_NUM_THREADS = 10;
    public static void test(SharedObj testObj, int numThreads) throws InterruptedException {
        
        ExecutorService service = Executors.newFixedThreadPool(numThreads);
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(numThreads);
        
        for (int i = 0; i < numThreads; ++i){ // create and start threads
        	Worker w = new Worker(startSignal, doneSignal, testObj);
          
          service.submit(w);
        }
        startSignal.countDown();      // let all threads proceed
        doneSignal.await();           // wait for all to finish
        testObj.onAllthreadsRunCompletion();
        service.shutdown();
    }
    public static void test(SharedObj testObj) throws InterruptedException {
    	test(testObj, DEFAULT_NUM_THREADS);
    }
}

class  Worker implements Callable<Integer> {
	private final CountDownLatch startSignal;
	private final CountDownLatch doneSignal;
	private final SharedObj sharedObj;

	Worker(CountDownLatch startSignal, CountDownLatch doneSignal, SharedObj sharedObj) {
		this.startSignal = startSignal;
		this.doneSignal = doneSignal;
		this.sharedObj = sharedObj;
	}
	public Integer call() throws InterruptedException {

		startSignal.await();
		sharedObj.doWork();
		doneSignal.countDown();
		return 0;
	}

}
