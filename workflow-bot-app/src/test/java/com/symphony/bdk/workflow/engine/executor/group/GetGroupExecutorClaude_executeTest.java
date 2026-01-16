package com.symphony.bdk.workflow.engine.executor.group;

import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.ext.group.gen.api.model.ReadGroup;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.group.GetGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetGroupExecutorClaude_executeTest {

  private GetGroupExecutor executor;
  private ActivityExecutorContext<GetGroup> context;
  private GetGroup activity;
  private BdkGateway bdkGateway;
  private SymphonyGroupService groupService;
  private ReadGroup readGroup;

  @BeforeEach
  void setUp() {
    executor = new GetGroupExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new GetGroup();
    bdkGateway = mock(BdkGateway.class);
    groupService = mock(SymphonyGroupService.class);
    readGroup = new ReadGroup();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.groups()).thenReturn(groupService);
  }

  // Tests for execute method - basic scenarios

  @Test
  void execute_withValidGroupId_shouldGetGroupAndSetOutputVariable() {
    // Given: A valid group ID
    String groupId = "group123";
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Group should be retrieved and output variable set
    verify(groupService).getGroup(groupId);
    verify(context).setOutputVariable(eq("group"), eq(readGroup));
  }

  @Test
  void execute_withDifferentGroupId_shouldUseCorrectGroupId() {
    // Given: A different group ID
    String groupId = "differentGroup456";
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use the correct group ID
    verify(groupService).getGroup("differentGroup456");
    verify(context).setOutputVariable(eq("group"), any(ReadGroup.class));
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid setup
    String groupId = "group123";
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(groupService, times(2)).getGroup(groupId);
    verify(context, times(2)).setOutputVariable(eq("group"), any(ReadGroup.class));
  }

  @Test
  void execute_shouldNotThrowException() {
    // Given: Valid setup
    String groupId = "group123";
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  // Tests for execute method - output variable behavior

  @Test
  void execute_withNullGroupResponse_shouldSetOutputVariableWithNull() {
    // Given: Group service returns null
    String groupId = "group123";
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should still set output variable with null
    verify(groupService).getGroup(groupId);
    verify(context).setOutputVariable(eq("group"), eq(null));
  }

  @Test
  void execute_withValidGroup_shouldSetOutputVariableExactlyOnce() {
    // Given: Valid group ID
    String groupId = "group123";
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Output variable should be set exactly once
    verify(context, times(1)).setOutputVariable(eq("group"), eq(readGroup));
  }

  @Test
  void execute_withDifferentGroupInstances_shouldSetCorrectInstance() {
    // Given: Different ReadGroup instances
    String groupId = "group123";
    activity.setGroupId(groupId);
    ReadGroup specificGroup = new ReadGroup();

    when(groupService.getGroup(groupId)).thenReturn(specificGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set the specific instance returned by the service
    verify(context).setOutputVariable(eq("group"), eq(specificGroup));
  }

  // Tests for execute method - edge cases with groupId

  @Test
  void execute_withNullGroupId_shouldPassNullToService() {
    // Given: Null group ID
    activity.setGroupId(null);

    when(groupService.getGroup(null)).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass null to service
    verify(groupService).getGroup(null);
    verify(context).setOutputVariable(eq("group"), eq(readGroup));
  }

  @Test
  void execute_withEmptyGroupId_shouldUseEmptyString() {
    // Given: Empty group ID
    String groupId = "";
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use empty string as group ID
    verify(groupService).getGroup("");
    verify(context).setOutputVariable(eq("group"), eq(readGroup));
  }

  @Test
  void execute_withWhitespaceGroupId_shouldUseWhitespaceString() {
    // Given: Whitespace group ID
    String groupId = "   ";
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use whitespace string as group ID
    verify(groupService).getGroup("   ");
    verify(context).setOutputVariable(eq("group"), eq(readGroup));
  }

  @Test
  void execute_withSpecialCharactersInGroupId_shouldHandleCorrectly() {
    // Given: Group ID with special characters
    String groupId = "group-123_test!@#$%";
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should handle special characters correctly
    verify(groupService).getGroup("group-123_test!@#$%");
    verify(context).setOutputVariable(eq("group"), any(ReadGroup.class));
  }

  @Test
  void execute_withVeryLongGroupId_shouldHandleCorrectly() {
    // Given: Very long group ID
    String groupId = "a".repeat(1000);
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should handle long group ID correctly
    verify(groupService).getGroup(groupId);
    verify(context).setOutputVariable(eq("group"), any(ReadGroup.class));
  }

  // Tests for execute method - interaction verification

  @Test
  void execute_shouldCallGetActivityExactlyOnce() {
    // Given: Valid setup
    String groupId = "group123";
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Should call getActivity exactly once
    verify(context, times(1)).getActivity();
  }

  @Test
  void execute_shouldCallBdkExactlyOnce() {
    // Given: Valid setup
    String groupId = "group123";
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Should call bdk() exactly once
    verify(context, times(1)).bdk();
  }

  @Test
  void execute_shouldCallGroupsExactlyOnce() {
    // Given: Valid setup
    String groupId = "group123";
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Should call groups() exactly once
    verify(bdkGateway, times(1)).groups();
  }

  @Test
  void execute_shouldCallGetGroupExactlyOnce() {
    // Given: Valid setup
    String groupId = "group123";
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Should call getGroup exactly once
    verify(groupService, times(1)).getGroup(groupId);
  }

  @Test
  void execute_shouldCallSetOutputVariableExactlyOnce() {
    // Given: Valid setup
    String groupId = "group123";
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Should call setOutputVariable exactly once
    verify(context, times(1)).setOutputVariable(anyString(), any());
  }

  // Tests for execute method - execution order

  @Test
  void execute_shouldFollowCorrectExecutionSequence() {
    // Given: Valid setup
    String groupId = "group123";
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Verify the execution sequence
    var inOrder = org.mockito.Mockito.inOrder(context, bdkGateway, groupService);
    inOrder.verify(context).getActivity();
    inOrder.verify(context).bdk();
    inOrder.verify(bdkGateway).groups();
    inOrder.verify(groupService).getGroup(groupId);
    inOrder.verify(context).setOutputVariable(eq("group"), eq(readGroup));
  }

  // Tests for execute method - output variable key

  @Test
  void execute_shouldUseCorrectOutputVariableKey() {
    // Given: Valid setup
    String groupId = "group123";
    activity.setGroupId(groupId);

    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use "group" as the output variable key
    verify(context).setOutputVariable(eq("group"), any());
    verify(context, never()).setOutputVariable(eq("Group"), any());
    verify(context, never()).setOutputVariable(eq("GROUP"), any());
    verify(context, never()).setOutputVariable(eq("groups"), any());
  }
}
