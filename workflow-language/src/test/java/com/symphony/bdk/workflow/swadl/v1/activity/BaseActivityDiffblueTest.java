package com.symphony.bdk.workflow.swadl.v1.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityCompletedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityExpiredEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityFailedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ConnectionAcceptedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ConnectionRequestedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.FormRepliedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ImCreatedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.MessageSuppressedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.PostSharedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RequestReceivedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomCreatedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomDeactivatedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomMemberDemotedFromOwnerEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomMemberPromotedToOwnerEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomReactivatedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomUpdatedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.TimerFiredEvent;
import com.symphony.bdk.workflow.swadl.v1.event.UserJoinedRoomEvent;
import com.symphony.bdk.workflow.swadl.v1.event.UserLeftRoomEvent;
import com.symphony.bdk.workflow.swadl.v1.event.UserRequestedToJoinRoomEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BaseActivityDiffblueTest {
  /**
   * Method under test: {@link BaseActivity#add(String, Object)}
   */
  @Test
  void testAdd() {
    // Arrange
    Debug debug = new Debug();

    // Act
    debug.add("Key", "Value");

    // Assert
    Map<String, Object> variableProperties = debug.getVariableProperties();
    assertEquals(1, variableProperties.size());
    assertEquals("Value", variableProperties.get("Key"));
  }

  /**
   * Method under test: {@link BaseActivity#getEvents()}
   */
  @Test
  void testGetEvents() {
    // Arrange and Act
    RelationalEvents actualEvents = (new Debug()).getEvents();

    // Assert
    assertNull(actualEvents.getParentId());
    assertFalse(actualEvents.isParallel());
    assertTrue(actualEvents.isEmpty());
    assertTrue(actualEvents.getEvents().isEmpty());
  }

  /**
   * Method under test: {@link BaseActivity#getEvents()}
   */
  @Test
  void testGetEvents2() {
    // Arrange
    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");

    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");

    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");

    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");

    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(new HashMap<>());
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");

    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");

    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");

    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");

    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");

    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("At");
    timerFired.setId("42");
    timerFired.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");

    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");

    EventWithTimeout on = new EventWithTimeout();
    on.setActivityCompleted(activityCompleted);
    on.setActivityExpired(activityExpired);
    on.setActivityFailed(activityFailed);
    on.setAllOf(new ArrayList<>());
    on.setConnectionAccepted(connectionAccepted);
    on.setConnectionRequested(connectionRequested);
    on.setFormReplied(formReplied);
    on.setImCreated(imCreated);
    on.setMessageReceived(messageReceived);
    on.setMessageSuppressed(messageSuppressed);
    ArrayList<Event> oneOf = new ArrayList<>();
    on.setOneOf(oneOf);
    on.setPostShared(postShared);
    on.setRequestReceived(requestReceived);
    on.setRoomCreated(roomCreated);
    on.setRoomDeactivated(roomDeactivated);
    on.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    on.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    on.setRoomReactivated(roomReactivated);
    on.setRoomUpdated(roomUpdated);
    on.setTimeout("Timeout");
    on.setTimerFired(timerFired);
    on.setUserJoinedRoom(userJoinedRoom);
    on.setUserLeftRoom(userLeftRoom);
    on.setUserRequestedJoinRoom(userRequestedJoinRoom);

    Debug debug = new Debug();
    debug.setOn(on);

    // Act
    RelationalEvents actualEvents = debug.getEvents();

    // Assert
    assertNull(actualEvents.getParentId());
    assertFalse(actualEvents.isParallel());
    assertTrue(actualEvents.isEmpty());
    List<Event> events = actualEvents.getEvents();
    assertTrue(events.isEmpty());
    assertSame(oneOf, events);
  }

  /**
   * Method under test: {@link BaseActivity#getEvents()}
   */
  @Test
  void testGetEvents3() {
    // Arrange
    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");

    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");

    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");

    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");

    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(new HashMap<>());
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");

    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");

    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");

    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");

    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");

    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("At");
    timerFired.setId("42");
    timerFired.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");

    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");

    EventWithTimeout on = new EventWithTimeout();
    on.setActivityCompleted(activityCompleted);
    on.setActivityExpired(activityExpired);
    on.setActivityFailed(activityFailed);
    on.setConnectionAccepted(connectionAccepted);
    on.setConnectionRequested(connectionRequested);
    on.setFormReplied(formReplied);
    on.setImCreated(imCreated);
    on.setMessageReceived(messageReceived);
    on.setMessageSuppressed(messageSuppressed);
    on.setPostShared(postShared);
    on.setRequestReceived(requestReceived);
    on.setRoomCreated(roomCreated);
    on.setRoomDeactivated(roomDeactivated);
    on.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    on.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    on.setRoomReactivated(roomReactivated);
    on.setRoomUpdated(roomUpdated);
    on.setTimeout("Timeout");
    on.setTimerFired(timerFired);
    on.setUserJoinedRoom(userJoinedRoom);
    on.setUserLeftRoom(userLeftRoom);
    on.setUserRequestedJoinRoom(userRequestedJoinRoom);
    on.setOneOf(null);
    on.setAllOf(null);

    Debug debug = new Debug();
    debug.setOn(on);

    // Act
    RelationalEvents actualEvents = debug.getEvents();

    // Assert
    assertNull(actualEvents.getParentId());
    List<Event> events = actualEvents.getEvents();
    assertEquals(1, events.size());
    assertFalse(actualEvents.isEmpty());
    assertFalse(actualEvents.isParallel());
    assertSame(on, events.get(0));
  }

  /**
   * Method under test: {@link BaseActivity#getEvents()}
   */
  @Test
  void testGetEvents4() {
    // Arrange
    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");

    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");

    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");

    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");

    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(new HashMap<>());
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");

    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");

    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");

    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");

    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");

    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("At");
    timerFired.setId("42");
    timerFired.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");

    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");

    EventWithTimeout on = new EventWithTimeout();
    on.setActivityCompleted(activityCompleted);
    on.setActivityExpired(activityExpired);
    on.setActivityFailed(activityFailed);
    on.setConnectionAccepted(connectionAccepted);
    on.setConnectionRequested(connectionRequested);
    on.setFormReplied(formReplied);
    on.setImCreated(imCreated);
    on.setMessageReceived(messageReceived);
    on.setMessageSuppressed(messageSuppressed);
    on.setPostShared(postShared);
    on.setRequestReceived(requestReceived);
    on.setRoomCreated(roomCreated);
    on.setRoomDeactivated(roomDeactivated);
    on.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    on.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    on.setRoomReactivated(roomReactivated);
    on.setRoomUpdated(roomUpdated);
    on.setTimeout("Timeout");
    on.setTimerFired(timerFired);
    on.setUserJoinedRoom(userJoinedRoom);
    on.setUserLeftRoom(userLeftRoom);
    on.setUserRequestedJoinRoom(userRequestedJoinRoom);
    on.setOneOf(null);
    ArrayList<Event> allOf = new ArrayList<>();
    on.setAllOf(allOf);

    Debug debug = new Debug();
    debug.setOn(on);

    // Act
    RelationalEvents actualEvents = debug.getEvents();

    // Assert
    assertNull(actualEvents.getParentId());
    assertTrue(actualEvents.isEmpty());
    assertTrue(actualEvents.isParallel());
    List<Event> events = actualEvents.getEvents();
    assertTrue(events.isEmpty());
    assertSame(allOf, events);
  }

  /**
   * Method under test: {@link BaseActivity#canEqual(Object)}
   */
  @Test
  void testCanEqual() {
    // Arrange, Act and Assert
    assertFalse((new Debug()).canEqual("Other"));
  }

  /**
   * Method under test: {@link BaseActivity#canEqual(Object)}
   */
  @Test
  void testCanEqual2() {
    // Arrange
    Debug debug = new Debug();

    // Act and Assert
    assertTrue(debug.canEqual(new Debug()));
  }

  /**
   * Method under test: {@link BaseActivity#equals(Object)}
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Debug debug = new Debug();

    // Act and Assert
    assertEquals(debug, debug);
    int expectedHashCodeResult = debug.hashCode();
    assertEquals(expectedHashCodeResult, debug.hashCode());
  }

  /**
   * Method under test: {@link BaseActivity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Debug debug = new Debug();

    // Act and Assert
    assertNotEquals(debug, new Debug());
  }

  /**
   * Method under test: {@link BaseActivity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Debug debug = new Debug();
    Debug debug2 = mock(Debug.class);
    when(debug2.getId()).thenReturn("42");
    when(debug2.canEqual(Mockito.<Object>any())).thenReturn(true);

    // Act and Assert
    assertNotEquals(debug, debug2);
  }

  /**
   * Method under test: {@link BaseActivity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Debug debug = new Debug();
    debug.setId("42");

    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");

    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");

    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");

    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");

    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(new HashMap<>());
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");

    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");

    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");

    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");

    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");

    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("At");
    timerFired.setId("42");
    timerFired.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");

    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");

    EventWithTimeout eventWithTimeout = new EventWithTimeout();
    eventWithTimeout.setActivityCompleted(activityCompleted);
    eventWithTimeout.setActivityExpired(activityExpired);
    eventWithTimeout.setActivityFailed(activityFailed);
    eventWithTimeout.setAllOf(new ArrayList<>());
    eventWithTimeout.setConnectionAccepted(connectionAccepted);
    eventWithTimeout.setConnectionRequested(connectionRequested);
    eventWithTimeout.setFormReplied(formReplied);
    eventWithTimeout.setImCreated(imCreated);
    eventWithTimeout.setMessageReceived(messageReceived);
    eventWithTimeout.setMessageSuppressed(messageSuppressed);
    eventWithTimeout.setOneOf(new ArrayList<>());
    eventWithTimeout.setPostShared(postShared);
    eventWithTimeout.setRequestReceived(requestReceived);
    eventWithTimeout.setRoomCreated(roomCreated);
    eventWithTimeout.setRoomDeactivated(roomDeactivated);
    eventWithTimeout.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    eventWithTimeout.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    eventWithTimeout.setRoomReactivated(roomReactivated);
    eventWithTimeout.setRoomUpdated(roomUpdated);
    eventWithTimeout.setTimeout("Timeout");
    eventWithTimeout.setTimerFired(timerFired);
    eventWithTimeout.setUserJoinedRoom(userJoinedRoom);
    eventWithTimeout.setUserLeftRoom(userLeftRoom);
    eventWithTimeout.setUserRequestedJoinRoom(userRequestedJoinRoom);
    Debug debug2 = mock(Debug.class);
    when(debug2.getOn()).thenReturn(eventWithTimeout);
    when(debug2.getElseCondition()).thenReturn("Else Condition");
    when(debug2.getObject()).thenReturn("Object");
    when(debug2.getIfCondition()).thenReturn("If Condition");
    when(debug2.getVariableProperties()).thenReturn(new HashMap<>());
    when(debug2.getId()).thenReturn("42");
    when(debug2.canEqual(Mockito.<Object>any())).thenReturn(true);

    // Act and Assert
    assertNotEquals(debug, debug2);
  }

  /**
   * Method under test: {@link BaseActivity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Debug debug = new Debug();
    debug.setId(null);

    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");

    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");

    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");

    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");

    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(new HashMap<>());
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");

    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");

    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");

    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");

    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");

    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("At");
    timerFired.setId("42");
    timerFired.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");

    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");

    EventWithTimeout eventWithTimeout = new EventWithTimeout();
    eventWithTimeout.setActivityCompleted(activityCompleted);
    eventWithTimeout.setActivityExpired(activityExpired);
    eventWithTimeout.setActivityFailed(activityFailed);
    eventWithTimeout.setAllOf(new ArrayList<>());
    eventWithTimeout.setConnectionAccepted(connectionAccepted);
    eventWithTimeout.setConnectionRequested(connectionRequested);
    eventWithTimeout.setFormReplied(formReplied);
    eventWithTimeout.setImCreated(imCreated);
    eventWithTimeout.setMessageReceived(messageReceived);
    eventWithTimeout.setMessageSuppressed(messageSuppressed);
    eventWithTimeout.setOneOf(new ArrayList<>());
    eventWithTimeout.setPostShared(postShared);
    eventWithTimeout.setRequestReceived(requestReceived);
    eventWithTimeout.setRoomCreated(roomCreated);
    eventWithTimeout.setRoomDeactivated(roomDeactivated);
    eventWithTimeout.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    eventWithTimeout.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    eventWithTimeout.setRoomReactivated(roomReactivated);
    eventWithTimeout.setRoomUpdated(roomUpdated);
    eventWithTimeout.setTimeout("Timeout");
    eventWithTimeout.setTimerFired(timerFired);
    eventWithTimeout.setUserJoinedRoom(userJoinedRoom);
    eventWithTimeout.setUserLeftRoom(userLeftRoom);
    eventWithTimeout.setUserRequestedJoinRoom(userRequestedJoinRoom);
    Debug debug2 = mock(Debug.class);
    when(debug2.getOn()).thenReturn(eventWithTimeout);
    when(debug2.getElseCondition()).thenReturn("Else Condition");
    when(debug2.getObject()).thenReturn("Object");
    when(debug2.getIfCondition()).thenReturn("If Condition");
    when(debug2.getVariableProperties()).thenReturn(new HashMap<>());
    when(debug2.getId()).thenReturn("42");
    when(debug2.canEqual(Mockito.<Object>any())).thenReturn(true);

    // Act and Assert
    assertNotEquals(debug, debug2);
  }

  /**
   * Method under test: {@link BaseActivity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("42");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");

    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");

    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");

    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");

    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(new HashMap<>());
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");

    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");

    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");

    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");

    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");

    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("42");
    timerFired.setId("42");
    timerFired.setRepeat("42");

    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");

    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");

    EventWithTimeout on = new EventWithTimeout();
    on.setActivityCompleted(activityCompleted);
    on.setActivityExpired(activityExpired);
    on.setActivityFailed(activityFailed);
    on.setAllOf(new ArrayList<>());
    on.setConnectionAccepted(connectionAccepted);
    on.setConnectionRequested(connectionRequested);
    on.setFormReplied(formReplied);
    on.setImCreated(imCreated);
    on.setMessageReceived(messageReceived);
    on.setMessageSuppressed(messageSuppressed);
    on.setOneOf(new ArrayList<>());
    on.setPostShared(postShared);
    on.setRequestReceived(requestReceived);
    on.setRoomCreated(roomCreated);
    on.setRoomDeactivated(roomDeactivated);
    on.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    on.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    on.setRoomReactivated(roomReactivated);
    on.setRoomUpdated(roomUpdated);
    on.setTimeout("42");
    on.setTimerFired(timerFired);
    on.setUserJoinedRoom(userJoinedRoom);
    on.setUserLeftRoom(userLeftRoom);
    on.setUserRequestedJoinRoom(userRequestedJoinRoom);

    Debug debug = new Debug();
    debug.setOn(on);
    debug.setId("42");

    ActivityCompletedEvent activityCompleted2 = new ActivityCompletedEvent();
    activityCompleted2.setActivityId("42");
    activityCompleted2.setId("42");
    activityCompleted2.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired2 = new ActivityExpiredEvent();
    activityExpired2.setActivityId("42");
    activityExpired2.setId("42");

    ActivityFailedEvent activityFailed2 = new ActivityFailedEvent();
    activityFailed2.setActivityId("42");
    activityFailed2.setId("42");

    ConnectionAcceptedEvent connectionAccepted2 = new ConnectionAcceptedEvent();
    connectionAccepted2.setId("42");

    ConnectionRequestedEvent connectionRequested2 = new ConnectionRequestedEvent();
    connectionRequested2.setId("42");

    FormRepliedEvent formReplied2 = new FormRepliedEvent();
    formReplied2.setExclusive(true);
    formReplied2.setFormId("42");
    formReplied2.setId("42");

    ImCreatedEvent imCreated2 = new ImCreatedEvent();
    imCreated2.setId("42");

    MessageReceivedEvent messageReceived2 = new MessageReceivedEvent();
    messageReceived2.setContent("Not all who wander are lost");
    messageReceived2.setId("42");
    messageReceived2.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed2 = new MessageSuppressedEvent();
    messageSuppressed2.setId("42");

    PostSharedEvent postShared2 = new PostSharedEvent();
    postShared2.setId("42");

    RequestReceivedEvent requestReceived2 = new RequestReceivedEvent();
    requestReceived2.setArguments(new HashMap<>());
    requestReceived2.setId("42");
    requestReceived2.setToken("ABC123");
    requestReceived2.setWorkflowId("42");

    RoomCreatedEvent roomCreated2 = new RoomCreatedEvent();
    roomCreated2.setId("42");

    RoomDeactivatedEvent roomDeactivated2 = new RoomDeactivatedEvent();
    roomDeactivated2.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner2 = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner2.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner2 = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner2.setId("42");

    RoomReactivatedEvent roomReactivated2 = new RoomReactivatedEvent();
    roomReactivated2.setId("42");

    RoomUpdatedEvent roomUpdated2 = new RoomUpdatedEvent();
    roomUpdated2.setId("42");

    TimerFiredEvent timerFired2 = new TimerFiredEvent();
    timerFired2.setAt("At");
    timerFired2.setId("42");
    timerFired2.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom2 = new UserJoinedRoomEvent();
    userJoinedRoom2.setId("42");

    UserLeftRoomEvent userLeftRoom2 = new UserLeftRoomEvent();
    userLeftRoom2.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom2 = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom2.setId("42");

    EventWithTimeout eventWithTimeout = new EventWithTimeout();
    eventWithTimeout.setActivityCompleted(activityCompleted2);
    eventWithTimeout.setActivityExpired(activityExpired2);
    eventWithTimeout.setActivityFailed(activityFailed2);
    eventWithTimeout.setAllOf(new ArrayList<>());
    eventWithTimeout.setConnectionAccepted(connectionAccepted2);
    eventWithTimeout.setConnectionRequested(connectionRequested2);
    eventWithTimeout.setFormReplied(formReplied2);
    eventWithTimeout.setImCreated(imCreated2);
    eventWithTimeout.setMessageReceived(messageReceived2);
    eventWithTimeout.setMessageSuppressed(messageSuppressed2);
    eventWithTimeout.setOneOf(new ArrayList<>());
    eventWithTimeout.setPostShared(postShared2);
    eventWithTimeout.setRequestReceived(requestReceived2);
    eventWithTimeout.setRoomCreated(roomCreated2);
    eventWithTimeout.setRoomDeactivated(roomDeactivated2);
    eventWithTimeout.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner2);
    eventWithTimeout.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner2);
    eventWithTimeout.setRoomReactivated(roomReactivated2);
    eventWithTimeout.setRoomUpdated(roomUpdated2);
    eventWithTimeout.setTimeout("Timeout");
    eventWithTimeout.setTimerFired(timerFired2);
    eventWithTimeout.setUserJoinedRoom(userJoinedRoom2);
    eventWithTimeout.setUserLeftRoom(userLeftRoom2);
    eventWithTimeout.setUserRequestedJoinRoom(userRequestedJoinRoom2);
    Debug debug2 = mock(Debug.class);
    when(debug2.getOn()).thenReturn(eventWithTimeout);
    when(debug2.getElseCondition()).thenReturn("Else Condition");
    when(debug2.getObject()).thenReturn("Object");
    when(debug2.getIfCondition()).thenReturn("If Condition");
    when(debug2.getVariableProperties()).thenReturn(new HashMap<>());
    when(debug2.getId()).thenReturn("42");
    when(debug2.canEqual(Mockito.<Object>any())).thenReturn(true);

    // Act and Assert
    assertNotEquals(debug, debug2);
  }

  /**
   * Method under test: {@link BaseActivity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Debug(), null);
  }

  /**
   * Method under test: {@link BaseActivity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Debug(), "Different type to BaseActivity");
  }

  /**
   * Method under test: {@link BaseActivity#getElseCondition()}
   */
  @Test
  void testGetElseCondition() {
    // Arrange, Act and Assert
    assertNull((new Debug()).getElseCondition());
  }

  /**
   * Method under test: {@link BaseActivity#getElseCondition()}
   */
  @Test
  void testGetElseCondition2() {
    // Arrange
    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");

    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");

    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");

    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");

    HashMap<String, Object> arguments = new HashMap<>();
    arguments.computeIfPresent("foo", mock(BiFunction.class));

    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(arguments);
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");

    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");

    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");

    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");

    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");

    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("At");
    timerFired.setId("42");
    timerFired.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");

    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");

    EventWithTimeout on = new EventWithTimeout();
    on.setActivityCompleted(activityCompleted);
    on.setActivityExpired(activityExpired);
    on.setActivityFailed(activityFailed);
    on.setAllOf(new ArrayList<>());
    on.setConnectionAccepted(connectionAccepted);
    on.setConnectionRequested(connectionRequested);
    on.setFormReplied(formReplied);
    on.setImCreated(imCreated);
    on.setMessageReceived(messageReceived);
    on.setMessageSuppressed(messageSuppressed);
    on.setOneOf(new ArrayList<>());
    on.setPostShared(postShared);
    on.setRequestReceived(requestReceived);
    on.setRoomCreated(roomCreated);
    on.setRoomDeactivated(roomDeactivated);
    on.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    on.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    on.setRoomReactivated(roomReactivated);
    on.setRoomUpdated(roomUpdated);
    on.setTimeout("Timeout");
    on.setTimerFired(timerFired);
    on.setUserJoinedRoom(userJoinedRoom);
    on.setUserLeftRoom(userLeftRoom);
    on.setUserRequestedJoinRoom(userRequestedJoinRoom);

    Debug debug = new Debug();
    debug.setOn(on);

    // Act and Assert
    assertNull(debug.getElseCondition());
  }

  /**
   * Method under test: {@link BaseActivity#getId()}
   */
  @Test
  void testGetId() {
    // Arrange
    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");

    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");

    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");

    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");

    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(new HashMap<>());
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");

    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");

    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");

    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");

    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");

    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("At");
    timerFired.setId("42");
    timerFired.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");

    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");
    EventWithTimeout on = mock(EventWithTimeout.class);
    doNothing().when(on).setActivityCompleted(Mockito.<ActivityCompletedEvent>any());
    doNothing().when(on).setActivityExpired(Mockito.<ActivityExpiredEvent>any());
    doNothing().when(on).setActivityFailed(Mockito.<ActivityFailedEvent>any());
    doNothing().when(on).setAllOf(Mockito.<List<Event>>any());
    doNothing().when(on).setConnectionAccepted(Mockito.<ConnectionAcceptedEvent>any());
    doNothing().when(on).setConnectionRequested(Mockito.<ConnectionRequestedEvent>any());
    doNothing().when(on).setFormReplied(Mockito.<FormRepliedEvent>any());
    doNothing().when(on).setImCreated(Mockito.<ImCreatedEvent>any());
    doNothing().when(on).setMessageReceived(Mockito.<MessageReceivedEvent>any());
    doNothing().when(on).setMessageSuppressed(Mockito.<MessageSuppressedEvent>any());
    doNothing().when(on).setOneOf(Mockito.<List<Event>>any());
    doNothing().when(on).setPostShared(Mockito.<PostSharedEvent>any());
    doNothing().when(on).setRequestReceived(Mockito.<RequestReceivedEvent>any());
    doNothing().when(on).setRoomCreated(Mockito.<RoomCreatedEvent>any());
    doNothing().when(on).setRoomDeactivated(Mockito.<RoomDeactivatedEvent>any());
    doNothing().when(on).setRoomMemberDemotedFromOwner(Mockito.<RoomMemberDemotedFromOwnerEvent>any());
    doNothing().when(on).setRoomMemberPromotedToOwner(Mockito.<RoomMemberPromotedToOwnerEvent>any());
    doNothing().when(on).setRoomReactivated(Mockito.<RoomReactivatedEvent>any());
    doNothing().when(on).setRoomUpdated(Mockito.<RoomUpdatedEvent>any());
    doNothing().when(on).setTimerFired(Mockito.<TimerFiredEvent>any());
    doNothing().when(on).setUserJoinedRoom(Mockito.<UserJoinedRoomEvent>any());
    doNothing().when(on).setUserLeftRoom(Mockito.<UserLeftRoomEvent>any());
    doNothing().when(on).setUserRequestedJoinRoom(Mockito.<UserRequestedToJoinRoomEvent>any());
    doNothing().when(on).setTimeout(Mockito.<String>any());
    on.setActivityCompleted(activityCompleted);
    on.setActivityExpired(activityExpired);
    on.setActivityFailed(activityFailed);
    on.setAllOf(new ArrayList<>());
    on.setConnectionAccepted(connectionAccepted);
    on.setConnectionRequested(connectionRequested);
    on.setFormReplied(formReplied);
    on.setImCreated(imCreated);
    on.setMessageReceived(messageReceived);
    on.setMessageSuppressed(messageSuppressed);
    on.setOneOf(new ArrayList<>());
    on.setPostShared(postShared);
    on.setRequestReceived(requestReceived);
    on.setRoomCreated(roomCreated);
    on.setRoomDeactivated(roomDeactivated);
    on.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    on.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    on.setRoomReactivated(roomReactivated);
    on.setRoomUpdated(roomUpdated);
    on.setTimeout("Timeout");
    on.setTimerFired(timerFired);
    on.setUserJoinedRoom(userJoinedRoom);
    on.setUserLeftRoom(userLeftRoom);
    on.setUserRequestedJoinRoom(userRequestedJoinRoom);

    Debug debug = new Debug();
    debug.setOn(on);

    // Act
    debug.getId();

    // Assert
    verify(on).setActivityCompleted(isA(ActivityCompletedEvent.class));
    verify(on).setActivityExpired(isA(ActivityExpiredEvent.class));
    verify(on).setActivityFailed(isA(ActivityFailedEvent.class));
    verify(on).setAllOf(isA(List.class));
    verify(on).setConnectionAccepted(isA(ConnectionAcceptedEvent.class));
    verify(on).setConnectionRequested(isA(ConnectionRequestedEvent.class));
    verify(on).setFormReplied(isA(FormRepliedEvent.class));
    verify(on).setImCreated(isA(ImCreatedEvent.class));
    verify(on).setMessageReceived(isA(MessageReceivedEvent.class));
    verify(on).setMessageSuppressed(isA(MessageSuppressedEvent.class));
    verify(on).setOneOf(isA(List.class));
    verify(on).setPostShared(isA(PostSharedEvent.class));
    verify(on).setRequestReceived(isA(RequestReceivedEvent.class));
    verify(on).setRoomCreated(isA(RoomCreatedEvent.class));
    verify(on).setRoomDeactivated(isA(RoomDeactivatedEvent.class));
    verify(on).setRoomMemberDemotedFromOwner(isA(RoomMemberDemotedFromOwnerEvent.class));
    verify(on).setRoomMemberPromotedToOwner(isA(RoomMemberPromotedToOwnerEvent.class));
    verify(on).setRoomReactivated(isA(RoomReactivatedEvent.class));
    verify(on).setRoomUpdated(isA(RoomUpdatedEvent.class));
    verify(on).setTimerFired(isA(TimerFiredEvent.class));
    verify(on).setUserJoinedRoom(isA(UserJoinedRoomEvent.class));
    verify(on).setUserLeftRoom(isA(UserLeftRoomEvent.class));
    verify(on).setUserRequestedJoinRoom(isA(UserRequestedToJoinRoomEvent.class));
    verify(on).setTimeout(eq("Timeout"));
  }

  /**
   * Method under test: {@link BaseActivity#getIfCondition()}
   */
  @Test
  void testGetIfCondition() {
    // Arrange, Act and Assert
    assertNull((new Debug()).getIfCondition());
  }

  /**
   * Method under test: {@link BaseActivity#getIfCondition()}
   */
  @Test
  void testGetIfCondition2() {
    // Arrange
    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");

    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");

    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");

    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");

    HashMap<String, Object> arguments = new HashMap<>();
    arguments.computeIfPresent("foo", mock(BiFunction.class));

    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(arguments);
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");

    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");

    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");

    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");

    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");

    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("At");
    timerFired.setId("42");
    timerFired.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");

    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");

    EventWithTimeout on = new EventWithTimeout();
    on.setActivityCompleted(activityCompleted);
    on.setActivityExpired(activityExpired);
    on.setActivityFailed(activityFailed);
    on.setAllOf(new ArrayList<>());
    on.setConnectionAccepted(connectionAccepted);
    on.setConnectionRequested(connectionRequested);
    on.setFormReplied(formReplied);
    on.setImCreated(imCreated);
    on.setMessageReceived(messageReceived);
    on.setMessageSuppressed(messageSuppressed);
    on.setOneOf(new ArrayList<>());
    on.setPostShared(postShared);
    on.setRequestReceived(requestReceived);
    on.setRoomCreated(roomCreated);
    on.setRoomDeactivated(roomDeactivated);
    on.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    on.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    on.setRoomReactivated(roomReactivated);
    on.setRoomUpdated(roomUpdated);
    on.setTimeout("Timeout");
    on.setTimerFired(timerFired);
    on.setUserJoinedRoom(userJoinedRoom);
    on.setUserLeftRoom(userLeftRoom);
    on.setUserRequestedJoinRoom(userRequestedJoinRoom);

    Debug debug = new Debug();
    debug.setOn(on);

    // Act and Assert
    assertNull(debug.getIfCondition());
  }

  /**
   * Method under test: {@link BaseActivity#getOn()}
   */
  @Test
  void testGetOn() {
    // Arrange, Act and Assert
    assertNull((new Debug()).getOn());
  }

  /**
   * Method under test: {@link BaseActivity#getOn()}
   */
  @Test
  void testGetOn2() {
    // Arrange
    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");

    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");

    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");

    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");

    HashMap<String, Object> arguments = new HashMap<>();
    arguments.computeIfPresent("foo", mock(BiFunction.class));

    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(arguments);
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");

    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");

    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");

    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");

    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");

    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("At");
    timerFired.setId("42");
    timerFired.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");

    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");

    EventWithTimeout on = new EventWithTimeout();
    on.setActivityCompleted(activityCompleted);
    on.setActivityExpired(activityExpired);
    on.setActivityFailed(activityFailed);
    on.setAllOf(new ArrayList<>());
    on.setConnectionAccepted(connectionAccepted);
    on.setConnectionRequested(connectionRequested);
    on.setFormReplied(formReplied);
    on.setImCreated(imCreated);
    on.setMessageReceived(messageReceived);
    on.setMessageSuppressed(messageSuppressed);
    on.setOneOf(new ArrayList<>());
    on.setPostShared(postShared);
    on.setRequestReceived(requestReceived);
    on.setRoomCreated(roomCreated);
    on.setRoomDeactivated(roomDeactivated);
    on.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    on.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    on.setRoomReactivated(roomReactivated);
    on.setRoomUpdated(roomUpdated);
    on.setTimeout("Timeout");
    on.setTimerFired(timerFired);
    on.setUserJoinedRoom(userJoinedRoom);
    on.setUserLeftRoom(userLeftRoom);
    on.setUserRequestedJoinRoom(userRequestedJoinRoom);

    Debug debug = new Debug();
    debug.setOn(on);

    // Act and Assert
    assertSame(on, debug.getOn());
  }

  /**
   * Method under test: {@link BaseActivity#getVariableProperties()}
   */
  @Test
  void testGetVariableProperties() {
    // Arrange, Act and Assert
    assertTrue((new Debug()).getVariableProperties().isEmpty());
  }

  /**
   * Method under test: {@link BaseActivity#getVariableProperties()}
   */
  @Test
  void testGetVariableProperties2() {
    // Arrange
    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");

    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");

    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");

    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");

    HashMap<String, Object> arguments = new HashMap<>();
    arguments.computeIfPresent("foo", mock(BiFunction.class));

    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(arguments);
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");

    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");

    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");

    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");

    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");

    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("At");
    timerFired.setId("42");
    timerFired.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");

    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");

    EventWithTimeout on = new EventWithTimeout();
    on.setActivityCompleted(activityCompleted);
    on.setActivityExpired(activityExpired);
    on.setActivityFailed(activityFailed);
    on.setAllOf(new ArrayList<>());
    on.setConnectionAccepted(connectionAccepted);
    on.setConnectionRequested(connectionRequested);
    on.setFormReplied(formReplied);
    on.setImCreated(imCreated);
    on.setMessageReceived(messageReceived);
    on.setMessageSuppressed(messageSuppressed);
    on.setOneOf(new ArrayList<>());
    on.setPostShared(postShared);
    on.setRequestReceived(requestReceived);
    on.setRoomCreated(roomCreated);
    on.setRoomDeactivated(roomDeactivated);
    on.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    on.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    on.setRoomReactivated(roomReactivated);
    on.setRoomUpdated(roomUpdated);
    on.setTimeout("Timeout");
    on.setTimerFired(timerFired);
    on.setUserJoinedRoom(userJoinedRoom);
    on.setUserLeftRoom(userLeftRoom);
    on.setUserRequestedJoinRoom(userRequestedJoinRoom);

    Debug debug = new Debug();
    debug.setOn(on);

    // Act and Assert
    assertTrue(debug.getVariableProperties().isEmpty());
  }

  /**
   * Method under test: {@link BaseActivity#setElseCondition(Object)}
   */
  @Test
  void testSetElseCondition() {
    // Arrange
    Debug debug = new Debug();

    // Act
    debug.setElseCondition("Else Condition");

    // Assert
    assertEquals("Else Condition", debug.getElseCondition());
  }

  /**
   * Method under test: {@link BaseActivity#setElseCondition(Object)}
   */
  @Test
  void testSetElseCondition2() {
    // Arrange
    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");

    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");

    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");

    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");

    HashMap<String, Object> arguments = new HashMap<>();
    arguments.computeIfPresent("foo", mock(BiFunction.class));

    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(arguments);
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");

    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");

    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");

    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");

    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");

    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("At");
    timerFired.setId("42");
    timerFired.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");

    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");

    EventWithTimeout on = new EventWithTimeout();
    on.setActivityCompleted(activityCompleted);
    on.setActivityExpired(activityExpired);
    on.setActivityFailed(activityFailed);
    on.setAllOf(new ArrayList<>());
    on.setConnectionAccepted(connectionAccepted);
    on.setConnectionRequested(connectionRequested);
    on.setFormReplied(formReplied);
    on.setImCreated(imCreated);
    on.setMessageReceived(messageReceived);
    on.setMessageSuppressed(messageSuppressed);
    on.setOneOf(new ArrayList<>());
    on.setPostShared(postShared);
    on.setRequestReceived(requestReceived);
    on.setRoomCreated(roomCreated);
    on.setRoomDeactivated(roomDeactivated);
    on.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    on.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    on.setRoomReactivated(roomReactivated);
    on.setRoomUpdated(roomUpdated);
    on.setTimeout("Timeout");
    on.setTimerFired(timerFired);
    on.setUserJoinedRoom(userJoinedRoom);
    on.setUserLeftRoom(userLeftRoom);
    on.setUserRequestedJoinRoom(userRequestedJoinRoom);

    Debug debug = new Debug();
    debug.setOn(on);

    // Act
    debug.setElseCondition("Else Condition");

    // Assert
    assertEquals("Else Condition", debug.getElseCondition());
  }

  /**
   * Method under test: {@link BaseActivity#setId(String)}
   */
  @Test
  void testSetId() {
    // Arrange
    Debug debug = new Debug();

    // Act
    debug.setId("42");

    // Assert
    assertEquals("42", debug.getId());
  }

  /**
   * Method under test: {@link BaseActivity#setId(String)}
   */
  @Test
  void testSetId2() {
    // Arrange
    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");

    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");

    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");

    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");

    HashMap<String, Object> arguments = new HashMap<>();
    arguments.computeIfPresent("foo", mock(BiFunction.class));

    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(arguments);
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");

    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");

    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");

    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");

    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");

    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("At");
    timerFired.setId("42");
    timerFired.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");

    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");

    EventWithTimeout on = new EventWithTimeout();
    on.setActivityCompleted(activityCompleted);
    on.setActivityExpired(activityExpired);
    on.setActivityFailed(activityFailed);
    on.setAllOf(new ArrayList<>());
    on.setConnectionAccepted(connectionAccepted);
    on.setConnectionRequested(connectionRequested);
    on.setFormReplied(formReplied);
    on.setImCreated(imCreated);
    on.setMessageReceived(messageReceived);
    on.setMessageSuppressed(messageSuppressed);
    on.setOneOf(new ArrayList<>());
    on.setPostShared(postShared);
    on.setRequestReceived(requestReceived);
    on.setRoomCreated(roomCreated);
    on.setRoomDeactivated(roomDeactivated);
    on.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    on.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    on.setRoomReactivated(roomReactivated);
    on.setRoomUpdated(roomUpdated);
    on.setTimeout("Timeout");
    on.setTimerFired(timerFired);
    on.setUserJoinedRoom(userJoinedRoom);
    on.setUserLeftRoom(userLeftRoom);
    on.setUserRequestedJoinRoom(userRequestedJoinRoom);

    Debug debug = new Debug();
    debug.setOn(on);

    // Act
    debug.setId("42");

    // Assert
    assertEquals("42", debug.getId());
  }

  /**
   * Method under test: {@link BaseActivity#setIfCondition(String)}
   */
  @Test
  void testSetIfCondition() {
    // Arrange
    Debug debug = new Debug();

    // Act
    debug.setIfCondition("If Condition");

    // Assert
    assertEquals("If Condition", debug.getIfCondition());
  }

  /**
   * Method under test: {@link BaseActivity#setIfCondition(String)}
   */
  @Test
  void testSetIfCondition2() {
    // Arrange
    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");

    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");

    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");

    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");

    HashMap<String, Object> arguments = new HashMap<>();
    arguments.computeIfPresent("foo", mock(BiFunction.class));

    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(arguments);
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");

    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");

    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");

    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");

    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");

    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("At");
    timerFired.setId("42");
    timerFired.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");

    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");

    EventWithTimeout on = new EventWithTimeout();
    on.setActivityCompleted(activityCompleted);
    on.setActivityExpired(activityExpired);
    on.setActivityFailed(activityFailed);
    on.setAllOf(new ArrayList<>());
    on.setConnectionAccepted(connectionAccepted);
    on.setConnectionRequested(connectionRequested);
    on.setFormReplied(formReplied);
    on.setImCreated(imCreated);
    on.setMessageReceived(messageReceived);
    on.setMessageSuppressed(messageSuppressed);
    on.setOneOf(new ArrayList<>());
    on.setPostShared(postShared);
    on.setRequestReceived(requestReceived);
    on.setRoomCreated(roomCreated);
    on.setRoomDeactivated(roomDeactivated);
    on.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    on.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    on.setRoomReactivated(roomReactivated);
    on.setRoomUpdated(roomUpdated);
    on.setTimeout("Timeout");
    on.setTimerFired(timerFired);
    on.setUserJoinedRoom(userJoinedRoom);
    on.setUserLeftRoom(userLeftRoom);
    on.setUserRequestedJoinRoom(userRequestedJoinRoom);

    Debug debug = new Debug();
    debug.setOn(on);

    // Act
    debug.setIfCondition("If Condition");

    // Assert
    assertEquals("If Condition", debug.getIfCondition());
  }

  /**
   * Method under test: {@link BaseActivity#setOn(EventWithTimeout)}
   */
  @Test
  void testSetOn() {
    // Arrange
    Debug debug = new Debug();
    EventWithTimeout on = mock(EventWithTimeout.class);

    // Act
    debug.setOn(on);

    // Assert
    assertSame(on, debug.getOn());
  }

  /**
   * Method under test: {@link BaseActivity#setVariableProperties(Map)}
   */
  @Test
  void testSetVariableProperties() {
    // Arrange
    Debug debug = new Debug();
    HashMap<String, Object> variableProperties = new HashMap<>();

    // Act
    debug.setVariableProperties(variableProperties);

    // Assert
    assertSame(variableProperties, debug.getVariableProperties());
  }

  /**
   * Method under test: {@link BaseActivity#setVariableProperties(Map)}
   */
  @Test
  void testSetVariableProperties2() {
    // Arrange
    Debug debug = new Debug();

    HashMap<String, Object> variableProperties = new HashMap<>();
    variableProperties.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    debug.setVariableProperties(variableProperties);

    // Assert
    assertSame(variableProperties, debug.getVariableProperties());
  }

  /**
   * Method under test: {@link BaseActivity#toString()}
   */
  @Test
  void testToString() {
    // Arrange, Act and Assert
    assertEquals("Debug(object=null)", (new Debug()).toString());
  }

  /**
   * Method under test: {@link BaseActivity#toString()}
   */
  @Test
  void testToString2() {
    // Arrange
    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");

    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");

    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");

    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");

    HashMap<String, Object> arguments = new HashMap<>();
    arguments.computeIfPresent("foo", mock(BiFunction.class));

    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(arguments);
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");

    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");

    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");

    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");

    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");

    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("At");
    timerFired.setId("42");
    timerFired.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");

    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");

    EventWithTimeout on = new EventWithTimeout();
    on.setActivityCompleted(activityCompleted);
    on.setActivityExpired(activityExpired);
    on.setActivityFailed(activityFailed);
    on.setAllOf(new ArrayList<>());
    on.setConnectionAccepted(connectionAccepted);
    on.setConnectionRequested(connectionRequested);
    on.setFormReplied(formReplied);
    on.setImCreated(imCreated);
    on.setMessageReceived(messageReceived);
    on.setMessageSuppressed(messageSuppressed);
    on.setOneOf(new ArrayList<>());
    on.setPostShared(postShared);
    on.setRequestReceived(requestReceived);
    on.setRoomCreated(roomCreated);
    on.setRoomDeactivated(roomDeactivated);
    on.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    on.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    on.setRoomReactivated(roomReactivated);
    on.setRoomUpdated(roomUpdated);
    on.setTimeout("Timeout");
    on.setTimerFired(timerFired);
    on.setUserJoinedRoom(userJoinedRoom);
    on.setUserLeftRoom(userLeftRoom);
    on.setUserRequestedJoinRoom(userRequestedJoinRoom);

    Debug debug = new Debug();
    debug.setOn(on);

    // Act and Assert
    assertEquals("Debug(object=null)", debug.toString());
  }
}
