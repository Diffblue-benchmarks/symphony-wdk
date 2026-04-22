package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WorkflowNodeTypeHelperTest {

  /**
   * Test {@link WorkflowNodeTypeHelper#toType(String)}.
   *
   * <ul>
   *   <li>When name ends with {@code _EVENT}.
   *   <li>Then return name without the suffix.
   * </ul>
   */
  @Test
  @DisplayName("Test toType(String); when name ends with '_EVENT'; then strip suffix")
  void testToType_whenEndsWithEvent_thenStripSuffix() {
    // Arrange, Act and Assert
    assertEquals("SEND_MESSAGE", WorkflowNodeTypeHelper.toType("SEND_MESSAGE_EVENT"));
  }

  /**
   * Test {@link WorkflowNodeTypeHelper#toType(String)}.
   *
   * <ul>
   *   <li>When name ends with {@code _GATEWAY}.
   *   <li>Then return name without the suffix.
   * </ul>
   */
  @Test
  @DisplayName("Test toType(String); when name ends with '_GATEWAY'; then strip suffix")
  void testToType_whenEndsWithGateway_thenStripSuffix() {
    // Arrange, Act and Assert
    assertEquals("EXCLUSIVE", WorkflowNodeTypeHelper.toType("EXCLUSIVE_GATEWAY"));
  }

  /**
   * Test {@link WorkflowNodeTypeHelper#toType(String)}.
   *
   * <ul>
   *   <li>When name has no special suffix.
   *   <li>Then return name unchanged.
   * </ul>
   */
  @Test
  @DisplayName("Test toType(String); when no special suffix; then return name unchanged")
  void testToType_whenNoSuffix_thenReturnNameUnchanged() {
    // Arrange, Act and Assert
    assertEquals("SEND_MESSAGE", WorkflowNodeTypeHelper.toType("SEND_MESSAGE"));
  }

  /**
   * Test {@link WorkflowNodeTypeHelper#toGroup(String)}.
   *
   * <ul>
   *   <li>When name ends with {@code _EVENT}.
   *   <li>Then return {@code EVENT}.
   * </ul>
   */
  @Test
  @DisplayName("Test toGroup(String); when name ends with '_EVENT'; then return 'EVENT'")
  void testToGroup_whenEndsWithEvent_thenReturnEvent() {
    // Arrange, Act and Assert
    assertEquals("EVENT", WorkflowNodeTypeHelper.toGroup("SEND_MESSAGE_EVENT"));
  }

  /**
   * Test {@link WorkflowNodeTypeHelper#toGroup(String)}.
   *
   * <ul>
   *   <li>When name ends with {@code _GATEWAY}.
   *   <li>Then return {@code GATEWAY}.
   * </ul>
   */
  @Test
  @DisplayName("Test toGroup(String); when name ends with '_GATEWAY'; then return 'GATEWAY'")
  void testToGroup_whenEndsWithGateway_thenReturnGateway() {
    // Arrange, Act and Assert
    assertEquals("GATEWAY", WorkflowNodeTypeHelper.toGroup("EXCLUSIVE_GATEWAY"));
  }

  /**
   * Test {@link WorkflowNodeTypeHelper#toGroup(String)}.
   *
   * <ul>
   *   <li>When name has no special suffix.
   *   <li>Then return {@code ACTIVITY}.
   * </ul>
   */
  @Test
  @DisplayName("Test toGroup(String); when no special suffix; then return 'ACTIVITY'")
  void testToGroup_whenNoSuffix_thenReturnActivity() {
    // Arrange, Act and Assert
    assertEquals("ACTIVITY", WorkflowNodeTypeHelper.toGroup("SEND_MESSAGE"));
  }

  /**
   * Test {@link WorkflowNodeTypeHelper#toUpperUnderscore(String)}.
   *
   * <ul>
   *   <li>When camelCase input.
   *   <li>Then return UPPER_UNDERSCORE.
   * </ul>
   */
  @Test
  @DisplayName("Test toUpperUnderscore(String); when camelCase; then return UPPER_UNDERSCORE")
  void testToUpperUnderscore_whenCamelCase_thenReturnUpperUnderscore() {
    // Arrange, Act and Assert
    assertEquals("SEND_MESSAGE", WorkflowNodeTypeHelper.toUpperUnderscore("sendMessage"));
  }

  /**
   * Test {@link WorkflowNodeTypeHelper#toUpperUnderscore(String)}.
   *
   * <ul>
   *   <li>When all lowercase input.
   *   <li>Then return all uppercase.
   * </ul>
   */
  @Test
  @DisplayName("Test toUpperUnderscore(String); when lowercase; then return uppercase")
  void testToUpperUnderscore_whenLowercase_thenReturnUppercase() {
    // Arrange, Act and Assert
    assertEquals("NAME", WorkflowNodeTypeHelper.toUpperUnderscore("name"));
  }

  /**
   * Test {@link WorkflowNodeTypeHelper#toUpperUnderscore(String)}.
   *
   * <ul>
   *   <li>When empty string input.
   *   <li>Then return empty string.
   * </ul>
   */
  @Test
  @DisplayName("Test toUpperUnderscore(String); when empty string; then return empty string")
  void testToUpperUnderscore_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", WorkflowNodeTypeHelper.toUpperUnderscore(""));
  }

  /**
   * Test {@link WorkflowNodeTypeHelper#toUpperUnderscore(String)}.
   *
   * <ul>
   *   <li>When null input.
   *   <li>Then throw NullPointerException.
   * </ul>
   */
  @Test
  @DisplayName("Test toUpperUnderscore(String); when null; then throw NullPointerException")
  void testToUpperUnderscore_whenNull_thenThrowNullPointerException() {
    // Arrange, Act and Assert
    assertThrows(NullPointerException.class, () -> WorkflowNodeTypeHelper.toUpperUnderscore(null));
  }
}
