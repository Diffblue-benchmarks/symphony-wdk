package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NodeStateViewDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NodeStateView#equals(Object)}
   *   <li>{@link NodeStateView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    NodeStateView.NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateView.NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
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
   * Methods under test:
   * <ul>
   *   <li>{@link NodeStateView#equals(Object)}
   *   <li>{@link NodeStateView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder.duration(Mockito.<Duration>any())).thenReturn(NodeStateView.builder());
    NodeStateView.NodeStateViewBuilder durationResult = nodeStateViewBuilder.duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateView.NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
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
   * Methods under test:
   * <ul>
   *   <li>{@link NodeStateView#equals(Object)}
   *   <li>{@link NodeStateView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    NodeStateView.NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
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
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder.endDate(Mockito.<Instant>any())).thenReturn(NodeStateView.builder());
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder2 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder2.duration(Mockito.<Duration>any())).thenReturn(nodeStateViewBuilder);
    NodeStateView.NodeStateViewBuilder durationResult = nodeStateViewBuilder2.duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateView.NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder.group(Mockito.<String>any())).thenReturn(NodeStateView.builder());
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder2 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder2.endDate(Mockito.<Instant>any())).thenReturn(nodeStateViewBuilder);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder3 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder3.duration(Mockito.<Duration>any())).thenReturn(nodeStateViewBuilder2);
    NodeStateView.NodeStateViewBuilder durationResult = nodeStateViewBuilder3.duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateView.NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder.instanceId(Mockito.<String>any())).thenReturn(NodeStateView.builder());
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder2 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeStateViewBuilder);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder3 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder3.endDate(Mockito.<Instant>any())).thenReturn(nodeStateViewBuilder2);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder4 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder4.duration(Mockito.<Duration>any())).thenReturn(nodeStateViewBuilder3);
    NodeStateView.NodeStateViewBuilder durationResult = nodeStateViewBuilder4.duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateView.NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder.instanceId(Mockito.<String>any())).thenReturn(NodeStateView.builder());
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder2 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeStateViewBuilder);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder3 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder3.endDate(Mockito.<Instant>any())).thenReturn(nodeStateViewBuilder2);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder4 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder4.duration(Mockito.<Duration>any())).thenReturn(nodeStateViewBuilder3);
    NodeStateView.NodeStateViewBuilder durationResult = nodeStateViewBuilder4.duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("Workflow Id")
        .build();
    NodeStateView.NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder.instanceId(Mockito.<String>any())).thenReturn(NodeStateView.builder());
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder2 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeStateViewBuilder);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder3 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder3.endDate(Mockito.<Instant>any())).thenReturn(nodeStateViewBuilder2);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder4 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder4.duration(Mockito.<Duration>any())).thenReturn(nodeStateViewBuilder3);
    NodeStateView.NodeStateViewBuilder durationResult = nodeStateViewBuilder4.duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId(null)
        .build();
    NodeStateView.NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder.instanceId(Mockito.<String>any())).thenReturn(NodeStateView.builder());
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder2 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeStateViewBuilder);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder3 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder3.endDate(Mockito.<Instant>any())).thenReturn(nodeStateViewBuilder2);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder4 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder4.duration(Mockito.<Duration>any())).thenReturn(nodeStateViewBuilder3);
    NodeStateView.NodeStateViewBuilder durationResult = nodeStateViewBuilder4.duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateView.NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId(null)
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder.instanceId(Mockito.<String>any())).thenReturn(NodeStateView.builder());
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder2 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeStateViewBuilder);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder3 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder3.endDate(Mockito.<Instant>any())).thenReturn(nodeStateViewBuilder2);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder4 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder4.duration(Mockito.<Duration>any())).thenReturn(nodeStateViewBuilder3);
    NodeStateView.NodeStateViewBuilder durationResult = nodeStateViewBuilder4.duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId(null)
        .build();
    NodeStateView.NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId(null)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeStateView.builder());
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder2 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder2.instanceId(Mockito.<String>any())).thenReturn(nodeStateViewBuilder);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder3 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder3.group(Mockito.<String>any())).thenReturn(nodeStateViewBuilder2);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder4 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder4.endDate(Mockito.<Instant>any())).thenReturn(nodeStateViewBuilder3);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder5 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder5.duration(Mockito.<Duration>any())).thenReturn(nodeStateViewBuilder4);
    NodeStateView.NodeStateViewBuilder durationResult = nodeStateViewBuilder5.duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateView.NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId(null)
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeStateView.builder());
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder2 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder2.instanceId(Mockito.<String>any())).thenReturn(nodeStateViewBuilder);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder3 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder3.group(Mockito.<String>any())).thenReturn(nodeStateViewBuilder2);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder4 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder4.endDate(Mockito.<Instant>any())).thenReturn(nodeStateViewBuilder3);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder5 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder5.duration(Mockito.<Duration>any())).thenReturn(nodeStateViewBuilder4);
    NodeStateView.NodeStateViewBuilder durationResult = nodeStateViewBuilder5.duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateView.NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId(null)
        .nodeId(null);
    NodeStateView.NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    NodeStateView.NodeStateViewBuilder builderResult = NodeStateView.builder();
    builderResult.instanceId("42");
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder.nodeId(Mockito.<String>any())).thenReturn(builderResult);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder2 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder2.instanceId(Mockito.<String>any())).thenReturn(nodeStateViewBuilder);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder3 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder3.group(Mockito.<String>any())).thenReturn(nodeStateViewBuilder2);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder4 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder4.endDate(Mockito.<Instant>any())).thenReturn(nodeStateViewBuilder3);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder5 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder5.duration(Mockito.<Duration>any())).thenReturn(nodeStateViewBuilder4);
    NodeStateView.NodeStateViewBuilder durationResult = nodeStateViewBuilder5.duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateView.NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId(null)
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeStateView.builder());
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder2 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder2.instanceId(Mockito.<String>any())).thenReturn(nodeStateViewBuilder);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder3 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder3.group(Mockito.<String>any())).thenReturn(nodeStateViewBuilder2);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder4 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder4.endDate(Mockito.<Instant>any())).thenReturn(nodeStateViewBuilder3);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder5 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder5.duration(Mockito.<Duration>any())).thenReturn(nodeStateViewBuilder4);
    NodeStateView.NodeStateViewBuilder durationResult = nodeStateViewBuilder5.duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("42")
        .workflowId("42")
        .build();
    NodeStateView.NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId(null)
        .nodeId(null);
    NodeStateView.NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeStateView.builder());
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder2 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder2.instanceId(Mockito.<String>any())).thenReturn(nodeStateViewBuilder);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder3 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder3.group(Mockito.<String>any())).thenReturn(nodeStateViewBuilder2);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder4 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder4.endDate(Mockito.<Instant>any())).thenReturn(nodeStateViewBuilder3);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder5 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder5.duration(Mockito.<Duration>any())).thenReturn(nodeStateViewBuilder4);
    NodeStateView.NodeStateViewBuilder durationResult = nodeStateViewBuilder5.duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type(null)
        .workflowId("42")
        .build();
    NodeStateView.NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId(null)
        .nodeId(null);
    NodeStateView.NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeStateView.builder());
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder2 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder2.instanceId(Mockito.<String>any())).thenReturn(nodeStateViewBuilder);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder3 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder3.group(Mockito.<String>any())).thenReturn(nodeStateViewBuilder2);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder4 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder4.endDate(Mockito.<Instant>any())).thenReturn(nodeStateViewBuilder3);
    NodeStateView.NodeStateViewBuilder nodeStateViewBuilder5 = mock(NodeStateView.NodeStateViewBuilder.class);
    when(nodeStateViewBuilder5.duration(Mockito.<Duration>any())).thenReturn(nodeStateViewBuilder4);
    NodeStateView.NodeStateViewBuilder durationResult = nodeStateViewBuilder5.duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    NodeStateView.NodeStateViewBuilder durationResult2 = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group(null)
        .instanceId(null)
        .nodeId(null);
    NodeStateView.NodeStateViewBuilder outputsResult2 = nodeIdResult2.outputs(new HashMap<>());
    NodeStateView buildResult2 = outputsResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    NodeStateView.NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Method under test: {@link NodeStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    NodeStateView.NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to NodeStateView");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link NodeStateView#NodeStateView(String, String, String, String, String, Instant, Instant, Duration, Map)}
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
    actualNodeStateView.getDuration();
    Instant actualEndDate = actualNodeStateView.getEndDate();
    String actualGroup = actualNodeStateView.getGroup();
    String actualInstanceId = actualNodeStateView.getInstanceId();
    String actualNodeId = actualNodeStateView.getNodeId();
    Map<String, Object> actualOutputs = actualNodeStateView.getOutputs();
    Instant actualStartDate = actualNodeStateView.getStartDate();
    String actualType = actualNodeStateView.getType();

    // Assert that nothing has changed
    assertEquals("42", actualInstanceId);
    assertEquals("42", actualNodeId);
    assertEquals("42", actualNodeStateView.getWorkflowId());
    assertEquals("Group", actualGroup);
    assertEquals(
        "NodeStateView(workflowId=42, instanceId=42, nodeId=42, type=Type, group=Group, startDate=1970-01-01T00"
            + ":00:00Z, endDate=1970-01-01T00:00:00Z, duration=null, outputs={})",
        actualToStringResult);
    assertEquals("Type", actualType);
    assertTrue(actualOutputs.isEmpty());
    assertSame(outputs, actualOutputs);
    Instant instant = actualStartDate.EPOCH;
    assertSame(instant, actualEndDate);
    assertSame(instant, actualStartDate);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NodeStateView.NodeStateViewBuilder#build()}
   *   <li>{@link NodeStateView.NodeStateViewBuilder#duration(Duration)}
   *   <li>{@link NodeStateView.NodeStateViewBuilder#endDate(Instant)}
   *   <li>{@link NodeStateView.NodeStateViewBuilder#group(String)}
   *   <li>{@link NodeStateView.NodeStateViewBuilder#instanceId(String)}
   *   <li>{@link NodeStateView.NodeStateViewBuilder#nodeId(String)}
   *   <li>{@link NodeStateView.NodeStateViewBuilder#outputs(Map)}
   *   <li>{@link NodeStateView.NodeStateViewBuilder#startDate(Instant)}
   *   <li>{@link NodeStateView.NodeStateViewBuilder#type(String)}
   *   <li>{@link NodeStateView.NodeStateViewBuilder#workflowId(String)}
   * </ul>
   */
  @Test
  void testNodeStateViewBuilderBuild() {
    // Arrange
    NodeStateView.NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    HashMap<String, Object> outputs = new HashMap<>();
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(outputs);

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
