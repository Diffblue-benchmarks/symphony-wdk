package com.symphony.bdk.workflow.engine.executor.room;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.DemoteRoomOwner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

class DemoteRoomOwnerExecutorClaudeTest {

  private DemoteRoomOwnerExecutor executor;
  private ActivityExecutorContext<DemoteRoomOwner> context;
  private DemoteRoomOwner activity;
  private BdkGateway bdkGateway;
  private StreamService streamService;
  private OboServices oboServices;
  private AuthSession authSession;
  private StreamService oboStreamService;

  @BeforeEach
  void setUp() {
    executor = new DemoteRoomOwnerExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new DemoteRoomOwner();
    bdkGateway = mock(BdkGateway.class);
    streamService = mock(StreamService.class);
    oboServices = mock(OboServices.class);
    authSession = mock(AuthSession.class);
    oboStreamService = mock(StreamService.class);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.streams()).thenReturn(streamService);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    DemoteRoomOwnerExecutor newExecutor = new DemoteRoomOwnerExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    DemoteRoomOwnerExecutor newExecutor = new DemoteRoomOwnerExecutor();

    // Then: Instance should be of correct type
    assertThat(newExecutor).isInstanceOf(DemoteRoomOwnerExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    DemoteRoomOwnerExecutor executor1 = new DemoteRoomOwnerExecutor();
    DemoteRoomOwnerExecutor executor2 = new DemoteRoomOwnerExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new DemoteRoomOwnerExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - non-OBO scenarios

  @Test
  void execute_withSingleUserNoObo_shouldDemoteUserToParticipant() {
    // Given: Activity with one user ID and no OBO
    String streamId = "streamId123";
    Long userId = 123456789L;
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should demote user using regular stream service
    verify(streamService).demoteUserToRoomParticipant(userId, streamId);
    verify(bdkGateway, never()).obo((String) null);
    verify(bdkGateway, never()).obo((Long) null);
  }

  @Test
  void execute_withMultipleUsersNoObo_shouldDemoteAllUsersToParticipants() {
    // Given: Activity with multiple user IDs and no OBO
    String streamId = "streamId123";
    List<Long> userIds = Arrays.asList(111L, 222L, 333L);
    activity.setStreamId(streamId);
    activity.setUserIds(userIds);
    activity.setObo(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should demote all users to participants
    verify(streamService).demoteUserToRoomParticipant(111L, streamId);
    verify(streamService).demoteUserToRoomParticipant(222L, streamId);
    verify(streamService).demoteUserToRoomParticipant(333L, streamId);
    verify(streamService, times(3)).demoteUserToRoomParticipant(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.eq(streamId));
  }

  @Test
  void execute_withEmptyUserListNoObo_shouldNotCallStreamService() {
    // Given: Activity with empty user ID list and no OBO
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.emptyList());
    activity.setObo(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should not call stream service
    verify(streamService, never()).demoteUserToRoomParticipant(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
  }

  @Test
  void execute_withEmptyOboObject_shouldUseNonOboPath() {
    // Given: Activity with empty OBO object (both username and userId are null)
    String streamId = "streamId123";
    Long userId = 123456789L;
    Obo emptyObo = new Obo();
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(emptyObo);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-OBO path and demote user using regular stream service
    verify(streamService).demoteUserToRoomParticipant(userId, streamId);
    verify(bdkGateway, never()).obo((String) null);
    verify(bdkGateway, never()).obo((Long) null);
  }

  @Test
  void execute_withDifferentStreamIds_shouldUseDifferentStreamIds() {
    // Given: Activity with one user and first stream ID
    String streamId1 = "streamId123";
    Long userId = 123456789L;
    activity.setStreamId(streamId1);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(null);

    // When: Execute is called with first stream ID
    executor.execute(context);

    // Then: Should call demoteUserToRoomParticipant with first stream ID
    verify(streamService).demoteUserToRoomParticipant(userId, streamId1);

    // Given: Activity with different stream ID
    String streamId2 = "streamId456";
    activity.setStreamId(streamId2);

    // When: Execute is called again with different stream ID
    executor.execute(context);

    // Then: Should call demoteUserToRoomParticipant with second stream ID
    verify(streamService).demoteUserToRoomParticipant(userId, streamId2);
  }

  @Test
  void execute_multipleCallsNoObo_shouldNotThrowException() {
    // Given: Activity with user IDs and no OBO
    String streamId = "streamId123";
    List<Long> userIds = Arrays.asList(111L, 222L);
    activity.setStreamId(streamId);
    activity.setUserIds(userIds);
    activity.setObo(null);

    // When/Then: Multiple execute calls should not throw exception
    assertThatCode(() -> {
      executor.execute(context);
      executor.execute(context);
      executor.execute(context);
    }).doesNotThrowAnyException();

    // Then: Should have demoted users 3 times (2 users per call, 3 calls)
    verify(streamService, times(3)).demoteUserToRoomParticipant(111L, streamId);
    verify(streamService, times(3)).demoteUserToRoomParticipant(222L, streamId);
  }

  // Tests for execute method - OBO with username

  @Test
  void execute_withOboUsername_shouldUseOboServices() {
    // Given: Activity with OBO username
    String streamId = "streamId123";
    Long userId = 123456789L;
    String oboUsername = "obo.user@example.com";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO services to demote user
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway).obo(authSession);
    verify(oboStreamService).demoteUserToRoomParticipant(userId, streamId);
    verify(streamService, never()).demoteUserToRoomParticipant(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
  }

  @Test
  void execute_withMultipleUsersOboUsername_shouldDemoteAllUsersViaObo() {
    // Given: Activity with multiple user IDs and OBO username
    String streamId = "streamId123";
    List<Long> userIds = Arrays.asList(111L, 222L, 333L);
    String oboUsername = "obo.user@example.com";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setUserIds(userIds);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: Execute is called
    executor.execute(context);

    // Then: Should demote all users via OBO services
    verify(oboStreamService).demoteUserToRoomParticipant(111L, streamId);
    verify(oboStreamService).demoteUserToRoomParticipant(222L, streamId);
    verify(oboStreamService).demoteUserToRoomParticipant(333L, streamId);
    verify(oboStreamService, times(3)).demoteUserToRoomParticipant(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.eq(streamId));
  }

  @Test
  void execute_withOboUsername_shouldNotCallRegularStreamService() {
    // Given: Activity with OBO username
    String streamId = "streamId123";
    Long userId = 123456789L;
    String oboUsername = "obo.user@example.com";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: Execute is called
    executor.execute(context);

    // Then: Should NOT call regular stream service
    verify(streamService, never()).demoteUserToRoomParticipant(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
  }

  // Tests for execute method - OBO with userId

  @Test
  void execute_withOboUserId_shouldUseOboServices() {
    // Given: Activity with OBO userId (no username)
    String streamId = "streamId123";
    Long userId = 123456789L;
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUserId(oboUserId);
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(obo);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO services with userId
    verify(bdkGateway).obo(oboUserId);
    verify(bdkGateway).obo(authSession);
    verify(oboStreamService).demoteUserToRoomParticipant(userId, streamId);
    verify(streamService, never()).demoteUserToRoomParticipant(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
  }

  @Test
  void execute_withMultipleUsersOboUserId_shouldDemoteAllUsersViaObo() {
    // Given: Activity with multiple user IDs and OBO userId
    String streamId = "streamId123";
    List<Long> userIds = Arrays.asList(111L, 222L, 333L);
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUserId(oboUserId);
    activity.setStreamId(streamId);
    activity.setUserIds(userIds);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: Execute is called
    executor.execute(context);

    // Then: Should demote all users via OBO services
    verify(oboStreamService).demoteUserToRoomParticipant(111L, streamId);
    verify(oboStreamService).demoteUserToRoomParticipant(222L, streamId);
    verify(oboStreamService).demoteUserToRoomParticipant(333L, streamId);
    verify(oboStreamService, times(3)).demoteUserToRoomParticipant(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.eq(streamId));
  }

  // Tests for execute method - OBO preference (username over userId)

  @Test
  void execute_withBothOboUsernameAndUserId_shouldPreferUsername() {
    // Given: Activity with both OBO username and userId set
    String streamId = "streamId123";
    Long userId = 123456789L;
    String oboUsername = "obo.user@example.com";
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    obo.setUserId(oboUserId);
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: Execute is called
    executor.execute(context);

    // Then: Should prefer username over userId
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway, never()).obo(oboUserId);
    verify(oboStreamService).demoteUserToRoomParticipant(userId, streamId);
  }

  // Tests for execute method - OBO edge cases

  @Test
  void execute_withOboAndEmptyUserList_shouldNotCallStreamService() {
    // Given: Activity with OBO but empty user list
    String streamId = "streamId123";
    String oboUsername = "obo.user@example.com";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.emptyList());
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: Execute is called
    executor.execute(context);

    // Then: Should not call stream service methods
    verify(oboStreamService, never()).demoteUserToRoomParticipant(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
    verify(streamService, never()).demoteUserToRoomParticipant(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
  }

  @Test
  void execute_multipleCallsWithObo_shouldNotThrowException() {
    // Given: Activity with OBO username
    String streamId = "streamId123";
    List<Long> userIds = Arrays.asList(111L, 222L);
    String oboUsername = "obo.user@example.com";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setUserIds(userIds);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When/Then: Multiple execute calls should not throw exception
    assertThatCode(() -> {
      executor.execute(context);
      executor.execute(context);
      executor.execute(context);
    }).doesNotThrowAnyException();

    // Then: Should have demoted users 3 times via OBO
    verify(oboStreamService, times(3)).demoteUserToRoomParticipant(111L, streamId);
    verify(oboStreamService, times(3)).demoteUserToRoomParticipant(222L, streamId);
  }

  // Tests for doOboWithCache method

  @Test
  void doOboWithCache_withOboUsername_shouldDemoteUserViaObo() {
    // Given: Activity with OBO username
    String streamId = "streamId123";
    Long userId = 123456789L;
    String oboUsername = "obo.user@example.com";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: doOboWithCache is called
    Void result = executor.doOboWithCache(context);

    // Then: Should demote user via OBO and return null
    verify(bdkGateway).obo(oboUsername);
    verify(oboStreamService).demoteUserToRoomParticipant(userId, streamId);
    assertThat(result).isNull();
  }

  @Test
  void doOboWithCache_withMultipleUsers_shouldDemoteAllUsersViaObo() {
    // Given: Activity with multiple users and OBO username
    String streamId = "streamId123";
    List<Long> userIds = Arrays.asList(111L, 222L, 333L);
    String oboUsername = "obo.user@example.com";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setUserIds(userIds);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: doOboWithCache is called
    Void result = executor.doOboWithCache(context);

    // Then: Should demote all users via OBO
    verify(oboStreamService).demoteUserToRoomParticipant(111L, streamId);
    verify(oboStreamService).demoteUserToRoomParticipant(222L, streamId);
    verify(oboStreamService).demoteUserToRoomParticipant(333L, streamId);
    verify(oboStreamService, times(3)).demoteUserToRoomParticipant(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.eq(streamId));
    assertThat(result).isNull();
  }

  @Test
  void doOboWithCache_withOboUserId_shouldDemoteUserViaObo() {
    // Given: Activity with OBO userId
    String streamId = "streamId123";
    Long userId = 123456789L;
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUserId(oboUserId);
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(obo);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: doOboWithCache is called
    Void result = executor.doOboWithCache(context);

    // Then: Should demote user via OBO using userId
    verify(bdkGateway).obo(oboUserId);
    verify(oboStreamService).demoteUserToRoomParticipant(userId, streamId);
    assertThat(result).isNull();
  }

  @Test
  void doOboWithCache_withEmptyUserList_shouldNotCallStreamService() {
    // Given: Activity with OBO but empty user list
    String streamId = "streamId123";
    String oboUsername = "obo.user@example.com";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.emptyList());
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: doOboWithCache is called
    Void result = executor.doOboWithCache(context);

    // Then: Should not call stream service and return null
    verify(oboStreamService, never()).demoteUserToRoomParticipant(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
    assertThat(result).isNull();
  }

  @Test
  void doOboWithCache_shouldAlwaysReturnNull() {
    // Given: Activity with OBO username and user
    String streamId = "streamId123";
    Long userId = 123456789L;
    String oboUsername = "obo.user@example.com";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: doOboWithCache is called
    Void result = executor.doOboWithCache(context);

    // Then: Should always return null
    assertThat(result).isNull();
  }

  @Test
  void doOboWithCache_multipleCalls_shouldNotThrowException() {
    // Given: Activity with OBO username
    String streamId = "streamId123";
    List<Long> userIds = Arrays.asList(111L, 222L);
    String oboUsername = "obo.user@example.com";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setUserIds(userIds);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When/Then: Multiple calls should not throw exception
    assertThatCode(() -> {
      executor.doOboWithCache(context);
      executor.doOboWithCache(context);
      executor.doOboWithCache(context);
    }).doesNotThrowAnyException();

    // Then: Should have demoted users 3 times
    verify(oboStreamService, times(3)).demoteUserToRoomParticipant(111L, streamId);
    verify(oboStreamService, times(3)).demoteUserToRoomParticipant(222L, streamId);
  }

  @Test
  void doOboWithCache_withBothOboUsernameAndUserId_shouldPreferUsername() {
    // Given: Activity with both OBO username and userId
    String streamId = "streamId123";
    Long userId = 123456789L;
    String oboUsername = "obo.user@example.com";
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    obo.setUserId(oboUserId);
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: doOboWithCache is called
    Void result = executor.doOboWithCache(context);

    // Then: Should prefer username over userId
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway, never()).obo(oboUserId);
    verify(oboStreamService).demoteUserToRoomParticipant(userId, streamId);
    assertThat(result).isNull();
  }
}
