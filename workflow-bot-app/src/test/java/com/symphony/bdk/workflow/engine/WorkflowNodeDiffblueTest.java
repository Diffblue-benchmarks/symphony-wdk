package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.DoSomething;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
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
import java.util.Map;
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
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class WorkflowNodeDiffblueTest {
  @Autowired
  private WorkflowNode workflowNode;

  /**
   * Test {@link WorkflowNode#addIfCondition(String, String)}.
   * <p>
   * Method under test: {@link WorkflowNode#addIfCondition(String, String)}
   */
  @Test
  @DisplayName("Test addIfCondition(String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WorkflowNode WorkflowNode.addIfCondition(String, String)"})
  void testAddIfCondition() {
    // Arrange and Act
    WorkflowNode actualAddIfConditionResult = workflowNode.addIfCondition("42", "If Condition");

    // Assert
    assertTrue(workflowNode.isConditional());
    assertSame(workflowNode, actualAddIfConditionResult);
  }

  /**
   * Test {@link WorkflowNode#isConditional()}.
   * <p>
   * Method under test: {@link WorkflowNode#isConditional()}
   */
  @Test
  @DisplayName("Test isConditional()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNode.isConditional()"})
  void testIsConditional() {
    // Arrange, Act and Assert
    assertFalse(workflowNode.isConditional());
  }

  /**
   * Test {@link WorkflowNode#isConditional(String)} with {@code String}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNode#isConditional(String)}
   */
  @Test
  @DisplayName("Test isConditional(String) with 'String'; given HashMap() '42' is '42'; then return 'true'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@code foo}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNode#isConditional(String)}
   */
  @Test
  @DisplayName("Test isConditional(String) with 'String'; given HashMap() 'foo' is 'foo'; then return 'false'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link WorkflowNode}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNode#isConditional(String)}
   */
  @Test
  @DisplayName("Test isConditional(String) with 'String'; given WorkflowNode; then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNode.isConditional(String)"})
  void testIsConditionalWithString_givenWorkflowNode_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(workflowNode.isConditional("42"));
  }

  /**
   * Test {@link WorkflowNode#getIfCondition(String)}.
   * <p>
   * Method under test: {@link WorkflowNode#getIfCondition(String)}
   */
  @Test
  @DisplayName("Test getIfCondition(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String WorkflowNode.getIfCondition(String)"})
  void testGetIfCondition() {
    // Arrange, Act and Assert
    assertNull(workflowNode.getIfCondition("42"));
  }

  /**
   * Test {@link WorkflowNode#isNotExclusiveFormReply()}.
   * <ul>
   *   <li>Given {@link WorkflowNode}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNode#isNotExclusiveFormReply()}
   */
  @Test
  @DisplayName("Test isNotExclusiveFormReply(); given WorkflowNode; then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNode.isNotExclusiveFormReply()"})
  void testIsNotExclusiveFormReply_givenWorkflowNode_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(workflowNode.isNotExclusiveFormReply());
  }

  /**
   * Test {@link WorkflowNode#equals(Object)}, and {@link WorkflowNode#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowNode#equals(Object)}
   *   <li>{@link WorkflowNode#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNode.equals(Object)", "int WorkflowNode.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    WorkflowNode workflowNode2 = new WorkflowNode();

    // Act and Assert
    assertEquals(workflowNode, workflowNode2);
    int expectedHashCodeResult = workflowNode.hashCode();
    assertEquals(expectedHashCodeResult, workflowNode2.hashCode());
  }

  /**
   * Test {@link WorkflowNode#equals(Object)}, and {@link WorkflowNode#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowNode#equals(Object)}
   *   <li>{@link WorkflowNode#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNode.equals(Object)", "int WorkflowNode.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();

    // Act and Assert
    assertEquals(workflowNode, workflowNode);
    int expectedHashCodeResult = workflowNode.hashCode();
    assertEquals(expectedHashCodeResult, workflowNode.hashCode());
  }

  /**
   * Test {@link WorkflowNode#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNode#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNode.equals(Object)", "int WorkflowNode.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.addIfCondition("42", "If Condition");

    // Act and Assert
    assertNotEquals(workflowNode, new WorkflowNode());
  }

  /**
   * Test {@link WorkflowNode#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNode#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNode.equals(Object)", "int WorkflowNode.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.id("42");
    workflowNode.addIfCondition("42", "If Condition");

    // Act and Assert
    assertNotEquals(workflowNode, new WorkflowNode());
  }

  /**
   * Test {@link WorkflowNode#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNode#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNode.equals(Object)", "int WorkflowNode.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.eventId("42");
    workflowNode.addIfCondition("42", "If Condition");

    // Act and Assert
    assertNotEquals(workflowNode, new WorkflowNode());
  }

  /**
   * Test {@link WorkflowNode#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNode#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNode.equals(Object)", "int WorkflowNode.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    Class<Object> clz = Object.class;
    workflowNode.wrappedType(clz);
    workflowNode.addIfCondition("42", "If Condition");

    // Act and Assert
    assertNotEquals(workflowNode, new WorkflowNode());
  }

  /**
   * Test {@link WorkflowNode#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNode#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNode.equals(Object)", "int WorkflowNode.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.activity(new DoSomething());
    workflowNode.addIfCondition("42", "If Condition");

    // Act and Assert
    assertNotEquals(workflowNode, new WorkflowNode());
  }

  /**
   * Test {@link WorkflowNode#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNode#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNode.equals(Object)", "int WorkflowNode.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
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

    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.event(event);
    workflowNode.addIfCondition("42", "If Condition");

    // Act and Assert
    assertNotEquals(workflowNode, new WorkflowNode());
  }

  /**
   * Test {@link WorkflowNode#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNode#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNode.equals(Object)", "int WorkflowNode.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.elementType(WorkflowNodeType.TIMER_FIRED_EVENT);
    workflowNode.addIfCondition("42", "If Condition");

    // Act and Assert
    assertNotEquals(workflowNode, new WorkflowNode());
  }

  /**
   * Test {@link WorkflowNode#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNode#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNode.equals(Object)", "int WorkflowNode.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new WorkflowNode(), null);
  }

  /**
   * Test {@link WorkflowNode#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNode#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNode.equals(Object)", "int WorkflowNode.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new WorkflowNode(), "Different type to WorkflowNode");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link WorkflowNode}
   *   <li>{@link WorkflowNode#activity(BaseActivity)}
   *   <li>{@link WorkflowNode#elementType(WorkflowNodeType)}
   *   <li>{@link WorkflowNode#event(Event)}
   *   <li>{@link WorkflowNode#eventId(String)}
   *   <li>{@link WorkflowNode#id(String)}
   *   <li>{@link WorkflowNode#setActivity(BaseActivity)}
   *   <li>{@link WorkflowNode#setElementType(WorkflowNodeType)}
   *   <li>{@link WorkflowNode#setEvent(Event)}
   *   <li>{@link WorkflowNode#setEventId(String)}
   *   <li>{@link WorkflowNode#setId(String)}
   *   <li>{@link WorkflowNode#setIfConditions(Map)}
   *   <li>{@link WorkflowNode#setWrappedType(Class)}
   *   <li>{@link WorkflowNode#wrappedType(Class)}
   *   <li>{@link WorkflowNode#toString()}
   *   <li>{@link WorkflowNode#getActivity()}
   *   <li>{@link WorkflowNode#getElementType()}
   *   <li>{@link WorkflowNode#getEvent()}
   *   <li>{@link WorkflowNode#getEventId()}
   *   <li>{@link WorkflowNode#getId()}
   *   <li>{@link WorkflowNode#getIfConditions()}
   *   <li>{@link WorkflowNode#getWrappedType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowNode.<init>()", "WorkflowNode WorkflowNode.activity(BaseActivity)",
      "WorkflowNode WorkflowNode.elementType(WorkflowNodeType)", "WorkflowNode WorkflowNode.event(Event)",
      "WorkflowNode WorkflowNode.eventId(String)", "BaseActivity WorkflowNode.getActivity()",
      "WorkflowNodeType WorkflowNode.getElementType()", "Event WorkflowNode.getEvent()",
      "String WorkflowNode.getEventId()", "String WorkflowNode.getId()", "Map WorkflowNode.getIfConditions()",
      "Class WorkflowNode.getWrappedType()", "WorkflowNode WorkflowNode.id(String)",
      "void WorkflowNode.setActivity(BaseActivity)", "void WorkflowNode.setElementType(WorkflowNodeType)",
      "void WorkflowNode.setEvent(Event)", "void WorkflowNode.setEventId(String)", "void WorkflowNode.setId(String)",
      "void WorkflowNode.setIfConditions(Map)", "void WorkflowNode.setWrappedType(Class)",
      "String WorkflowNode.toString()", "WorkflowNode WorkflowNode.wrappedType(Class)"})
  void testGettersAndSetters() {
    // Arrange and Act
    WorkflowNode actualWorkflowNode = new WorkflowNode();
    WorkflowNode actualActivityResult = actualWorkflowNode.activity(new DoSomething());
    WorkflowNode actualElementTypeResult = actualWorkflowNode.elementType(WorkflowNodeType.TIMER_FIRED_EVENT);
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
    WorkflowNode actualEventResult = actualWorkflowNode.event(event);
    WorkflowNode actualEventIdResult = actualWorkflowNode.eventId("42");
    WorkflowNode actualIdResult = actualWorkflowNode.id("42");
    DoSomething activity = new DoSomething();
    actualWorkflowNode.setActivity(activity);
    actualWorkflowNode.setElementType(WorkflowNodeType.TIMER_FIRED_EVENT);
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
    actualWorkflowNode.setEvent(event2);
    actualWorkflowNode.setEventId("42");
    actualWorkflowNode.setId("42");
    HashMap<String, String> ifConditions = new HashMap<>();
    actualWorkflowNode.setIfConditions(ifConditions);
    Class<Object> wrappedType = Object.class;
    actualWorkflowNode.setWrappedType(wrappedType);
    Class<Object> clz = Object.class;
    WorkflowNode actualWrappedTypeResult = actualWorkflowNode.wrappedType(clz);
    String actualToStringResult = actualWorkflowNode.toString();
    BaseActivity actualActivity = actualWorkflowNode.getActivity();
    WorkflowNodeType actualElementType = actualWorkflowNode.getElementType();
    Event actualEvent = actualWorkflowNode.getEvent();
    String actualEventId = actualWorkflowNode.getEventId();
    String actualId = actualWorkflowNode.getId();
    Map<String, String> actualIfConditions = actualWorkflowNode.getIfConditions();
    Class<?> actualWrappedType = actualWorkflowNode.getWrappedType();

    // Assert
    assertEquals("42", actualEventId);
    assertEquals("42", actualId);
    assertEquals("WorkflowNode(id=42, wrappedType=class java.lang.Object, event=Event(oneOf=[], allOf=[], formReplied"
        + "=FormRepliedEvent(formId=42, exclusive=true), activityExpired=ActivityExpiredEvent(), activityCompleted"
        + "=ActivityCompletedEvent(ifCondition=If Condition), activityFailed=ActivityFailedEvent(), messageReceived"
        + "=MessageReceivedEvent(content=Not all who wander are lost, requiresBotMention=true), messageSuppressed"
        + "=MessageSuppressedEvent(), postShared=PostSharedEvent(), imCreated=ImCreatedEvent(), roomCreated"
        + "=RoomCreatedEvent(), roomUpdated=RoomUpdatedEvent(), roomDeactivated=RoomDeactivatedEvent(),"
        + " roomReactivated=RoomReactivatedEvent(), roomMemberPromotedToOwner=RoomMemberPromotedToOwnerEvent(),"
        + " roomMemberDemotedFromOwner=RoomMemberDemotedFromOwnerEvent(), userJoinedRoom=UserJoinedRoomEvent(),"
        + " userLeftRoom=UserLeftRoomEvent(), userRequestedJoinRoom=UserRequestedToJoinRoomEvent(), connectionRequested"
        + "=ConnectionRequestedEvent(), connectionAccepted=ConnectionAcceptedEvent(), timerFired=TimerFiredEvent(at=At,"
        + " repeat=Repeat), requestReceived=RequestReceivedEvent(token=ABC123, arguments={}, workflowId=42)),"
        + " eventId=42, activity=DoSomething(myParameter=null), elementType=TIMER_FIRED_EVENT, ifConditions={})",
        actualToStringResult);
    assertEquals(WorkflowNodeType.TIMER_FIRED_EVENT, actualElementType);
    assertTrue(actualIfConditions.isEmpty());
    Class<Object> expectedWrappedType = Object.class;
    assertEquals(expectedWrappedType, actualWrappedType);
    assertSame(activity, actualActivity);
    assertSame(actualWorkflowNode, actualActivityResult);
    assertSame(actualWorkflowNode, actualElementTypeResult);
    assertSame(actualWorkflowNode, actualEventResult);
    assertSame(actualWorkflowNode, actualEventIdResult);
    assertSame(actualWorkflowNode, actualIdResult);
    assertSame(actualWorkflowNode, actualWrappedTypeResult);
    assertSame(event2, actualEvent);
    assertSame(ifConditions, actualIfConditions);
    assertSame(clz, actualWrappedType);
  }
}
