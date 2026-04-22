package com.symphony.bdk.workflow.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EventVisitorDiffblueTest {

  /**
   * Test {@link EventVisitor#predict(Event)}.
   *
   * <p>Method under test: {@link EventVisitor#predict(Event)}
   */
  @Test
  @DisplayName("Test predict(Event) via EventVisitor interface reference")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean EventVisitor.predict(Event)"})
  void testPredict() {
    // Arrange
    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("/test");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(false);

    Event event = new Event();
    event.setMessageReceived(messageReceived);

    EventVisitor visitor = WorkflowEventType.MESSAGE_RECEIVED;

    // Act
    boolean result = visitor.predict(event);

    // Assert
    assertTrue(result);
  }

  /**
   * Test {@link EventVisitor#getEventTripleInfo(Event, String, String)}.
   *
   * <p>Method under test: {@link EventVisitor#getEventTripleInfo(Event, String, String)}
   */
  @Test
  @DisplayName("Test getEventTripleInfo(Event, String, String) via EventVisitor interface reference")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Triple EventVisitor.getEventTripleInfo(Event, String, String)"})
  void testGetEventTripleInfo() {
    // Arrange
    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("/test");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(false);

    Event event = new Event();
    event.setMessageReceived(messageReceived);

    EventVisitor visitor = WorkflowEventType.MESSAGE_RECEIVED;

    // Act
    Triple<String, String, Class<?>> result = visitor.getEventTripleInfo(event, "workflowId", "botName");

    // Assert
    assertEquals("42", result.getLeft());
    assertEquals(MessageReceivedEvent.class, result.getRight());
  }
}
