package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class WorkflowNodeTypeHelperDiffblueTest {
  /**
   * Method under test: {@link WorkflowNodeTypeHelper#toType(String)}
   */
  @Test
  void testToType() {
    // Arrange, Act and Assert
    assertEquals("Name", WorkflowNodeTypeHelper.toType("Name"));
    assertEquals("", WorkflowNodeTypeHelper.toType("_EVENT"));
    assertEquals("", WorkflowNodeTypeHelper.toType("_GATEWAY"));
  }

  /**
   * Method under test: {@link WorkflowNodeTypeHelper#toGroup(String)}
   */
  @Test
  void testToGroup() {
    // Arrange, Act and Assert
    assertEquals("ACTIVITY", WorkflowNodeTypeHelper.toGroup("Name"));
    assertEquals("EVENT", WorkflowNodeTypeHelper.toGroup("_EVENT"));
    assertEquals("GATEWAY", WorkflowNodeTypeHelper.toGroup("_GATEWAY"));
  }

  /**
   * Method under test: {@link WorkflowNodeTypeHelper#toUpperUnderscore(String)}
   */
  @Test
  void testToUpperUnderscore() {
    // Arrange, Act and Assert
    assertEquals("NAME", WorkflowNodeTypeHelper.toUpperUnderscore("Name"));
    assertEquals("E_V_E_N_T", WorkflowNodeTypeHelper.toUpperUnderscore("EVENT"));
  }
}
