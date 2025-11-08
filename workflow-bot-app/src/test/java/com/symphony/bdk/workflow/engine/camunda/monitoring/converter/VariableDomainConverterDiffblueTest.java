package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import org.camunda.bpm.engine.history.HistoricDetail;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
   * Test {@link VariableDomainConverter#apply(HistoricDetail)} with {@code HistoricDetail}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>Then return UpdateTime Nano is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableDomainConverter#apply(HistoricDetail)}
   */
  @Test
  @DisplayName("Test apply(HistoricDetail) with 'HistoricDetail'; given one; then return UpdateTime Nano is zero")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"VariablesDomain VariableDomainConverter.apply(HistoricDetail)"})
  void testApplyWithHistoricDetail_givenOne_thenReturnUpdateTimeNanoIsZero() {
    // Arrange
    HistoricDetailVariableInstanceUpdateEntity historicVariableUpdate = mock(
        HistoricDetailVariableInstanceUpdateEntity.class);
    when(historicVariableUpdate.getRevision()).thenReturn(1);
    when(historicVariableUpdate.getValue()).thenReturn(new HashMap<>());
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
    assertTrue(actualApplyResult.getOutputs().isEmpty());
  }
}
