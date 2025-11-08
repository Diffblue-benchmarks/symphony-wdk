package com.symphony.bdk.workflow.swadl.v1.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
import org.mockito.Mockito;

class BaseActivityDiffblueTest {
  /**
   * Test {@link BaseActivity#add(String, Object)}.
   * <p>
   * Method under test: {@link BaseActivity#add(String, Object)}
   */
  @Test
  @DisplayName("Test add(String, Object)")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link Debug} (default constructor).</li>
   *   <li>Then return not Parallel.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseActivity#getEvents()}
   */
  @Test
  @DisplayName("Test getEvents(); given Debug (default constructor); then return not Parallel")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"RelationalEvents BaseActivity.getEvents()"})
  void testGetEvents_givenDebug_thenReturnNotParallel() {
    // Arrange and Act
    RelationalEvents actualEvents = (new Debug()).getEvents();

    // Assert
    assertNull(actualEvents.getParentId());
    assertFalse(actualEvents.isParallel());
    assertTrue(actualEvents.isEmpty());
    assertTrue(actualEvents.getEvents().isEmpty());
  }

  /**
   * Test {@link BaseActivity#getEvents()}.
   * <ul>
   *   <li>Given {@link EventWithTimeout} (default constructor) AllOf is {@code null}.</li>
   *   <li>Then return Events size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseActivity#getEvents()}
   */
  @Test
  @DisplayName("Test getEvents(); given EventWithTimeout (default constructor) AllOf is 'null'; then return Events size is one")
  @Tag("MaintainedByDiffblue")
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
    List<Event> events = actualEvents.getEvents();
    assertEquals(1, events.size());
    Event getResult = events.get(0);
    assertTrue(getResult instanceof EventWithTimeout);
    assertFalse(actualEvents.isEmpty());
    assertSame(on, getResult);
  }

  /**
   * Test {@link BaseActivity#getEvents()}.
   * <ul>
   *   <li>Given {@link EventWithTimeout} (default constructor) OneOf is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return not Parallel.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseActivity#getEvents()}
   */
  @Test
  @DisplayName("Test getEvents(); given EventWithTimeout (default constructor) OneOf is ArrayList(); then return not Parallel")
  @Tag("MaintainedByDiffblue")
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
    RelationalEvents actualEvents = debug.getEvents();

    // Assert
    assertNull(actualEvents.getParentId());
    assertFalse(actualEvents.isParallel());
    assertTrue(actualEvents.isEmpty());
    assertTrue(actualEvents.getEvents().isEmpty());
  }

  /**
   * Test {@link BaseActivity#getEvents()}.
   * <ul>
   *   <li>Given {@link EventWithTimeout} (default constructor) OneOf is {@code null}.</li>
   *   <li>Then return Parallel.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseActivity#getEvents()}
   */
  @Test
  @DisplayName("Test getEvents(); given EventWithTimeout (default constructor) OneOf is 'null'; then return Parallel")
  @Tag("MaintainedByDiffblue")
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

  /**
   * Test {@link BaseActivity#canEqual(Object)}.
   * <ul>
   *   <li>When {@link Debug} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseActivity#canEqual(Object)}
   */
  @Test
  @DisplayName("Test canEqual(Object); when Debug (default constructor); then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BaseActivity.canEqual(Object)"})
  void testCanEqual_whenDebug_thenReturnTrue() {
    // Arrange
    Debug debug = new Debug();

    // Act and Assert
    assertTrue(debug.canEqual(new Debug()));
  }

  /**
   * Test {@link BaseActivity#canEqual(Object)}.
   * <ul>
   *   <li>When {@code Other}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseActivity#canEqual(Object)}
   */
  @Test
  @DisplayName("Test canEqual(Object); when 'Other'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BaseActivity.canEqual(Object)"})
  void testCanEqual_whenOther_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new Debug()).canEqual("Other"));
  }

  /**
   * Test {@link BaseActivity#equals(Object)}, and {@link BaseActivity#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseActivity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BaseActivity.equals(Object)", "int BaseActivity.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Debug debug = new Debug();

    // Act and Assert
    assertEquals(debug, debug);
    int expectedHashCodeResult = debug.hashCode();
    assertEquals(expectedHashCodeResult, debug.hashCode());
  }

  /**
   * Test {@link BaseActivity#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseActivity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BaseActivity.equals(Object)", "int BaseActivity.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Debug debug = new Debug();

    // Act and Assert
    assertNotEquals(debug, new Debug());
  }

  /**
   * Test {@link BaseActivity#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseActivity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BaseActivity.equals(Object)", "int BaseActivity.hashCode()"})
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
   * Test {@link BaseActivity#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseActivity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BaseActivity.equals(Object)", "int BaseActivity.hashCode()"})
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
   * Test {@link BaseActivity#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseActivity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BaseActivity.equals(Object)", "int BaseActivity.hashCode()"})
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
   * Test {@link BaseActivity#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseActivity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BaseActivity.equals(Object)", "int BaseActivity.hashCode()"})
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
   * Test {@link BaseActivity#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseActivity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BaseActivity.equals(Object)", "int BaseActivity.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Debug(), null);
  }

  /**
   * Test {@link BaseActivity#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseActivity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BaseActivity.equals(Object)", "int BaseActivity.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Debug(), "Different type to BaseActivity");
  }

  /**
   * Test {@link BaseActivity#getElseCondition()}.
   * <p>
   * Method under test: {@link BaseActivity#getElseCondition()}
   */
  @Test
  @DisplayName("Test getElseCondition()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object BaseActivity.getElseCondition()"})
  void testGetElseCondition() {
    // Arrange, Act and Assert
    assertNull((new Debug()).getElseCondition());
  }

  /**
   * Test {@link BaseActivity#getIfCondition()}.
   * <p>
   * Method under test: {@link BaseActivity#getIfCondition()}
   */
  @Test
  @DisplayName("Test getIfCondition()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BaseActivity.getIfCondition()"})
  void testGetIfCondition() {
    // Arrange, Act and Assert
    assertNull((new Debug()).getIfCondition());
  }

  /**
   * Test {@link BaseActivity#getOn()}.
   * <p>
   * Method under test: {@link BaseActivity#getOn()}
   */
  @Test
  @DisplayName("Test getOn()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"EventWithTimeout BaseActivity.getOn()"})
  void testGetOn() {
    // Arrange, Act and Assert
    assertNull((new Debug()).getOn());
  }

  /**
   * Test {@link BaseActivity#getVariableProperties()}.
   * <p>
   * Method under test: {@link BaseActivity#getVariableProperties()}
   */
  @Test
  @DisplayName("Test getVariableProperties()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map BaseActivity.getVariableProperties()"})
  void testGetVariableProperties() {
    // Arrange, Act and Assert
    assertTrue((new Debug()).getVariableProperties().isEmpty());
  }

  /**
   * Test {@link BaseActivity#setElseCondition(Object)}.
   * <p>
   * Method under test: {@link BaseActivity#setElseCondition(Object)}
   */
  @Test
  @DisplayName("Test setElseCondition(Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseActivity.setElseCondition(Object)"})
  void testSetElseCondition() {
    // Arrange
    Debug debug = new Debug();

    // Act
    debug.setElseCondition("Else Condition");

    // Assert
    assertEquals("Else Condition", debug.getElseCondition());
  }

  /**
   * Test {@link BaseActivity#setId(String)}.
   * <p>
   * Method under test: {@link BaseActivity#setId(String)}
   */
  @Test
  @DisplayName("Test setId(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseActivity.setId(String)"})
  void testSetId() {
    // Arrange
    Debug debug = new Debug();

    // Act
    debug.setId("42");

    // Assert
    assertEquals("42", debug.getId());
  }

  /**
   * Test {@link BaseActivity#setIfCondition(String)}.
   * <p>
   * Method under test: {@link BaseActivity#setIfCondition(String)}
   */
  @Test
  @DisplayName("Test setIfCondition(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseActivity.setIfCondition(String)"})
  void testSetIfCondition() {
    // Arrange
    Debug debug = new Debug();

    // Act
    debug.setIfCondition("If Condition");

    // Assert
    assertEquals("If Condition", debug.getIfCondition());
  }

  /**
   * Test {@link BaseActivity#setOn(EventWithTimeout)}.
   * <p>
   * Method under test: {@link BaseActivity#setOn(EventWithTimeout)}
   */
  @Test
  @DisplayName("Test setOn(EventWithTimeout)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseActivity.setOn(EventWithTimeout)"})
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
   * Test {@link BaseActivity#setVariableProperties(Map)}.
   * <p>
   * Method under test: {@link BaseActivity#setVariableProperties(Map)}
   */
  @Test
  @DisplayName("Test setVariableProperties(Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BaseActivity.setVariableProperties(Map)"})
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
   * Test {@link BaseActivity#toString()}.
   * <p>
   * Method under test: {@link BaseActivity#toString()}
   */
  @Test
  @DisplayName("Test toString()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BaseActivity.toString()"})
  void testToString() {
    // Arrange, Act and Assert
    assertEquals("Debug(object=null)", (new Debug()).toString());
  }
}
