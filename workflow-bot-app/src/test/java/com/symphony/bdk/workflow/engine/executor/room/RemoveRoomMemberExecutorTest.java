package com.symphony.bdk.workflow.engine.executor.room;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.RemoveRoomMember;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class RemoveRoomMemberExecutorTest {

  private RemoveRoomMemberExecutor executor;

  @Mock
  private ActivityExecutorContext<RemoveRoomMember> context;

  @Mock
  private BdkGateway bdkGateway;

  @Mock
  private StreamService streamService;

  @Mock
  private OboServices oboServices;

  @Mock
  private AuthSession authSession;

  @BeforeEach
  void setUp() {
    executor = new RemoveRoomMemberExecutor();
    when(context.bdk()).thenReturn(bdkGateway);
  }

  @Test
  void execute_shouldRemoveMemberWithoutObo() {
    // Arrange
    RemoveRoomMember activity = new RemoveRoomMember();
    activity.setStreamId("room123");
    activity.setUserIds(List.of(12345L));
    activity.setObo(null);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.streams()).thenReturn(streamService);

    // Act
    executor.execute(context);

    // Assert
    verify(streamService).removeMemberFromRoom(12345L, "room123");
  }

  @Test
  void execute_shouldRemoveMultipleMembersWithoutObo() {
    // Arrange
    RemoveRoomMember activity = new RemoveRoomMember();
    activity.setStreamId("room456");
    activity.setUserIds(List.of(11111L, 22222L, 33333L));
    activity.setObo(null);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.streams()).thenReturn(streamService);

    // Act
    executor.execute(context);

    // Assert
    verify(streamService).removeMemberFromRoom(11111L, "room456");
    verify(streamService).removeMemberFromRoom(22222L, "room456");
    verify(streamService).removeMemberFromRoom(33333L, "room456");
  }

  @Test
  void execute_shouldRemoveMemberWithOboUsername() {
    // Arrange
    RemoveRoomMember activity = new RemoveRoomMember();
    activity.setStreamId("room789");
    activity.setUserIds(List.of(67890L));
    Obo obo = new Obo();
    obo.setUsername("testuser");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.obo("testuser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);

    // Act
    executor.execute(context);

    // Assert
    verify(bdkGateway).obo("testuser");
    verify(bdkGateway).obo(authSession);
    verify(streamService).removeMemberFromRoom(67890L, "room789");
  }

  @Test
  void execute_shouldRemoveMemberWithOboUserId() {
    // Arrange
    RemoveRoomMember activity = new RemoveRoomMember();
    activity.setStreamId("room999");
    activity.setUserIds(List.of(11111L));
    Obo obo = new Obo();
    obo.setUserId(99999L);
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.obo(99999L)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);

    // Act
    executor.execute(context);

    // Assert
    verify(bdkGateway).obo(99999L);
    verify(bdkGateway).obo(authSession);
    verify(streamService).removeMemberFromRoom(11111L, "room999");
  }

  @Test
  void doOboWithCache_shouldRemoveMemberWithObo() {
    // Arrange
    RemoveRoomMember activity = new RemoveRoomMember();
    activity.setStreamId("room555");
    activity.setUserIds(List.of(22222L));
    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);

    // Act
    Void result = executor.doOboWithCache(context);

    // Assert
    assertThat(result).isNull();
    verify(bdkGateway).obo("obouser");
    verify(bdkGateway).obo(authSession);
    verify(streamService).removeMemberFromRoom(22222L, "room555");
  }

  @Test
  void doOboWithCache_shouldRemoveMultipleMembersWithObo() {
    // Arrange
    RemoveRoomMember activity = new RemoveRoomMember();
    activity.setStreamId("room777");
    activity.setUserIds(List.of(44444L, 55555L));
    Obo obo = new Obo();
    obo.setUserId(88888L);
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.obo(88888L)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);

    // Act
    Void result = executor.doOboWithCache(context);

    // Assert
    assertThat(result).isNull();
    verify(bdkGateway).obo(88888L);
    verify(streamService).removeMemberFromRoom(44444L, "room777");
    verify(streamService).removeMemberFromRoom(55555L, "room777");
  }
}
