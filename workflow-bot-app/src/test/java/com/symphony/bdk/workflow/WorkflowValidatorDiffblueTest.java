package com.symphony.bdk.workflow;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.swadl.exception.InvalidActivityException;
import com.symphony.bdk.workflow.swadl.v1.Activity;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
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
import org.mockito.Mockito;

class WorkflowValidatorDiffblueTest {
  /**
   * Test {@link WorkflowValidator#validateFirstActivity(BaseActivity, Event, String)}.
   * <p>
   * Method under test: {@link WorkflowValidator#validateFirstActivity(BaseActivity, Event, String)}
   */
  @Test
  @DisplayName("Test validateFirstActivity(BaseActivity, Event, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowValidator.validateFirstActivity(BaseActivity, Event, String)"})
  void testValidateFirstActivity() {
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

    DoSomething activity = new DoSomething();
    activity.setOn(on);

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

    EventWithTimeout event = new EventWithTimeout();
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
    event.setPostShared(postShared2);
    event.setRequestReceived(requestReceived2);
    event.setRoomCreated(roomCreated2);
    event.setRoomDeactivated(roomDeactivated2);
    event.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner2);
    event.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner2);
    event.setRoomReactivated(roomReactivated2);
    event.setRoomUpdated(roomUpdated2);
    event.setTimerFired(timerFired2);
    event.setUserJoinedRoom(userJoinedRoom2);
    event.setUserLeftRoom(userLeftRoom2);
    event.setUserRequestedJoinRoom(userRequestedJoinRoom2);
    event.setTimeout(null);

    // Act and Assert
    assertThrows(InvalidActivityException.class, () -> WorkflowValidator.validateFirstActivity(activity, event, "42"));
  }

  /**
   * Test {@link WorkflowValidator#validateFirstActivity(BaseActivity, Event, String)}.
   * <p>
   * Method under test: {@link WorkflowValidator#validateFirstActivity(BaseActivity, Event, String)}
   */
  @Test
  @DisplayName("Test validateFirstActivity(BaseActivity, Event, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowValidator.validateFirstActivity(BaseActivity, Event, String)"})
  void testValidateFirstActivity2() {
    // Arrange
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

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    EventWithTimeout on = new EventWithTimeout();
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
    on.setActivityCompleted(null);
    on.setActivityFailed(activityFailed);
    on.setActivityExpired(null);

    DoSomething activity = new DoSomething();
    activity.setOn(on);

    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

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

    EventWithTimeout event = new EventWithTimeout();
    event.setActivityCompleted(activityCompleted);
    event.setActivityExpired(activityExpired);
    event.setActivityFailed(activityFailed2);
    event.setAllOf(new ArrayList<>());
    event.setConnectionAccepted(connectionAccepted2);
    event.setConnectionRequested(connectionRequested2);
    event.setFormReplied(formReplied2);
    event.setImCreated(imCreated2);
    event.setMessageReceived(messageReceived2);
    event.setMessageSuppressed(messageSuppressed2);
    event.setOneOf(new ArrayList<>());
    event.setPostShared(postShared2);
    event.setRequestReceived(requestReceived2);
    event.setRoomCreated(roomCreated2);
    event.setRoomDeactivated(roomDeactivated2);
    event.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner2);
    event.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner2);
    event.setRoomReactivated(roomReactivated2);
    event.setRoomUpdated(roomUpdated2);
    event.setTimerFired(timerFired2);
    event.setUserJoinedRoom(userJoinedRoom2);
    event.setUserLeftRoom(userLeftRoom2);
    event.setUserRequestedJoinRoom(userRequestedJoinRoom2);
    event.setTimeout(null);

    // Act and Assert
    assertThrows(InvalidActivityException.class, () -> WorkflowValidator.validateFirstActivity(activity, event, "42"));
  }

  /**
   * Test {@link WorkflowValidator#validateFirstActivity(BaseActivity, Event, String)}.
   * <ul>
   *   <li>Given {@link EventWithTimeout} (default constructor) ActivityFailed is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowValidator#validateFirstActivity(BaseActivity, Event, String)}
   */
  @Test
  @DisplayName("Test validateFirstActivity(BaseActivity, Event, String); given EventWithTimeout (default constructor) ActivityFailed is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowValidator.validateFirstActivity(BaseActivity, Event, String)"})
  void testValidateFirstActivity_givenEventWithTimeoutActivityFailedIsNull() {
    // Arrange
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

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    EventWithTimeout on = new EventWithTimeout();
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
    on.setActivityCompleted(null);
    on.setActivityFailed(null);
    on.setActivityExpired(activityExpired);

    DoSomething activity = new DoSomething();
    activity.setOn(on);

    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired2 = new ActivityExpiredEvent();
    activityExpired2.setActivityId("42");
    activityExpired2.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

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

    EventWithTimeout event = new EventWithTimeout();
    event.setActivityCompleted(activityCompleted);
    event.setActivityExpired(activityExpired2);
    event.setActivityFailed(activityFailed);
    event.setAllOf(new ArrayList<>());
    event.setConnectionAccepted(connectionAccepted2);
    event.setConnectionRequested(connectionRequested2);
    event.setFormReplied(formReplied2);
    event.setImCreated(imCreated2);
    event.setMessageReceived(messageReceived2);
    event.setMessageSuppressed(messageSuppressed2);
    event.setOneOf(new ArrayList<>());
    event.setPostShared(postShared2);
    event.setRequestReceived(requestReceived2);
    event.setRoomCreated(roomCreated2);
    event.setRoomDeactivated(roomDeactivated2);
    event.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner2);
    event.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner2);
    event.setRoomReactivated(roomReactivated2);
    event.setRoomUpdated(roomUpdated2);
    event.setTimerFired(timerFired2);
    event.setUserJoinedRoom(userJoinedRoom2);
    event.setUserLeftRoom(userLeftRoom2);
    event.setUserRequestedJoinRoom(userRequestedJoinRoom2);
    event.setTimeout(null);

    // Act and Assert
    assertThrows(InvalidActivityException.class, () -> WorkflowValidator.validateFirstActivity(activity, event, "42"));
  }

  /**
   * Test {@link WorkflowValidator#validateFirstActivity(BaseActivity, Event, String)}.
   * <ul>
   *   <li>Given {@code Event}.</li>
   *   <li>When {@link EventWithTimeout} (default constructor) Timeout is {@code Event}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowValidator#validateFirstActivity(BaseActivity, Event, String)}
   */
  @Test
  @DisplayName("Test validateFirstActivity(BaseActivity, Event, String); given 'Event'; when EventWithTimeout (default constructor) Timeout is 'Event'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowValidator.validateFirstActivity(BaseActivity, Event, String)"})
  void testValidateFirstActivity_givenEvent_whenEventWithTimeoutTimeoutIsEvent() {
    // Arrange
    DoSomething activity = new DoSomething();
    activity.setOn(null);

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

    EventWithTimeout event = new EventWithTimeout();
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
    event.setTimeout("Event");

    // Act and Assert
    assertThrows(InvalidActivityException.class, () -> WorkflowValidator.validateFirstActivity(activity, event, "42"));
  }

  /**
   * Test {@link WorkflowValidator#validateActivityCompletedNodeId(String, String, Workflow)}.
   * <ul>
   *   <li>Given {@link Activity} {@link Activity#getActivity()} return {@link Debug} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowValidator#validateActivityCompletedNodeId(String, String, Workflow)}
   */
  @Test
  @DisplayName("Test validateActivityCompletedNodeId(String, String, Workflow); given Activity getActivity() return Debug (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowValidator.validateActivityCompletedNodeId(String, String, Workflow)"})
  void testValidateActivityCompletedNodeId_givenActivityGetActivityReturnDebug() {
    // Arrange
    Activity activity = mock(Activity.class);
    when(activity.getActivity()).thenReturn(new Debug());
    doNothing().when(activity).setImplementation(Mockito.<BaseActivity>any());
    activity.setImplementation(new DoSomething());

    ArrayList<Activity> activities = new ArrayList<>();
    activities.add(activity);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(activities);
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    // Act and Assert
    assertThrows(NotFoundException.class,
        () -> WorkflowValidator.validateActivityCompletedNodeId("42", "42", workflow));
    verify(activity).getActivity();
    verify(activity).setImplementation(isA(BaseActivity.class));
  }

  /**
   * Test {@link WorkflowValidator#validateActivityCompletedNodeId(String, String, Workflow)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowValidator#validateActivityCompletedNodeId(String, String, Workflow)}
   */
  @Test
  @DisplayName("Test validateActivityCompletedNodeId(String, String, Workflow); given ArrayList(); then throw NotFoundException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowValidator.validateActivityCompletedNodeId(String, String, Workflow)"})
  void testValidateActivityCompletedNodeId_givenArrayList_thenThrowNotFoundException() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    // Act and Assert
    assertThrows(NotFoundException.class,
        () -> WorkflowValidator.validateActivityCompletedNodeId("42", "42", workflow));
  }

  /**
   * Test {@link WorkflowValidator#validateActivityCompletedNodeId(String, String, Workflow)}.
   * <ul>
   *   <li>Given {@link DoSomething} (default constructor) Id is {@code 42}.</li>
   *   <li>Then calls {@link Activity#getActivity()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowValidator#validateActivityCompletedNodeId(String, String, Workflow)}
   */
  @Test
  @DisplayName("Test validateActivityCompletedNodeId(String, String, Workflow); given DoSomething (default constructor) Id is '42'; then calls getActivity()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowValidator.validateActivityCompletedNodeId(String, String, Workflow)"})
  void testValidateActivityCompletedNodeId_givenDoSomethingIdIs42_thenCallsGetActivity() {
    // Arrange
    DoSomething doSomething = new DoSomething();
    doSomething.setId("42");
    Activity activity = mock(Activity.class);
    when(activity.getActivity()).thenReturn(doSomething);
    doNothing().when(activity).setImplementation(Mockito.<BaseActivity>any());
    activity.setImplementation(new DoSomething());

    ArrayList<Activity> activities = new ArrayList<>();
    activities.add(activity);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(activities);
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    // Act
    WorkflowValidator.validateActivityCompletedNodeId("42", "42", workflow);

    // Assert
    verify(activity).getActivity();
    verify(activity).setImplementation(isA(BaseActivity.class));
  }

  /**
   * Test {@link WorkflowValidator#validateExistingNodeId(String, String, String, WorkflowDirectedGraph)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>Then calls {@link WorkflowDirectedGraph#hasSeenBefore(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowValidator#validateExistingNodeId(String, String, String, WorkflowDirectedGraph)}
   */
  @Test
  @DisplayName("Test validateExistingNodeId(String, String, String, WorkflowDirectedGraph); given 'true'; then calls hasSeenBefore(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowValidator.validateExistingNodeId(String, String, String, WorkflowDirectedGraph)"})
  void testValidateExistingNodeId_givenTrue_thenCallsHasSeenBefore() {
    // Arrange
    WorkflowDirectedGraph graph = mock(WorkflowDirectedGraph.class);
    when(graph.hasSeenBefore(Mockito.<String>any())).thenReturn(true);

    // Act
    WorkflowValidator.validateExistingNodeId("42", "42", "42", graph);

    // Assert
    verify(graph).hasSeenBefore(eq("42"));
  }

  /**
   * Test {@link WorkflowValidator#validateExistingNodeId(String, String, String, WorkflowDirectedGraph)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowValidator#validateExistingNodeId(String, String, String, WorkflowDirectedGraph)}
   */
  @Test
  @DisplayName("Test validateExistingNodeId(String, String, String, WorkflowDirectedGraph); then throw NotFoundException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowValidator.validateExistingNodeId(String, String, String, WorkflowDirectedGraph)"})
  void testValidateExistingNodeId_thenThrowNotFoundException() {
    // Arrange, Act and Assert
    assertThrows(NotFoundException.class,
        () -> WorkflowValidator.validateExistingNodeId("42", "42", "42", new WorkflowDirectedGraph("42")));
  }
}
