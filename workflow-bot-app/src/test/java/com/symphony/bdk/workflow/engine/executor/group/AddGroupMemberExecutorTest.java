package com.symphony.bdk.workflow.engine.executor.group;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.ext.group.gen.api.model.ReadGroup;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.group.AddGroupMember;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class AddGroupMemberExecutorTest {

  private AddGroupMemberExecutor executor;

  @Mock
  private ActivityExecutorContext<AddGroupMember> context;

  @Mock
  private BdkGateway bdkGateway;

  @Mock
  private SymphonyGroupService groupService;

  @Mock
  private ReadGroup readGroup;

  @BeforeEach
  void setUp() {
    executor = new AddGroupMemberExecutor();
  }

  @Test
  void execute_shouldAddSingleMemberToGroup() {
    // Arrange
    AddGroupMember activity = new AddGroupMember();
    activity.setGroupId("group123");

    CreateGroup.GroupMember member = new CreateGroup.GroupMember();
    member.setUserId(12345L);
    member.setTenantId(1);

    activity.setMembers(List.of(member));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.groups()).thenReturn(groupService);
    when(groupService.addMemberToGroup("group123", 12345L)).thenReturn(readGroup);

    // Act
    executor.execute(context);

    // Assert
    verify(groupService).addMemberToGroup("group123", 12345L);
    verify(context).setOutputVariable("group", readGroup);
  }

  @Test
  void execute_shouldAddMultipleMembersToGroup() {
    // Arrange
    AddGroupMember activity = new AddGroupMember();
    activity.setGroupId("group456");

    CreateGroup.GroupMember member1 = new CreateGroup.GroupMember();
    member1.setUserId(11111L);
    member1.setTenantId(1);

    CreateGroup.GroupMember member2 = new CreateGroup.GroupMember();
    member2.setUserId(22222L);
    member2.setTenantId(2);

    CreateGroup.GroupMember member3 = new CreateGroup.GroupMember();
    member3.setUserId(33333L);
    member3.setTenantId(3);

    activity.setMembers(List.of(member1, member2, member3));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.groups()).thenReturn(groupService);
    when(groupService.addMemberToGroup("group456", 11111L)).thenReturn(readGroup);
    when(groupService.addMemberToGroup("group456", 22222L)).thenReturn(readGroup);
    when(groupService.addMemberToGroup("group456", 33333L)).thenReturn(readGroup);

    // Act
    executor.execute(context);

    // Assert
    verify(groupService).addMemberToGroup("group456", 11111L);
    verify(groupService).addMemberToGroup("group456", 22222L);
    verify(groupService).addMemberToGroup("group456", 33333L);
    verify(context).setOutputVariable("group", readGroup);
  }

  @Test
  void execute_shouldHandleEmptyMemberList() {
    // Arrange
    AddGroupMember activity = new AddGroupMember();
    activity.setGroupId("group789");
    activity.setMembers(List.of());

    when(context.getActivity()).thenReturn(activity);

    // Act
    executor.execute(context);

    // Assert
    verifyNoInteractions(groupService);
  }
}
