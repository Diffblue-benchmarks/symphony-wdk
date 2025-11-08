package com.symphony.bdk.workflow.swadl.v1.activity.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup.GroupMember;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup.ImplicitConnection;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup.InteractionTransfer;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup.Job;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup.Owner;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup.Profile;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup.VisibilityRestriction;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateGroupDiffblueTest {
  /**
   * Test {@link CreateGroup#equals(Object)}, and {@link CreateGroup#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup#equals(Object)}
   *   <li>{@link CreateGroup#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateGroup.equals(Object)", "int CreateGroup.hashCode()"})
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
   * Test {@link CreateGroup#equals(Object)}, and {@link CreateGroup#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup#equals(Object)}
   *   <li>{@link CreateGroup#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateGroup.equals(Object)", "int CreateGroup.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateGroup createGroup = new CreateGroup();

    // Act and Assert
    assertEquals(createGroup, createGroup);
    int expectedHashCodeResult = createGroup.hashCode();
    assertEquals(expectedHashCodeResult, createGroup.hashCode());
  }

  /**
   * Test {@link CreateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateGroup.equals(Object)", "int CreateGroup.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();

    // Act and Assert
    assertNotEquals(updateGroup, new CreateGroup());
  }

  /**
   * Test {@link CreateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateGroup.equals(Object)", "int CreateGroup.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateGroup createGroup = new CreateGroup();
    createGroup.add("SDL", "Value");

    // Act and Assert
    assertNotEquals(createGroup, new CreateGroup());
  }

  /**
   * Test {@link CreateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateGroup.equals(Object)", "int CreateGroup.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CreateGroup createGroup = new CreateGroup();

    // Act and Assert
    assertNotEquals(createGroup, new UpdateGroup());
  }

  /**
   * Test {@link CreateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateGroup.equals(Object)", "int CreateGroup.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
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
   * Test {@link CreateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateGroup.equals(Object)", "int CreateGroup.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateGroup(), null);
  }

  /**
   * Test {@link CreateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateGroup.equals(Object)", "int CreateGroup.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateGroup(), "Different type to CreateGroup");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CreateGroup#setImplicitConnection(ImplicitConnection)}
   *   <li>{@link CreateGroup#setInteractionTransfer(InteractionTransfer)}
   *   <li>{@link CreateGroup#setMembers(List)}
   *   <li>{@link CreateGroup#setName(String)}
   *   <li>{@link CreateGroup#setOwner(Owner)}
   *   <li>{@link CreateGroup#setProfile(Profile)}
   *   <li>{@link CreateGroup#setReferrer(String)}
   *   <li>{@link CreateGroup#setSubType(String)}
   *   <li>{@link CreateGroup#setType(String)}
   *   <li>{@link CreateGroup#setVisibilityRestriction(VisibilityRestriction)}
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ImplicitConnection CreateGroup.getImplicitConnection()",
      "InteractionTransfer CreateGroup.getInteractionTransfer()", "List CreateGroup.getMembers()",
      "String CreateGroup.getName()", "Owner CreateGroup.getOwner()", "Profile CreateGroup.getProfile()",
      "String CreateGroup.getReferrer()", "String CreateGroup.getSubType()", "String CreateGroup.getType()",
      "VisibilityRestriction CreateGroup.getVisibilityRestriction()",
      "void CreateGroup.setImplicitConnection(ImplicitConnection)",
      "void CreateGroup.setInteractionTransfer(InteractionTransfer)", "void CreateGroup.setMembers(List)",
      "void CreateGroup.setName(String)", "void CreateGroup.setOwner(Owner)", "void CreateGroup.setProfile(Profile)",
      "void CreateGroup.setReferrer(String)", "void CreateGroup.setSubType(String)", "void CreateGroup.setType(String)",
      "void CreateGroup.setVisibilityRestriction(VisibilityRestriction)", "String CreateGroup.toString()"})
  void testGettersAndSetters() {
    // Arrange
    CreateGroup createGroup = new CreateGroup();

    ImplicitConnection implicitConnection = new ImplicitConnection();
    implicitConnection.setTenantIds(new ArrayList<>());
    implicitConnection.setUserIds(new ArrayList<>());

    // Act
    createGroup.setImplicitConnection(implicitConnection);
    InteractionTransfer interactionTransfer = new InteractionTransfer();
    interactionTransfer.setTenantIds(new ArrayList<>());
    interactionTransfer.setUserIds(new ArrayList<>());
    createGroup.setInteractionTransfer(interactionTransfer);
    ArrayList<GroupMember> members = new ArrayList<>();
    createGroup.setMembers(members);
    createGroup.setName("Name");
    Owner owner = new Owner();
    owner.setId(1L);
    owner.setType("Type");
    createGroup.setOwner(owner);
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");
    Profile profile = new Profile();
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
    VisibilityRestriction visibilityRestriction = new VisibilityRestriction();
    visibilityRestriction.setTenantIds(new ArrayList<>());
    visibilityRestriction.setUserIds(new ArrayList<>());
    createGroup.setVisibilityRestriction(visibilityRestriction);
    String actualToStringResult = createGroup.toString();
    ImplicitConnection actualImplicitConnection = createGroup.getImplicitConnection();
    InteractionTransfer actualInteractionTransfer = createGroup.getInteractionTransfer();
    List<GroupMember> actualMembers = createGroup.getMembers();
    String actualName = createGroup.getName();
    Owner actualOwner = createGroup.getOwner();
    Profile actualProfile = createGroup.getProfile();
    String actualReferrer = createGroup.getReferrer();
    String actualSubType = createGroup.getSubType();
    String actualType = createGroup.getType();
    VisibilityRestriction actualVisibilityRestriction = createGroup.getVisibilityRestriction();

    // Assert
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
   * Test GroupMember {@link GroupMember#equals(Object)}, and {@link GroupMember#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GroupMember#equals(Object)}
   *   <li>{@link GroupMember#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test GroupMember equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GroupMember.equals(Object)", "int GroupMember.hashCode()"})
  void testGroupMemberEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GroupMember groupMember = new GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(1L);

    GroupMember groupMember2 = new GroupMember();
    groupMember2.setTenantId(1);
    groupMember2.setUserId(1L);

    // Act and Assert
    assertEquals(groupMember, groupMember2);
    int expectedHashCodeResult = groupMember.hashCode();
    assertEquals(expectedHashCodeResult, groupMember2.hashCode());
  }

  /**
   * Test GroupMember {@link GroupMember#equals(Object)}, and {@link GroupMember#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GroupMember#equals(Object)}
   *   <li>{@link GroupMember#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test GroupMember equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GroupMember.equals(Object)", "int GroupMember.hashCode()"})
  void testGroupMemberEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GroupMember groupMember = new GroupMember();
    groupMember.setTenantId(null);
    groupMember.setUserId(1L);

    GroupMember groupMember2 = new GroupMember();
    groupMember2.setTenantId(null);
    groupMember2.setUserId(1L);

    // Act and Assert
    assertEquals(groupMember, groupMember2);
    int expectedHashCodeResult = groupMember.hashCode();
    assertEquals(expectedHashCodeResult, groupMember2.hashCode());
  }

  /**
   * Test GroupMember {@link GroupMember#equals(Object)}, and {@link GroupMember#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GroupMember#equals(Object)}
   *   <li>{@link GroupMember#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test GroupMember equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GroupMember.equals(Object)", "int GroupMember.hashCode()"})
  void testGroupMemberEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    GroupMember groupMember = new GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(null);

    GroupMember groupMember2 = new GroupMember();
    groupMember2.setTenantId(1);
    groupMember2.setUserId(null);

    // Act and Assert
    assertEquals(groupMember, groupMember2);
    int expectedHashCodeResult = groupMember.hashCode();
    assertEquals(expectedHashCodeResult, groupMember2.hashCode());
  }

  /**
   * Test GroupMember {@link GroupMember#equals(Object)}, and {@link GroupMember#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GroupMember#equals(Object)}
   *   <li>{@link GroupMember#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test GroupMember equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GroupMember.equals(Object)", "int GroupMember.hashCode()"})
  void testGroupMemberEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GroupMember groupMember = new GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(1L);

    // Act and Assert
    assertEquals(groupMember, groupMember);
    int expectedHashCodeResult = groupMember.hashCode();
    assertEquals(expectedHashCodeResult, groupMember.hashCode());
  }

  /**
   * Test GroupMember {@link GroupMember#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GroupMember#equals(Object)}
   */
  @Test
  @DisplayName("Test GroupMember equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GroupMember.equals(Object)", "int GroupMember.hashCode()"})
  void testGroupMemberEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GroupMember groupMember = new GroupMember();
    groupMember.setTenantId(2);
    groupMember.setUserId(1L);

    GroupMember groupMember2 = new GroupMember();
    groupMember2.setTenantId(1);
    groupMember2.setUserId(1L);

    // Act and Assert
    assertNotEquals(groupMember, groupMember2);
  }

  /**
   * Test GroupMember {@link GroupMember#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GroupMember#equals(Object)}
   */
  @Test
  @DisplayName("Test GroupMember equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GroupMember.equals(Object)", "int GroupMember.hashCode()"})
  void testGroupMemberEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GroupMember groupMember = new GroupMember();
    groupMember.setTenantId(null);
    groupMember.setUserId(1L);

    GroupMember groupMember2 = new GroupMember();
    groupMember2.setTenantId(1);
    groupMember2.setUserId(1L);

    // Act and Assert
    assertNotEquals(groupMember, groupMember2);
  }

  /**
   * Test GroupMember {@link GroupMember#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GroupMember#equals(Object)}
   */
  @Test
  @DisplayName("Test GroupMember equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GroupMember.equals(Object)", "int GroupMember.hashCode()"})
  void testGroupMemberEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GroupMember groupMember = new GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(2L);

    GroupMember groupMember2 = new GroupMember();
    groupMember2.setTenantId(1);
    groupMember2.setUserId(1L);

    // Act and Assert
    assertNotEquals(groupMember, groupMember2);
  }

  /**
   * Test GroupMember {@link GroupMember#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GroupMember#equals(Object)}
   */
  @Test
  @DisplayName("Test GroupMember equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GroupMember.equals(Object)", "int GroupMember.hashCode()"})
  void testGroupMemberEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GroupMember groupMember = new GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(null);

    GroupMember groupMember2 = new GroupMember();
    groupMember2.setTenantId(1);
    groupMember2.setUserId(1L);

    // Act and Assert
    assertNotEquals(groupMember, groupMember2);
  }

  /**
   * Test GroupMember {@link GroupMember#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GroupMember#equals(Object)}
   */
  @Test
  @DisplayName("Test GroupMember equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GroupMember.equals(Object)", "int GroupMember.hashCode()"})
  void testGroupMemberEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    GroupMember groupMember = new GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(1L);

    // Act and Assert
    assertNotEquals(groupMember, null);
  }

  /**
   * Test GroupMember {@link GroupMember#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GroupMember#equals(Object)}
   */
  @Test
  @DisplayName("Test GroupMember equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GroupMember.equals(Object)", "int GroupMember.hashCode()"})
  void testGroupMemberEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    GroupMember groupMember = new GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(1L);

    // Act and Assert
    assertNotEquals(groupMember, "Different type to GroupMember");
  }

  /**
   * Test GroupMember getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GroupMember}
   *   <li>{@link GroupMember#setTenantId(Integer)}
   *   <li>{@link GroupMember#setUserId(Long)}
   *   <li>{@link GroupMember#toString()}
   *   <li>{@link GroupMember#getTenantId()}
   *   <li>{@link GroupMember#getUserId()}
   * </ul>
   */
  @Test
  @DisplayName("Test GroupMember getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GroupMember.<init>()", "Integer GroupMember.getTenantId()", "Long GroupMember.getUserId()",
      "void GroupMember.setTenantId(Integer)", "void GroupMember.setUserId(Long)", "String GroupMember.toString()"})
  void testGroupMemberGettersAndSetters() {
    // Arrange and Act
    GroupMember actualGroupMember = new GroupMember();
    actualGroupMember.setTenantId(1);
    actualGroupMember.setUserId(1L);
    String actualToStringResult = actualGroupMember.toString();
    Integer actualTenantId = actualGroupMember.getTenantId();
    Long actualUserId = actualGroupMember.getUserId();

    // Assert
    assertEquals("CreateGroup.GroupMember(userId=1, tenantId=1)", actualToStringResult);
    assertEquals(1, actualTenantId.intValue());
    assertEquals(1L, actualUserId.longValue());
  }

  /**
   * Test ImplicitConnection {@link ImplicitConnection#equals(Object)}, and {@link ImplicitConnection#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ImplicitConnection#equals(Object)}
   *   <li>{@link ImplicitConnection#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test ImplicitConnection equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ImplicitConnection.equals(Object)", "int ImplicitConnection.hashCode()"})
  void testImplicitConnectionEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ImplicitConnection implicitConnection = new ImplicitConnection();
    implicitConnection.setTenantIds(new ArrayList<>());
    implicitConnection.setUserIds(new ArrayList<>());

    ImplicitConnection implicitConnection2 = new ImplicitConnection();
    implicitConnection2.setTenantIds(new ArrayList<>());
    implicitConnection2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(implicitConnection, implicitConnection2);
    int expectedHashCodeResult = implicitConnection.hashCode();
    assertEquals(expectedHashCodeResult, implicitConnection2.hashCode());
  }

  /**
   * Test ImplicitConnection {@link ImplicitConnection#equals(Object)}, and {@link ImplicitConnection#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ImplicitConnection#equals(Object)}
   *   <li>{@link ImplicitConnection#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test ImplicitConnection equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ImplicitConnection.equals(Object)", "int ImplicitConnection.hashCode()"})
  void testImplicitConnectionEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ImplicitConnection implicitConnection = new ImplicitConnection();
    implicitConnection.setTenantIds(new ArrayList<>());
    implicitConnection.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(implicitConnection, implicitConnection);
    int expectedHashCodeResult = implicitConnection.hashCode();
    assertEquals(expectedHashCodeResult, implicitConnection.hashCode());
  }

  /**
   * Test ImplicitConnection {@link ImplicitConnection#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ImplicitConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test ImplicitConnection equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ImplicitConnection.equals(Object)", "int ImplicitConnection.hashCode()"})
  void testImplicitConnectionEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ArrayList<Integer> tenantIds = new ArrayList<>();
    tenantIds.add(2);

    ImplicitConnection implicitConnection = new ImplicitConnection();
    implicitConnection.setTenantIds(tenantIds);
    implicitConnection.setUserIds(new ArrayList<>());

    ImplicitConnection implicitConnection2 = new ImplicitConnection();
    implicitConnection2.setTenantIds(new ArrayList<>());
    implicitConnection2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(implicitConnection, implicitConnection2);
  }

  /**
   * Test ImplicitConnection {@link ImplicitConnection#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ImplicitConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test ImplicitConnection equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ImplicitConnection.equals(Object)", "int ImplicitConnection.hashCode()"})
  void testImplicitConnectionEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ArrayList<Long> userIds = new ArrayList<>();
    userIds.add(1L);

    ImplicitConnection implicitConnection = new ImplicitConnection();
    implicitConnection.setTenantIds(new ArrayList<>());
    implicitConnection.setUserIds(userIds);

    ImplicitConnection implicitConnection2 = new ImplicitConnection();
    implicitConnection2.setTenantIds(new ArrayList<>());
    implicitConnection2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(implicitConnection, implicitConnection2);
  }

  /**
   * Test ImplicitConnection {@link ImplicitConnection#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ImplicitConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test ImplicitConnection equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ImplicitConnection.equals(Object)", "int ImplicitConnection.hashCode()"})
  void testImplicitConnectionEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ImplicitConnection implicitConnection = new ImplicitConnection();
    implicitConnection.setTenantIds(new ArrayList<>());
    implicitConnection.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(implicitConnection, null);
  }

  /**
   * Test ImplicitConnection {@link ImplicitConnection#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ImplicitConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test ImplicitConnection equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ImplicitConnection.equals(Object)", "int ImplicitConnection.hashCode()"})
  void testImplicitConnectionEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ImplicitConnection implicitConnection = new ImplicitConnection();
    implicitConnection.setTenantIds(new ArrayList<>());
    implicitConnection.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(implicitConnection, "Different type to ImplicitConnection");
  }

  /**
   * Test ImplicitConnection getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ImplicitConnection}
   *   <li>{@link ImplicitConnection#setTenantIds(List)}
   *   <li>{@link ImplicitConnection#setUserIds(List)}
   *   <li>{@link ImplicitConnection#toString()}
   *   <li>{@link ImplicitConnection#getTenantIds()}
   *   <li>{@link ImplicitConnection#getUserIds()}
   * </ul>
   */
  @Test
  @DisplayName("Test ImplicitConnection getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ImplicitConnection.<init>()", "List ImplicitConnection.getTenantIds()",
      "List ImplicitConnection.getUserIds()", "void ImplicitConnection.setTenantIds(List)",
      "void ImplicitConnection.setUserIds(List)", "String ImplicitConnection.toString()"})
  void testImplicitConnectionGettersAndSetters() {
    // Arrange and Act
    ImplicitConnection actualImplicitConnection = new ImplicitConnection();
    ArrayList<Integer> tenantIds = new ArrayList<>();
    actualImplicitConnection.setTenantIds(tenantIds);
    ArrayList<Long> userIds = new ArrayList<>();
    actualImplicitConnection.setUserIds(userIds);
    String actualToStringResult = actualImplicitConnection.toString();
    List<Integer> actualTenantIds = actualImplicitConnection.getTenantIds();
    List<Long> actualUserIds = actualImplicitConnection.getUserIds();

    // Assert
    assertEquals("CreateGroup.ImplicitConnection(tenantIds=[], userIds=[])", actualToStringResult);
    assertTrue(actualTenantIds.isEmpty());
    assertTrue(actualUserIds.isEmpty());
    assertSame(tenantIds, actualTenantIds);
    assertSame(userIds, actualUserIds);
  }

  /**
   * Test InteractionTransfer {@link InteractionTransfer#equals(Object)}, and {@link InteractionTransfer#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InteractionTransfer#equals(Object)}
   *   <li>{@link InteractionTransfer#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test InteractionTransfer equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InteractionTransfer.equals(Object)", "int InteractionTransfer.hashCode()"})
  void testInteractionTransferEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    InteractionTransfer interactionTransfer = new InteractionTransfer();
    interactionTransfer.setTenantIds(new ArrayList<>());
    interactionTransfer.setUserIds(new ArrayList<>());

    InteractionTransfer interactionTransfer2 = new InteractionTransfer();
    interactionTransfer2.setTenantIds(new ArrayList<>());
    interactionTransfer2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(interactionTransfer, interactionTransfer2);
    int expectedHashCodeResult = interactionTransfer.hashCode();
    assertEquals(expectedHashCodeResult, interactionTransfer2.hashCode());
  }

  /**
   * Test InteractionTransfer {@link InteractionTransfer#equals(Object)}, and {@link InteractionTransfer#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InteractionTransfer#equals(Object)}
   *   <li>{@link InteractionTransfer#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test InteractionTransfer equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InteractionTransfer.equals(Object)", "int InteractionTransfer.hashCode()"})
  void testInteractionTransferEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InteractionTransfer interactionTransfer = new InteractionTransfer();
    interactionTransfer.setTenantIds(new ArrayList<>());
    interactionTransfer.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(interactionTransfer, interactionTransfer);
    int expectedHashCodeResult = interactionTransfer.hashCode();
    assertEquals(expectedHashCodeResult, interactionTransfer.hashCode());
  }

  /**
   * Test InteractionTransfer {@link InteractionTransfer#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InteractionTransfer#equals(Object)}
   */
  @Test
  @DisplayName("Test InteractionTransfer equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InteractionTransfer.equals(Object)", "int InteractionTransfer.hashCode()"})
  void testInteractionTransferEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ArrayList<Integer> tenantIds = new ArrayList<>();
    tenantIds.add(2);

    InteractionTransfer interactionTransfer = new InteractionTransfer();
    interactionTransfer.setTenantIds(tenantIds);
    interactionTransfer.setUserIds(new ArrayList<>());

    InteractionTransfer interactionTransfer2 = new InteractionTransfer();
    interactionTransfer2.setTenantIds(new ArrayList<>());
    interactionTransfer2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(interactionTransfer, interactionTransfer2);
  }

  /**
   * Test InteractionTransfer {@link InteractionTransfer#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InteractionTransfer#equals(Object)}
   */
  @Test
  @DisplayName("Test InteractionTransfer equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InteractionTransfer.equals(Object)", "int InteractionTransfer.hashCode()"})
  void testInteractionTransferEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ArrayList<Long> userIds = new ArrayList<>();
    userIds.add(1L);

    InteractionTransfer interactionTransfer = new InteractionTransfer();
    interactionTransfer.setTenantIds(new ArrayList<>());
    interactionTransfer.setUserIds(userIds);

    InteractionTransfer interactionTransfer2 = new InteractionTransfer();
    interactionTransfer2.setTenantIds(new ArrayList<>());
    interactionTransfer2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(interactionTransfer, interactionTransfer2);
  }

  /**
   * Test InteractionTransfer {@link InteractionTransfer#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InteractionTransfer#equals(Object)}
   */
  @Test
  @DisplayName("Test InteractionTransfer equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InteractionTransfer.equals(Object)", "int InteractionTransfer.hashCode()"})
  void testInteractionTransferEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    InteractionTransfer interactionTransfer = new InteractionTransfer();
    interactionTransfer.setTenantIds(new ArrayList<>());
    interactionTransfer.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(interactionTransfer, null);
  }

  /**
   * Test InteractionTransfer {@link InteractionTransfer#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InteractionTransfer#equals(Object)}
   */
  @Test
  @DisplayName("Test InteractionTransfer equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InteractionTransfer.equals(Object)", "int InteractionTransfer.hashCode()"})
  void testInteractionTransferEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    InteractionTransfer interactionTransfer = new InteractionTransfer();
    interactionTransfer.setTenantIds(new ArrayList<>());
    interactionTransfer.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(interactionTransfer, "Different type to InteractionTransfer");
  }

  /**
   * Test InteractionTransfer getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link InteractionTransfer}
   *   <li>{@link InteractionTransfer#setTenantIds(List)}
   *   <li>{@link InteractionTransfer#setUserIds(List)}
   *   <li>{@link InteractionTransfer#toString()}
   *   <li>{@link InteractionTransfer#getTenantIds()}
   *   <li>{@link InteractionTransfer#getUserIds()}
   * </ul>
   */
  @Test
  @DisplayName("Test InteractionTransfer getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InteractionTransfer.<init>()", "List InteractionTransfer.getTenantIds()",
      "List InteractionTransfer.getUserIds()", "void InteractionTransfer.setTenantIds(List)",
      "void InteractionTransfer.setUserIds(List)", "String InteractionTransfer.toString()"})
  void testInteractionTransferGettersAndSetters() {
    // Arrange and Act
    InteractionTransfer actualInteractionTransfer = new InteractionTransfer();
    ArrayList<Integer> tenantIds = new ArrayList<>();
    actualInteractionTransfer.setTenantIds(tenantIds);
    ArrayList<Long> userIds = new ArrayList<>();
    actualInteractionTransfer.setUserIds(userIds);
    String actualToStringResult = actualInteractionTransfer.toString();
    List<Integer> actualTenantIds = actualInteractionTransfer.getTenantIds();
    List<Long> actualUserIds = actualInteractionTransfer.getUserIds();

    // Assert
    assertEquals("CreateGroup.InteractionTransfer(tenantIds=[], userIds=[])", actualToStringResult);
    assertTrue(actualTenantIds.isEmpty());
    assertTrue(actualUserIds.isEmpty());
    assertSame(tenantIds, actualTenantIds);
    assertSame(userIds, actualUserIds);
  }

  /**
   * Test Job {@link Job#equals(Object)}, and {@link Job#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Job#equals(Object)}
   *   <li>{@link Job#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Job equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Job job2 = new Job();
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
   * Test Job {@link Job#equals(Object)}, and {@link Job#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Job#equals(Object)}
   *   <li>{@link Job#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Job equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Job job = new Job();
    job.setCity(null);
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Job job2 = new Job();
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
   * Test Job {@link Job#equals(Object)}, and {@link Job#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Job#equals(Object)}
   *   <li>{@link Job#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Job equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment(null);
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Job job2 = new Job();
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
   * Test Job {@link Job#equals(Object)}, and {@link Job#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Job#equals(Object)}
   *   <li>{@link Job#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Job equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Job job = new Job();
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
   * Test Job {@link Job#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Job#equals(Object)}
   */
  @Test
  @DisplayName("Test Job equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Job job = new Job();
    job.setCity("London");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Job job2 = new Job();
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
   * Test Job {@link Job#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Job#equals(Object)}
   */
  @Test
  @DisplayName("Test Job equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Job job = new Job();
    job.setCity(null);
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Job job2 = new Job();
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
   * Test Job {@link Job#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Job#equals(Object)}
   */
  @Test
  @DisplayName("Test Job equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Dr");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Job job2 = new Job();
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
   * Test Job {@link Job#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Job#equals(Object)}
   */
  @Test
  @DisplayName("Test Job equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment(null);
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Job job2 = new Job();
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
   * Test Job {@link Job#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Job#equals(Object)}
   */
  @Test
  @DisplayName("Test Job equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Dr");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Job job2 = new Job();
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
   * Test Job {@link Job#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Job#equals(Object)}
   */
  @Test
  @DisplayName("Test Job equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision(null);
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Job job2 = new Job();
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
   * Test Job {@link Job#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Job#equals(Object)}
   */
  @Test
  @DisplayName("Test Job equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("8605550118");
    job.setRole("Role");
    job.setTitle("Dr");

    Job job2 = new Job();
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
   * Test Job {@link Job#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Job#equals(Object)}
   */
  @Test
  @DisplayName("Test Job equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone(null);
    job.setRole("Role");
    job.setTitle("Dr");

    Job job2 = new Job();
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
   * Test Job {@link Job#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Job#equals(Object)}
   */
  @Test
  @DisplayName("Test Job equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Dr");
    job.setTitle("Dr");

    Job job2 = new Job();
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
   * Test Job {@link Job#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Job#equals(Object)}
   */
  @Test
  @DisplayName("Test Job equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole(null);
    job.setTitle("Dr");

    Job job2 = new Job();
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
   * Test Job {@link Job#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Job#equals(Object)}
   */
  @Test
  @DisplayName("Test Job equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Mr");

    Job job2 = new Job();
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
   * Test Job {@link Job#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Job#equals(Object)}
   */
  @Test
  @DisplayName("Test Job equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle(null);

    Job job2 = new Job();
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
   * Test Job {@link Job#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Job#equals(Object)}
   */
  @Test
  @DisplayName("Test Job equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Job job = new Job();
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
   * Test Job {@link Job#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Job#equals(Object)}
   */
  @Test
  @DisplayName("Test Job equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Job.equals(Object)", "int Job.hashCode()"})
  void testJobEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Job job = new Job();
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
   * Test Job getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Job}
   *   <li>{@link Job#setCity(String)}
   *   <li>{@link Job#setDepartment(String)}
   *   <li>{@link Job#setDivision(String)}
   *   <li>{@link Job#setPhone(String)}
   *   <li>{@link Job#setRole(String)}
   *   <li>{@link Job#setTitle(String)}
   *   <li>{@link Job#toString()}
   *   <li>{@link Job#getCity()}
   *   <li>{@link Job#getDepartment()}
   *   <li>{@link Job#getDivision()}
   *   <li>{@link Job#getPhone()}
   *   <li>{@link Job#getRole()}
   *   <li>{@link Job#getTitle()}
   * </ul>
   */
  @Test
  @DisplayName("Test Job getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Job.<init>()", "String Job.getCity()", "String Job.getDepartment()",
      "String Job.getDivision()", "String Job.getPhone()", "String Job.getRole()", "String Job.getTitle()",
      "void Job.setCity(String)", "void Job.setDepartment(String)", "void Job.setDivision(String)",
      "void Job.setPhone(String)", "void Job.setRole(String)", "void Job.setTitle(String)", "String Job.toString()"})
  void testJobGettersAndSetters() {
    // Arrange and Act
    Job actualJob = new Job();
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

    // Assert
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
   * Test new {@link CreateGroup} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link CreateGroup}
   */
  @Test
  @DisplayName("Test new CreateGroup (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CreateGroup.<init>()"})
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
    assertNull(actualCreateGroup.getName());
    assertNull(actualCreateGroup.getReferrer());
    assertNull(actualCreateGroup.getSubType());
    assertTrue(actualCreateGroup.getMembers().isEmpty());
    assertTrue(actualCreateGroup.getVariableProperties().isEmpty());
  }

  /**
   * Test Owner {@link Owner#equals(Object)}, and {@link Owner#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Owner#equals(Object)}
   *   <li>{@link Owner#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Owner equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Owner.equals(Object)", "int Owner.hashCode()"})
  void testOwnerEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Owner owner = new Owner();
    owner.setId(1L);
    owner.setType("Type");

    Owner owner2 = new Owner();
    owner2.setId(1L);
    owner2.setType("Type");

    // Act and Assert
    assertEquals(owner, owner2);
    int expectedHashCodeResult = owner.hashCode();
    assertEquals(expectedHashCodeResult, owner2.hashCode());
  }

  /**
   * Test Owner {@link Owner#equals(Object)}, and {@link Owner#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Owner#equals(Object)}
   *   <li>{@link Owner#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Owner equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Owner.equals(Object)", "int Owner.hashCode()"})
  void testOwnerEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Owner owner = new Owner();
    owner.setId(null);
    owner.setType("Type");

    Owner owner2 = new Owner();
    owner2.setId(null);
    owner2.setType("Type");

    // Act and Assert
    assertEquals(owner, owner2);
    int expectedHashCodeResult = owner.hashCode();
    assertEquals(expectedHashCodeResult, owner2.hashCode());
  }

  /**
   * Test Owner {@link Owner#equals(Object)}, and {@link Owner#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Owner#equals(Object)}
   *   <li>{@link Owner#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Owner equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Owner.equals(Object)", "int Owner.hashCode()"})
  void testOwnerEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    Owner owner = new Owner();
    owner.setId(1L);
    owner.setType(null);

    Owner owner2 = new Owner();
    owner2.setId(1L);
    owner2.setType(null);

    // Act and Assert
    assertEquals(owner, owner2);
    int expectedHashCodeResult = owner.hashCode();
    assertEquals(expectedHashCodeResult, owner2.hashCode());
  }

  /**
   * Test Owner {@link Owner#equals(Object)}, and {@link Owner#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Owner#equals(Object)}
   *   <li>{@link Owner#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Owner equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Owner.equals(Object)", "int Owner.hashCode()"})
  void testOwnerEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Owner owner = new Owner();
    owner.setId(1L);
    owner.setType("Type");

    // Act and Assert
    assertEquals(owner, owner);
    int expectedHashCodeResult = owner.hashCode();
    assertEquals(expectedHashCodeResult, owner.hashCode());
  }

  /**
   * Test Owner {@link Owner#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#equals(Object)}
   */
  @Test
  @DisplayName("Test Owner equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Owner.equals(Object)", "int Owner.hashCode()"})
  void testOwnerEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Owner owner = new Owner();
    owner.setId(2L);
    owner.setType("Type");

    Owner owner2 = new Owner();
    owner2.setId(1L);
    owner2.setType("Type");

    // Act and Assert
    assertNotEquals(owner, owner2);
  }

  /**
   * Test Owner {@link Owner#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#equals(Object)}
   */
  @Test
  @DisplayName("Test Owner equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Owner.equals(Object)", "int Owner.hashCode()"})
  void testOwnerEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Owner owner = new Owner();
    owner.setId(null);
    owner.setType("Type");

    Owner owner2 = new Owner();
    owner2.setId(1L);
    owner2.setType("Type");

    // Act and Assert
    assertNotEquals(owner, owner2);
  }

  /**
   * Test Owner {@link Owner#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#equals(Object)}
   */
  @Test
  @DisplayName("Test Owner equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Owner.equals(Object)", "int Owner.hashCode()"})
  void testOwnerEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Owner owner = new Owner();
    owner.setId(1L);
    owner.setType(null);

    Owner owner2 = new Owner();
    owner2.setId(1L);
    owner2.setType("Type");

    // Act and Assert
    assertNotEquals(owner, owner2);
  }

  /**
   * Test Owner {@link Owner#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#equals(Object)}
   */
  @Test
  @DisplayName("Test Owner equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Owner.equals(Object)", "int Owner.hashCode()"})
  void testOwnerEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Owner owner = new Owner();
    owner.setId(1L);
    owner.setType("com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup$Owner");

    Owner owner2 = new Owner();
    owner2.setId(1L);
    owner2.setType("Type");

    // Act and Assert
    assertNotEquals(owner, owner2);
  }

  /**
   * Test Owner {@link Owner#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#equals(Object)}
   */
  @Test
  @DisplayName("Test Owner equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Owner.equals(Object)", "int Owner.hashCode()"})
  void testOwnerEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Owner owner = new Owner();
    owner.setId(1L);
    owner.setType("Type");

    // Act and Assert
    assertNotEquals(owner, null);
  }

  /**
   * Test Owner {@link Owner#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#equals(Object)}
   */
  @Test
  @DisplayName("Test Owner equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Owner.equals(Object)", "int Owner.hashCode()"})
  void testOwnerEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Owner owner = new Owner();
    owner.setId(1L);
    owner.setType("Type");

    // Act and Assert
    assertNotEquals(owner, "Different type to Owner");
  }

  /**
   * Test Owner getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Owner}
   *   <li>{@link Owner#setId(Long)}
   *   <li>{@link Owner#setType(String)}
   *   <li>{@link Owner#toString()}
   *   <li>{@link Owner#getId()}
   *   <li>{@link Owner#getType()}
   * </ul>
   */
  @Test
  @DisplayName("Test Owner getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Owner.<init>()", "Long Owner.getId()", "String Owner.getType()", "void Owner.setId(Long)",
      "void Owner.setType(String)", "String Owner.toString()"})
  void testOwnerGettersAndSetters() {
    // Arrange and Act
    Owner actualOwner = new Owner();
    actualOwner.setId(1L);
    actualOwner.setType("Type");
    String actualToStringResult = actualOwner.toString();
    Long actualId = actualOwner.getId();

    // Assert
    assertEquals("CreateGroup.Owner(id=1, type=Type)", actualToStringResult);
    assertEquals("Type", actualOwner.getType());
    assertEquals(1L, actualId.longValue());
  }

  /**
   * Test Profile {@link Profile#equals(Object)}, and {@link Profile#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Profile#equals(Object)}
   *   <li>{@link Profile#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Profile equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Profile profile = new Profile();
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

    Job job2 = new Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    Profile profile2 = new Profile();
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
   * Test Profile {@link Profile#equals(Object)}, and {@link Profile#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Profile#equals(Object)}
   *   <li>{@link Profile#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Profile equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Profile profile = new Profile();
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
   * Test Profile {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test Profile equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ArrayList<String> assetClasses = new ArrayList<>();
    assetClasses.add("Display Name");

    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Profile profile = new Profile();
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

    Job job2 = new Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    Profile profile2 = new Profile();
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
   * Test Profile {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test Profile equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Profile profile = new Profile();
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

    Job job2 = new Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    Profile profile2 = new Profile();
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
   * Test Profile {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test Profile equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Profile profile = new Profile();
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

    Job job2 = new Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    Profile profile2 = new Profile();
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
   * Test Profile {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test Profile equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Profile profile = new Profile();
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

    Job job2 = new Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    Profile profile2 = new Profile();
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
   * Test Profile {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test Profile equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Profile profile = new Profile();
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

    Job job2 = new Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    Profile profile2 = new Profile();
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
   * Test Profile {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test Profile equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Profile profile = new Profile();
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

    Job job2 = new Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    Profile profile2 = new Profile();
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
   * Test Profile {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test Profile equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Profile profile = new Profile();
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

    Job job2 = new Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    Profile profile2 = new Profile();
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
   * Test Profile {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test Profile equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    HashSet<String> functions = new HashSet<>();
    functions.add("Display Name");

    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Profile profile = new Profile();
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

    Job job2 = new Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    Profile profile2 = new Profile();
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
   * Test Profile {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test Profile equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    ArrayList<String> industries = new ArrayList<>();
    industries.add("Display Name");

    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Profile profile = new Profile();
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

    Job job2 = new Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    Profile profile2 = new Profile();
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
   * Test Profile {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test Profile equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    HashSet<String> instruments = new HashSet<>();
    instruments.add("Display Name");

    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Profile profile = new Profile();
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

    Job job2 = new Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    Profile profile2 = new Profile();
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
   * Test Profile {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test Profile equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    Job job = mock(Job.class);
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

    Profile profile = new Profile();
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

    Job job2 = new Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    Profile profile2 = new Profile();
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
   * Test Profile {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test Profile equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    Job job = mock(Job.class);
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

    Profile profile = new Profile();
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

    Job job2 = new Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    Profile profile2 = new Profile();
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
   * Test Profile {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test Profile equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    Job job = mock(Job.class);
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

    Profile profile = new Profile();
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

    Job job2 = new Job();
    job2.setCity("Oxford");
    job2.setDepartment("Department");
    job2.setDivision("Division");
    job2.setPhone("6625550144");
    job2.setRole("Role");
    job2.setTitle("Dr");

    Profile profile2 = new Profile();
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
   * Test Profile {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test Profile equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Profile profile = new Profile();
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
   * Test Profile {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test Profile equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testProfileEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Job job = new Job();
    job.setCity("Oxford");
    job.setDepartment("Department");
    job.setDivision("Division");
    job.setPhone("6625550144");
    job.setRole("Role");
    job.setTitle("Dr");

    Profile profile = new Profile();
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
   * Test Profile getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Profile}
   *   <li>{@link Profile#setAssetClasses(List)}
   *   <li>{@link Profile#setCompanyName(String)}
   *   <li>{@link Profile#setDisplayName(String)}
   *   <li>{@link Profile#setEmail(String)}
   *   <li>{@link Profile#setFunctions(Set)}
   *   <li>{@link Profile#setIndustries(List)}
   *   <li>{@link Profile#setInstruments(Set)}
   *   <li>{@link Profile#setJob(Job)}
   *   <li>{@link Profile#setMarketCoverages(Set)}
   *   <li>{@link Profile#setMobile(String)}
   *   <li>{@link Profile#setResponsibilities(Set)}
   *   <li>{@link Profile#toString()}
   *   <li>{@link Profile#getAssetClasses()}
   *   <li>{@link Profile#getCompanyName()}
   *   <li>{@link Profile#getDisplayName()}
   *   <li>{@link Profile#getEmail()}
   *   <li>{@link Profile#getFunctions()}
   *   <li>{@link Profile#getIndustries()}
   *   <li>{@link Profile#getInstruments()}
   *   <li>{@link Profile#getJob()}
   *   <li>{@link Profile#getMarketCoverages()}
   *   <li>{@link Profile#getMobile()}
   *   <li>{@link Profile#getResponsibilities()}
   * </ul>
   */
  @Test
  @DisplayName("Test Profile getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Profile.<init>()", "List Profile.getAssetClasses()", "String Profile.getCompanyName()",
      "String Profile.getDisplayName()", "String Profile.getEmail()", "Set Profile.getFunctions()",
      "List Profile.getIndustries()", "Set Profile.getInstruments()", "Job Profile.getJob()",
      "Set Profile.getMarketCoverages()", "String Profile.getMobile()", "Set Profile.getResponsibilities()",
      "void Profile.setAssetClasses(List)", "void Profile.setCompanyName(String)",
      "void Profile.setDisplayName(String)", "void Profile.setEmail(String)", "void Profile.setFunctions(Set)",
      "void Profile.setIndustries(List)", "void Profile.setInstruments(Set)", "void Profile.setJob(Job)",
      "void Profile.setMarketCoverages(Set)", "void Profile.setMobile(String)", "void Profile.setResponsibilities(Set)",
      "String Profile.toString()"})
  void testProfileGettersAndSetters() {
    // Arrange and Act
    Profile actualProfile = new Profile();
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
    Job job = new Job();
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
    Job actualJob = actualProfile.getJob();
    Set<String> actualMarketCoverages = actualProfile.getMarketCoverages();
    String actualMobile = actualProfile.getMobile();
    Set<String> actualResponsibilities = actualProfile.getResponsibilities();

    // Assert
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
   * Test VisibilityRestriction {@link VisibilityRestriction#equals(Object)}, and {@link VisibilityRestriction#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VisibilityRestriction#equals(Object)}
   *   <li>{@link VisibilityRestriction#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test VisibilityRestriction equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VisibilityRestriction.equals(Object)", "int VisibilityRestriction.hashCode()"})
  void testVisibilityRestrictionEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    VisibilityRestriction visibilityRestriction = new VisibilityRestriction();
    visibilityRestriction.setTenantIds(new ArrayList<>());
    visibilityRestriction.setUserIds(new ArrayList<>());

    VisibilityRestriction visibilityRestriction2 = new VisibilityRestriction();
    visibilityRestriction2.setTenantIds(new ArrayList<>());
    visibilityRestriction2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(visibilityRestriction, visibilityRestriction2);
    int expectedHashCodeResult = visibilityRestriction.hashCode();
    assertEquals(expectedHashCodeResult, visibilityRestriction2.hashCode());
  }

  /**
   * Test VisibilityRestriction {@link VisibilityRestriction#equals(Object)}, and {@link VisibilityRestriction#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VisibilityRestriction#equals(Object)}
   *   <li>{@link VisibilityRestriction#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test VisibilityRestriction equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VisibilityRestriction.equals(Object)", "int VisibilityRestriction.hashCode()"})
  void testVisibilityRestrictionEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    VisibilityRestriction visibilityRestriction = new VisibilityRestriction();
    visibilityRestriction.setTenantIds(new ArrayList<>());
    visibilityRestriction.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(visibilityRestriction, visibilityRestriction);
    int expectedHashCodeResult = visibilityRestriction.hashCode();
    assertEquals(expectedHashCodeResult, visibilityRestriction.hashCode());
  }

  /**
   * Test VisibilityRestriction {@link VisibilityRestriction#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VisibilityRestriction#equals(Object)}
   */
  @Test
  @DisplayName("Test VisibilityRestriction equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VisibilityRestriction.equals(Object)", "int VisibilityRestriction.hashCode()"})
  void testVisibilityRestrictionEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ArrayList<Integer> tenantIds = new ArrayList<>();
    tenantIds.add(2);

    VisibilityRestriction visibilityRestriction = new VisibilityRestriction();
    visibilityRestriction.setTenantIds(tenantIds);
    visibilityRestriction.setUserIds(new ArrayList<>());

    VisibilityRestriction visibilityRestriction2 = new VisibilityRestriction();
    visibilityRestriction2.setTenantIds(new ArrayList<>());
    visibilityRestriction2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(visibilityRestriction, visibilityRestriction2);
  }

  /**
   * Test VisibilityRestriction {@link VisibilityRestriction#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VisibilityRestriction#equals(Object)}
   */
  @Test
  @DisplayName("Test VisibilityRestriction equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VisibilityRestriction.equals(Object)", "int VisibilityRestriction.hashCode()"})
  void testVisibilityRestrictionEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ArrayList<Long> userIds = new ArrayList<>();
    userIds.add(1L);

    VisibilityRestriction visibilityRestriction = new VisibilityRestriction();
    visibilityRestriction.setTenantIds(new ArrayList<>());
    visibilityRestriction.setUserIds(userIds);

    VisibilityRestriction visibilityRestriction2 = new VisibilityRestriction();
    visibilityRestriction2.setTenantIds(new ArrayList<>());
    visibilityRestriction2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(visibilityRestriction, visibilityRestriction2);
  }

  /**
   * Test VisibilityRestriction {@link VisibilityRestriction#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VisibilityRestriction#equals(Object)}
   */
  @Test
  @DisplayName("Test VisibilityRestriction equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VisibilityRestriction.equals(Object)", "int VisibilityRestriction.hashCode()"})
  void testVisibilityRestrictionEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    VisibilityRestriction visibilityRestriction = new VisibilityRestriction();
    visibilityRestriction.setTenantIds(new ArrayList<>());
    visibilityRestriction.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(visibilityRestriction, null);
  }

  /**
   * Test VisibilityRestriction {@link VisibilityRestriction#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VisibilityRestriction#equals(Object)}
   */
  @Test
  @DisplayName("Test VisibilityRestriction equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean VisibilityRestriction.equals(Object)", "int VisibilityRestriction.hashCode()"})
  void testVisibilityRestrictionEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    VisibilityRestriction visibilityRestriction = new VisibilityRestriction();
    visibilityRestriction.setTenantIds(new ArrayList<>());
    visibilityRestriction.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(visibilityRestriction, "Different type to VisibilityRestriction");
  }

  /**
   * Test VisibilityRestriction getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link VisibilityRestriction}
   *   <li>{@link VisibilityRestriction#setTenantIds(List)}
   *   <li>{@link VisibilityRestriction#setUserIds(List)}
   *   <li>{@link VisibilityRestriction#toString()}
   *   <li>{@link VisibilityRestriction#getTenantIds()}
   *   <li>{@link VisibilityRestriction#getUserIds()}
   * </ul>
   */
  @Test
  @DisplayName("Test VisibilityRestriction getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void VisibilityRestriction.<init>()", "List VisibilityRestriction.getTenantIds()",
      "List VisibilityRestriction.getUserIds()", "void VisibilityRestriction.setTenantIds(List)",
      "void VisibilityRestriction.setUserIds(List)", "String VisibilityRestriction.toString()"})
  void testVisibilityRestrictionGettersAndSetters() {
    // Arrange and Act
    VisibilityRestriction actualVisibilityRestriction = new VisibilityRestriction();
    ArrayList<Integer> tenantIds = new ArrayList<>();
    actualVisibilityRestriction.setTenantIds(tenantIds);
    ArrayList<Long> userIds = new ArrayList<>();
    actualVisibilityRestriction.setUserIds(userIds);
    String actualToStringResult = actualVisibilityRestriction.toString();
    List<Integer> actualTenantIds = actualVisibilityRestriction.getTenantIds();
    List<Long> actualUserIds = actualVisibilityRestriction.getUserIds();

    // Assert
    assertEquals("CreateGroup.VisibilityRestriction(tenantIds=[], userIds=[])", actualToStringResult);
    assertTrue(actualTenantIds.isEmpty());
    assertTrue(actualUserIds.isEmpty());
    assertSame(tenantIds, actualTenantIds);
    assertSame(userIds, actualUserIds);
  }
}
