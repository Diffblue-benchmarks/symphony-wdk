package com.symphony.bdk.workflow.engine.executor.stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.stream.GetStreamMembers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GetStreamMembersExecutor.class})
@ExtendWith(SpringExtension.class)
class GetStreamMembersExecutorDiffblueTest {
  @Autowired
  private GetStreamMembersExecutor getStreamMembersExecutor;

  /**
   * Test {@link GetStreamMembersExecutor#execute(ActivityExecutorContext)}.
   * <p>
   * Method under test:
   * {@link GetStreamMembersExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext)")
  void testExecute() {
    // Arrange
    ActivityExecutorContext<GetStreamMembers> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException("Getting stream members for stream {}"));
    when(execution.getActivity()).thenReturn(new GetStreamMembers());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getStreamMembersExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }
}
