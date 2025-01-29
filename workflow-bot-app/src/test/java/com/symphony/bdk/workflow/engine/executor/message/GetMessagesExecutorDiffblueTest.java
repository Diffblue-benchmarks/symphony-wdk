package com.symphony.bdk.workflow.engine.executor.message;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.message.GetMessages;
import org.junit.jupiter.api.DisplayName;
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
   * Test {@link GetMessagesExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link GetMessages} (default constructor).</li>
   *   <li>Then calls {@link ActivityExecutorContext#getActivity()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetMessagesExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given GetMessages (default constructor); then calls getActivity()")
  void testExecute_givenGetMessages_thenCallsGetActivity() {
    // Arrange
    ActivityExecutorContext<GetMessages> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(new GetMessages());

    // Act
    getMessagesExecutor.execute(context);

    // Assert
    verify(context).getActivity();
  }
}
