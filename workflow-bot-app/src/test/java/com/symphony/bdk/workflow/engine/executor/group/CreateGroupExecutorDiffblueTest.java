package com.symphony.bdk.workflow.engine.executor.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateGroupExecutorDiffblueTest {
  /**
   * Test {@link CreateGroupExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Then calls {@link SymphonyGroupService#insertGroup(CreateGroup)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CreateGroupExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); then calls insertGroup(CreateGroup)")
  void testExecute_thenCallsInsertGroup() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateGroupExecutor createGroupExecutor = new CreateGroupExecutor();

    CreateGroup.Owner owner = new CreateGroup.Owner();
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

    ActivityExecutorContext<com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup> execution = mock(
        ActivityExecutorContext.class);
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
  void testToMembers_givenGroupMemberTenantIdIsOne_thenReturnSizeIsOne() {
    // Arrange
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(1L);

    ArrayList<CreateGroup.GroupMember> members = new ArrayList<>();
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
  void testToMembers_givenGroupMemberTenantIdIsTwo_thenReturnSizeIsTwo() {
    // Arrange
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setTenantId(1);
    groupMember.setUserId(1L);

    CreateGroup.GroupMember groupMember2 = new CreateGroup.GroupMember();
    groupMember2.setTenantId(2);
    groupMember2.setUserId(2L);

    ArrayList<CreateGroup.GroupMember> members = new ArrayList<>();
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
  void testToMembers_whenArrayList_thenReturnEmpty() {
    // Arrange and Act
    List<Member> actualToMembersResult = CreateGroupExecutor.toMembers(new ArrayList<>());

    // Assert
    assertTrue(actualToMembersResult.isEmpty());
  }

  /**
   * Test {@link CreateGroupExecutor#toProfile(Profile)}.
   * <p>
   * Method under test: {@link CreateGroupExecutor#toProfile(CreateGroup.Profile)}
   */
  @Test
  @DisplayName("Test toProfile(Profile)")
  void testToProfile() {
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
    ArrayList<String> industries = new ArrayList<>();
    profile.setIndustries(industries);
    profile.setInstruments(new HashSet<>());
    profile.setJob(job);
    profile.setMarketCoverages(new HashSet<>());
    profile.setMobile("Mobile");
    profile.setResponsibilities(new HashSet<>());

    // Act
    BaseProfile actualToProfileResult = CreateGroupExecutor.toProfile(profile);

    // Assert
    assertEquals("Company Name", actualToProfileResult.getCompanyName());
    assertEquals("Department", actualToProfileResult.getJobDepartment());
    assertEquals("Display Name", actualToProfileResult.getDisplayName());
    assertEquals("Division", actualToProfileResult.getJobDivision());
    assertEquals("Division", actualToProfileResult.getJobPhone());
    assertEquals("Dr", actualToProfileResult.getJobTitle());
    assertEquals("Mobile", actualToProfileResult.getMobile());
    assertEquals("Oxford", actualToProfileResult.getJobCity());
    assertEquals("Role", actualToProfileResult.getJobRole());
    assertEquals("jane.doe@example.org", actualToProfileResult.getEmail());
    assertTrue(actualToProfileResult.getAssetClassesOfInterest().isEmpty());
    assertTrue(actualToProfileResult.getFunction().isEmpty());
    assertTrue(actualToProfileResult.getInstrument().isEmpty());
    assertTrue(actualToProfileResult.getMarketCoverage().isEmpty());
    assertTrue(actualToProfileResult.getResponsibility().isEmpty());
    assertSame(industries, actualToProfileResult.getIndustryOfInterest());
  }

  /**
   * Test
   * {@link CreateGroupExecutor#toVisibilityRestriction(VisibilityRestriction)}.
   * <p>
   * Method under test:
   * {@link CreateGroupExecutor#toVisibilityRestriction(CreateGroup.VisibilityRestriction)}
   */
  @Test
  @DisplayName("Test toVisibilityRestriction(VisibilityRestriction)")
  void testToVisibilityRestriction() {
    // Arrange
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
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
   * Test {@link CreateGroupExecutor#toImplicitConnection(ImplicitConnection)}.
   * <p>
   * Method under test:
   * {@link CreateGroupExecutor#toImplicitConnection(CreateGroup.ImplicitConnection)}
   */
  @Test
  @DisplayName("Test toImplicitConnection(ImplicitConnection)")
  void testToImplicitConnection() {
    // Arrange
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
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
   * Test {@link CreateGroupExecutor#toInteractionTransfer(InteractionTransfer)}.
   * <p>
   * Method under test:
   * {@link CreateGroupExecutor#toInteractionTransfer(CreateGroup.InteractionTransfer)}
   */
  @Test
  @DisplayName("Test toInteractionTransfer(InteractionTransfer)")
  void testToInteractionTransfer() {
    // Arrange
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    interactionTransfer.setTenantIds(new ArrayList<>());
    interactionTransfer.setUserIds(new ArrayList<>());

    // Act
    GroupInteractionTransfer actualToInteractionTransferResult = CreateGroupExecutor
        .toInteractionTransfer(interactionTransfer);

    // Assert
    assertTrue(actualToInteractionTransferResult.getRestrictedTenantsList().isEmpty());
    assertTrue(actualToInteractionTransferResult.getRestrictedUsersList().isEmpty());
  }
}
