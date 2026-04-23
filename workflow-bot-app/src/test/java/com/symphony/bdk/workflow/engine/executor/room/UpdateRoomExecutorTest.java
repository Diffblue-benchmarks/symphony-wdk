package com.symphony.bdk.workflow.engine.executor.room;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V3RoomAttributes;
import com.symphony.bdk.gen.api.model.V3RoomDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.UpdateRoom;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateRoomExecutorTest {

  @Test
  void shouldExecuteUpdateRoomWithAttributes() {
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    UpdateRoom activity = new UpdateRoom();
    activity.setStreamId("stream123");
    activity.setRoomName("New Room Name");
    activity.setRoomDescription("New Description");

    V3RoomDetail roomDetail = new V3RoomDetail();
    StreamService streamService = mock(StreamService.class);
    when(streamService.getRoomInfo("stream123")).thenReturn(roomDetail);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.streams()).thenReturn(streamService);

    ActivityExecutorContext<UpdateRoom> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    executor.execute(context);

    verify(streamService).updateRoom(eq("stream123"), any(V3RoomAttributes.class));
    verify(streamService, never()).setRoomActive(any(), any());
    verify(streamService).getRoomInfo("stream123");
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  void shouldExecuteUpdateRoomWithActiveStatus() {
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    UpdateRoom activity = new UpdateRoom();
    activity.setStreamId("stream456");
    activity.setActive(false);

    V3RoomDetail roomDetail = new V3RoomDetail();
    StreamService streamService = mock(StreamService.class);
    when(streamService.getRoomInfo("stream456")).thenReturn(roomDetail);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.streams()).thenReturn(streamService);

    ActivityExecutorContext<UpdateRoom> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    executor.execute(context);

    verify(streamService, never()).updateRoom(any(), any());
    verify(streamService).setRoomActive("stream456", false);
    verify(streamService).getRoomInfo("stream456");
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  void shouldExecuteUpdateRoomWithAttributesAndActiveStatus() {
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    UpdateRoom activity = new UpdateRoom();
    activity.setStreamId("stream789");
    activity.setRoomName("Updated Name");
    activity.setActive(true);

    V3RoomDetail roomDetail = new V3RoomDetail();
    StreamService streamService = mock(StreamService.class);
    when(streamService.getRoomInfo("stream789")).thenReturn(roomDetail);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.streams()).thenReturn(streamService);

    ActivityExecutorContext<UpdateRoom> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    executor.execute(context);

    verify(streamService).updateRoom(eq("stream789"), any(V3RoomAttributes.class));
    verify(streamService).setRoomActive("stream789", true);
    verify(streamService).getRoomInfo("stream789");
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  void shouldExecuteUpdateRoomWithNoChanges() {
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    UpdateRoom activity = new UpdateRoom();
    activity.setStreamId("streamNoChange");

    V3RoomDetail roomDetail = new V3RoomDetail();
    StreamService streamService = mock(StreamService.class);
    when(streamService.getRoomInfo("streamNoChange")).thenReturn(roomDetail);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.streams()).thenReturn(streamService);

    ActivityExecutorContext<UpdateRoom> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    executor.execute(context);

    verify(streamService, never()).updateRoom(any(), any());
    verify(streamService, never()).setRoomActive(any(), any());
    verify(streamService).getRoomInfo("streamNoChange");
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  void shouldExecuteUpdateRoomWithObo() {
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    Obo obo = new Obo();
    obo.setUsername("oboUser");

    UpdateRoom activity = new UpdateRoom();
    activity.setStreamId("oboStream");
    activity.setRoomName("OBO Room");
    activity.setObo(obo);

    V3RoomDetail roomDetail = new V3RoomDetail();
    StreamService oboStreamService = mock(StreamService.class);
    when(oboStreamService.getRoomInfo("oboStream")).thenReturn(roomDetail);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(oboStreamService);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.obo("oboUser")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);

    ActivityExecutorContext<UpdateRoom> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    executor.execute(context);

    verify(oboStreamService).updateRoom(eq("oboStream"), any(V3RoomAttributes.class));
    verify(oboStreamService).getRoomInfo("oboStream");
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  void shouldThrowWhenOboAndActiveIsSet() {
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    Obo obo = new Obo();
    obo.setUsername("oboUser");

    UpdateRoom activity = new UpdateRoom();
    activity.setStreamId("oboStream");
    activity.setActive(true);
    activity.setObo(obo);
    activity.setId("my-activity");

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService oboStreamService = mock(StreamService.class);
    when(oboServices.streams()).thenReturn(oboStreamService);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.obo("oboUser")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);

    ActivityExecutorContext<UpdateRoom> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Room active status update")
        .hasMessageContaining("my-activity")
        .hasMessageContaining("not OBO enabled");
  }

  @Test
  void shouldExecuteUpdateRoomWithKeywords() {
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    Map<String, String> keywords = new HashMap<>();
    keywords.put("key1", "value1");
    keywords.put("key2", "value2");

    UpdateRoom activity = new UpdateRoom();
    activity.setStreamId("keywordStream");
    activity.setKeywords(keywords);

    V3RoomDetail roomDetail = new V3RoomDetail();
    StreamService streamService = mock(StreamService.class);
    when(streamService.getRoomInfo("keywordStream")).thenReturn(roomDetail);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.streams()).thenReturn(streamService);

    ActivityExecutorContext<UpdateRoom> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    executor.execute(context);

    verify(streamService).updateRoom(eq("keywordStream"), any(V3RoomAttributes.class));
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  void shouldExecuteUpdateRoomWithAllBooleanAttributes() {
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    UpdateRoom activity = new UpdateRoom();
    activity.setStreamId("allBoolStream");
    activity.setMembersCanInvite(true);
    activity.setDiscoverable(false);
    activity.setIsPublic(true);
    activity.setReadOnly(false);
    activity.setCopyProtected(true);
    activity.setCrossPod(false);
    activity.setViewHistory(true);
    activity.setMultilateralRoom(false);

    V3RoomDetail roomDetail = new V3RoomDetail();
    StreamService streamService = mock(StreamService.class);
    when(streamService.getRoomInfo("allBoolStream")).thenReturn(roomDetail);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.streams()).thenReturn(streamService);

    ActivityExecutorContext<UpdateRoom> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    executor.execute(context);

    verify(streamService).updateRoom(eq("allBoolStream"), any(V3RoomAttributes.class));
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  void shouldExecuteUpdateRoomWithOboAndNoAttributes() {
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    Obo obo = new Obo();
    obo.setUsername("oboUser");

    UpdateRoom activity = new UpdateRoom();
    activity.setStreamId("oboNoAttrStream");
    activity.setObo(obo);

    V3RoomDetail roomDetail = new V3RoomDetail();
    StreamService oboStreamService = mock(StreamService.class);
    when(oboStreamService.getRoomInfo("oboNoAttrStream")).thenReturn(roomDetail);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(oboStreamService);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.obo("oboUser")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);

    ActivityExecutorContext<UpdateRoom> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    executor.execute(context);

    verify(oboStreamService, never()).updateRoom(any(), any());
    verify(oboStreamService).getRoomInfo("oboNoAttrStream");
    verify(context).setOutputVariable("room", roomDetail);
  }
}
