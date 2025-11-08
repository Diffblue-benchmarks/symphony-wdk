package com.symphony.bdk.workflow.swadl.v1.activity.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UpdateUserDiffblueTest {
  /**
   * Test {@link UpdateUser#equals(Object)}, and {@link UpdateUser#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateUser#equals(Object)}
   *   <li>{@link UpdateUser#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateUser.equals(Object)", "int UpdateUser.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UpdateUser updateUser = new UpdateUser();
    UpdateUser updateUser2 = new UpdateUser();

    // Act and Assert
    assertEquals(updateUser, updateUser2);
    int expectedHashCodeResult = updateUser.hashCode();
    assertEquals(expectedHashCodeResult, updateUser2.hashCode());
  }

  /**
   * Test {@link UpdateUser#equals(Object)}, and {@link UpdateUser#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateUser#equals(Object)}
   *   <li>{@link UpdateUser#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateUser.equals(Object)", "int UpdateUser.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UpdateUser updateUser = new UpdateUser();

    // Act and Assert
    assertEquals(updateUser, updateUser);
    int expectedHashCodeResult = updateUser.hashCode();
    assertEquals(expectedHashCodeResult, updateUser.hashCode());
  }

  /**
   * Test {@link UpdateUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateUser.equals(Object)", "int UpdateUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UpdateSystemUser updateSystemUser = new UpdateSystemUser();

    // Act and Assert
    assertNotEquals(updateSystemUser, new UpdateUser());
  }

  /**
   * Test {@link UpdateUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateUser.equals(Object)", "int UpdateUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UpdateUser updateUser = new UpdateUser();
    updateUser.add("NORMAL", "Value");

    // Act and Assert
    assertNotEquals(updateUser, new UpdateUser());
  }

  /**
   * Test {@link UpdateUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateUser.equals(Object)", "int UpdateUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    UpdateUser updateUser = new UpdateUser();

    // Act and Assert
    assertNotEquals(updateUser, new UpdateSystemUser());
  }

  /**
   * Test {@link UpdateUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateUser.equals(Object)", "int UpdateUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    UpdateUser updateUser = new UpdateUser();

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
    UpdateSystemUser updateSystemUser = mock(UpdateSystemUser.class);
    when(updateSystemUser.getOn()).thenReturn(eventWithTimeout);
    when(updateSystemUser.getElseCondition()).thenReturn("Else Condition");
    when(updateSystemUser.getId()).thenReturn("42");
    when(updateSystemUser.getIfCondition()).thenReturn("If Condition");
    when(updateSystemUser.getType()).thenReturn("Type");
    when(updateSystemUser.getVariableProperties()).thenReturn(new HashMap<>());
    when(updateSystemUser.canEqual(Mockito.<Object>any())).thenReturn(true);

    // Act and Assert
    assertNotEquals(updateUser, updateSystemUser);
  }

  /**
   * Test {@link UpdateUser#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateUser.equals(Object)", "int UpdateUser.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UpdateUser(), null);
  }

  /**
   * Test {@link UpdateUser#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateUser.equals(Object)", "int UpdateUser.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UpdateUser(), "Different type to UpdateUser");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link UpdateUser}
   *   <li>{@link UpdateUser#setUserId(String)}
   *   <li>{@link UpdateUser#toString()}
   *   <li>{@link UpdateUser#getUserId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void UpdateUser.<init>()", "String UpdateUser.getUserId()", "void UpdateUser.setUserId(String)",
      "String UpdateUser.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    UpdateUser actualUpdateUser = new UpdateUser();
    actualUpdateUser.setUserId("42");
    String actualToStringResult = actualUpdateUser.toString();

    // Assert
    assertEquals("42", actualUpdateUser.getUserId());
    assertEquals("NORMAL", actualUpdateUser.getType());
    assertEquals("UpdateUser(userId=42)", actualToStringResult);
    assertNull(actualUpdateUser.getOn());
    assertNull(actualUpdateUser.getBusiness());
    assertNull(actualUpdateUser.getContact());
    assertNull(actualUpdateUser.getKeys());
    assertNull(actualUpdateUser.getPassword());
    assertNull(actualUpdateUser.getElseCondition());
    assertNull(actualUpdateUser.getId());
    assertNull(actualUpdateUser.getIfCondition());
    assertNull(actualUpdateUser.getDisplayName());
    assertNull(actualUpdateUser.getEmail());
    assertNull(actualUpdateUser.getFirstname());
    assertNull(actualUpdateUser.getLastname());
    assertNull(actualUpdateUser.getRecommendedLanguage());
    assertNull(actualUpdateUser.getStatus());
    assertNull(actualUpdateUser.getUsername());
    assertNull(actualUpdateUser.getRoles());
    assertNull(actualUpdateUser.getEntitlements());
    assertTrue(actualUpdateUser.getVariableProperties().isEmpty());
  }
}
