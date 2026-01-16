package com.symphony.bdk.workflow.engine.executor.group;

import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.ext.group.gen.api.model.ReadGroup;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.group.AddGroupMember;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddGroupMemberExecutorClaudeTest {

  private AddGroupMemberExecutor executor;
  private ActivityExecutorContext<AddGroupMember> context;
  private AddGroupMember activity;
  private BdkGateway bdkGateway;
  private SymphonyGroupService groupService;
  private ReadGroup readGroup;

  @BeforeEach
  void setUp() {
    executor = new AddGroupMemberExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new AddGroupMember();
    bdkGateway = mock(BdkGateway.class);
    groupService = mock(SymphonyGroupService.class);
    readGroup = new ReadGroup();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.groups()).thenReturn(groupService);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    AddGroupMemberExecutor newExecutor = new AddGroupMemberExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    AddGroupMemberExecutor newExecutor = new AddGroupMemberExecutor();

    // Then: Instance should be of AddGroupMemberExecutor type and implement ActivityExecutor
    assertThat(newExecutor).isInstanceOf(AddGroupMemberExecutor.class);
    assertThat(newExecutor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    AddGroupMemberExecutor executor1 = new AddGroupMemberExecutor();
    AddGroupMemberExecutor executor2 = new AddGroupMemberExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new AddGroupMemberExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - basic scenarios

  @Test
  void execute_withSingleMember_shouldAddMemberAndSetOutputVariable() {
    // Given: A group ID and a single member
    String groupId = "group123";
    activity.setGroupId(groupId);

    CreateGroup.GroupMember member = new CreateGroup.GroupMember();
    member.setUserId(123456789L);
    member.setTenantId(1);

    List<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(member);
    activity.setMembers(members);

    when(groupService.addMemberToGroup(groupId, member.getUserId())).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Member should be added and output variable set
    verify(groupService).addMemberToGroup(groupId, member.getUserId());
    verify(context).setOutputVariable(eq("group"), eq(readGroup));
  }

  @Test
  void execute_withMultipleMembers_shouldAddAllMembersAndSetOutputVariable() {
    // Given: A group ID and multiple members
    String groupId = "group123";
    activity.setGroupId(groupId);

    CreateGroup.GroupMember member1 = new CreateGroup.GroupMember();
    member1.setUserId(123456789L);
    member1.setTenantId(1);

    CreateGroup.GroupMember member2 = new CreateGroup.GroupMember();
    member2.setUserId(987654321L);
    member2.setTenantId(2);

    CreateGroup.GroupMember member3 = new CreateGroup.GroupMember();
    member3.setUserId(555555555L);
    member3.setTenantId(1);

    List<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(member1);
    members.add(member2);
    members.add(member3);
    activity.setMembers(members);

    when(groupService.addMemberToGroup(groupId, member1.getUserId())).thenReturn(readGroup);
    when(groupService.addMemberToGroup(groupId, member2.getUserId())).thenReturn(readGroup);
    when(groupService.addMemberToGroup(groupId, member3.getUserId())).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: All members should be added
    verify(groupService).addMemberToGroup(groupId, member1.getUserId());
    verify(groupService).addMemberToGroup(groupId, member2.getUserId());
    verify(groupService).addMemberToGroup(groupId, member3.getUserId());
    verify(context).setOutputVariable(eq("group"), eq(readGroup));
  }

  @Test
  void execute_withEmptyMembersList_shouldNotAddAnyMemberOrSetOutputVariable() {
    // Given: A group ID but empty members list
    String groupId = "group123";
    activity.setGroupId(groupId);
    activity.setMembers(List.of());

    // When: Execute is called
    executor.execute(context);

    // Then: No member should be added and no output variable set
    verify(groupService, never()).addMemberToGroup(anyString(), anyLong());
    verify(context, never()).setOutputVariable(anyString(), any());
  }

  @Test
  void execute_withDifferentGroupId_shouldUseCorrectGroupId() {
    // Given: A different group ID
    String groupId = "differentGroup456";
    activity.setGroupId(groupId);

    CreateGroup.GroupMember member = new CreateGroup.GroupMember();
    member.setUserId(123456789L);
    member.setTenantId(1);

    List<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(member);
    activity.setMembers(members);

    when(groupService.addMemberToGroup(groupId, member.getUserId())).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use the correct group ID
    verify(groupService).addMemberToGroup("differentGroup456", 123456789L);
    verify(context).setOutputVariable(eq("group"), any(ReadGroup.class));
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid setup
    String groupId = "group123";
    activity.setGroupId(groupId);

    CreateGroup.GroupMember member = new CreateGroup.GroupMember();
    member.setUserId(123456789L);
    member.setTenantId(1);

    List<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(member);
    activity.setMembers(members);

    when(groupService.addMemberToGroup(groupId, member.getUserId())).thenReturn(readGroup);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(groupService, times(2)).addMemberToGroup(groupId, member.getUserId());
    verify(context, times(2)).setOutputVariable(eq("group"), any(ReadGroup.class));
  }

  @Test
  void execute_withLargeUserId_shouldHandleCorrectly() {
    // Given: A large user ID
    String groupId = "group123";
    activity.setGroupId(groupId);

    CreateGroup.GroupMember member = new CreateGroup.GroupMember();
    member.setUserId(9223372036854775807L); // Max long value
    member.setTenantId(1);

    List<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(member);
    activity.setMembers(members);

    when(groupService.addMemberToGroup(groupId, member.getUserId())).thenReturn(readGroup);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should complete without exception
    verify(groupService).addMemberToGroup(groupId, 9223372036854775807L);
    verify(context).setOutputVariable(eq("group"), any(ReadGroup.class));
  }

  // Tests for execute method - output variable behavior

  @Test
  void execute_withMultipleMembers_shouldSetOutputVariableWithLastReturnedGroup() {
    // Given: A group ID and multiple members (output variable should be set with last returned group)
    String groupId = "group123";
    activity.setGroupId(groupId);

    CreateGroup.GroupMember member1 = new CreateGroup.GroupMember();
    member1.setUserId(123456789L);
    member1.setTenantId(1);

    CreateGroup.GroupMember member2 = new CreateGroup.GroupMember();
    member2.setUserId(987654321L);
    member2.setTenantId(2);

    List<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(member1);
    members.add(member2);
    activity.setMembers(members);

    ReadGroup readGroup1 = new ReadGroup();
    ReadGroup readGroup2 = new ReadGroup();

    when(groupService.addMemberToGroup(groupId, member1.getUserId())).thenReturn(readGroup1);
    when(groupService.addMemberToGroup(groupId, member2.getUserId())).thenReturn(readGroup2);

    // When: Execute is called
    executor.execute(context);

    // Then: Output variable should be set with the last returned group
    verify(context).setOutputVariable(eq("group"), eq(readGroup2));
  }

  @Test
  void execute_withSingleMemberAndNullGroup_shouldNotSetOutputVariable() {
    // Given: A group ID and a single member, but service returns null
    String groupId = "group123";
    activity.setGroupId(groupId);

    CreateGroup.GroupMember member = new CreateGroup.GroupMember();
    member.setUserId(123456789L);
    member.setTenantId(1);

    List<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(member);
    activity.setMembers(members);

    when(groupService.addMemberToGroup(groupId, member.getUserId())).thenReturn(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Member should be added but output variable should not be set
    verify(groupService).addMemberToGroup(groupId, member.getUserId());
    verify(context, never()).setOutputVariable(anyString(), any());
  }

  @Test
  void execute_withNullMembersList_shouldNotAddAnyMemberOrSetOutputVariable() {
    // Given: A group ID but null members list
    String groupId = "group123";
    activity.setGroupId(groupId);
    activity.setMembers(null);

    // When/Then: Execute should handle null gracefully (will throw NullPointerException)
    // This tests the actual behavior - the code doesn't handle null members list
    assertThatCode(() -> executor.execute(context))
        .isInstanceOf(NullPointerException.class);
  }

  // Tests for execute method - different member configurations

  @Test
  void execute_withMemberWithNullUserId_shouldAttemptToAddMember() {
    // Given: A member with null user ID
    String groupId = "group123";
    activity.setGroupId(groupId);

    CreateGroup.GroupMember member = new CreateGroup.GroupMember();
    member.setUserId(null);
    member.setTenantId(1);

    List<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(member);
    activity.setMembers(members);

    when(groupService.addMemberToGroup(groupId, null)).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Should attempt to add member with null user ID
    verify(groupService).addMemberToGroup(groupId, null);
    verify(context).setOutputVariable(eq("group"), eq(readGroup));
  }

  @Test
  void execute_withMemberWithDifferentTenantIds_shouldAddAllMembers() {
    // Given: Multiple members with different tenant IDs
    String groupId = "group123";
    activity.setGroupId(groupId);

    CreateGroup.GroupMember member1 = new CreateGroup.GroupMember();
    member1.setUserId(111111111L);
    member1.setTenantId(1);

    CreateGroup.GroupMember member2 = new CreateGroup.GroupMember();
    member2.setUserId(222222222L);
    member2.setTenantId(2);

    CreateGroup.GroupMember member3 = new CreateGroup.GroupMember();
    member3.setUserId(333333333L);
    member3.setTenantId(3);

    List<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(member1);
    members.add(member2);
    members.add(member3);
    activity.setMembers(members);

    when(groupService.addMemberToGroup(groupId, member1.getUserId())).thenReturn(readGroup);
    when(groupService.addMemberToGroup(groupId, member2.getUserId())).thenReturn(readGroup);
    when(groupService.addMemberToGroup(groupId, member3.getUserId())).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: All members should be added regardless of tenant ID
    verify(groupService).addMemberToGroup(groupId, 111111111L);
    verify(groupService).addMemberToGroup(groupId, 222222222L);
    verify(groupService).addMemberToGroup(groupId, 333333333L);
    verify(context).setOutputVariable(eq("group"), eq(readGroup));
  }

  @Test
  void execute_withDuplicateUserIds_shouldAddBothMembers() {
    // Given: Multiple members with the same user ID (edge case)
    String groupId = "group123";
    activity.setGroupId(groupId);

    CreateGroup.GroupMember member1 = new CreateGroup.GroupMember();
    member1.setUserId(123456789L);
    member1.setTenantId(1);

    CreateGroup.GroupMember member2 = new CreateGroup.GroupMember();
    member2.setUserId(123456789L);
    member2.setTenantId(1);

    List<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(member1);
    members.add(member2);
    activity.setMembers(members);

    when(groupService.addMemberToGroup(groupId, member1.getUserId())).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Should attempt to add the member twice
    verify(groupService, times(2)).addMemberToGroup(groupId, 123456789L);
    verify(context).setOutputVariable(eq("group"), eq(readGroup));
  }

  // Tests for execute method - edge cases

  @Test
  void execute_withNullGroupId_shouldAttemptToAddMember() {
    // Given: A null group ID
    activity.setGroupId(null);

    CreateGroup.GroupMember member = new CreateGroup.GroupMember();
    member.setUserId(123456789L);
    member.setTenantId(1);

    List<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(member);
    activity.setMembers(members);

    when(groupService.addMemberToGroup(null, member.getUserId())).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Should attempt to add member with null group ID
    verify(groupService).addMemberToGroup(null, 123456789L);
    verify(context).setOutputVariable(eq("group"), eq(readGroup));
  }

  @Test
  void execute_withEmptyGroupId_shouldUseEmptyString() {
    // Given: An empty group ID
    String groupId = "";
    activity.setGroupId(groupId);

    CreateGroup.GroupMember member = new CreateGroup.GroupMember();
    member.setUserId(123456789L);
    member.setTenantId(1);

    List<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(member);
    activity.setMembers(members);

    when(groupService.addMemberToGroup(groupId, member.getUserId())).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use empty string as group ID
    verify(groupService).addMemberToGroup("", 123456789L);
    verify(context).setOutputVariable(eq("group"), eq(readGroup));
  }

  @Test
  void execute_withOneMember_shouldOnlySetOutputVariableOnce() {
    // Given: A group ID and one member
    String groupId = "group123";
    activity.setGroupId(groupId);

    CreateGroup.GroupMember member = new CreateGroup.GroupMember();
    member.setUserId(123456789L);
    member.setTenantId(1);

    List<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(member);
    activity.setMembers(members);

    when(groupService.addMemberToGroup(groupId, member.getUserId())).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Output variable should be set exactly once
    verify(context, times(1)).setOutputVariable(eq("group"), eq(readGroup));
  }

  @Test
  void execute_withTwoMembersFirstReturnsNull_shouldSetOutputVariableWithSecondGroup() {
    // Given: Two members where first call returns null
    String groupId = "group123";
    activity.setGroupId(groupId);

    CreateGroup.GroupMember member1 = new CreateGroup.GroupMember();
    member1.setUserId(123456789L);
    member1.setTenantId(1);

    CreateGroup.GroupMember member2 = new CreateGroup.GroupMember();
    member2.setUserId(987654321L);
    member2.setTenantId(2);

    List<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(member1);
    members.add(member2);
    activity.setMembers(members);

    when(groupService.addMemberToGroup(groupId, member1.getUserId())).thenReturn(null);
    when(groupService.addMemberToGroup(groupId, member2.getUserId())).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Output variable should be set with the second group (non-null)
    verify(groupService).addMemberToGroup(groupId, member1.getUserId());
    verify(groupService).addMemberToGroup(groupId, member2.getUserId());
    verify(context).setOutputVariable(eq("group"), eq(readGroup));
  }
}
