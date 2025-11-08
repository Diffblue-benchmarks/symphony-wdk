package com.symphony.bdk.workflow.engine.executor.user;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.user.GetUsers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GetUsersExecutor.class})
@ExtendWith(SpringExtension.class)
class GetUsersExecutorDiffblueTest {
  @Autowired
  private GetUsersExecutor getUsersExecutor;

  /**
   * Method under test: {@link GetUsersExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute() {
    // Arrange
    ActivityExecutorContext<GetUsers> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(new GetUsers());

    // Act
    getUsersExecutor.execute(context);

    // Assert
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("users"), isNull());
  }
}
