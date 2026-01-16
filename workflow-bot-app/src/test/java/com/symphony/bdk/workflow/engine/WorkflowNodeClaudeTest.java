package com.symphony.bdk.workflow.engine;

import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.event.FormRepliedEvent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkflowNodeClaudeTest {

  // ==================== id() Method Tests ====================

  @Test
  void id_withNonNullId_shouldSetIdAndReturnSelf() {
    // Given: A WorkflowNode and a non-null id
    WorkflowNode node = new WorkflowNode();
    String testId = "testId";

    // When: Setting the id
    WorkflowNode result = node.id(testId);

    // Then: The id should be set and the same instance should be returned
    assertThat(result).isSameAs(node);
    assertThat(node.getId()).isEqualTo("testId");
  }

  @Test
  void id_withNullId_shouldSetIdToNullAndReturnSelf() {
    // Given: A WorkflowNode with an existing id
    WorkflowNode node = new WorkflowNode();
    node.setId("existingId");

    // When: Setting the id to null
    WorkflowNode result = node.id(null);

    // Then: The id should be null and the same instance should be returned
    assertThat(result).isSameAs(node);
    assertThat(node.getId()).isNull();
  }

  @Test
  void id_withEmptyString_shouldSetIdToEmptyString() {
    // Given: A WorkflowNode
    WorkflowNode node = new WorkflowNode();

    // When: Setting the id to empty string
    WorkflowNode result = node.id("");

    // Then: The id should be empty string
    assertThat(result).isSameAs(node);
    assertThat(node.getId()).isEmpty();
  }

  @Test
  void id_fluentChaining_shouldAllowChaining() {
    // Given: A new WorkflowNode
    WorkflowNode node = new WorkflowNode();

    // When: Chaining multiple method calls
    WorkflowNode result = node.id("id1").eventId("eventId1");

    // Then: All values should be set and the same instance returned
    assertThat(result).isSameAs(node);
    assertThat(node.getId()).isEqualTo("id1");
    assertThat(node.getEventId()).isEqualTo("eventId1");
  }

  // ==================== eventId() Method Tests ====================

  @Test
  void eventId_withNonNullEventId_shouldSetEventIdAndReturnSelf() {
    // Given: A WorkflowNode and a non-null eventId
    WorkflowNode node = new WorkflowNode();
    String testEventId = "testEventId";

    // When: Setting the eventId
    WorkflowNode result = node.eventId(testEventId);

    // Then: The eventId should be set and the same instance should be returned
    assertThat(result).isSameAs(node);
    assertThat(node.getEventId()).isEqualTo("testEventId");
  }

  @Test
  void eventId_withNullEventId_shouldSetEventIdToNullAndReturnSelf() {
    // Given: A WorkflowNode with an existing eventId
    WorkflowNode node = new WorkflowNode();
    node.setEventId("existingEventId");

    // When: Setting the eventId to null
    WorkflowNode result = node.eventId(null);

    // Then: The eventId should be null and the same instance should be returned
    assertThat(result).isSameAs(node);
    assertThat(node.getEventId()).isNull();
  }

  @Test
  void eventId_withEmptyString_shouldSetEventIdToEmptyString() {
    // Given: A WorkflowNode
    WorkflowNode node = new WorkflowNode();

    // When: Setting the eventId to empty string
    WorkflowNode result = node.eventId("");

    // Then: The eventId should be empty string
    assertThat(result).isSameAs(node);
    assertThat(node.getEventId()).isEmpty();
  }

  // ==================== wrappedType() Method Tests ====================

  @Test
  void wrappedType_withNonNullClass_shouldSetWrappedTypeAndReturnSelf() {
    // Given: A WorkflowNode and a class type
    WorkflowNode node = new WorkflowNode();
    Class<?> testClass = String.class;

    // When: Setting the wrappedType
    WorkflowNode result = node.wrappedType(testClass);

    // Then: The wrappedType should be set and the same instance should be returned
    assertThat(result).isSameAs(node);
    assertThat(node.getWrappedType()).isEqualTo(String.class);
  }

  @Test
  void wrappedType_withNullClass_shouldSetWrappedTypeToNullAndReturnSelf() {
    // Given: A WorkflowNode with an existing wrappedType
    WorkflowNode node = new WorkflowNode();
    node.setWrappedType(Integer.class);

    // When: Setting the wrappedType to null
    WorkflowNode result = node.wrappedType(null);

    // Then: The wrappedType should be null and the same instance should be returned
    assertThat(result).isSameAs(node);
    assertThat(node.getWrappedType()).isNull();
  }

  @Test
  void wrappedType_withDifferentClassTypes_shouldSetCorrectly() {
    // Given: A WorkflowNode
    WorkflowNode node = new WorkflowNode();

    // When: Setting different class types
    node.wrappedType(Integer.class);
    assertThat(node.getWrappedType()).isEqualTo(Integer.class);

    node.wrappedType(Event.class);
    assertThat(node.getWrappedType()).isEqualTo(Event.class);

    node.wrappedType(WorkflowNode.class);
    assertThat(node.getWrappedType()).isEqualTo(WorkflowNode.class);
  }

  // ==================== activity() Method Tests ====================

  @Test
  void activity_withNonNullActivity_shouldSetActivityAndReturnSelf() {
    // Given: A WorkflowNode and a BaseActivity
    WorkflowNode node = new WorkflowNode();
    BaseActivity testActivity = new BaseActivity() {};
    testActivity.setId("activityId");

    // When: Setting the activity
    WorkflowNode result = node.activity(testActivity);

    // Then: The activity should be set and the same instance should be returned
    assertThat(result).isSameAs(node);
    assertThat(node.getActivity()).isSameAs(testActivity);
    assertThat(node.getActivity().getId()).isEqualTo("activityId");
  }

  @Test
  void activity_withNullActivity_shouldSetActivityToNullAndReturnSelf() {
    // Given: A WorkflowNode with an existing activity
    WorkflowNode node = new WorkflowNode();
    BaseActivity existingActivity = new BaseActivity() {};
    node.setActivity(existingActivity);

    // When: Setting the activity to null
    WorkflowNode result = node.activity(null);

    // Then: The activity should be null and the same instance should be returned
    assertThat(result).isSameAs(node);
    assertThat(node.getActivity()).isNull();
  }

  // ==================== event() Method Tests ====================

  @Test
  void event_withNonNullEvent_shouldSetEventAndReturnSelf() {
    // Given: A WorkflowNode and an Event
    WorkflowNode node = new WorkflowNode();
    Event testEvent = new Event();
    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setFormId("formId");
    testEvent.setFormReplied(formReplied);

    // When: Setting the event
    WorkflowNode result = node.event(testEvent);

    // Then: The event should be set and the same instance should be returned
    assertThat(result).isSameAs(node);
    assertThat(node.getEvent()).isSameAs(testEvent);
    assertThat(node.getEvent().getFormReplied().getFormId()).isEqualTo("formId");
  }

  @Test
  void event_withNullEvent_shouldSetEventToNullAndReturnSelf() {
    // Given: A WorkflowNode with an existing event
    WorkflowNode node = new WorkflowNode();
    Event existingEvent = new Event();
    node.setEvent(existingEvent);

    // When: Setting the event to null
    WorkflowNode result = node.event(null);

    // Then: The event should be null and the same instance should be returned
    assertThat(result).isSameAs(node);
    assertThat(node.getEvent()).isNull();
  }

  // ==================== elementType() Method Tests ====================

  @Test
  void elementType_withActivityType_shouldSetElementTypeAndReturnSelf() {
    // Given: A WorkflowNode
    WorkflowNode node = new WorkflowNode();

    // When: Setting the elementType to ACTIVITY
    WorkflowNode result = node.elementType(WorkflowNodeType.ACTIVITY);

    // Then: The elementType should be set and the same instance should be returned
    assertThat(result).isSameAs(node);
    assertThat(node.getElementType()).isEqualTo(WorkflowNodeType.ACTIVITY);
  }

  @Test
  void elementType_withFormRepliedEventType_shouldSetElementTypeAndReturnSelf() {
    // Given: A WorkflowNode
    WorkflowNode node = new WorkflowNode();

    // When: Setting the elementType to FORM_REPLIED_EVENT
    WorkflowNode result = node.elementType(WorkflowNodeType.FORM_REPLIED_EVENT);

    // Then: The elementType should be set and the same instance should be returned
    assertThat(result).isSameAs(node);
    assertThat(node.getElementType()).isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
  }

  @Test
  void elementType_withAllEnumValues_shouldSetCorrectly() {
    // Given: A WorkflowNode
    WorkflowNode node = new WorkflowNode();

    // When/Then: Testing all enum values
    for (WorkflowNodeType type : WorkflowNodeType.values()) {
      node.elementType(type);
      assertThat(node.getElementType()).isEqualTo(type);
    }
  }

  @Test
  void elementType_withNullType_shouldSetElementTypeToNullAndReturnSelf() {
    // Given: A WorkflowNode with an existing elementType
    WorkflowNode node = new WorkflowNode();
    node.setElementType(WorkflowNodeType.ACTIVITY);

    // When: Setting the elementType to null
    WorkflowNode result = node.elementType(null);

    // Then: The elementType should be null and the same instance should be returned
    assertThat(result).isSameAs(node);
    assertThat(node.getElementType()).isNull();
  }

  // ==================== addIfCondition() Method Tests ====================

  @Test
  void addIfCondition_withValidParentIdAndCondition_shouldAddConditionAndReturnSelf() {
    // Given: A WorkflowNode
    WorkflowNode node = new WorkflowNode();

    // When: Adding an if condition
    WorkflowNode result = node.addIfCondition("parentId1", "condition1");

    // Then: The condition should be added and the same instance should be returned
    assertThat(result).isSameAs(node);
    assertThat(node.getIfConditions()).containsEntry("parentId1", "condition1");
  }

  @Test
  void addIfCondition_withMultipleConditions_shouldAddAllConditions() {
    // Given: A WorkflowNode
    WorkflowNode node = new WorkflowNode();

    // When: Adding multiple if conditions
    node.addIfCondition("parent1", "condition1")
        .addIfCondition("parent2", "condition2")
        .addIfCondition("parent3", "condition3");

    // Then: All conditions should be added
    assertThat(node.getIfConditions()).hasSize(3);
    assertThat(node.getIfConditions()).containsEntry("parent1", "condition1");
    assertThat(node.getIfConditions()).containsEntry("parent2", "condition2");
    assertThat(node.getIfConditions()).containsEntry("parent3", "condition3");
  }

  @Test
  void addIfCondition_withDuplicateParentId_shouldOverwriteExistingCondition() {
    // Given: A WorkflowNode with an existing condition
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parent1", "oldCondition");

    // When: Adding a new condition with the same parent ID
    node.addIfCondition("parent1", "newCondition");

    // Then: The condition should be overwritten
    assertThat(node.getIfConditions()).hasSize(1);
    assertThat(node.getIfConditions()).containsEntry("parent1", "newCondition");
  }

  @Test
  void addIfCondition_withNullParentId_shouldAddWithNullKey() {
    // Given: A WorkflowNode
    WorkflowNode node = new WorkflowNode();

    // When: Adding a condition with null parent ID
    node.addIfCondition(null, "condition");

    // Then: The condition should be added with null key
    assertThat(node.getIfConditions()).containsEntry(null, "condition");
  }

  @Test
  void addIfCondition_withNullCondition_shouldAddWithNullValue() {
    // Given: A WorkflowNode
    WorkflowNode node = new WorkflowNode();

    // When: Adding a null condition
    node.addIfCondition("parent1", null);

    // Then: The condition should be added with null value
    assertThat(node.getIfConditions()).containsEntry("parent1", null);
  }

  @Test
  void addIfCondition_withEmptyStrings_shouldAddEmptyValues() {
    // Given: A WorkflowNode
    WorkflowNode node = new WorkflowNode();

    // When: Adding conditions with empty strings
    node.addIfCondition("", "condition");
    node.addIfCondition("parent", "");

    // Then: The conditions should be added with empty values
    assertThat(node.getIfConditions()).containsEntry("", "condition");
    assertThat(node.getIfConditions()).containsEntry("parent", "");
  }

  // ==================== isConditional() Method Tests ====================

  @Test
  void isConditional_withNoConditions_shouldReturnFalse() {
    // Given: A WorkflowNode with no conditions
    WorkflowNode node = new WorkflowNode();

    // When/Then: isConditional should return false
    assertThat(node.isConditional()).isFalse();
  }

  @Test
  void isConditional_withOneCondition_shouldReturnTrue() {
    // Given: A WorkflowNode with one condition
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parent1", "condition1");

    // When/Then: isConditional should return true
    assertThat(node.isConditional()).isTrue();
  }

  @Test
  void isConditional_withMultipleConditions_shouldReturnTrue() {
    // Given: A WorkflowNode with multiple conditions
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parent1", "condition1");
    node.addIfCondition("parent2", "condition2");

    // When/Then: isConditional should return true
    assertThat(node.isConditional()).isTrue();
  }

  @Test
  void isConditional_afterClearingConditions_shouldReturnFalse() {
    // Given: A WorkflowNode with conditions that are then cleared
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parent1", "condition1");
    node.getIfConditions().clear();

    // When/Then: isConditional should return false after clearing
    assertThat(node.isConditional()).isFalse();
  }

  // ==================== isConditional(String) Method Tests ====================

  @Test
  void isConditionalWithParentId_withNoConditions_shouldReturnFalse() {
    // Given: A WorkflowNode with no conditions
    WorkflowNode node = new WorkflowNode();

    // When/Then: isConditional with any parent ID should return false
    assertThat(node.isConditional("parent1")).isFalse();
  }

  @Test
  void isConditionalWithParentId_withMatchingParentId_shouldReturnTrue() {
    // Given: A WorkflowNode with a condition for parent1
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parent1", "condition1");

    // When/Then: isConditional with matching parent ID should return true
    assertThat(node.isConditional("parent1")).isTrue();
  }

  @Test
  void isConditionalWithParentId_withNonMatchingParentId_shouldReturnFalse() {
    // Given: A WorkflowNode with a condition for parent1
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parent1", "condition1");

    // When/Then: isConditional with non-matching parent ID should return false
    assertThat(node.isConditional("parent2")).isFalse();
  }

  @Test
  void isConditionalWithParentId_withMultipleConditions_shouldReturnTrueOnlyForExistingKeys() {
    // Given: A WorkflowNode with multiple conditions
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parent1", "condition1");
    node.addIfCondition("parent2", "condition2");

    // When/Then: isConditional should return true for existing keys, false otherwise
    assertThat(node.isConditional("parent1")).isTrue();
    assertThat(node.isConditional("parent2")).isTrue();
    assertThat(node.isConditional("parent3")).isFalse();
  }

  @Test
  void isConditionalWithParentId_withNullParentId_shouldReturnTrueIfNullKeyExists() {
    // Given: A WorkflowNode with a null parent ID condition
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition(null, "condition");

    // When/Then: isConditional with null should return true
    assertThat(node.isConditional(null)).isTrue();
  }

  @Test
  void isConditionalWithParentId_withNullParentIdButNoNullKey_shouldReturnFalse() {
    // Given: A WorkflowNode with only non-null keys
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parent1", "condition1");

    // When/Then: isConditional with null should return false
    assertThat(node.isConditional(null)).isFalse();
  }

  @Test
  void isConditionalWithParentId_withEmptyStringParentId_shouldReturnTrueIfEmptyKeyExists() {
    // Given: A WorkflowNode with an empty string parent ID condition
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("", "condition");

    // When/Then: isConditional with empty string should return true
    assertThat(node.isConditional("")).isTrue();
  }

  // ==================== getIfCondition() Method Tests ====================

  @Test
  void getIfCondition_withExistingParentId_shouldReturnCondition() {
    // Given: A WorkflowNode with a condition
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parent1", "condition1");

    // When: Getting the condition
    String condition = node.getIfCondition("parent1");

    // Then: The correct condition should be returned
    assertThat(condition).isEqualTo("condition1");
  }

  @Test
  void getIfCondition_withNonExistingParentId_shouldReturnNull() {
    // Given: A WorkflowNode with a condition for parent1
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parent1", "condition1");

    // When: Getting a condition for non-existing parent
    String condition = node.getIfCondition("parent2");

    // Then: Null should be returned
    assertThat(condition).isNull();
  }

  @Test
  void getIfCondition_withMultipleConditions_shouldReturnCorrectCondition() {
    // Given: A WorkflowNode with multiple conditions
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parent1", "condition1");
    node.addIfCondition("parent2", "condition2");
    node.addIfCondition("parent3", "condition3");

    // When/Then: Each parent should return its own condition
    assertThat(node.getIfCondition("parent1")).isEqualTo("condition1");
    assertThat(node.getIfCondition("parent2")).isEqualTo("condition2");
    assertThat(node.getIfCondition("parent3")).isEqualTo("condition3");
  }

  @Test
  void getIfCondition_withNullParentId_shouldReturnNullKeyValue() {
    // Given: A WorkflowNode with a null parent ID condition
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition(null, "nullCondition");

    // When: Getting the condition with null key
    String condition = node.getIfCondition(null);

    // Then: The condition should be returned
    assertThat(condition).isEqualTo("nullCondition");
  }

  @Test
  void getIfCondition_withEmptyStringParentId_shouldReturnEmptyKeyValue() {
    // Given: A WorkflowNode with an empty string parent ID condition
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("", "emptyCondition");

    // When: Getting the condition with empty key
    String condition = node.getIfCondition("");

    // Then: The condition should be returned
    assertThat(condition).isEqualTo("emptyCondition");
  }

  @Test
  void getIfCondition_withNoConditions_shouldReturnNull() {
    // Given: A WorkflowNode with no conditions
    WorkflowNode node = new WorkflowNode();

    // When: Getting a condition
    String condition = node.getIfCondition("parent1");

    // Then: Null should be returned
    assertThat(condition).isNull();
  }

  // ==================== isNotExclusiveFormReply() Method Tests ====================

  @Test
  void isNotExclusiveFormReply_withFormRepliedEventAndExclusiveFalse_shouldReturnTrue() {
    // Given: A WorkflowNode with FORM_REPLIED_EVENT and exclusive=false
    WorkflowNode node = new WorkflowNode();
    Event event = new Event();
    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(false);
    event.setFormReplied(formReplied);

    node.elementType(WorkflowNodeType.FORM_REPLIED_EVENT)
        .event(event);

    // When/Then: isNotExclusiveFormReply should return true
    assertThat(node.isNotExclusiveFormReply()).isTrue();
  }

  @Test
  void isNotExclusiveFormReply_withFormRepliedEventAndExclusiveTrue_shouldReturnFalse() {
    // Given: A WorkflowNode with FORM_REPLIED_EVENT and exclusive=true
    WorkflowNode node = new WorkflowNode();
    Event event = new Event();
    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    event.setFormReplied(formReplied);

    node.elementType(WorkflowNodeType.FORM_REPLIED_EVENT)
        .event(event);

    // When/Then: isNotExclusiveFormReply should return false
    assertThat(node.isNotExclusiveFormReply()).isFalse();
  }

  @Test
  void isNotExclusiveFormReply_withFormRepliedEventAndDefaultExclusive_shouldReturnFalse() {
    // Given: A WorkflowNode with FORM_REPLIED_EVENT and default exclusive (true)
    WorkflowNode node = new WorkflowNode();
    Event event = new Event();
    FormRepliedEvent formReplied = new FormRepliedEvent();
    // exclusive defaults to true in FormRepliedEvent
    event.setFormReplied(formReplied);

    node.elementType(WorkflowNodeType.FORM_REPLIED_EVENT)
        .event(event);

    // When/Then: isNotExclusiveFormReply should return false
    assertThat(node.isNotExclusiveFormReply()).isFalse();
  }

  @Test
  void isNotExclusiveFormReply_withNonFormRepliedEventType_shouldReturnFalse() {
    // Given: A WorkflowNode with ACTIVITY type
    WorkflowNode node = new WorkflowNode();
    Event event = new Event();
    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(false);
    event.setFormReplied(formReplied);

    node.elementType(WorkflowNodeType.ACTIVITY)
        .event(event);

    // When/Then: isNotExclusiveFormReply should return false
    assertThat(node.isNotExclusiveFormReply()).isFalse();
  }

  @Test
  void isNotExclusiveFormReply_withSignalEventType_shouldReturnFalse() {
    // Given: A WorkflowNode with SIGNAL_EVENT type
    WorkflowNode node = new WorkflowNode();
    Event event = new Event();
    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(false);
    event.setFormReplied(formReplied);

    node.elementType(WorkflowNodeType.SIGNAL_EVENT)
        .event(event);

    // When/Then: isNotExclusiveFormReply should return false
    assertThat(node.isNotExclusiveFormReply()).isFalse();
  }

  @Test
  void isNotExclusiveFormReply_withTimerFiredEventType_shouldReturnFalse() {
    // Given: A WorkflowNode with TIMER_FIRED_EVENT type
    WorkflowNode node = new WorkflowNode();
    Event event = new Event();
    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(false);
    event.setFormReplied(formReplied);

    node.elementType(WorkflowNodeType.TIMER_FIRED_EVENT)
        .event(event);

    // When/Then: isNotExclusiveFormReply should return false
    assertThat(node.isNotExclusiveFormReply()).isFalse();
  }

  // ==================== Integration Tests with Full Object Construction ====================

  @Test
  void workflowNode_fluentApiChaining_shouldConstructCompleteNode() {
    // Given: A new WorkflowNode
    WorkflowNode node = new WorkflowNode();
    Event event = new Event();
    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(false);
    event.setFormReplied(formReplied);

    // When: Building a complete node using fluent API
    WorkflowNode result = node
        .id("nodeId1")
        .eventId("eventId1")
        .wrappedType(String.class)
        .event(event)
        .elementType(WorkflowNodeType.FORM_REPLIED_EVENT)
        .addIfCondition("parent1", "condition1")
        .addIfCondition("parent2", "condition2");

    // Then: All fields should be set correctly
    assertThat(result).isSameAs(node);
    assertThat(node.getId()).isEqualTo("nodeId1");
    assertThat(node.getEventId()).isEqualTo("eventId1");
    assertThat(node.getWrappedType()).isEqualTo(String.class);
    assertThat(node.getEvent()).isSameAs(event);
    assertThat(node.getElementType()).isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
    assertThat(node.getIfConditions()).hasSize(2);
    assertThat(node.isConditional()).isTrue();
    assertThat(node.isNotExclusiveFormReply()).isTrue();
  }

  @Test
  void workflowNode_activityNode_shouldConstructCorrectly() {
    // Given: Activity node components
    BaseActivity activity = new BaseActivity() {};
    activity.setId("activityId");

    // When: Building an activity node
    WorkflowNode node = new WorkflowNode()
        .id("activityId")
        .eventId("activityId")
        .wrappedType(BaseActivity.class)
        .activity(activity)
        .elementType(WorkflowNodeType.ACTIVITY);

    // Then: The activity node should be constructed correctly
    assertThat(node.getId()).isEqualTo("activityId");
    assertThat(node.getEventId()).isEqualTo("activityId");
    assertThat(node.getWrappedType()).isEqualTo(BaseActivity.class);
    assertThat(node.getActivity()).isSameAs(activity);
    assertThat(node.getElementType()).isEqualTo(WorkflowNodeType.ACTIVITY);
    assertThat(node.isConditional()).isFalse();
  }

  @Test
  void workflowNode_eventNode_shouldConstructCorrectly() {
    // Given: Event node components
    Event event = new Event();
    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setFormId("formId");
    event.setFormReplied(formReplied);

    // When: Building an event node
    WorkflowNode node = new WorkflowNode()
        .id("eventId")
        .eventId("eventId")
        .wrappedType(FormRepliedEvent.class)
        .event(event)
        .elementType(WorkflowNodeType.FORM_REPLIED_EVENT);

    // Then: The event node should be constructed correctly
    assertThat(node.getId()).isEqualTo("eventId");
    assertThat(node.getEventId()).isEqualTo("eventId");
    assertThat(node.getWrappedType()).isEqualTo(FormRepliedEvent.class);
    assertThat(node.getEvent()).isSameAs(event);
    assertThat(node.getElementType()).isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
    assertThat(node.isConditional()).isFalse();
  }

  @Test
  void workflowNode_conditionalNode_shouldHandleConditionsCorrectly() {
    // Given: A node with multiple conditions
    WorkflowNode node = new WorkflowNode()
        .id("conditionalNode")
        .addIfCondition("parent1", "${workflow.status == 'active'}")
        .addIfCondition("parent2", "${workflow.count > 0}")
        .addIfCondition("parent3", "${workflow.enabled}");

    // When/Then: All conditional methods should work correctly
    assertThat(node.isConditional()).isTrue();
    assertThat(node.isConditional("parent1")).isTrue();
    assertThat(node.isConditional("parent2")).isTrue();
    assertThat(node.isConditional("parent3")).isTrue();
    assertThat(node.isConditional("parent4")).isFalse();
    assertThat(node.getIfCondition("parent1")).isEqualTo("${workflow.status == 'active'}");
    assertThat(node.getIfCondition("parent2")).isEqualTo("${workflow.count > 0}");
    assertThat(node.getIfCondition("parent3")).isEqualTo("${workflow.enabled}");
  }

  // ==================== Lombok Generated Methods Tests ====================

  @Test
  void workflowNode_equalsAndHashCode_withIdenticalNodes_shouldBeEqual() {
    // Given: Two identical WorkflowNodes
    WorkflowNode node1 = new WorkflowNode()
        .id("id1")
        .eventId("eventId1")
        .elementType(WorkflowNodeType.ACTIVITY);

    WorkflowNode node2 = new WorkflowNode()
        .id("id1")
        .eventId("eventId1")
        .elementType(WorkflowNodeType.ACTIVITY);

    // When/Then: They should be equal
    assertThat(node1).isEqualTo(node2);
    assertThat(node1.hashCode()).isEqualTo(node2.hashCode());
  }

  @Test
  void workflowNode_equalsAndHashCode_withDifferentIds_shouldNotBeEqual() {
    // Given: Two WorkflowNodes with different ids
    WorkflowNode node1 = new WorkflowNode().id("id1");
    WorkflowNode node2 = new WorkflowNode().id("id2");

    // When/Then: They should not be equal
    assertThat(node1).isNotEqualTo(node2);
  }

  @Test
  void workflowNode_noArgsConstructor_shouldInitializeWithDefaults() {
    // Given/When: Creating a WorkflowNode with no-args constructor
    WorkflowNode node = new WorkflowNode();

    // Then: All fields should be null or default values except ifConditions
    assertThat(node.getId()).isNull();
    assertThat(node.getEventId()).isNull();
    assertThat(node.getWrappedType()).isNull();
    assertThat(node.getActivity()).isNull();
    assertThat(node.getEvent()).isNull();
    assertThat(node.getElementType()).isNull();
    assertThat(node.getIfConditions()).isNotNull().isEmpty();
  }

  @Test
  void workflowNode_toString_shouldContainFieldValues() {
    // Given: A WorkflowNode with some fields set
    WorkflowNode node = new WorkflowNode()
        .id("testId")
        .eventId("testEventId")
        .elementType(WorkflowNodeType.ACTIVITY);

    // When: Calling toString (Lombok @Data generates toString)
    String result = node.toString();

    // Then: The string should contain field values
    assertThat(result).contains("testId");
    assertThat(result).contains("testEventId");
    assertThat(result).contains("ACTIVITY");
  }
}
