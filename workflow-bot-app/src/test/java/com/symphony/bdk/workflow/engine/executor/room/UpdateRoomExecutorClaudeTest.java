package com.symphony.bdk.workflow.engine.executor.room;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.RoomSystemInfo;
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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateRoomExecutorClaudeTest {

  private UpdateRoomExecutor executor;
  private ActivityExecutorContext<UpdateRoom> context;
  private UpdateRoom activity;
  private BdkGateway bdkGateway;
  private StreamService streamService;
  private OboServices oboServices;
  private AuthSession authSession;
  private StreamService oboStreamService;
  private V3RoomDetail roomDetail;

  @BeforeEach
  void setUp() {
    executor = new UpdateRoomExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new UpdateRoom();
    bdkGateway = mock(BdkGateway.class);
    streamService = mock(StreamService.class);
    oboServices = mock(OboServices.class);
    authSession = mock(AuthSession.class);
    oboStreamService = mock(StreamService.class);
    roomDetail = createV3RoomDetail("roomId123");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.streams()).thenReturn(streamService);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    UpdateRoomExecutor newExecutor = new UpdateRoomExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    UpdateRoomExecutor newExecutor = new UpdateRoomExecutor();

    // Then: Instance should be of correct type
    assertThat(newExecutor).isInstanceOf(UpdateRoomExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    UpdateRoomExecutor executor1 = new UpdateRoomExecutor();
    UpdateRoomExecutor executor2 = new UpdateRoomExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new UpdateRoomExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - updating room attributes only (no active status, no OBO)

  @Test
  void execute_withRoomNameOnlyNoObo_shouldUpdateRoomAndSetOutput() {
    // Given: Activity with room name only
    String streamId = "streamId123";
    String roomName = "Updated Room Name";
    activity.setStreamId(streamId);
    activity.setRoomName(roomName);
    activity.setActive(null);
    activity.setObo(null);

    when(streamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should update room with attributes and get room info
    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(eq(streamId), attributesCaptor.capture());
    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getName()).isEqualTo(roomName);
    verify(streamService).getRoomInfo(streamId);
    verify(streamService, never()).setRoomActive(anyString(), any());
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  void execute_withFullRoomAttributesNoObo_shouldUpdateRoomWithAllAttributes() {
    // Given: Activity with full room attributes
    String streamId = "streamId123";
    String roomName = "Updated Room";
    String description = "Updated Description";
    activity.setStreamId(streamId);
    activity.setRoomName(roomName);
    activity.setRoomDescription(description);
    activity.setIsPublic(true);
    activity.setViewHistory(true);
    activity.setDiscoverable(false);
    activity.setReadOnly(false);
    activity.setCopyProtected(true);
    activity.setCrossPod(false);
    activity.setMultilateralRoom(true);
    activity.setMembersCanInvite(false);
    activity.setActive(null);
    activity.setObo(null);

    when(streamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should update room with all attributes
    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(eq(streamId), attributesCaptor.capture());
    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getName()).isEqualTo(roomName);
    assertThat(capturedAttributes.getDescription()).isEqualTo(description);
    assertThat(capturedAttributes.getPublic()).isTrue();
    assertThat(capturedAttributes.getViewHistory()).isTrue();
    assertThat(capturedAttributes.getDiscoverable()).isFalse();
    assertThat(capturedAttributes.getReadOnly()).isFalse();
    assertThat(capturedAttributes.getCopyProtected()).isTrue();
    assertThat(capturedAttributes.getCrossPod()).isFalse();
    assertThat(capturedAttributes.getMultiLateralRoom()).isTrue();
    assertThat(capturedAttributes.getMembersCanInvite()).isFalse();
    verify(streamService).getRoomInfo(streamId);
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  void execute_withKeywordsNoObo_shouldUpdateRoomWithKeywords() {
    // Given: Activity with keywords
    String streamId = "streamId123";
    String roomName = "Updated Room";
    Map<String, String> keywords = new HashMap<>();
    keywords.put("topic", "technology");
    keywords.put("region", "US");
    activity.setStreamId(streamId);
    activity.setRoomName(roomName);
    activity.setKeywords(keywords);
    activity.setActive(null);
    activity.setObo(null);

    when(streamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should update room with keywords
    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(eq(streamId), attributesCaptor.capture());
    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getKeywords()).hasSize(2);
    assertThat(capturedAttributes.getKeywords().get(0).getKey()).isIn("topic", "region");
    assertThat(capturedAttributes.getKeywords().get(1).getKey()).isIn("topic", "region");
    verify(streamService).getRoomInfo(streamId);
    verify(context).setOutputVariable("room", roomDetail);
  }

  // Tests for execute method - updating active status only

  @Test
  void execute_withActiveStatusOnlyNoObo_shouldSetRoomActive() {
    // Given: Activity with active status only (no other attributes)
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setActive(true);
    activity.setRoomName(null);
    activity.setRoomDescription(null);
    activity.setObo(null);

    when(streamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should only set room active status
    verify(streamService, never()).updateRoom(anyString(), any(V3RoomAttributes.class));
    verify(streamService).setRoomActive(streamId, true);
    verify(streamService).getRoomInfo(streamId);
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  void execute_withActiveStatusFalseNoObo_shouldDeactivateRoom() {
    // Given: Activity with active status set to false
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setActive(false);
    activity.setRoomName(null);
    activity.setObo(null);

    when(streamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should deactivate room
    verify(streamService).setRoomActive(streamId, false);
    verify(streamService).getRoomInfo(streamId);
    verify(context).setOutputVariable("room", roomDetail);
  }

  // Tests for execute method - updating both attributes and active status

  @Test
  void execute_withAttributesAndActiveStatusNoObo_shouldUpdateBoth() {
    // Given: Activity with both room attributes and active status
    String streamId = "streamId123";
    String roomName = "Updated Room";
    activity.setStreamId(streamId);
    activity.setRoomName(roomName);
    activity.setActive(true);
    activity.setObo(null);

    when(streamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should update both attributes and active status
    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(eq(streamId), attributesCaptor.capture());
    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getName()).isEqualTo(roomName);
    verify(streamService).setRoomActive(streamId, true);
    verify(streamService).getRoomInfo(streamId);
    verify(context).setOutputVariable("room", roomDetail);
  }

  // Tests for execute method - no updates (only streamId provided)

  @Test
  void execute_withNoUpdatesNoObo_shouldOnlyGetRoomInfo() {
    // Given: Activity with only streamId (no attributes, no active status)
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setRoomName(null);
    activity.setRoomDescription(null);
    activity.setActive(null);
    activity.setObo(null);

    when(streamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should only get room info without any updates
    verify(streamService, never()).updateRoom(anyString(), any(V3RoomAttributes.class));
    verify(streamService, never()).setRoomActive(anyString(), any());
    verify(streamService).getRoomInfo(streamId);
    verify(context).setOutputVariable("room", roomDetail);
  }

  // Tests for execute method - OBO scenarios

  @Test
  void execute_withOboUsername_shouldCallDoOboWithCache() {
    // Given: Activity with OBO username
    String streamId = "streamId123";
    String roomName = "Updated OBO Room";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setRoomName(roomName);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO services
    verify(bdkGateway).obo(oboUsername);
    verify(oboStreamService).updateRoom(eq(streamId), any(V3RoomAttributes.class));
    verify(oboStreamService).getRoomInfo(streamId);
    verify(streamService, never()).updateRoom(anyString(), any(V3RoomAttributes.class));
    verify(streamService, never()).getRoomInfo(anyString());
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  void execute_withOboUserId_shouldUseOboServices() {
    // Given: Activity with OBO user ID
    String streamId = "streamId123";
    String roomName = "Updated OBO Room";
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(oboUserId);
    activity.setStreamId(streamId);
    activity.setRoomName(roomName);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO services with user ID
    verify(bdkGateway).obo(oboUserId);
    verify(oboStreamService).updateRoom(eq(streamId), any(V3RoomAttributes.class));
    verify(oboStreamService).getRoomInfo(streamId);
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  void execute_withOboAndActiveStatus_shouldThrowException() {
    // Given: Activity with OBO and active status
    String streamId = "streamId123";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setActive(true);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When/Then: Execute should throw exception
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Room active status update")
        .hasMessageContaining("is not OBO enabled");
  }

  @Test
  void execute_withEmptyOboObject_shouldUseNonOboPath() {
    // Given: Activity with empty OBO (no username or userId)
    String streamId = "streamId123";
    String roomName = "Updated Room";
    Obo emptyObo = new Obo();
    emptyObo.setUsername(null);
    emptyObo.setUserId(null);
    activity.setStreamId(streamId);
    activity.setRoomName(roomName);
    activity.setObo(emptyObo);

    when(streamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-OBO path
    verify(streamService).updateRoom(eq(streamId), any(V3RoomAttributes.class));
    verify(streamService).getRoomInfo(streamId);
    verify(bdkGateway, never()).obo((String) null);
    verify(bdkGateway, never()).obo((Long) null);
    verify(context).setOutputVariable("room", roomDetail);
  }

  // Tests for execute method - edge cases

  @Test
  void execute_shouldNotThrowException() {
    // Given: Valid activity setup
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setRoomName("Updated Room");
    activity.setObo(null);

    when(streamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid activity setup
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setRoomName("Updated Room");
    activity.setObo(null);

    when(streamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(streamService, times(2)).updateRoom(eq(streamId), any(V3RoomAttributes.class));
    verify(streamService, times(2)).getRoomInfo(streamId);
    verify(context, times(2)).setOutputVariable("room", roomDetail);
  }

  @Test
  void execute_withDifferentStreamIds_shouldUseCorrectStreamId() {
    // Given: Activity with specific stream ID
    String streamId1 = "stream123";
    String streamId2 = "stream456";

    // First call with stream ID 1
    activity.setStreamId(streamId1);
    activity.setRoomName("Room 1");
    activity.setObo(null);
    when(streamService.getRoomInfo(streamId1)).thenReturn(roomDetail);
    executor.execute(context);

    // Second call with stream ID 2
    activity.setStreamId(streamId2);
    activity.setRoomName("Room 2");
    when(streamService.getRoomInfo(streamId2)).thenReturn(roomDetail);
    executor.execute(context);

    // Then: Should use correct stream IDs
    verify(streamService).updateRoom(eq(streamId1), any(V3RoomAttributes.class));
    verify(streamService).getRoomInfo(streamId1);
    verify(streamService).updateRoom(eq(streamId2), any(V3RoomAttributes.class));
    verify(streamService).getRoomInfo(streamId2);
  }

  // Tests for doOboWithCache method - updating room attributes

  @Test
  void doOboWithCache_withRoomNameOnly_shouldUpdateRoomViaObo() {
    // Given: Activity with room name only
    String streamId = "streamId123";
    String roomName = "OBO Updated Room";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setRoomName(roomName);
    activity.setActive(null);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: doOboWithCache is called
    V3RoomDetail result = executor.doOboWithCache(context);

    // Then: Should update room via OBO and return room detail
    assertThat(result).isSameAs(roomDetail);
    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).updateRoom(eq(streamId), attributesCaptor.capture());
    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getName()).isEqualTo(roomName);
    verify(oboStreamService).getRoomInfo(streamId);
  }

  @Test
  void doOboWithCache_withFullRoomAttributes_shouldUpdateRoomWithAllAttributes() {
    // Given: Activity with full room attributes
    String streamId = "streamId123";
    String roomName = "OBO Full Update";
    String description = "OBO Description";
    Map<String, String> keywords = new HashMap<>();
    keywords.put("obo", "test");
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setRoomName(roomName);
    activity.setRoomDescription(description);
    activity.setKeywords(keywords);
    activity.setIsPublic(true);
    activity.setViewHistory(false);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: doOboWithCache is called
    V3RoomDetail result = executor.doOboWithCache(context);

    // Then: Should update room with all attributes via OBO
    assertThat(result).isSameAs(roomDetail);
    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).updateRoom(eq(streamId), attributesCaptor.capture());
    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getName()).isEqualTo(roomName);
    assertThat(capturedAttributes.getDescription()).isEqualTo(description);
    assertThat(capturedAttributes.getPublic()).isTrue();
    assertThat(capturedAttributes.getViewHistory()).isFalse();
    assertThat(capturedAttributes.getKeywords()).hasSize(1);
    verify(oboStreamService).getRoomInfo(streamId);
  }

  @Test
  void doOboWithCache_withNoUpdates_shouldOnlyGetRoomInfo() {
    // Given: Activity with no updates (only streamId)
    String streamId = "streamId123";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setRoomName(null);
    activity.setRoomDescription(null);
    activity.setActive(null);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: doOboWithCache is called
    V3RoomDetail result = executor.doOboWithCache(context);

    // Then: Should only get room info without updates
    assertThat(result).isSameAs(roomDetail);
    verify(oboStreamService, never()).updateRoom(anyString(), any(V3RoomAttributes.class));
    verify(oboStreamService).getRoomInfo(streamId);
  }

  // Tests for doOboWithCache method - active status throws exception

  @Test
  void doOboWithCache_withActiveStatus_shouldThrowException() {
    // Given: Activity with OBO and active status
    String streamId = "streamId123";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setActive(true);
    activity.setObo(obo);
    activity.setId("updateRoomActivity1");

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When/Then: doOboWithCache should throw exception
    assertThatThrownBy(() -> executor.doOboWithCache(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Room active status update")
        .hasMessageContaining("in activity updateRoomActivity1")
        .hasMessageContaining("is not OBO enabled");
  }

  // Tests for doOboWithCache method - OBO with userId

  @Test
  void doOboWithCache_withOboUserId_shouldUseUserIdForAuth() {
    // Given: Activity with OBO user ID
    String streamId = "streamId123";
    String roomName = "OBO UserID Update";
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(oboUserId);
    activity.setStreamId(streamId);
    activity.setRoomName(roomName);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: doOboWithCache is called
    V3RoomDetail result = executor.doOboWithCache(context);

    // Then: Should use user ID for OBO auth
    assertThat(result).isSameAs(roomDetail);
    verify(bdkGateway).obo(oboUserId);
    verify(bdkGateway, never()).obo((String) null);
    verify(oboStreamService).updateRoom(eq(streamId), any(V3RoomAttributes.class));
    verify(oboStreamService).getRoomInfo(streamId);
  }

  @Test
  void doOboWithCache_withBothOboUsernameAndUserId_shouldPreferUsername() {
    // Given: Activity with both OBO username and user ID
    String streamId = "streamId123";
    String roomName = "OBO Preference Update";
    String oboUsername = "obo.user";
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    obo.setUserId(oboUserId);
    activity.setStreamId(streamId);
    activity.setRoomName(roomName);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: doOboWithCache is called
    V3RoomDetail result = executor.doOboWithCache(context);

    // Then: Should prefer username over user ID
    assertThat(result).isSameAs(roomDetail);
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway, never()).obo(oboUserId);
    verify(oboStreamService).updateRoom(eq(streamId), any(V3RoomAttributes.class));
    verify(oboStreamService).getRoomInfo(streamId);
  }

  // Tests for doOboWithCache method - edge cases

  @Test
  void doOboWithCache_shouldNotThrowException() {
    // Given: Valid activity with OBO
    String streamId = "streamId123";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setRoomName("Updated Room");
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When/Then: doOboWithCache should not throw exception
    assertThatCode(() -> executor.doOboWithCache(context))
        .doesNotThrowAnyException();
  }

  @Test
  void doOboWithCache_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid activity with OBO
    String streamId = "streamId123";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setRoomName("Updated Room");
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    // When: doOboWithCache is called multiple times
    V3RoomDetail result1 = executor.doOboWithCache(context);
    V3RoomDetail result2 = executor.doOboWithCache(context);

    // Then: Should work correctly both times
    assertThat(result1).isSameAs(roomDetail);
    assertThat(result2).isSameAs(roomDetail);
    verify(oboStreamService, times(2)).updateRoom(eq(streamId), any(V3RoomAttributes.class));
    verify(oboStreamService, times(2)).getRoomInfo(streamId);
  }

  @Test
  void doOboWithCache_withDifferentStreamIds_shouldUseCorrectStreamId() {
    // Given: Activity with OBO
    String streamId1 = "stream123";
    String streamId2 = "stream456";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setRoomName("Updated Room");

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomDetail roomDetail1 = createV3RoomDetail("roomId1");
    V3RoomDetail roomDetail2 = createV3RoomDetail("roomId2");
    when(oboStreamService.getRoomInfo(streamId1)).thenReturn(roomDetail1);
    when(oboStreamService.getRoomInfo(streamId2)).thenReturn(roomDetail2);

    // First call with stream ID 1
    activity.setStreamId(streamId1);
    V3RoomDetail result1 = executor.doOboWithCache(context);

    // Second call with stream ID 2
    activity.setStreamId(streamId2);
    V3RoomDetail result2 = executor.doOboWithCache(context);

    // Then: Should use correct stream IDs and return correct room details
    assertThat(result1).isSameAs(roomDetail1);
    assertThat(result2).isSameAs(roomDetail2);
    verify(oboStreamService).updateRoom(eq(streamId1), any(V3RoomAttributes.class));
    verify(oboStreamService).getRoomInfo(streamId1);
    verify(oboStreamService).updateRoom(eq(streamId2), any(V3RoomAttributes.class));
    verify(oboStreamService).getRoomInfo(streamId2);
  }

  @Test
  void doOboWithCache_withNullRoomDetail_shouldReturnNull() {
    // Given: Activity with OBO where getRoomInfo returns null
    String streamId = "streamId123";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setRoomName("Updated Room");
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getRoomInfo(streamId)).thenReturn(null);

    // When: doOboWithCache is called
    V3RoomDetail result = executor.doOboWithCache(context);

    // Then: Should return null
    assertThat(result).isNull();
    verify(oboStreamService).updateRoom(eq(streamId), any(V3RoomAttributes.class));
    verify(oboStreamService).getRoomInfo(streamId);
  }

  // Helper method to create V3RoomDetail with room ID

  private V3RoomDetail createV3RoomDetail(String roomId) {
    V3RoomDetail roomDetail = new V3RoomDetail();
    RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
    roomSystemInfo.setId(roomId);
    roomDetail.setRoomSystemInfo(roomSystemInfo);
    return roomDetail;
  }
}
