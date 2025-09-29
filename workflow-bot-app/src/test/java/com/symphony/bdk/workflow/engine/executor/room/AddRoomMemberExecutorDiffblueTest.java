package com.symphony.bdk.workflow.engine.executor.room;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.room.AddRoomMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AddRoomMemberExecutor.class})
@ExtendWith(SpringExtension.class)
class AddRoomMemberExecutorDiffblueTest {
  @Autowired private AddRoomMemberExecutor addRoomMemberExecutor;

  /**
   * Test {@link AddRoomMemberExecutor#execute(ActivityExecutorContext)}.
   *
   * <p>Method under test: {@link AddRoomMemberExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AddRoomMemberExecutor.execute(ActivityExecutorContext)"})
  void testExecute() {
    // Arrange
    ActivityExecutorContext<AddRoomMember> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(new AddRoomMember());

    // Act
    addRoomMemberExecutor.execute(execution);

    // Assert
    verify(execution).getActivity();
  }
}
