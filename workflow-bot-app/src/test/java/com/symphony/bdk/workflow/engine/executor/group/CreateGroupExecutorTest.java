package com.symphony.bdk.workflow.engine.executor.group;

import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.ext.group.gen.api.model.BaseProfile;
import com.symphony.bdk.ext.group.gen.api.model.GroupImplicitConnection;
import com.symphony.bdk.ext.group.gen.api.model.GroupInteractionTransfer;
import com.symphony.bdk.ext.group.gen.api.model.GroupVisibilityRestriction;
import com.symphony.bdk.ext.group.gen.api.model.Member;
import com.symphony.bdk.ext.group.gen.api.model.ReadGroup;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateGroupExecutorTest {

  @InjectMocks
  private CreateGroupExecutor executor;

  @Mock
  private ActivityExecutorContext<CreateGroup> context;

  @Mock
  private BdkGateway bdk;

  @Mock
  private SymphonyGroupService groupService;

  private CreateGroup buildCreateGroup() {
    CreateGroup createGroup = new CreateGroup();
    createGroup.setType("SDL");
    createGroup.setName("test-group");
    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(123L);
    owner.setType("TENANT");
    createGroup.setOwner(owner);
    return createGroup;
  }

  @Test
  void shouldExecuteAndSetOutputVariable() {
    CreateGroup createGroup = buildCreateGroup();
    ReadGroup readGroup = mock(ReadGroup.class);
    when(context.getActivity()).thenReturn(createGroup);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.groups()).thenReturn(groupService);
    when(groupService.insertGroup(any())).thenReturn(readGroup);

    executor.execute(context);

    verify(groupService).insertGroup(any());
    verify(context).setOutputVariable("group", readGroup);
  }

  @Test
  void shouldReturnNullMembersWhenMembersIsNull() {
    List<Member> result = CreateGroupExecutor.toMembers(null);

    assertThat(result).isNull();
  }

  @Test
  void shouldMapMembersWhenMembersIsNotNull() {
    CreateGroup.GroupMember member1 = new CreateGroup.GroupMember();
    member1.setUserId(111L);
    member1.setTenantId(10);
    CreateGroup.GroupMember member2 = new CreateGroup.GroupMember();
    member2.setUserId(222L);
    member2.setTenantId(20);

    List<Member> result = CreateGroupExecutor.toMembers(Arrays.asList(member1, member2));

    assertThat(result).hasSize(2);
    assertThat(result.get(0).getMemberId()).isEqualTo(111L);
    assertThat(result.get(0).getMemberTenant()).isEqualTo(10);
    assertThat(result.get(1).getMemberId()).isEqualTo(222L);
    assertThat(result.get(1).getMemberTenant()).isEqualTo(20);
  }

  @Test
  void shouldReturnNullProfileWhenProfileIsNull() {
    BaseProfile result = CreateGroupExecutor.toProfile(null);

    assertThat(result).isNull();
  }

  @Test
  void shouldMapProfileWithoutJob() {
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setDisplayName("Display Name");
    profile.setCompanyName("Company");
    profile.setEmail("test@example.com");
    profile.setMobile("1234567890");
    profile.setIndustries(Collections.singletonList("Finance"));
    profile.setMarketCoverages(Collections.singleton("EMEA"));
    profile.setResponsibilities(Collections.singleton("Dev"));
    profile.setFunctions(Collections.singleton("Func"));
    profile.setInstruments(Collections.singleton("Bonds"));

    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    assertThat(result).isNotNull();
    assertThat(result.getDisplayName()).isEqualTo("Display Name");
    assertThat(result.getCompanyName()).isEqualTo("Company");
    assertThat(result.getEmail()).isEqualTo("test@example.com");
    assertThat(result.getMobile()).isEqualTo("1234567890");
  }

  @Test
  void shouldMapProfileWithJob() {
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setDisplayName("Display Name");
    CreateGroup.Job job = new CreateGroup.Job();
    job.setTitle("Engineer");
    job.setRole("Developer");
    job.setDepartment("Engineering");
    job.setDivision("R&D");
    job.setCity("New York");
    profile.setJob(job);

    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    assertThat(result).isNotNull();
    assertThat(result.getJobTitle()).isEqualTo("Engineer");
    assertThat(result.getJobRole()).isEqualTo("Developer");
    assertThat(result.getJobDepartment()).isEqualTo("Engineering");
    assertThat(result.getJobDivision()).isEqualTo("R&D");
    assertThat(result.getJobCity()).isEqualTo("New York");
  }

  @Test
  void shouldReturnNullVisibilityRestrictionWhenNull() {
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(null);

    assertThat(result).isNull();
  }

  @Test
  void shouldMapVisibilityRestrictionWhenNotNull() {
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    visibilityRestriction.setTenantIds(Collections.singletonList(1));
    visibilityRestriction.setUserIds(Collections.singletonList(100L));

    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).containsExactly(1);
    assertThat(result.getRestrictedUsersList()).containsExactly(100L);
  }

  @Test
  void shouldReturnNullImplicitConnectionWhenNull() {
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(null);

    assertThat(result).isNull();
  }

  @Test
  void shouldMapImplicitConnectionWhenNotNull() {
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    implicitConnection.setTenantIds(Collections.singletonList(2));
    implicitConnection.setUserIds(Collections.singletonList(200L));

    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).containsExactly(2);
    assertThat(result.getConnectedUsersList()).containsExactly(200L);
  }

  @Test
  void shouldReturnNullInteractionTransferWhenNull() {
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(null);

    assertThat(result).isNull();
  }

  @Test
  void shouldMapInteractionTransferWhenNotNull() {
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    interactionTransfer.setTenantIds(Collections.singletonList(3));
    interactionTransfer.setUserIds(Collections.singletonList(300L));

    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).containsExactly(3);
    assertThat(result.getRestrictedUsersList()).containsExactly(300L);
  }

  @Test
  void shouldExecuteWithSubTypeSet() {
    CreateGroup createGroup = buildCreateGroup();
    createGroup.setSubType("COMMUNITY");
    ReadGroup readGroup = mock(ReadGroup.class);
    when(context.getActivity()).thenReturn(createGroup);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.groups()).thenReturn(groupService);
    when(groupService.insertGroup(any())).thenReturn(readGroup);

    executor.execute(context);

    verify(groupService).insertGroup(any());
  }
}
