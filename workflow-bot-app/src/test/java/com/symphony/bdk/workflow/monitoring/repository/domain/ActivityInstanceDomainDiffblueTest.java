package com.symphony.bdk.workflow.monitoring.repository.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain.ActivityInstanceDomainBuilder;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivityInstanceDomainBuilder.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class ActivityInstanceDomainDiffblueTest {
  @Autowired
  private ActivityInstanceDomainBuilder activityInstanceDomainBuilder;

  /**
   * Test ActivityInstanceDomainBuilder {@link ActivityInstanceDomainBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityInstanceDomainBuilder#build()}
   *   <li>{@link ActivityInstanceDomainBuilder#duration(Duration)}
   *   <li>{@link ActivityInstanceDomainBuilder#endDate(Instant)}
   *   <li>{@link ActivityInstanceDomainBuilder#id(String)}
   *   <li>{@link ActivityInstanceDomainBuilder#name(String)}
   *   <li>{@link ActivityInstanceDomainBuilder#procInstId(String)}
   *   <li>{@link ActivityInstanceDomainBuilder#startDate(Instant)}
   *   <li>{@link ActivityInstanceDomainBuilder#type(String)}
   *   <li>{@link ActivityInstanceDomainBuilder#workflowId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test ActivityInstanceDomainBuilder build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ActivityInstanceDomainBuilder.<init>()",
      "ActivityInstanceDomain ActivityInstanceDomainBuilder.build()",
      "ActivityInstanceDomainBuilder ActivityInstanceDomainBuilder.duration(Duration)",
      "ActivityInstanceDomainBuilder ActivityInstanceDomainBuilder.endDate(Instant)",
      "ActivityInstanceDomainBuilder ActivityInstanceDomainBuilder.id(String)",
      "ActivityInstanceDomainBuilder ActivityInstanceDomainBuilder.name(String)",
      "ActivityInstanceDomainBuilder ActivityInstanceDomainBuilder.procInstId(String)",
      "ActivityInstanceDomainBuilder ActivityInstanceDomainBuilder.startDate(Instant)",
      "String ActivityInstanceDomainBuilder.toString()",
      "ActivityInstanceDomainBuilder ActivityInstanceDomainBuilder.type(String)",
      "ActivityInstanceDomainBuilder ActivityInstanceDomainBuilder.workflowId(String)"})
  void testActivityInstanceDomainBuilderBuild() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");

    // Act
    ActivityInstanceDomain actualBuildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getId());
    assertEquals("42", actualBuildResult.getProcInstId());
    assertEquals("42", actualBuildResult.getWorkflowId());
    assertEquals("Name", actualBuildResult.getName());
    assertEquals("Type", actualBuildResult.getType());
    assertNull(actualBuildResult.getDuration());
    VariablesDomain variables = actualBuildResult.getVariables();
    assertNull(variables.getUpdateTime());
    assertEquals(0, variables.getRevision());
    Instant endDate = actualBuildResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    assertTrue(variables.getOutputs().isEmpty());
  }

  /**
   * Test ActivityInstanceDomainBuilder {@link ActivityInstanceDomainBuilder#variables(VariablesDomain)}.
   * <p>
   * Method under test: {@link ActivityInstanceDomainBuilder#variables(VariablesDomain)}
   */
  @Test
  @DisplayName("Test ActivityInstanceDomainBuilder variables(VariablesDomain)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ActivityInstanceDomainBuilder ActivityInstanceDomainBuilder.variables(VariablesDomain)"})
  void testActivityInstanceDomainBuilderVariables() {
    // Arrange
    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(new HashMap<>());
    variables.setRevision(1);
    variables.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(activityInstanceDomainBuilder, activityInstanceDomainBuilder.variables(variables));
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}, and {@link ActivityInstanceDomain#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityInstanceDomain#equals(Object)}
   *   <li>{@link ActivityInstanceDomain#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}, and {@link ActivityInstanceDomain#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityInstanceDomain#equals(Object)}
   *   <li>{@link ActivityInstanceDomain#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}, and {@link ActivityInstanceDomain#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityInstanceDomain#equals(Object)}
   *   <li>{@link ActivityInstanceDomain#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name(null)
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name(null)
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}, and {@link ActivityInstanceDomain#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityInstanceDomain#equals(Object)}
   *   <li>{@link ActivityInstanceDomain#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("Name")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("42")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name(null)
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("Name");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId(null);
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("42")
        .workflowId("42")
        .build();
    ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type(null)
        .workflowId("42")
        .build();
    ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("Name")
        .build();
    ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId(null)
        .build();
    ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test {@link ActivityInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityInstanceDomain.equals(Object)", "int ActivityInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder().duration(null);
    ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to ActivityInstanceDomain");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityInstanceDomain#ActivityInstanceDomain(String, String, String, String, String, Instant, Instant, Duration, VariablesDomain)}
   *   <li>{@link ActivityInstanceDomain#setVariables(VariablesDomain)}
   *   <li>{@link ActivityInstanceDomain#toString()}
   *   <li>{@link ActivityInstanceDomain#getDuration()}
   *   <li>{@link ActivityInstanceDomain#getEndDate()}
   *   <li>{@link ActivityInstanceDomain#getId()}
   *   <li>{@link ActivityInstanceDomain#getName()}
   *   <li>{@link ActivityInstanceDomain#getProcInstId()}
   *   <li>{@link ActivityInstanceDomain#getStartDate()}
   *   <li>{@link ActivityInstanceDomain#getType()}
   *   <li>{@link ActivityInstanceDomain#getVariables()}
   *   <li>{@link ActivityInstanceDomain#getWorkflowId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ActivityInstanceDomain.<init>(String, String, String, String, String, Instant, Instant, Duration, VariablesDomain)",
      "Duration ActivityInstanceDomain.getDuration()", "Instant ActivityInstanceDomain.getEndDate()",
      "String ActivityInstanceDomain.getId()", "String ActivityInstanceDomain.getName()",
      "String ActivityInstanceDomain.getProcInstId()", "Instant ActivityInstanceDomain.getStartDate()",
      "String ActivityInstanceDomain.getType()", "VariablesDomain ActivityInstanceDomain.getVariables()",
      "String ActivityInstanceDomain.getWorkflowId()", "void ActivityInstanceDomain.setVariables(VariablesDomain)",
      "String ActivityInstanceDomain.toString()"})
  void testGettersAndSetters() {
    // Arrange
    Instant startDate = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Instant endDate = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(new HashMap<>());
    variables.setRevision(1);
    variables.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    ActivityInstanceDomain actualActivityInstanceDomain = new ActivityInstanceDomain("42", "Name", "42", "42", "Type",
        startDate, endDate, null, variables);
    VariablesDomain variables2 = new VariablesDomain();
    variables2.setOutputs(new HashMap<>());
    variables2.setRevision(1);
    variables2.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualActivityInstanceDomain.setVariables(variables2);
    String actualToStringResult = actualActivityInstanceDomain.toString();
    Duration actualDuration = actualActivityInstanceDomain.getDuration();
    Instant actualEndDate = actualActivityInstanceDomain.getEndDate();
    String actualId = actualActivityInstanceDomain.getId();
    String actualName = actualActivityInstanceDomain.getName();
    String actualProcInstId = actualActivityInstanceDomain.getProcInstId();
    Instant actualStartDate = actualActivityInstanceDomain.getStartDate();
    String actualType = actualActivityInstanceDomain.getType();
    VariablesDomain actualVariables = actualActivityInstanceDomain.getVariables();

    // Assert
    assertEquals("42", actualId);
    assertEquals("42", actualProcInstId);
    assertEquals("42", actualActivityInstanceDomain.getWorkflowId());
    assertEquals("ActivityInstanceDomain(id=42, name=Name, procInstId=42, workflowId=42, type=Type, startDate=1970-01"
        + "-01T00:00:00Z, endDate=1970-01-01T00:00:00Z, duration=null, variables=VariablesDomain(outputs={},"
        + " revision=1, updateTime=1970-01-01T00:00:00Z))", actualToStringResult);
    assertEquals("Name", actualName);
    assertEquals("Type", actualType);
    assertNull(actualDuration);
    assertSame(variables2, actualVariables);
    Instant instant = actualStartDate.EPOCH;
    assertSame(instant, actualEndDate);
    assertSame(instant, actualStartDate);
  }
}
