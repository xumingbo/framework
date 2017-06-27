# framework
Generic Java tools

## thread-safe unit test framework

The core of this framework is the class 'Shareability'. Applying CountDownLatch to
start workers on multiple threads simultaneously, and wait for all workers complete.

Examples:
  1. Negative test case:    UnsafeCounterUnitTest

