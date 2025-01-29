package com.symphony.bdk.workflow.scheduled;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.scheduled.RunnableScheduledJob.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScheduledJobsRegistryDiffblueTest {
  /**
   * Test {@link ScheduledJobsRegistry#scheduleJob(RunnableScheduledJob)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link Id} {@link Id#id()} return {@code 42}.</li>
   *   <li>Then calls {@link Id#id()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScheduledJobsRegistry#scheduleJob(RunnableScheduledJob)}
   */
  @Test
  @DisplayName("Test scheduleJob(RunnableScheduledJob); given '42'; when Id id() return '42'; then calls id()")
  void testScheduleJob_given42_whenIdIdReturn42_thenCallsId() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ScheduledJobsRegistry scheduledJobsRegistry = new ScheduledJobsRegistry(3);
    RunnableScheduledJob.Id id = mock(RunnableScheduledJob.Id.class);
    when(id.id()).thenReturn("42");

    // Act
    scheduledJobsRegistry.scheduleJob(new RunnableScheduledJob(id, 1L, mock(Runnable.class)));

    // Assert
    verify(id).id();
  }
}
