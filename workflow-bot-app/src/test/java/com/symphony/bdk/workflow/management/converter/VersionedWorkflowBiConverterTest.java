package com.symphony.bdk.workflow.management.converter;

import com.symphony.bdk.workflow.api.v1.dto.SwadlView;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.swadl.v1.Workflow;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VersionedWorkflowBiConverterTest {

  @Test
  void shouldConvertWorkflowAndSwadlViewToVersionedWorkflowWhenDeploymentIdIsPresent() {
    Workflow workflow = new Workflow();
    workflow.setId("my-workflow");
    workflow.setVersion(42L);

    SwadlView swadlView = SwadlView.builder()
        .swadl("swadl-content")
        .description("my description")
        .createdBy(123L)
        .build();

    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter("deploy-id-1");

    VersionedWorkflow result = converter.apply(workflow, swadlView);

    assertThat(result).isNotNull();
    assertThat(result.getWorkflowId()).isEqualTo("my-workflow");
    assertThat(result.getVersion()).isEqualTo(42L);
    assertThat(result.getDeploymentId()).isEqualTo("deploy-id-1");
    assertThat(result.getSwadl()).isEqualTo("swadl-content");
    assertThat(result.getDescription()).isEqualTo("my description");
    assertThat(result.getCreatedBy()).isEqualTo(123L);
    assertThat(result.getPublished()).isTrue();
    assertThat(result.getActive()).isTrue();
  }

  @Test
  void shouldSetActiveToNullAndDeploymentIdToNullWhenDeploymentIdIsAbsent() {
    Workflow workflow = new Workflow();
    workflow.setId("my-workflow");
    workflow.setVersion(1L);

    SwadlView swadlView = SwadlView.builder()
        .swadl("swadl-content")
        .build();

    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter(null);

    VersionedWorkflow result = converter.apply(workflow, swadlView);

    assertThat(result).isNotNull();
    assertThat(result.getDeploymentId()).isNull();
    assertThat(result.getActive()).isFalse();
  }
}
