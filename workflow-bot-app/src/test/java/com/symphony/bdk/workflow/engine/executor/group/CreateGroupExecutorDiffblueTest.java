package com.symphony.bdk.workflow.engine.executor.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.ext.group.gen.api.model.BaseProfile;
import com.symphony.bdk.ext.group.gen.api.model.GroupImplicitConnection;
import com.symphony.bdk.ext.group.gen.api.model.GroupInteractionTransfer;
import com.symphony.bdk.ext.group.gen.api.model.GroupVisibilityRestriction;
import com.symphony.bdk.ext.group.gen.api.model.Member;
import com.symphony.bdk.ext.group.gen.api.model.ReadGroup;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup.GroupMember;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup.ImplicitConnection;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup.InteractionTransfer;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup.Job;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup.Owner;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup.Profile;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup.VisibilityRestriction;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateGroupExecutorDiffblueTest {
  /**
   * Test {@link CreateGroupExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Then calls {@link SymphonyGroupService#insertGroup(CreateGroup)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroupExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); then calls insertGroup(CreateGroup)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CreateGroupExecutor.execute(ActivityExecutorContext)"})
  void testExecute_thenCallsInsertGroup() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateGroupExecutor createGroupExecutor = new CreateGroupExecutor();

    Owner owner = new Owner();
    owner.setId(1L);
    owner.setType("PLATFORM");

    CreateGroup createGroup = new CreateGroup();
    createGroup.setOwner(owner);
    SymphonyGroupService groupService = mock(SymphonyGroupService.class);
    when(groupService.insertGroup(Mockito.<com.symphony.bdk.ext.group.gen.api.model.CreateGroup>any()))
        .thenReturn(new ReadGroup());
    SpringBdkGateway springBdkGateway = new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        mock(MessageService.class), mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        groupService, mock(SessionService.class));

    ActivityExecutorContext<CreateGroup> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(createGroup);

    // Act
    createGroupExecutor.execute(execution);

    // Assert
    verify(groupService).insertGroup(isA(com.symphony.bdk.ext.group.gen.api.model.CreateGroup.class));
    verify(execution).bdk();
    verify(execution).getActivity();
    verify(execution).setOutputVariable(eq("group"), isA(Object.class));
  }

  /**
   * Test {@link CreateGroupExecutor#toMembers(List)}.
   * <ul>
   *   <li>Given {@link GroupMember} (default constructor) TenantId is one.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroupExecutor#toMembers(List)}
   */
  @Test
  @DisplayName("Test toMembers(List); given GroupMember (default constructor) TenantId is one; then return size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List CreateGroupExecutor.toMembers(List)"})
  void testToMembers_givenGroupMemberTenantIdIsOne_thenReturnSizeIsOne() {
    // Arrange
    GroupMember groupMember = new GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(1L);

    ArrayList<GroupMember> members = new ArrayList<>();
    members.add(groupMember);

    // Act
    List<Member> actualToMembersResult = CreateGroupExecutor.toMembers(members);

    // Assert
    assertEquals(1, actualToMembersResult.size());
    Member getResult = actualToMembersResult.get(0);
    assertEquals(1, getResult.getMemberTenant().intValue());
    assertEquals(1L, getResult.getMemberId().longValue());
  }

  /**
   * Test {@link CreateGroupExecutor#toMembers(List)}.
   * <ul>
   *   <li>Given {@link GroupMember} (default constructor) TenantId is two.</li>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroupExecutor#toMembers(List)}
   */
  @Test
  @DisplayName("Test toMembers(List); given GroupMember (default constructor) TenantId is two; then return size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List CreateGroupExecutor.toMembers(List)"})
  void testToMembers_givenGroupMemberTenantIdIsTwo_thenReturnSizeIsTwo() {
    // Arrange
    GroupMember groupMember = new GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(1L);

    GroupMember groupMember2 = new GroupMember();
    groupMember2.setTenantId(2);
    groupMember2.setUserId(2L);

    ArrayList<GroupMember> members = new ArrayList<>();
    members.add(groupMember2);
    members.add(groupMember);

    // Act
    List<Member> actualToMembersResult = CreateGroupExecutor.toMembers(members);

    // Assert
    assertEquals(2, actualToMembersResult.size());
    Member getResult = actualToMembersResult.get(1);
    assertEquals(1, getResult.getMemberTenant().intValue());
    assertEquals(1L, getResult.getMemberId().longValue());
    Member getResult2 = actualToMembersResult.get(0);
    assertEquals(2, getResult2.getMemberTenant().intValue());
    assertEquals(2L, getResult2.getMemberId().longValue());
  }

  /**
   * Test {@link CreateGroupExecutor#toMembers(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroupExecutor#toMembers(List)}
   */
  @Test
  @DisplayName("Test toMembers(List); when ArrayList(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List CreateGroupExecutor.toMembers(List)"})
  void testToMembers_whenArrayList_thenReturnEmpty() {
    // Arrange and Act
    List<Member> actualToMembersResult = CreateGroupExecutor.toMembers(new ArrayList<>());

    // Assert
    assertTrue(actualToMembersResult.isEmpty());
  }

  /**
   * Test {@link CreateGroupExecutor#toMembers(List)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroupExecutor#toMembers(List)}
   */
  @Test
  @DisplayName("Test toMembers(List); when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List CreateGroupExecutor.toMembers(List)"})
  void testToMembers_whenNull_thenReturnNull() {
    // Arrange and Act
    List<Member> actualToMembersResult = CreateGroupExecutor.toMembers(null);

    // Assert
    assertNull(actualToMembersResult);
  }

  /**
   * Test {@link CreateGroupExecutor#toProfile(Profile)}.
   * <ul>
   *   <li>Given {@link Job} (default constructor) City is {@code Oxford}.</li>
   *   <li>Then return JobDepartment is {@code Department}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroupExecutor#toProfile(Profile)}
   */
  @Test
  @DisplayName("Test toProfile(Profile); given Job (default constructor) City is 'Oxford'; then return JobDepartment is 'Department'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseProfile CreateGroupExecutor.toProfile(Profile)"})
  void testToProfile_givenJobCityIsOxford_thenReturnJobDepartmentIsDepartment() {
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

    // Act
    BaseProfile actualToProfileResult = CreateGroupExecutor.toProfile(profile);

    // Assert
    assertEquals("Department", actualToProfileResult.getJobDepartment());
    assertEquals("Division", actualToProfileResult.getJobDivision());
    assertEquals("Division", actualToProfileResult.getJobPhone());
    assertEquals("Dr", actualToProfileResult.getJobTitle());
    assertEquals("Oxford", actualToProfileResult.getJobCity());
    assertEquals("Role", actualToProfileResult.getJobRole());
  }

  /**
   * Test {@link CreateGroupExecutor#toProfile(Profile)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link Profile} (default constructor) Job is {@code null}.</li>
   *   <li>Then return JobCity is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroupExecutor#toProfile(Profile)}
   */
  @Test
  @DisplayName("Test toProfile(Profile); given 'null'; when Profile (default constructor) Job is 'null'; then return JobCity is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseProfile CreateGroupExecutor.toProfile(Profile)"})
  void testToProfile_givenNull_whenProfileJobIsNull_thenReturnJobCityIsNull() {
    // Arrange
    Profile profile = new Profile();
    profile.setAssetClasses(new ArrayList<>());
    profile.setCompanyName("Company Name");
    profile.setDisplayName("Display Name");
    profile.setEmail("jane.doe@example.org");
    profile.setFunctions(new HashSet<>());
    profile.setIndustries(new ArrayList<>());
    profile.setInstruments(new HashSet<>());
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());
    profile.setJob(null);

    // Act
    BaseProfile actualToProfileResult = CreateGroupExecutor.toProfile(profile);

    // Assert
    assertNull(actualToProfileResult.getJobCity());
    assertNull(actualToProfileResult.getJobDepartment());
    assertNull(actualToProfileResult.getJobDivision());
    assertNull(actualToProfileResult.getJobPhone());
    assertNull(actualToProfileResult.getJobRole());
    assertNull(actualToProfileResult.getJobTitle());
  }

  /**
   * Test {@link CreateGroupExecutor#toProfile(Profile)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroupExecutor#toProfile(Profile)}
   */
  @Test
  @DisplayName("Test toProfile(Profile); when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseProfile CreateGroupExecutor.toProfile(Profile)"})
  void testToProfile_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(CreateGroupExecutor.toProfile(null));
  }

  /**
   * Test {@link CreateGroupExecutor#toVisibilityRestriction(VisibilityRestriction)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return RestrictedTenantsList Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroupExecutor#toVisibilityRestriction(VisibilityRestriction)}
   */
  @Test
  @DisplayName("Test toVisibilityRestriction(VisibilityRestriction); given ArrayList(); then return RestrictedTenantsList Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"GroupVisibilityRestriction CreateGroupExecutor.toVisibilityRestriction(VisibilityRestriction)"})
  void testToVisibilityRestriction_givenArrayList_thenReturnRestrictedTenantsListEmpty() {
    // Arrange
    VisibilityRestriction visibilityRestriction = new VisibilityRestriction();
    visibilityRestriction.setTenantIds(new ArrayList<>());
    visibilityRestriction.setUserIds(new ArrayList<>());

    // Act
    GroupVisibilityRestriction actualToVisibilityRestrictionResult = CreateGroupExecutor
        .toVisibilityRestriction(visibilityRestriction);

    // Assert
    assertTrue(actualToVisibilityRestrictionResult.getRestrictedTenantsList().isEmpty());
    assertTrue(actualToVisibilityRestrictionResult.getRestrictedUsersList().isEmpty());
  }

  /**
   * Test {@link CreateGroupExecutor#toVisibilityRestriction(VisibilityRestriction)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroupExecutor#toVisibilityRestriction(VisibilityRestriction)}
   */
  @Test
  @DisplayName("Test toVisibilityRestriction(VisibilityRestriction); when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"GroupVisibilityRestriction CreateGroupExecutor.toVisibilityRestriction(VisibilityRestriction)"})
  void testToVisibilityRestriction_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(CreateGroupExecutor.toVisibilityRestriction(null));
  }

  /**
   * Test {@link CreateGroupExecutor#toImplicitConnection(ImplicitConnection)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return ConnectedTenantsList Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroupExecutor#toImplicitConnection(ImplicitConnection)}
   */
  @Test
  @DisplayName("Test toImplicitConnection(ImplicitConnection); given ArrayList(); then return ConnectedTenantsList Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"GroupImplicitConnection CreateGroupExecutor.toImplicitConnection(ImplicitConnection)"})
  void testToImplicitConnection_givenArrayList_thenReturnConnectedTenantsListEmpty() {
    // Arrange
    ImplicitConnection implicitConnection = new ImplicitConnection();
    implicitConnection.setTenantIds(new ArrayList<>());
    implicitConnection.setUserIds(new ArrayList<>());

    // Act
    GroupImplicitConnection actualToImplicitConnectionResult = CreateGroupExecutor
        .toImplicitConnection(implicitConnection);

    // Assert
    assertTrue(actualToImplicitConnectionResult.getConnectedTenantsList().isEmpty());
    assertTrue(actualToImplicitConnectionResult.getConnectedUsersList().isEmpty());
  }

  /**
   * Test {@link CreateGroupExecutor#toImplicitConnection(ImplicitConnection)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroupExecutor#toImplicitConnection(ImplicitConnection)}
   */
  @Test
  @DisplayName("Test toImplicitConnection(ImplicitConnection); when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"GroupImplicitConnection CreateGroupExecutor.toImplicitConnection(ImplicitConnection)"})
  void testToImplicitConnection_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(CreateGroupExecutor.toImplicitConnection(null));
  }

  /**
   * Test {@link CreateGroupExecutor#toInteractionTransfer(InteractionTransfer)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return RestrictedTenantsList Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroupExecutor#toInteractionTransfer(InteractionTransfer)}
   */
  @Test
  @DisplayName("Test toInteractionTransfer(InteractionTransfer); given ArrayList(); then return RestrictedTenantsList Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"GroupInteractionTransfer CreateGroupExecutor.toInteractionTransfer(InteractionTransfer)"})
  void testToInteractionTransfer_givenArrayList_thenReturnRestrictedTenantsListEmpty() {
    // Arrange
    InteractionTransfer interactionTransfer = new InteractionTransfer();
    interactionTransfer.setTenantIds(new ArrayList<>());
    interactionTransfer.setUserIds(new ArrayList<>());

    // Act
    GroupInteractionTransfer actualToInteractionTransferResult = CreateGroupExecutor
        .toInteractionTransfer(interactionTransfer);

    // Assert
    assertTrue(actualToInteractionTransferResult.getRestrictedTenantsList().isEmpty());
    assertTrue(actualToInteractionTransferResult.getRestrictedUsersList().isEmpty());
  }

  /**
   * Test {@link CreateGroupExecutor#toInteractionTransfer(InteractionTransfer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateGroupExecutor#toInteractionTransfer(InteractionTransfer)}
   */
  @Test
  @DisplayName("Test toInteractionTransfer(InteractionTransfer); when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"GroupInteractionTransfer CreateGroupExecutor.toInteractionTransfer(InteractionTransfer)"})
  void testToInteractionTransfer_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(CreateGroupExecutor.toInteractionTransfer(null));
  }
}
