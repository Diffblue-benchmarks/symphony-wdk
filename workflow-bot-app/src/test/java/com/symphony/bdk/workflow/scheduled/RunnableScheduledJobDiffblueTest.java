package com.symphony.bdk.workflow.scheduled;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.scheduled.RunnableScheduledJob.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RunnableScheduledJobDiffblueTest {
  /**
   * Test {@link RunnableScheduledJob#run()}.
   *
   * <p>Method under test: {@link RunnableScheduledJob#run()}
   */
  @Test
  @DisplayName("Test run()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RunnableScheduledJob.run()"})
  void testRun() {
    // Arrange
    Runnable job = mock(Runnable.class);
    doNothing().when(job).run();

    // Act
    new RunnableScheduledJob(mock(Id.class), 1L, job).run();

    // Assert
    verify(job).run();
  }
}
