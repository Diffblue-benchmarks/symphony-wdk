package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WorkflowViewDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowView#equals(Object)}
   *   <li>{@link WorkflowView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    WorkflowView buildResult = WorkflowView.builder().createdBy(1L).id("42").version(1L).build();
    WorkflowView buildResult2 = WorkflowView.builder().createdBy(1L).id("42").version(1L).build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowView#equals(Object)}
   *   <li>{@link WorkflowView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    WorkflowView.WorkflowViewBuilder workflowViewBuilder = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(WorkflowView.builder());
    WorkflowView buildResult = workflowViewBuilder.createdBy(1L).id("42").version(1L).build();
    WorkflowView.WorkflowViewBuilder workflowViewBuilder2 = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(WorkflowView.builder());
    WorkflowView buildResult2 = workflowViewBuilder2.createdBy(1L).id("42").version(1L).build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowView#equals(Object)}
   *   <li>{@link WorkflowView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    WorkflowView.WorkflowViewBuilder workflowViewBuilder = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder.id(Mockito.<String>any())).thenReturn(WorkflowView.builder());
    WorkflowView.WorkflowViewBuilder workflowViewBuilder2 = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(workflowViewBuilder);
    WorkflowView buildResult = workflowViewBuilder2.createdBy(1L).id("42").version(1L).build();
    WorkflowView.WorkflowViewBuilder workflowViewBuilder3 = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder3.id(Mockito.<String>any())).thenReturn(WorkflowView.builder());
    WorkflowView.WorkflowViewBuilder workflowViewBuilder4 = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder4.createdBy(Mockito.<Long>any())).thenReturn(workflowViewBuilder3);
    WorkflowView buildResult2 = workflowViewBuilder4.createdBy(1L).id("42").version(1L).build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowView#equals(Object)}
   *   <li>{@link WorkflowView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    WorkflowView buildResult = WorkflowView.builder().createdBy(1L).id("42").version(1L).build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Method under test: {@link WorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    WorkflowView.WorkflowViewBuilder workflowViewBuilder = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(WorkflowView.builder());
    WorkflowView buildResult = workflowViewBuilder.createdBy(1L).id("42").version(1L).build();
    WorkflowView buildResult2 = WorkflowView.builder().createdBy(1L).id("42").version(1L).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    WorkflowView.WorkflowViewBuilder workflowViewBuilder = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(WorkflowView.builder());
    WorkflowView buildResult = workflowViewBuilder.createdBy(1L).id("42").version(3L).build();
    WorkflowView buildResult2 = WorkflowView.builder().createdBy(1L).id("42").version(1L).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    WorkflowView.WorkflowViewBuilder workflowViewBuilder = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(WorkflowView.builder());
    WorkflowView buildResult = workflowViewBuilder.createdBy(1L).id("42").version(null).build();
    WorkflowView buildResult2 = WorkflowView.builder().createdBy(1L).id("42").version(1L).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    WorkflowView.WorkflowViewBuilder workflowViewBuilder = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder.id(Mockito.<String>any())).thenReturn(WorkflowView.builder());
    WorkflowView.WorkflowViewBuilder workflowViewBuilder2 = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(workflowViewBuilder);
    WorkflowView buildResult = workflowViewBuilder2.createdBy(1L).id("42").version(1L).build();
    WorkflowView.WorkflowViewBuilder workflowViewBuilder3 = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(WorkflowView.builder());
    WorkflowView buildResult2 = workflowViewBuilder3.createdBy(1L).id("42").version(1L).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    WorkflowView.WorkflowViewBuilder builderResult = WorkflowView.builder();
    builderResult.id("42");
    WorkflowView.WorkflowViewBuilder workflowViewBuilder = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder.id(Mockito.<String>any())).thenReturn(builderResult);
    WorkflowView.WorkflowViewBuilder workflowViewBuilder2 = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(workflowViewBuilder);
    WorkflowView buildResult = workflowViewBuilder2.createdBy(1L).id("42").version(1L).build();
    WorkflowView.WorkflowViewBuilder workflowViewBuilder3 = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder3.id(Mockito.<String>any())).thenReturn(WorkflowView.builder());
    WorkflowView.WorkflowViewBuilder workflowViewBuilder4 = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder4.createdBy(Mockito.<Long>any())).thenReturn(workflowViewBuilder3);
    WorkflowView buildResult2 = workflowViewBuilder4.createdBy(1L).id("42").version(1L).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    WorkflowView.WorkflowViewBuilder builderResult = WorkflowView.builder();
    builderResult.createdBy(1L);
    WorkflowView.WorkflowViewBuilder workflowViewBuilder = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder.id(Mockito.<String>any())).thenReturn(builderResult);
    WorkflowView.WorkflowViewBuilder workflowViewBuilder2 = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(workflowViewBuilder);
    WorkflowView buildResult = workflowViewBuilder2.createdBy(1L).id("42").version(1L).build();
    WorkflowView.WorkflowViewBuilder workflowViewBuilder3 = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder3.id(Mockito.<String>any())).thenReturn(WorkflowView.builder());
    WorkflowView.WorkflowViewBuilder workflowViewBuilder4 = mock(WorkflowView.WorkflowViewBuilder.class);
    when(workflowViewBuilder4.createdBy(Mockito.<Long>any())).thenReturn(workflowViewBuilder3);
    WorkflowView buildResult2 = workflowViewBuilder4.createdBy(1L).id("42").version(1L).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    WorkflowView buildResult = WorkflowView.builder().createdBy(1L).id("42").version(1L).build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Method under test: {@link WorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    WorkflowView buildResult = WorkflowView.builder().createdBy(1L).id("42").version(1L).build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to WorkflowView");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowView#WorkflowView(String, Long, Long)}
   *   <li>{@link WorkflowView#setCreatedBy(Long)}
   *   <li>{@link WorkflowView#setId(String)}
   *   <li>{@link WorkflowView#setVersion(Long)}
   *   <li>{@link WorkflowView#toString()}
   *   <li>{@link WorkflowView#getCreatedBy()}
   *   <li>{@link WorkflowView#getId()}
   *   <li>{@link WorkflowView#getVersion()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    WorkflowView actualWorkflowView = new WorkflowView("42", 1L, 1L);
    actualWorkflowView.setCreatedBy(1L);
    actualWorkflowView.setId("42");
    actualWorkflowView.setVersion(1L);
    String actualToStringResult = actualWorkflowView.toString();
    Long actualCreatedBy = actualWorkflowView.getCreatedBy();
    String actualId = actualWorkflowView.getId();
    Long actualVersion = actualWorkflowView.getVersion();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("WorkflowView(id=42, version=1, createdBy=1)", actualToStringResult);
    assertEquals(1L, actualCreatedBy.longValue());
    assertEquals(1L, actualVersion.longValue());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowView.WorkflowViewBuilder#build()}
   *   <li>{@link WorkflowView.WorkflowViewBuilder#createdBy(Long)}
   *   <li>{@link WorkflowView.WorkflowViewBuilder#id(String)}
   *   <li>{@link WorkflowView.WorkflowViewBuilder#version(Long)}
   * </ul>
   */
  @Test
  void testWorkflowViewBuilderBuild() {
    // Arrange and Act
    WorkflowView actualBuildResult = WorkflowView.builder().createdBy(1L).id("42").version(1L).build();

    // Assert
    assertEquals("42", actualBuildResult.getId());
    assertEquals(1L, actualBuildResult.getCreatedBy().longValue());
    assertEquals(1L, actualBuildResult.getVersion().longValue());
  }
}
