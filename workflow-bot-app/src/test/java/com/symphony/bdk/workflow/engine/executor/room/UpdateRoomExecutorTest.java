package com.symphony.bdk.workflow.engine.executor.room;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.OboStreamService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.RoomTag;
import com.symphony.bdk.gen.api.model.V3RoomAttributes;
import com.symphony.bdk.gen.api.model.V3RoomDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.UpdateRoom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UpdateRoomExecutorTest {

  private UpdateRoomExecutor executor;
  private ActivityExecutorContext<UpdateRoom> context;
  private BdkGateway bdkGateway;
  private StreamService streamService;
  private UpdateRoom activity;

  @BeforeEach
  void setUp() {
    executor = new UpdateRoomExecutor();
    context = mock(ActivityExecutorContext.class);
    bdkGateway = mock(BdkGateway.class);
    streamService = mock(StreamService.class);
    activity = new UpdateRoom();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.streams()).thenReturn(streamService);
  }

  @Test
  void shouldUpdateRoomAttributesOnly() {
    String streamId = "stream-123";
    activity.setStreamId(streamId);
    activity.setRoomName("New Room Name");
    activity.setRoomDescription("New Description");

    V3RoomDetail roomDetail = new V3RoomDetail();
    when(streamService.getRoomInfo(eq(streamId))).thenReturn(roomDetail);

    executor.execute(context);

    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(eq(streamId), attributesCaptor.capture());
    verify(streamService, never()).setRoomActive(any(), any());
    verify(streamService).getRoomInfo(eq(streamId));
    verify(context).setOutputVariable(eq("room"), eq(roomDetail));

    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getName()).isEqualTo("New Room Name");
    assertThat(capturedAttributes.getDescription()).isEqualTo("New Description");
  }

  @Test
  void shouldUpdateRoomActiveStatusOnly() {
    String streamId = "stream-456";
    activity.setStreamId(streamId);
    activity.setActive(true);

    V3RoomDetail roomDetail = new V3RoomDetail();
    when(streamService.getRoomInfo(eq(streamId))).thenReturn(roomDetail);

    executor.execute(context);

    verify(streamService, never()).updateRoom(any(), any());
    verify(streamService).setRoomActive(eq(streamId), eq(true));
    verify(streamService).getRoomInfo(eq(streamId));
    verify(context).setOutputVariable(eq("room"), eq(roomDetail));
  }

  @Test
  void shouldUpdateBothAttributesAndActiveStatus() {
    String streamId = "stream-789";
    activity.setStreamId(streamId);
    activity.setRoomName("Updated Room");
    activity.setActive(false);

    V3RoomDetail roomDetail = new V3RoomDetail();
    when(streamService.getRoomInfo(eq(streamId))).thenReturn(roomDetail);

    executor.execute(context);

    verify(streamService).updateRoom(eq(streamId), any(V3RoomAttributes.class));
    verify(streamService).setRoomActive(eq(streamId), eq(false));
    verify(streamService).getRoomInfo(eq(streamId));
    verify(context).setOutputVariable(eq("room"), eq(roomDetail));
  }

  @Test
  void shouldNotUpdateWhenNoAttributesSet() {
    String streamId = "stream-000";
    activity.setStreamId(streamId);

    V3RoomDetail roomDetail = new V3RoomDetail();
    when(streamService.getRoomInfo(eq(streamId))).thenReturn(roomDetail);

    executor.execute(context);

    verify(streamService, never()).updateRoom(any(), any());
    verify(streamService, never()).setRoomActive(any(), any());
    verify(streamService).getRoomInfo(eq(streamId));
    verify(context).setOutputVariable(eq("room"), eq(roomDetail));
  }

  @Test
  void shouldUpdateRoomWithAllAttributes() {
    String streamId = "stream-full";
    activity.setStreamId(streamId);
    activity.setRoomName("Full Room");
    activity.setRoomDescription("Full Description");

    Map<String, String> keywords = new HashMap<>();
    keywords.put("key1", "value1");
    keywords.put("key2", "value2");
    activity.setKeywords(keywords);

    activity.setMembersCanInvite(true);
    activity.setDiscoverable(false);
    activity.setIsPublic(true);
    activity.setReadOnly(false);
    activity.setCopyProtected(true);
    activity.setCrossPod(false);
    activity.setViewHistory(true);
    activity.setMultilateralRoom(false);

    V3RoomDetail roomDetail = new V3RoomDetail();
    when(streamService.getRoomInfo(eq(streamId))).thenReturn(roomDetail);

    executor.execute(context);

    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(eq(streamId), attributesCaptor.capture());

    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getName()).isEqualTo("Full Room");
    assertThat(capturedAttributes.getDescription()).isEqualTo("Full Description");
    assertThat(capturedAttributes.getKeywords()).hasSize(2);
    assertThat(capturedAttributes.getMembersCanInvite()).isTrue();
    assertThat(capturedAttributes.getDiscoverable()).isFalse();
    assertThat(capturedAttributes.getPublic()).isTrue();
    assertThat(capturedAttributes.getReadOnly()).isFalse();
    assertThat(capturedAttributes.getCopyProtected()).isTrue();
    assertThat(capturedAttributes.getCrossPod()).isFalse();
    assertThat(capturedAttributes.getViewHistory()).isTrue();
    assertThat(capturedAttributes.getMultiLateralRoom()).isFalse();
  }

  @Test
  void shouldConvertKeywordsToRoomTags() {
    String streamId = "stream-keywords";
    activity.setStreamId(streamId);

    Map<String, String> keywords = new HashMap<>();
    keywords.put("department", "Engineering");
    keywords.put("project", "BDK");
    activity.setKeywords(keywords);

    V3RoomDetail roomDetail = new V3RoomDetail();
    when(streamService.getRoomInfo(eq(streamId))).thenReturn(roomDetail);

    executor.execute(context);

    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(eq(streamId), attributesCaptor.capture());

    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    List<RoomTag> tags = capturedAttributes.getKeywords();
    assertThat(tags).hasSize(2);
    assertThat(tags).anyMatch(tag -> "department".equals(tag.getKey()) && "Engineering".equals(tag.getValue()));
    assertThat(tags).anyMatch(tag -> "project".equals(tag.getKey()) && "BDK".equals(tag.getValue()));
  }

  @Test
  void shouldUpdateRoomWithOboUsername() {
    String streamId = "stream-obo-user";
    activity.setStreamId(streamId);
    activity.setRoomName("OBO Room");

    Obo obo = new Obo();
    obo.setUsername("test.user");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    OboStreamService oboStreamService = mock(OboStreamService.class);

    when(bdkGateway.obo(eq("test.user"))).thenReturn(authSession);
    when(bdkGateway.obo(eq(authSession))).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomDetail roomDetail = new V3RoomDetail();
    when(oboStreamService.getRoomInfo(eq(streamId))).thenReturn(roomDetail);

    executor.execute(context);

    verify(oboStreamService).updateRoom(eq(streamId), any(V3RoomAttributes.class));
    verify(oboStreamService).getRoomInfo(eq(streamId));
    verify(context).setOutputVariable(eq("room"), eq(roomDetail));
  }

  @Test
  void shouldUpdateRoomWithOboUserId() {
    String streamId = "stream-obo-id";
    activity.setStreamId(streamId);
    activity.setRoomDescription("OBO Description");

    Obo obo = new Obo();
    obo.setUserId(12345L);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    OboStreamService oboStreamService = mock(OboStreamService.class);

    when(bdkGateway.obo(eq(12345L))).thenReturn(authSession);
    when(bdkGateway.obo(eq(authSession))).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomDetail roomDetail = new V3RoomDetail();
    when(oboStreamService.getRoomInfo(eq(streamId))).thenReturn(roomDetail);

    executor.execute(context);

    verify(oboStreamService).updateRoom(eq(streamId), any(V3RoomAttributes.class));
    verify(oboStreamService).getRoomInfo(eq(streamId));
    verify(context).setOutputVariable(eq("room"), eq(roomDetail));
  }

  @Test
  void shouldThrowExceptionWhenActiveStatusUpdateWithObo() {
    String streamId = "stream-obo-active";
    activity.setStreamId(streamId);
    activity.setActive(true);
    activity.setId("activity-123");

    Obo obo = new Obo();
    obo.setUsername("test.user");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo(eq("test.user"))).thenReturn(authSession);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Room active status update, in activity activity-123, is not OBO enabled");
  }

  @Test
  void shouldNotUpdateRoomWithOboWhenNoAttributesSet() {
    String streamId = "stream-obo-none";
    activity.setStreamId(streamId);

    Obo obo = new Obo();
    obo.setUsername("test.user");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    OboStreamService oboStreamService = mock(OboStreamService.class);

    when(bdkGateway.obo(eq("test.user"))).thenReturn(authSession);
    when(bdkGateway.obo(eq(authSession))).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomDetail roomDetail = new V3RoomDetail();
    when(oboStreamService.getRoomInfo(eq(streamId))).thenReturn(roomDetail);

    executor.execute(context);

    verify(oboStreamService, never()).updateRoom(any(), any());
    verify(oboStreamService).getRoomInfo(eq(streamId));
    verify(context).setOutputVariable(eq("room"), eq(roomDetail));
  }

}
