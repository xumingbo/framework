package com.a1soft.framework.unit.threadsafe;

import org.testng.Assert;
import org.testng.annotations.Test;


public class UnsafeCounterUnitTest { // ...
	@Test
	public void testShareability() throws InterruptedException {
		int N = 10;
		SharedObj sharedCounter = new SharedCounter(N);
		Shareability.test(sharedCounter, N);

		if (sharedCounter.isThreadSafe()){
        System.out.println("  !!!! not able to detect thread unsafe this time");
    }
    else {
        System.out.println("  !!!! thread unsafe detected this time");
    }
		Assert.assertTrue(true);
	}

}

/*
 * UnsafeCounter is a simple counter. It is not thread-safe.
 * provided here as a negative test case
 */
class UnsafeCounter {
    private int count = 0;

    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }

    public int value() {
        return count;
    }
}


class SharedCounter extends SharedObj
{
	private final UnsafeCounter counter = new UnsafeCounter();
	private final int LOOP_COUNT = 100;
	private int threadCount;
	public SharedCounter(int threadCount){
		this.threadCount = threadCount;
	}
	public void doWork()
	{
		for (int i = 0; i < LOOP_COUNT; i++){
			counter.increment();
		}
	}
	public Integer checkResult(){
		return counter.value();
	}
	@Override
	public boolean isThreadSafe() {
		return completeAllThreadsRun && (counter.value() == threadCount * LOOP_COUNT);

	}
}
