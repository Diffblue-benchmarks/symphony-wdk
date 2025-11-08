package com.symphony.bdk.workflow.engine.executor.room;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.room.GetRooms;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GetRoomsExecutor.class})
@ExtendWith(SpringExtension.class)
class GetRoomsExecutorDiffblueTest {
  @Autowired
  private GetRoomsExecutor getRoomsExecutor;

  /**
   * Test {@link GetRoomsExecutor#execute(ActivityExecutorContext)}.
   * <p>
   * Method under test: {@link GetRoomsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetRoomsExecutor.execute(ActivityExecutorContext)"})
  void testExecute() {
    // Arrange
    ActivityExecutorContext<GetRooms> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException("Getting rooms"));
    when(execution.getActivity()).thenReturn(new GetRooms());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getRoomsExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution, atLeast(1)).getActivity();
  }

  /**
   * Test {@link GetRoomsExecutor#doOboWithCache(ActivityExecutorContext)}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRoomsExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "com.symphony.bdk.gen.api.model.V3RoomSearchResults GetRoomsExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache_thenThrowIllegalArgumentException() {
    // Arrange
    ActivityExecutorContext<GetRooms> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getRoomsExecutor.doOboWithCache(execution));
    verify(execution).getActivity();
  }
}
