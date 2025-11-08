package com.symphony.bdk.workflow.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WorkflowEventTypeDiffblueTest {
  /**
   * Method under test: {@link WorkflowEventType#getEventType(Event)}
   */
  @Test
  void testGetEventType() {
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

    // Act
    Optional<WorkflowEventType> actualEventType = WorkflowEventType.getEventType(event);

    // Assert
    assertEquals(WorkflowEventType.MESSAGE_RECEIVED, actualEventType.get());
    assertTrue(actualEventType.isPresent());
  }

  /**
   * Method under test: {@link WorkflowEventType#getEventType(Event)}
   */
  @Test
  void testGetEventType2() {
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

    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    messageReceivedEvent.setContent("Not all who wander are lost");
    messageReceivedEvent.setId("42");
    messageReceivedEvent.setRequiresBotMention(true);
    Event event = mock(Event.class);
    when(event.getMessageReceived()).thenReturn(messageReceivedEvent);
    doNothing().when(event).setActivityCompleted(Mockito.<ActivityCompletedEvent>any());
    doNothing().when(event).setActivityExpired(Mockito.<ActivityExpiredEvent>any());
    doNothing().when(event).setActivityFailed(Mockito.<ActivityFailedEvent>any());
    doNothing().when(event).setAllOf(Mockito.<List<Event>>any());
    doNothing().when(event).setConnectionAccepted(Mockito.<ConnectionAcceptedEvent>any());
    doNothing().when(event).setConnectionRequested(Mockito.<ConnectionRequestedEvent>any());
    doNothing().when(event).setFormReplied(Mockito.<FormRepliedEvent>any());
    doNothing().when(event).setImCreated(Mockito.<ImCreatedEvent>any());
    doNothing().when(event).setMessageReceived(Mockito.<MessageReceivedEvent>any());
    doNothing().when(event).setMessageSuppressed(Mockito.<MessageSuppressedEvent>any());
    doNothing().when(event).setOneOf(Mockito.<List<Event>>any());
    doNothing().when(event).setPostShared(Mockito.<PostSharedEvent>any());
    doNothing().when(event).setRequestReceived(Mockito.<RequestReceivedEvent>any());
    doNothing().when(event).setRoomCreated(Mockito.<RoomCreatedEvent>any());
    doNothing().when(event).setRoomDeactivated(Mockito.<RoomDeactivatedEvent>any());
    doNothing().when(event).setRoomMemberDemotedFromOwner(Mockito.<RoomMemberDemotedFromOwnerEvent>any());
    doNothing().when(event).setRoomMemberPromotedToOwner(Mockito.<RoomMemberPromotedToOwnerEvent>any());
    doNothing().when(event).setRoomReactivated(Mockito.<RoomReactivatedEvent>any());
    doNothing().when(event).setRoomUpdated(Mockito.<RoomUpdatedEvent>any());
    doNothing().when(event).setTimerFired(Mockito.<TimerFiredEvent>any());
    doNothing().when(event).setUserJoinedRoom(Mockito.<UserJoinedRoomEvent>any());
    doNothing().when(event).setUserLeftRoom(Mockito.<UserLeftRoomEvent>any());
    doNothing().when(event).setUserRequestedJoinRoom(Mockito.<UserRequestedToJoinRoomEvent>any());
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

    // Act
    Optional<WorkflowEventType> actualEventType = WorkflowEventType.getEventType(event);

    // Assert
    verify(event).getMessageReceived();
    verify(event).setActivityCompleted(isA(ActivityCompletedEvent.class));
    verify(event).setActivityExpired(isA(ActivityExpiredEvent.class));
    verify(event).setActivityFailed(isA(ActivityFailedEvent.class));
    verify(event).setAllOf(isA(List.class));
    verify(event).setConnectionAccepted(isA(ConnectionAcceptedEvent.class));
    verify(event).setConnectionRequested(isA(ConnectionRequestedEvent.class));
    verify(event).setFormReplied(isA(FormRepliedEvent.class));
    verify(event).setImCreated(isA(ImCreatedEvent.class));
    verify(event).setMessageReceived(isA(MessageReceivedEvent.class));
    verify(event).setMessageSuppressed(isA(MessageSuppressedEvent.class));
    verify(event).setOneOf(isA(List.class));
    verify(event).setPostShared(isA(PostSharedEvent.class));
    verify(event).setRequestReceived(isA(RequestReceivedEvent.class));
    verify(event).setRoomCreated(isA(RoomCreatedEvent.class));
    verify(event).setRoomDeactivated(isA(RoomDeactivatedEvent.class));
    verify(event).setRoomMemberDemotedFromOwner(isA(RoomMemberDemotedFromOwnerEvent.class));
    verify(event).setRoomMemberPromotedToOwner(isA(RoomMemberPromotedToOwnerEvent.class));
    verify(event).setRoomReactivated(isA(RoomReactivatedEvent.class));
    verify(event).setRoomUpdated(isA(RoomUpdatedEvent.class));
    verify(event).setTimerFired(isA(TimerFiredEvent.class));
    verify(event).setUserJoinedRoom(isA(UserJoinedRoomEvent.class));
    verify(event).setUserLeftRoom(isA(UserLeftRoomEvent.class));
    verify(event).setUserRequestedJoinRoom(isA(UserRequestedToJoinRoomEvent.class));
    assertEquals(WorkflowEventType.MESSAGE_RECEIVED, actualEventType.get());
    assertTrue(actualEventType.isPresent());
  }
}
