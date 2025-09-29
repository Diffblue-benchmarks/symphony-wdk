package com.symphony.bdk.workflow.engine.executor.group;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.group.AddGroupMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
}
