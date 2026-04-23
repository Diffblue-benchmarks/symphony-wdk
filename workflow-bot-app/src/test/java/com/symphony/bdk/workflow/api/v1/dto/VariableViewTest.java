package com.symphony.bdk.workflow.api.v1.dto;

import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class VariableViewTest {

  @Test
  void shouldSetFieldsWhenDomainIsNotNull() {
    VariablesDomain domain = new VariablesDomain();
    Map<String, Object> outputs = Collections.singletonMap("key", "value");
    domain.setOutputs(outputs);
    domain.setRevision(3);
    Instant updateTime = Instant.now();
    domain.setUpdateTime(updateTime);

    VariableView view = new VariableView(domain);

    assertThat(view.getOutputs()).isEqualTo(outputs);
    assertThat(view.getRevision()).isEqualTo(3);
    assertThat(view.getUpdateTime()).isEqualTo(updateTime);
  }

  @Test
  void shouldLeaveFieldsDefaultWhenDomainIsNull() {
    VariableView view = new VariableView(null);

    assertThat(view.getOutputs()).isNull();
    assertThat(view.getRevision()).isZero();
    assertThat(view.getUpdateTime()).isNull();
  }
}
