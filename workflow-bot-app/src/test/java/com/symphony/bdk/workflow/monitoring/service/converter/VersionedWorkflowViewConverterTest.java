package com.symphony.bdk.workflow.monitoring.service.converter;

import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VersionedWorkflowViewConverterTest {

  private final VersionedWorkflowViewConverter converter = new VersionedWorkflowViewConverter();

  @Test
  void shouldConvertVersionedWorkflowToWorkflowView() {
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setWorkflowId("workflow-123");
    versionedWorkflow.setVersion(5L);
    versionedWorkflow.setCreatedBy(1001L);

    WorkflowView result = converter.apply(versionedWorkflow);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("workflow-123");
    assertThat(result.getVersion()).isEqualTo(5L);
    assertThat(result.getCreatedBy()).isEqualTo(1001L);
  }

  @Test
  void shouldHandleNullCreatedBy() {
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setWorkflowId("workflow-456");
    versionedWorkflow.setVersion(3L);
    versionedWorkflow.setCreatedBy(null);

    WorkflowView result = converter.apply(versionedWorkflow);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("workflow-456");
    assertThat(result.getVersion()).isEqualTo(3L);
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void shouldConvertWithMinimalData() {
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setWorkflowId("workflow-789");
    versionedWorkflow.setVersion(1L);

    WorkflowView result = converter.apply(versionedWorkflow);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("workflow-789");
    assertThat(result.getVersion()).isEqualTo(1L);
  }
}
