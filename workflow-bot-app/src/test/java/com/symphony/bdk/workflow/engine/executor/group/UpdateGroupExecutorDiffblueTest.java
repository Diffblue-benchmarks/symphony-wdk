package com.symphony.bdk.workflow.engine.executor.group;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.group.UpdateGroup;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UpdateGroupExecutor.class})
@ExtendWith(SpringExtension.class)
class UpdateGroupExecutorDiffblueTest {
  @Autowired
  private UpdateGroupExecutor updateGroupExecutor;

  /**
   * Method under test:
   * {@link UpdateGroupExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute() throws IOException {
    // Arrange
    ActivityExecutorContext<UpdateGroup> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(new UpdateGroup());

    // Act
    updateGroupExecutor.execute(execution);

    // Assert that nothing has changed
    verify(execution, atLeast(1)).getActivity();
  }
}
