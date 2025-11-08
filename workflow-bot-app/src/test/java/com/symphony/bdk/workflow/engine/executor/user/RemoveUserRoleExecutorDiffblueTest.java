package com.symphony.bdk.workflow.engine.executor.user;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.user.RemoveUserRole;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RemoveUserRoleExecutor.class})
@ExtendWith(SpringExtension.class)
class RemoveUserRoleExecutorDiffblueTest {
  @Autowired
  private RemoveUserRoleExecutor removeUserRoleExecutor;

  /**
   * Method under test:
   * {@link RemoveUserRoleExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute() {
    // Arrange
    ActivityExecutorContext<RemoveUserRole> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(new RemoveUserRole());

    // Act
    removeUserRoleExecutor.execute(context);

    // Assert that nothing has changed
    verify(context).getActivity();
  }

  /**
   * Method under test:
   * {@link RemoveUserRoleExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute2() {
    // Arrange
    ArrayList<Long> userIds = new ArrayList<>();
    userIds.add(1L);

    RemoveUserRole removeUserRole = new RemoveUserRole();
    removeUserRole.setUserIds(userIds);
    ActivityExecutorContext<RemoveUserRole> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(removeUserRole);

    // Act
    removeUserRoleExecutor.execute(context);

    // Assert that nothing has changed
    verify(context).getActivity();
  }
}
