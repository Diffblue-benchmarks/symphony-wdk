package com.symphony.bdk.workflow.management.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VersionedWorkflowConverter.class})
@ExtendWith(SpringExtension.class)
class VersionedWorkflowConverterDiffblueTest {
  @Autowired
  private VersionedWorkflowConverter versionedWorkflowConverter;

  /**
   * Method under test:
   * {@link VersionedWorkflowConverter#apply(VersionedWorkflow)}
   */
  @Test
  void testApply() {
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

  /**
   * Method under test:
   * {@link VersionedWorkflowConverter#apply(VersionedWorkflow)}
   */
  @Test
  void testApply2() {
    // Arrange
    VersionedWorkflow versionedWorkflow = mock(VersionedWorkflow.class);
    when(versionedWorkflow.getActive()).thenReturn(true);
    when(versionedWorkflow.getPublished()).thenReturn(true);
    when(versionedWorkflow.getCreatedBy()).thenReturn(1L);
    when(versionedWorkflow.getVersion()).thenReturn(1L);
    when(versionedWorkflow.getDeploymentId()).thenReturn("42");
    when(versionedWorkflow.getDescription()).thenReturn("The characteristics of someone or something");
    when(versionedWorkflow.getId()).thenReturn("42");
    when(versionedWorkflow.getSwadl()).thenReturn("Swadl");
    when(versionedWorkflow.getWorkflowId()).thenReturn("42");
    doNothing().when(versionedWorkflow).setActive(Mockito.<Boolean>any());
    doNothing().when(versionedWorkflow).setCreatedBy(Mockito.<Long>any());
    doNothing().when(versionedWorkflow).setDeploymentId(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setDescription(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setEtag(Mockito.<Long>any());
    doNothing().when(versionedWorkflow).setId(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setPublished(Mockito.<Boolean>any());
    doNothing().when(versionedWorkflow).setSwadl(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setVersion(Mockito.<Long>any());
    doNothing().when(versionedWorkflow).setWorkflowId(Mockito.<String>any());
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
    verify(versionedWorkflow).getActive();
    verify(versionedWorkflow).getCreatedBy();
    verify(versionedWorkflow).getDeploymentId();
    verify(versionedWorkflow).getDescription();
    verify(versionedWorkflow).getId();
    verify(versionedWorkflow).getPublished();
    verify(versionedWorkflow).getSwadl();
    verify(versionedWorkflow).getVersion();
    verify(versionedWorkflow).getWorkflowId();
    verify(versionedWorkflow).setActive(eq(true));
    verify(versionedWorkflow).setCreatedBy(eq(1L));
    verify(versionedWorkflow).setDeploymentId(eq("42"));
    verify(versionedWorkflow).setDescription(eq("The characteristics of someone or something"));
    verify(versionedWorkflow).setEtag(eq(1L));
    verify(versionedWorkflow).setId(eq("42"));
    verify(versionedWorkflow).setPublished(eq(true));
    verify(versionedWorkflow).setSwadl(eq("Swadl"));
    verify(versionedWorkflow).setVersion(eq(1L));
    verify(versionedWorkflow).setWorkflowId(eq("42"));
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
