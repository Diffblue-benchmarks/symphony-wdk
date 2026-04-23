package com.symphony.bdk.workflow.engine.executor.room;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.Stream;
import com.symphony.bdk.gen.api.model.V3RoomDetail;
import com.symphony.bdk.gen.api.model.V3RoomSystemInfo;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.CreateRoom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRoomExecutorTest {

  @InjectMocks
  private CreateRoomExecutor executor;

  @Mock
  private ActivityExecutorContext<CreateRoom> context;

  @Mock
  private BdkGateway bdk;

  @Mock
  private StreamService streamService;

  private V3RoomDetail stubRoomDetail(String roomId) {
    V3RoomSystemInfo sysInfo = mock(V3RoomSystemInfo.class);
    when(sysInfo.getId()).thenReturn(roomId);
    V3RoomDetail detail = mock(V3RoomDetail.class);
    when(detail.getRoomSystemInfo()).thenReturn(sysInfo);
    return detail;
  }

  @Test
  void shouldCreateRoomWithAttributesWhenNoUids() {
    CreateRoom activity = new CreateRoom();
    activity.setRoomName("test-room");
    activity.setRoomDescription("description");

    V3RoomDetail roomDetail = stubRoomDetail("stream-123");
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.create(any())).thenReturn(roomDetail);

    executor.execute(context);

    verify(streamService).create(any());
    verify(context).setOutputVariable("roomId", "stream-123");
  }

  @Test
  void shouldCreateRoomWithUidsOnlyWhenNameAndDescriptionAreEmpty() {
    CreateRoom activity = new CreateRoom();
    activity.setUserIds(Arrays.asList(111L, 222L));

    Stream stream = mock(Stream.class);
    when(stream.getId()).thenReturn("mim-456");
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.create(Arrays.asList(111L, 222L))).thenReturn(stream);

    executor.execute(context);

    verify(streamService).create(Arrays.asList(111L, 222L));
    verify(context).setOutputVariable("roomId", "mim-456");
  }

  @Test
  void shouldCreateRoomWithUidsAndAttributesWhenNameAndDescriptionSet() {
    CreateRoom activity = new CreateRoom();
    activity.setRoomName("my-room");
    activity.setRoomDescription("my-desc");
    activity.setUserIds(Arrays.asList(333L));

    V3RoomDetail roomDetail = stubRoomDetail("room-789");
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.create(any())).thenReturn(roomDetail);

    executor.execute(context);

    verify(streamService).create(any());
    verify(streamService).addMemberToRoom(333L, "room-789");
    verify(context).setOutputVariable("roomId", "room-789");
  }

  @Test
  void shouldCreateRoomWithKeywords() {
    CreateRoom activity = new CreateRoom();
    activity.setRoomName("kw-room");
    activity.setRoomDescription("kw-desc");
    Map<String, String> keywords = new HashMap<>();
    keywords.put("key1", "val1");
    activity.setKeywords(keywords);

    V3RoomDetail roomDetail = stubRoomDetail("kw-stream");
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.create(any())).thenReturn(roomDetail);

    executor.execute(context);

    verify(streamService).create(any());
    verify(context).setOutputVariable("roomId", "kw-stream");
  }

  @Test
  void shouldDoOboWithUsernameWhenOboIsSetAndUidsWithNameAndDescription() {
    CreateRoom activity = new CreateRoom();
    activity.setRoomName("obo-room");
    activity.setRoomDescription("obo-desc");
    activity.setUserIds(Arrays.asList(444L));
    Obo obo = new Obo();
    obo.setUsername("obo-user");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService oboStreamService = mock(StreamService.class);
    V3RoomDetail roomDetail = stubRoomDetail("obo-stream-1");
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("obo-user")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.create(any())).thenReturn(roomDetail);

    executor.execute(context);

    verify(oboStreamService).create(any());
    verify(oboStreamService).addMemberToRoom(444L, "obo-stream-1");
    verify(context).setOutputVariable("roomId", "obo-stream-1");
  }

  @Test
  void shouldDoOboWithUsernameWhenOboIsSetAndUidsOnly() {
    CreateRoom activity = new CreateRoom();
    activity.setUserIds(Arrays.asList(555L));
    Obo obo = new Obo();
    obo.setUsername("obo-user2");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService oboStreamService = mock(StreamService.class);
    Stream stream = mock(Stream.class);
    when(stream.getId()).thenReturn("obo-mim");
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("obo-user2")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.create(Arrays.asList(555L))).thenReturn(stream);

    executor.execute(context);

    verify(oboStreamService).create(Arrays.asList(555L));
    verify(context).setOutputVariable("roomId", "obo-mim");
  }

  @Test
  void shouldDoOboWithUsernameWhenOboIsSetAndNoUids() {
    CreateRoom activity = new CreateRoom();
    activity.setRoomName("obo-room-no-uids");
    Obo obo = new Obo();
    obo.setUsername("obo-user3");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService oboStreamService = mock(StreamService.class);
    V3RoomDetail roomDetail = stubRoomDetail("obo-room-stream");
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("obo-user3")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.create(any())).thenReturn(roomDetail);

    executor.execute(context);

    verify(oboStreamService).create(any());
    verify(oboStreamService, never()).addMemberToRoom(anyLong(), any());
    verify(context).setOutputVariable("roomId", "obo-room-stream");
  }

  @Test
  void shouldDoOboWithUserIdWhenOboUserIdIsSet() {
    CreateRoom activity = new CreateRoom();
    activity.setRoomName("obo-userid-room");
    Obo obo = new Obo();
    obo.setUserId(999L);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService oboStreamService = mock(StreamService.class);
    V3RoomDetail roomDetail = stubRoomDetail("obo-userid-stream");
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo(999L)).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.create(any())).thenReturn(roomDetail);

    executor.execute(context);

    verify(bdk).obo(999L);
    verify(oboStreamService).create(any());
    verify(context).setOutputVariable("roomId", "obo-userid-stream");
  }
}
