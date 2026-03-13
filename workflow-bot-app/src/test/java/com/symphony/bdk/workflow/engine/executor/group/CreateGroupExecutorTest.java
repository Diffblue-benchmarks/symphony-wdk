package com.symphony.bdk.workflow.engine.executor.group;

import com.symphony.bdk.ext.group.gen.api.model.BaseProfile;
import com.symphony.bdk.ext.group.gen.api.model.CreateGroup.SubTypeEnum;
import com.symphony.bdk.ext.group.gen.api.model.GroupImplicitConnection;
import com.symphony.bdk.ext.group.gen.api.model.GroupInteractionTransfer;
import com.symphony.bdk.ext.group.gen.api.model.GroupVisibilityRestriction;
import com.symphony.bdk.ext.group.gen.api.model.Member;
import com.symphony.bdk.ext.group.gen.api.model.ReadGroup;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateGroupExecutorTest {

  @Mock
  private ActivityExecutorContext<CreateGroup> context;

  @Mock
  private BdkGateway bdkGateway;

  @Mock
  private SymphonyGroupService groupService;

  @Mock
  private ReadGroup readGroup;

  private CreateGroupExecutor executor;

  @BeforeEach
  void setUp() {
    executor = new CreateGroupExecutor();
    lenient().when(context.bdk()).thenReturn(bdkGateway);
    lenient().when(bdkGateway.groups()).thenReturn(groupService);
  }

  @Test
  void shouldExecuteCreateGroup() {
    CreateGroup activity = new CreateGroup();
    activity.setType("SDL");
    activity.setName("Test Group");
    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(123L);
    owner.setType("USER");
    activity.setOwner(owner);

    when(context.getActivity()).thenReturn(activity);
    when(readGroup.getId()).thenReturn("group123");
    when(groupService.insertGroup(any(com.symphony.bdk.ext.group.gen.api.model.CreateGroup.class)))
        .thenReturn(readGroup);

    executor.execute(context);

    ArgumentCaptor<com.symphony.bdk.ext.group.gen.api.model.CreateGroup> captor =
        ArgumentCaptor.forClass(com.symphony.bdk.ext.group.gen.api.model.CreateGroup.class);
    verify(groupService).insertGroup(captor.capture());
    verify(context).setOutputVariable("group", readGroup);

    com.symphony.bdk.ext.group.gen.api.model.CreateGroup capturedGroup = captor.getValue();
    assertThat(capturedGroup.getType()).isEqualTo("SDL");
    assertThat(capturedGroup.getName()).isEqualTo("Test Group");
  }

  @Test
  void shouldConvertToCreateGroup() {
    CreateGroup activity = new CreateGroup();
    activity.setType("SDL");
    activity.setName("Test Group");
    activity.setReferrer("referrer123");

    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(123L);
    owner.setType("USER");
    activity.setOwner(owner);

    CreateGroup.GroupMember member = new CreateGroup.GroupMember();
    member.setUserId(456L);
    member.setTenantId(789);
    activity.setMembers(List.of(member));

    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setDisplayName("Display Name");
    activity.setProfile(profile);

    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    visibilityRestriction.setTenantIds(List.of(1, 2));
    activity.setVisibilityRestriction(visibilityRestriction);

    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    implicitConnection.setUserIds(List.of(100L, 200L));
    activity.setImplicitConnection(implicitConnection);

    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    interactionTransfer.setTenantIds(List.of(3, 4));
    activity.setInteractionTransfer(interactionTransfer);

    when(context.getActivity()).thenReturn(activity);
    when(groupService.insertGroup(any(com.symphony.bdk.ext.group.gen.api.model.CreateGroup.class)))
        .thenReturn(readGroup);

    executor.execute(context);

    ArgumentCaptor<com.symphony.bdk.ext.group.gen.api.model.CreateGroup> captor =
        ArgumentCaptor.forClass(com.symphony.bdk.ext.group.gen.api.model.CreateGroup.class);
    verify(groupService).insertGroup(captor.capture());

    com.symphony.bdk.ext.group.gen.api.model.CreateGroup capturedGroup = captor.getValue();
    assertThat(capturedGroup.getType()).isEqualTo("SDL");
    assertThat(capturedGroup.getName()).isEqualTo("Test Group");
    assertThat(capturedGroup.getReferrer()).isEqualTo("referrer123");
    assertThat(capturedGroup.getMembers()).hasSize(1);
    assertThat(capturedGroup.getProfile()).isNotNull();
    assertThat(capturedGroup.getVisibilityRestriction()).isNotNull();
    assertThat(capturedGroup.getImplicitConnection()).isNotNull();
    assertThat(capturedGroup.getInteractionTransfer()).isNotNull();
  }


  @Test
  void shouldReturnNullSubTypeWhenNull() {
    CreateGroup activity = new CreateGroup();
    activity.setSubType(null);

    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(123L);
    owner.setType("USER");
    activity.setOwner(owner);

    when(context.getActivity()).thenReturn(activity);
    when(groupService.insertGroup(any(com.symphony.bdk.ext.group.gen.api.model.CreateGroup.class)))
        .thenReturn(readGroup);

    executor.execute(context);

    ArgumentCaptor<com.symphony.bdk.ext.group.gen.api.model.CreateGroup> captor =
        ArgumentCaptor.forClass(com.symphony.bdk.ext.group.gen.api.model.CreateGroup.class);
    verify(groupService).insertGroup(captor.capture());

    assertThat(captor.getValue().getSubType()).isNull();
  }

  @Test
  void shouldConvertMembersWhenNotNull() {
    CreateGroup.GroupMember member1 = new CreateGroup.GroupMember();
    member1.setUserId(456L);
    member1.setTenantId(789);

    CreateGroup.GroupMember member2 = new CreateGroup.GroupMember();
    member2.setUserId(111L);
    member2.setTenantId(222);

    List<Member> members = CreateGroupExecutor.toMembers(List.of(member1, member2));

    assertThat(members).hasSize(2);
    assertThat(members.get(0).getMemberId()).isEqualTo(456L);
    assertThat(members.get(0).getMemberTenant()).isEqualTo(789);
    assertThat(members.get(1).getMemberId()).isEqualTo(111L);
    assertThat(members.get(1).getMemberTenant()).isEqualTo(222);
  }

  @Test
  void shouldReturnNullMembersWhenNull() {
    List<Member> members = CreateGroupExecutor.toMembers(null);
    assertThat(members).isNull();
  }

  @Test
  void shouldConvertProfileWhenNotNull() {
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setDisplayName("Display Name");
    profile.setCompanyName("Company Name");
    profile.setEmail("test@example.com");
    profile.setMobile("+1234567890");
    profile.setIndustries(List.of("Technology", "Finance"));
    profile.setAssetClasses(List.of("Equity", "Fixed Income"));
    profile.setMarketCoverages(Set.of("US", "EU"));
    profile.setResponsibilities(Set.of("Trading", "Research"));
    profile.setFunctions(Set.of("Sales", "Marketing"));
    profile.setInstruments(Set.of("Futures", "Options"));

    BaseProfile baseProfile = CreateGroupExecutor.toProfile(profile);

    assertThat(baseProfile).isNotNull();
    assertThat(baseProfile.getDisplayName()).isEqualTo("Display Name");
    assertThat(baseProfile.getCompanyName()).isEqualTo("Company Name");
    assertThat(baseProfile.getEmail()).isEqualTo("test@example.com");
    assertThat(baseProfile.getMobile()).isEqualTo("+1234567890");
    assertThat(baseProfile.getIndustryOfInterest()).containsExactly("Technology", "Finance");
    assertThat(baseProfile.getAssetClassesOfInterest()).containsExactly("Technology", "Finance");
    assertThat(baseProfile.getMarketCoverage()).containsExactlyInAnyOrder("US", "EU");
    assertThat(baseProfile.getResponsibility()).containsExactlyInAnyOrder("Trading", "Research");
    assertThat(baseProfile.getFunction()).containsExactlyInAnyOrder("Sales", "Marketing");
    assertThat(baseProfile.getInstrument()).containsExactlyInAnyOrder("Futures", "Options");
  }

  @Test
  void shouldConvertProfileWithJobWhenNotNull() {
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setDisplayName("Display Name");

    CreateGroup.Job job = new CreateGroup.Job();
    job.setTitle("Software Engineer");
    job.setRole("Developer");
    job.setDepartment("Engineering");
    job.setDivision("Technology");
    job.setPhone("+9876543210");
    job.setCity("San Francisco");
    profile.setJob(job);

    BaseProfile baseProfile = CreateGroupExecutor.toProfile(profile);

    assertThat(baseProfile).isNotNull();
    assertThat(baseProfile.getJobTitle()).isEqualTo("Software Engineer");
    assertThat(baseProfile.getJobRole()).isEqualTo("Developer");
    assertThat(baseProfile.getJobDepartment()).isEqualTo("Engineering");
    assertThat(baseProfile.getJobDivision()).isEqualTo("Technology");
    assertThat(baseProfile.getJobPhone()).isEqualTo("Technology");
    assertThat(baseProfile.getJobCity()).isEqualTo("San Francisco");
  }

  @Test
  void shouldReturnNullProfileWhenNull() {
    BaseProfile baseProfile = CreateGroupExecutor.toProfile(null);
    assertThat(baseProfile).isNull();
  }

  @Test
  void shouldConvertVisibilityRestrictionWhenNotNull() {
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    visibilityRestriction.setTenantIds(List.of(1, 2, 3));
    visibilityRestriction.setUserIds(List.of(100L, 200L, 300L));

    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).containsExactly(1, 2, 3);
    assertThat(result.getRestrictedUsersList()).containsExactly(100L, 200L, 300L);
  }

  @Test
  void shouldReturnNullVisibilityRestrictionWhenNull() {
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(null);
    assertThat(result).isNull();
  }

  @Test
  void shouldConvertImplicitConnectionWhenNotNull() {
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    implicitConnection.setTenantIds(List.of(4, 5, 6));
    implicitConnection.setUserIds(List.of(400L, 500L, 600L));

    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).containsExactly(4, 5, 6);
    assertThat(result.getConnectedUsersList()).containsExactly(400L, 500L, 600L);
  }

  @Test
  void shouldReturnNullImplicitConnectionWhenNull() {
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(null);
    assertThat(result).isNull();
  }

  @Test
  void shouldConvertInteractionTransferWhenNotNull() {
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    interactionTransfer.setTenantIds(List.of(7, 8, 9));
    interactionTransfer.setUserIds(List.of(700L, 800L, 900L));

    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).containsExactly(7, 8, 9);
    assertThat(result.getRestrictedUsersList()).containsExactly(700L, 800L, 900L);
  }

  @Test
  void shouldReturnNullInteractionTransferWhenNull() {
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(null);
    assertThat(result).isNull();
  }
}
