package com.symphony.bdk.workflow.engine.executor.room;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.room.PromoteRoomOwner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PromoteRoomOwnerExecutor.class})
@ExtendWith(SpringExtension.class)
class PromoteRoomOwnerExecutorDiffblueTest {
  @Autowired private PromoteRoomOwnerExecutor promoteRoomOwnerExecutor;

  /**
   * Test {@link PromoteRoomOwnerExecutor#execute(ActivityExecutorContext)}.
   *
   * <p>Method under test: {@link PromoteRoomOwnerExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void PromoteRoomOwnerExecutor.execute(ActivityExecutorContext)"})
  void testExecute() {
    // Arrange
    ActivityExecutorContext<PromoteRoomOwner> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(new PromoteRoomOwner());

    // Act
    promoteRoomOwnerExecutor.execute(execution);

    // Assert
    verify(execution).getActivity();
  }
}
