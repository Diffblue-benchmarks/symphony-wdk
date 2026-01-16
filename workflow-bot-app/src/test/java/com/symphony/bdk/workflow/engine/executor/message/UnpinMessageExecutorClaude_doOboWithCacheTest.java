package com.symphony.bdk.workflow.engine.executor.message;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V3RoomAttributes;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.message.UnpinMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UnpinMessageExecutorClaude_doOboWithCacheTest {

  private UnpinMessageExecutor executor;
  private ActivityExecutorContext<UnpinMessage> context;
  private UnpinMessage activity;
  private BdkGateway bdkGateway;
  private AuthSession authSession;
  private OboServices oboServices;
  private StreamService oboStreamService;

  @BeforeEach
  void setUp() {
    executor = new UnpinMessageExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new UnpinMessage();
    bdkGateway = mock(BdkGateway.class);
    authSession = mock(AuthSession.class);
    oboServices = mock(OboServices.class);
    oboStreamService = mock(StreamService.class);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
  }

  // Tests for OBO with username

  @Test
  void doOboWithCache_withUsername_shouldUnpinMessageInRoom() {
    // Given: Activity with streamId and OBO username
    String streamId = "room-stream-123";
    String username = "obo-user";
    activity.setStreamId(streamId);
    activity.setId("unpinActivity1");

    Obo obo = new Obo();
    obo.setUsername(username);
    activity.setObo(obo);

    when(bdkGateway.obo(username)).thenReturn(authSession);

    // When: doOboWithCache is called
    Void result = executor.doOboWithCache(context);

    // Then: Should update room with empty pinned message ID using OBO
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).updateRoom(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEmpty();
    assertThat(result).isNull();
  }

  @Test
  void doOboWithCache_withDifferentUsername_shouldUnpinMessageInCorrectRoom() {
    // Given: Activity with different streamId and OBO username
    String streamId = "room-stream-xyz";
    String username = "another-obo-user";
    activity.setStreamId(streamId);
    activity.setId("unpinActivity2");

    Obo obo = new Obo();
    obo.setUsername(username);
    activity.setObo(obo);

    when(bdkGateway.obo(username)).thenReturn(authSession);

    // When: doOboWithCache is called
    Void result = executor.doOboWithCache(context);

    // Then: Should update correct room with empty pinned message ID
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).updateRoom(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEmpty();
    assertThat(result).isNull();
  }

  // Tests for OBO with userId

  @Test
  void doOboWithCache_withUserId_shouldUnpinMessageInRoom() {
    // Given: Activity with streamId and OBO userId
    String streamId = "room-stream-456";
    Long userId = 12345L;
    activity.setStreamId(streamId);
    activity.setId("unpinActivity3");

    Obo obo = new Obo();
    obo.setUserId(userId);
    activity.setObo(obo);

    when(bdkGateway.obo(userId)).thenReturn(authSession);

    // When: doOboWithCache is called
    Void result = executor.doOboWithCache(context);

    // Then: Should update room with empty pinned message ID using OBO
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).updateRoom(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEmpty();
    assertThat(result).isNull();
  }

  @Test
  void doOboWithCache_withDifferentUserId_shouldUnpinMessageInCorrectRoom() {
    // Given: Activity with different streamId and OBO userId
    String streamId = "room-stream-999";
    Long userId = 99999L;
    activity.setStreamId(streamId);
    activity.setId("unpinActivity4");

    Obo obo = new Obo();
    obo.setUserId(userId);
    activity.setObo(obo);

    when(bdkGateway.obo(userId)).thenReturn(authSession);

    // When: doOboWithCache is called
    Void result = executor.doOboWithCache(context);

    // Then: Should update correct room with empty pinned message ID
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).updateRoom(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEmpty();
    assertThat(result).isNull();
  }

  // Tests for username taking precedence over userId

  @Test
  void doOboWithCache_withBothUsernameAndUserId_shouldUseUsername() {
    // Given: Activity with both OBO username and userId (username takes precedence)
    String streamId = "room-stream-789";
    String username = "primary-user";
    Long userId = 67890L;
    activity.setStreamId(streamId);
    activity.setId("unpinActivity5");

    Obo obo = new Obo();
    obo.setUsername(username);
    obo.setUserId(userId);
    activity.setObo(obo);

    when(bdkGateway.obo(username)).thenReturn(authSession);

    // When: doOboWithCache is called
    Void result = executor.doOboWithCache(context);

    // Then: Should use username for OBO authentication
    verify(bdkGateway).obo(eq(username));
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).updateRoom(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEmpty();
    assertThat(result).isNull();
  }

  // Tests for edge cases with stream IDs

  @Test
  void doOboWithCache_withSpecialCharactersInStreamId_shouldHandleCorrectly() {
    // Given: StreamId with special characters
    String streamId = "room-stream-special_123!@#";
    String username = "obo-user";
    activity.setStreamId(streamId);
    activity.setId("unpinActivity6");

    Obo obo = new Obo();
    obo.setUsername(username);
    activity.setObo(obo);

    when(bdkGateway.obo(username)).thenReturn(authSession);

    // When: doOboWithCache is called
    assertThatCode(() -> executor.doOboWithCache(context))
        .doesNotThrowAnyException();

    // Then: Should handle special characters correctly
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).updateRoom(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEmpty();
  }

  @Test
  void doOboWithCache_withLongStreamId_shouldHandleCorrectly() {
    // Given: Very long stream ID
    String streamId = "room" + "1234567890".repeat(20);
    String username = "obo-user";
    activity.setStreamId(streamId);
    activity.setId("unpinActivity7");

    Obo obo = new Obo();
    obo.setUsername(username);
    activity.setObo(obo);

    when(bdkGateway.obo(username)).thenReturn(authSession);

    // When: doOboWithCache is called
    assertThatCode(() -> executor.doOboWithCache(context))
        .doesNotThrowAnyException();

    // Then: Should handle long stream ID correctly
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).updateRoom(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEmpty();
  }

  // Tests for multiple invocations

  @Test
  void doOboWithCache_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid activity setup
    String streamId = "room-stream-555";
    String username = "obo-user";
    activity.setStreamId(streamId);
    activity.setId("unpinActivity8");

    Obo obo = new Obo();
    obo.setUsername(username);
    activity.setObo(obo);

    when(bdkGateway.obo(username)).thenReturn(authSession);

    // When: doOboWithCache is called multiple times
    executor.doOboWithCache(context);
    executor.doOboWithCache(context);

    // Then: Should work correctly both times
    verify(bdkGateway, org.mockito.Mockito.times(2)).obo(eq(username));
    verify(oboStreamService, org.mockito.Mockito.times(2)).updateRoom(eq(streamId),
        org.mockito.ArgumentMatchers.any(V3RoomAttributes.class));
  }

  // Tests for return value

  @Test
  void doOboWithCache_shouldAlwaysReturnNull() {
    // Given: Valid activity setup
    String streamId = "room-stream-return";
    String username = "obo-user";
    activity.setStreamId(streamId);
    activity.setId("unpinActivity9");

    Obo obo = new Obo();
    obo.setUsername(username);
    activity.setObo(obo);

    when(bdkGateway.obo(username)).thenReturn(authSession);

    // When: doOboWithCache is called
    Void result = executor.doOboWithCache(context);

    // Then: Should return null
    assertThat(result).isNull();
  }

  // Tests for verifying correct V3RoomAttributes setup

  @Test
  void doOboWithCache_shouldSetPinnedMessageIdToEmptyString() {
    // Given: Valid activity setup
    String streamId = "room-stream-attributes";
    String username = "obo-user";
    activity.setStreamId(streamId);
    activity.setId("unpinActivity10");

    Obo obo = new Obo();
    obo.setUsername(username);
    activity.setObo(obo);

    when(bdkGateway.obo(username)).thenReturn(authSession);

    // When: doOboWithCache is called
    executor.doOboWithCache(context);

    // Then: V3RoomAttributes should have pinnedMessageId set to empty string
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).updateRoom(eq(streamId), captor.capture());
    V3RoomAttributes attributes = captor.getValue();
    assertThat(attributes.getPinnedMessageId()).isNotNull();
    assertThat(attributes.getPinnedMessageId()).isEmpty();
    assertThat(attributes.getPinnedMessageId()).isEqualTo("");
  }

  // Tests for OBO authentication flow

  @Test
  void doOboWithCache_shouldGetAuthSessionFromBdkGateway() {
    // Given: Activity with username
    String streamId = "room-stream-auth";
    String username = "test-user";
    activity.setStreamId(streamId);
    activity.setId("unpinActivity11");

    Obo obo = new Obo();
    obo.setUsername(username);
    activity.setObo(obo);

    when(bdkGateway.obo(username)).thenReturn(authSession);

    // When: doOboWithCache is called
    executor.doOboWithCache(context);

    // Then: Should get auth session using username
    verify(bdkGateway).obo(eq(username));
    verify(bdkGateway).obo(eq(authSession));
  }

  @Test
  void doOboWithCache_withUserId_shouldGetAuthSessionFromBdkGateway() {
    // Given: Activity with userId
    String streamId = "room-stream-auth-id";
    Long userId = 11111L;
    activity.setStreamId(streamId);
    activity.setId("unpinActivity12");

    Obo obo = new Obo();
    obo.setUserId(userId);
    activity.setObo(obo);

    when(bdkGateway.obo(userId)).thenReturn(authSession);

    // When: doOboWithCache is called
    executor.doOboWithCache(context);

    // Then: Should get auth session using userId
    verify(bdkGateway).obo(eq(userId));
    verify(bdkGateway).obo(eq(authSession));
  }

  // Tests for correct services chain

  @Test
  void doOboWithCache_shouldCallCorrectServiceChain() {
    // Given: Valid activity setup
    String streamId = "room-stream-chain";
    String username = "obo-user";
    activity.setStreamId(streamId);
    activity.setId("unpinActivity13");

    Obo obo = new Obo();
    obo.setUsername(username);
    activity.setObo(obo);

    when(bdkGateway.obo(username)).thenReturn(authSession);

    // When: doOboWithCache is called
    executor.doOboWithCache(context);

    // Then: Should call services in correct order
    verify(bdkGateway).obo(username); // Get auth session
    verify(bdkGateway).obo(authSession); // Get OBO services
    verify(oboServices).streams(); // Get stream service
    verify(oboStreamService).updateRoom(eq(streamId),
        org.mockito.ArgumentMatchers.any(V3RoomAttributes.class)); // Update room
  }
}
