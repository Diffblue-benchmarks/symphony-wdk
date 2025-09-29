package com.symphony.bdk.workflow.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.camunda.monitoring.converter.ActivityDomainConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;
import java.util.ArrayList;
import java.util.List;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivityDomainConverter.class})
@ExtendWith(SpringExtension.class)
class ConverterDiffblueTest {
  @Autowired private Converter<HistoricActivityInstanceEntity, ActivityInstanceDomain> converter;

  /**
   * Test {@link Converter#getSourceClass()}.
   *
   * <p>Method under test: {@link Converter#getSourceClass()}
   */
  @Test
  @DisplayName("Test getSourceClass()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class Converter.getSourceClass()"})
  void testGetSourceClass() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    Class<HistoricActivityInstanceEntity> actualSourceClass =
        new ActivityDomainConverter().getSourceClass();

    // Assert
    Class<HistoricActivityInstanceEntity> expectedSourceClass =
        HistoricActivityInstanceEntity.class;
    assertEquals(expectedSourceClass, actualSourceClass);
  }

  /**
   * Test {@link Converter#getTargetClass()}.
   *
   * <p>Method under test: {@link Converter#getTargetClass()}
   */
  @Test
  @DisplayName("Test getTargetClass()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class Converter.getTargetClass()"})
  void testGetTargetClass() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    Class<ActivityInstanceDomain> actualTargetClass =
        new ActivityDomainConverter().getTargetClass();

    // Assert
    Class<ActivityInstanceDomain> expectedTargetClass = ActivityInstanceDomain.class;
    assertEquals(expectedTargetClass, actualTargetClass);
  }

  /**
   * Test {@link Converter#applyCollection(List)}.
   *
   * <p>Method under test: {@link Converter#applyCollection(List)}
   */
  @Test
  @DisplayName("Test applyCollection(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List Converter.applyCollection(List)"})
  void testApplyCollection() {
    // Arrange
    Converter<HistoricActivityInstanceEntity, ActivityInstanceDomain> converter =
        mock(Converter.class);
    when(converter.applyCollection(Mockito.<List<HistoricActivityInstanceEntity>>any()))
        .thenReturn(new ArrayList<>());

    ArrayList<HistoricActivityInstanceEntity> source = new ArrayList<>();
    source.add(new HistoricActivityInstanceEntity());

    // Act
    converter.applyCollection(source);

    // Assert
    verify(converter).applyCollection(isA(List.class));
  }

  /**
   * Test {@link Converter#applyCollection(List)}.
   *
   * <p>Method under test: {@link Converter#applyCollection(List)}
   */
  @Test
  @DisplayName("Test applyCollection(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List Converter.applyCollection(List)"})
  void testApplyCollection2() {
    // Arrange
    Converter<HistoricActivityInstanceEntity, ActivityInstanceDomain> converter =
        mock(Converter.class);
    when(converter.applyCollection(Mockito.<List<HistoricActivityInstanceEntity>>any()))
        .thenReturn(new ArrayList<>());

    ArrayList<HistoricActivityInstanceEntity> source = new ArrayList<>();
    source.add(new HistoricActivityInstanceEntity());
    source.add(new HistoricActivityInstanceEntity());

    // Act
    converter.applyCollection(source);

    // Assert
    verify(converter).applyCollection(isA(List.class));
  }
}
