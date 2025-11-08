package com.symphony.bdk.workflow.engine.executor.message;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.message.GetMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GetMessagesExecutor.class})
@ExtendWith(SpringExtension.class)
class GetMessagesExecutorDiffblueTest {
  @Autowired
  private GetMessagesExecutor getMessagesExecutor;

  /**
   * Method under test:
   * {@link GetMessagesExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute() {
    // Arrange
    ActivityExecutorContext<GetMessages> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(new GetMessages());

    // Act
    getMessagesExecutor.execute(context);

    // Assert that nothing has changed
    verify(context).getActivity();
  }
}
