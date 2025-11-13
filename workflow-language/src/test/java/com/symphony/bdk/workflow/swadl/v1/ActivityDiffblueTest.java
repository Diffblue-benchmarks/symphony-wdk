package com.symphony.bdk.workflow.swadl.v1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
import com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents;
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

class ActivityDiffblueTest {
  /**
   * Test {@link Activity#getActivity()}.
   *
   * <p>Method under test: {@link Activity#getActivity()}
   */
  @Test
  @DisplayName("Test getActivity()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity Activity.getActivity()"
  })
  void testGetActivity() {
    // Arrange, Act and Assert
    assertNull(new Activity().getActivity());
  }

  /**
   * Test {@link Activity#getEvent()}.
   *
   * <ul>
   *   <li>Given {@link Activity} (default constructor) Implementation is {@link Debug} (default
   *       constructor).
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link Activity#getEvent()}
   */
  @Test
  @DisplayName(
      "Test getEvent(); given Activity (default constructor) Implementation is Debug (default constructor); then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Optional Activity.getEvent()"})
  void testGetEvent_givenActivityImplementationIsDebug_thenReturnNotPresent() {
    // Arrange
    Activity activity = new Activity();
    activity.setImplementation(new Debug());

    // Act and Assert
    assertFalse(activity.getEvent().isPresent());
  }

  /**
   * Test {@link Activity#getEvents()}.
   *
   * <ul>
   *   <li>Given {@link ActivityCompletedEvent} (default constructor) ActivityId is {@code 42}.
   *   <li>Then return ParentId is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Activity#getEvents()}
   */
  @Test
  @DisplayName(
      "Test getEvents(); given ActivityCompletedEvent (default constructor) ActivityId is '42'; then return ParentId is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"RelationalEvents Activity.getEvents()"})
  void testGetEvents_givenActivityCompletedEventActivityIdIs42_thenReturnParentIdIsNull() {
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

    Debug implementation = new Debug();
    implementation.setOn(on);

    Activity activity = new Activity();
    activity.setImplementation(implementation);

    // Act
    RelationalEvents actualEvents = activity.getEvents();

    // Assert
    assertNull(actualEvents.getParentId());
    assertFalse(actualEvents.isParallel());
    assertTrue(actualEvents.isEmpty());
    assertTrue(actualEvents.getEvents().isEmpty());
  }
}
