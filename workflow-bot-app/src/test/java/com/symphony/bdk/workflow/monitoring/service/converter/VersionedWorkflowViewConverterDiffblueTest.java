package com.symphony.bdk.workflow.monitoring.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VersionedWorkflowViewConverter.class})
@ExtendWith(SpringExtension.class)
class VersionedWorkflowViewConverterDiffblueTest {
  @Autowired
  private VersionedWorkflowViewConverter versionedWorkflowViewConverter;

  /**
   * Test {@link VersionedWorkflowViewConverter#apply(VersionedWorkflow)} with
   * {@code VersionedWorkflow}.
   * <p>
   * Method under test:
   * {@link VersionedWorkflowViewConverter#apply(VersionedWorkflow)}
   */
  @Test
  @DisplayName("Test apply(VersionedWorkflow) with 'VersionedWorkflow'")
  void testApplyWithVersionedWorkflow() {
    // Arrange
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setActive(true);
    workflow.setCreatedBy(1L);
    workflow.setDeploymentId("42");
    workflow.setDescription("The characteristics of someone or something");
    workflow.setEtag(1L);
    workflow.setId("42");
    workflow.setPublished(true);
    workflow.setSwadl("Swadl");
    workflow.setVersion(1L);
    workflow.setWorkflowId("42");

    // Act
    WorkflowView actualApplyResult = versionedWorkflowViewConverter.apply(workflow);

    // Assert
    assertEquals("42", actualApplyResult.getId());
    assertEquals(1L, actualApplyResult.getCreatedBy().longValue());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
  }
}
