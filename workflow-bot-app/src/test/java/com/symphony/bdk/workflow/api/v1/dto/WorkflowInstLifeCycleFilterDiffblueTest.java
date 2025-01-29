package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertSame;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WorkflowInstLifeCycleFilterDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link WorkflowInstLifeCycleFilter#WorkflowInstLifeCycleFilter(Instant, Instant, Instant, Instant)}
   *   <li>{@link WorkflowInstLifeCycleFilter#getFinishedAfter()}
   *   <li>{@link WorkflowInstLifeCycleFilter#getFinishedBefore()}
   *   <li>{@link WorkflowInstLifeCycleFilter#getStartedAfter()}
   *   <li>{@link WorkflowInstLifeCycleFilter#getStartedBefore()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    Instant startedBefore = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Instant startedAfter = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Instant finishedBefore = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    WorkflowInstLifeCycleFilter actualWorkflowInstLifeCycleFilter = new WorkflowInstLifeCycleFilter(startedBefore,
        startedAfter, finishedBefore, LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    Instant actualFinishedAfter = actualWorkflowInstLifeCycleFilter.getFinishedAfter();
    Instant actualFinishedBefore = actualWorkflowInstLifeCycleFilter.getFinishedBefore();
    Instant actualStartedAfter = actualWorkflowInstLifeCycleFilter.getStartedAfter();
    Instant actualStartedBefore = actualWorkflowInstLifeCycleFilter.getStartedBefore();

    // Assert
    Instant instant = actualStartedBefore.EPOCH;
    assertSame(instant, actualFinishedAfter);
    assertSame(instant, actualFinishedBefore);
    assertSame(instant, actualStartedAfter);
    assertSame(instant, actualStartedBefore);
  }
}
