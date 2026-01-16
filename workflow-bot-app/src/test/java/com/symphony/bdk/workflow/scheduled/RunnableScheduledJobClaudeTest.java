package com.symphony.bdk.workflow.scheduled;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class RunnableScheduledJobClaudeTest {

  @Test
  void testRun_successfulExecution() {
    // Test that run() executes the wrapped job successfully
    AtomicBoolean jobExecuted = new AtomicBoolean(false);
    Runnable job = () -> jobExecuted.set(true);
    RunnableScheduledJob.Id id = () -> "test-id";

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 1000L, job);

    scheduledJob.run();

    assertThat(jobExecuted.get()).isTrue();
  }

  @Test
  void testRun_catchesRuntimeException() {
    // Test that run() catches RuntimeException and doesn't propagate it
    Runnable job = () -> {
      throw new RuntimeException("Test exception");
    };
    RunnableScheduledJob.Id id = () -> "test-id";

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 1000L, job);

    // Should not throw exception
    scheduledJob.run();
  }

  @Test
  void testRun_catchesError() {
    // Test that run() catches Error (extends Throwable) and doesn't propagate it
    AtomicBoolean errorThrown = new AtomicBoolean(false);
    Runnable job = () -> {
      errorThrown.set(true);
      throw new AssertionError("Test error");
    };
    RunnableScheduledJob.Id id = () -> "test-id";

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 1000L, job);

    // Should not throw error
    scheduledJob.run();

    assertThat(errorThrown.get()).isTrue();
  }

  @Test
  void testRun_catchesCheckedException() {
    // Test that run() catches any Throwable including checked exceptions wrapped in lambdas
    AtomicBoolean jobAttempted = new AtomicBoolean(false);
    Runnable job = () -> {
      jobAttempted.set(true);
      // Simulate a situation where a checked exception might be thrown
      throw new RuntimeException(new Exception("Simulated checked exception"));
    };
    RunnableScheduledJob.Id id = () -> "test-id";

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 1000L, job);

    // Should not throw exception
    scheduledJob.run();

    assertThat(jobAttempted.get()).isTrue();
  }

  @Test
  void testRun_multipleExecutions() {
    // Test that run() can be called multiple times
    AtomicInteger executionCount = new AtomicInteger(0);
    Runnable job = executionCount::incrementAndGet;
    RunnableScheduledJob.Id id = () -> "test-id";

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 1000L, job);

    scheduledJob.run();
    scheduledJob.run();
    scheduledJob.run();

    assertThat(executionCount.get()).isEqualTo(3);
  }

  @Test
  void testRun_withNullPointerException() {
    // Test that run() catches NullPointerException
    Runnable job = () -> {
      String str = null;
      str.length(); // Will throw NullPointerException
    };
    RunnableScheduledJob.Id id = () -> "test-id";

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 1000L, job);

    // Should not throw exception
    scheduledJob.run();
  }

  @Test
  void testRun_withIllegalStateException() {
    // Test that run() catches IllegalStateException
    Runnable job = () -> {
      throw new IllegalStateException("Invalid state");
    };
    RunnableScheduledJob.Id id = () -> "test-id";

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 1000L, job);

    // Should not throw exception
    scheduledJob.run();
  }

  @Test
  void testRun_partialExecution() {
    // Test that if job throws exception midway, it's caught
    AtomicBoolean firstPartExecuted = new AtomicBoolean(false);
    AtomicBoolean secondPartExecuted = new AtomicBoolean(false);

    Runnable job = () -> {
      firstPartExecuted.set(true);
      throw new RuntimeException("Midway exception");
      // This line would never be reached but demonstrates partial execution
      // secondPartExecuted.set(true);
    };
    RunnableScheduledJob.Id id = () -> "test-id";

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 1000L, job);

    scheduledJob.run();

    assertThat(firstPartExecuted.get()).isTrue();
    assertThat(secondPartExecuted.get()).isFalse();
  }

  @Test
  void testGetId() {
    // Test that getId() returns the correct id
    RunnableScheduledJob.Id id = () -> "test-workflow-id";
    Runnable job = () -> {};

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 2000L, job);

    assertThat(scheduledJob.getId()).isNotNull();
    assertThat(scheduledJob.getId().id()).isEqualTo("test-workflow-id");
  }

  @Test
  void testGetDelay() {
    // Test that getDelay() returns the correct delay
    RunnableScheduledJob.Id id = () -> "test-id";
    Runnable job = () -> {};
    long delay = 5000L;

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, delay, job);

    assertThat(scheduledJob.getDelay()).isEqualTo(delay);
  }

  @Test
  void testConstructor_withZeroDelay() {
    // Test constructor with zero delay
    RunnableScheduledJob.Id id = () -> "test-id";
    Runnable job = () -> {};

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 0L, job);

    assertThat(scheduledJob.getDelay()).isEqualTo(0L);
    assertThat(scheduledJob.getId().id()).isEqualTo("test-id");
  }

  @Test
  void testConstructor_withNegativeDelay() {
    // Test constructor with negative delay (edge case)
    RunnableScheduledJob.Id id = () -> "test-id";
    Runnable job = () -> {};

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, -1000L, job);

    assertThat(scheduledJob.getDelay()).isEqualTo(-1000L);
  }

  @Test
  void testImplementsRunnable() {
    // Test that RunnableScheduledJob implements Runnable
    RunnableScheduledJob.Id id = () -> "test-id";
    Runnable job = () -> {};

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 1000L, job);

    assertThat(scheduledJob).isInstanceOf(Runnable.class);
  }

  @Test
  void testImplementsScheduledJob() {
    // Test that RunnableScheduledJob implements ScheduledJob
    RunnableScheduledJob.Id id = () -> "test-id";
    Runnable job = () -> {};

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 1000L, job);

    assertThat(scheduledJob).isInstanceOf(ScheduledJob.class);
  }

  @Test
  void testId_functionalInterface() {
    // Test that Id is a functional interface that can be created with lambda
    RunnableScheduledJob.Id id1 = () -> "id-1";
    RunnableScheduledJob.Id id2 = () -> "id-2";

    assertThat(id1.id()).isEqualTo("id-1");
    assertThat(id2.id()).isEqualTo("id-2");
    assertThat(id1).isNotEqualTo(id2);
  }

  @Test
  void testRun_exceptionDoesNotAffectSubsequentRuns() {
    // Test that exception in one run doesn't prevent subsequent runs
    AtomicInteger runCount = new AtomicInteger(0);
    Runnable job = () -> {
      int count = runCount.incrementAndGet();
      if (count == 1) {
        throw new RuntimeException("First run exception");
      }
    };
    RunnableScheduledJob.Id id = () -> "test-id";

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 1000L, job);

    // First run throws exception
    scheduledJob.run();
    assertThat(runCount.get()).isEqualTo(1);

    // Second run should succeed
    scheduledJob.run();
    assertThat(runCount.get()).isEqualTo(2);

    // Third run should succeed
    scheduledJob.run();
    assertThat(runCount.get()).isEqualTo(3);
  }

  @Test
  void testRun_withOutOfMemoryError() {
    // Test that run() even catches OutOfMemoryError (though unlikely in test)
    AtomicBoolean attempted = new AtomicBoolean(false);
    Runnable job = () -> {
      attempted.set(true);
      // Simulating with regular Error instead of actually causing OOM
      throw new Error("Simulated memory error");
    };
    RunnableScheduledJob.Id id = () -> "test-id";

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 1000L, job);

    // Should catch even Error subclasses
    scheduledJob.run();

    assertThat(attempted.get()).isTrue();
  }

  @Test
  void testRun_jobWithSideEffects() {
    // Test that run() properly executes job with side effects
    StringBuilder result = new StringBuilder();
    Runnable job = () -> {
      result.append("executed");
      result.append("-");
      result.append("successfully");
    };
    RunnableScheduledJob.Id id = () -> "test-id";

    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 1000L, job);

    scheduledJob.run();

    assertThat(result.toString()).isEqualTo("executed-successfully");
  }

  @Test
  void testMultipleInstancesIndependent() {
    // Test that multiple RunnableScheduledJob instances are independent
    AtomicInteger counter1 = new AtomicInteger(0);
    AtomicInteger counter2 = new AtomicInteger(0);

    RunnableScheduledJob job1 = new RunnableScheduledJob(
        () -> "job-1",
        1000L,
        counter1::incrementAndGet
    );

    RunnableScheduledJob job2 = new RunnableScheduledJob(
        () -> "job-2",
        2000L,
        counter2::incrementAndGet
    );

    job1.run();
    job1.run();
    job2.run();

    assertThat(counter1.get()).isEqualTo(2);
    assertThat(counter2.get()).isEqualTo(1);
    assertThat(job1.getId().id()).isEqualTo("job-1");
    assertThat(job2.getId().id()).isEqualTo("job-2");
    assertThat(job1.getDelay()).isEqualTo(1000L);
    assertThat(job2.getDelay()).isEqualTo(2000L);
  }
}
