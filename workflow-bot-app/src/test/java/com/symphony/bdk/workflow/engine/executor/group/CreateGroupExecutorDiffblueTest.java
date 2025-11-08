package com.symphony.bdk.workflow.engine.executor.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateGroupExecutorDiffblueTest {
  /**
   * Method under test:
   * {@link CreateGroupExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test: {@link CreateGroupExecutor#toMembers(List)}
   */
  @Test
  void testToMembers() {
    // Arrange and Act
    List<Member> actualToMembersResult = CreateGroupExecutor.toMembers(new ArrayList<>());

    // Assert
    assertTrue(actualToMembersResult.isEmpty());
  }

  /**
   * Method under test: {@link CreateGroupExecutor#toMembers(List)}
   */
  @Test
  void testToMembers2() {
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
   * Method under test: {@link CreateGroupExecutor#toMembers(List)}
   */
  @Test
  void testToMembers3() {
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
   * Method under test: {@link CreateGroupExecutor#toMembers(List)}
   */
  @Test
  void testToMembers4() {
    // Arrange
    CreateGroup.GroupMember groupMember = mock(CreateGroup.GroupMember.class);
    when(groupMember.getTenantId()).thenReturn(1);
    when(groupMember.getUserId()).thenReturn(1L);
    doNothing().when(groupMember).setTenantId(Mockito.<Integer>any());
    doNothing().when(groupMember).setUserId(Mockito.<Long>any());
    groupMember.setTenantId(1);
    groupMember.setUserId(1L);

    ArrayList<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(groupMember);

    // Act
    List<Member> actualToMembersResult = CreateGroupExecutor.toMembers(members);

    // Assert
    verify(groupMember).getTenantId();
    verify(groupMember).getUserId();
    verify(groupMember).setTenantId(eq(1));
    verify(groupMember).setUserId(eq(1L));
    assertEquals(1, actualToMembersResult.size());
    Member getResult = actualToMembersResult.get(0);
    assertEquals(1, getResult.getMemberTenant().intValue());
    assertEquals(1L, getResult.getMemberId().longValue());
  }

  /**
   * Method under test: {@link CreateGroupExecutor#toProfile(CreateGroup.Profile)}
   */
  @Test
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
    HashSet<String> functions = new HashSet<>();
    profile.setFunctions(functions);
    ArrayList<String> industries = new ArrayList<>();
    profile.setIndustries(industries);
    HashSet<String> instruments = new HashSet<>();
    profile.setInstruments(instruments);
    profile.setJob(job);
    HashSet<String> marketCoverages = new HashSet<>();
    profile.setMarketCoverages(marketCoverages);
    profile.setMobile("Mobile");
    HashSet<String> responsibilities = new HashSet<>();
    profile.setResponsibilities(responsibilities);

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
    List<String> assetClassesOfInterest = actualToProfileResult.getAssetClassesOfInterest();
    assertTrue(assetClassesOfInterest.isEmpty());
    Set<String> function = actualToProfileResult.getFunction();
    assertTrue(function.isEmpty());
    Set<String> instrument = actualToProfileResult.getInstrument();
    assertTrue(instrument.isEmpty());
    Set<String> marketCoverage = actualToProfileResult.getMarketCoverage();
    assertTrue(marketCoverage.isEmpty());
    Set<String> responsibility = actualToProfileResult.getResponsibility();
    assertTrue(responsibility.isEmpty());
    assertSame(industries, assetClassesOfInterest);
    assertSame(industries, actualToProfileResult.getIndustryOfInterest());
    assertSame(functions, function);
    assertSame(instruments, instrument);
    assertSame(marketCoverages, marketCoverage);
    assertSame(responsibilities, responsibility);
  }

  /**
   * Method under test: {@link CreateGroupExecutor#toProfile(CreateGroup.Profile)}
   */
  @Test
  void testToProfile2() {
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
    CreateGroup.Profile profile = mock(CreateGroup.Profile.class);
    when(profile.getJob()).thenReturn(job2);
    when(profile.getCompanyName()).thenReturn("Company Name");
    when(profile.getDisplayName()).thenReturn("Display Name");
    when(profile.getEmail()).thenReturn("jane.doe@example.org");
    when(profile.getMobile()).thenReturn("Mobile");
    ArrayList<String> stringList = new ArrayList<>();
    when(profile.getIndustries()).thenReturn(stringList);
    HashSet<String> stringSet = new HashSet<>();
    when(profile.getFunctions()).thenReturn(stringSet);
    HashSet<String> stringSet2 = new HashSet<>();
    when(profile.getInstruments()).thenReturn(stringSet2);
    HashSet<String> stringSet3 = new HashSet<>();
    when(profile.getMarketCoverages()).thenReturn(stringSet3);
    HashSet<String> stringSet4 = new HashSet<>();
    when(profile.getResponsibilities()).thenReturn(stringSet4);
    doNothing().when(profile).setAssetClasses(Mockito.<List<String>>any());
    doNothing().when(profile).setCompanyName(Mockito.<String>any());
    doNothing().when(profile).setDisplayName(Mockito.<String>any());
    doNothing().when(profile).setEmail(Mockito.<String>any());
    doNothing().when(profile).setFunctions(Mockito.<Set<String>>any());
    doNothing().when(profile).setIndustries(Mockito.<List<String>>any());
    doNothing().when(profile).setInstruments(Mockito.<Set<String>>any());
    doNothing().when(profile).setJob(Mockito.<CreateGroup.Job>any());
    doNothing().when(profile).setMarketCoverages(Mockito.<Set<String>>any());
    doNothing().when(profile).setMobile(Mockito.<String>any());
    doNothing().when(profile).setResponsibilities(Mockito.<Set<String>>any());
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
    verify(profile).getCompanyName();
    verify(profile).getDisplayName();
    verify(profile).getEmail();
    verify(profile).getFunctions();
    verify(profile, atLeast(1)).getIndustries();
    verify(profile).getInstruments();
    verify(profile, atLeast(1)).getJob();
    verify(profile).getMarketCoverages();
    verify(profile).getMobile();
    verify(profile).getResponsibilities();
    verify(profile).setAssetClasses(isA(List.class));
    verify(profile).setCompanyName(eq("Company Name"));
    verify(profile).setDisplayName(eq("Display Name"));
    verify(profile).setEmail(eq("jane.doe@example.org"));
    verify(profile).setFunctions(isA(Set.class));
    verify(profile).setIndustries(isA(List.class));
    verify(profile).setInstruments(isA(Set.class));
    verify(profile).setJob(isA(CreateGroup.Job.class));
    verify(profile).setMarketCoverages(isA(Set.class));
    verify(profile).setMobile(eq("Mobile"));
    verify(profile).setResponsibilities(isA(Set.class));
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
    List<String> assetClassesOfInterest = actualToProfileResult.getAssetClassesOfInterest();
    assertTrue(assetClassesOfInterest.isEmpty());
    Set<String> function = actualToProfileResult.getFunction();
    assertTrue(function.isEmpty());
    Set<String> instrument = actualToProfileResult.getInstrument();
    assertTrue(instrument.isEmpty());
    Set<String> marketCoverage = actualToProfileResult.getMarketCoverage();
    assertTrue(marketCoverage.isEmpty());
    Set<String> responsibility = actualToProfileResult.getResponsibility();
    assertTrue(responsibility.isEmpty());
    assertSame(stringList, assetClassesOfInterest);
    assertSame(stringList, actualToProfileResult.getIndustryOfInterest());
    assertSame(stringSet, function);
    assertSame(stringSet2, instrument);
    assertSame(stringSet3, marketCoverage);
    assertSame(stringSet4, responsibility);
  }

  /**
   * Method under test:
   * {@link CreateGroupExecutor#toVisibilityRestriction(CreateGroup.VisibilityRestriction)}
   */
  @Test
  void testToVisibilityRestriction() {
    // Arrange
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    ArrayList<Integer> tenantIds = new ArrayList<>();
    visibilityRestriction.setTenantIds(tenantIds);
    ArrayList<Long> userIds = new ArrayList<>();
    visibilityRestriction.setUserIds(userIds);

    // Act
    GroupVisibilityRestriction actualToVisibilityRestrictionResult = CreateGroupExecutor
        .toVisibilityRestriction(visibilityRestriction);

    // Assert
    List<Integer> restrictedTenantsList = actualToVisibilityRestrictionResult.getRestrictedTenantsList();
    assertTrue(restrictedTenantsList.isEmpty());
    List<Long> restrictedUsersList = actualToVisibilityRestrictionResult.getRestrictedUsersList();
    assertTrue(restrictedUsersList.isEmpty());
    assertSame(tenantIds, restrictedTenantsList);
    assertSame(userIds, restrictedUsersList);
  }

  /**
   * Method under test:
   * {@link CreateGroupExecutor#toVisibilityRestriction(CreateGroup.VisibilityRestriction)}
   */
  @Test
  void testToVisibilityRestriction2() {
    // Arrange
    CreateGroup.VisibilityRestriction visibilityRestriction = mock(CreateGroup.VisibilityRestriction.class);
    ArrayList<Integer> integerList = new ArrayList<>();
    when(visibilityRestriction.getTenantIds()).thenReturn(integerList);
    ArrayList<Long> resultLongList = new ArrayList<>();
    when(visibilityRestriction.getUserIds()).thenReturn(resultLongList);
    doNothing().when(visibilityRestriction).setTenantIds(Mockito.<List<Integer>>any());
    doNothing().when(visibilityRestriction).setUserIds(Mockito.<List<Long>>any());
    visibilityRestriction.setTenantIds(new ArrayList<>());
    visibilityRestriction.setUserIds(new ArrayList<>());

    // Act
    GroupVisibilityRestriction actualToVisibilityRestrictionResult = CreateGroupExecutor
        .toVisibilityRestriction(visibilityRestriction);

    // Assert
    verify(visibilityRestriction).getTenantIds();
    verify(visibilityRestriction).getUserIds();
    verify(visibilityRestriction).setTenantIds(isA(List.class));
    verify(visibilityRestriction).setUserIds(isA(List.class));
    List<Integer> restrictedTenantsList = actualToVisibilityRestrictionResult.getRestrictedTenantsList();
    assertTrue(restrictedTenantsList.isEmpty());
    List<Long> restrictedUsersList = actualToVisibilityRestrictionResult.getRestrictedUsersList();
    assertTrue(restrictedUsersList.isEmpty());
    assertSame(integerList, restrictedTenantsList);
    assertSame(resultLongList, restrictedUsersList);
  }

  /**
   * Method under test:
   * {@link CreateGroupExecutor#toImplicitConnection(CreateGroup.ImplicitConnection)}
   */
  @Test
  void testToImplicitConnection() {
    // Arrange
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    ArrayList<Integer> tenantIds = new ArrayList<>();
    implicitConnection.setTenantIds(tenantIds);
    ArrayList<Long> userIds = new ArrayList<>();
    implicitConnection.setUserIds(userIds);

    // Act
    GroupImplicitConnection actualToImplicitConnectionResult = CreateGroupExecutor
        .toImplicitConnection(implicitConnection);

    // Assert
    List<Integer> connectedTenantsList = actualToImplicitConnectionResult.getConnectedTenantsList();
    assertTrue(connectedTenantsList.isEmpty());
    List<Long> connectedUsersList = actualToImplicitConnectionResult.getConnectedUsersList();
    assertTrue(connectedUsersList.isEmpty());
    assertSame(tenantIds, connectedTenantsList);
    assertSame(userIds, connectedUsersList);
  }

  /**
   * Method under test:
   * {@link CreateGroupExecutor#toImplicitConnection(CreateGroup.ImplicitConnection)}
   */
  @Test
  void testToImplicitConnection2() {
    // Arrange
    CreateGroup.ImplicitConnection implicitConnection = mock(CreateGroup.ImplicitConnection.class);
    ArrayList<Integer> integerList = new ArrayList<>();
    when(implicitConnection.getTenantIds()).thenReturn(integerList);
    ArrayList<Long> resultLongList = new ArrayList<>();
    when(implicitConnection.getUserIds()).thenReturn(resultLongList);
    doNothing().when(implicitConnection).setTenantIds(Mockito.<List<Integer>>any());
    doNothing().when(implicitConnection).setUserIds(Mockito.<List<Long>>any());
    implicitConnection.setTenantIds(new ArrayList<>());
    implicitConnection.setUserIds(new ArrayList<>());

    // Act
    GroupImplicitConnection actualToImplicitConnectionResult = CreateGroupExecutor
        .toImplicitConnection(implicitConnection);

    // Assert
    verify(implicitConnection).getTenantIds();
    verify(implicitConnection).getUserIds();
    verify(implicitConnection).setTenantIds(isA(List.class));
    verify(implicitConnection).setUserIds(isA(List.class));
    List<Integer> connectedTenantsList = actualToImplicitConnectionResult.getConnectedTenantsList();
    assertTrue(connectedTenantsList.isEmpty());
    List<Long> connectedUsersList = actualToImplicitConnectionResult.getConnectedUsersList();
    assertTrue(connectedUsersList.isEmpty());
    assertSame(integerList, connectedTenantsList);
    assertSame(resultLongList, connectedUsersList);
  }

  /**
   * Method under test:
   * {@link CreateGroupExecutor#toInteractionTransfer(CreateGroup.InteractionTransfer)}
   */
  @Test
  void testToInteractionTransfer() {
    // Arrange
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    ArrayList<Integer> tenantIds = new ArrayList<>();
    interactionTransfer.setTenantIds(tenantIds);
    ArrayList<Long> userIds = new ArrayList<>();
    interactionTransfer.setUserIds(userIds);

    // Act
    GroupInteractionTransfer actualToInteractionTransferResult = CreateGroupExecutor
        .toInteractionTransfer(interactionTransfer);

    // Assert
    List<Integer> restrictedTenantsList = actualToInteractionTransferResult.getRestrictedTenantsList();
    assertTrue(restrictedTenantsList.isEmpty());
    List<Long> restrictedUsersList = actualToInteractionTransferResult.getRestrictedUsersList();
    assertTrue(restrictedUsersList.isEmpty());
    assertSame(tenantIds, restrictedTenantsList);
    assertSame(userIds, restrictedUsersList);
  }

  /**
   * Method under test:
   * {@link CreateGroupExecutor#toInteractionTransfer(CreateGroup.InteractionTransfer)}
   */
  @Test
  void testToInteractionTransfer2() {
    // Arrange
    CreateGroup.InteractionTransfer interactionTransfer = mock(CreateGroup.InteractionTransfer.class);
    ArrayList<Integer> integerList = new ArrayList<>();
    when(interactionTransfer.getTenantIds()).thenReturn(integerList);
    ArrayList<Long> resultLongList = new ArrayList<>();
    when(interactionTransfer.getUserIds()).thenReturn(resultLongList);
    doNothing().when(interactionTransfer).setTenantIds(Mockito.<List<Integer>>any());
    doNothing().when(interactionTransfer).setUserIds(Mockito.<List<Long>>any());
    interactionTransfer.setTenantIds(new ArrayList<>());
    interactionTransfer.setUserIds(new ArrayList<>());

    // Act
    GroupInteractionTransfer actualToInteractionTransferResult = CreateGroupExecutor
        .toInteractionTransfer(interactionTransfer);

    // Assert
    verify(interactionTransfer).getTenantIds();
    verify(interactionTransfer).getUserIds();
    verify(interactionTransfer).setTenantIds(isA(List.class));
    verify(interactionTransfer).setUserIds(isA(List.class));
    List<Integer> restrictedTenantsList = actualToInteractionTransferResult.getRestrictedTenantsList();
    assertTrue(restrictedTenantsList.isEmpty());
    List<Long> restrictedUsersList = actualToInteractionTransferResult.getRestrictedUsersList();
    assertTrue(restrictedUsersList.isEmpty());
    assertSame(integerList, restrictedTenantsList);
    assertSame(resultLongList, restrictedUsersList);
  }
}
