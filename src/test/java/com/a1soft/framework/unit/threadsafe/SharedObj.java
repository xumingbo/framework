package com.a1soft.framework.unit.threadsafe;

/*
 * Implement ShareObj interface with a class which contains the class / methods to be tested thread-safe.
 * See example classes:
 * 
 * 1. Negative test case:    SharedCounter
 * 
 */
abstract class SharedObj
{
	/*
	 * called by the framework
	 */
	boolean completeAllThreadsRun = false;
	void onAllthreadsRunCompletion(){
		completeAllThreadsRun = true;
	}
	
	/*
	 * This method is to run concurrently on multiple threads.
	 */
	abstract void doWork();
	
	/*
	 * Returns the status of thread-safe on completion of all doWork() calls running on 
	 * multiple threads
	 */
	abstract boolean isThreadSafe();
}
