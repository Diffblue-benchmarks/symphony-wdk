package com.symphony.bdk.workflow.engine.executor.room;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.OboStreamService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.DemoteRoomOwner;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DemoteRoomOwnerExecutor.class})
@ExtendWith(SpringExtension.class)
class DemoteRoomOwnerExecutorDiffblueTest {
  @Autowired private DemoteRoomOwnerExecutor demoteRoomOwnerExecutor;

  /**
   * Test {@link DemoteRoomOwnerExecutor#execute(ActivityExecutorContext)}.
   *
   * <p>Method under test: {@link DemoteRoomOwnerExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DemoteRoomOwnerExecutor.execute(ActivityExecutorContext)"})
  void testExecute() {
    // Arrange
    ActivityExecutorContext<DemoteRoomOwner> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(new DemoteRoomOwner());

    // Act
    demoteRoomOwnerExecutor.execute(execution);

    // Assert
    verify(execution).getActivity();
  }

  /**
   * Test {@link DemoteRoomOwnerExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link DemoteRoomOwner} with user IDs and stream ID.
   *   <li>Then calls {@link StreamService#demoteUserToRoomParticipant(Long, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DemoteRoomOwnerExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given DemoteRoomOwner with user IDs; then calls demoteUserToRoomParticipant")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void DemoteRoomOwnerExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUserIds_thenCallsDemoteUserToRoomParticipant() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();
    demoteRoomOwner.setStreamId("streamId");
    demoteRoomOwner.setUserIds(List.of(1L));

    StreamService streamService = mock(StreamService.class);
    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.streams()).thenReturn(streamService);

    ActivityExecutorContext<DemoteRoomOwner> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(demoteRoomOwner);
    when(execution.bdk()).thenReturn(bdkGateway);

    // Act
    demoteRoomOwnerExecutor.execute(execution);

    // Assert
    verify(streamService).demoteUserToRoomParticipant(1L, "streamId");
  }

  /**
   * Test {@link DemoteRoomOwnerExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link DemoteRoomOwner} with OBO and user IDs.
   *   <li>Then calls {@link OboStreamService#demoteUserToRoomParticipant(Long, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DemoteRoomOwnerExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given DemoteRoomOwner with OBO; then calls obo demoteUserToRoomParticipant")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void DemoteRoomOwnerExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenObo_thenCallsOboDemoteUserToRoomParticipant() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();
    demoteRoomOwner.setStreamId("streamId");
    demoteRoomOwner.setUserIds(List.of(1L));
    Obo obo = new Obo();
    obo.setUsername("username");
    demoteRoomOwner.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboStreamService oboStreamService = mock(OboStreamService.class);
    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(oboStreamService);

    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.obo("username")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);

    ActivityExecutorContext<DemoteRoomOwner> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(demoteRoomOwner);
    when(execution.bdk()).thenReturn(bdkGateway);

    // Act
    demoteRoomOwnerExecutor.execute(execution);

    // Assert
    verify(oboStreamService).demoteUserToRoomParticipant(1L, "streamId");
  }

  /**
   * Test {@link DemoteRoomOwnerExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <p>Method under test: {@link DemoteRoomOwnerExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext)")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"java.lang.Void DemoteRoomOwnerExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache() {
    // Arrange
    ActivityExecutorContext<DemoteRoomOwner> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> demoteRoomOwnerExecutor.doOboWithCache(execution));
    verify(execution).getActivity();
  }
}
