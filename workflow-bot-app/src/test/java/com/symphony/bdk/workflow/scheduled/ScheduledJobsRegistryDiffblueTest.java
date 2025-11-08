package com.symphony.bdk.workflow.scheduled;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;

class ScheduledJobsRegistryDiffblueTest {
  /**
   * Method under test:
   * {@link ScheduledJobsRegistry#scheduleJob(RunnableScheduledJob)}
   */
  @Test
  void testScheduleJob() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ScheduledJobsRegistry scheduledJobsRegistry = new ScheduledJobsRegistry(3);
    RunnableScheduledJob.Id id = mock(RunnableScheduledJob.Id.class);
    when(id.id()).thenReturn("42");

    // Act
    scheduledJobsRegistry.scheduleJob(new RunnableScheduledJob(id, 1L, mock(Runnable.class)));

    // Assert that nothing has changed
    verify(id).id();
  }
}
