package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowNode.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
class WorkflowNodeDiffblueTest {
  @Autowired private WorkflowNode workflowNode;

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link WorkflowNode#activity(BaseActivity)}
   *   <li>{@link WorkflowNode#elementType(WorkflowNodeType)}
   *   <li>{@link WorkflowNode#event(Event)}
   *   <li>{@link WorkflowNode#eventId(String)}
   *   <li>{@link WorkflowNode#id(String)}
   *   <li>{@link WorkflowNode#wrappedType(Class)}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "WorkflowNode WorkflowNode.activity(BaseActivity)",
    "WorkflowNode WorkflowNode.elementType(WorkflowNodeType)",
    "WorkflowNode WorkflowNode.event(Event)",
    "WorkflowNode WorkflowNode.eventId(String)",
    "WorkflowNode WorkflowNode.id(String)",
    "WorkflowNode WorkflowNode.wrappedType(Class)"
  })
  void testGettersAndSetters() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();

    // Act
    WorkflowNode actualActivityResult = workflowNode.activity(new CreateGroup());
    WorkflowNode actualElementTypeResult =
        workflowNode.elementType(WorkflowNodeType.TIMER_FIRED_EVENT);
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
    WorkflowNode actualEventResult = workflowNode.event(event);
    WorkflowNode actualEventIdResult = workflowNode.eventId("42");
    WorkflowNode actualIdResult = workflowNode.id("42");
    Class<Object> clz = Object.class;
    WorkflowNode actualWrappedTypeResult = workflowNode.wrappedType(clz);

    // Assert
    assertSame(workflowNode, actualActivityResult);
    assertSame(workflowNode, actualElementTypeResult);
    assertSame(workflowNode, actualEventResult);
    assertSame(workflowNode, actualEventIdResult);
    assertSame(workflowNode, actualIdResult);
    assertSame(workflowNode, actualWrappedTypeResult);
  }

  /**
   * Test {@link WorkflowNode#addIfCondition(String, String)}.
   *
   * <p>Method under test: {@link WorkflowNode#addIfCondition(String, String)}
   */
  @Test
  @DisplayName("Test addIfCondition(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNode WorkflowNode.addIfCondition(String, String)"})
  void testAddIfCondition() {
    // Arrange and Act
    WorkflowNode actualAddIfConditionResult = workflowNode.addIfCondition("42", "If Condition");

    // Assert
    assertTrue(workflowNode.isConditional());
    assertSame(workflowNode, actualAddIfConditionResult);
  }

  /**
   * Test {@link WorkflowNode#isConditional(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNode#isConditional(String)}
   */
  @Test
  @DisplayName(
      "Test isConditional(String) with 'String'; given HashMap() '42' is '42'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean WorkflowNode.isConditional(String)"})
  void testIsConditionalWithString_givenHashMap42Is42_thenReturnTrue() {
    // Arrange
    HashMap<String, String> ifConditions = new HashMap<>();
    ifConditions.put("42", "42");
    ifConditions.put("foo", "foo");
    workflowNode.setIfConditions(ifConditions);

    // Act and Assert
    assertTrue(workflowNode.isConditional("42"));
  }

