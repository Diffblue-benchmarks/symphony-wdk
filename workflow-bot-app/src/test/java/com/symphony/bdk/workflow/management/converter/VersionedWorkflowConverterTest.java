package com.symphony.bdk.workflow.management.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VersionedWorkflowConverterTest {

  private VersionedWorkflowConverter converter;

  @BeforeEach
  void setUp() {
    converter = new VersionedWorkflowConverter();
  }

  @Test
  void shouldConvertVersionedWorkflowToView() {
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("test-id-123");
    versionedWorkflow.setWorkflowId("workflow-id-456");
    versionedWorkflow.setVersion(5L);
    versionedWorkflow.setActive(true);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setDeploymentId("deployment-789");
    versionedWorkflow.setSwadl("id: test-workflow");
    versionedWorkflow.setDescription("Test workflow description");
    versionedWorkflow.setCreatedBy(987654321L);

    VersionedWorkflowView result = converter.apply(versionedWorkflow);

    assertThat(result.getId()).isEqualTo("test-id-123");
    assertThat(result.getWorkflowId()).isEqualTo("workflow-id-456");
    assertThat(result.getVersion()).isEqualTo(5L);
    assertThat(result.getActive()).isTrue();
    assertThat(result.getPublished()).isTrue();
    assertThat(result.getDeploymentId()).isEqualTo("deployment-789");
    assertThat(result.getSwadl()).isEqualTo("id: test-workflow");
    assertThat(result.getDescription()).isEqualTo("Test workflow description");
    assertThat(result.getCreatedBy()).isEqualTo(987654321L);
  }

  @Test
  void shouldConvertWithNullableFields() {
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("id");
    versionedWorkflow.setWorkflowId("workflowId");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setPublished(false);
    versionedWorkflow.setSwadl("swadl");

    VersionedWorkflowView result = converter.apply(versionedWorkflow);

    assertThat(result.getId()).isEqualTo("id");
    assertThat(result.getWorkflowId()).isEqualTo("workflowId");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getActive()).isFalse();
    assertThat(result.getPublished()).isFalse();
    assertThat(result.getDeploymentId()).isNull();
    assertThat(result.getSwadl()).isEqualTo("swadl");
    assertThat(result.getDescription()).isNull();
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void shouldHandleActiveAsNullAndReturnFalse() {
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("test-id");
    versionedWorkflow.setWorkflowId("workflow-id");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("swadl-content");
    versionedWorkflow.setActive(null);

    VersionedWorkflowView result = converter.apply(versionedWorkflow);

    assertThat(result.getActive()).isFalse();
  }
}
