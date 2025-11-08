package com.symphony.bdk.workflow.scheduled;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;

class RunnableScheduledJobDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RunnableScheduledJob#getDelay()}
   *   <li>{@link RunnableScheduledJob#getId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    RunnableScheduledJob runnableScheduledJob = new RunnableScheduledJob(mock(RunnableScheduledJob.Id.class), 1L,
        mock(Runnable.class));

    // Act
    long actualDelay = runnableScheduledJob.getDelay();
    runnableScheduledJob.getId();

    // Assert
    assertEquals(1L, actualDelay);
  }

  /**
   * Method under test: {@link RunnableScheduledJob#run()}
   */
  @Test
  void testRun() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Runnable job = mock(Runnable.class);
    doNothing().when(job).run();

    // Act
    (new RunnableScheduledJob(mock(RunnableScheduledJob.Id.class), 1L, job)).run();

    // Assert
    verify(job).run();
  }

  /**
   * Method under test:
   * {@link RunnableScheduledJob#RunnableScheduledJob(RunnableScheduledJob.Id, long, Runnable)}
   */
  @Test
  void testNewRunnableScheduledJob() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    RunnableScheduledJob.Id id = mock(RunnableScheduledJob.Id.class);

    // Act
    RunnableScheduledJob actualRunnableScheduledJob = new RunnableScheduledJob(id, 1L, mock(Runnable.class));

    // Assert
    assertEquals(1L, actualRunnableScheduledJob.getDelay());
    assertSame(id, actualRunnableScheduledJob.getId());
  }
}
