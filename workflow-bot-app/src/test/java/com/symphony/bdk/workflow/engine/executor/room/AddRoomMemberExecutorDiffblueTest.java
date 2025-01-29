package com.symphony.bdk.workflow.engine.executor.room;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.room.AddRoomMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AddRoomMemberExecutor.class})
@ExtendWith(SpringExtension.class)
class AddRoomMemberExecutorDiffblueTest {
  @Autowired
  private AddRoomMemberExecutor addRoomMemberExecutor;

  /**
   * Test {@link AddRoomMemberExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link AddRoomMember} (default constructor).</li>
   *   <li>Then calls {@link ActivityExecutorContext#getActivity()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddRoomMemberExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given AddRoomMember (default constructor); then calls getActivity()")
  void testExecute_givenAddRoomMember_thenCallsGetActivity() {
    // Arrange
    ActivityExecutorContext<AddRoomMember> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(new AddRoomMember());

    // Act
    addRoomMemberExecutor.execute(execution);

    // Assert
    verify(execution).getActivity();
  }
}