  /**
   * Test {@link WorkflowNode#isConditional(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@code foo}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNode#isConditional(String)}
   */
  @Test
  @DisplayName(
      "Test isConditional(String) with 'String'; given HashMap() 'foo' is 'foo'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean WorkflowNode.isConditional(String)"})
  void testIsConditionalWithString_givenHashMapFooIsFoo_thenReturnFalse() {
    // Arrange
    HashMap<String, String> ifConditions = new HashMap<>();
    ifConditions.put("foo", "foo");
    workflowNode.setIfConditions(ifConditions);

    // Act and Assert
    assertFalse(workflowNode.isConditional("42"));
  }

  /**
   * Test {@link WorkflowNode#isConditional(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link WorkflowNode}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNode#isConditional(String)}
   */
  @Test
  @DisplayName("Test isConditional(String) with 'String'; given WorkflowNode; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean WorkflowNode.isConditional(String)"})
  void testIsConditionalWithString_givenWorkflowNode_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(workflowNode.isConditional("42"));
  }

  /**
   * Test {@link WorkflowNode#isConditional()}.
   *
   * <ul>
   *   <li>Given {@link WorkflowNode} (default constructor) addIfCondition {@code 42} and {@code If
   *       Condition}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNode#isConditional()}
   */
  @Test
  @DisplayName(
      "Test isConditional(); given WorkflowNode (default constructor) addIfCondition '42' and 'If Condition'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean WorkflowNode.isConditional()"})
  void testIsConditional_givenWorkflowNodeAddIfCondition42AndIfCondition_thenReturnTrue() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.addIfCondition("42", "If Condition");

    // Act and Assert
    assertTrue(workflowNode.isConditional());
  }

  /**
   * Test {@link WorkflowNode#isConditional()}.
   *
   * <ul>
   *   <li>Given {@link WorkflowNode}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNode#isConditional()}
   */
  @Test
  @DisplayName("Test isConditional(); given WorkflowNode; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean WorkflowNode.isConditional()"})
  void testIsConditional_givenWorkflowNode_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(workflowNode.isConditional());
  }

  /**
   * Test {@link WorkflowNode#getIfCondition(String)}.
   *
   * <p>Method under test: {@link WorkflowNode#getIfCondition(String)}
   */
  @Test
  @DisplayName("Test getIfCondition(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String WorkflowNode.getIfCondition(String)"})
  void testGetIfCondition() {
    // Arrange, Act and Assert
    assertNull(workflowNode.getIfCondition("42"));
  }

  /**
   * Test {@link WorkflowNode#isNotExclusiveFormReply()}.
   *
   * <ul>
   *   <li>Given {@link FormRepliedEvent} (default constructor) Exclusive is {@code false}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNode#isNotExclusiveFormReply()}
   */
  @Test
  @DisplayName(
      "Test isNotExclusiveFormReply(); given FormRepliedEvent (default constructor) Exclusive is 'false'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean WorkflowNode.isNotExclusiveFormReply()"})
  void testIsNotExclusiveFormReply_givenFormRepliedEventExclusiveIsFalse_thenReturnTrue() {
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
    formReplied.setExclusive(false);
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

    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.event(event);
    workflowNode.elementType(WorkflowNodeType.FORM_REPLIED_EVENT);

    // Act and Assert
    assertTrue(workflowNode.isNotExclusiveFormReply());
  }

  /**
   * Test {@link WorkflowNode#isNotExclusiveFormReply()}.
   *
   * <ul>
   *   <li>Given {@link FormRepliedEvent} (default constructor) Exclusive is {@code true}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNode#isNotExclusiveFormReply()}
   */
  @Test
  @DisplayName(
      "Test isNotExclusiveFormReply(); given FormRepliedEvent (default constructor) Exclusive is 'true'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean WorkflowNode.isNotExclusiveFormReply()"})
  void testIsNotExclusiveFormReply_givenFormRepliedEventExclusiveIsTrue_thenReturnFalse() {
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

    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.event(event);
    workflowNode.elementType(WorkflowNodeType.FORM_REPLIED_EVENT);

    // Act and Assert
    assertFalse(workflowNode.isNotExclusiveFormReply());
  }

  /**
   * Test {@link WorkflowNode#isNotExclusiveFormReply()}.
   *
   * <ul>
   *   <li>Given {@link WorkflowNode} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNode#isNotExclusiveFormReply()}
   */
  @Test
  @DisplayName(
      "Test isNotExclusiveFormReply(); given WorkflowNode (default constructor); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean WorkflowNode.isNotExclusiveFormReply()"})
  void testIsNotExclusiveFormReply_givenWorkflowNode_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new WorkflowNode().isNotExclusiveFormReply());
  }
}
