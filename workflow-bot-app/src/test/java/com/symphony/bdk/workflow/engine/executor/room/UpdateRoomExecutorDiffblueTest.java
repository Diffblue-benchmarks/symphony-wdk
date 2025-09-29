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
import com.symphony.bdk.workflow.swadl.v1.activity.room.UpdateRoom;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UpdateRoomExecutor.class})
@ExtendWith(SpringExtension.class)
class UpdateRoomExecutorDiffblueTest {
  @Autowired private UpdateRoomExecutor updateRoomExecutor;

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link Obo} (default constructor) UserId is one.
   *   <li>Then calls {@link UpdateRoom#getObo()}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given Obo (default constructor) UserId is one; then calls getObo()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenOboUserIdIsOne_thenCallsGetObo() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");

    UpdateRoom updateRoom = mock(UpdateRoom.class);
    when(updateRoom.getObo()).thenReturn(obo);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution, atLeast(1)).getActivity();
    verify(updateRoom, atLeast(1)).getObo();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given UpdateRoom (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoom() {
    // Arrange
    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(new UpdateRoom());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) Active is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) Active is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomActiveIsTrue() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setActive(true);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) CopyProtected is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) CopyProtected is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomCopyProtectedIsTrue() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setCopyProtected(true);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) CrossPod is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) CrossPod is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomCrossPodIsTrue() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setCrossPod(true);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) Discoverable is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) Discoverable is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomDiscoverableIsTrue() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setDiscoverable(true);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) IsPublic is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) IsPublic is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomIsPublicIsTrue() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setIsPublic(true);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) Keywords is {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) Keywords is HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomKeywordsIsHashMap() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setKeywords(new HashMap<>());

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) MembersCanInvite is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) MembersCanInvite is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomMembersCanInviteIsTrue() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setMembersCanInvite(true);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) MultilateralRoom is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) MultilateralRoom is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomMultilateralRoomIsTrue() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setMultilateralRoom(true);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) ReadOnly is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) ReadOnly is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomReadOnlyIsTrue() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setReadOnly(true);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) RoomDescription is {@code Activity}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) RoomDescription is 'Activity'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomRoomDescriptionIsActivity() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setRoomDescription("Activity");

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) RoomName is {@code Activity}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) RoomName is 'Activity'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomRoomNameIsActivity() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setRoomName("Activity");

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) ViewHistory is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) ViewHistory is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomViewHistoryIsTrue() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setViewHistory(true);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>When {@link ActivityExecutorContext} {@link ActivityExecutorContext#getActivity()} throw
   *       {@link IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); when ActivityExecutorContext getActivity() throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_whenActivityExecutorContextGetActivityThrowIllegalArgumentException() {
    // Arrange
    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext); then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.gen.api.model.V3RoomDetail UpdateRoomExecutor.doOboWithCache(ActivityExecutorContext)"
  })
  void testDoOboWithCache_thenThrowIllegalArgumentException() {
    // Arrange
    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> updateRoomExecutor.doOboWithCache(execution));
    verify(execution).getActivity();
  }
}
