package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup;
import com.symphony.bdk.workflow.swadl.v1.event.FormRepliedEvent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WorkflowNodeTest {

  @Test
  @DisplayName("Test id(String) sets id and returns this")
  void testId_setsIdAndReturnsThis() {
    // Arrange
    WorkflowNode node = new WorkflowNode();

    // Act
    WorkflowNode result = node.id("myId");

    // Assert
    assertSame(node, result);
    assertEquals("myId", node.getId());
  }

  @Test
  @DisplayName("Test eventId(String) sets eventId and returns this")
  void testEventId_setsEventIdAndReturnsThis() {
    // Arrange
    WorkflowNode node = new WorkflowNode();

    // Act
    WorkflowNode result = node.eventId("evtId");

    // Assert
    assertSame(node, result);
    assertEquals("evtId", node.getEventId());
  }

  @Test
  @DisplayName("Test wrappedType(Class) sets wrappedType and returns this")
  void testWrappedType_setsWrappedTypeAndReturnsThis() {
    // Arrange
    WorkflowNode node = new WorkflowNode();

    // Act
    WorkflowNode result = node.wrappedType(String.class);

    // Assert
    assertSame(node, result);
    assertEquals(String.class, node.getWrappedType());
  }

  @Test
  @DisplayName("Test activity(BaseActivity) sets activity and returns this")
  void testActivity_setsActivityAndReturnsThis() {
    // Arrange
    WorkflowNode node = new WorkflowNode();
    BaseActivity activity = new CreateGroup();

    // Act
    WorkflowNode result = node.activity(activity);

    // Assert
    assertSame(node, result);
    assertSame(activity, node.getActivity());
  }

  @Test
  @DisplayName("Test event(Event) sets event and returns this")
  void testEvent_setsEventAndReturnsThis() {
    // Arrange
    WorkflowNode node = new WorkflowNode();
    Event event = new Event();

    // Act
    WorkflowNode result = node.event(event);

    // Assert
    assertSame(node, result);
    assertSame(event, node.getEvent());
  }

  @Test
  @DisplayName("Test elementType(WorkflowNodeType) sets elementType and returns this")
  void testElementType_setsElementTypeAndReturnsThis() {
    // Arrange
    WorkflowNode node = new WorkflowNode();

    // Act
    WorkflowNode result = node.elementType(WorkflowNodeType.ACTIVITY);

    // Assert
    assertSame(node, result);
    assertEquals(WorkflowNodeType.ACTIVITY, node.getElementType());
  }

  @Test
  @DisplayName("Test addIfCondition(String, String) stores condition and returns this")
  void testAddIfCondition_storesConditionAndReturnsThis() {
    // Arrange
    WorkflowNode node = new WorkflowNode();

    // Act
    WorkflowNode result = node.addIfCondition("parent1", "condition1");

    // Assert
    assertSame(node, result);
    assertEquals("condition1", node.getIfConditions().get("parent1"));
  }

  @Test
  @DisplayName("Test isConditional() returns false when no conditions")
  void testIsConditional_returnsFalseWhenNoConditions() {
    // Arrange
    WorkflowNode node = new WorkflowNode();

    // Act and Assert
    assertFalse(node.isConditional());
  }

  @Test
  @DisplayName("Test isConditional() returns true when conditions present")
  void testIsConditional_returnsTrueWhenConditionsPresent() {
    // Arrange
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parentId", "someCondition");

    // Act and Assert
    assertTrue(node.isConditional());
  }

  @Test
  @DisplayName("Test isConditional(String) returns false when parentId not in conditions")
  void testIsConditionalWithParentId_returnsFalseWhenParentIdNotFound() {
    // Arrange
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("otherParent", "condition");

    // Act and Assert
    assertFalse(node.isConditional("parentId"));
  }

  @Test
  @DisplayName("Test isConditional(String) returns true when parentId found in conditions")
  void testIsConditionalWithParentId_returnsTrueWhenParentIdFound() {
    // Arrange
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parentId", "condition");

    // Act and Assert
    assertTrue(node.isConditional("parentId"));
  }

  @Test
  @DisplayName("Test isConditional(String) returns false when no conditions at all")
  void testIsConditionalWithParentId_returnsFalseWhenNoConditions() {
    // Arrange
    WorkflowNode node = new WorkflowNode();

    // Act and Assert
    assertFalse(node.isConditional("parentId"));
  }

  @Test
  @DisplayName("Test getIfCondition(String) returns null when not present")
  void testGetIfCondition_returnsNullWhenNotPresent() {
    // Arrange
    WorkflowNode node = new WorkflowNode();

    // Act and Assert
    assertNull(node.getIfCondition("parentId"));
  }

  @Test
  @DisplayName("Test getIfCondition(String) returns condition when present")
  void testGetIfCondition_returnsConditionWhenPresent() {
    // Arrange
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parentId", "myCondition");

    // Act and Assert
    assertEquals("myCondition", node.getIfCondition("parentId"));
  }

  @Test
  @DisplayName("Test isNotExclusiveFormReply() returns true when exclusive is false")
  void testIsNotExclusiveFormReply_returnsTrueWhenExclusiveIsFalse() {
    // Arrange
    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(false);
    Event event = new Event();
    event.setFormReplied(formReplied);

    WorkflowNode node = new WorkflowNode();
    node.event(event);
    node.elementType(WorkflowNodeType.FORM_REPLIED_EVENT);

    // Act and Assert
    assertTrue(node.isNotExclusiveFormReply());
  }

  @Test
  @DisplayName("Test isNotExclusiveFormReply() returns false when exclusive is true")
  void testIsNotExclusiveFormReply_returnsFalseWhenExclusiveIsTrue() {
    // Arrange
    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    Event event = new Event();
    event.setFormReplied(formReplied);

    WorkflowNode node = new WorkflowNode();
    node.event(event);
    node.elementType(WorkflowNodeType.FORM_REPLIED_EVENT);

    // Act and Assert
    assertFalse(node.isNotExclusiveFormReply());
  }
}
