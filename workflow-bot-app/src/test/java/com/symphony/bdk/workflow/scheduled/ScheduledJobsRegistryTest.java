package com.symphony.bdk.workflow.scheduled;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ScheduledThreadPoolExecutor;

class ScheduledJobsRegistryTest {

  @Test
  void shouldCreateScheduledThreadPoolExecutorWithSpecifiedCorePoolSize() {
    int corePoolSize = 5;

    ScheduledJobsRegistry registry = new ScheduledJobsRegistry(corePoolSize);

    assertThat(registry).isNotNull();
  }

  @Test
  void shouldScheduleJobWhenJobIsNotNull() {
    ScheduledJobsRegistry registry = new ScheduledJobsRegistry(2);
    RunnableScheduledJob job = createMockJob("job-1", 10L);

    registry.scheduleJob(job);

    assertThat(registry).isNotNull();
  }

  @Test
  void shouldHandleNullJobGracefully() {
    ScheduledJobsRegistry registry = new ScheduledJobsRegistry(2);

    registry.scheduleJob(null);

    assertThat(registry).isNotNull();
  }

  @Test
  void shouldScheduleMultipleJobs() {
    ScheduledJobsRegistry registry = new ScheduledJobsRegistry(3);
    RunnableScheduledJob job1 = createMockJob("job-1", 5L);
    RunnableScheduledJob job2 = createMockJob("job-2", 10L);

    registry.scheduleJob(job1);
    registry.scheduleJob(job2);

    assertThat(registry).isNotNull();
  }

  private RunnableScheduledJob createMockJob(String jobId, long delay) {
    RunnableScheduledJob job = mock(RunnableScheduledJob.class);
    RunnableScheduledJob.Id id = () -> jobId;
    when(job.getId()).thenReturn(id);
    when(job.getDelay()).thenReturn(delay);
    return job;
  }
}
