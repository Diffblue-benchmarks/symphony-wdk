package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VersionedWorkflowViewDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VersionedWorkflowView#equals(Object)}
   *   <li>{@link VersionedWorkflowView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    VersionedWorkflowView buildResult = VersionedWorkflowView.builder()
        .active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView buildResult2 = VersionedWorkflowView.builder()
        .active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VersionedWorkflowView#equals(Object)}
   *   <li>{@link VersionedWorkflowView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.active(Mockito.<Boolean>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.active(Mockito.<Boolean>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder2.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VersionedWorkflowView#equals(Object)}
   *   <li>{@link VersionedWorkflowView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder2.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder3);
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder4.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VersionedWorkflowView#equals(Object)}
   *   <li>{@link VersionedWorkflowView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder3.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder4);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder6.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VersionedWorkflowView#equals(Object)}
   *   <li>{@link VersionedWorkflowView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual5() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder3);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder4.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder7 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder7.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder6);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder8 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder8.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder7);
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder8.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VersionedWorkflowView#equals(Object)}
   *   <li>{@link VersionedWorkflowView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    VersionedWorkflowView buildResult = VersionedWorkflowView.builder()
        .active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.active(Mockito.<Boolean>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView buildResult2 = VersionedWorkflowView.builder()
        .active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.active(Mockito.<Boolean>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(3L)
        .workflowId("42")
        .build();
    VersionedWorkflowView buildResult2 = VersionedWorkflowView.builder()
        .active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.active(Mockito.<Boolean>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(null)
        .workflowId("42")
        .build();
    VersionedWorkflowView buildResult2 = VersionedWorkflowView.builder()
        .active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder2.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.active(Mockito.<Boolean>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder3.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder2.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(false)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.active(Mockito.<Boolean>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder3.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder2.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(null)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.active(Mockito.<Boolean>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder3.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder3.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder4);
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder5.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder3.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("Id")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder4);
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder5.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder3.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id(null)
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder4);
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder5.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder3.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("Workflow Id")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder4);
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder5.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder3.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId(null)
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder4);
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder5.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder3);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder4.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder7 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder7.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder6);
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder7.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder3);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder4.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("42")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder7 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder7.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder6);
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder7.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual14() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder3);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder4.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl(null)
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder7 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder7.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder6);
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder7.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual15() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder builderResult = VersionedWorkflowView.builder();
    builderResult.active(true);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.description(Mockito.<String>any())).thenReturn(builderResult);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder3);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder4.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder7 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder7.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder6);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder8 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder8.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder7);
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder8.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual16() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder builderResult = VersionedWorkflowView.builder();
    builderResult.deploymentId("42");
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.description(Mockito.<String>any())).thenReturn(builderResult);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder3);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder4.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder7 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder7.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder6);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder8 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder8.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder7);
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder8.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual17() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder builderResult = VersionedWorkflowView.builder();
    builderResult.createdBy(1L);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.description(Mockito.<String>any())).thenReturn(builderResult);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder3);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder4.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder7 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder7.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder6);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder8 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder8.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder7);
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder8.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual18() {
    // Arrange
    VersionedWorkflowView.VersionedWorkflowViewBuilder builderResult = VersionedWorkflowView.builder();
    builderResult.description("The characteristics of someone or something");
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.description(Mockito.<String>any())).thenReturn(builderResult);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder3);
    VersionedWorkflowView buildResult = versionedWorkflowViewBuilder4.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder7 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder7.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder6);
    VersionedWorkflowView.VersionedWorkflowViewBuilder versionedWorkflowViewBuilder8 = mock(
        VersionedWorkflowView.VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder8.active(Mockito.<Boolean>any())).thenReturn(versionedWorkflowViewBuilder7);
    VersionedWorkflowView buildResult2 = versionedWorkflowViewBuilder8.active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    VersionedWorkflowView buildResult = VersionedWorkflowView.builder()
        .active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    VersionedWorkflowView buildResult = VersionedWorkflowView.builder()
        .active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to VersionedWorkflowView");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link VersionedWorkflowView#VersionedWorkflowView(String, String, Long, Boolean, Boolean, String, Long, String, String)}
   *   <li>{@link VersionedWorkflowView#setActive(Boolean)}
   *   <li>{@link VersionedWorkflowView#setCreatedBy(Long)}
   *   <li>{@link VersionedWorkflowView#setDeploymentId(String)}
   *   <li>{@link VersionedWorkflowView#setDescription(String)}
   *   <li>{@link VersionedWorkflowView#setId(String)}
   *   <li>{@link VersionedWorkflowView#setPublished(Boolean)}
   *   <li>{@link VersionedWorkflowView#setSwadl(String)}
   *   <li>{@link VersionedWorkflowView#setVersion(Long)}
   *   <li>{@link VersionedWorkflowView#setWorkflowId(String)}
   *   <li>{@link VersionedWorkflowView#toString()}
   *   <li>{@link VersionedWorkflowView#getActive()}
   *   <li>{@link VersionedWorkflowView#getCreatedBy()}
   *   <li>{@link VersionedWorkflowView#getDeploymentId()}
   *   <li>{@link VersionedWorkflowView#getDescription()}
   *   <li>{@link VersionedWorkflowView#getId()}
   *   <li>{@link VersionedWorkflowView#getPublished()}
   *   <li>{@link VersionedWorkflowView#getSwadl()}
   *   <li>{@link VersionedWorkflowView#getVersion()}
   *   <li>{@link VersionedWorkflowView#getWorkflowId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    VersionedWorkflowView actualVersionedWorkflowView = new VersionedWorkflowView("42", "42", 1L, true, true, "42", 1L,
        "Swadl", "The characteristics of someone or something");
    actualVersionedWorkflowView.setActive(true);
    actualVersionedWorkflowView.setCreatedBy(1L);
    actualVersionedWorkflowView.setDeploymentId("42");
    actualVersionedWorkflowView.setDescription("The characteristics of someone or something");
    actualVersionedWorkflowView.setId("42");
    actualVersionedWorkflowView.setPublished(true);
    actualVersionedWorkflowView.setSwadl("Swadl");
    actualVersionedWorkflowView.setVersion(1L);
    actualVersionedWorkflowView.setWorkflowId("42");
    String actualToStringResult = actualVersionedWorkflowView.toString();
    Boolean actualActive = actualVersionedWorkflowView.getActive();
    Long actualCreatedBy = actualVersionedWorkflowView.getCreatedBy();
    String actualDeploymentId = actualVersionedWorkflowView.getDeploymentId();
    String actualDescription = actualVersionedWorkflowView.getDescription();
    String actualId = actualVersionedWorkflowView.getId();
    Boolean actualPublished = actualVersionedWorkflowView.getPublished();
    String actualSwadl = actualVersionedWorkflowView.getSwadl();
    Long actualVersion = actualVersionedWorkflowView.getVersion();

    // Assert that nothing has changed
    assertEquals("42", actualDeploymentId);
    assertEquals("42", actualId);
    assertEquals("42", actualVersionedWorkflowView.getWorkflowId());
    assertEquals("Swadl", actualSwadl);
    assertEquals("The characteristics of someone or something", actualDescription);
    assertEquals(
        "VersionedWorkflowView(id=42, workflowId=42, version=1, active=true, published=true, deploymentId=42,"
            + " createdBy=1, swadl=Swadl, description=The characteristics of someone or something)",
        actualToStringResult);
    assertEquals(1L, actualCreatedBy.longValue());
    assertEquals(1L, actualVersion.longValue());
    assertTrue(actualActive);
    assertTrue(actualPublished);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link VersionedWorkflowView.VersionedWorkflowViewBuilder#build()}
   *   <li>
   * {@link VersionedWorkflowView.VersionedWorkflowViewBuilder#active(Boolean)}
   *   <li>
   * {@link VersionedWorkflowView.VersionedWorkflowViewBuilder#createdBy(Long)}
   *   <li>
   * {@link VersionedWorkflowView.VersionedWorkflowViewBuilder#deploymentId(String)}
   *   <li>
   * {@link VersionedWorkflowView.VersionedWorkflowViewBuilder#description(String)}
   *   <li>{@link VersionedWorkflowView.VersionedWorkflowViewBuilder#id(String)}
   *   <li>
   * {@link VersionedWorkflowView.VersionedWorkflowViewBuilder#published(Boolean)}
   *   <li>{@link VersionedWorkflowView.VersionedWorkflowViewBuilder#swadl(String)}
   *   <li>{@link VersionedWorkflowView.VersionedWorkflowViewBuilder#version(Long)}
   *   <li>
   * {@link VersionedWorkflowView.VersionedWorkflowViewBuilder#workflowId(String)}
   * </ul>
   */
  @Test
  void testVersionedWorkflowViewBuilderBuild() {
    // Arrange and Act
    VersionedWorkflowView actualBuildResult = VersionedWorkflowView.builder()
        .active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getDeploymentId());
    assertEquals("42", actualBuildResult.getId());
    assertEquals("42", actualBuildResult.getWorkflowId());
    assertEquals("Swadl", actualBuildResult.getSwadl());
    assertEquals("The characteristics of someone or something", actualBuildResult.getDescription());
    assertEquals(1L, actualBuildResult.getCreatedBy().longValue());
    assertEquals(1L, actualBuildResult.getVersion().longValue());
    assertTrue(actualBuildResult.getActive());
    assertTrue(actualBuildResult.getPublished());
  }
}
