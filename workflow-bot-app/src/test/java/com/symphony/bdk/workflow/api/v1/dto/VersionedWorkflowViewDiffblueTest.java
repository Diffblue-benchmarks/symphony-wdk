package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView.VersionedWorkflowViewBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VersionedWorkflowViewBuilder.class})
@ExtendWith(SpringExtension.class)
class VersionedWorkflowViewDiffblueTest {
  @Autowired
  private VersionedWorkflowViewBuilder versionedWorkflowViewBuilder;

  /**
   * Test {@link VersionedWorkflowView#equals(Object)}, and {@link VersionedWorkflowView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VersionedWorkflowView#equals(Object)}
   *   <li>{@link VersionedWorkflowView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
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
   * Test {@link VersionedWorkflowView#equals(Object)}, and {@link VersionedWorkflowView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VersionedWorkflowView#equals(Object)}
   *   <li>{@link VersionedWorkflowView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}, and {@link VersionedWorkflowView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VersionedWorkflowView#equals(Object)}
   *   <li>{@link VersionedWorkflowView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}, and {@link VersionedWorkflowView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VersionedWorkflowView#equals(Object)}
   *   <li>{@link VersionedWorkflowView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder4);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}, and {@link VersionedWorkflowView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VersionedWorkflowView#equals(Object)}
   *   <li>{@link VersionedWorkflowView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual5() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder7 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder7.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder6);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder8 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}, and {@link VersionedWorkflowView#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VersionedWorkflowView#equals(Object)}
   *   <li>{@link VersionedWorkflowView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder4.createdBy(Mockito.<Long>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder7 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder7 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual14() {
    // Arrange
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.deploymentId(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder7 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual15() {
    // Arrange
    VersionedWorkflowViewBuilder builderResult = VersionedWorkflowView.builder();
    builderResult.active(true);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.description(Mockito.<String>any())).thenReturn(builderResult);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder7 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder7.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder6);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder8 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual16() {
    // Arrange
    VersionedWorkflowViewBuilder builderResult = VersionedWorkflowView.builder();
    builderResult.deploymentId("42");
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.description(Mockito.<String>any())).thenReturn(builderResult);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder7 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder7.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder6);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder8 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual17() {
    // Arrange
    VersionedWorkflowViewBuilder builderResult = VersionedWorkflowView.builder();
    builderResult.createdBy(1L);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.description(Mockito.<String>any())).thenReturn(builderResult);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder7 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder7.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder6);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder8 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual18() {
    // Arrange
    VersionedWorkflowViewBuilder builderResult = VersionedWorkflowView.builder();
    builderResult.description("The characteristics of someone or something");
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder.description(Mockito.<String>any())).thenReturn(builderResult);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder2 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder2.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder3 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder2);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder4 = mock(VersionedWorkflowViewBuilder.class);
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
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder5 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder5.description(Mockito.<String>any())).thenReturn(VersionedWorkflowView.builder());
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder6 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder6.deploymentId(Mockito.<String>any())).thenReturn(versionedWorkflowViewBuilder5);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder7 = mock(VersionedWorkflowViewBuilder.class);
    when(versionedWorkflowViewBuilder7.createdBy(Mockito.<Long>any())).thenReturn(versionedWorkflowViewBuilder6);
    VersionedWorkflowViewBuilder versionedWorkflowViewBuilder8 = mock(VersionedWorkflowViewBuilder.class);
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
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
   * Test {@link VersionedWorkflowView#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VersionedWorkflowView.equals(Object)", "int VersionedWorkflowView.hashCode()"})
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
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VersionedWorkflowView#VersionedWorkflowView(String, String, Long, Boolean, Boolean, String, Long, String, String)}
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void VersionedWorkflowView.<init>(String, String, Long, Boolean, Boolean, String, Long, String, String)",
      "Boolean VersionedWorkflowView.getActive()", "Long VersionedWorkflowView.getCreatedBy()",
      "String VersionedWorkflowView.getDeploymentId()", "String VersionedWorkflowView.getDescription()",
      "String VersionedWorkflowView.getId()", "Boolean VersionedWorkflowView.getPublished()",
      "String VersionedWorkflowView.getSwadl()", "Long VersionedWorkflowView.getVersion()",
      "String VersionedWorkflowView.getWorkflowId()", "void VersionedWorkflowView.setActive(Boolean)",
      "void VersionedWorkflowView.setCreatedBy(Long)", "void VersionedWorkflowView.setDeploymentId(String)",
      "void VersionedWorkflowView.setDescription(String)", "void VersionedWorkflowView.setId(String)",
      "void VersionedWorkflowView.setPublished(Boolean)", "void VersionedWorkflowView.setSwadl(String)",
      "void VersionedWorkflowView.setVersion(Long)", "void VersionedWorkflowView.setWorkflowId(String)",
      "String VersionedWorkflowView.toString()"})
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

    // Assert
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
   * Test VersionedWorkflowViewBuilder {@link VersionedWorkflowViewBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VersionedWorkflowViewBuilder#build()}
   *   <li>{@link VersionedWorkflowViewBuilder#active(Boolean)}
   *   <li>{@link VersionedWorkflowViewBuilder#createdBy(Long)}
   *   <li>{@link VersionedWorkflowViewBuilder#deploymentId(String)}
   *   <li>{@link VersionedWorkflowViewBuilder#description(String)}
   *   <li>{@link VersionedWorkflowViewBuilder#id(String)}
   *   <li>{@link VersionedWorkflowViewBuilder#published(Boolean)}
   *   <li>{@link VersionedWorkflowViewBuilder#swadl(String)}
   *   <li>{@link VersionedWorkflowViewBuilder#version(Long)}
   *   <li>{@link VersionedWorkflowViewBuilder#workflowId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test VersionedWorkflowViewBuilder build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void VersionedWorkflowViewBuilder.<init>()",
      "VersionedWorkflowViewBuilder VersionedWorkflowViewBuilder.active(Boolean)",
      "VersionedWorkflowView VersionedWorkflowViewBuilder.build()",
      "VersionedWorkflowViewBuilder VersionedWorkflowViewBuilder.createdBy(Long)",
      "VersionedWorkflowViewBuilder VersionedWorkflowViewBuilder.deploymentId(String)",
      "VersionedWorkflowViewBuilder VersionedWorkflowViewBuilder.description(String)",
      "VersionedWorkflowViewBuilder VersionedWorkflowViewBuilder.id(String)",
      "VersionedWorkflowViewBuilder VersionedWorkflowViewBuilder.published(Boolean)",
      "VersionedWorkflowViewBuilder VersionedWorkflowViewBuilder.swadl(String)",
      "String VersionedWorkflowViewBuilder.toString()",
      "VersionedWorkflowViewBuilder VersionedWorkflowViewBuilder.version(Long)",
      "VersionedWorkflowViewBuilder VersionedWorkflowViewBuilder.workflowId(String)"})
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
