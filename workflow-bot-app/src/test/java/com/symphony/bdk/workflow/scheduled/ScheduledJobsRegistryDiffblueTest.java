package com.symphony.bdk.workflow.scheduled;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.scheduled.RunnableScheduledJob.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
   * Method under test: {@link ScheduledJobsRegistry#scheduleJob(RunnableScheduledJob)}
   */
  @Test
  @DisplayName("Test scheduleJob(RunnableScheduledJob); given '42'; when Id id() return '42'; then calls id()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ScheduledJobsRegistry.scheduleJob(RunnableScheduledJob)"})
  void testScheduleJob_given42_whenIdIdReturn42_thenCallsId() {
    // Arrange
    ScheduledJobsRegistry scheduledJobsRegistry = new ScheduledJobsRegistry(3);
    Id id = mock(Id.class);
    when(id.id()).thenReturn("42");

    // Act
    scheduledJobsRegistry.scheduleJob(new RunnableScheduledJob(id, 1L, mock(Runnable.class)));

    // Assert
    verify(id).id();
  }
}
