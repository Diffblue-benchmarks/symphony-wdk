package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;

import org.camunda.bpm.engine.history.HistoricVariableUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class VariableDomainConverterTest {

  private VariableDomainConverter converter;

  @BeforeEach
  void setUp() {
    converter = new VariableDomainConverter();
  }

  @Test
  void shouldConvertHistoricVariableUpdateToVariablesDomain() {
    HistoricVariableUpdate variableUpdate = createHistoricVariableUpdate();
    Map<String, Object> expectedOutputs = new HashMap<>();
    expectedOutputs.put("key1", "value1");
    expectedOutputs.put("key2", 42);
    when(variableUpdate.getValue()).thenReturn(expectedOutputs);
    when(variableUpdate.getRevision()).thenReturn(5);
    Date updateDate = new Date();
    when(variableUpdate.getTime()).thenReturn(updateDate);

    VariablesDomain result = converter.apply(variableUpdate);

    assertThat(result).isNotNull();
    assertThat(result.getOutputs()).isEqualTo(expectedOutputs);
    assertThat(result.getRevision()).isEqualTo(5);
    assertThat(result.getUpdateTime()).isEqualTo(updateDate.toInstant());
  }

  @Test
  void shouldHandleNullValuesInVariableUpdate() {
    HistoricVariableUpdate variableUpdate = createHistoricVariableUpdate();
    when(variableUpdate.getValue()).thenReturn(null);
    when(variableUpdate.getRevision()).thenReturn(0);
    Date updateDate = new Date();
    when(variableUpdate.getTime()).thenReturn(updateDate);

    VariablesDomain result = converter.apply(variableUpdate);

    assertThat(result).isNotNull();
    assertThat(result.getOutputs()).isNull();
    assertThat(result.getRevision()).isEqualTo(0);
    assertThat(result.getUpdateTime()).isEqualTo(updateDate.toInstant());
  }

  @Test
  void shouldHandleEmptyMapInVariableUpdate() {
    HistoricVariableUpdate variableUpdate = createHistoricVariableUpdate();
    Map<String, Object> emptyMap = new HashMap<>();
    when(variableUpdate.getValue()).thenReturn(emptyMap);
    when(variableUpdate.getRevision()).thenReturn(1);
    Date updateDate = new Date();
    when(variableUpdate.getTime()).thenReturn(updateDate);

    VariablesDomain result = converter.apply(variableUpdate);

    assertThat(result).isNotNull();
    assertThat(result.getOutputs()).isEmpty();
    assertThat(result.getRevision()).isEqualTo(1);
    assertThat(result.getUpdateTime()).isEqualTo(updateDate.toInstant());
  }

  private HistoricVariableUpdate createHistoricVariableUpdate() {
    return Mockito.mock(HistoricVariableUpdate.class);
  }
}
