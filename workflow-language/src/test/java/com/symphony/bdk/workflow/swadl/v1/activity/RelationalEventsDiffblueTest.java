package com.symphony.bdk.workflow.swadl.v1.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.Event;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RelationalEventsDiffblueTest {
  /**
   * Test {@link RelationalEvents#isEmpty()}.
   *
   * <ul>
   *   <li>Given {@link ActivityCompletedEvent} (default constructor) ActivityId is {@code 42}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link RelationalEvents#isEmpty()}
   */
  @Test
  @DisplayName(
      "Test isEmpty(); given ActivityCompletedEvent (default constructor) ActivityId is '42'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RelationalEvents.isEmpty()"})
  void testIsEmpty_givenActivityCompletedEventActivityIdIs42_thenReturnFalse() {
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

    Event event = new Event();
    event.setActivityCompleted(activityCompleted);
    event.setActivityExpired(activityExpired);
    event.setActivityFailed(activityFailed);
    event.setAllOf(new ArrayList<>());
    event.setConnectionAccepted(connectionAccepted);
    event.setConnectionRequested(connectionRequested);
    event.setFormReplied(formReplied);
    event.setImCreated(imCreated);
    event.setMessageReceived(messageReceived);
    event.setMessageSuppressed(messageSuppressed);
    event.setOneOf(new ArrayList<>());
    event.setPostShared(postShared);
    event.setRequestReceived(requestReceived);
    event.setRoomCreated(roomCreated);
    event.setRoomDeactivated(roomDeactivated);
    event.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    event.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    event.setRoomReactivated(roomReactivated);
    event.setRoomUpdated(roomUpdated);
    event.setTimerFired(timerFired);
    event.setUserJoinedRoom(userJoinedRoom);
    event.setUserLeftRoom(userLeftRoom);
    event.setUserRequestedJoinRoom(userRequestedJoinRoom);

    ArrayList<Event> events = new ArrayList<>();
    events.add(event);

    // Act and Assert
    assertFalse(new RelationalEvents(events, true).isEmpty());
  }

  /**
   * Test {@link RelationalEvents#isEmpty()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link RelationalEvents#isEmpty()}
   */
  @Test
  @DisplayName("Test isEmpty(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RelationalEvents.isEmpty()"})
  void testIsEmpty_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(new RelationalEvents(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Test {@link RelationalEvents#getParentId()}.
   *
   * <p>Method under test: {@link RelationalEvents#getParentId()}
   */
  @Test
  @DisplayName("Test getParentId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String RelationalEvents.getParentId()"})
  void testGetParentId() {
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

    Event event = new Event();
    event.setActivityCompleted(activityCompleted);
    event.setActivityExpired(activityExpired);
    event.setActivityFailed(activityFailed);
    event.setAllOf(new ArrayList<>());
    event.setConnectionAccepted(connectionAccepted);
    event.setConnectionRequested(connectionRequested);
    event.setFormReplied(formReplied);
    event.setImCreated(imCreated);
    event.setMessageReceived(messageReceived);
    event.setMessageSuppressed(messageSuppressed);
    event.setOneOf(new ArrayList<>());
    event.setPostShared(postShared);
    event.setRequestReceived(requestReceived);
    event.setRoomCreated(roomCreated);
    event.setRoomDeactivated(roomDeactivated);
    event.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    event.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    event.setRoomReactivated(roomReactivated);
    event.setRoomUpdated(roomUpdated);
    event.setTimerFired(timerFired);
    event.setUserJoinedRoom(userJoinedRoom);
    event.setUserLeftRoom(userLeftRoom);
    event.setUserRequestedJoinRoom(userRequestedJoinRoom);

    ArrayList<Event> events = new ArrayList<>();
    events.add(event);

    RelationalEvents relationalEvents = new RelationalEvents(events, true);
    relationalEvents.setParentId("42");

    // Act and Assert
    assertEquals("42", relationalEvents.getParentId());
  }

  /**
   * Test {@link RelationalEvents#getParentId()}.
   *
   * <ul>
   *   <li>Given {@link ActivityCompletedEvent} (default constructor) ActivityId is {@code 42}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link RelationalEvents#getParentId()}
   */
  @Test
  @DisplayName(
      "Test getParentId(); given ActivityCompletedEvent (default constructor) ActivityId is '42'; then return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String RelationalEvents.getParentId()"})
  void testGetParentId_givenActivityCompletedEventActivityIdIs42_thenReturn42() {
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

    Event event = new Event();
    event.setActivityCompleted(activityCompleted);
    event.setActivityExpired(activityExpired);
    event.setActivityFailed(activityFailed);
    event.setAllOf(new ArrayList<>());
    event.setConnectionAccepted(connectionAccepted);
    event.setConnectionRequested(connectionRequested);
    event.setFormReplied(formReplied);
    event.setImCreated(imCreated);
    event.setMessageReceived(messageReceived);
    event.setMessageSuppressed(messageSuppressed);
    event.setOneOf(new ArrayList<>());
    event.setPostShared(postShared);
    event.setRequestReceived(requestReceived);
    event.setRoomCreated(roomCreated);
    event.setRoomDeactivated(roomDeactivated);
    event.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    event.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    event.setRoomReactivated(roomReactivated);
    event.setRoomUpdated(roomUpdated);
    event.setTimerFired(timerFired);
    event.setUserJoinedRoom(userJoinedRoom);
    event.setUserLeftRoom(userLeftRoom);
    event.setUserRequestedJoinRoom(userRequestedJoinRoom);

    ArrayList<Event> events = new ArrayList<>();
    events.add(event);

    // Act and Assert
    assertEquals("42", new RelationalEvents(events, true).getParentId());
  }

  /**
   * Test {@link RelationalEvents#getParentId()}.
   *
   * <ul>
   *   <li>Given {@link RelationalEvents#RelationalEvents(List, boolean)} with events is {@link
   *       ArrayList#ArrayList()} and parallel is {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link RelationalEvents#getParentId()}
   */
  @Test
  @DisplayName(
      "Test getParentId(); given RelationalEvents(List, boolean) with events is ArrayList() and parallel is 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String RelationalEvents.getParentId()"})
  void testGetParentId_givenRelationalEventsWithEventsIsArrayListAndParallelIsFalse() {
    // Arrange, Act and Assert
    assertNull(new RelationalEvents(new ArrayList<>(), false).getParentId());
  }

  /**
   * Test {@link RelationalEvents#getParentId()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link RelationalEvents#getParentId()}
   */
  @Test
  @DisplayName("Test getParentId(); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String RelationalEvents.getParentId()"})
  void testGetParentId_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new RelationalEvents(new ArrayList<>(), true).getParentId());
  }
}
