package com.symphony.bdk.workflow.configuration;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WorkflowDeployerDiffblueTest {
  @InjectMocks private WorkflowDeployer workflowDeployer;

  /**
   * Test {@link WorkflowDeployer#addAllWorkflowsFromFolder(Path)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowDeployer#addAllWorkflowsFromFolder(Path)}
   */
  @Test
  @DisplayName("Test addAllWorkflowsFromFolder(Path); then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowDeployer.addAllWorkflowsFromFolder(Path)"})
  void testAddAllWorkflowsFromFolder_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            workflowDeployer.addAllWorkflowsFromFolder(
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt")));
  }
}
