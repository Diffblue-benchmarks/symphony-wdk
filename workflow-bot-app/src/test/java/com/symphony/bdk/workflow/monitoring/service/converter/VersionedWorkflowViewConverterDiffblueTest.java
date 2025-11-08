package com.symphony.bdk.workflow.monitoring.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VersionedWorkflowViewConverter.class})
@ExtendWith(SpringExtension.class)
class VersionedWorkflowViewConverterDiffblueTest {
  @Autowired
  private VersionedWorkflowViewConverter versionedWorkflowViewConverter;

  /**
   * Method under test:
   * {@link VersionedWorkflowViewConverter#apply(VersionedWorkflow)}
   */
  @Test
  void testApply() {
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

  /**
   * Method under test:
   * {@link VersionedWorkflowViewConverter#apply(VersionedWorkflow)}
   */
  @Test
  void testApply2() {
    // Arrange
    VersionedWorkflow workflow = mock(VersionedWorkflow.class);
    when(workflow.getCreatedBy()).thenReturn(1L);
    when(workflow.getVersion()).thenReturn(1L);
    when(workflow.getWorkflowId()).thenReturn("42");
    doNothing().when(workflow).setActive(Mockito.<Boolean>any());
    doNothing().when(workflow).setCreatedBy(Mockito.<Long>any());
    doNothing().when(workflow).setDeploymentId(Mockito.<String>any());
    doNothing().when(workflow).setDescription(Mockito.<String>any());
    doNothing().when(workflow).setEtag(Mockito.<Long>any());
    doNothing().when(workflow).setId(Mockito.<String>any());
    doNothing().when(workflow).setPublished(Mockito.<Boolean>any());
    doNothing().when(workflow).setSwadl(Mockito.<String>any());
    doNothing().when(workflow).setVersion(Mockito.<Long>any());
    doNothing().when(workflow).setWorkflowId(Mockito.<String>any());
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
    verify(workflow).getCreatedBy();
    verify(workflow).getVersion();
    verify(workflow).getWorkflowId();
    verify(workflow).setActive(eq(true));
    verify(workflow).setCreatedBy(eq(1L));
    verify(workflow).setDeploymentId(eq("42"));
    verify(workflow).setDescription(eq("The characteristics of someone or something"));
    verify(workflow).setEtag(eq(1L));
    verify(workflow).setId(eq("42"));
    verify(workflow).setPublished(eq(true));
    verify(workflow).setSwadl(eq("Swadl"));
    verify(workflow).setVersion(eq(1L));
    verify(workflow).setWorkflowId(eq("42"));
    assertEquals("42", actualApplyResult.getId());
    assertEquals(1L, actualApplyResult.getCreatedBy().longValue());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
  }
}
