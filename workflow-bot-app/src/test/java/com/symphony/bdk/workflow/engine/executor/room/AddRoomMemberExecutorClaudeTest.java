package com.symphony.bdk.workflow.engine.executor.room;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.AddRoomMember;
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

class AddRoomMemberExecutorClaudeTest {

  private AddRoomMemberExecutor executor;
  private ActivityExecutorContext<AddRoomMember> context;
  private AddRoomMember activity;
  private BdkGateway bdkGateway;
  private StreamService streamService;
  private OboServices oboServices;
  private AuthSession authSession;
  private StreamService oboStreamService;

  @BeforeEach
  void setUp() {
    executor = new AddRoomMemberExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new AddRoomMember();
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
    AddRoomMemberExecutor newExecutor = new AddRoomMemberExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    AddRoomMemberExecutor newExecutor = new AddRoomMemberExecutor();

    // Then: Instance should be of correct type
    assertThat(newExecutor).isInstanceOf(AddRoomMemberExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    AddRoomMemberExecutor executor1 = new AddRoomMemberExecutor();
    AddRoomMemberExecutor executor2 = new AddRoomMemberExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new AddRoomMemberExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - non-OBO scenarios

  @Test
  void execute_withSingleUserNoObo_shouldAddMemberToRoom() {
    // Given: Activity with one user ID and no OBO
    String streamId = "streamId123";
    Long userId = 123456789L;
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should add member to room using regular stream service
    verify(streamService).addMemberToRoom(userId, streamId);
    verify(bdkGateway, never()).obo((String) null);
    verify(bdkGateway, never()).obo((Long) null);
  }

  @Test
  void execute_withMultipleUsersNoObo_shouldAddAllMembersToRoom() {
    // Given: Activity with multiple user IDs and no OBO
    String streamId = "streamId123";
    List<Long> userIds = Arrays.asList(111L, 222L, 333L);
    activity.setStreamId(streamId);
    activity.setUserIds(userIds);
    activity.setObo(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should add all members to room
    verify(streamService).addMemberToRoom(111L, streamId);
    verify(streamService).addMemberToRoom(222L, streamId);
    verify(streamService).addMemberToRoom(333L, streamId);
    verify(streamService, times(3)).addMemberToRoom(org.mockito.ArgumentMatchers.anyLong(),
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
    verify(streamService, never()).addMemberToRoom(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
  }

  @Test
  void execute_withEmptyOboObject_shouldUseNonOboPath() {
    // Given: Activity with empty OBO object (both username and userId are null)
    String streamId = "streamId123";
    Long userId = 123456789L;
    Obo emptyObo = new Obo();
    emptyObo.setUsername(null);
    emptyObo.setUserId(null);
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(emptyObo);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-OBO path since OBO is not valid
    verify(streamService).addMemberToRoom(userId, streamId);
    verify(bdkGateway, never()).obo((String) null);
  }

  @Test
  void execute_withDifferentStreamIds_shouldUseCorrectStreamId() {
    // Given: Activity with specific stream ID
    String streamId1 = "stream123";
    String streamId2 = "stream456";
    Long userId = 111L;

    // First call with stream ID 1
    activity.setStreamId(streamId1);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(null);
    executor.execute(context);

    // Second call with stream ID 2
    activity.setStreamId(streamId2);
    executor.execute(context);

    // Then: Should use correct stream IDs
    verify(streamService).addMemberToRoom(userId, streamId1);
    verify(streamService).addMemberToRoom(userId, streamId2);
  }

  // Tests for execute method - OBO scenarios with username

  @Test
  void execute_withOboUsername_shouldUseOboServices() {
    // Given: Activity with OBO username
    String streamId = "streamId123";
    Long userId = 123456789L;
    String oboUsername = "obo.user";
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

    // Then: Should use OBO services
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway).obo(authSession);
    verify(oboStreamService).addMemberToRoom(userId, streamId);
    verify(streamService, never()).addMemberToRoom(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
  }

  @Test
  void execute_withOboUsernameAndMultipleUsers_shouldAddAllMembersViaObo() {
    // Given: Activity with OBO username and multiple users
    String streamId = "streamId123";
    List<Long> userIds = Arrays.asList(111L, 222L, 333L);
    String oboUsername = "obo.user";
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

    // Then: Should add all members via OBO
    verify(oboStreamService).addMemberToRoom(111L, streamId);
    verify(oboStreamService).addMemberToRoom(222L, streamId);
    verify(oboStreamService).addMemberToRoom(333L, streamId);
    verify(oboStreamService, times(3)).addMemberToRoom(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.eq(streamId));
  }

  // Tests for execute method - OBO scenarios with userId

  @Test
  void execute_withOboUserId_shouldUseOboServices() {
    // Given: Activity with OBO user ID
    String streamId = "streamId123";
    Long userId = 123456789L;
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(oboUserId);
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(obo);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO services
    verify(bdkGateway).obo(oboUserId);
    verify(bdkGateway).obo(authSession);
    verify(oboStreamService).addMemberToRoom(userId, streamId);
    verify(streamService, never()).addMemberToRoom(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
  }

  @Test
  void execute_withOboUserIdAndMultipleUsers_shouldAddAllMembersViaObo() {
    // Given: Activity with OBO user ID and multiple users
    String streamId = "streamId123";
    List<Long> userIds = Arrays.asList(111L, 222L);
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(oboUserId);
    activity.setStreamId(streamId);
    activity.setUserIds(userIds);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: Execute is called
    executor.execute(context);

    // Then: Should add all members via OBO
    verify(oboStreamService).addMemberToRoom(111L, streamId);
    verify(oboStreamService).addMemberToRoom(222L, streamId);
    verify(oboStreamService, times(2)).addMemberToRoom(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.eq(streamId));
  }

  // Tests for execute method - OBO preference (username over userId)

  @Test
  void execute_withBothOboUsernameAndUserId_shouldPreferUsername() {
    // Given: Activity with both OBO username and user ID
    String streamId = "streamId123";
    Long userId = 123456789L;
    String oboUsername = "obo.user";
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

    // Then: Should prefer username over user ID
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway, never()).obo(oboUserId);
    verify(oboStreamService).addMemberToRoom(userId, streamId);
  }

  // Tests for execute method - edge cases

  @Test
  void execute_withOboAndEmptyUserList_shouldNotCallStreamService() {
    // Given: Activity with OBO and empty user list
    String streamId = "streamId123";
    String oboUsername = "obo.user";
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

    // Then: Should not call stream service
    verify(oboStreamService, never()).addMemberToRoom(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
  }

  @Test
  void execute_shouldNotThrowException() {
    // Given: Valid activity setup
    String streamId = "streamId123";
    Long userId = 123456789L;
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(null);

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid activity setup
    String streamId = "streamId123";
    Long userId = 123456789L;
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(null);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(streamService, times(2)).addMemberToRoom(userId, streamId);
  }

  // Tests for doOboWithCache method

  @Test
  void doOboWithCache_withOboUsername_shouldAddMembersViaObo() {
    // Given: Activity with OBO username
    String streamId = "streamId123";
    Long userId = 123456789L;
    String oboUsername = "obo.user";
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

    // Then: Should add member via OBO and return null
    assertThat(result).isNull();
    verify(bdkGateway).obo(oboUsername);
    verify(oboStreamService).addMemberToRoom(userId, streamId);
  }

  @Test
  void doOboWithCache_withOboUserId_shouldAddMembersViaObo() {
    // Given: Activity with OBO user ID
    String streamId = "streamId123";
    Long userId = 123456789L;
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(oboUserId);
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(obo);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: doOboWithCache is called
    Void result = executor.doOboWithCache(context);

    // Then: Should add member via OBO and return null
    assertThat(result).isNull();
    verify(bdkGateway).obo(oboUserId);
    verify(oboStreamService).addMemberToRoom(userId, streamId);
  }

  @Test
  void doOboWithCache_withMultipleUsers_shouldAddAllMembers() {
    // Given: Activity with multiple users and OBO
    String streamId = "streamId123";
    List<Long> userIds = Arrays.asList(111L, 222L, 333L, 444L);
    String oboUsername = "obo.user";
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

    // Then: Should add all members and return null
    assertThat(result).isNull();
    verify(oboStreamService).addMemberToRoom(111L, streamId);
    verify(oboStreamService).addMemberToRoom(222L, streamId);
    verify(oboStreamService).addMemberToRoom(333L, streamId);
    verify(oboStreamService).addMemberToRoom(444L, streamId);
    verify(oboStreamService, times(4)).addMemberToRoom(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.eq(streamId));
  }

  @Test
  void doOboWithCache_withEmptyUserList_shouldNotCallStreamService() {
    // Given: Activity with empty user list and OBO
    String streamId = "streamId123";
    String oboUsername = "obo.user";
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

    // Then: Should return null without calling stream service
    assertThat(result).isNull();
    verify(oboStreamService, never()).addMemberToRoom(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
  }

  @Test
  void doOboWithCache_shouldAlwaysReturnNull() {
    // Given: Valid activity with OBO
    String streamId = "streamId123";
    Long userId = 123456789L;
    String oboUsername = "obo.user";
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

    // Then: Should return null
    assertThat(result).isNull();
  }

  @Test
  void doOboWithCache_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid activity with OBO
    String streamId = "streamId123";
    Long userId = 123456789L;
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: doOboWithCache is called multiple times
    Void result1 = executor.doOboWithCache(context);
    Void result2 = executor.doOboWithCache(context);

    // Then: Should work correctly both times and always return null
    assertThat(result1).isNull();
    assertThat(result2).isNull();
    verify(oboStreamService, times(2)).addMemberToRoom(userId, streamId);
  }

  @Test
  void doOboWithCache_withDifferentStreamIds_shouldUseCorrectStreamId() {
    // Given: Activity with OBO
    String streamId1 = "stream123";
    String streamId2 = "stream456";
    Long userId = 111L;
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setUserIds(Collections.singletonList(userId));

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // First call with stream ID 1
    activity.setStreamId(streamId1);
    executor.doOboWithCache(context);

    // Second call with stream ID 2
    activity.setStreamId(streamId2);
    executor.doOboWithCache(context);

    // Then: Should use correct stream IDs
    verify(oboStreamService).addMemberToRoom(userId, streamId1);
    verify(oboStreamService).addMemberToRoom(userId, streamId2);
  }

  @Test
  void doOboWithCache_shouldNotThrowException() {
    // Given: Valid activity with OBO
    String streamId = "streamId123";
    Long userId = 123456789L;
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setUserIds(Collections.singletonList(userId));
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When/Then: doOboWithCache should not throw exception
    assertThatCode(() -> executor.doOboWithCache(context))
        .doesNotThrowAnyException();
  }
}
