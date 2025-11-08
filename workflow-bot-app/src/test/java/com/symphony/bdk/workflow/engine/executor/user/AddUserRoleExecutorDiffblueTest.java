package com.symphony.bdk.workflow.engine.executor.user;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.user.AddUserRole;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AddUserRoleExecutor.class})
@ExtendWith(SpringExtension.class)
class AddUserRoleExecutorDiffblueTest {
  @Autowired
  private AddUserRoleExecutor addUserRoleExecutor;

  /**
   * Method under test:
   * {@link AddUserRoleExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute() {
    // Arrange
    ActivityExecutorContext<AddUserRole> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(new AddUserRole());

    // Act
    addUserRoleExecutor.execute(context);

    // Assert that nothing has changed
    verify(context).getActivity();
  }

  /**
   * Method under test:
   * {@link AddUserRoleExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute2() {
    // Arrange
    ArrayList<Long> userIds = new ArrayList<>();
    userIds.add(1L);

    AddUserRole addUserRole = new AddUserRole();
    addUserRole.setUserIds(userIds);
    ActivityExecutorContext<AddUserRole> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(addUserRole);

    // Act
    addUserRoleExecutor.execute(context);

    // Assert that nothing has changed
    verify(context).getActivity();
  }
}
