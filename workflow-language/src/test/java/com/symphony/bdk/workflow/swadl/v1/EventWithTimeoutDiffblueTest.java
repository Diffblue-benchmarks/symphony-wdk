package com.symphony.bdk.workflow.swadl.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EventWithTimeoutDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link EventWithTimeout#equals(Object)}
   *   <li>{@link EventWithTimeout#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
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

    EventWithTimeout eventWithTimeout2 = new EventWithTimeout();
    eventWithTimeout2.setActivityCompleted(activityCompleted2);
    eventWithTimeout2.setActivityExpired(activityExpired2);
    eventWithTimeout2.setActivityFailed(activityFailed2);
    eventWithTimeout2.setAllOf(new ArrayList<>());
    eventWithTimeout2.setConnectionAccepted(connectionAccepted2);
    eventWithTimeout2.setConnectionRequested(connectionRequested2);
    eventWithTimeout2.setFormReplied(formReplied2);
    eventWithTimeout2.setImCreated(imCreated2);
    eventWithTimeout2.setMessageReceived(messageReceived2);
    eventWithTimeout2.setMessageSuppressed(messageSuppressed2);
    eventWithTimeout2.setOneOf(new ArrayList<>());
    eventWithTimeout2.setPostShared(postShared2);
    eventWithTimeout2.setRequestReceived(requestReceived2);
    eventWithTimeout2.setRoomCreated(roomCreated2);
    eventWithTimeout2.setRoomDeactivated(roomDeactivated2);
    eventWithTimeout2.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner2);
    eventWithTimeout2.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner2);
    eventWithTimeout2.setRoomReactivated(roomReactivated2);
    eventWithTimeout2.setRoomUpdated(roomUpdated2);
    eventWithTimeout2.setTimeout("Timeout");
    eventWithTimeout2.setTimerFired(timerFired2);
    eventWithTimeout2.setUserJoinedRoom(userJoinedRoom2);
    eventWithTimeout2.setUserLeftRoom(userLeftRoom2);
    eventWithTimeout2.setUserRequestedJoinRoom(userRequestedJoinRoom2);

    // Act and Assert
    assertEquals(eventWithTimeout, eventWithTimeout2);
    int expectedHashCodeResult = eventWithTimeout.hashCode();
    assertEquals(expectedHashCodeResult, eventWithTimeout2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link EventWithTimeout#equals(Object)}
   *   <li>{@link EventWithTimeout#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ActivityCompletedEvent activityCompleted = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompleted).setIfCondition(Mockito.<String>any());
    doNothing().when(activityCompleted).setActivityId(Mockito.<String>any());
    doNothing().when(activityCompleted).setId(Mockito.<String>any());
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

    EventWithTimeout eventWithTimeout2 = new EventWithTimeout();
    eventWithTimeout2.setActivityCompleted(activityCompleted2);
    eventWithTimeout2.setActivityExpired(activityExpired2);
    eventWithTimeout2.setActivityFailed(activityFailed2);
    eventWithTimeout2.setAllOf(new ArrayList<>());
    eventWithTimeout2.setConnectionAccepted(connectionAccepted2);
    eventWithTimeout2.setConnectionRequested(connectionRequested2);
    eventWithTimeout2.setFormReplied(formReplied2);
    eventWithTimeout2.setImCreated(imCreated2);
    eventWithTimeout2.setMessageReceived(messageReceived2);
    eventWithTimeout2.setMessageSuppressed(messageSuppressed2);
    eventWithTimeout2.setOneOf(new ArrayList<>());
    eventWithTimeout2.setPostShared(postShared2);
    eventWithTimeout2.setRequestReceived(requestReceived2);
    eventWithTimeout2.setRoomCreated(roomCreated2);
    eventWithTimeout2.setRoomDeactivated(roomDeactivated2);
    eventWithTimeout2.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner2);
    eventWithTimeout2.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner2);
    eventWithTimeout2.setRoomReactivated(roomReactivated2);
    eventWithTimeout2.setRoomUpdated(roomUpdated2);
    eventWithTimeout2.setTimeout("Timeout");
    eventWithTimeout2.setTimerFired(timerFired2);
    eventWithTimeout2.setUserJoinedRoom(userJoinedRoom2);
    eventWithTimeout2.setUserLeftRoom(userLeftRoom2);
    eventWithTimeout2.setUserRequestedJoinRoom(userRequestedJoinRoom2);

    // Act and Assert
    assertEquals(eventWithTimeout, eventWithTimeout2);
    int expectedHashCodeResult = eventWithTimeout.hashCode();
    assertEquals(expectedHashCodeResult, eventWithTimeout2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link EventWithTimeout#equals(Object)}
   *   <li>{@link EventWithTimeout#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
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

    // Act and Assert
    assertEquals(eventWithTimeout, eventWithTimeout);
    int expectedHashCodeResult = eventWithTimeout.hashCode();
    assertEquals(expectedHashCodeResult, eventWithTimeout.hashCode());
  }

  /**
   * Method under test: {@link EventWithTimeout#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ActivityCompletedEvent activityCompleted = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompleted).setIfCondition(Mockito.<String>any());
    doNothing().when(activityCompleted).setActivityId(Mockito.<String>any());
    doNothing().when(activityCompleted).setId(Mockito.<String>any());
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
    eventWithTimeout.setTimeout(null);
    eventWithTimeout.setTimerFired(timerFired);
    eventWithTimeout.setUserJoinedRoom(userJoinedRoom);
    eventWithTimeout.setUserLeftRoom(userLeftRoom);
    eventWithTimeout.setUserRequestedJoinRoom(userRequestedJoinRoom);

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

    EventWithTimeout eventWithTimeout2 = new EventWithTimeout();
    eventWithTimeout2.setActivityCompleted(activityCompleted2);
    eventWithTimeout2.setActivityExpired(activityExpired2);
    eventWithTimeout2.setActivityFailed(activityFailed2);
    eventWithTimeout2.setAllOf(new ArrayList<>());
    eventWithTimeout2.setConnectionAccepted(connectionAccepted2);
    eventWithTimeout2.setConnectionRequested(connectionRequested2);
    eventWithTimeout2.setFormReplied(formReplied2);
    eventWithTimeout2.setImCreated(imCreated2);
    eventWithTimeout2.setMessageReceived(messageReceived2);
    eventWithTimeout2.setMessageSuppressed(messageSuppressed2);
    eventWithTimeout2.setOneOf(new ArrayList<>());
    eventWithTimeout2.setPostShared(postShared2);
    eventWithTimeout2.setRequestReceived(requestReceived2);
    eventWithTimeout2.setRoomCreated(roomCreated2);
    eventWithTimeout2.setRoomDeactivated(roomDeactivated2);
    eventWithTimeout2.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner2);
    eventWithTimeout2.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner2);
    eventWithTimeout2.setRoomReactivated(roomReactivated2);
    eventWithTimeout2.setRoomUpdated(roomUpdated2);
    eventWithTimeout2.setTimeout("Timeout");
    eventWithTimeout2.setTimerFired(timerFired2);
    eventWithTimeout2.setUserJoinedRoom(userJoinedRoom2);
    eventWithTimeout2.setUserLeftRoom(userLeftRoom2);
    eventWithTimeout2.setUserRequestedJoinRoom(userRequestedJoinRoom2);

    // Act and Assert
    assertNotEquals(eventWithTimeout, eventWithTimeout2);
  }

  /**
   * Method under test: {@link EventWithTimeout#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ActivityCompletedEvent activityCompleted = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompleted).setIfCondition(Mockito.<String>any());
    doNothing().when(activityCompleted).setActivityId(Mockito.<String>any());
    doNothing().when(activityCompleted).setId(Mockito.<String>any());
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
    eventWithTimeout.setTimeout("com.symphony.bdk.workflow.swadl.v1.EventWithTimeout");
    eventWithTimeout.setTimerFired(timerFired);
    eventWithTimeout.setUserJoinedRoom(userJoinedRoom);
    eventWithTimeout.setUserLeftRoom(userLeftRoom);
    eventWithTimeout.setUserRequestedJoinRoom(userRequestedJoinRoom);

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

    EventWithTimeout eventWithTimeout2 = new EventWithTimeout();
    eventWithTimeout2.setActivityCompleted(activityCompleted2);
    eventWithTimeout2.setActivityExpired(activityExpired2);
    eventWithTimeout2.setActivityFailed(activityFailed2);
    eventWithTimeout2.setAllOf(new ArrayList<>());
    eventWithTimeout2.setConnectionAccepted(connectionAccepted2);
    eventWithTimeout2.setConnectionRequested(connectionRequested2);
    eventWithTimeout2.setFormReplied(formReplied2);
    eventWithTimeout2.setImCreated(imCreated2);
    eventWithTimeout2.setMessageReceived(messageReceived2);
    eventWithTimeout2.setMessageSuppressed(messageSuppressed2);
    eventWithTimeout2.setOneOf(new ArrayList<>());
    eventWithTimeout2.setPostShared(postShared2);
    eventWithTimeout2.setRequestReceived(requestReceived2);
    eventWithTimeout2.setRoomCreated(roomCreated2);
    eventWithTimeout2.setRoomDeactivated(roomDeactivated2);
    eventWithTimeout2.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner2);
    eventWithTimeout2.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner2);
    eventWithTimeout2.setRoomReactivated(roomReactivated2);
    eventWithTimeout2.setRoomUpdated(roomUpdated2);
    eventWithTimeout2.setTimeout("Timeout");
    eventWithTimeout2.setTimerFired(timerFired2);
    eventWithTimeout2.setUserJoinedRoom(userJoinedRoom2);
    eventWithTimeout2.setUserLeftRoom(userLeftRoom2);
    eventWithTimeout2.setUserRequestedJoinRoom(userRequestedJoinRoom2);

    // Act and Assert
    assertNotEquals(eventWithTimeout, eventWithTimeout2);
  }

  /**
   * Method under test: {@link EventWithTimeout#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
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

    // Act and Assert
    assertNotEquals(eventWithTimeout, null);
  }

  /**
   * Method under test: {@link EventWithTimeout#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
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

    // Act and Assert
    assertNotEquals(eventWithTimeout, "Different type to EventWithTimeout");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link EventWithTimeout}
   *   <li>{@link EventWithTimeout#setTimeout(String)}
   *   <li>{@link EventWithTimeout#toString()}
   *   <li>{@link EventWithTimeout#getTimeout()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    EventWithTimeout actualEventWithTimeout = new EventWithTimeout();
    actualEventWithTimeout.setTimeout("Timeout");
    String actualToStringResult = actualEventWithTimeout.toString();

    // Assert that nothing has changed
    assertEquals("EventWithTimeout(timeout=Timeout)", actualToStringResult);
    assertEquals("Timeout", actualEventWithTimeout.getTimeout());
  }
}
