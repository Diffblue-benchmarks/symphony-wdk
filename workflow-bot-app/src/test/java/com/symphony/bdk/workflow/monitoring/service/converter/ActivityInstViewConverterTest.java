package com.symphony.bdk.workflow.monitoring.service.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.symphony.bdk.workflow.api.v1.dto.NodeStateView;
import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;
import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

class ActivityInstViewConverterTest {

  private ActivityInstViewConverter converter;

  @BeforeEach
  void setUp() {
    converter = new ActivityInstViewConverter();
  }

  @Test
  void shouldConvertActivityInstanceToNodeStateView() {
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("key1", "value1");
    outputs.put("key2", 123);

    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(outputs);

    Instant startDate = Instant.parse("2026-03-13T10:00:00Z");
    Instant endDate = Instant.parse("2026-03-13T10:05:00Z");
    Duration duration = Duration.ofMinutes(5);

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-id-123")
        .name("test-activity")
        .procInstId("process-instance-456")
        .workflowId("workflow-id-789")
        .type("serviceTask")
        .startDate(startDate)
        .endDate(endDate)
        .duration(duration)
        .variables(variables)
        .build();

    NodeStateView result = converter.apply(domain);

    assertThat(result.getNodeId()).isEqualTo("test-activity");
    assertThat(result.getInstanceId()).isEqualTo("process-instance-456");
    assertThat(result.getWorkflowId()).isEqualTo("workflow-id-789");
    assertThat(result.getStartDate()).isEqualTo(startDate);
    assertThat(result.getEndDate()).isEqualTo(endDate);
    assertThat(result.getDuration()).isEqualTo(duration);
    assertThat(result.getOutputs()).isEqualTo(outputs);
  }

  @Test
  void shouldConvertWithNullableFields() {
    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .name("activity-name")
        .procInstId("proc-inst-id")
        .workflowId("workflow-id")
        .build();

    NodeStateView result = converter.apply(domain);

    assertThat(result.getNodeId()).isEqualTo("activity-name");
    assertThat(result.getInstanceId()).isEqualTo("proc-inst-id");
    assertThat(result.getWorkflowId()).isEqualTo("workflow-id");
    assertThat(result.getStartDate()).isNull();
    assertThat(result.getEndDate()).isNull();
    assertThat(result.getDuration()).isNull();
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void shouldConvertWithEmptyOutputs() {
    VariablesDomain variables = new VariablesDomain();

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .name("activity-name")
        .procInstId("proc-inst-id")
        .workflowId("workflow-id")
        .variables(variables)
        .build();

    NodeStateView result = converter.apply(domain);

    assertThat(result.getNodeId()).isEqualTo("activity-name");
    assertThat(result.getInstanceId()).isEqualTo("proc-inst-id");
    assertThat(result.getWorkflowId()).isEqualTo("workflow-id");
    assertThat(result.getOutputs()).isEmpty();
  }
}
