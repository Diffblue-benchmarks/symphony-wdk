package com.symphony.bdk.workflow.engine.executor.group;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.group.AddGroupMember;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AddGroupMemberExecutor.class})
@ExtendWith(SpringExtension.class)
class AddGroupMemberExecutorDiffblueTest {
  @Autowired
  private AddGroupMemberExecutor addGroupMemberExecutor;

  /**
   * Method under test:
   * {@link AddGroupMemberExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute() {
    // Arrange
    ActivityExecutorContext<AddGroupMember> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(new AddGroupMember());

    // Act
    addGroupMemberExecutor.execute(execution);

    // Assert that nothing has changed
    verify(execution, atLeast(1)).getActivity();
  }
}
