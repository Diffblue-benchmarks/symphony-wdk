package com.symphony.bdk.workflow.engine.executor.room;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
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
  @Autowired private GetRoomsExecutor getRoomsExecutor;

  /**
   * Test {@link GetRoomsExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetRooms} (default constructor) Limit is one.
   * </ul>
   *
   * <p>Method under test: {@link GetRoomsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetRooms (default constructor) Limit is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetRoomsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetRoomsLimitIsOne() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setLimit(1);

    ActivityExecutorContext<GetRooms> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(getRooms);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getRoomsExecutor.execute(execution));
    verify(execution).getActivity();
  }

  /**
   * Test {@link GetRoomsExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetRooms} (default constructor) Skip is one.
   * </ul>
   *
   * <p>Method under test: {@link GetRoomsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetRooms (default constructor) Skip is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetRoomsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetRoomsSkipIsOne() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setSkip(1);

    ActivityExecutorContext<GetRooms> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(getRooms);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getRoomsExecutor.execute(execution));
    verify(execution).getActivity();
  }

  /**
   * Test {@link GetRoomsExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetRooms} (default constructor).
   *   <li>Then calls {@link ActivityExecutorContext#bdk()}.
   * </ul>
   *
   * <p>Method under test: {@link GetRoomsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetRooms (default constructor); then calls bdk()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetRoomsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetRooms_thenCallsBdk() {
    // Arrange
    ActivityExecutorContext<GetRooms> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(new GetRooms());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getRoomsExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution, atLeast(1)).getActivity();
  }

  /**
   * Test {@link GetRoomsExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link Obo} (default constructor) UserId is one.
   *   <li>Then calls {@link GetRooms#getObo()}.
   * </ul>
   *
   * <p>Method under test: {@link GetRoomsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given Obo (default constructor) UserId is one; then calls getObo()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetRoomsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenOboUserIdIsOne_thenCallsGetObo() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");

    GetRooms getRooms = mock(GetRooms.class);
    when(getRooms.getObo()).thenReturn(obo);

    ActivityExecutorContext<GetRooms> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(getRooms);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getRoomsExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution, atLeast(1)).getActivity();
    verify(getRooms, atLeast(1)).getObo();
  }

  /**
   * Test {@link GetRoomsExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>When {@link ActivityExecutorContext} {@link ActivityExecutorContext#getActivity()} throw
   *       {@link IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link GetRoomsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); when ActivityExecutorContext getActivity() throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetRoomsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_whenActivityExecutorContextGetActivityThrowIllegalArgumentException() {
    // Arrange
    ActivityExecutorContext<GetRooms> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getRoomsExecutor.execute(execution));
    verify(execution).getActivity();
  }

  /**
   * Test {@link GetRoomsExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link GetRoomsExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext); then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.gen.api.model.V3RoomSearchResults GetRoomsExecutor.doOboWithCache(ActivityExecutorContext)"
  })
  void testDoOboWithCache_thenThrowIllegalArgumentException() {
    // Arrange
    ActivityExecutorContext<GetRooms> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getRoomsExecutor.doOboWithCache(execution));
    verify(execution).getActivity();
  }
}
