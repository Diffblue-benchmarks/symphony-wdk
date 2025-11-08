package com.symphony.bdk.workflow.swadl.v1.activity.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
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

class ConnectionDiffblueTest {
  /**
   * Test {@link Connection#equals(Object)}, and {@link Connection#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Connection#equals(Object)}
   *   <li>{@link Connection#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Connection.equals(Object)", "int Connection.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Connection connection = new Connection();
    Connection connection2 = new Connection();

    // Act and Assert
    assertEquals(connection, connection2);
    int expectedHashCodeResult = connection.hashCode();
    assertEquals(expectedHashCodeResult, connection2.hashCode());
  }

  /**
   * Test {@link Connection#equals(Object)}, and {@link Connection#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Connection#equals(Object)}
   *   <li>{@link Connection#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Connection.equals(Object)", "int Connection.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Connection connection = new Connection();

    // Act and Assert
    assertEquals(connection, connection);
    int expectedHashCodeResult = connection.hashCode();
    assertEquals(expectedHashCodeResult, connection.hashCode());
  }

  /**
   * Test {@link Connection#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Connection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Connection.equals(Object)", "int Connection.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AcceptConnection acceptConnection = new AcceptConnection();

    // Act and Assert
    assertNotEquals(acceptConnection, new Connection());
  }

  /**
   * Test {@link Connection#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Connection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Connection.equals(Object)", "int Connection.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Connection connection = new Connection();
    connection.add("Key", "Value");

    // Act and Assert
    assertNotEquals(connection, new Connection());
  }

  /**
   * Test {@link Connection#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Connection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Connection.equals(Object)", "int Connection.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Connection connection = new Connection();

    // Act and Assert
    assertNotEquals(connection, new AcceptConnection());
  }

  /**
   * Test {@link Connection#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Connection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Connection.equals(Object)", "int Connection.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Connection connection = new Connection();

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

    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");
    AcceptConnection acceptConnection = mock(AcceptConnection.class);
    when(acceptConnection.getOn()).thenReturn(eventWithTimeout);
    when(acceptConnection.getObo()).thenReturn(obo);
    when(acceptConnection.getElseCondition()).thenReturn("Else Condition");
    when(acceptConnection.getId()).thenReturn("42");
    when(acceptConnection.getIfCondition()).thenReturn("If Condition");
    when(acceptConnection.getUserId()).thenReturn("42");
    when(acceptConnection.getVariableProperties()).thenReturn(new HashMap<>());
    when(acceptConnection.canEqual(Mockito.<Object>any())).thenReturn(true);

    // Act and Assert
    assertNotEquals(connection, acceptConnection);
  }

  /**
   * Test {@link Connection#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Connection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Connection.equals(Object)", "int Connection.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Connection(), null);
  }

  /**
   * Test {@link Connection#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Connection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Connection.equals(Object)", "int Connection.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Connection(), "Different type to Connection");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Connection}
   *   <li>{@link Connection#setObo(Obo)}
   *   <li>{@link Connection#setUserId(String)}
   *   <li>{@link Connection#toString()}
   *   <li>{@link Connection#getObo()}
   *   <li>{@link Connection#getUserId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Connection.<init>()", "Obo Connection.getObo()", "String Connection.getUserId()",
      "void Connection.setObo(Obo)", "void Connection.setUserId(String)", "String Connection.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    Connection actualConnection = new Connection();
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");
    actualConnection.setObo(obo);
    actualConnection.setUserId("42");
    String actualToStringResult = actualConnection.toString();
    Obo actualObo = actualConnection.getObo();

    // Assert
    assertEquals("42", actualConnection.getUserId());
    assertEquals("Connection(userId=42, obo=Obo(username=janedoe, userId=1))", actualToStringResult);
    assertNull(actualConnection.getOn());
    assertNull(actualConnection.getElseCondition());
    assertNull(actualConnection.getId());
    assertNull(actualConnection.getIfCondition());
    assertTrue(actualConnection.getVariableProperties().isEmpty());
    assertSame(obo, actualObo);
  }
}
