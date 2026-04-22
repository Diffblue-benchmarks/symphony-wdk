package com.symphony.bdk.workflow.management.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.symphony.bdk.workflow.api.v1.dto.SwadlView;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

class VersionedWorkflowBiConverterTest {

  @Test
  void apply_withNonNullDeploymentId_setsActiveTrue() {
    // Arrange
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter("deploy-123");

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("my-workflow");
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    workflow.setProperties(properties);

    SwadlView swadlView = SwadlView.builder()
        .swadl("swadl-content")
        .description("a description")
        .createdBy(42L)
        .build();

    // Act
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Assert
    assertThat(result.getWorkflowId()).isEqualTo("my-workflow");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getDeploymentId()).isEqualTo("deploy-123");
    assertThat(result.getSwadl()).isEqualTo("swadl-content");
    assertThat(result.getDescription()).isEqualTo("a description");
    assertThat(result.getCreatedBy()).isEqualTo(42L);
    assertThat(result.getPublished()).isTrue();
    assertThat(result.getActive()).isTrue();
  }

  @Test
  void apply_withNullDeploymentId_setsActiveNull() {
    // Arrange
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter(null);

    Properties properties = new Properties();
    properties.setPublish(false);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("my-workflow");
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(2L);
    workflow.setProperties(properties);

    SwadlView swadlView = SwadlView.builder()
        .swadl("swadl-content")
        .description(null)
        .createdBy(null)
        .build();

    // Act
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Assert
    assertThat(result.getWorkflowId()).isEqualTo("my-workflow");
    assertThat(result.getVersion()).isEqualTo(2L);
    assertThat(result.getDeploymentId()).isNull();
    assertThat(result.getSwadl()).isEqualTo("swadl-content");
    assertThat(result.getDescription()).isNull();
    assertThat(result.getCreatedBy()).isNull();
    assertThat(result.getPublished()).isFalse();
    assertThat(result.getActive()).isFalse();
  }
}
