package com.symphony.bdk.workflow.engine.executor.room;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.room.RemoveRoomMember;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RemoveRoomMemberExecutor.class})
@ExtendWith(SpringExtension.class)
class RemoveRoomMemberExecutorDiffblueTest {
  @Autowired
  private RemoveRoomMemberExecutor removeRoomMemberExecutor;

  /**
   * Method under test:
   * {@link RemoveRoomMemberExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute() {
    // Arrange
    ActivityExecutorContext<RemoveRoomMember> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(new RemoveRoomMember());

    // Act
    removeRoomMemberExecutor.execute(execution);

    // Assert that nothing has changed
    verify(execution).getActivity();
  }
}
