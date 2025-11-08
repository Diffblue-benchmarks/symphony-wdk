package com.symphony.bdk.workflow.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.camunda.monitoring.converter.WorkflowInstDomainVersionConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
   * Test {@link BiConverter#getSourceClass()}.
   * <p>
   * Method under test: {@link BiConverter#getSourceClass()}
   */
  @Test
  @DisplayName("Test getSourceClass()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class BiConverter.getSourceClass()"})
  void testGetSourceClass() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    Class<HistoricProcessInstanceEntity> actualSourceClass = (new WorkflowInstDomainVersionConverter())
        .getSourceClass();

    // Assert
    Class<HistoricProcessInstanceEntity> expectedSourceClass = HistoricProcessInstanceEntity.class;
    assertEquals(expectedSourceClass, actualSourceClass);
  }

  /**
   * Test {@link BiConverter#getTargetClass()}.
   * <p>
   * Method under test: {@link BiConverter#getTargetClass()}
   */
  @Test
  @DisplayName("Test getTargetClass()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class BiConverter.getTargetClass()"})
  void testGetTargetClass() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    Class<WorkflowInstanceDomain> actualTargetClass = (new WorkflowInstDomainVersionConverter()).getTargetClass();

    // Assert
    Class<WorkflowInstanceDomain> expectedTargetClass = WorkflowInstanceDomain.class;
    assertEquals(expectedTargetClass, actualTargetClass);
  }

  /**
   * Test {@link BiConverter#applyCollection(List, Object)}.
   * <ul>
   *   <li>Given {@link HistoricProcessInstanceEntity} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BiConverter#applyCollection(List, Object)}
   */
  @Test
  @DisplayName("Test applyCollection(List, Object); given HistoricProcessInstanceEntity (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BiConverter.applyCollection(List, Object)"})
  void testApplyCollection_givenHistoricProcessInstanceEntity() {
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
   * Test {@link BiConverter#applyCollection(List, Object)}.
   * <ul>
   *   <li>Given {@link HistoricProcessInstanceEntity} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BiConverter#applyCollection(List, Object)}
   */
  @Test
  @DisplayName("Test applyCollection(List, Object); given HistoricProcessInstanceEntity (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BiConverter.applyCollection(List, Object)"})
  void testApplyCollection_givenHistoricProcessInstanceEntity2() {
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

  /**
   * Test {@link BiConverter#applyCollection(List, Object)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BiConverter#applyCollection(List, Object)}
   */
  @Test
  @DisplayName("Test applyCollection(List, Object); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BiConverter.applyCollection(List, Object)"})
  void testApplyCollection_whenArrayList() {
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
}
