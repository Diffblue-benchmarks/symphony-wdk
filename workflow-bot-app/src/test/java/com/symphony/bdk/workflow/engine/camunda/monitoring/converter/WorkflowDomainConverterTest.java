package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowDomain;

import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.junit.jupiter.api.Test;

class WorkflowDomainConverterTest {

  @Test
  void shouldConvertProcessDefinitionWhenVersionTagPresent() {
    // Arrange
    WorkflowDomainConverter converter = new WorkflowDomainConverter();
    ProcessDefinitionEntity processDefinition = mock(ProcessDefinitionEntity.class);

    when(processDefinition.getId()).thenReturn("workflow-id-123");
    when(processDefinition.getName()).thenReturn("Test Workflow");
    when(processDefinition.getVersionTag()).thenReturn("42");

    // Act
    WorkflowDomain result = converter.apply(processDefinition);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("workflow-id-123");
    assertThat(result.getName()).isEqualTo("Test Workflow");
    assertThat(result.getVersion()).isEqualTo(42L);
  }

  @Test
  void shouldConvertProcessDefinitionWhenVersionTagNull() {
    // Arrange
    WorkflowDomainConverter converter = new WorkflowDomainConverter();
    ProcessDefinitionEntity processDefinition = mock(ProcessDefinitionEntity.class);

    when(processDefinition.getId()).thenReturn("workflow-id-456");
    when(processDefinition.getName()).thenReturn("Another Workflow");
    when(processDefinition.getVersionTag()).thenReturn(null);

    // Act
    WorkflowDomain result = converter.apply(processDefinition);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("workflow-id-456");
    assertThat(result.getName()).isEqualTo("Another Workflow");
    assertThat(result.getVersion()).isNull();
  }
}
