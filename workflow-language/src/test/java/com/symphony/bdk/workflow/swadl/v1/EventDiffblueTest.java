package com.symphony.bdk.workflow.swadl.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EventDiffblueTest {
  /**
   * Test {@link Event#equals(Object)}, and {@link Event#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Event#equals(Object)}
   *   <li>{@link Event#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Event.equals(Object)", "int Event.hashCode()"})
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

    Event event2 = new Event();
    event2.setActivityCompleted(activityCompleted2);
    event2.setActivityExpired(activityExpired2);
    event2.setActivityFailed(activityFailed2);
    event2.setAllOf(new ArrayList<>());
    event2.setConnectionAccepted(connectionAccepted2);
    event2.setConnectionRequested(connectionRequested2);
    event2.setFormReplied(formReplied2);
    event2.setImCreated(imCreated2);
    event2.setMessageReceived(messageReceived2);
    event2.setMessageSuppressed(messageSuppressed2);
    event2.setOneOf(new ArrayList<>());
    event2.setPostShared(postShared2);
    event2.setRequestReceived(requestReceived2);
    event2.setRoomCreated(roomCreated2);
    event2.setRoomDeactivated(roomDeactivated2);
    event2.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner2);
    event2.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner2);
    event2.setRoomReactivated(roomReactivated2);
    event2.setRoomUpdated(roomUpdated2);
    event2.setTimerFired(timerFired2);
    event2.setUserJoinedRoom(userJoinedRoom2);
    event2.setUserLeftRoom(userLeftRoom2);
    event2.setUserRequestedJoinRoom(userRequestedJoinRoom2);

    // Act and Assert
    assertEquals(event, event2);
    int expectedHashCodeResult = event.hashCode();
    assertEquals(expectedHashCodeResult, event2.hashCode());
  }

  /**
   * Test {@link Event#equals(Object)}, and {@link Event#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Event#equals(Object)}
   *   <li>{@link Event#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Event.equals(Object)", "int Event.hashCode()"})
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

    // Act and Assert
    assertEquals(event, event);
    int expectedHashCodeResult = event.hashCode();
    assertEquals(expectedHashCodeResult, event.hashCode());
  }

  /**
   * Test {@link Event#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Event#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Event.equals(Object)", "int Event.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
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

    ActivityCompletedEvent activityCompleted2 = new ActivityCompletedEvent();
    activityCompleted2.setActivityId("42");
    activityCompleted2.setId("42");
    activityCompleted2.setIfCondition("42");

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
    timerFired2.setAt("42");
    timerFired2.setId("42");
    timerFired2.setRepeat("42");

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
    eventWithTimeout.setTimeout("42");
    eventWithTimeout.setTimerFired(timerFired2);
    eventWithTimeout.setUserJoinedRoom(userJoinedRoom2);
    eventWithTimeout.setUserLeftRoom(userLeftRoom2);
    eventWithTimeout.setUserRequestedJoinRoom(userRequestedJoinRoom2);
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
    eventWithTimeout.setTimerFired(timerFired);
    eventWithTimeout.setUserJoinedRoom(userJoinedRoom);
    eventWithTimeout.setUserLeftRoom(userLeftRoom);
    eventWithTimeout.setUserRequestedJoinRoom(userRequestedJoinRoom);

    ActivityCompletedEvent activityCompleted3 = new ActivityCompletedEvent();
    activityCompleted3.setActivityId("42");
    activityCompleted3.setId("42");
    activityCompleted3.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired3 = new ActivityExpiredEvent();
    activityExpired3.setActivityId("42");
    activityExpired3.setId("42");

    ActivityFailedEvent activityFailed3 = new ActivityFailedEvent();
    activityFailed3.setActivityId("42");
    activityFailed3.setId("42");

    ConnectionAcceptedEvent connectionAccepted3 = new ConnectionAcceptedEvent();
    connectionAccepted3.setId("42");

    ConnectionRequestedEvent connectionRequested3 = new ConnectionRequestedEvent();
    connectionRequested3.setId("42");

    FormRepliedEvent formReplied3 = new FormRepliedEvent();
    formReplied3.setExclusive(true);
    formReplied3.setFormId("42");
    formReplied3.setId("42");

    ImCreatedEvent imCreated3 = new ImCreatedEvent();
    imCreated3.setId("42");

    MessageReceivedEvent messageReceived3 = new MessageReceivedEvent();
    messageReceived3.setContent("Not all who wander are lost");
    messageReceived3.setId("42");
    messageReceived3.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed3 = new MessageSuppressedEvent();
    messageSuppressed3.setId("42");

    PostSharedEvent postShared3 = new PostSharedEvent();
    postShared3.setId("42");

    RequestReceivedEvent requestReceived3 = new RequestReceivedEvent();
    requestReceived3.setArguments(new HashMap<>());
    requestReceived3.setId("42");
    requestReceived3.setToken("ABC123");
    requestReceived3.setWorkflowId("42");

    RoomCreatedEvent roomCreated3 = new RoomCreatedEvent();
    roomCreated3.setId("42");

    RoomDeactivatedEvent roomDeactivated3 = new RoomDeactivatedEvent();
    roomDeactivated3.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner3 = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner3.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner3 = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner3.setId("42");

    RoomReactivatedEvent roomReactivated3 = new RoomReactivatedEvent();
    roomReactivated3.setId("42");

    RoomUpdatedEvent roomUpdated3 = new RoomUpdatedEvent();
    roomUpdated3.setId("42");

    TimerFiredEvent timerFired3 = new TimerFiredEvent();
    timerFired3.setAt("At");
    timerFired3.setId("42");
    timerFired3.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom3 = new UserJoinedRoomEvent();
    userJoinedRoom3.setId("42");

    UserLeftRoomEvent userLeftRoom3 = new UserLeftRoomEvent();
    userLeftRoom3.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom3 = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom3.setId("42");

    Event event = new Event();
    event.setActivityCompleted(activityCompleted3);
    event.setActivityExpired(activityExpired3);
    event.setActivityFailed(activityFailed3);
    event.setAllOf(new ArrayList<>());
    event.setConnectionAccepted(connectionAccepted3);
    event.setConnectionRequested(connectionRequested3);
    event.setFormReplied(formReplied3);
    event.setImCreated(imCreated3);
    event.setMessageReceived(messageReceived3);
    event.setMessageSuppressed(messageSuppressed3);
    event.setOneOf(new ArrayList<>());
    event.setPostShared(postShared3);
    event.setRequestReceived(requestReceived3);
    event.setRoomCreated(roomCreated3);
    event.setRoomDeactivated(roomDeactivated3);
    event.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner3);
    event.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner3);
    event.setRoomReactivated(roomReactivated3);
    event.setRoomUpdated(roomUpdated3);
    event.setTimerFired(timerFired3);
    event.setUserJoinedRoom(userJoinedRoom3);
    event.setUserLeftRoom(userLeftRoom3);
    event.setUserRequestedJoinRoom(userRequestedJoinRoom3);

    // Act and Assert
    assertNotEquals(eventWithTimeout, event);
  }

  /**
   * Test {@link Event#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Event#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Event.equals(Object)", "int Event.hashCode()"})
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

    Event event2 = new Event();
    event2.setActivityCompleted(activityCompleted2);
    event2.setActivityExpired(activityExpired2);
    event2.setActivityFailed(activityFailed2);
    event2.setAllOf(new ArrayList<>());
    event2.setConnectionAccepted(connectionAccepted2);
    event2.setConnectionRequested(connectionRequested2);
    event2.setFormReplied(formReplied2);
    event2.setImCreated(imCreated2);
    event2.setMessageReceived(messageReceived2);
    event2.setMessageSuppressed(messageSuppressed2);
    event2.setOneOf(new ArrayList<>());
    event2.setPostShared(postShared2);
    event2.setRequestReceived(requestReceived2);
    event2.setRoomCreated(roomCreated2);
    event2.setRoomDeactivated(roomDeactivated2);
    event2.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner2);
    event2.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner2);
    event2.setRoomReactivated(roomReactivated2);
    event2.setRoomUpdated(roomUpdated2);
    event2.setTimerFired(timerFired2);
    event2.setUserJoinedRoom(userJoinedRoom2);
    event2.setUserLeftRoom(userLeftRoom2);
    event2.setUserRequestedJoinRoom(userRequestedJoinRoom2);

    // Act and Assert
    assertNotEquals(event, event2);
  }

  /**
   * Test {@link Event#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Event#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Event.equals(Object)", "int Event.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ActivityCompletedEvent activityCompleted = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompleted).setIfCondition(Mockito.<String>any());
    doNothing().when(activityCompleted).setActivityId(Mockito.<String>any());
    doNothing().when(activityCompleted).setId(Mockito.<String>any());
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");
    ActivityExpiredEvent activityExpired = mock(ActivityExpiredEvent.class);
    doNothing().when(activityExpired).setActivityId(Mockito.<String>any());
    doNothing().when(activityExpired).setId(Mockito.<String>any());
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

    Event event2 = new Event();
    event2.setActivityCompleted(activityCompleted2);
    event2.setActivityExpired(activityExpired2);
    event2.setActivityFailed(activityFailed2);
    event2.setAllOf(new ArrayList<>());
    event2.setConnectionAccepted(connectionAccepted2);
    event2.setConnectionRequested(connectionRequested2);
    event2.setFormReplied(formReplied2);
    event2.setImCreated(imCreated2);
    event2.setMessageReceived(messageReceived2);
    event2.setMessageSuppressed(messageSuppressed2);
    event2.setOneOf(new ArrayList<>());
    event2.setPostShared(postShared2);
    event2.setRequestReceived(requestReceived2);
    event2.setRoomCreated(roomCreated2);
    event2.setRoomDeactivated(roomDeactivated2);
    event2.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner2);
    event2.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner2);
    event2.setRoomReactivated(roomReactivated2);
    event2.setRoomUpdated(roomUpdated2);
    event2.setTimerFired(timerFired2);
    event2.setUserJoinedRoom(userJoinedRoom2);
    event2.setUserLeftRoom(userLeftRoom2);
    event2.setUserRequestedJoinRoom(userRequestedJoinRoom2);

    // Act and Assert
    assertNotEquals(event, event2);
  }

  /**
   * Test {@link Event#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Event#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Event.equals(Object)", "int Event.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ActivityCompletedEvent activityCompleted = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompleted).setIfCondition(Mockito.<String>any());
    doNothing().when(activityCompleted).setActivityId(Mockito.<String>any());
    doNothing().when(activityCompleted).setId(Mockito.<String>any());
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");
    ActivityExpiredEvent activityExpired = mock(ActivityExpiredEvent.class);
    doNothing().when(activityExpired).setActivityId(Mockito.<String>any());
    doNothing().when(activityExpired).setId(Mockito.<String>any());
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ActivityCompletedEvent activityCompleted2 = new ActivityCompletedEvent();
    activityCompleted2.setActivityId("42");
    activityCompleted2.setId("42");
    activityCompleted2.setIfCondition("42");

    ActivityExpiredEvent activityExpired2 = new ActivityExpiredEvent();
    activityExpired2.setActivityId("42");
    activityExpired2.setId("42");

    ActivityFailedEvent activityFailed2 = new ActivityFailedEvent();
    activityFailed2.setActivityId("42");
    activityFailed2.setId("42");

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

    Event event = new Event();
    event.setActivityCompleted(activityCompleted2);
    event.setActivityExpired(activityExpired2);
    event.setActivityFailed(activityFailed2);
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

    ArrayList<Event> allOf = new ArrayList<>();
    allOf.add(event);

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

    Event event2 = new Event();
    event2.setActivityCompleted(activityCompleted);
    event2.setActivityExpired(activityExpired);
    event2.setActivityFailed(activityFailed);
    event2.setAllOf(allOf);
    event2.setConnectionAccepted(connectionAccepted2);
    event2.setConnectionRequested(connectionRequested2);
    event2.setFormReplied(formReplied2);
    event2.setImCreated(imCreated2);
    event2.setMessageReceived(messageReceived2);
    event2.setMessageSuppressed(messageSuppressed2);
    event2.setOneOf(new ArrayList<>());
    event2.setPostShared(postShared2);
    event2.setRequestReceived(requestReceived2);
    event2.setRoomCreated(roomCreated2);
    event2.setRoomDeactivated(roomDeactivated2);
    event2.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner2);
    event2.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner2);
    event2.setRoomReactivated(roomReactivated2);
    event2.setRoomUpdated(roomUpdated2);
    event2.setTimerFired(timerFired2);
    event2.setUserJoinedRoom(userJoinedRoom2);
    event2.setUserLeftRoom(userLeftRoom2);
    event2.setUserRequestedJoinRoom(userRequestedJoinRoom2);

    ActivityCompletedEvent activityCompleted3 = new ActivityCompletedEvent();
    activityCompleted3.setActivityId("42");
    activityCompleted3.setId("42");
    activityCompleted3.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired3 = new ActivityExpiredEvent();
    activityExpired3.setActivityId("42");
    activityExpired3.setId("42");

    ActivityFailedEvent activityFailed3 = new ActivityFailedEvent();
    activityFailed3.setActivityId("42");
    activityFailed3.setId("42");

    ConnectionAcceptedEvent connectionAccepted3 = new ConnectionAcceptedEvent();
    connectionAccepted3.setId("42");

    ConnectionRequestedEvent connectionRequested3 = new ConnectionRequestedEvent();
    connectionRequested3.setId("42");

    FormRepliedEvent formReplied3 = new FormRepliedEvent();
    formReplied3.setExclusive(true);
    formReplied3.setFormId("42");
    formReplied3.setId("42");

    ImCreatedEvent imCreated3 = new ImCreatedEvent();
    imCreated3.setId("42");

    MessageReceivedEvent messageReceived3 = new MessageReceivedEvent();
    messageReceived3.setContent("Not all who wander are lost");
    messageReceived3.setId("42");
    messageReceived3.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed3 = new MessageSuppressedEvent();
    messageSuppressed3.setId("42");

    PostSharedEvent postShared3 = new PostSharedEvent();
    postShared3.setId("42");

    RequestReceivedEvent requestReceived3 = new RequestReceivedEvent();
    requestReceived3.setArguments(new HashMap<>());
    requestReceived3.setId("42");
    requestReceived3.setToken("ABC123");
    requestReceived3.setWorkflowId("42");

    RoomCreatedEvent roomCreated3 = new RoomCreatedEvent();
    roomCreated3.setId("42");

    RoomDeactivatedEvent roomDeactivated3 = new RoomDeactivatedEvent();
    roomDeactivated3.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner3 = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner3.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner3 = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner3.setId("42");

    RoomReactivatedEvent roomReactivated3 = new RoomReactivatedEvent();
    roomReactivated3.setId("42");

    RoomUpdatedEvent roomUpdated3 = new RoomUpdatedEvent();
    roomUpdated3.setId("42");

    TimerFiredEvent timerFired3 = new TimerFiredEvent();
    timerFired3.setAt("At");
    timerFired3.setId("42");
    timerFired3.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom3 = new UserJoinedRoomEvent();
    userJoinedRoom3.setId("42");

    UserLeftRoomEvent userLeftRoom3 = new UserLeftRoomEvent();
    userLeftRoom3.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom3 = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom3.setId("42");

    Event event3 = new Event();
    event3.setActivityCompleted(activityCompleted3);
    event3.setActivityExpired(activityExpired3);
    event3.setActivityFailed(activityFailed3);
    event3.setAllOf(new ArrayList<>());
    event3.setConnectionAccepted(connectionAccepted3);
    event3.setConnectionRequested(connectionRequested3);
    event3.setFormReplied(formReplied3);
    event3.setImCreated(imCreated3);
    event3.setMessageReceived(messageReceived3);
    event3.setMessageSuppressed(messageSuppressed3);
    event3.setOneOf(new ArrayList<>());
    event3.setPostShared(postShared3);
    event3.setRequestReceived(requestReceived3);
    event3.setRoomCreated(roomCreated3);
    event3.setRoomDeactivated(roomDeactivated3);
    event3.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner3);
    event3.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner3);
    event3.setRoomReactivated(roomReactivated3);
    event3.setRoomUpdated(roomUpdated3);
    event3.setTimerFired(timerFired3);
    event3.setUserJoinedRoom(userJoinedRoom3);
    event3.setUserLeftRoom(userLeftRoom3);
    event3.setUserRequestedJoinRoom(userRequestedJoinRoom3);

    // Act and Assert
    assertNotEquals(event2, event3);
  }

  /**
   * Test {@link Event#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Event#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Event.equals(Object)", "int Event.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    ActivityCompletedEvent activityCompleted = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompleted).setIfCondition(Mockito.<String>any());
    doNothing().when(activityCompleted).setActivityId(Mockito.<String>any());
    doNothing().when(activityCompleted).setId(Mockito.<String>any());
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");
    ActivityExpiredEvent activityExpired = mock(ActivityExpiredEvent.class);
    doNothing().when(activityExpired).setActivityId(Mockito.<String>any());
    doNothing().when(activityExpired).setId(Mockito.<String>any());
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");
    FormRepliedEvent formReplied = mock(FormRepliedEvent.class);
    doNothing().when(formReplied).setExclusive(Mockito.<Boolean>any());
    doNothing().when(formReplied).setFormId(Mockito.<String>any());
    doNothing().when(formReplied).setId(Mockito.<String>any());
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

    Event event2 = new Event();
    event2.setActivityCompleted(activityCompleted2);
    event2.setActivityExpired(activityExpired2);
    event2.setActivityFailed(activityFailed2);
    event2.setAllOf(new ArrayList<>());
    event2.setConnectionAccepted(connectionAccepted2);
    event2.setConnectionRequested(connectionRequested2);
    event2.setFormReplied(formReplied2);
    event2.setImCreated(imCreated2);
    event2.setMessageReceived(messageReceived2);
    event2.setMessageSuppressed(messageSuppressed2);
    event2.setOneOf(new ArrayList<>());
    event2.setPostShared(postShared2);
    event2.setRequestReceived(requestReceived2);
    event2.setRoomCreated(roomCreated2);
    event2.setRoomDeactivated(roomDeactivated2);
    event2.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner2);
    event2.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner2);
    event2.setRoomReactivated(roomReactivated2);
    event2.setRoomUpdated(roomUpdated2);
    event2.setTimerFired(timerFired2);
    event2.setUserJoinedRoom(userJoinedRoom2);
    event2.setUserLeftRoom(userLeftRoom2);
    event2.setUserRequestedJoinRoom(userRequestedJoinRoom2);

    // Act and Assert
    assertNotEquals(event, event2);
  }

  /**
   * Test {@link Event#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Event#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Event.equals(Object)", "int Event.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    ActivityCompletedEvent activityCompleted = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompleted).setIfCondition(Mockito.<String>any());
    doNothing().when(activityCompleted).setActivityId(Mockito.<String>any());
    doNothing().when(activityCompleted).setId(Mockito.<String>any());
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");
    ActivityExpiredEvent activityExpired = mock(ActivityExpiredEvent.class);
    doNothing().when(activityExpired).setActivityId(Mockito.<String>any());
    doNothing().when(activityExpired).setId(Mockito.<String>any());
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");
    FormRepliedEvent formReplied = mock(FormRepliedEvent.class);
    doNothing().when(formReplied).setExclusive(Mockito.<Boolean>any());
    doNothing().when(formReplied).setFormId(Mockito.<String>any());
    doNothing().when(formReplied).setId(Mockito.<String>any());
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

    Event event = new Event();
    event.setActivityCompleted(activityCompleted2);
    event.setActivityExpired(activityExpired2);
    event.setActivityFailed(activityFailed2);
    event.setAllOf(new ArrayList<>());
    event.setConnectionAccepted(connectionAccepted2);
    event.setConnectionRequested(connectionRequested2);
    event.setFormReplied(formReplied2);
    event.setImCreated(imCreated2);
    event.setMessageReceived(messageReceived2);
    event.setMessageSuppressed(messageSuppressed2);
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

    ArrayList<Event> oneOf = new ArrayList<>();
    oneOf.add(event);

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

    Event event2 = new Event();
    event2.setActivityCompleted(activityCompleted);
    event2.setActivityExpired(activityExpired);
    event2.setActivityFailed(activityFailed);
    event2.setAllOf(new ArrayList<>());
    event2.setConnectionAccepted(connectionAccepted);
    event2.setConnectionRequested(connectionRequested);
    event2.setFormReplied(formReplied);
    event2.setImCreated(imCreated);
    event2.setMessageReceived(messageReceived);
    event2.setMessageSuppressed(messageSuppressed);
    event2.setOneOf(oneOf);
    event2.setPostShared(postShared2);
    event2.setRequestReceived(requestReceived2);
    event2.setRoomCreated(roomCreated2);
    event2.setRoomDeactivated(roomDeactivated2);
    event2.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner2);
    event2.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner2);
    event2.setRoomReactivated(roomReactivated2);
    event2.setRoomUpdated(roomUpdated2);
    event2.setTimerFired(timerFired2);
    event2.setUserJoinedRoom(userJoinedRoom2);
    event2.setUserLeftRoom(userLeftRoom2);
    event2.setUserRequestedJoinRoom(userRequestedJoinRoom2);

    ActivityCompletedEvent activityCompleted3 = new ActivityCompletedEvent();
    activityCompleted3.setActivityId("42");
    activityCompleted3.setId("42");
    activityCompleted3.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired3 = new ActivityExpiredEvent();
    activityExpired3.setActivityId("42");
    activityExpired3.setId("42");

    ActivityFailedEvent activityFailed3 = new ActivityFailedEvent();
    activityFailed3.setActivityId("42");
    activityFailed3.setId("42");

    ConnectionAcceptedEvent connectionAccepted3 = new ConnectionAcceptedEvent();
    connectionAccepted3.setId("42");

    ConnectionRequestedEvent connectionRequested3 = new ConnectionRequestedEvent();
    connectionRequested3.setId("42");

    FormRepliedEvent formReplied3 = new FormRepliedEvent();
    formReplied3.setExclusive(true);
    formReplied3.setFormId("42");
    formReplied3.setId("42");

    ImCreatedEvent imCreated3 = new ImCreatedEvent();
    imCreated3.setId("42");

    MessageReceivedEvent messageReceived3 = new MessageReceivedEvent();
    messageReceived3.setContent("Not all who wander are lost");
    messageReceived3.setId("42");
    messageReceived3.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed3 = new MessageSuppressedEvent();
    messageSuppressed3.setId("42");

    PostSharedEvent postShared3 = new PostSharedEvent();
    postShared3.setId("42");

    RequestReceivedEvent requestReceived3 = new RequestReceivedEvent();
    requestReceived3.setArguments(new HashMap<>());
    requestReceived3.setId("42");
    requestReceived3.setToken("ABC123");
    requestReceived3.setWorkflowId("42");

    RoomCreatedEvent roomCreated3 = new RoomCreatedEvent();
    roomCreated3.setId("42");

    RoomDeactivatedEvent roomDeactivated3 = new RoomDeactivatedEvent();
    roomDeactivated3.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner3 = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner3.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner3 = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner3.setId("42");

    RoomReactivatedEvent roomReactivated3 = new RoomReactivatedEvent();
    roomReactivated3.setId("42");

    RoomUpdatedEvent roomUpdated3 = new RoomUpdatedEvent();
    roomUpdated3.setId("42");

    TimerFiredEvent timerFired3 = new TimerFiredEvent();
    timerFired3.setAt("At");
    timerFired3.setId("42");
    timerFired3.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom3 = new UserJoinedRoomEvent();
    userJoinedRoom3.setId("42");

    UserLeftRoomEvent userLeftRoom3 = new UserLeftRoomEvent();
    userLeftRoom3.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom3 = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom3.setId("42");

    Event event3 = new Event();
    event3.setActivityCompleted(activityCompleted3);
    event3.setActivityExpired(activityExpired3);
    event3.setActivityFailed(activityFailed3);
    event3.setAllOf(new ArrayList<>());
    event3.setConnectionAccepted(connectionAccepted3);
    event3.setConnectionRequested(connectionRequested3);
    event3.setFormReplied(formReplied3);
    event3.setImCreated(imCreated3);
    event3.setMessageReceived(messageReceived3);
    event3.setMessageSuppressed(messageSuppressed3);
    event3.setOneOf(new ArrayList<>());
    event3.setPostShared(postShared3);
    event3.setRequestReceived(requestReceived3);
    event3.setRoomCreated(roomCreated3);
    event3.setRoomDeactivated(roomDeactivated3);
    event3.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner3);
    event3.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner3);
    event3.setRoomReactivated(roomReactivated3);
    event3.setRoomUpdated(roomUpdated3);
    event3.setTimerFired(timerFired3);
    event3.setUserJoinedRoom(userJoinedRoom3);
    event3.setUserLeftRoom(userLeftRoom3);
    event3.setUserRequestedJoinRoom(userRequestedJoinRoom3);

    // Act and Assert
    assertNotEquals(event2, event3);
  }

  /**
   * Test {@link Event#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Event#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Event.equals(Object)", "int Event.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    ActivityCompletedEvent activityCompleted = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompleted).setIfCondition(Mockito.<String>any());
    doNothing().when(activityCompleted).setActivityId(Mockito.<String>any());
    doNothing().when(activityCompleted).setId(Mockito.<String>any());
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");
    ActivityExpiredEvent activityExpired = mock(ActivityExpiredEvent.class);
    doNothing().when(activityExpired).setActivityId(Mockito.<String>any());
    doNothing().when(activityExpired).setId(Mockito.<String>any());
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");
    FormRepliedEvent formReplied = mock(FormRepliedEvent.class);
    doNothing().when(formReplied).setExclusive(Mockito.<Boolean>any());
    doNothing().when(formReplied).setFormId(Mockito.<String>any());
    doNothing().when(formReplied).setId(Mockito.<String>any());
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

    ActivityCompletedEvent activityCompleted3 = new ActivityCompletedEvent();
    activityCompleted3.setActivityId("42");
    activityCompleted3.setId("42");
    activityCompleted3.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired3 = new ActivityExpiredEvent();
    activityExpired3.setActivityId("42");
    activityExpired3.setId("42");

    ActivityFailedEvent activityFailed3 = new ActivityFailedEvent();
    activityFailed3.setActivityId("42");
    activityFailed3.setId("42");

    ConnectionAcceptedEvent connectionAccepted3 = new ConnectionAcceptedEvent();
    connectionAccepted3.setId("42");

    ConnectionRequestedEvent connectionRequested3 = new ConnectionRequestedEvent();
    connectionRequested3.setId("42");

    FormRepliedEvent formReplied3 = new FormRepliedEvent();
    formReplied3.setExclusive(true);
    formReplied3.setFormId("42");
    formReplied3.setId("42");

    ImCreatedEvent imCreated3 = new ImCreatedEvent();
    imCreated3.setId("42");

    MessageReceivedEvent messageReceived3 = new MessageReceivedEvent();
    messageReceived3.setContent("Not all who wander are lost");
    messageReceived3.setId("42");
    messageReceived3.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed3 = new MessageSuppressedEvent();
    messageSuppressed3.setId("42");

    PostSharedEvent postShared3 = new PostSharedEvent();
    postShared3.setId("42");

    RequestReceivedEvent requestReceived3 = new RequestReceivedEvent();
    requestReceived3.setArguments(new HashMap<>());
    requestReceived3.setId("42");
    requestReceived3.setToken("ABC123");
    requestReceived3.setWorkflowId("42");

    RoomCreatedEvent roomCreated3 = new RoomCreatedEvent();
    roomCreated3.setId("42");

    RoomDeactivatedEvent roomDeactivated3 = new RoomDeactivatedEvent();
    roomDeactivated3.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner3 = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner3.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner3 = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner3.setId("42");

    RoomReactivatedEvent roomReactivated3 = new RoomReactivatedEvent();
    roomReactivated3.setId("42");

    RoomUpdatedEvent roomUpdated3 = new RoomUpdatedEvent();
    roomUpdated3.setId("42");

    TimerFiredEvent timerFired3 = new TimerFiredEvent();
    timerFired3.setAt("At");
    timerFired3.setId("42");
    timerFired3.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom3 = new UserJoinedRoomEvent();
    userJoinedRoom3.setId("42");

    UserLeftRoomEvent userLeftRoom3 = new UserLeftRoomEvent();
    userLeftRoom3.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom3 = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom3.setId("42");

    EventWithTimeout eventWithTimeout = new EventWithTimeout();
    eventWithTimeout.setActivityCompleted(activityCompleted3);
    eventWithTimeout.setActivityExpired(activityExpired3);
    eventWithTimeout.setActivityFailed(activityFailed3);
    eventWithTimeout.setAllOf(new ArrayList<>());
    eventWithTimeout.setConnectionAccepted(connectionAccepted3);
    eventWithTimeout.setConnectionRequested(connectionRequested3);
    eventWithTimeout.setFormReplied(formReplied3);
    eventWithTimeout.setImCreated(imCreated3);
    eventWithTimeout.setMessageReceived(messageReceived3);
    eventWithTimeout.setMessageSuppressed(messageSuppressed3);
    eventWithTimeout.setOneOf(new ArrayList<>());
    eventWithTimeout.setPostShared(postShared3);
    eventWithTimeout.setRequestReceived(requestReceived3);
    eventWithTimeout.setRoomCreated(roomCreated3);
    eventWithTimeout.setRoomDeactivated(roomDeactivated3);
    eventWithTimeout.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner3);
    eventWithTimeout.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner3);
    eventWithTimeout.setRoomReactivated(roomReactivated3);
    eventWithTimeout.setRoomUpdated(roomUpdated3);
    eventWithTimeout.setTimeout("Timeout");
    eventWithTimeout.setTimerFired(timerFired3);
    eventWithTimeout.setUserJoinedRoom(userJoinedRoom3);
    eventWithTimeout.setUserLeftRoom(userLeftRoom3);
    eventWithTimeout.setUserRequestedJoinRoom(userRequestedJoinRoom3);
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
    eventWithTimeout.setTimerFired(timerFired2);
    eventWithTimeout.setUserJoinedRoom(userJoinedRoom2);
    eventWithTimeout.setUserLeftRoom(userLeftRoom2);
    eventWithTimeout.setUserRequestedJoinRoom(userRequestedJoinRoom2);

    // Act and Assert
    assertNotEquals(event, eventWithTimeout);
  }

  /**
   * Test {@link Event#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Event#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Event.equals(Object)", "int Event.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    ActivityCompletedEvent activityCompleted = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompleted).setIfCondition(Mockito.<String>any());
    doNothing().when(activityCompleted).setActivityId(Mockito.<String>any());
    doNothing().when(activityCompleted).setId(Mockito.<String>any());
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");
    ActivityExpiredEvent activityExpired = mock(ActivityExpiredEvent.class);
    doNothing().when(activityExpired).setActivityId(Mockito.<String>any());
    doNothing().when(activityExpired).setId(Mockito.<String>any());
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");
    FormRepliedEvent formReplied = mock(FormRepliedEvent.class);
    doNothing().when(formReplied).setExclusive(Mockito.<Boolean>any());
    doNothing().when(formReplied).setFormId(Mockito.<String>any());
    doNothing().when(formReplied).setId(Mockito.<String>any());
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

    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(true);
    formRepliedEvent.setFormId("42");
    formRepliedEvent.setId("42");
    EventWithTimeout eventWithTimeout = mock(EventWithTimeout.class);
    when(eventWithTimeout.getFormReplied()).thenReturn(formRepliedEvent);
    when(eventWithTimeout.getAllOf()).thenReturn(new ArrayList<>());
    when(eventWithTimeout.getOneOf()).thenReturn(new ArrayList<>());
    when(eventWithTimeout.canEqual(Mockito.<Object>any())).thenReturn(true);
    doNothing().when(eventWithTimeout).setActivityCompleted(Mockito.<ActivityCompletedEvent>any());
    doNothing().when(eventWithTimeout).setActivityExpired(Mockito.<ActivityExpiredEvent>any());
    doNothing().when(eventWithTimeout).setActivityFailed(Mockito.<ActivityFailedEvent>any());
    doNothing().when(eventWithTimeout).setAllOf(Mockito.<List<Event>>any());
    doNothing().when(eventWithTimeout).setConnectionAccepted(Mockito.<ConnectionAcceptedEvent>any());
    doNothing().when(eventWithTimeout).setConnectionRequested(Mockito.<ConnectionRequestedEvent>any());
    doNothing().when(eventWithTimeout).setFormReplied(Mockito.<FormRepliedEvent>any());
    doNothing().when(eventWithTimeout).setImCreated(Mockito.<ImCreatedEvent>any());
    doNothing().when(eventWithTimeout).setMessageReceived(Mockito.<MessageReceivedEvent>any());
    doNothing().when(eventWithTimeout).setMessageSuppressed(Mockito.<MessageSuppressedEvent>any());
    doNothing().when(eventWithTimeout).setOneOf(Mockito.<List<Event>>any());
    doNothing().when(eventWithTimeout).setPostShared(Mockito.<PostSharedEvent>any());
    doNothing().when(eventWithTimeout).setRequestReceived(Mockito.<RequestReceivedEvent>any());
    doNothing().when(eventWithTimeout).setRoomCreated(Mockito.<RoomCreatedEvent>any());
    doNothing().when(eventWithTimeout).setRoomDeactivated(Mockito.<RoomDeactivatedEvent>any());
    doNothing().when(eventWithTimeout).setRoomMemberDemotedFromOwner(Mockito.<RoomMemberDemotedFromOwnerEvent>any());
    doNothing().when(eventWithTimeout).setRoomMemberPromotedToOwner(Mockito.<RoomMemberPromotedToOwnerEvent>any());
    doNothing().when(eventWithTimeout).setRoomReactivated(Mockito.<RoomReactivatedEvent>any());
    doNothing().when(eventWithTimeout).setRoomUpdated(Mockito.<RoomUpdatedEvent>any());
    doNothing().when(eventWithTimeout).setTimerFired(Mockito.<TimerFiredEvent>any());
    doNothing().when(eventWithTimeout).setUserJoinedRoom(Mockito.<UserJoinedRoomEvent>any());
    doNothing().when(eventWithTimeout).setUserLeftRoom(Mockito.<UserLeftRoomEvent>any());
    doNothing().when(eventWithTimeout).setUserRequestedJoinRoom(Mockito.<UserRequestedToJoinRoomEvent>any());
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
    eventWithTimeout.setTimerFired(timerFired2);
    eventWithTimeout.setUserJoinedRoom(userJoinedRoom2);
    eventWithTimeout.setUserLeftRoom(userLeftRoom2);
    eventWithTimeout.setUserRequestedJoinRoom(userRequestedJoinRoom2);

    // Act and Assert
    assertNotEquals(event, eventWithTimeout);
  }

  /**
   * Test {@link Event#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Event#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Event.equals(Object)", "int Event.hashCode()"})
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

    // Act and Assert
    assertNotEquals(event, null);
  }

  /**
   * Test {@link Event#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Event#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Event.equals(Object)", "int Event.hashCode()"})
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

    // Act and Assert
    assertNotEquals(event, "Different type to Event");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Event}
   *   <li>{@link Event#setActivityCompleted(ActivityCompletedEvent)}
   *   <li>{@link Event#setActivityExpired(ActivityExpiredEvent)}
   *   <li>{@link Event#setActivityFailed(ActivityFailedEvent)}
   *   <li>{@link Event#setAllOf(List)}
   *   <li>{@link Event#setConnectionAccepted(ConnectionAcceptedEvent)}
   *   <li>{@link Event#setConnectionRequested(ConnectionRequestedEvent)}
   *   <li>{@link Event#setFormReplied(FormRepliedEvent)}
   *   <li>{@link Event#setImCreated(ImCreatedEvent)}
   *   <li>{@link Event#setMessageReceived(MessageReceivedEvent)}
   *   <li>{@link Event#setMessageSuppressed(MessageSuppressedEvent)}
   *   <li>{@link Event#setOneOf(List)}
   *   <li>{@link Event#setPostShared(PostSharedEvent)}
   *   <li>{@link Event#setRequestReceived(RequestReceivedEvent)}
   *   <li>{@link Event#setRoomCreated(RoomCreatedEvent)}
   *   <li>{@link Event#setRoomDeactivated(RoomDeactivatedEvent)}
   *   <li>{@link Event#setRoomMemberDemotedFromOwner(RoomMemberDemotedFromOwnerEvent)}
   *   <li>{@link Event#setRoomMemberPromotedToOwner(RoomMemberPromotedToOwnerEvent)}
   *   <li>{@link Event#setRoomReactivated(RoomReactivatedEvent)}
   *   <li>{@link Event#setRoomUpdated(RoomUpdatedEvent)}
   *   <li>{@link Event#setTimerFired(TimerFiredEvent)}
   *   <li>{@link Event#setUserJoinedRoom(UserJoinedRoomEvent)}
   *   <li>{@link Event#setUserLeftRoom(UserLeftRoomEvent)}
   *   <li>{@link Event#setUserRequestedJoinRoom(UserRequestedToJoinRoomEvent)}
   *   <li>{@link Event#toString()}
   *   <li>{@link Event#getActivityCompleted()}
   *   <li>{@link Event#getActivityExpired()}
   *   <li>{@link Event#getActivityFailed()}
   *   <li>{@link Event#getAllOf()}
   *   <li>{@link Event#getConnectionAccepted()}
   *   <li>{@link Event#getConnectionRequested()}
   *   <li>{@link Event#getFormReplied()}
   *   <li>{@link Event#getImCreated()}
   *   <li>{@link Event#getMessageReceived()}
   *   <li>{@link Event#getMessageSuppressed()}
   *   <li>{@link Event#getOneOf()}
   *   <li>{@link Event#getPostShared()}
   *   <li>{@link Event#getRequestReceived()}
   *   <li>{@link Event#getRoomCreated()}
   *   <li>{@link Event#getRoomDeactivated()}
   *   <li>{@link Event#getRoomMemberDemotedFromOwner()}
   *   <li>{@link Event#getRoomMemberPromotedToOwner()}
   *   <li>{@link Event#getRoomReactivated()}
   *   <li>{@link Event#getRoomUpdated()}
   *   <li>{@link Event#getTimerFired()}
   *   <li>{@link Event#getUserJoinedRoom()}
   *   <li>{@link Event#getUserLeftRoom()}
   *   <li>{@link Event#getUserRequestedJoinRoom()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Event.<init>()", "ActivityCompletedEvent Event.getActivityCompleted()",
      "ActivityExpiredEvent Event.getActivityExpired()", "ActivityFailedEvent Event.getActivityFailed()",
      "List Event.getAllOf()", "ConnectionAcceptedEvent Event.getConnectionAccepted()",
      "ConnectionRequestedEvent Event.getConnectionRequested()", "FormRepliedEvent Event.getFormReplied()",
      "ImCreatedEvent Event.getImCreated()", "MessageReceivedEvent Event.getMessageReceived()",
      "MessageSuppressedEvent Event.getMessageSuppressed()", "List Event.getOneOf()",
      "PostSharedEvent Event.getPostShared()", "RequestReceivedEvent Event.getRequestReceived()",
      "RoomCreatedEvent Event.getRoomCreated()", "RoomDeactivatedEvent Event.getRoomDeactivated()",
      "RoomMemberDemotedFromOwnerEvent Event.getRoomMemberDemotedFromOwner()",
      "RoomMemberPromotedToOwnerEvent Event.getRoomMemberPromotedToOwner()",
      "RoomReactivatedEvent Event.getRoomReactivated()", "RoomUpdatedEvent Event.getRoomUpdated()",
      "TimerFiredEvent Event.getTimerFired()", "UserJoinedRoomEvent Event.getUserJoinedRoom()",
      "UserLeftRoomEvent Event.getUserLeftRoom()", "UserRequestedToJoinRoomEvent Event.getUserRequestedJoinRoom()",
      "void Event.setActivityCompleted(ActivityCompletedEvent)", "void Event.setActivityExpired(ActivityExpiredEvent)",
      "void Event.setActivityFailed(ActivityFailedEvent)", "void Event.setAllOf(List)",
      "void Event.setConnectionAccepted(ConnectionAcceptedEvent)",
      "void Event.setConnectionRequested(ConnectionRequestedEvent)", "void Event.setFormReplied(FormRepliedEvent)",
      "void Event.setImCreated(ImCreatedEvent)", "void Event.setMessageReceived(MessageReceivedEvent)",
      "void Event.setMessageSuppressed(MessageSuppressedEvent)", "void Event.setOneOf(List)",
      "void Event.setPostShared(PostSharedEvent)", "void Event.setRequestReceived(RequestReceivedEvent)",
      "void Event.setRoomCreated(RoomCreatedEvent)", "void Event.setRoomDeactivated(RoomDeactivatedEvent)",
      "void Event.setRoomMemberDemotedFromOwner(RoomMemberDemotedFromOwnerEvent)",
      "void Event.setRoomMemberPromotedToOwner(RoomMemberPromotedToOwnerEvent)",
      "void Event.setRoomReactivated(RoomReactivatedEvent)", "void Event.setRoomUpdated(RoomUpdatedEvent)",
      "void Event.setTimerFired(TimerFiredEvent)", "void Event.setUserJoinedRoom(UserJoinedRoomEvent)",
      "void Event.setUserLeftRoom(UserLeftRoomEvent)",
      "void Event.setUserRequestedJoinRoom(UserRequestedToJoinRoomEvent)", "String Event.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    Event actualEvent = new Event();
    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");
    actualEvent.setActivityCompleted(activityCompleted);
    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");
    actualEvent.setActivityExpired(activityExpired);
    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");
    actualEvent.setActivityFailed(activityFailed);
    ArrayList<Event> allOf = new ArrayList<>();
    actualEvent.setAllOf(allOf);
    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");
    actualEvent.setConnectionAccepted(connectionAccepted);
    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");
    actualEvent.setConnectionRequested(connectionRequested);
    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");
    actualEvent.setFormReplied(formReplied);
    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");
    actualEvent.setImCreated(imCreated);
    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);
    actualEvent.setMessageReceived(messageReceived);
    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");
    actualEvent.setMessageSuppressed(messageSuppressed);
    ArrayList<Event> oneOf = new ArrayList<>();
    actualEvent.setOneOf(oneOf);
    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");
    actualEvent.setPostShared(postShared);
    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(new HashMap<>());
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");
    actualEvent.setRequestReceived(requestReceived);
    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");
    actualEvent.setRoomCreated(roomCreated);
    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");
    actualEvent.setRoomDeactivated(roomDeactivated);
    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");
    actualEvent.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");
    actualEvent.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");
    actualEvent.setRoomReactivated(roomReactivated);
    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");
    actualEvent.setRoomUpdated(roomUpdated);
    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("At");
    timerFired.setId("42");
    timerFired.setRepeat("Repeat");
    actualEvent.setTimerFired(timerFired);
    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");
    actualEvent.setUserJoinedRoom(userJoinedRoom);
    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");
    actualEvent.setUserLeftRoom(userLeftRoom);
    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");
    actualEvent.setUserRequestedJoinRoom(userRequestedJoinRoom);
    String actualToStringResult = actualEvent.toString();
    ActivityCompletedEvent actualActivityCompleted = actualEvent.getActivityCompleted();
    ActivityExpiredEvent actualActivityExpired = actualEvent.getActivityExpired();
    ActivityFailedEvent actualActivityFailed = actualEvent.getActivityFailed();
    List<Event> actualAllOf = actualEvent.getAllOf();
    ConnectionAcceptedEvent actualConnectionAccepted = actualEvent.getConnectionAccepted();
    ConnectionRequestedEvent actualConnectionRequested = actualEvent.getConnectionRequested();
    FormRepliedEvent actualFormReplied = actualEvent.getFormReplied();
    ImCreatedEvent actualImCreated = actualEvent.getImCreated();
    MessageReceivedEvent actualMessageReceived = actualEvent.getMessageReceived();
    MessageSuppressedEvent actualMessageSuppressed = actualEvent.getMessageSuppressed();
    List<Event> actualOneOf = actualEvent.getOneOf();
    PostSharedEvent actualPostShared = actualEvent.getPostShared();
    RequestReceivedEvent actualRequestReceived = actualEvent.getRequestReceived();
    RoomCreatedEvent actualRoomCreated = actualEvent.getRoomCreated();
    RoomDeactivatedEvent actualRoomDeactivated = actualEvent.getRoomDeactivated();
    RoomMemberDemotedFromOwnerEvent actualRoomMemberDemotedFromOwner = actualEvent.getRoomMemberDemotedFromOwner();
    RoomMemberPromotedToOwnerEvent actualRoomMemberPromotedToOwner = actualEvent.getRoomMemberPromotedToOwner();
    RoomReactivatedEvent actualRoomReactivated = actualEvent.getRoomReactivated();
    RoomUpdatedEvent actualRoomUpdated = actualEvent.getRoomUpdated();
    TimerFiredEvent actualTimerFired = actualEvent.getTimerFired();
    UserJoinedRoomEvent actualUserJoinedRoom = actualEvent.getUserJoinedRoom();
    UserLeftRoomEvent actualUserLeftRoom = actualEvent.getUserLeftRoom();
    UserRequestedToJoinRoomEvent actualUserRequestedJoinRoom = actualEvent.getUserRequestedJoinRoom();

    // Assert
    assertEquals("42", actualConnectionAccepted.getId());
    assertEquals("42", actualConnectionRequested.getId());
    assertEquals("42", actualImCreated.getId());
    assertEquals("42", actualMessageSuppressed.getId());
    assertEquals("42", actualPostShared.getId());
    assertEquals("42", actualRoomCreated.getId());
    assertEquals("42", actualRoomDeactivated.getId());
    assertEquals("42", actualRoomMemberDemotedFromOwner.getId());
    assertEquals("42", actualRoomMemberPromotedToOwner.getId());
    assertEquals("42", actualRoomReactivated.getId());
    assertEquals("42", actualRoomUpdated.getId());
    assertEquals("42", actualUserJoinedRoom.getId());
    assertEquals("42", actualUserLeftRoom.getId());
    assertEquals("42", actualUserRequestedJoinRoom.getId());
    assertEquals("Event(oneOf=[], allOf=[], formReplied=FormRepliedEvent(formId=42, exclusive=true), activityExpired"
        + "=ActivityExpiredEvent(), activityCompleted=ActivityCompletedEvent(ifCondition=If Condition),"
        + " activityFailed=ActivityFailedEvent(), messageReceived=MessageReceivedEvent(content=Not all who wander"
        + " are lost, requiresBotMention=true), messageSuppressed=MessageSuppressedEvent(), postShared=PostSharedEvent"
        + "(), imCreated=ImCreatedEvent(), roomCreated=RoomCreatedEvent(), roomUpdated=RoomUpdatedEvent(),"
        + " roomDeactivated=RoomDeactivatedEvent(), roomReactivated=RoomReactivatedEvent(), roomMemberPromotedToOwner"
        + "=RoomMemberPromotedToOwnerEvent(), roomMemberDemotedFromOwner=RoomMemberDemotedFromOwnerEvent(),"
        + " userJoinedRoom=UserJoinedRoomEvent(), userLeftRoom=UserLeftRoomEvent(), userRequestedJoinRoom"
        + "=UserRequestedToJoinRoomEvent(), connectionRequested=ConnectionRequestedEvent(), connectionAccepted"
        + "=ConnectionAcceptedEvent(), timerFired=TimerFiredEvent(at=At, repeat=Repeat), requestReceived"
        + "=RequestReceivedEvent(token=ABC123, arguments={}, workflowId=42))", actualToStringResult);
    assertTrue(actualAllOf.isEmpty());
    assertTrue(actualOneOf.isEmpty());
    assertSame(activityCompleted, actualActivityCompleted);
    assertSame(activityExpired, actualActivityExpired);
    assertSame(activityFailed, actualActivityFailed);
    assertSame(connectionAccepted, actualConnectionAccepted);
    assertSame(connectionRequested, actualConnectionRequested);
    assertSame(formReplied, actualFormReplied);
    assertSame(imCreated, actualImCreated);
    assertSame(messageReceived, actualMessageReceived);
    assertSame(messageSuppressed, actualMessageSuppressed);
    assertSame(postShared, actualPostShared);
    assertSame(requestReceived, actualRequestReceived);
    assertSame(roomCreated, actualRoomCreated);
    assertSame(roomDeactivated, actualRoomDeactivated);
    assertSame(roomMemberDemotedFromOwner, actualRoomMemberDemotedFromOwner);
    assertSame(roomMemberPromotedToOwner, actualRoomMemberPromotedToOwner);
    assertSame(roomReactivated, actualRoomReactivated);
    assertSame(roomUpdated, actualRoomUpdated);
    assertSame(timerFired, actualTimerFired);
    assertSame(userJoinedRoom, actualUserJoinedRoom);
    assertSame(userLeftRoom, actualUserLeftRoom);
    assertSame(userRequestedJoinRoom, actualUserRequestedJoinRoom);
    assertSame(allOf, actualAllOf);
    assertSame(oneOf, actualOneOf);
  }
}
