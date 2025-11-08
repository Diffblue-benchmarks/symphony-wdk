package com.symphony.bdk.workflow.converter;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.camunda.monitoring.converter.ActivityDomainConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;
import java.util.ArrayList;
import java.util.List;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivityDomainConverter.class})
@ExtendWith(SpringExtension.class)
class ConverterDiffblueTest {
  @Autowired
  private Converter<HistoricActivityInstanceEntity, ActivityInstanceDomain> converter;

  /**
   * Method under test: {@link Converter#getSourceClass()}
   */
  @Test
  void testGetSourceClass() {
    // Arrange
    Converter<HistoricActivityInstanceEntity, ActivityInstanceDomain> converter2 = mock(Converter.class);
    Class<HistoricActivityInstanceEntity> forNameResult = HistoricActivityInstanceEntity.class;
    when(converter2.getSourceClass()).thenReturn(forNameResult);

    // Act
    converter2.getSourceClass();

    // Assert
    verify(converter2).getSourceClass();
  }

  /**
   * Method under test: {@link Converter#getTargetClass()}
   */
  @Test
  void testGetTargetClass() {
    // Arrange
    Converter<HistoricActivityInstanceEntity, ActivityInstanceDomain> converter2 = mock(Converter.class);
    Class<ActivityInstanceDomain> forNameResult = ActivityInstanceDomain.class;
    when(converter2.getTargetClass()).thenReturn(forNameResult);

    // Act
    converter2.getTargetClass();

    // Assert
    verify(converter2).getTargetClass();
  }

  /**
   * Method under test: {@link Converter#applyCollection(List)}
   */
  @Test
  void testApplyCollection() {
    // Arrange
    Converter<HistoricActivityInstanceEntity, ActivityInstanceDomain> converter2 = mock(Converter.class);
    when(converter2.applyCollection(Mockito.<List<HistoricActivityInstanceEntity>>any())).thenReturn(new ArrayList<>());

    // Act
    converter2.applyCollection(new ArrayList<>());

    // Assert
    verify(converter2).applyCollection(isA(List.class));
  }

  /**
   * Method under test: {@link Converter#applyCollection(List)}
   */
  @Test
  void testApplyCollection2() {
    // Arrange
    Converter<HistoricActivityInstanceEntity, ActivityInstanceDomain> converter2 = mock(Converter.class);
    when(converter2.applyCollection(Mockito.<List<HistoricActivityInstanceEntity>>any())).thenReturn(new ArrayList<>());

    ArrayList<HistoricActivityInstanceEntity> source = new ArrayList<>();
    source.add(new HistoricActivityInstanceEntity());

    // Act
    converter2.applyCollection(source);

    // Assert
    verify(converter2).applyCollection(isA(List.class));
  }

  /**
   * Method under test: {@link Converter#applyCollection(List)}
   */
  @Test
  void testApplyCollection3() {
    // Arrange
    Converter<HistoricActivityInstanceEntity, ActivityInstanceDomain> converter2 = mock(Converter.class);
    when(converter2.applyCollection(Mockito.<List<HistoricActivityInstanceEntity>>any())).thenReturn(new ArrayList<>());

    ArrayList<HistoricActivityInstanceEntity> source = new ArrayList<>();
    source.add(new HistoricActivityInstanceEntity());
    source.add(new HistoricActivityInstanceEntity());

    // Act
    converter2.applyCollection(source);

    // Assert
    verify(converter2).applyCollection(isA(List.class));
  }
}
