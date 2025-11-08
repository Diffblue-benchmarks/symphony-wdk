package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.NodeStateView.NodeStateViewBuilder;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NodeStateViewBuilder.class})
@ExtendWith(SpringExtension.class)
class NodeStateViewDiffblueTest {
  @Autowired
  private NodeStateViewBuilder nodeStateViewBuilder;

  /**
   * Test {@link NodeStateView#equals(Object)}, and {@link NodeStateView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link NodeStateView#equals(Object)}
   *   <li>{@link NodeStateView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
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
   * Test {@link NodeStateView#equals(Object)}, and {@link NodeStateView#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link NodeStateView#equals(Object)}
   *   <li>{@link NodeStateView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
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
   * Test {@link NodeStateView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeStateView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("42")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeStateView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group(null)
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeStateView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("Type")
        .nodeId("42");
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeStateView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId(null)
        .nodeId("42");
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeStateView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("Type");
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeStateView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId(null);
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeStateView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    HashMap<String, Object> outputs = new HashMap<>();
    outputs.put("42", "42");
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder outputsResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42")
        .outputs(outputs);
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult2 = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeStateView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeStateView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("42")
        .workflowId("42")
        .build();
    NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeStateView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type(null)
        .workflowId("42")
        .build();
    NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeStateView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("Type")
        .build();
    NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeStateView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId(null)
        .build();
    NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeStateView#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test {@link NodeStateView#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeStateView.equals(Object)", "int NodeStateView.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to NodeStateView");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link NodeStateView#NodeStateView(String, String, String, String, String, Instant, Instant, Duration, Map)}
   *   <li>{@link NodeStateView#setDuration(Duration)}
   *   <li>{@link NodeStateView#setEndDate(Instant)}
   *   <li>{@link NodeStateView#setGroup(String)}
   *   <li>{@link NodeStateView#setInstanceId(String)}
   *   <li>{@link NodeStateView#setNodeId(String)}
   *   <li>{@link NodeStateView#setOutputs(Map)}
   *   <li>{@link NodeStateView#setStartDate(Instant)}
   *   <li>{@link NodeStateView#setType(String)}
   *   <li>{@link NodeStateView#setWorkflowId(String)}
   *   <li>{@link NodeStateView#toString()}
   *   <li>{@link NodeStateView#getDuration()}
   *   <li>{@link NodeStateView#getEndDate()}
   *   <li>{@link NodeStateView#getGroup()}
   *   <li>{@link NodeStateView#getInstanceId()}
   *   <li>{@link NodeStateView#getNodeId()}
   *   <li>{@link NodeStateView#getOutputs()}
   *   <li>{@link NodeStateView#getStartDate()}
   *   <li>{@link NodeStateView#getType()}
   *   <li>{@link NodeStateView#getWorkflowId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void NodeStateView.<init>(String, String, String, String, String, Instant, Instant, Duration, Map)",
      "Duration NodeStateView.getDuration()", "Instant NodeStateView.getEndDate()", "String NodeStateView.getGroup()",
      "String NodeStateView.getInstanceId()", "String NodeStateView.getNodeId()", "Map NodeStateView.getOutputs()",
      "Instant NodeStateView.getStartDate()", "String NodeStateView.getType()", "String NodeStateView.getWorkflowId()",
      "void NodeStateView.setDuration(Duration)", "void NodeStateView.setEndDate(Instant)",
      "void NodeStateView.setGroup(String)", "void NodeStateView.setInstanceId(String)",
      "void NodeStateView.setNodeId(String)", "void NodeStateView.setOutputs(Map)",
      "void NodeStateView.setStartDate(Instant)", "void NodeStateView.setType(String)",
      "void NodeStateView.setWorkflowId(String)", "String NodeStateView.toString()"})
  void testGettersAndSetters() {
    // Arrange
    Instant startDate = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Instant endDate = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    NodeStateView actualNodeStateView = new NodeStateView("42", "42", "42", "Type", "Group", startDate, endDate, null,
        new HashMap<>());
    actualNodeStateView.setDuration(null);
    actualNodeStateView.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualNodeStateView.setGroup("Group");
    actualNodeStateView.setInstanceId("42");
    actualNodeStateView.setNodeId("42");
    HashMap<String, Object> outputs = new HashMap<>();
    actualNodeStateView.setOutputs(outputs);
    actualNodeStateView.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualNodeStateView.setType("Type");
    actualNodeStateView.setWorkflowId("42");
    String actualToStringResult = actualNodeStateView.toString();
    Duration actualDuration = actualNodeStateView.getDuration();
    Instant actualEndDate = actualNodeStateView.getEndDate();
    String actualGroup = actualNodeStateView.getGroup();
    String actualInstanceId = actualNodeStateView.getInstanceId();
    String actualNodeId = actualNodeStateView.getNodeId();
    Map<String, Object> actualOutputs = actualNodeStateView.getOutputs();
    Instant actualStartDate = actualNodeStateView.getStartDate();
    String actualType = actualNodeStateView.getType();

    // Assert
    assertEquals("42", actualInstanceId);
    assertEquals("42", actualNodeId);
    assertEquals("42", actualNodeStateView.getWorkflowId());
    assertEquals("Group", actualGroup);
    assertEquals(
        "NodeStateView(workflowId=42, instanceId=42, nodeId=42, type=Type, group=Group, startDate=1970-01-01T00"
            + ":00:00Z, endDate=1970-01-01T00:00:00Z, duration=null, outputs={})",
        actualToStringResult);
    assertEquals("Type", actualType);
    assertNull(actualDuration);
    assertTrue(actualOutputs.isEmpty());
    assertSame(outputs, actualOutputs);
    Instant instant = actualStartDate.EPOCH;
    assertSame(instant, actualEndDate);
    assertSame(instant, actualStartDate);
  }

  /**
   * Test NodeStateViewBuilder {@link NodeStateViewBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link NodeStateViewBuilder#build()}
   *   <li>{@link NodeStateViewBuilder#duration(Duration)}
   *   <li>{@link NodeStateViewBuilder#endDate(Instant)}
   *   <li>{@link NodeStateViewBuilder#group(String)}
   *   <li>{@link NodeStateViewBuilder#instanceId(String)}
   *   <li>{@link NodeStateViewBuilder#nodeId(String)}
   *   <li>{@link NodeStateViewBuilder#outputs(Map)}
   *   <li>{@link NodeStateViewBuilder#startDate(Instant)}
   *   <li>{@link NodeStateViewBuilder#type(String)}
   *   <li>{@link NodeStateViewBuilder#workflowId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test NodeStateViewBuilder build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void NodeStateViewBuilder.<init>()", "NodeStateView NodeStateViewBuilder.build()",
      "NodeStateViewBuilder NodeStateViewBuilder.duration(Duration)",
      "NodeStateViewBuilder NodeStateViewBuilder.endDate(Instant)",
      "NodeStateViewBuilder NodeStateViewBuilder.group(String)",
      "NodeStateViewBuilder NodeStateViewBuilder.instanceId(String)",
      "NodeStateViewBuilder NodeStateViewBuilder.nodeId(String)",
      "NodeStateViewBuilder NodeStateViewBuilder.outputs(Map)",
      "NodeStateViewBuilder NodeStateViewBuilder.startDate(Instant)", "String NodeStateViewBuilder.toString()",
      "NodeStateViewBuilder NodeStateViewBuilder.type(String)",
      "NodeStateViewBuilder NodeStateViewBuilder.workflowId(String)"})
  void testNodeStateViewBuilderBuild() {
    // Arrange
    NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    HashMap<String, Object> outputs = new HashMap<>();
    NodeStateViewBuilder outputsResult = nodeIdResult.outputs(outputs);

    // Act
    NodeStateView actualBuildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getInstanceId());
    assertEquals("42", actualBuildResult.getNodeId());
    assertEquals("42", actualBuildResult.getWorkflowId());
    assertEquals("Group", actualBuildResult.getGroup());
    assertEquals("Type", actualBuildResult.getType());
    assertNull(actualBuildResult.getDuration());
    Instant endDate = actualBuildResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    Map<String, Object> outputs2 = actualBuildResult.getOutputs();
    assertTrue(outputs2.isEmpty());
    assertSame(outputs, outputs2);
  }
}
