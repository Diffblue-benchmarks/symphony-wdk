package com.symphony.bdk.workflow.scheduled;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.scheduled.RunnableScheduledJob.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RunnableScheduledJobDiffblueTest {
  /**
   * Test {@link RunnableScheduledJob#run()}.
   * <p>
   * Method under test: {@link RunnableScheduledJob#run()}
   */
  @Test
  @DisplayName("Test run()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void RunnableScheduledJob.run()"})
  void testRun() {
    // Arrange
    Runnable job = mock(Runnable.class);
    doNothing().when(job).run();

    // Act
    (new RunnableScheduledJob(mock(Id.class), 1L, job)).run();

    // Assert
    verify(job).run();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RunnableScheduledJob#getDelay()}
   *   <li>{@link RunnableScheduledJob#getId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long RunnableScheduledJob.getDelay()", "Id RunnableScheduledJob.getId()"})
  void testGettersAndSetters() {
    // Arrange
    RunnableScheduledJob runnableScheduledJob = new RunnableScheduledJob(mock(Id.class), 1L, mock(Runnable.class));

    // Act
    long actualDelay = runnableScheduledJob.getDelay();
    runnableScheduledJob.getId();

    // Assert
    assertEquals(1L, actualDelay);
  }

  /**
   * Test {@link RunnableScheduledJob#RunnableScheduledJob(Id, long, Runnable)}.
   * <p>
   * Method under test: {@link RunnableScheduledJob#RunnableScheduledJob(Id, long, Runnable)}
   */
  @Test
  @DisplayName("Test new RunnableScheduledJob(Id, long, Runnable)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void RunnableScheduledJob.<init>(Id, long, Runnable)"})
  void testNewRunnableScheduledJob() {
    // Arrange
    Id id = mock(Id.class);

    // Act
    RunnableScheduledJob actualRunnableScheduledJob = new RunnableScheduledJob(id, 1L, mock(Runnable.class));

    // Assert
    assertEquals(1L, actualRunnableScheduledJob.getDelay());
    assertSame(id, actualRunnableScheduledJob.getId());
  }
}
