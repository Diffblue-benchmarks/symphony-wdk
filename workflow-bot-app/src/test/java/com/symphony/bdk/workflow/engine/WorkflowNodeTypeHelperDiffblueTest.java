package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class WorkflowNodeTypeHelperDiffblueTest {
  /**
   * Test {@link WorkflowNodeTypeHelper#toType(String)}.
   *
   * <ul>
   *   <li>When {@code _EVENT}.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNodeTypeHelper#toType(String)}
   */
  @Test
  @DisplayName("Test toType(String); when '_EVENT'; then return empty string")
  @Tag("MaintainedByDiffblue")
  void testToType_whenEvent_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", WorkflowNodeTypeHelper.toType("_EVENT"));
  }

  /**
   * Test {@link WorkflowNodeTypeHelper#toType(String)}.
   *
   * <ul>
   *   <li>When {@code _GATEWAY}.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNodeTypeHelper#toType(String)}
   */
  @Test
  @DisplayName("Test toType(String); when '_GATEWAY'; then return empty string")
  @Tag("MaintainedByDiffblue")
  void testToType_whenGateway_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", WorkflowNodeTypeHelper.toType("_GATEWAY"));
  }

  /**
   * Test {@link WorkflowNodeTypeHelper#toType(String)}.
   *
   * <ul>
   *   <li>When {@code Name}.
   *   <li>Then return {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNodeTypeHelper#toType(String)}
   */
  @Test
  @DisplayName("Test toType(String); when 'Name'; then return 'Name'")
  @Tag("MaintainedByDiffblue")
  void testToType_whenName_thenReturnName() {
    // Arrange, Act and Assert
    assertEquals("Name", WorkflowNodeTypeHelper.toType("Name"));
  }

  /**
   * Test {@link WorkflowNodeTypeHelper#toGroup(String)}.
   *
   * <ul>
   *   <li>When {@code _EVENT}.
   *   <li>Then return {@code EVENT}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNodeTypeHelper#toGroup(String)}
   */
  @Test
  @DisplayName("Test toGroup(String); when '_EVENT'; then return 'EVENT'")
  @Tag("MaintainedByDiffblue")
  void testToGroup_whenEvent_thenReturnEvent() {
    // Arrange, Act and Assert
    assertEquals("EVENT", WorkflowNodeTypeHelper.toGroup("_EVENT"));
  }

  /**
   * Test {@link WorkflowNodeTypeHelper#toGroup(String)}.
   *
   * <ul>
   *   <li>When {@code _GATEWAY}.
   *   <li>Then return {@code GATEWAY}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNodeTypeHelper#toGroup(String)}
   */
  @Test
  @DisplayName("Test toGroup(String); when '_GATEWAY'; then return 'GATEWAY'")
  @Tag("MaintainedByDiffblue")
  void testToGroup_whenGateway_thenReturnGateway() {
    // Arrange, Act and Assert
    assertEquals("GATEWAY", WorkflowNodeTypeHelper.toGroup("_GATEWAY"));
  }

  /**
   * Test {@link WorkflowNodeTypeHelper#toGroup(String)}.
   *
   * <ul>
   *   <li>When {@code Name}.
   *   <li>Then return {@code ACTIVITY}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNodeTypeHelper#toGroup(String)}
   */
  @Test
  @DisplayName("Test toGroup(String); when 'Name'; then return 'ACTIVITY'")
  @Tag("MaintainedByDiffblue")
  void testToGroup_whenName_thenReturnActivity() {
    // Arrange, Act and Assert
    assertEquals("ACTIVITY", WorkflowNodeTypeHelper.toGroup("Name"));
  }

  /**
   * Test {@link WorkflowNodeTypeHelper#toUpperUnderscore(String)}.
   *
   * <ul>
   *   <li>When {@code EVENT}.
   *   <li>Then return {@code E_V_E_N_T}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNodeTypeHelper#toUpperUnderscore(String)}
   */
  @Test
  @DisplayName("Test toUpperUnderscore(String); when 'EVENT'; then return 'E_V_E_N_T'")
  @Tag("MaintainedByDiffblue")
  void testToUpperUnderscore_whenEvent_thenReturnEVENT() {
    // Arrange, Act and Assert
    assertEquals("E_V_E_N_T", WorkflowNodeTypeHelper.toUpperUnderscore("EVENT"));
  }

  /**
   * Test {@link WorkflowNodeTypeHelper#toUpperUnderscore(String)}.
   *
   * <ul>
   *   <li>When {@code Name}.
   *   <li>Then return {@code NAME}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNodeTypeHelper#toUpperUnderscore(String)}
   */
  @Test
  @DisplayName("Test toUpperUnderscore(String); when 'Name'; then return 'NAME'")
  @Tag("MaintainedByDiffblue")
  void testToUpperUnderscore_whenName_thenReturnName() {
    // Arrange, Act and Assert
    assertEquals("NAME", WorkflowNodeTypeHelper.toUpperUnderscore("Name"));
  }
}
