package com.symphony.bdk.workflow.engine.executor.room;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.room.UpdateRoom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UpdateRoomExecutor.class})
@ExtendWith(SpringExtension.class)
class UpdateRoomExecutorDiffblueTest {
  @Autowired
  private UpdateRoomExecutor updateRoomExecutor;

  /**
   * Method under test:
   * {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute() {
    // Arrange
    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException("foo"));
    when(execution.getActivity()).thenReturn(new UpdateRoom());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Method under test:
   * {@link UpdateRoomExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  void testDoOboWithCache() {
    // Arrange
    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.doOboWithCache(execution));
    verify(execution).getActivity();
  }
}
