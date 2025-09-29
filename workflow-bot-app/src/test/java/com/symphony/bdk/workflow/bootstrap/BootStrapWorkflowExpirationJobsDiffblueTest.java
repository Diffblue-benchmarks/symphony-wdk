package com.symphony.bdk.workflow.bootstrap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.expiration.WorkflowExpirationPlanner;
import com.symphony.bdk.workflow.management.repository.WorkflowExpirationJobRepository;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BootStrapWorkflowExpirationJobsDiffblueTest {
  /**
   * Test {@link BootStrapWorkflowExpirationJobs#setupWorkflowExpirationJobs()}.
   *
   * <p>Method under test: {@link BootStrapWorkflowExpirationJobs#setupWorkflowExpirationJobs()}
   */
  @Test
  @DisplayName("Test setupWorkflowExpirationJobs()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BootStrapWorkflowExpirationJobs.setupWorkflowExpirationJobs()"})
  void testSetupWorkflowExpirationJobs() {
    // Arrange
    WorkflowExpirationJobRepository expirationJobRepository =
        mock(WorkflowExpirationJobRepository.class);
    when(expirationJobRepository.findAll()).thenReturn(new ArrayList<>());
    BootStrapWorkflowExpirationJobs bootStrapWorkflowExpirationJobs =
        new BootStrapWorkflowExpirationJobs(
            expirationJobRepository, mock(WorkflowExpirationPlanner.class));

    // Act
    bootStrapWorkflowExpirationJobs.setupWorkflowExpirationJobs();

    // Assert
    verify(expirationJobRepository).findAll();
  }
}
