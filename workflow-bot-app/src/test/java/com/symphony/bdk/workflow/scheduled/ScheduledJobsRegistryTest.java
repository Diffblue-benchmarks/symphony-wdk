package com.symphony.bdk.workflow.scheduled;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScheduledJobsRegistryTest {

  @Test
  void shouldCreateRegistryWithGivenPoolSize() {
    // when / then
    assertThatCode(() -> new ScheduledJobsRegistry(2))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldScheduleJobWhenJobIsNotNull() {
    // given
    ScheduledJobsRegistry registry = new ScheduledJobsRegistry(2);
    RunnableScheduledJob job = mock(RunnableScheduledJob.class);
    RunnableScheduledJob.Id id = () -> "test-job-id";
    when(job.getId()).thenReturn(id);
    when(job.getDelay()).thenReturn(1L);

    // when / then
    assertThatCode(() -> registry.scheduleJob(job))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldHandleNullJobWithoutException() {
    // given
    ScheduledJobsRegistry registry = new ScheduledJobsRegistry(2);

    // when / then
    assertThatCode(() -> registry.scheduleJob(null))
        .doesNotThrowAnyException();
  }
}
