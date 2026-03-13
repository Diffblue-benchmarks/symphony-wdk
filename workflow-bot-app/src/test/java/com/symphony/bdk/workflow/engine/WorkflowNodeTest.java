package com.symphony.bdk.workflow.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.event.FormRepliedEvent;

import org.junit.jupiter.api.Test;

class WorkflowNodeTest {

  @Test
  void shouldSetIdAndReturnSelf() {
    WorkflowNode node = new WorkflowNode();

    WorkflowNode result = node.id("test-id");

    assertThat(result).isSameAs(node);
    assertThat(node.getId()).isEqualTo("test-id");
  }

  @Test
  void shouldSetEventIdAndReturnSelf() {
    WorkflowNode node = new WorkflowNode();

    WorkflowNode result = node.eventId("event-123");

    assertThat(result).isSameAs(node);
    assertThat(node.getEventId()).isEqualTo("event-123");
  }

  @Test
  void shouldSetWrappedTypeAndReturnSelf() {
    WorkflowNode node = new WorkflowNode();

    WorkflowNode result = node.wrappedType(String.class);

    assertThat(result).isSameAs(node);
    assertThat(node.getWrappedType()).isEqualTo(String.class);
  }

  @Test
  void shouldSetActivityAndReturnSelf() {
    WorkflowNode node = new WorkflowNode();
    BaseActivity activity = mock(BaseActivity.class);

    WorkflowNode result = node.activity(activity);

    assertThat(result).isSameAs(node);
    assertThat(node.getActivity()).isEqualTo(activity);
  }

  @Test
  void shouldSetEventAndReturnSelf() {
    WorkflowNode node = new WorkflowNode();
    Event event = new Event();

    WorkflowNode result = node.event(event);

    assertThat(result).isSameAs(node);
    assertThat(node.getEvent()).isEqualTo(event);
  }

  @Test
  void shouldSetElementTypeAndReturnSelf() {
    WorkflowNode node = new WorkflowNode();

    WorkflowNode result = node.elementType(WorkflowNodeType.ACTIVITY);

    assertThat(result).isSameAs(node);
    assertThat(node.getElementType()).isEqualTo(WorkflowNodeType.ACTIVITY);
  }

  @Test
  void shouldAddIfConditionAndReturnSelf() {
    WorkflowNode node = new WorkflowNode();

    WorkflowNode result = node.addIfCondition("parent-1", "condition-expression");

    assertThat(result).isSameAs(node);
    assertThat(node.getIfConditions()).containsEntry("parent-1", "condition-expression");
  }

  @Test
  void shouldReturnFalseWhenNoIfConditions() {
    WorkflowNode node = new WorkflowNode();

    boolean result = node.isConditional();

    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnTrueWhenIfConditionsExist() {
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parent-1", "condition");

    boolean result = node.isConditional();

    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenParentIdNotInConditions() {
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parent-1", "condition");

    boolean result = node.isConditional("parent-2");

    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnTrueWhenParentIdInConditions() {
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parent-1", "condition");

    boolean result = node.isConditional("parent-1");

    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenNoConditionsAndCheckingParentId() {
    WorkflowNode node = new WorkflowNode();

    boolean result = node.isConditional("parent-1");

    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnIfConditionForParentId() {
    WorkflowNode node = new WorkflowNode();
    node.addIfCondition("parent-1", "test-condition");

    String result = node.getIfCondition("parent-1");

    assertThat(result).isEqualTo("test-condition");
  }

  @Test
  void shouldReturnNullWhenIfConditionNotFoundForParentId() {
    WorkflowNode node = new WorkflowNode();

    String result = node.getIfCondition("parent-1");

    assertThat(result).isNull();
  }

  @Test
  void shouldReturnTrueWhenFormReplyIsNotExclusive() {
    WorkflowNode node = new WorkflowNode();
    Event event = new Event();
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(false);
    event.setFormReplied(formRepliedEvent);

    node.setEvent(event);
    node.setElementType(WorkflowNodeType.FORM_REPLIED_EVENT);

    boolean result = node.isNotExclusiveFormReply();

    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenFormReplyIsExclusive() {
    WorkflowNode node = new WorkflowNode();
    Event event = new Event();
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(true);
    event.setFormReplied(formRepliedEvent);

    node.setEvent(event);
    node.setElementType(WorkflowNodeType.FORM_REPLIED_EVENT);

    boolean result = node.isNotExclusiveFormReply();

    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnFalseWhenNotFormRepliedEvent() {
    WorkflowNode node = new WorkflowNode();
    Event event = new Event();

    node.setEvent(event);
    node.setElementType(WorkflowNodeType.ACTIVITY);

    boolean result = node.isNotExclusiveFormReply();

    assertThat(result).isFalse();
  }
}
