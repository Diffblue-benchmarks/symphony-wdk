package com.symphony.bdk.workflow.scheduled;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class ScheduledJobsRegistryClaudeTest {

  private ScheduledJobsRegistry registry;

  @BeforeEach
  void setUp() {
    registry = new ScheduledJobsRegistry(2);
  }

  // Tests for constructor <init>(I)V

  @Test
  void constructor_shouldCreateInstanceWithValidCorePoolSize() {
    // When: Creating ScheduledJobsRegistry with core pool size of 5
    ScheduledJobsRegistry newRegistry = new ScheduledJobsRegistry(5);

    // Then: The instance should be created successfully
    assertThat(newRegistry).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceWithCorePoolSizeOne() {
    // When: Creating ScheduledJobsRegistry with minimum practical core pool size
    ScheduledJobsRegistry newRegistry = new ScheduledJobsRegistry(1);

    // Then: The instance should be created successfully
    assertThat(newRegistry).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceWithZeroCorePoolSize() {
    // When: Creating ScheduledJobsRegistry with core pool size of 0
    ScheduledJobsRegistry newRegistry = new ScheduledJobsRegistry(0);

    // Then: The instance should be created successfully (Java allows this)
    assertThat(newRegistry).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceWithLargeCorePoolSize() {
    // When: Creating ScheduledJobsRegistry with large core pool size
    ScheduledJobsRegistry newRegistry = new ScheduledJobsRegistry(100);

    // Then: The instance should be created successfully
    assertThat(newRegistry).isNotNull();
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    ScheduledJobsRegistry registry1 = new ScheduledJobsRegistry(2);
    ScheduledJobsRegistry registry2 = new ScheduledJobsRegistry(2);

    // Then: Each instance should be distinct
    assertThat(registry1).isNotSameAs(registry2);
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    ScheduledJobsRegistry newRegistry = new ScheduledJobsRegistry(2);

    // Then: The instance should be of type ScheduledJobsRegistry
    assertThat(newRegistry).isInstanceOf(ScheduledJobsRegistry.class);
  }

  // Tests for scheduleJob(RunnableScheduledJob)V

  @Test
  void scheduleJob_shouldScheduleJobSuccessfully() throws InterruptedException {
    // Given: A job that will be executed
    AtomicBoolean jobExecuted = new AtomicBoolean(false);
    CountDownLatch latch = new CountDownLatch(1);

    Runnable job = () -> {
      jobExecuted.set(true);
      latch.countDown();
    };
    RunnableScheduledJob.Id id = () -> "test-job-1";
    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 0L, job);

    // When: Scheduling the job with 0 delay
    registry.scheduleJob(scheduledJob);

    // Then: The job should execute
    boolean completed = latch.await(2, TimeUnit.SECONDS);
    assertThat(completed).isTrue();
    assertThat(jobExecuted.get()).isTrue();
  }

  @Test
  void scheduleJob_shouldScheduleJobWithDelay() throws InterruptedException {
    // Given: A job with a 1 second delay
    AtomicBoolean jobExecuted = new AtomicBoolean(false);
    CountDownLatch latch = new CountDownLatch(1);
    long startTime = System.currentTimeMillis();

    Runnable job = () -> {
      jobExecuted.set(true);
      latch.countDown();
    };
    RunnableScheduledJob.Id id = () -> "test-job-delay";
    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 1L, job);

    // When: Scheduling the job
    registry.scheduleJob(scheduledJob);

    // Then: The job should execute after the delay
    boolean completed = latch.await(3, TimeUnit.SECONDS);
    long elapsed = System.currentTimeMillis() - startTime;

    assertThat(completed).isTrue();
    assertThat(jobExecuted.get()).isTrue();
    assertThat(elapsed).isGreaterThanOrEqualTo(1000); // At least 1 second
  }

  @Test
  void scheduleJob_shouldHandleNullJob() {
    // When: Scheduling a null job
    registry.scheduleJob(null);

    // Then: No exception should be thrown (method should handle null gracefully)
    // This test verifies the null check in the method
  }

  @Test
  void scheduleJob_shouldScheduleMultipleJobs() throws InterruptedException {
    // Given: Multiple jobs
    AtomicInteger jobCount = new AtomicInteger(0);
    CountDownLatch latch = new CountDownLatch(3);

    Runnable job1 = () -> {
      jobCount.incrementAndGet();
      latch.countDown();
    };
    Runnable job2 = () -> {
      jobCount.incrementAndGet();
      latch.countDown();
    };
    Runnable job3 = () -> {
      jobCount.incrementAndGet();
      latch.countDown();
    };

    RunnableScheduledJob scheduledJob1 = new RunnableScheduledJob(() -> "job-1", 0L, job1);
    RunnableScheduledJob scheduledJob2 = new RunnableScheduledJob(() -> "job-2", 0L, job2);
    RunnableScheduledJob scheduledJob3 = new RunnableScheduledJob(() -> "job-3", 0L, job3);

    // When: Scheduling multiple jobs
    registry.scheduleJob(scheduledJob1);
    registry.scheduleJob(scheduledJob2);
    registry.scheduleJob(scheduledJob3);

    // Then: All jobs should execute
    boolean completed = latch.await(3, TimeUnit.SECONDS);
    assertThat(completed).isTrue();
    assertThat(jobCount.get()).isEqualTo(3);
  }

  @Test
  void scheduleJob_shouldScheduleJobThatThrowsException() throws InterruptedException {
    // Given: A job that throws an exception
    CountDownLatch latch = new CountDownLatch(1);
    Runnable job = () -> {
      latch.countDown();
      throw new RuntimeException("Test exception");
    };
    RunnableScheduledJob.Id id = () -> "exception-job";
    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(id, 0L, job);

    // When: Scheduling the job
    registry.scheduleJob(scheduledJob);

    // Then: The job should execute (exception is caught by RunnableScheduledJob)
    boolean completed = latch.await(2, TimeUnit.SECONDS);
    assertThat(completed).isTrue();
  }

  @Test
  void scheduleJob_shouldHandleJobWithZeroDelay() throws InterruptedException {
    // Given: A job with zero delay
    AtomicBoolean jobExecuted = new AtomicBoolean(false);
    CountDownLatch latch = new CountDownLatch(1);

    Runnable job = () -> {
      jobExecuted.set(true);
      latch.countDown();
    };
    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(() -> "zero-delay", 0L, job);

    // When: Scheduling the job
    registry.scheduleJob(scheduledJob);

    // Then: The job should execute immediately
    boolean completed = latch.await(2, TimeUnit.SECONDS);
    assertThat(completed).isTrue();
    assertThat(jobExecuted.get()).isTrue();
  }

  @Test
  void scheduleJob_shouldHandleJobWithNegativeDelay() throws InterruptedException {
    // Given: A job with negative delay (edge case)
    AtomicBoolean jobExecuted = new AtomicBoolean(false);
    CountDownLatch latch = new CountDownLatch(1);

    Runnable job = () -> {
      jobExecuted.set(true);
      latch.countDown();
    };
    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(() -> "negative-delay", -5L, job);

    // When: Scheduling the job
    registry.scheduleJob(scheduledJob);

    // Then: The job should execute immediately (negative delay is treated as 0)
    boolean completed = latch.await(2, TimeUnit.SECONDS);
    assertThat(completed).isTrue();
    assertThat(jobExecuted.get()).isTrue();
  }

  @Test
  void scheduleJob_shouldAddJobToQueue() {
    // Given: A job with a longer delay to keep it in queue
    Runnable job = () -> {};
    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(() -> "queued-job", 10L, job);

    // When: Scheduling the job
    registry.scheduleJob(scheduledJob);

    // Then: The job should be in the queue (we can't directly verify queue size
    // without reflection, but we verify no exception is thrown)
  }

  @Test
  void scheduleJob_shouldScheduleJobsIndependently() throws InterruptedException {
    // Given: Two independent jobs
    AtomicBoolean job1Executed = new AtomicBoolean(false);
    AtomicBoolean job2Executed = new AtomicBoolean(false);
    CountDownLatch latch = new CountDownLatch(2);

    Runnable job1 = () -> {
      job1Executed.set(true);
      latch.countDown();
    };
    Runnable job2 = () -> {
      job2Executed.set(true);
      latch.countDown();
    };

    RunnableScheduledJob scheduledJob1 = new RunnableScheduledJob(() -> "independent-1", 0L, job1);
    RunnableScheduledJob scheduledJob2 = new RunnableScheduledJob(() -> "independent-2", 0L, job2);

    // When: Scheduling both jobs
    registry.scheduleJob(scheduledJob1);
    registry.scheduleJob(scheduledJob2);

    // Then: Both jobs should execute independently
    boolean completed = latch.await(3, TimeUnit.SECONDS);
    assertThat(completed).isTrue();
    assertThat(job1Executed.get()).isTrue();
    assertThat(job2Executed.get()).isTrue();
  }

  @Test
  void scheduleJob_shouldHandleJobWithLongDelay() {
    // Given: A job with a very long delay
    Runnable job = () -> {};
    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(() -> "long-delay", 1000L, job);

    // When: Scheduling the job (we don't wait for it to execute)
    registry.scheduleJob(scheduledJob);

    // Then: No exception should be thrown
  }

  @Test
  void scheduleJob_shouldHandleRapidSuccessiveScheduling() throws InterruptedException {
    // Given: Many jobs scheduled rapidly
    AtomicInteger executionCount = new AtomicInteger(0);
    int jobCount = 10;
    CountDownLatch latch = new CountDownLatch(jobCount);

    // When: Scheduling many jobs rapidly
    for (int i = 0; i < jobCount; i++) {
      final int jobId = i;
      Runnable job = () -> {
        executionCount.incrementAndGet();
        latch.countDown();
      };
      RunnableScheduledJob scheduledJob = new RunnableScheduledJob(
          () -> "rapid-job-" + jobId, 0L, job);
      registry.scheduleJob(scheduledJob);
    }

    // Then: All jobs should be scheduled and execute
    boolean completed = latch.await(5, TimeUnit.SECONDS);
    assertThat(completed).isTrue();
    assertThat(executionCount.get()).isEqualTo(jobCount);
  }

  @Test
  void scheduleJob_shouldRespectDelayOrder() throws InterruptedException {
    // Given: Jobs with different delays
    AtomicInteger executionOrder = new AtomicInteger(0);
    CountDownLatch latch = new CountDownLatch(2);
    AtomicInteger firstJobOrder = new AtomicInteger(-1);
    AtomicInteger secondJobOrder = new AtomicInteger(-1);

    Runnable job1 = () -> {
      firstJobOrder.set(executionOrder.incrementAndGet());
      latch.countDown();
    };
    Runnable job2 = () -> {
      secondJobOrder.set(executionOrder.incrementAndGet());
      latch.countDown();
    };

    RunnableScheduledJob scheduledJob1 = new RunnableScheduledJob(() -> "delayed-job", 1L, job1);
    RunnableScheduledJob scheduledJob2 = new RunnableScheduledJob(() -> "immediate-job", 0L, job2);

    // When: Scheduling jobs with different delays
    registry.scheduleJob(scheduledJob1);
    registry.scheduleJob(scheduledJob2);

    // Then: Job with 0 delay should execute first
    boolean completed = latch.await(3, TimeUnit.SECONDS);
    assertThat(completed).isTrue();
    assertThat(secondJobOrder.get()).isEqualTo(1); // Job2 executes first
    assertThat(firstJobOrder.get()).isEqualTo(2);   // Job1 executes second
  }

  @Test
  void scheduleJob_shouldWorkWithDifferentJobIds() throws InterruptedException {
    // Given: Jobs with different IDs
    CountDownLatch latch = new CountDownLatch(3);

    RunnableScheduledJob job1 = new RunnableScheduledJob(() -> "job-a", 0L, latch::countDown);
    RunnableScheduledJob job2 = new RunnableScheduledJob(() -> "job-b", 0L, latch::countDown);
    RunnableScheduledJob job3 = new RunnableScheduledJob(() -> "job-c", 0L, latch::countDown);

    // When: Scheduling jobs with different IDs
    registry.scheduleJob(job1);
    registry.scheduleJob(job2);
    registry.scheduleJob(job3);

    // Then: All jobs should execute
    boolean completed = latch.await(3, TimeUnit.SECONDS);
    assertThat(completed).isTrue();
  }

  @Test
  void scheduleJob_shouldHandleEmptyJob() throws InterruptedException {
    // Given: A job that does nothing
    CountDownLatch latch = new CountDownLatch(1);
    Runnable job = latch::countDown;
    RunnableScheduledJob scheduledJob = new RunnableScheduledJob(() -> "empty-job", 0L, job);

    // When: Scheduling the empty job
    registry.scheduleJob(scheduledJob);

    // Then: The job should execute
    boolean completed = latch.await(2, TimeUnit.SECONDS);
    assertThat(completed).isTrue();
  }

  @Test
  void scheduleJob_multipleRegistriesShouldBeIndependent() throws InterruptedException {
    // Given: Two different registries
    ScheduledJobsRegistry registry1 = new ScheduledJobsRegistry(2);
    ScheduledJobsRegistry registry2 = new ScheduledJobsRegistry(2);

    CountDownLatch latch1 = new CountDownLatch(1);
    CountDownLatch latch2 = new CountDownLatch(1);

    RunnableScheduledJob job1 = new RunnableScheduledJob(() -> "reg1-job", 0L, latch1::countDown);
    RunnableScheduledJob job2 = new RunnableScheduledJob(() -> "reg2-job", 0L, latch2::countDown);

    // When: Scheduling jobs on different registries
    registry1.scheduleJob(job1);
    registry2.scheduleJob(job2);

    // Then: Both jobs should execute independently
    boolean completed1 = latch1.await(2, TimeUnit.SECONDS);
    boolean completed2 = latch2.await(2, TimeUnit.SECONDS);

    assertThat(completed1).isTrue();
    assertThat(completed2).isTrue();
  }
}
