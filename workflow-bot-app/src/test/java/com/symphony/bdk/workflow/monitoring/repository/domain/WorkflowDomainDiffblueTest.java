package com.symphony.bdk.workflow.monitoring.repository.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowDomain.WorkflowDomainBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowDomainBuilder.class})
@ExtendWith(SpringExtension.class)
class WorkflowDomainDiffblueTest {
  @Autowired
  private WorkflowDomainBuilder workflowDomainBuilder;

  /**
   * Test {@link WorkflowDomain#equals(Object)}, and {@link WorkflowDomain#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowDomain#equals(Object)}
   *   <li>{@link WorkflowDomain#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowDomain.equals(Object)", "int WorkflowDomain.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    WorkflowDomain buildResult = WorkflowDomain.builder().id("42").name("Name").version(1L).build();
    WorkflowDomain buildResult2 = WorkflowDomain.builder().id("42").name("Name").version(1L).build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link WorkflowDomain#equals(Object)}, and {@link WorkflowDomain#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowDomain#equals(Object)}
   *   <li>{@link WorkflowDomain#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowDomain.equals(Object)", "int WorkflowDomain.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    WorkflowDomainBuilder workflowDomainBuilder = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder.id(Mockito.<String>any())).thenReturn(WorkflowDomain.builder());
    WorkflowDomain buildResult = workflowDomainBuilder.id("42").name("Name").version(1L).build();
    WorkflowDomainBuilder workflowDomainBuilder2 = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder2.id(Mockito.<String>any())).thenReturn(WorkflowDomain.builder());
    WorkflowDomain buildResult2 = workflowDomainBuilder2.id("42").name("Name").version(1L).build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link WorkflowDomain#equals(Object)}, and {@link WorkflowDomain#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowDomain#equals(Object)}
   *   <li>{@link WorkflowDomain#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowDomain.equals(Object)", "int WorkflowDomain.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    WorkflowDomainBuilder workflowDomainBuilder = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder.name(Mockito.<String>any())).thenReturn(WorkflowDomain.builder());
    WorkflowDomainBuilder workflowDomainBuilder2 = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder2.id(Mockito.<String>any())).thenReturn(workflowDomainBuilder);
    WorkflowDomain buildResult = workflowDomainBuilder2.id("42").name("Name").version(1L).build();
    WorkflowDomainBuilder workflowDomainBuilder3 = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder3.name(Mockito.<String>any())).thenReturn(WorkflowDomain.builder());
    WorkflowDomainBuilder workflowDomainBuilder4 = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder4.id(Mockito.<String>any())).thenReturn(workflowDomainBuilder3);
    WorkflowDomain buildResult2 = workflowDomainBuilder4.id("42").name("Name").version(1L).build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link WorkflowDomain#equals(Object)}, and {@link WorkflowDomain#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowDomain#equals(Object)}
   *   <li>{@link WorkflowDomain#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowDomain.equals(Object)", "int WorkflowDomain.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    WorkflowDomain buildResult = WorkflowDomain.builder().id("42").name("Name").version(1L).build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Test {@link WorkflowDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowDomain.equals(Object)", "int WorkflowDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    WorkflowDomainBuilder workflowDomainBuilder = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder.id(Mockito.<String>any())).thenReturn(WorkflowDomain.builder());
    WorkflowDomain buildResult = workflowDomainBuilder.id("42").name("Name").version(1L).build();
    WorkflowDomain buildResult2 = WorkflowDomain.builder().id("42").name("Name").version(1L).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowDomain.equals(Object)", "int WorkflowDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    WorkflowDomainBuilder workflowDomainBuilder = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder.id(Mockito.<String>any())).thenReturn(WorkflowDomain.builder());
    WorkflowDomain buildResult = workflowDomainBuilder.id("42").name("Name").version(3L).build();
    WorkflowDomain buildResult2 = WorkflowDomain.builder().id("42").name("Name").version(1L).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowDomain.equals(Object)", "int WorkflowDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    WorkflowDomainBuilder workflowDomainBuilder = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder.id(Mockito.<String>any())).thenReturn(WorkflowDomain.builder());
    WorkflowDomain buildResult = workflowDomainBuilder.id("42").name("Name").version(null).build();
    WorkflowDomain buildResult2 = WorkflowDomain.builder().id("42").name("Name").version(1L).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowDomain.equals(Object)", "int WorkflowDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    WorkflowDomainBuilder workflowDomainBuilder = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder.name(Mockito.<String>any())).thenReturn(WorkflowDomain.builder());
    WorkflowDomainBuilder workflowDomainBuilder2 = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder2.id(Mockito.<String>any())).thenReturn(workflowDomainBuilder);
    WorkflowDomain buildResult = workflowDomainBuilder2.id("42").name("Name").version(1L).build();
    WorkflowDomainBuilder workflowDomainBuilder3 = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder3.id(Mockito.<String>any())).thenReturn(WorkflowDomain.builder());
    WorkflowDomain buildResult2 = workflowDomainBuilder3.id("42").name("Name").version(1L).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowDomain.equals(Object)", "int WorkflowDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    WorkflowDomainBuilder builderResult = WorkflowDomain.builder();
    builderResult.id("42");
    WorkflowDomainBuilder workflowDomainBuilder = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder.name(Mockito.<String>any())).thenReturn(builderResult);
    WorkflowDomainBuilder workflowDomainBuilder2 = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder2.id(Mockito.<String>any())).thenReturn(workflowDomainBuilder);
    WorkflowDomain buildResult = workflowDomainBuilder2.id("42").name("Name").version(1L).build();
    WorkflowDomainBuilder workflowDomainBuilder3 = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder3.name(Mockito.<String>any())).thenReturn(WorkflowDomain.builder());
    WorkflowDomainBuilder workflowDomainBuilder4 = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder4.id(Mockito.<String>any())).thenReturn(workflowDomainBuilder3);
    WorkflowDomain buildResult2 = workflowDomainBuilder4.id("42").name("Name").version(1L).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowDomain.equals(Object)", "int WorkflowDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    WorkflowDomainBuilder builderResult = WorkflowDomain.builder();
    builderResult.name("Name");
    WorkflowDomainBuilder workflowDomainBuilder = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder.name(Mockito.<String>any())).thenReturn(builderResult);
    WorkflowDomainBuilder workflowDomainBuilder2 = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder2.id(Mockito.<String>any())).thenReturn(workflowDomainBuilder);
    WorkflowDomain buildResult = workflowDomainBuilder2.id("42").name("Name").version(1L).build();
    WorkflowDomainBuilder workflowDomainBuilder3 = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder3.name(Mockito.<String>any())).thenReturn(WorkflowDomain.builder());
    WorkflowDomainBuilder workflowDomainBuilder4 = mock(WorkflowDomainBuilder.class);
    when(workflowDomainBuilder4.id(Mockito.<String>any())).thenReturn(workflowDomainBuilder3);
    WorkflowDomain buildResult2 = workflowDomainBuilder4.id("42").name("Name").version(1L).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowDomain#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowDomain.equals(Object)", "int WorkflowDomain.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    WorkflowDomain buildResult = WorkflowDomain.builder().id("42").name("Name").version(1L).build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test {@link WorkflowDomain#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowDomain.equals(Object)", "int WorkflowDomain.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    WorkflowDomain buildResult = WorkflowDomain.builder().id("42").name("Name").version(1L).build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to WorkflowDomain");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowDomain#WorkflowDomain(String, String, Long)}
   *   <li>{@link WorkflowDomain#toString()}
   *   <li>{@link WorkflowDomain#getId()}
   *   <li>{@link WorkflowDomain#getName()}
   *   <li>{@link WorkflowDomain#getVersion()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowDomain.<init>(String, String, Long)", "String WorkflowDomain.getId()",
      "String WorkflowDomain.getName()", "Long WorkflowDomain.getVersion()", "String WorkflowDomain.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    WorkflowDomain actualWorkflowDomain = new WorkflowDomain("42", "Name", 1L);
    String actualToStringResult = actualWorkflowDomain.toString();
    String actualId = actualWorkflowDomain.getId();
    String actualName = actualWorkflowDomain.getName();

    // Assert
    assertEquals("42", actualId);
    assertEquals("Name", actualName);
    assertEquals("WorkflowDomain(id=42, name=Name, version=1)", actualToStringResult);
    assertEquals(1L, actualWorkflowDomain.getVersion().longValue());
  }

  /**
   * Test WorkflowDomainBuilder {@link WorkflowDomainBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowDomainBuilder#build()}
   *   <li>{@link WorkflowDomainBuilder#id(String)}
   *   <li>{@link WorkflowDomainBuilder#name(String)}
   *   <li>{@link WorkflowDomainBuilder#version(Long)}
   * </ul>
   */
  @Test
  @DisplayName("Test WorkflowDomainBuilder build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowDomainBuilder.<init>()", "WorkflowDomain WorkflowDomainBuilder.build()",
      "WorkflowDomainBuilder WorkflowDomainBuilder.id(String)",
      "WorkflowDomainBuilder WorkflowDomainBuilder.name(String)", "String WorkflowDomainBuilder.toString()",
      "WorkflowDomainBuilder WorkflowDomainBuilder.version(Long)"})
  void testWorkflowDomainBuilderBuild() {
    // Arrange and Act
    WorkflowDomain actualBuildResult = WorkflowDomain.builder().id("42").name("Name").version(1L).build();

    // Assert
    assertEquals("42", actualBuildResult.getId());
    assertEquals("Name", actualBuildResult.getName());
    assertEquals(1L, actualBuildResult.getVersion().longValue());
  }
}
