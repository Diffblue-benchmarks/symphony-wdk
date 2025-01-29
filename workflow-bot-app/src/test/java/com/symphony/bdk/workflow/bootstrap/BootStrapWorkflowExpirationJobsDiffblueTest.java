package com.symphony.bdk.workflow.bootstrap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.expiration.WorkflowExpirationPlanner;
import com.symphony.bdk.workflow.management.repository.WorkflowExpirationJobRepository;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BootStrapWorkflowExpirationJobsDiffblueTest {
  /**
   * Test {@link BootStrapWorkflowExpirationJobs#setupWorkflowExpirationJobs()}.
   * <p>
   * Method under test:
   * {@link BootStrapWorkflowExpirationJobs#setupWorkflowExpirationJobs()}
   */
  @Test
  @DisplayName("Test setupWorkflowExpirationJobs()")
  void testSetupWorkflowExpirationJobs() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    WorkflowExpirationJobRepository expirationJobRepository = mock(WorkflowExpirationJobRepository.class);
    when(expirationJobRepository.findAll()).thenReturn(new ArrayList<>());

    // Act
    (new BootStrapWorkflowExpirationJobs(expirationJobRepository, mock(WorkflowExpirationPlanner.class)))
        .setupWorkflowExpirationJobs();

    // Assert
    verify(expirationJobRepository).findAll();
  }
}
