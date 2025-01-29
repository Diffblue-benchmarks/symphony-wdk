package com.symphony.bdk.workflow.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.camunda.monitoring.converter.ActivityDomainConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;
import java.util.ArrayList;
import java.util.List;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.junit.jupiter.api.DisplayName;
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
   * Test {@link Converter#getSourceClass()}.
   * <p>
   * Method under test: {@link Converter#getSourceClass()}
   */
  @Test
  @DisplayName("Test getSourceClass()")
  void testGetSourceClass() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    Class<HistoricActivityInstanceEntity> actualSourceClass = (new ActivityDomainConverter()).getSourceClass();

    // Assert
    Class<HistoricActivityInstanceEntity> expectedSourceClass = HistoricActivityInstanceEntity.class;
    assertEquals(expectedSourceClass, actualSourceClass);
  }

  /**
   * Test {@link Converter#getTargetClass()}.
   * <p>
   * Method under test: {@link Converter#getTargetClass()}
   */
  @Test
  @DisplayName("Test getTargetClass()")
  void testGetTargetClass() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    Class<ActivityInstanceDomain> actualTargetClass = (new ActivityDomainConverter()).getTargetClass();

    // Assert
    Class<ActivityInstanceDomain> expectedTargetClass = ActivityInstanceDomain.class;
    assertEquals(expectedTargetClass, actualTargetClass);
  }

  /**
   * Test {@link Converter#applyCollection(List)}.
   * <ul>
   *   <li>Given {@link HistoricActivityInstanceEntity} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Converter#applyCollection(List)}
   */
  @Test
  @DisplayName("Test applyCollection(List); given HistoricActivityInstanceEntity (default constructor)")
  void testApplyCollection_givenHistoricActivityInstanceEntity() {
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
   * Test {@link Converter#applyCollection(List)}.
   * <ul>
   *   <li>Given {@link HistoricActivityInstanceEntity} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Converter#applyCollection(List)}
   */
  @Test
  @DisplayName("Test applyCollection(List); given HistoricActivityInstanceEntity (default constructor)")
  void testApplyCollection_givenHistoricActivityInstanceEntity2() {
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

  /**
   * Test {@link Converter#applyCollection(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Converter#applyCollection(List)}
   */
  @Test
  @DisplayName("Test applyCollection(List); when ArrayList()")
  void testApplyCollection_whenArrayList() {
    // Arrange
    Converter<HistoricActivityInstanceEntity, ActivityInstanceDomain> converter2 = mock(Converter.class);
    when(converter2.applyCollection(Mockito.<List<HistoricActivityInstanceEntity>>any())).thenReturn(new ArrayList<>());

    // Act
    converter2.applyCollection(new ArrayList<>());

    // Assert
    verify(converter2).applyCollection(isA(List.class));
  }
}
