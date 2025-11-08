package com.symphony.bdk.workflow.monitoring.repository.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain.WorkflowInstanceDomainBuilder;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowInstanceDomainBuilder.class})
@ExtendWith(SpringExtension.class)
class WorkflowInstanceDomainDiffblueTest {
  @Autowired
  private WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder;

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}, and {@link WorkflowInstanceDomain#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstanceDomain#equals(Object)}
   *   <li>{@link WorkflowInstanceDomain#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}, and {@link WorkflowInstanceDomain#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstanceDomain#equals(Object)}
   *   <li>{@link WorkflowInstanceDomain#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}, and {@link WorkflowInstanceDomain#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstanceDomain#equals(Object)}
   *   <li>{@link WorkflowInstanceDomain#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId(null)
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId(null)
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}, and {@link WorkflowInstanceDomain#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstanceDomain#equals(Object)}
   *   <li>{@link WorkflowInstanceDomain#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("Name")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("Name")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId(null)
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("42");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name(null);
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("42")
        .version(1L)
        .build();
    WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(null)
        .version(1L)
        .build();
    WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(3L)
        .build();
    WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(null)
        .build();
    WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test {@link WorkflowInstanceDomain#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowInstanceDomain.equals(Object)", "int WorkflowInstanceDomain.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to WorkflowInstanceDomain");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstanceDomain#WorkflowInstanceDomain(String, String, Long, String, String, Instant, Instant, Duration)}
   *   <li>{@link WorkflowInstanceDomain#toString()}
   *   <li>{@link WorkflowInstanceDomain#getDuration()}
   *   <li>{@link WorkflowInstanceDomain#getEndDate()}
   *   <li>{@link WorkflowInstanceDomain#getId()}
   *   <li>{@link WorkflowInstanceDomain#getInstanceId()}
   *   <li>{@link WorkflowInstanceDomain#getName()}
   *   <li>{@link WorkflowInstanceDomain#getStartDate()}
   *   <li>{@link WorkflowInstanceDomain#getStatus()}
   *   <li>{@link WorkflowInstanceDomain#getVersion()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void WorkflowInstanceDomain.<init>(String, String, Long, String, String, Instant, Instant, Duration)",
      "Duration WorkflowInstanceDomain.getDuration()", "Instant WorkflowInstanceDomain.getEndDate()",
      "String WorkflowInstanceDomain.getId()", "String WorkflowInstanceDomain.getInstanceId()",
      "String WorkflowInstanceDomain.getName()", "Instant WorkflowInstanceDomain.getStartDate()",
      "String WorkflowInstanceDomain.getStatus()", "Long WorkflowInstanceDomain.getVersion()",
      "String WorkflowInstanceDomain.toString()"})
  void testGettersAndSetters() {
    // Arrange
    Instant startDate = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    WorkflowInstanceDomain actualWorkflowInstanceDomain = new WorkflowInstanceDomain("42", "Name", 1L, "42", "Status",
        startDate, LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(), null);
    String actualToStringResult = actualWorkflowInstanceDomain.toString();
    Duration actualDuration = actualWorkflowInstanceDomain.getDuration();
    Instant actualEndDate = actualWorkflowInstanceDomain.getEndDate();
    String actualId = actualWorkflowInstanceDomain.getId();
    String actualInstanceId = actualWorkflowInstanceDomain.getInstanceId();
    String actualName = actualWorkflowInstanceDomain.getName();
    Instant actualStartDate = actualWorkflowInstanceDomain.getStartDate();
    String actualStatus = actualWorkflowInstanceDomain.getStatus();

    // Assert
    assertEquals("42", actualId);
    assertEquals("42", actualInstanceId);
    assertEquals("Name", actualName);
    assertEquals("Status", actualStatus);
    assertEquals("WorkflowInstanceDomain(id=42, name=Name, version=1, instanceId=42, status=Status, startDate=1970-01"
        + "-01T00:00:00Z, endDate=1970-01-01T00:00:00Z, duration=null)", actualToStringResult);
    assertNull(actualDuration);
    assertEquals(1L, actualWorkflowInstanceDomain.getVersion().longValue());
    Instant instant = actualStartDate.EPOCH;
    assertSame(instant, actualEndDate);
    assertSame(instant, actualStartDate);
  }

  /**
   * Test WorkflowInstanceDomainBuilder {@link WorkflowInstanceDomainBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstanceDomainBuilder#build()}
   *   <li>{@link WorkflowInstanceDomainBuilder#duration(Duration)}
   *   <li>{@link WorkflowInstanceDomainBuilder#endDate(Instant)}
   *   <li>{@link WorkflowInstanceDomainBuilder#id(String)}
   *   <li>{@link WorkflowInstanceDomainBuilder#instanceId(String)}
   *   <li>{@link WorkflowInstanceDomainBuilder#name(String)}
   *   <li>{@link WorkflowInstanceDomainBuilder#startDate(Instant)}
   *   <li>{@link WorkflowInstanceDomainBuilder#status(String)}
   *   <li>{@link WorkflowInstanceDomainBuilder#version(Long)}
   * </ul>
   */
  @Test
  @DisplayName("Test WorkflowInstanceDomainBuilder build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowInstanceDomainBuilder.<init>()",
      "WorkflowInstanceDomain WorkflowInstanceDomainBuilder.build()",
      "WorkflowInstanceDomainBuilder WorkflowInstanceDomainBuilder.duration(Duration)",
      "WorkflowInstanceDomainBuilder WorkflowInstanceDomainBuilder.endDate(Instant)",
      "WorkflowInstanceDomainBuilder WorkflowInstanceDomainBuilder.id(String)",
      "WorkflowInstanceDomainBuilder WorkflowInstanceDomainBuilder.instanceId(String)",
      "WorkflowInstanceDomainBuilder WorkflowInstanceDomainBuilder.name(String)",
      "WorkflowInstanceDomainBuilder WorkflowInstanceDomainBuilder.startDate(Instant)",
      "WorkflowInstanceDomainBuilder WorkflowInstanceDomainBuilder.status(String)",
      "String WorkflowInstanceDomainBuilder.toString()",
      "WorkflowInstanceDomainBuilder WorkflowInstanceDomainBuilder.version(Long)"})
  void testWorkflowInstanceDomainBuilderBuild() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");

    // Act
    WorkflowInstanceDomain actualBuildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getId());
    assertEquals("42", actualBuildResult.getInstanceId());
    assertEquals("Name", actualBuildResult.getName());
    assertEquals("Status", actualBuildResult.getStatus());
    assertNull(actualBuildResult.getDuration());
    Instant endDate = actualBuildResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    assertEquals(1L, actualBuildResult.getVersion().longValue());
  }
}
