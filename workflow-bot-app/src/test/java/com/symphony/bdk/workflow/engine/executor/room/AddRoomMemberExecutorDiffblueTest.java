package com.symphony.bdk.workflow.engine.executor.room;

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
import com.symphony.bdk.workflow.swadl.v1.activity.room.AddRoomMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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

  /**
   * Test {@link AddRoomMemberExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given activity with user IDs, calls addMemberToRoom for each user.
   * </ul>
   *
   * <p>Method under test: {@link AddRoomMemberExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given user IDs, calls addMemberToRoom for each user")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void AddRoomMemberExecutor.execute(ActivityExecutorContext)"})
  void testExecute_withUserIds_callsAddMemberToRoom() {
    // Arrange
    AddRoomMemberExecutor executor = new AddRoomMemberExecutor();

    AddRoomMember addRoomMember = new AddRoomMember();
    addRoomMember.setStreamId("streamId");
    addRoomMember.setUserIds(List.of(1L, 2L));

    StreamService streamService = mock(StreamService.class);
    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.streams()).thenReturn(streamService);

    ActivityExecutorContext<AddRoomMember> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(addRoomMember);
    when(execution.bdk()).thenReturn(bdkGateway);

    // Act
    executor.execute(execution);

    // Assert
    verify(streamService).addMemberToRoom(1L, "streamId");
    verify(streamService).addMemberToRoom(2L, "streamId");
  }

  /**
   * Test {@link AddRoomMemberExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given activity with OBO username, delegates to doOboWithCache.
   * </ul>
   *
   * <p>Method under test: {@link AddRoomMemberExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given OBO username, delegates to doOboWithCache")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void AddRoomMemberExecutor.execute(ActivityExecutorContext)"})
  void testExecute_withObo_delegatesToDoOboWithCache() {
    // Arrange
    AddRoomMemberExecutor executor = new AddRoomMemberExecutor();

    Obo obo = new Obo();
    obo.setUsername("oboUser");

    AddRoomMember addRoomMember = new AddRoomMember();
    addRoomMember.setStreamId("streamId");
    addRoomMember.setUserIds(List.of(42L));
    addRoomMember.setObo(obo);

    StreamService streamService = mock(StreamService.class);
    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(streamService);

    AuthSession authSession = mock(AuthSession.class);
    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.obo(Mockito.<String>any())).thenReturn(authSession);
    when(bdkGateway.obo(Mockito.<AuthSession>any())).thenReturn(oboServices);

    ActivityExecutorContext<AddRoomMember> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(addRoomMember);
    when(execution.bdk()).thenReturn(bdkGateway);

    // Act
    executor.execute(execution);

    // Assert
    verify(streamService).addMemberToRoom(42L, "streamId");
  }

  /**
   * Test {@link AddRoomMemberExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Calls addMemberToRoom via OBO session for each user ID.
   * </ul>
   *
   * <p>Method under test: {@link AddRoomMemberExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext); calls addMemberToRoom via OBO session")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"Void AddRoomMemberExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache_callsAddMemberToRoomViaOboSession() throws Exception {
    // Arrange
    AddRoomMemberExecutor executor = new AddRoomMemberExecutor();

    Obo obo = new Obo();
    obo.setUsername("oboUser");

    AddRoomMember addRoomMember = new AddRoomMember();
    addRoomMember.setStreamId("roomStreamId");
    addRoomMember.setUserIds(List.of(10L, 20L));
    addRoomMember.setObo(obo);

    StreamService streamService = mock(StreamService.class);
    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(streamService);

    AuthSession authSession = mock(AuthSession.class);
    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.obo(Mockito.<String>any())).thenReturn(authSession);
    when(bdkGateway.obo(Mockito.<AuthSession>any())).thenReturn(oboServices);

    ActivityExecutorContext<AddRoomMember> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(addRoomMember);
    when(execution.bdk()).thenReturn(bdkGateway);

    // Act
    executor.doOboWithCache(execution);

    // Assert
    verify(streamService).addMemberToRoom(10L, "roomStreamId");
    verify(streamService).addMemberToRoom(20L, "roomStreamId");
  }
}
