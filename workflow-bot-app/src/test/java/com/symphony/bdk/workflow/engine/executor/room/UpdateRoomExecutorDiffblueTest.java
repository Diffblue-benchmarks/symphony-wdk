package com.symphony.bdk.workflow.engine.executor.room;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.room.UpdateRoom;
import org.junit.jupiter.api.DisplayName;
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
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   * <p>
   * Method under test:
   * {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext)")
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
   * Test {@link UpdateRoomExecutor#doOboWithCache(ActivityExecutorContext)}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UpdateRoomExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext); then throw IllegalArgumentException")
  void testDoOboWithCache_thenThrowIllegalArgumentException() {
    // Arrange
    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.doOboWithCache(execution));
    verify(execution).getActivity();
  }
}
