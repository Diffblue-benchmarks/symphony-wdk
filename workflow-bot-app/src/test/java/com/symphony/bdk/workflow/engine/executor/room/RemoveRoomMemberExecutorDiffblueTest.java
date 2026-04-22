package com.symphony.bdk.workflow.engine.executor.room;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.RemoveRoomMember;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RemoveRoomMemberExecutor.class})
@ExtendWith(SpringExtension.class)
class RemoveRoomMemberExecutorDiffblueTest {
  @Autowired private RemoveRoomMemberExecutor removeRoomMemberExecutor;

  /**
   * Test {@link RemoveRoomMemberExecutor#execute(ActivityExecutorContext)}.
   *
   * <p>Method under test: {@link RemoveRoomMemberExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RemoveRoomMemberExecutor.execute(ActivityExecutorContext)"})
  void testExecute() {
    // Arrange
    ActivityExecutorContext<RemoveRoomMember> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(new RemoveRoomMember());

    // Act
    removeRoomMemberExecutor.execute(execution);

    // Assert
    verify(execution).getActivity();
  }

  /**
   * Test {@link RemoveRoomMemberExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link RemoveRoomMember} with userIds.
   *   <li>Then calls {@code bdk().streams().removeMemberFromRoom(...)}.
   * </ul>
   *
   * <p>Method under test: {@link RemoveRoomMemberExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given RemoveRoomMember with userIds; then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RemoveRoomMemberExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenRemoveRoomMemberWithUserIds_thenThrowIllegalArgumentException() {
    // Arrange
    RemoveRoomMember removeRoomMember = new RemoveRoomMember();
    removeRoomMember.setStreamId("42");
    removeRoomMember.setUserIds(List.of(1L));

    ActivityExecutorContext<RemoveRoomMember> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(removeRoomMember);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> removeRoomMemberExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution, atLeast(1)).getActivity();
  }

  /**
   * Test {@link RemoveRoomMemberExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link Obo} (default constructor) UserId is one.
   *   <li>Then calls {@code bdk()} for OBO flow.
   * </ul>
   *
   * <p>Method under test: {@link RemoveRoomMemberExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given Obo UserId is one; then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RemoveRoomMemberExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenOboUserIdIsOne_thenThrowIllegalArgumentException() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");

    RemoveRoomMember removeRoomMember = mock(RemoveRoomMember.class);
    when(removeRoomMember.getObo()).thenReturn(obo);

    ActivityExecutorContext<RemoveRoomMember> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(removeRoomMember);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> removeRoomMemberExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution, atLeast(1)).getActivity();
    verify(removeRoomMember, atLeast(1)).getObo();
  }

  /**
   * Test {@link RemoveRoomMemberExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link RemoveRoomMemberExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doOboWithCache(ActivityExecutorContext); then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Void RemoveRoomMemberExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache_thenThrowIllegalArgumentException() {
    // Arrange
    ActivityExecutorContext<RemoveRoomMember> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> removeRoomMemberExecutor.doOboWithCache(execution));
    verify(execution).getActivity();
  }

  /**
   * Test {@link RemoveRoomMemberExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link RemoveRoomMember} with OBO username and userIds set.
   *   <li>Then calls {@code obo(authSession).streams().removeMemberFromRoom(...)} and returns null.
   * </ul>
   *
   * <p>Method under test: {@link RemoveRoomMemberExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doOboWithCache(ActivityExecutorContext); given OBO username and userIds; then call removeMemberFromRoom")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Void RemoveRoomMemberExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache_givenOboUsernameAndUserIds_thenCallRemoveMemberFromRoom() {
    // Arrange
    Obo obo = new Obo();
    obo.setUsername("janedoe");

    RemoveRoomMember removeRoomMember = new RemoveRoomMember();
    removeRoomMember.setStreamId("42");
    removeRoomMember.setUserIds(List.of(1L));
    removeRoomMember.setObo(obo);

    StreamService streamService = mock(StreamService.class);
    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(streamService);

    AuthSession authSession = mock(AuthSession.class);
    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.obo(Mockito.<String>any())).thenReturn(authSession);
    when(bdkGateway.obo(Mockito.<AuthSession>any())).thenReturn(oboServices);

    ActivityExecutorContext<RemoveRoomMember> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(removeRoomMember);
    when(execution.bdk()).thenReturn(bdkGateway);

    // Act
    removeRoomMemberExecutor.doOboWithCache(execution);

    // Assert
    verify(streamService).removeMemberFromRoom(1L, "42");
  }

  /**
   * Test {@link RemoveRoomMemberExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link RemoveRoomMember} with OBO userId and userIds set.
   *   <li>Then calls {@code obo(authSession).streams().removeMemberFromRoom(...)} and returns null.
   * </ul>
   *
   * <p>Method under test: {@link RemoveRoomMemberExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doOboWithCache(ActivityExecutorContext); given OBO userId and userIds; then call removeMemberFromRoom")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Void RemoveRoomMemberExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache_givenOboUserIdAndUserIds_thenCallRemoveMemberFromRoom() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);

    RemoveRoomMember removeRoomMember = new RemoveRoomMember();
    removeRoomMember.setStreamId("42");
    removeRoomMember.setUserIds(List.of(2L));
    removeRoomMember.setObo(obo);

    StreamService streamService = mock(StreamService.class);
    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(streamService);

    AuthSession authSession = mock(AuthSession.class);
    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.obo(Mockito.<Long>any())).thenReturn(authSession);
    when(bdkGateway.obo(Mockito.<AuthSession>any())).thenReturn(oboServices);

    ActivityExecutorContext<RemoveRoomMember> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(removeRoomMember);
    when(execution.bdk()).thenReturn(bdkGateway);

    // Act
    removeRoomMemberExecutor.doOboWithCache(execution);

    // Assert
    verify(streamService).removeMemberFromRoom(2L, "42");
  }
}
