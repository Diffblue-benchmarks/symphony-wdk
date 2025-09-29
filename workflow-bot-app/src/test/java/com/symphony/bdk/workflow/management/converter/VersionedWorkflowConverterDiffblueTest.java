package com.symphony.bdk.workflow.management.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VersionedWorkflowConverter.class})
@ExtendWith(SpringExtension.class)
class VersionedWorkflowConverterDiffblueTest {
  @Autowired private VersionedWorkflowConverter versionedWorkflowConverter;

  /**
   * Test {@link VersionedWorkflowConverter#apply(VersionedWorkflow)} with {@code
   * VersionedWorkflow}.
   *
   * <p>Method under test: {@link VersionedWorkflowConverter#apply(VersionedWorkflow)}
   */
  @Test
  @DisplayName("Test apply(VersionedWorkflow) with 'VersionedWorkflow'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"VersionedWorkflowView VersionedWorkflowConverter.apply(VersionedWorkflow)"})
  void testApplyWithVersionedWorkflow() {
    // Arrange
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");

    // Act
    VersionedWorkflowView actualApplyResult = versionedWorkflowConverter.apply(versionedWorkflow);

    // Assert
    assertEquals("42", actualApplyResult.getDeploymentId());
    assertEquals("42", actualApplyResult.getId());
    assertEquals("42", actualApplyResult.getWorkflowId());
    assertEquals("Swadl", actualApplyResult.getSwadl());
    assertEquals("The characteristics of someone or something", actualApplyResult.getDescription());
    assertEquals(1L, actualApplyResult.getCreatedBy().longValue());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
    assertTrue(actualApplyResult.getActive());
    assertTrue(actualApplyResult.getPublished());
  }
}
