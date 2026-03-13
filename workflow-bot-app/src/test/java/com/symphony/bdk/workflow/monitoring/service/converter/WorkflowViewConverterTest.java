package com.symphony.bdk.workflow.monitoring.service.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowDomain;

import org.junit.jupiter.api.Test;

class WorkflowViewConverterTest {

  @Test
  void shouldConvertWorkflowDomainToWorkflowView() {
    WorkflowViewConverter converter = new WorkflowViewConverter();

    WorkflowDomain domain = WorkflowDomain.builder()
        .id("domain-id-123")
        .name("test-workflow")
        .version(1L)
        .build();

    WorkflowView result = converter.apply(domain);

    assertThat(result.getId()).isEqualTo("test-workflow");
    assertThat(result.getVersion()).isEqualTo(1L);
  }

  @Test
  void shouldConvertWorkflowDomainWithDifferentVersion() {
    WorkflowViewConverter converter = new WorkflowViewConverter();

    WorkflowDomain domain = WorkflowDomain.builder()
        .id("domain-id-456")
        .name("workflow-v2")
        .version(2L)
        .build();

    WorkflowView result = converter.apply(domain);

    assertThat(result.getId()).isEqualTo("workflow-v2");
    assertThat(result.getVersion()).isEqualTo(2L);
  }

  @Test
  void shouldConvertWorkflowDomainWithNullVersion() {
    WorkflowViewConverter converter = new WorkflowViewConverter();

    WorkflowDomain domain = WorkflowDomain.builder()
        .id("domain-id-789")
        .name("workflow-no-version")
        .version(null)
        .build();

    WorkflowView result = converter.apply(domain);

    assertThat(result.getId()).isEqualTo("workflow-no-version");
    assertThat(result.getVersion()).isNull();
  }
}
