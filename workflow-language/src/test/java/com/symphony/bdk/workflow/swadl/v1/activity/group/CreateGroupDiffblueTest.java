package com.symphony.bdk.workflow.swadl.v1.activity.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateGroupDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup#equals(Object)}
   *   <li>{@link CreateGroup#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateGroup createGroup = new CreateGroup();
    CreateGroup createGroup2 = new CreateGroup();

    // Act and Assert
    assertEquals(createGroup, createGroup2);
    int expectedHashCodeResult = createGroup.hashCode();
    assertEquals(expectedHashCodeResult, createGroup2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup#equals(Object)}
   *   <li>{@link CreateGroup#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateGroup createGroup = new CreateGroup();

    // Act and Assert
    assertEquals(createGroup, createGroup);
    int expectedHashCodeResult = createGroup.hashCode();
    assertEquals(expectedHashCodeResult, createGroup.hashCode());
  }

  /**
   * Method under test: {@link CreateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();

    // Act and Assert
    assertNotEquals(updateGroup, new CreateGroup());
  }

  /**
   * Method under test: {@link CreateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateGroup createGroup = new CreateGroup();
    createGroup.add("SDL", "Value");

    // Act and Assert
    assertNotEquals(createGroup, new CreateGroup());
  }

  /**
   * Method under test: {@link CreateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CreateGroup createGroup = new CreateGroup();
    createGroup.add("SDL", mock(AddGroupMember.class));

    // Act and Assert
    assertNotEquals(createGroup, new CreateGroup());
  }

  /**
   * Method under test: {@link CreateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CreateGroup createGroup = new CreateGroup();

    // Act and Assert
    assertNotEquals(createGroup, new UpdateGroup());
  }

  /**
   * Method under test: {@link CreateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    CreateGroup createGroup = new CreateGroup();

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
    UpdateGroup updateGroup = mock(UpdateGroup.class);
    when(updateGroup.getOn()).thenReturn(eventWithTimeout);
    when(updateGroup.getElseCondition()).thenReturn("Else Condition");
    when(updateGroup.getId()).thenReturn("42");
    when(updateGroup.getIfCondition()).thenReturn("If Condition");
    when(updateGroup.getType()).thenReturn("Type");
    when(updateGroup.getVariableProperties()).thenReturn(new HashMap<>());
    when(updateGroup.canEqual(Mockito.<Object>any())).thenReturn(true);

    // Act and Assert
    assertNotEquals(createGroup, updateGroup);
  }

  /**
   * Method under test: {@link CreateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateGroup(), null);
  }

  /**
   * Method under test: {@link CreateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateGroup(), "Different type to CreateGroup");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup#setImplicitConnection(CreateGroup.ImplicitConnection)}
   *   <li>
   * {@link CreateGroup#setInteractionTransfer(CreateGroup.InteractionTransfer)}
   *   <li>{@link CreateGroup#setMembers(List)}
   *   <li>{@link CreateGroup#setName(String)}
   *   <li>{@link CreateGroup#setOwner(CreateGroup.Owner)}
   *   <li>{@link CreateGroup#setProfile(CreateGroup.Profile)}
   *   <li>{@link CreateGroup#setReferrer(String)}
   *   <li>{@link CreateGroup#setSubType(String)}
   *   <li>{@link CreateGroup#setType(String)}
   *   <li>
   * {@link CreateGroup#setVisibilityRestriction(CreateGroup.VisibilityRestriction)}
   *   <li>{@link CreateGroup#toString()}
   *   <li>{@link CreateGroup#getImplicitConnection()}
   *   <li>{@link CreateGroup#getInteractionTransfer()}
   *   <li>{@link CreateGroup#getMembers()}
   *   <li>{@link CreateGroup#getName()}
   *   <li>{@link CreateGroup#getOwner()}
   *   <li>{@link CreateGroup#getProfile()}
   *   <li>{@link CreateGroup#getReferrer()}
   *   <li>{@link CreateGroup#getSubType()}
   *   <li>{@link CreateGroup#getType()}
   *   <li>{@link CreateGroup#getVisibilityRestriction()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    CreateGroup createGroup = new CreateGroup();

    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    implicitConnection.setTenantIds(new ArrayList<>());
    implicitConnection.setUserIds(new ArrayList<>());

    // Act
    createGroup.setImplicitConnection(implicitConnection);
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    interactionTransfer.setTenantIds(new ArrayList<>());
    interactionTransfer.setUserIds(new ArrayList<>());
    createGroup.setInteractionTransfer(interactionTransfer);
    ArrayList<CreateGroup.GroupMember> members = new ArrayList<>();
    createGroup.setMembers(members);
    createGroup.setName("Name");
    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(1L);
    owner.setType("Type");
    createGroup.setOwner(owner);
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Company Name");
    profile.setDisplayName("Display Name");
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());
    createGroup.setProfile(profile);
    createGroup.setReferrer("Referrer");
    createGroup.setSubType("Sub Type");
    createGroup.setType("Type");
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    visibilityRestriction.setTenantIds(new ArrayList<>());
    visibilityRestriction.setUserIds(new ArrayList<>());
    createGroup.setVisibilityRestriction(visibilityRestriction);
    String actualToStringResult = createGroup.toString();
    CreateGroup.ImplicitConnection actualImplicitConnection = createGroup.getImplicitConnection();
    CreateGroup.InteractionTransfer actualInteractionTransfer = createGroup.getInteractionTransfer();
    List<CreateGroup.GroupMember> actualMembers = createGroup.getMembers();
    String actualName = createGroup.getName();
    CreateGroup.Owner actualOwner = createGroup.getOwner();
    CreateGroup.Profile actualProfile = createGroup.getProfile();
    String actualReferrer = createGroup.getReferrer();
    String actualSubType = createGroup.getSubType();
    String actualType = createGroup.getType();
    CreateGroup.VisibilityRestriction actualVisibilityRestriction = createGroup.getVisibilityRestriction();

    // Assert that nothing has changed
    assertEquals("CreateGroup(type=Type, owner=CreateGroup.Owner(id=1, type=Type), name=Name, subType=Sub Type,"
        + " referrer=Referrer, members=[], profile=CreateGroup.Profile(displayName=Display Name, companyName=Company"
        + " Name, email=jane.doe@example.org, mobile=Mobile, job=CreateGroup.Job(title=Dr, role=Role,"
        + " department=Department, division=Division, phone=6625550144, city=Oxford), industries=[], assetClasses=[],"
        + " marketCoverages=[], responsibilities=[], functions=[], instruments=[]), visibilityRestriction"
        + "=CreateGroup.VisibilityRestriction(tenantIds=[], userIds=[]), implicitConnection=CreateGroup"
        + ".ImplicitConnection(tenantIds=[], userIds=[]), interactionTransfer=CreateGroup.InteractionTransfer"
        + "(tenantIds=[], userIds=[]))", actualToStringResult);
    assertEquals("Name", actualName);
    assertEquals("Referrer", actualReferrer);
    assertEquals("Sub Type", actualSubType);
    assertEquals("Type", actualType);
    assertTrue(actualMembers.isEmpty());
    assertSame(implicitConnection, actualImplicitConnection);
    assertSame(interactionTransfer, actualInteractionTransfer);
    assertSame(owner, actualOwner);
    assertSame(profile, actualProfile);
    assertSame(visibilityRestriction, actualVisibilityRestriction);
    assertSame(members, actualMembers);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.GroupMember#equals(Object)}
   *   <li>{@link CreateGroup.GroupMember#hashCode()}
   * </ul>
   */
  @Test
  void testGroupMemberEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(1L);

    CreateGroup.GroupMember groupMember2 = new CreateGroup.GroupMember();
    groupMember2.setTenantId(1);
    groupMember2.setUserId(1L);

    // Act and Assert
    assertEquals(groupMember, groupMember2);
    int expectedHashCodeResult = groupMember.hashCode();
    assertEquals(expectedHashCodeResult, groupMember2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.GroupMember#equals(Object)}
   *   <li>{@link CreateGroup.GroupMember#hashCode()}
   * </ul>
   */
  @Test
  void testGroupMemberEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setTenantId(null);
    groupMember.setUserId(1L);

    CreateGroup.GroupMember groupMember2 = new CreateGroup.GroupMember();
    groupMember2.setTenantId(null);
    groupMember2.setUserId(1L);

    // Act and Assert
    assertEquals(groupMember, groupMember2);
    int expectedHashCodeResult = groupMember.hashCode();
    assertEquals(expectedHashCodeResult, groupMember2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.GroupMember#equals(Object)}
   *   <li>{@link CreateGroup.GroupMember#hashCode()}
   * </ul>
   */
  @Test
  void testGroupMemberEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(null);

    CreateGroup.GroupMember groupMember2 = new CreateGroup.GroupMember();
    groupMember2.setTenantId(1);
    groupMember2.setUserId(null);

    // Act and Assert
    assertEquals(groupMember, groupMember2);
    int expectedHashCodeResult = groupMember.hashCode();
    assertEquals(expectedHashCodeResult, groupMember2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.GroupMember#equals(Object)}
   *   <li>{@link CreateGroup.GroupMember#hashCode()}
   * </ul>
   */
  @Test
  void testGroupMemberEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(1L);

    // Act and Assert
    assertEquals(groupMember, groupMember);
    int expectedHashCodeResult = groupMember.hashCode();
    assertEquals(expectedHashCodeResult, groupMember.hashCode());
  }

  /**
   * Method under test: {@link CreateGroup.GroupMember#equals(Object)}
   */
  @Test
  void testGroupMemberEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setTenantId(2);
    groupMember.setUserId(1L);

    CreateGroup.GroupMember groupMember2 = new CreateGroup.GroupMember();
    groupMember2.setTenantId(1);
    groupMember2.setUserId(1L);

    // Act and Assert
    assertNotEquals(groupMember, groupMember2);
  }

  /**
   * Method under test: {@link CreateGroup.GroupMember#equals(Object)}
   */
  @Test
  void testGroupMemberEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setTenantId(null);
    groupMember.setUserId(1L);

    CreateGroup.GroupMember groupMember2 = new CreateGroup.GroupMember();
    groupMember2.setTenantId(1);
    groupMember2.setUserId(1L);

    // Act and Assert
    assertNotEquals(groupMember, groupMember2);
  }

  /**
   * Method under test: {@link CreateGroup.GroupMember#equals(Object)}
   */
  @Test
  void testGroupMemberEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(2L);

    CreateGroup.GroupMember groupMember2 = new CreateGroup.GroupMember();
    groupMember2.setTenantId(1);
    groupMember2.setUserId(1L);

    // Act and Assert
    assertNotEquals(groupMember, groupMember2);
  }

  /**
   * Method under test: {@link CreateGroup.GroupMember#equals(Object)}
   */
  @Test
  void testGroupMemberEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(null);

    CreateGroup.GroupMember groupMember2 = new CreateGroup.GroupMember();
    groupMember2.setTenantId(1);
    groupMember2.setUserId(1L);

    // Act and Assert
    assertNotEquals(groupMember, groupMember2);
  }

  /**
   * Method under test: {@link CreateGroup.GroupMember#equals(Object)}
   */
  @Test
  void testGroupMemberEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(1L);

    // Act and Assert
    assertNotEquals(groupMember, null);
  }

  /**
   * Method under test: {@link CreateGroup.GroupMember#equals(Object)}
   */
  @Test
  void testGroupMemberEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(1L);

    // Act and Assert
    assertNotEquals(groupMember, "Different type to GroupMember");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CreateGroup.GroupMember}
   *   <li>{@link CreateGroup.GroupMember#setTenantId(Integer)}
   *   <li>{@link CreateGroup.GroupMember#setUserId(Long)}
   *   <li>{@link CreateGroup.GroupMember#toString()}
   *   <li>{@link CreateGroup.GroupMember#getTenantId()}
   *   <li>{@link CreateGroup.GroupMember#getUserId()}
   * </ul>
   */
  @Test
  void testGroupMemberGettersAndSetters() {
    // Arrange and Act
    CreateGroup.GroupMember actualGroupMember = new CreateGroup.GroupMember();
    actualGroupMember.setTenantId(1);
    actualGroupMember.setUserId(1L);
    String actualToStringResult = actualGroupMember.toString();
    Integer actualTenantId = actualGroupMember.getTenantId();
    Long actualUserId = actualGroupMember.getUserId();

    // Assert that nothing has changed
    assertEquals("CreateGroup.GroupMember(userId=1, tenantId=1)", actualToStringResult);
    assertEquals(1, actualTenantId.intValue());
    assertEquals(1L, actualUserId.longValue());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.ImplicitConnection#equals(Object)}
   *   <li>{@link CreateGroup.ImplicitConnection#hashCode()}
   * </ul>
   */
  @Test
  void testImplicitConnectionEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    implicitConnection.setTenantIds(new ArrayList<>());
    implicitConnection.setUserIds(new ArrayList<>());

    CreateGroup.ImplicitConnection implicitConnection2 = new CreateGroup.ImplicitConnection();
    implicitConnection2.setTenantIds(new ArrayList<>());
    implicitConnection2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(implicitConnection, implicitConnection2);
    int expectedHashCodeResult = implicitConnection.hashCode();
    assertEquals(expectedHashCodeResult, implicitConnection2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.ImplicitConnection#equals(Object)}
   *   <li>{@link CreateGroup.ImplicitConnection#hashCode()}
   * </ul>
   */
  @Test
  void testImplicitConnectionEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    implicitConnection.setTenantIds(new ArrayList<>());
    implicitConnection.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(implicitConnection, implicitConnection);
    int expectedHashCodeResult = implicitConnection.hashCode();
    assertEquals(expectedHashCodeResult, implicitConnection.hashCode());
  }

  /**
   * Method under test: {@link CreateGroup.ImplicitConnection#equals(Object)}
   */
  @Test
  void testImplicitConnectionEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ArrayList<Integer> tenantIds = new ArrayList<>();
    tenantIds.add(2);

    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    implicitConnection.setTenantIds(tenantIds);
    implicitConnection.setUserIds(new ArrayList<>());

    CreateGroup.ImplicitConnection implicitConnection2 = new CreateGroup.ImplicitConnection();
    implicitConnection2.setTenantIds(new ArrayList<>());
    implicitConnection2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(implicitConnection, implicitConnection2);
  }

  /**
   * Method under test: {@link CreateGroup.ImplicitConnection#equals(Object)}
   */
  @Test
  void testImplicitConnectionEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ArrayList<Long> userIds = new ArrayList<>();
    userIds.add(1L);

    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    implicitConnection.setTenantIds(new ArrayList<>());
    implicitConnection.setUserIds(userIds);

    CreateGroup.ImplicitConnection implicitConnection2 = new CreateGroup.ImplicitConnection();
    implicitConnection2.setTenantIds(new ArrayList<>());
    implicitConnection2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(implicitConnection, implicitConnection2);
  }

  /**
   * Method under test: {@link CreateGroup.ImplicitConnection#equals(Object)}
   */
  @Test
  void testImplicitConnectionEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    implicitConnection.setTenantIds(new ArrayList<>());
    implicitConnection.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(implicitConnection, null);
  }

  /**
   * Method under test: {@link CreateGroup.ImplicitConnection#equals(Object)}
   */
  @Test
  void testImplicitConnectionEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    implicitConnection.setTenantIds(new ArrayList<>());
    implicitConnection.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(implicitConnection, "Different type to ImplicitConnection");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link CreateGroup.ImplicitConnection}
   *   <li>{@link CreateGroup.ImplicitConnection#setTenantIds(List)}
   *   <li>{@link CreateGroup.ImplicitConnection#setUserIds(List)}
   *   <li>{@link CreateGroup.ImplicitConnection#toString()}
   *   <li>{@link CreateGroup.ImplicitConnection#getTenantIds()}
   *   <li>{@link CreateGroup.ImplicitConnection#getUserIds()}
   * </ul>
   */
  @Test
  void testImplicitConnectionGettersAndSetters() {
    // Arrange and Act
    CreateGroup.ImplicitConnection actualImplicitConnection = new CreateGroup.ImplicitConnection();
    ArrayList<Integer> tenantIds = new ArrayList<>();
    actualImplicitConnection.setTenantIds(tenantIds);
    ArrayList<Long> userIds = new ArrayList<>();
    actualImplicitConnection.setUserIds(userIds);
    String actualToStringResult = actualImplicitConnection.toString();
    List<Integer> actualTenantIds = actualImplicitConnection.getTenantIds();
    List<Long> actualUserIds = actualImplicitConnection.getUserIds();

    // Assert that nothing has changed
    assertEquals("CreateGroup.ImplicitConnection(tenantIds=[], userIds=[])", actualToStringResult);
    assertTrue(actualTenantIds.isEmpty());
    assertTrue(actualUserIds.isEmpty());
    assertSame(tenantIds, actualTenantIds);
    assertSame(userIds, actualUserIds);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.InteractionTransfer#equals(Object)}
   *   <li>{@link CreateGroup.InteractionTransfer#hashCode()}
   * </ul>
   */
  @Test
  void testInteractionTransferEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    interactionTransfer.setTenantIds(new ArrayList<>());
    interactionTransfer.setUserIds(new ArrayList<>());

    CreateGroup.InteractionTransfer interactionTransfer2 = new CreateGroup.InteractionTransfer();
    interactionTransfer2.setTenantIds(new ArrayList<>());
    interactionTransfer2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(interactionTransfer, interactionTransfer2);
    int expectedHashCodeResult = interactionTransfer.hashCode();
    assertEquals(expectedHashCodeResult, interactionTransfer2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.InteractionTransfer#equals(Object)}
   *   <li>{@link CreateGroup.InteractionTransfer#hashCode()}
   * </ul>
   */
  @Test
  void testInteractionTransferEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    interactionTransfer.setTenantIds(new ArrayList<>());
    interactionTransfer.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(interactionTransfer, interactionTransfer);
    int expectedHashCodeResult = interactionTransfer.hashCode();
    assertEquals(expectedHashCodeResult, interactionTransfer.hashCode());
  }

  /**
   * Method under test: {@link CreateGroup.InteractionTransfer#equals(Object)}
   */
  @Test
  void testInteractionTransferEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ArrayList<Integer> tenantIds = new ArrayList<>();
    tenantIds.add(2);

    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    interactionTransfer.setTenantIds(tenantIds);
    interactionTransfer.setUserIds(new ArrayList<>());

    CreateGroup.InteractionTransfer interactionTransfer2 = new CreateGroup.InteractionTransfer();
    interactionTransfer2.setTenantIds(new ArrayList<>());
    interactionTransfer2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(interactionTransfer, interactionTransfer2);
  }

  /**
   * Method under test: {@link CreateGroup.InteractionTransfer#equals(Object)}
   */
  @Test
  void testInteractionTransferEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ArrayList<Long> userIds = new ArrayList<>();
    userIds.add(1L);

    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    interactionTransfer.setTenantIds(new ArrayList<>());
    interactionTransfer.setUserIds(userIds);

    CreateGroup.InteractionTransfer interactionTransfer2 = new CreateGroup.InteractionTransfer();
    interactionTransfer2.setTenantIds(new ArrayList<>());
    interactionTransfer2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(interactionTransfer, interactionTransfer2);
  }

  /**
   * Method under test: {@link CreateGroup.InteractionTransfer#equals(Object)}
   */
  @Test
  void testInteractionTransferEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    interactionTransfer.setTenantIds(new ArrayList<>());
    interactionTransfer.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(interactionTransfer, null);
  }

  /**
   * Method under test: {@link CreateGroup.InteractionTransfer#equals(Object)}
   */
  @Test
  void testInteractionTransferEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    interactionTransfer.setTenantIds(new ArrayList<>());
    interactionTransfer.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(interactionTransfer, "Different type to InteractionTransfer");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link CreateGroup.InteractionTransfer}
   *   <li>{@link CreateGroup.InteractionTransfer#setTenantIds(List)}
   *   <li>{@link CreateGroup.InteractionTransfer#setUserIds(List)}
   *   <li>{@link CreateGroup.InteractionTransfer#toString()}
   *   <li>{@link CreateGroup.InteractionTransfer#getTenantIds()}
   *   <li>{@link CreateGroup.InteractionTransfer#getUserIds()}
   * </ul>
   */
  @Test
  void testInteractionTransferGettersAndSetters() {
    // Arrange and Act
    CreateGroup.InteractionTransfer actualInteractionTransfer = new CreateGroup.InteractionTransfer();
    ArrayList<Integer> tenantIds = new ArrayList<>();
    actualInteractionTransfer.setTenantIds(tenantIds);
    ArrayList<Long> userIds = new ArrayList<>();
    actualInteractionTransfer.setUserIds(userIds);
    String actualToStringResult = actualInteractionTransfer.toString();
    List<Integer> actualTenantIds = actualInteractionTransfer.getTenantIds();
    List<Long> actualUserIds = actualInteractionTransfer.getUserIds();

    // Assert that nothing has changed
    assertEquals("CreateGroup.InteractionTransfer(tenantIds=[], userIds=[])", actualToStringResult);
    assertTrue(actualTenantIds.isEmpty());
    assertTrue(actualUserIds.isEmpty());
    assertSame(tenantIds, actualTenantIds);
    assertSame(userIds, actualUserIds);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.Job#equals(Object)}
   *   <li>{@link CreateGroup.Job#hashCode()}
   * </ul>
   */
  @Test
  void testJobEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    // Act and Assert
    assertEquals(job, job2);
    int expectedHashCodeResult = job.hashCode();
    assertEquals(expectedHashCodeResult, job2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.Job#equals(Object)}
   *   <li>{@link CreateGroup.Job#hashCode()}
   * </ul>
   */
  @Test
  void testJobEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity(null);
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity(null);
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    // Act and Assert
    assertEquals(job, job2);
    int expectedHashCodeResult = job.hashCode();
    assertEquals(expectedHashCodeResult, job2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.Job#equals(Object)}
   *   <li>{@link CreateGroup.Job#hashCode()}
   * </ul>
   */
  @Test
  void testJobEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment(null);
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment(null);
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    // Act and Assert
    assertEquals(job, job2);
    int expectedHashCodeResult = job.hashCode();
    assertEquals(expectedHashCodeResult, job2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.Job#equals(Object)}
   *   <li>{@link CreateGroup.Job#hashCode()}
   * </ul>
   */
  @Test
  void testJobEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    // Act and Assert
    assertEquals(job, job);
    int expectedHashCodeResult = job.hashCode();
    assertEquals(expectedHashCodeResult, job.hashCode());
  }

  /**
   * Method under test: {@link CreateGroup.Job#equals(Object)}
   */
  @Test
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("London");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(job, job2);
  }

  /**
   * Method under test: {@link CreateGroup.Job#equals(Object)}
   */
  @Test
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity(null);
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(job, job2);
  }

  /**
   * Method under test: {@link CreateGroup.Job#equals(Object)}
   */
  @Test
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Dr");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(job, job2);
  }

  /**
   * Method under test: {@link CreateGroup.Job#equals(Object)}
   */
  @Test
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment(null);
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(job, job2);
  }

  /**
   * Method under test: {@link CreateGroup.Job#equals(Object)}
   */
  @Test
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Dr");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(job, job2);
  }

  /**
   * Method under test: {@link CreateGroup.Job#equals(Object)}
   */
  @Test
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision(null);
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(job, job2);
  }

  /**
   * Method under test: {@link CreateGroup.Job#equals(Object)}
   */
  @Test
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("8605550118");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(job, job2);
  }

  /**
   * Method under test: {@link CreateGroup.Job#equals(Object)}
   */
  @Test
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone(null);
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(job, job2);
  }

  /**
   * Method under test: {@link CreateGroup.Job#equals(Object)}
   */
  @Test
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Dr");
    job.setTitle("Dr");

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(job, job2);
  }

  /**
   * Method under test: {@link CreateGroup.Job#equals(Object)}
   */
  @Test
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole(null);
    job.setTitle("Dr");

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(job, job2);
  }

  /**
   * Method under test: {@link CreateGroup.Job#equals(Object)}
   */
  @Test
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Mr");

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(job, job2);
  }

  /**
   * Method under test: {@link CreateGroup.Job#equals(Object)}
   */
  @Test
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle(null);

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(job, job2);
  }

  /**
   * Method under test: {@link CreateGroup.Job#equals(Object)}
   */
  @Test
  void testJobEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    // Act and Assert
    assertNotEquals(job, null);
  }

  /**
   * Method under test: {@link CreateGroup.Job#equals(Object)}
   */
  @Test
  void testJobEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    // Act and Assert
    assertNotEquals(job, "Different type to Job");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CreateGroup.Job}
   *   <li>{@link CreateGroup.Job#setCity(String)}
   *   <li>{@link CreateGroup.Job#setDepartment(String)}
   *   <li>{@link CreateGroup.Job#setDivision(String)}
   *   <li>{@link CreateGroup.Job#setPhone(String)}
   *   <li>{@link CreateGroup.Job#setRole(String)}
   *   <li>{@link CreateGroup.Job#setTitle(String)}
   *   <li>{@link CreateGroup.Job#toString()}
   *   <li>{@link CreateGroup.Job#getCity()}
   *   <li>{@link CreateGroup.Job#getDepartment()}
   *   <li>{@link CreateGroup.Job#getDivision()}
   *   <li>{@link CreateGroup.Job#getPhone()}
   *   <li>{@link CreateGroup.Job#getRole()}
   *   <li>{@link CreateGroup.Job#getTitle()}
   * </ul>
   */
  @Test
  void testJobGettersAndSetters() {
    // Arrange and Act
    CreateGroup.Job actualJob = new CreateGroup.Job();
    actualJob.setCity("Oxford");
    actualJob.setDepartment("Department");
    actualJob.setDivision("Division");
    actualJob.setPhone("6625550144");
    actualJob.setRole("Role");
    actualJob.setTitle("Dr");
    String actualToStringResult = actualJob.toString();
    String actualCity = actualJob.getCity();
    String actualDepartment = actualJob.getDepartment();
    String actualDivision = actualJob.getDivision();
    String actualPhone = actualJob.getPhone();
    String actualRole = actualJob.getRole();

    // Assert that nothing has changed
    assertEquals("6625550144", actualPhone);
    assertEquals("CreateGroup.Job(title=Dr, role=Role, department=Department, division=Division, phone=6625550144,"
        + " city=Oxford)", actualToStringResult);
    assertEquals("Department", actualDepartment);
    assertEquals("Division", actualDivision);
    assertEquals("Dr", actualJob.getTitle());
    assertEquals("Oxford", actualCity);
    assertEquals("Role", actualRole);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link CreateGroup}
   */
  @Test
  void testNewCreateGroup() {
    // Arrange and Act
    CreateGroup actualCreateGroup = new CreateGroup();

    // Assert
    assertEquals("SDL", actualCreateGroup.getType());
    assertNull(actualCreateGroup.getOn());
    assertNull(actualCreateGroup.getImplicitConnection());
    assertNull(actualCreateGroup.getInteractionTransfer());
    assertNull(actualCreateGroup.getOwner());
    assertNull(actualCreateGroup.getProfile());
    assertNull(actualCreateGroup.getVisibilityRestriction());
    assertNull(actualCreateGroup.getElseCondition());
    assertNull(actualCreateGroup.getId());
    assertNull(actualCreateGroup.getIfCondition());
    RelationalEvents events = actualCreateGroup.getEvents();
    assertNull(events.getParentId());
    assertNull(actualCreateGroup.getName());
    assertNull(actualCreateGroup.getReferrer());
    assertNull(actualCreateGroup.getSubType());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualCreateGroup.getMembers().isEmpty());
    assertTrue(actualCreateGroup.getVariableProperties().isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.Owner#equals(Object)}
   *   <li>{@link CreateGroup.Owner#hashCode()}
   * </ul>
   */
  @Test
  void testOwnerEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(1L);
    owner.setType("Type");

    CreateGroup.Owner owner2 = new CreateGroup.Owner();
    owner2.setId(1L);
    owner2.setType("Type");

    // Act and Assert
    assertEquals(owner, owner2);
    int expectedHashCodeResult = owner.hashCode();
    assertEquals(expectedHashCodeResult, owner2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.Owner#equals(Object)}
   *   <li>{@link CreateGroup.Owner#hashCode()}
   * </ul>
   */
  @Test
  void testOwnerEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(null);
    owner.setType("Type");

    CreateGroup.Owner owner2 = new CreateGroup.Owner();
    owner2.setId(null);
    owner2.setType("Type");

    // Act and Assert
    assertEquals(owner, owner2);
    int expectedHashCodeResult = owner.hashCode();
    assertEquals(expectedHashCodeResult, owner2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.Owner#equals(Object)}
   *   <li>{@link CreateGroup.Owner#hashCode()}
   * </ul>
   */
  @Test
  void testOwnerEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(1L);
    owner.setType(null);

    CreateGroup.Owner owner2 = new CreateGroup.Owner();
    owner2.setId(1L);
    owner2.setType(null);

    // Act and Assert
    assertEquals(owner, owner2);
    int expectedHashCodeResult = owner.hashCode();
    assertEquals(expectedHashCodeResult, owner2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.Owner#equals(Object)}
   *   <li>{@link CreateGroup.Owner#hashCode()}
   * </ul>
   */
  @Test
  void testOwnerEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(1L);
    owner.setType("Type");

    // Act and Assert
    assertEquals(owner, owner);
    int expectedHashCodeResult = owner.hashCode();
    assertEquals(expectedHashCodeResult, owner.hashCode());
  }

  /**
   * Method under test: {@link CreateGroup.Owner#equals(Object)}
   */
  @Test
  void testOwnerEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(2L);
    owner.setType("Type");

    CreateGroup.Owner owner2 = new CreateGroup.Owner();
    owner2.setId(1L);
    owner2.setType("Type");

    // Act and Assert
    assertNotEquals(owner, owner2);
  }

  /**
   * Method under test: {@link CreateGroup.Owner#equals(Object)}
   */
  @Test
  void testOwnerEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(null);
    owner.setType("Type");

    CreateGroup.Owner owner2 = new CreateGroup.Owner();
    owner2.setId(1L);
    owner2.setType("Type");

    // Act and Assert
    assertNotEquals(owner, owner2);
  }

  /**
   * Method under test: {@link CreateGroup.Owner#equals(Object)}
   */
  @Test
  void testOwnerEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(1L);
    owner.setType(null);

    CreateGroup.Owner owner2 = new CreateGroup.Owner();
    owner2.setId(1L);
    owner2.setType("Type");

    // Act and Assert
    assertNotEquals(owner, owner2);
  }

  /**
   * Method under test: {@link CreateGroup.Owner#equals(Object)}
   */
  @Test
  void testOwnerEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(1L);
    owner.setType("com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup$Owner");

    CreateGroup.Owner owner2 = new CreateGroup.Owner();
    owner2.setId(1L);
    owner2.setType("Type");

    // Act and Assert
    assertNotEquals(owner, owner2);
  }

  /**
   * Method under test: {@link CreateGroup.Owner#equals(Object)}
   */
  @Test
  void testOwnerEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(1L);
    owner.setType("Type");

    // Act and Assert
    assertNotEquals(owner, null);
  }

  /**
   * Method under test: {@link CreateGroup.Owner#equals(Object)}
   */
  @Test
  void testOwnerEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(1L);
    owner.setType("Type");

    // Act and Assert
    assertNotEquals(owner, "Different type to Owner");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CreateGroup.Owner}
   *   <li>{@link CreateGroup.Owner#setId(Long)}
   *   <li>{@link CreateGroup.Owner#setType(String)}
   *   <li>{@link CreateGroup.Owner#toString()}
   *   <li>{@link CreateGroup.Owner#getId()}
   *   <li>{@link CreateGroup.Owner#getType()}
   * </ul>
   */
  @Test
  void testOwnerGettersAndSetters() {
    // Arrange and Act
    CreateGroup.Owner actualOwner = new CreateGroup.Owner();
    actualOwner.setId(1L);
    actualOwner.setType("Type");
    String actualToStringResult = actualOwner.toString();
    Long actualId = actualOwner.getId();

    // Assert that nothing has changed
    assertEquals("CreateGroup.Owner(id=1, type=Type)", actualToStringResult);
    assertEquals("Type", actualOwner.getType());
    assertEquals(1L, actualId.longValue());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.Profile#equals(Object)}
   *   <li>{@link CreateGroup.Profile#hashCode()}
   * </ul>
   */
  @Test
  void testProfileEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Company Name");
    profile.setDisplayName("Display Name");
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    CreateGroup.Profile profile2 = new CreateGroup.Profile();
    profile2.setAssetClasses(new ArrayList<>());
    profile2.setCompanyName("Company Name");
    profile2.setDisplayName("Display Name");
    profile2.setEmail("jane.doe@example.org");
    profile2.setFunctions(new HashSet<>());
    profile2.setIndustries(new ArrayList<>());
    profile2.setInstruments(new HashSet<>());
    profile2.setJob(job2);
    profile2.setMarketCoverages(new HashSet<>());
    profile2.setMobile("Mobile");
    profile2.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertEquals(profile, profile2);
    int expectedHashCodeResult = profile.hashCode();
    assertEquals(expectedHashCodeResult, profile2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.Profile#equals(Object)}
   *   <li>{@link CreateGroup.Profile#hashCode()}
   * </ul>
   */
  @Test
  void testProfileEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Company Name");
    profile.setDisplayName("Display Name");
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertEquals(profile, profile);
    int expectedHashCodeResult = profile.hashCode();
    assertEquals(expectedHashCodeResult, profile.hashCode());
  }

  /**
   * Method under test: {@link CreateGroup.Profile#equals(Object)}
   */
  @Test
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ArrayList<String> assetClasses = new ArrayList<>();
    assetClasses.add("Display Name");

    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(assetClasses);
    profile.setCompanyName("Company Name");
    profile.setDisplayName("Display Name");
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    CreateGroup.Profile profile2 = new CreateGroup.Profile();
    profile2.setAssetClasses(new ArrayList<>());
    profile2.setCompanyName("Company Name");
    profile2.setDisplayName("Display Name");
    profile2.setEmail("jane.doe@example.org");
    profile2.setFunctions(new HashSet<>());
    profile2.setIndustries(new ArrayList<>());
    profile2.setInstruments(new HashSet<>());
    profile2.setJob(job2);
    profile2.setMarketCoverages(new HashSet<>());
    profile2.setMobile("Mobile");
    profile2.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertNotEquals(profile, profile2);
  }

  /**
   * Method under test: {@link CreateGroup.Profile#equals(Object)}
   */
  @Test
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Display Name");
    profile.setDisplayName("Display Name");
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    CreateGroup.Profile profile2 = new CreateGroup.Profile();
    profile2.setAssetClasses(new ArrayList<>());
    profile2.setCompanyName("Company Name");
    profile2.setDisplayName("Display Name");
    profile2.setEmail("jane.doe@example.org");
    profile2.setFunctions(new HashSet<>());
    profile2.setIndustries(new ArrayList<>());
    profile2.setInstruments(new HashSet<>());
    profile2.setJob(job2);
    profile2.setMarketCoverages(new HashSet<>());
    profile2.setMobile("Mobile");
    profile2.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertNotEquals(profile, profile2);
  }

  /**
   * Method under test: {@link CreateGroup.Profile#equals(Object)}
   */
  @Test
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName(null);
    profile.setDisplayName("Display Name");
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    CreateGroup.Profile profile2 = new CreateGroup.Profile();
    profile2.setAssetClasses(new ArrayList<>());
    profile2.setCompanyName("Company Name");
    profile2.setDisplayName("Display Name");
    profile2.setEmail("jane.doe@example.org");
    profile2.setFunctions(new HashSet<>());
    profile2.setIndustries(new ArrayList<>());
    profile2.setInstruments(new HashSet<>());
    profile2.setJob(job2);
    profile2.setMarketCoverages(new HashSet<>());
    profile2.setMobile("Mobile");
    profile2.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertNotEquals(profile, profile2);
  }

  /**
   * Method under test: {@link CreateGroup.Profile#equals(Object)}
   */
  @Test
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Company Name");
    profile.setDisplayName("Company Name");
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    CreateGroup.Profile profile2 = new CreateGroup.Profile();
    profile2.setAssetClasses(new ArrayList<>());
    profile2.setCompanyName("Company Name");
    profile2.setDisplayName("Display Name");
    profile2.setEmail("jane.doe@example.org");
    profile2.setFunctions(new HashSet<>());
    profile2.setIndustries(new ArrayList<>());
    profile2.setInstruments(new HashSet<>());
    profile2.setJob(job2);
    profile2.setMarketCoverages(new HashSet<>());
    profile2.setMobile("Mobile");
    profile2.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertNotEquals(profile, profile2);
  }

  /**
   * Method under test: {@link CreateGroup.Profile#equals(Object)}
   */
  @Test
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Company Name");
    profile.setDisplayName(null);
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    CreateGroup.Profile profile2 = new CreateGroup.Profile();
    profile2.setAssetClasses(new ArrayList<>());
    profile2.setCompanyName("Company Name");
    profile2.setDisplayName("Display Name");
    profile2.setEmail("jane.doe@example.org");
    profile2.setFunctions(new HashSet<>());
    profile2.setIndustries(new ArrayList<>());
    profile2.setInstruments(new HashSet<>());
    profile2.setJob(job2);
    profile2.setMarketCoverages(new HashSet<>());
    profile2.setMobile("Mobile");
    profile2.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertNotEquals(profile, profile2);
  }

  /**
   * Method under test: {@link CreateGroup.Profile#equals(Object)}
   */
  @Test
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Company Name");
    profile.setDisplayName("Display Name");
    profile.setEmail("john.smith@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    CreateGroup.Profile profile2 = new CreateGroup.Profile();
    profile2.setAssetClasses(new ArrayList<>());
    profile2.setCompanyName("Company Name");
    profile2.setDisplayName("Display Name");
    profile2.setEmail("jane.doe@example.org");
    profile2.setFunctions(new HashSet<>());
    profile2.setIndustries(new ArrayList<>());
    profile2.setInstruments(new HashSet<>());
    profile2.setJob(job2);
    profile2.setMarketCoverages(new HashSet<>());
    profile2.setMobile("Mobile");
    profile2.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertNotEquals(profile, profile2);
  }

  /**
   * Method under test: {@link CreateGroup.Profile#equals(Object)}
   */
  @Test
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Company Name");
    profile.setDisplayName("Display Name");
    profile.setEmail(null);
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    CreateGroup.Profile profile2 = new CreateGroup.Profile();
    profile2.setAssetClasses(new ArrayList<>());
    profile2.setCompanyName("Company Name");
    profile2.setDisplayName("Display Name");
    profile2.setEmail("jane.doe@example.org");
    profile2.setFunctions(new HashSet<>());
    profile2.setIndustries(new ArrayList<>());
    profile2.setInstruments(new HashSet<>());
    profile2.setJob(job2);
    profile2.setMarketCoverages(new HashSet<>());
    profile2.setMobile("Mobile");
    profile2.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertNotEquals(profile, profile2);
  }

  /**
   * Method under test: {@link CreateGroup.Profile#equals(Object)}
   */
  @Test
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    HashSet<String> functions = new HashSet<>();
    functions.add("Display Name");

    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Company Name");
    profile.setDisplayName("Display Name");
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(functions);
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    CreateGroup.Profile profile2 = new CreateGroup.Profile();
    profile2.setAssetClasses(new ArrayList<>());
    profile2.setCompanyName("Company Name");
    profile2.setDisplayName("Display Name");
    profile2.setEmail("jane.doe@example.org");
    profile2.setFunctions(new HashSet<>());
    profile2.setIndustries(new ArrayList<>());
    profile2.setInstruments(new HashSet<>());
    profile2.setJob(job2);
    profile2.setMarketCoverages(new HashSet<>());
    profile2.setMobile("Mobile");
    profile2.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertNotEquals(profile, profile2);
  }

  /**
   * Method under test: {@link CreateGroup.Profile#equals(Object)}
   */
  @Test
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    ArrayList<String> industries = new ArrayList<>();
    industries.add("Display Name");

    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Company Name");
    profile.setDisplayName("Display Name");
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(industries);
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    CreateGroup.Profile profile2 = new CreateGroup.Profile();
    profile2.setAssetClasses(new ArrayList<>());
    profile2.setCompanyName("Company Name");
    profile2.setDisplayName("Display Name");
    profile2.setEmail("jane.doe@example.org");
    profile2.setFunctions(new HashSet<>());
    profile2.setIndustries(new ArrayList<>());
    profile2.setInstruments(new HashSet<>());
    profile2.setJob(job2);
    profile2.setMarketCoverages(new HashSet<>());
    profile2.setMobile("Mobile");
    profile2.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertNotEquals(profile, profile2);
  }

  /**
   * Method under test: {@link CreateGroup.Profile#equals(Object)}
   */
  @Test
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    HashSet<String> instruments = new HashSet<>();
    instruments.add("Display Name");

    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Company Name");
    profile.setDisplayName("Display Name");
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(instruments);
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    CreateGroup.Profile profile2 = new CreateGroup.Profile();
    profile2.setAssetClasses(new ArrayList<>());
    profile2.setCompanyName("Company Name");
    profile2.setDisplayName("Display Name");
    profile2.setEmail("jane.doe@example.org");
    profile2.setFunctions(new HashSet<>());
    profile2.setIndustries(new ArrayList<>());
    profile2.setInstruments(new HashSet<>());
    profile2.setJob(job2);
    profile2.setMarketCoverages(new HashSet<>());
    profile2.setMobile("Mobile");
    profile2.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertNotEquals(profile, profile2);
  }

  /**
   * Method under test: {@link CreateGroup.Profile#equals(Object)}
   */
  @Test
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    CreateGroup.Job job = mock(CreateGroup.Job.class);
    doNothing().when(job).setCity(Mockito.<String>any());
    doNothing().when(job).setDepartment(Mockito.<String>any());
    doNothing().when(job).setDivision(Mockito.<String>any());
    doNothing().when(job).setPhone(Mockito.<String>any());
    doNothing().when(job).setRole(Mockito.<String>any());
    doNothing().when(job).setTitle(Mockito.<String>any());
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Company Name");
    profile.setDisplayName("Display Name");
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    CreateGroup.Profile profile2 = new CreateGroup.Profile();
    profile2.setAssetClasses(new ArrayList<>());
    profile2.setCompanyName("Company Name");
    profile2.setDisplayName("Display Name");
    profile2.setEmail("jane.doe@example.org");
    profile2.setFunctions(new HashSet<>());
    profile2.setIndustries(new ArrayList<>());
    profile2.setInstruments(new HashSet<>());
    profile2.setJob(job2);
    profile2.setMarketCoverages(new HashSet<>());
    profile2.setMobile("Mobile");
    profile2.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertNotEquals(profile, profile2);
  }

  /**
   * Method under test: {@link CreateGroup.Profile#equals(Object)}
   */
  @Test
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    CreateGroup.Job job = mock(CreateGroup.Job.class);
    doNothing().when(job).setCity(Mockito.<String>any());
    doNothing().when(job).setDepartment(Mockito.<String>any());
    doNothing().when(job).setDivision(Mockito.<String>any());
    doNothing().when(job).setPhone(Mockito.<String>any());
    doNothing().when(job).setRole(Mockito.<String>any());
    doNothing().when(job).setTitle(Mockito.<String>any());
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Company Name");
    profile.setDisplayName("Display Name");
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Display Name");
    profile.setResponsibilities(new HashSet<>());

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    CreateGroup.Profile profile2 = new CreateGroup.Profile();
    profile2.setAssetClasses(new ArrayList<>());
    profile2.setCompanyName("Company Name");
    profile2.setDisplayName("Display Name");
    profile2.setEmail("jane.doe@example.org");
    profile2.setFunctions(new HashSet<>());
    profile2.setIndustries(new ArrayList<>());
    profile2.setInstruments(new HashSet<>());
    profile2.setJob(job2);
    profile2.setMarketCoverages(new HashSet<>());
    profile2.setMobile("Mobile");
    profile2.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertNotEquals(profile, profile2);
  }

  /**
   * Method under test: {@link CreateGroup.Profile#equals(Object)}
   */
  @Test
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    CreateGroup.Job job = mock(CreateGroup.Job.class);
    doNothing().when(job).setCity(Mockito.<String>any());
    doNothing().when(job).setDepartment(Mockito.<String>any());
    doNothing().when(job).setDivision(Mockito.<String>any());
    doNothing().when(job).setPhone(Mockito.<String>any());
    doNothing().when(job).setRole(Mockito.<String>any());
    doNothing().when(job).setTitle(Mockito.<String>any());
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Company Name");
    profile.setDisplayName("Display Name");
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile(null);
    profile.setResponsibilities(new HashSet<>());

    CreateGroup.Job job2 = new CreateGroup.Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    CreateGroup.Profile profile2 = new CreateGroup.Profile();
    profile2.setAssetClasses(new ArrayList<>());
    profile2.setCompanyName("Company Name");
    profile2.setDisplayName("Display Name");
    profile2.setEmail("jane.doe@example.org");
    profile2.setFunctions(new HashSet<>());
    profile2.setIndustries(new ArrayList<>());
    profile2.setInstruments(new HashSet<>());
    profile2.setJob(job2);
    profile2.setMarketCoverages(new HashSet<>());
    profile2.setMobile("Mobile");
    profile2.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertNotEquals(profile, profile2);
  }

  /**
   * Method under test: {@link CreateGroup.Profile#equals(Object)}
   */
  @Test
  void testProfileEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Company Name");
    profile.setDisplayName("Display Name");
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertNotEquals(profile, null);
  }

  /**
   * Method under test: {@link CreateGroup.Profile#equals(Object)}
   */
  @Test
  void testProfileEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Company Name");
    profile.setDisplayName("Display Name");
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());

    // Act and Assert
    assertNotEquals(profile, "Different type to Profile");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CreateGroup.Profile}
   *   <li>{@link CreateGroup.Profile#setAssetClasses(List)}
   *   <li>{@link CreateGroup.Profile#setCompanyName(String)}
   *   <li>{@link CreateGroup.Profile#setDisplayName(String)}
   *   <li>{@link CreateGroup.Profile#setEmail(String)}
   *   <li>{@link CreateGroup.Profile#setFunctions(Set)}
   *   <li>{@link CreateGroup.Profile#setIndustries(List)}
   *   <li>{@link CreateGroup.Profile#setInstruments(Set)}
   *   <li>{@link CreateGroup.Profile#setJob(CreateGroup.Job)}
   *   <li>{@link CreateGroup.Profile#setMarketCoverages(Set)}
   *   <li>{@link CreateGroup.Profile#setMobile(String)}
   *   <li>{@link CreateGroup.Profile#setResponsibilities(Set)}
   *   <li>{@link CreateGroup.Profile#toString()}
   *   <li>{@link CreateGroup.Profile#getAssetClasses()}
   *   <li>{@link CreateGroup.Profile#getCompanyName()}
   *   <li>{@link CreateGroup.Profile#getDisplayName()}
   *   <li>{@link CreateGroup.Profile#getEmail()}
   *   <li>{@link CreateGroup.Profile#getFunctions()}
   *   <li>{@link CreateGroup.Profile#getIndustries()}
   *   <li>{@link CreateGroup.Profile#getInstruments()}
   *   <li>{@link CreateGroup.Profile#getJob()}
   *   <li>{@link CreateGroup.Profile#getMarketCoverages()}
   *   <li>{@link CreateGroup.Profile#getMobile()}
   *   <li>{@link CreateGroup.Profile#getResponsibilities()}
   * </ul>
   */
  @Test
  void testProfileGettersAndSetters() {
    // Arrange and Act
    CreateGroup.Profile actualProfile = new CreateGroup.Profile();
    ArrayList<String> assetClasses = new ArrayList<>();
    actualProfile.setAssetClasses(assetClasses);
    actualProfile.setCompanyName("Company Name");
    actualProfile.setDisplayName("Display Name");
    actualProfile.setEmail("jane.doe@example.org");
    HashSet<String> functions = new HashSet<>();
    actualProfile.setFunctions(functions);
    ArrayList<String> industries = new ArrayList<>();
    actualProfile.setIndustries(industries);
    HashSet<String> instruments = new HashSet<>();
    actualProfile.setInstruments(instruments);
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");
    actualProfile.setJob(job);
    HashSet<String> marketCoverages = new HashSet<>();
    actualProfile.setMarketCoverages(marketCoverages);
    actualProfile.setMobile("Mobile");
    HashSet<String> responsibilities = new HashSet<>();
    actualProfile.setResponsibilities(responsibilities);
    String actualToStringResult = actualProfile.toString();
    List<String> actualAssetClasses = actualProfile.getAssetClasses();
    String actualCompanyName = actualProfile.getCompanyName();
    String actualDisplayName = actualProfile.getDisplayName();
    String actualEmail = actualProfile.getEmail();
    Set<String> actualFunctions = actualProfile.getFunctions();
    List<String> actualIndustries = actualProfile.getIndustries();
    Set<String> actualInstruments = actualProfile.getInstruments();
    CreateGroup.Job actualJob = actualProfile.getJob();
    Set<String> actualMarketCoverages = actualProfile.getMarketCoverages();
    String actualMobile = actualProfile.getMobile();
    Set<String> actualResponsibilities = actualProfile.getResponsibilities();

    // Assert that nothing has changed
    assertEquals("Company Name", actualCompanyName);
    assertEquals("CreateGroup.Profile(displayName=Display Name, companyName=Company Name, email=jane.doe@example.org,"
        + " mobile=Mobile, job=CreateGroup.Job(title=Dr, role=Role, department=Department, division=Division,"
        + " phone=6625550144, city=Oxford), industries=[], assetClasses=[], marketCoverages=[], responsibilities=[],"
        + " functions=[], instruments=[])", actualToStringResult);
    assertEquals("Display Name", actualDisplayName);
    assertEquals("Mobile", actualMobile);
    assertEquals("jane.doe@example.org", actualEmail);
    assertTrue(actualAssetClasses.isEmpty());
    assertTrue(actualIndustries.isEmpty());
    assertTrue(actualFunctions.isEmpty());
    assertTrue(actualInstruments.isEmpty());
    assertTrue(actualMarketCoverages.isEmpty());
    assertTrue(actualResponsibilities.isEmpty());
    assertSame(job, actualJob);
    assertSame(assetClasses, actualAssetClasses);
    assertSame(industries, actualIndustries);
    assertSame(functions, actualFunctions);
    assertSame(instruments, actualInstruments);
    assertSame(marketCoverages, actualMarketCoverages);
    assertSame(responsibilities, actualResponsibilities);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.VisibilityRestriction#equals(Object)}
   *   <li>{@link CreateGroup.VisibilityRestriction#hashCode()}
   * </ul>
   */
  @Test
  void testVisibilityRestrictionEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    visibilityRestriction.setTenantIds(new ArrayList<>());
    visibilityRestriction.setUserIds(new ArrayList<>());

    CreateGroup.VisibilityRestriction visibilityRestriction2 = new CreateGroup.VisibilityRestriction();
    visibilityRestriction2.setTenantIds(new ArrayList<>());
    visibilityRestriction2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(visibilityRestriction, visibilityRestriction2);
    int expectedHashCodeResult = visibilityRestriction.hashCode();
    assertEquals(expectedHashCodeResult, visibilityRestriction2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup.VisibilityRestriction#equals(Object)}
   *   <li>{@link CreateGroup.VisibilityRestriction#hashCode()}
   * </ul>
   */
  @Test
  void testVisibilityRestrictionEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    visibilityRestriction.setTenantIds(new ArrayList<>());
    visibilityRestriction.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(visibilityRestriction, visibilityRestriction);
    int expectedHashCodeResult = visibilityRestriction.hashCode();
    assertEquals(expectedHashCodeResult, visibilityRestriction.hashCode());
  }

  /**
   * Method under test: {@link CreateGroup.VisibilityRestriction#equals(Object)}
   */
  @Test
  void testVisibilityRestrictionEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ArrayList<Integer> tenantIds = new ArrayList<>();
    tenantIds.add(2);

    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    visibilityRestriction.setTenantIds(tenantIds);
    visibilityRestriction.setUserIds(new ArrayList<>());

    CreateGroup.VisibilityRestriction visibilityRestriction2 = new CreateGroup.VisibilityRestriction();
    visibilityRestriction2.setTenantIds(new ArrayList<>());
    visibilityRestriction2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(visibilityRestriction, visibilityRestriction2);
  }

  /**
   * Method under test: {@link CreateGroup.VisibilityRestriction#equals(Object)}
   */
  @Test
  void testVisibilityRestrictionEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ArrayList<Long> userIds = new ArrayList<>();
    userIds.add(1L);

    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    visibilityRestriction.setTenantIds(new ArrayList<>());
    visibilityRestriction.setUserIds(userIds);

    CreateGroup.VisibilityRestriction visibilityRestriction2 = new CreateGroup.VisibilityRestriction();
    visibilityRestriction2.setTenantIds(new ArrayList<>());
    visibilityRestriction2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(visibilityRestriction, visibilityRestriction2);
  }

  /**
   * Method under test: {@link CreateGroup.VisibilityRestriction#equals(Object)}
   */
  @Test
  void testVisibilityRestrictionEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    visibilityRestriction.setTenantIds(new ArrayList<>());
    visibilityRestriction.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(visibilityRestriction, null);
  }

  /**
   * Method under test: {@link CreateGroup.VisibilityRestriction#equals(Object)}
   */
  @Test
  void testVisibilityRestrictionEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    visibilityRestriction.setTenantIds(new ArrayList<>());
    visibilityRestriction.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(visibilityRestriction, "Different type to VisibilityRestriction");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link CreateGroup.VisibilityRestriction}
   *   <li>{@link CreateGroup.VisibilityRestriction#setTenantIds(List)}
   *   <li>{@link CreateGroup.VisibilityRestriction#setUserIds(List)}
   *   <li>{@link CreateGroup.VisibilityRestriction#toString()}
   *   <li>{@link CreateGroup.VisibilityRestriction#getTenantIds()}
   *   <li>{@link CreateGroup.VisibilityRestriction#getUserIds()}
   * </ul>
   */
  @Test
  void testVisibilityRestrictionGettersAndSetters() {
    // Arrange and Act
    CreateGroup.VisibilityRestriction actualVisibilityRestriction = new CreateGroup.VisibilityRestriction();
    ArrayList<Integer> tenantIds = new ArrayList<>();
    actualVisibilityRestriction.setTenantIds(tenantIds);
    ArrayList<Long> userIds = new ArrayList<>();
    actualVisibilityRestriction.setUserIds(userIds);
    String actualToStringResult = actualVisibilityRestriction.toString();
    List<Integer> actualTenantIds = actualVisibilityRestriction.getTenantIds();
    List<Long> actualUserIds = actualVisibilityRestriction.getUserIds();

    // Assert that nothing has changed
    assertEquals("CreateGroup.VisibilityRestriction(tenantIds=[], userIds=[])", actualToStringResult);
    assertTrue(actualTenantIds.isEmpty());
    assertTrue(actualUserIds.isEmpty());
    assertSame(tenantIds, actualTenantIds);
    assertSame(userIds, actualUserIds);
  }
}
