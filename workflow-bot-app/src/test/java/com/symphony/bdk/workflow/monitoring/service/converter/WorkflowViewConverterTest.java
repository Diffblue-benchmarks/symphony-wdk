package com.symphony.bdk.workflow.monitoring.service.converter;

import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowDomain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkflowViewConverterTest {

  private WorkflowViewConverter converter;

  @BeforeEach
  void setUp() {
    converter = new WorkflowViewConverter();
  }

  @Test
  void shouldMapWorkflowDomainToWorkflowViewWhenApplied() {
    // given
    WorkflowDomain domain = WorkflowDomain.builder()
        .id("domain-id")
        .name("my-workflow")
        .version(42L)
        .build();

    // when
    WorkflowView result = converter.apply(domain);

    // then
    assertThat(result.getId()).isEqualTo("my-workflow");
    assertThat(result.getVersion()).isEqualTo(42L);
  }
}
