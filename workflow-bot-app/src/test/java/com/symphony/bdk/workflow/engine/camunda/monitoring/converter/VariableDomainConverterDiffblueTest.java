package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.history.HistoricDetail;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VariableDomainConverter.class})
@ExtendWith(SpringExtension.class)
class VariableDomainConverterDiffblueTest {
  @Autowired
  private VariableDomainConverter variableDomainConverter;

  /**
   * Method under test: {@link VariableDomainConverter#apply(HistoricDetail)}
   */
  @Test
  void testApply() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntity historicVariableUpdate = mock(
        HistoricDetailVariableInstanceUpdateEntity.class);
    when(historicVariableUpdate.getRevision()).thenReturn(1);
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    when(historicVariableUpdate.getValue()).thenReturn(objectObjectMap);
    when(historicVariableUpdate.getTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    VariablesDomain actualApplyResult = variableDomainConverter.apply(historicVariableUpdate);

    // Assert
    verify(historicVariableUpdate).getRevision();
    verify(historicVariableUpdate).getTime();
    verify(historicVariableUpdate).getValue();
    Instant updateTime = actualApplyResult.getUpdateTime();
    assertEquals(0, updateTime.getNano());
    assertEquals(0L, updateTime.getEpochSecond());
    assertEquals(1, actualApplyResult.getRevision());
    Map<String, Object> outputs = actualApplyResult.getOutputs();
    assertTrue(outputs.isEmpty());
    assertSame(objectObjectMap, outputs);
  }
}
