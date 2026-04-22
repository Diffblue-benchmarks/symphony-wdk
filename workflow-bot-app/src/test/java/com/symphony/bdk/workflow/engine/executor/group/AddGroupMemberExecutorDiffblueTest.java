package com.symphony.bdk.workflow.engine.executor.group;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.ext.group.gen.api.model.ReadGroup;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.group.AddGroupMember;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AddGroupMemberExecutor.class})
@ExtendWith(SpringExtension.class)
class AddGroupMemberExecutorDiffblueTest {
  @Autowired private AddGroupMemberExecutor addGroupMemberExecutor;

  /**
   * Test {@link AddGroupMemberExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link AddGroupMember} (default constructor).
   *   <li>Then calls {@link ActivityExecutorContext#getActivity()}.
   * </ul>
   *
   * <p>Method under test: {@link AddGroupMemberExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given AddGroupMember (default constructor); then calls getActivity()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AddGroupMemberExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenAddGroupMember_thenCallsGetActivity() {
    // Arrange
    ActivityExecutorContext<AddGroupMember> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(new AddGroupMember());

    // Act
    addGroupMemberExecutor.execute(execution);

    // Assert
    verify(execution, atLeast(1)).getActivity();
  }

  /**
   * Test {@link AddGroupMemberExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link AddGroupMember} with one member.
   *   <li>Then calls {@link SymphonyGroupService#addMemberToGroup(String, Long)} and
   *       {@link ActivityExecutorContext#setOutputVariable(String, Object)}.
   * </ul>
   *
   * <p>Method under test: {@link AddGroupMemberExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given AddGroupMember with one member; then calls addMemberToGroup and setOutputVariable")
  @MethodsUnderTest({"void AddGroupMemberExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenAddGroupMemberWithOneMember_thenCallsAddMemberToGroupAndSetOutputVariable() {
    // Arrange
    AddGroupMemberExecutor executor = new AddGroupMemberExecutor();

    CreateGroup.GroupMember member = new CreateGroup.GroupMember();
    member.setUserId(42L);
    member.setTenantId(1);

    AddGroupMember addGroupMember = new AddGroupMember();
    addGroupMember.setGroupId("testGroupId");
    addGroupMember.setMembers(List.of(member));

    ReadGroup readGroup = new ReadGroup();
    SymphonyGroupService groupService = mock(SymphonyGroupService.class);
    when(groupService.addMemberToGroup(eq("testGroupId"), eq(42L))).thenReturn(readGroup);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.groups()).thenReturn(groupService);

    ActivityExecutorContext<AddGroupMember> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(addGroupMember);
    when(execution.bdk()).thenReturn(bdk);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());

    // Act
    executor.execute(execution);

    // Assert
    verify(groupService).addMemberToGroup(eq("testGroupId"), eq(42L));
    verify(execution).setOutputVariable(eq("group"), eq(readGroup));
  }

  /**
   * Test {@link AddGroupMemberExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link AddGroupMember} with multiple members.
   *   <li>Then calls {@link SymphonyGroupService#addMemberToGroup(String, Long)} for each member.
   * </ul>
   *
   * <p>Method under test: {@link AddGroupMemberExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given AddGroupMember with multiple members; then calls addMemberToGroup for each member")
  @MethodsUnderTest({"void AddGroupMemberExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenAddGroupMemberWithMultipleMembers_thenCallsAddMemberToGroupForEachMember() {
    // Arrange
    AddGroupMemberExecutor executor = new AddGroupMemberExecutor();

    CreateGroup.GroupMember member1 = new CreateGroup.GroupMember();
    member1.setUserId(1L);
    member1.setTenantId(1);

    CreateGroup.GroupMember member2 = new CreateGroup.GroupMember();
    member2.setUserId(2L);
    member2.setTenantId(2);

    AddGroupMember addGroupMember = new AddGroupMember();
    addGroupMember.setGroupId("groupAbc");
    addGroupMember.setMembers(List.of(member1, member2));

    ReadGroup readGroup1 = new ReadGroup();
    ReadGroup readGroup2 = new ReadGroup();
    SymphonyGroupService groupService = mock(SymphonyGroupService.class);
    when(groupService.addMemberToGroup(eq("groupAbc"), eq(1L))).thenReturn(readGroup1);
    when(groupService.addMemberToGroup(eq("groupAbc"), eq(2L))).thenReturn(readGroup2);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.groups()).thenReturn(groupService);

    ActivityExecutorContext<AddGroupMember> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(addGroupMember);
    when(execution.bdk()).thenReturn(bdk);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());

    // Act
    executor.execute(execution);

    // Assert
    verify(groupService).addMemberToGroup(eq("groupAbc"), eq(1L));
    verify(groupService).addMemberToGroup(eq("groupAbc"), eq(2L));
    verify(execution).setOutputVariable(eq("group"), eq(readGroup2));
  }
}
