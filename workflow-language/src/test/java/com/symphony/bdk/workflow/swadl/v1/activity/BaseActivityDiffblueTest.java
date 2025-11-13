package com.symphony.bdk.workflow.swadl.v1.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BaseActivityDiffblueTest {
  /**
   * Test {@link BaseActivity#add(String, Object)}.
   *
   * <p>Method under test: {@link BaseActivity#add(String, Object)}
   */
  @Test
  @DisplayName("Test add(String, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseActivity.add(String, Object)"})
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
   * Test {@link BaseActivity#getEvents()}.
   *
   * <ul>
   *   <li>Given {@link Debug} (default constructor).
   *   <li>Then return not Parallel.
   * </ul>
   *
   * <p>Method under test: {@link BaseActivity#getEvents()}
   */
  @Test
  @DisplayName("Test getEvents(); given Debug (default constructor); then return not Parallel")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"RelationalEvents BaseActivity.getEvents()"})
  void testGetEvents_givenDebug_thenReturnNotParallel() {
    // Arrange and Act
    RelationalEvents actualEvents = new Debug().getEvents();

    // Assert
    assertNull(actualEvents.getParentId());
    assertFalse(actualEvents.isParallel());
    assertTrue(actualEvents.isEmpty());
    assertTrue(actualEvents.getEvents().isEmpty());
  }

  /**
   * Test {@link BaseActivity#getEvents()}.
   *
   * <ul>
   *   <li>Given {@link EventWithTimeout} (default constructor) AllOf is {@code null}.
   *   <li>Then return Events size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseActivity#getEvents()}
   */
  @Test
  @DisplayName(
      "Test getEvents(); given EventWithTimeout (default constructor) AllOf is 'null'; then return Events size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"RelationalEvents BaseActivity.getEvents()"})
  void testGetEvents_givenEventWithTimeoutAllOfIsNull_thenReturnEventsSizeIsOne() {
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

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner =
        new RoomMemberDemotedFromOwnerEvent();
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
    List<Event> events = actualEvents.getEvents();
    assertEquals(1, events.size());
    Event getResult = events.get(0);
    assertTrue(getResult instanceof EventWithTimeout);
    assertFalse(actualEvents.isEmpty());
    assertSame(on, getResult);
  }

  /**
   * Test {@link BaseActivity#getEvents()}.
   *
   * <ul>
   *   <li>Given {@link EventWithTimeout} (default constructor) OneOf is {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then return not Parallel.
   * </ul>
   *
   * <p>Method under test: {@link BaseActivity#getEvents()}
   */
  @Test
  @DisplayName(
      "Test getEvents(); given EventWithTimeout (default constructor) OneOf is ArrayList(); then return not Parallel")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"RelationalEvents BaseActivity.getEvents()"})
  void testGetEvents_givenEventWithTimeoutOneOfIsArrayList_thenReturnNotParallel() {
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

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner =
        new RoomMemberDemotedFromOwnerEvent();
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
    on.setOneOf(new ArrayList<>());
    on.setAllOf(new ArrayList<>());

    Debug debug = new Debug();
    debug.setOn(on);

    // Act
    RelationalEvents actualEvents = debug.getEvents();

    // Assert
    assertNull(actualEvents.getParentId());
    assertFalse(actualEvents.isParallel());
    assertTrue(actualEvents.isEmpty());
    assertTrue(actualEvents.getEvents().isEmpty());
  }

  /**
   * Test {@link BaseActivity#getEvents()}.
   *
   * <ul>
   *   <li>Given {@link EventWithTimeout} (default constructor) OneOf is {@code null}.
   *   <li>Then return Parallel.
   * </ul>
   *
   * <p>Method under test: {@link BaseActivity#getEvents()}
   */
  @Test
  @DisplayName(
      "Test getEvents(); given EventWithTimeout (default constructor) OneOf is 'null'; then return Parallel")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"RelationalEvents BaseActivity.getEvents()"})
  void testGetEvents_givenEventWithTimeoutOneOfIsNull_thenReturnParallel() {
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

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner =
        new RoomMemberDemotedFromOwnerEvent();
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
    on.setAllOf(new ArrayList<>());

    Debug debug = new Debug();
    debug.setOn(on);

    // Act
    RelationalEvents actualEvents = debug.getEvents();

    // Assert
    assertNull(actualEvents.getParentId());
    assertTrue(actualEvents.isEmpty());
    assertTrue(actualEvents.isParallel());
    assertTrue(actualEvents.getEvents().isEmpty());
  }
}
