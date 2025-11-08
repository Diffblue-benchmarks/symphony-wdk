package com.symphony.bdk.workflow.converter;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.camunda.monitoring.converter.WorkflowInstDomainVersionConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowInstDomainVersionConverter.class})
@ExtendWith(SpringExtension.class)
class BiConverterDiffblueTest {
  @Autowired
  private BiConverter<HistoricProcessInstanceEntity, Map<String, String>, WorkflowInstanceDomain> biConverter;

  /**
   * Method under test: {@link BiConverter#getSourceClass()}
   */
  @Test
  void testGetSourceClass() {
    // Arrange
    BiConverter<HistoricProcessInstanceEntity, Map<String, String>, WorkflowInstanceDomain> biConverter2 = mock(
        BiConverter.class);
    Class<HistoricProcessInstanceEntity> forNameResult = HistoricProcessInstanceEntity.class;
    when(biConverter2.getSourceClass()).thenReturn(forNameResult);

    // Act
    biConverter2.getSourceClass();

    // Assert
    verify(biConverter2).getSourceClass();
  }

  /**
   * Method under test: {@link BiConverter#getTargetClass()}
   */
  @Test
  void testGetTargetClass() {
    // Arrange
    BiConverter<HistoricProcessInstanceEntity, Map<String, String>, WorkflowInstanceDomain> biConverter2 = mock(
        BiConverter.class);
    Class<WorkflowInstanceDomain> forNameResult = WorkflowInstanceDomain.class;
    when(biConverter2.getTargetClass()).thenReturn(forNameResult);

    // Act
    biConverter2.getTargetClass();

    // Assert
    verify(biConverter2).getTargetClass();
  }

  /**
   * Method under test: {@link BiConverter#applyCollection(List, Object)}
   */
  @Test
  void testApplyCollection() {
    // Arrange
    BiConverter<HistoricProcessInstanceEntity, Map<String, String>, WorkflowInstanceDomain> biConverter2 = mock(
        BiConverter.class);
    when(biConverter2.applyCollection(Mockito.<List<HistoricProcessInstanceEntity>>any(),
        Mockito.<Map<String, String>>any())).thenReturn(new ArrayList<>());
    ArrayList<HistoricProcessInstanceEntity> source = new ArrayList<>();

    // Act
    biConverter2.applyCollection(source, new HashMap<>());

    // Assert
    verify(biConverter2).applyCollection(isA(List.class), isA(Map.class));
  }

  /**
   * Method under test: {@link BiConverter#applyCollection(List, Object)}
   */
  @Test
  void testApplyCollection2() {
    // Arrange
    BiConverter<HistoricProcessInstanceEntity, Map<String, String>, WorkflowInstanceDomain> biConverter2 = mock(
        BiConverter.class);
    when(biConverter2.applyCollection(Mockito.<List<HistoricProcessInstanceEntity>>any(),
        Mockito.<Map<String, String>>any())).thenReturn(new ArrayList<>());

    ArrayList<HistoricProcessInstanceEntity> source = new ArrayList<>();
    source.add(new HistoricProcessInstanceEntity());

    // Act
    biConverter2.applyCollection(source, new HashMap<>());

    // Assert
    verify(biConverter2).applyCollection(isA(List.class), isA(Map.class));
  }

  /**
   * Method under test: {@link BiConverter#applyCollection(List, Object)}
   */
  @Test
  void testApplyCollection3() {
    // Arrange
    BiConverter<HistoricProcessInstanceEntity, Map<String, String>, WorkflowInstanceDomain> biConverter2 = mock(
        BiConverter.class);
    when(biConverter2.applyCollection(Mockito.<List<HistoricProcessInstanceEntity>>any(),
        Mockito.<Map<String, String>>any())).thenReturn(new ArrayList<>());

    ArrayList<HistoricProcessInstanceEntity> source = new ArrayList<>();
    source.add(new HistoricProcessInstanceEntity());
    source.add(new HistoricProcessInstanceEntity());

    // Act
    biConverter2.applyCollection(source, new HashMap<>());

    // Assert
    verify(biConverter2).applyCollection(isA(List.class), isA(Map.class));
  }
}
