package com.symphony.bdk.workflow.engine.executor.connection;

import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.connection.constant.ConnectionStatus;
import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.gen.api.model.UserConnection;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.GetConnections;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

class GetConnectionsExecutorClaudeTest {

  private GetConnectionsExecutor executor;
  private ActivityExecutorContext<GetConnections> context;
  private GetConnections activity;
  private BdkGateway bdkGateway;
  private ConnectionService connectionService;
  private List<UserConnection> userConnections;

  @BeforeEach
  void setUp() {
    executor = new GetConnectionsExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new GetConnections();
    bdkGateway = mock(BdkGateway.class);
    connectionService = mock(ConnectionService.class);

    UserConnection conn1 = new UserConnection();
    conn1.setUserId(123456789L);
    conn1.setStatus(UserConnection.StatusEnum.ACCEPTED);

    UserConnection conn2 = new UserConnection();
    conn2.setUserId(987654321L);
    conn2.setStatus(UserConnection.StatusEnum.PENDING_INCOMING);

    userConnections = Arrays.asList(conn1, conn2);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.connections()).thenReturn(connectionService);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    GetConnectionsExecutor newExecutor = new GetConnectionsExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    GetConnectionsExecutor newExecutor = new GetConnectionsExecutor();

    // Then: Instance should be of GetConnectionsExecutor type and implement ActivityExecutor
    assertThat(newExecutor).isInstanceOf(GetConnectionsExecutor.class);
    assertThat(newExecutor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    GetConnectionsExecutor executor1 = new GetConnectionsExecutor();
    GetConnectionsExecutor executor2 = new GetConnectionsExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new GetConnectionsExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - non-OBO scenarios with no filters

  @Test
  void execute_withNoFilters_shouldGetAllConnectionsAndSetOutputVariable() {
    // Given: No status filter or userIds without OBO
    activity.setStatus(null);
    activity.setUserIds(null);
    activity.setObo(null);

    when(connectionService.listConnections(null, null)).thenReturn(userConnections);

    // When: Execute is called
    executor.execute(context);

    // Then: Connections should be retrieved and output variable set
    verify(connectionService).listConnections(isNull(), isNull());
    verify(context).setOutputVariable(eq("connections"), eq(userConnections));
  }

  @Test
  void execute_withEmptyUserIds_shouldUseEmptyList() {
    // Given: Empty userIds list
    List<Long> emptyUserIds = Collections.emptyList();
    activity.setUserIds(emptyUserIds);
    activity.setStatus(null);
    activity.setObo(null);

    when(connectionService.listConnections(null, emptyUserIds)).thenReturn(Collections.emptyList());

    // When: Execute is called
    executor.execute(context);

    // Then: Should use empty list
    verify(connectionService).listConnections(isNull(), eq(emptyUserIds));
    verify(context).setOutputVariable(eq("connections"), any(List.class));
  }

  // Tests for execute method - non-OBO scenarios with status filter

  @Test
  void execute_withAcceptedStatus_shouldFilterByStatus() {
    // Given: Status filter set to ACCEPTED
    activity.setStatus("ACCEPTED");
    activity.setUserIds(null);
    activity.setObo(null);

    List<UserConnection> acceptedConnections = Collections.singletonList(userConnections.get(0));
    when(connectionService.listConnections(ConnectionStatus.ACCEPTED, null))
        .thenReturn(acceptedConnections);

    // When: Execute is called
    executor.execute(context);

    // Then: Should filter by ACCEPTED status
    verify(connectionService).listConnections(eq(ConnectionStatus.ACCEPTED), isNull());
    verify(context).setOutputVariable(eq("connections"), eq(acceptedConnections));
  }

  @Test
  void execute_withPendingIncomingStatus_shouldFilterByStatus() {
    // Given: Status filter set to PENDING_INCOMING
    activity.setStatus("PENDING_INCOMING");
    activity.setUserIds(null);
    activity.setObo(null);

    List<UserConnection> pendingConnections = Collections.singletonList(userConnections.get(1));
    when(connectionService.listConnections(ConnectionStatus.PENDING_INCOMING, null))
        .thenReturn(pendingConnections);

    // When: Execute is called
    executor.execute(context);

    // Then: Should filter by PENDING_INCOMING status
    verify(connectionService).listConnections(eq(ConnectionStatus.PENDING_INCOMING), isNull());
    verify(context).setOutputVariable(eq("connections"), eq(pendingConnections));
  }

  @Test
  void execute_withPendingOutgoingStatus_shouldFilterByStatus() {
    // Given: Status filter set to PENDING_OUTGOING
    activity.setStatus("PENDING_OUTGOING");
    activity.setUserIds(null);
    activity.setObo(null);

    when(connectionService.listConnections(ConnectionStatus.PENDING_OUTGOING, null))
        .thenReturn(Collections.emptyList());

    // When: Execute is called
    executor.execute(context);

    // Then: Should filter by PENDING_OUTGOING status
    verify(connectionService).listConnections(eq(ConnectionStatus.PENDING_OUTGOING), isNull());
    verify(context).setOutputVariable(eq("connections"), any(List.class));
  }

  @Test
  void execute_withRejectedStatus_shouldFilterByStatus() {
    // Given: Status filter set to REJECTED
    activity.setStatus("REJECTED");
    activity.setUserIds(null);
    activity.setObo(null);

    when(connectionService.listConnections(ConnectionStatus.REJECTED, null))
        .thenReturn(Collections.emptyList());

    // When: Execute is called
    executor.execute(context);

    // Then: Should filter by REJECTED status
    verify(connectionService).listConnections(eq(ConnectionStatus.REJECTED), isNull());
    verify(context).setOutputVariable(eq("connections"), any(List.class));
  }

  @Test
  void execute_withAllStatus_shouldFilterByStatus() {
    // Given: Status filter set to ALL
    activity.setStatus("ALL");
    activity.setUserIds(null);
    activity.setObo(null);

    when(connectionService.listConnections(ConnectionStatus.ALL, null))
        .thenReturn(userConnections);

    // When: Execute is called
    executor.execute(context);

    // Then: Should filter by ALL status
    verify(connectionService).listConnections(eq(ConnectionStatus.ALL), isNull());
    verify(context).setOutputVariable(eq("connections"), eq(userConnections));
  }

  // Tests for execute method - non-OBO scenarios with userIds filter

  @Test
  void execute_withSingleUserId_shouldFilterByUserId() {
    // Given: Single user ID filter
    List<Long> userIds = Collections.singletonList(123456789L);
    activity.setUserIds(userIds);
    activity.setStatus(null);
    activity.setObo(null);

    List<UserConnection> filteredConnections = Collections.singletonList(userConnections.get(0));
    when(connectionService.listConnections(null, userIds)).thenReturn(filteredConnections);

    // When: Execute is called
    executor.execute(context);

    // Then: Should filter by user ID
    verify(connectionService).listConnections(isNull(), eq(userIds));
    verify(context).setOutputVariable(eq("connections"), eq(filteredConnections));
  }

  @Test
  void execute_withMultipleUserIds_shouldFilterByUserIds() {
    // Given: Multiple user ID filters
    List<Long> userIds = Arrays.asList(123456789L, 987654321L);
    activity.setUserIds(userIds);
    activity.setStatus(null);
    activity.setObo(null);

    when(connectionService.listConnections(null, userIds)).thenReturn(userConnections);

    // When: Execute is called
    executor.execute(context);

    // Then: Should filter by user IDs
    verify(connectionService).listConnections(isNull(), eq(userIds));
    verify(context).setOutputVariable(eq("connections"), eq(userConnections));
  }

  @Test
  void execute_withLargeUserId_shouldHandleCorrectly() {
    // Given: A large user ID
    List<Long> userIds = Collections.singletonList(9223372036854775807L); // Max long value
    activity.setUserIds(userIds);
    activity.setStatus(null);
    activity.setObo(null);

    when(connectionService.listConnections(null, userIds)).thenReturn(Collections.emptyList());

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should complete without exception
    verify(connectionService).listConnections(isNull(), eq(userIds));
    verify(context).setOutputVariable(eq("connections"), any(List.class));
  }

  // Tests for execute method - non-OBO scenarios with both filters

  @Test
  void execute_withStatusAndUserIds_shouldApplyBothFilters() {
    // Given: Both status and userIds filters
    activity.setStatus("ACCEPTED");
    List<Long> userIds = Collections.singletonList(123456789L);
    activity.setUserIds(userIds);
    activity.setObo(null);

    List<UserConnection> filteredConnections = Collections.singletonList(userConnections.get(0));
    when(connectionService.listConnections(ConnectionStatus.ACCEPTED, userIds))
        .thenReturn(filteredConnections);

    // When: Execute is called
    executor.execute(context);

    // Then: Should apply both filters
    verify(connectionService).listConnections(eq(ConnectionStatus.ACCEPTED), eq(userIds));
    verify(context).setOutputVariable(eq("connections"), eq(filteredConnections));
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid setup
    activity.setStatus(null);
    activity.setUserIds(null);
    activity.setObo(null);

    when(connectionService.listConnections(null, null)).thenReturn(userConnections);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(connectionService, times(2)).listConnections(isNull(), isNull());
    verify(context, times(2)).setOutputVariable(eq("connections"), any(List.class));
  }

  // Tests for execute method - OBO scenarios with username

  @Test
  void execute_withOboUsername_shouldUseOboSession() {
    // Given: Activity with OBO username
    String oboUsername = "obo.user";
    activity.setStatus(null);
    activity.setUserIds(null);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.listConnections(null, null)).thenReturn(userConnections);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO session
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway).obo(authSession);
    verify(oboConnectionService).listConnections(isNull(), isNull());
    verify(context).setOutputVariable(eq("connections"), eq(userConnections));
  }

  @Test
  void execute_withOboUsernameAndStatus_shouldUseOboSessionWithStatus() {
    // Given: Activity with OBO username and status filter
    String oboUsername = "obo.user";
    activity.setStatus("ACCEPTED");
    activity.setUserIds(null);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.listConnections(ConnectionStatus.ACCEPTED, null))
        .thenReturn(userConnections);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO session with status filter
    verify(oboConnectionService).listConnections(eq(ConnectionStatus.ACCEPTED), isNull());
    verify(context).setOutputVariable(eq("connections"), eq(userConnections));
  }

  @Test
  void execute_withOboUsernameAndUserIds_shouldUseOboSessionWithUserIds() {
    // Given: Activity with OBO username and userIds filter
    String oboUsername = "obo.user";
    List<Long> userIds = Arrays.asList(123456789L, 987654321L);
    activity.setStatus(null);
    activity.setUserIds(userIds);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.listConnections(null, userIds)).thenReturn(userConnections);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO session with userIds filter
    verify(oboConnectionService).listConnections(isNull(), eq(userIds));
    verify(context).setOutputVariable(eq("connections"), eq(userConnections));
  }

  @Test
  void execute_withOboUsernameStatusAndUserIds_shouldUseOboSessionWithAllFilters() {
    // Given: Activity with OBO username, status, and userIds filters
    String oboUsername = "obo.user";
    List<Long> userIds = Collections.singletonList(123456789L);
    activity.setStatus("ACCEPTED");
    activity.setUserIds(userIds);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.listConnections(ConnectionStatus.ACCEPTED, userIds))
        .thenReturn(Collections.singletonList(userConnections.get(0)));

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO session with all filters
    verify(oboConnectionService).listConnections(eq(ConnectionStatus.ACCEPTED), eq(userIds));
    verify(context).setOutputVariable(eq("connections"), any(List.class));
  }

  @Test
  void execute_withOboUsername_shouldNotCallNonOboConnection() {
    // Given: Activity with OBO username
    String oboUsername = "obo.user";
    activity.setStatus(null);
    activity.setUserIds(null);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.listConnections(null, null)).thenReturn(userConnections);

    // When: Execute is called
    executor.execute(context);

    // Then: Non-OBO connection service should not be called
    verify(connectionService, times(0)).listConnections(any(), anyList());
  }

  // Tests for execute method - OBO scenarios with userId

  @Test
  void execute_withOboUserId_shouldUseOboSession() {
    // Given: Activity with OBO user ID
    Long oboUserId = 987654321L;
    activity.setStatus(null);
    activity.setUserIds(null);

    Obo obo = new Obo();
    obo.setUserId(oboUserId);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.listConnections(null, null)).thenReturn(userConnections);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO session
    verify(bdkGateway).obo(oboUserId);
    verify(bdkGateway).obo(authSession);
    verify(oboConnectionService).listConnections(isNull(), isNull());
    verify(context).setOutputVariable(eq("connections"), eq(userConnections));
  }

  @Test
  void execute_withOboUserIdAndUsername_shouldPreferUsername() {
    // Given: Activity with both OBO username and user ID (username should be preferred)
    String oboUsername = "obo.user";
    Long oboUserId = 987654321L;
    activity.setStatus(null);
    activity.setUserIds(null);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    obo.setUserId(oboUserId);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.listConnections(null, null)).thenReturn(userConnections);

    // When: Execute is called
    executor.execute(context);

    // Then: Should prefer username over user ID
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway, times(0)).obo(oboUserId);
    verify(oboConnectionService).listConnections(isNull(), isNull());
    verify(context).setOutputVariable(eq("connections"), eq(userConnections));
  }

  // Tests for execute method - edge cases

  @Test
  void execute_withEmptyOboObject_shouldUseNonOboConnection() {
    // Given: Activity with empty OBO object (both username and userId are null)
    activity.setStatus(null);
    activity.setUserIds(null);

    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(null);
    activity.setObo(obo);

    when(connectionService.listConnections(null, null)).thenReturn(userConnections);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-OBO connection (since OBO is effectively null)
    verify(connectionService).listConnections(isNull(), isNull());
    verify(context).setOutputVariable(eq("connections"), eq(userConnections));
  }

  @Test
  void execute_withNullObo_shouldUseNonOboConnection() {
    // Given: Activity without OBO
    activity.setStatus(null);
    activity.setUserIds(null);
    activity.setObo(null);

    when(connectionService.listConnections(null, null)).thenReturn(userConnections);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-OBO connection
    verify(connectionService).listConnections(isNull(), isNull());
    verify(context).setOutputVariable(eq("connections"), eq(userConnections));
  }

  @Test
  void execute_withEmptyResultList_shouldSetEmptyOutputVariable() {
    // Given: Service returns empty list
    activity.setStatus("ACCEPTED");
    activity.setUserIds(null);
    activity.setObo(null);

    List<UserConnection> emptyList = Collections.emptyList();
    when(connectionService.listConnections(ConnectionStatus.ACCEPTED, null)).thenReturn(emptyList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with empty list
    verify(context).setOutputVariable(eq("connections"), eq(emptyList));
  }

  // Tests for doOboWithCache method

  @Test
  void doOboWithCache_withOboUsername_shouldListConnections() {
    // Given: Activity with OBO username
    String oboUsername = "obo.user";
    activity.setStatus(null);
    activity.setUserIds(null);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.listConnections(null, null)).thenReturn(userConnections);

    // When: doOboWithCache is called via execute
    executor.execute(context);

    // Then: Should list connections using OBO session
    verify(oboConnectionService).listConnections(isNull(), isNull());
    verify(context).setOutputVariable(eq("connections"), eq(userConnections));
  }

  @Test
  void doOboWithCache_withOboUserId_shouldListConnections() {
    // Given: Activity with OBO user ID
    Long oboUserId = 987654321L;
    activity.setStatus(null);
    activity.setUserIds(null);

    Obo obo = new Obo();
    obo.setUserId(oboUserId);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.listConnections(null, null)).thenReturn(userConnections);

    // When: doOboWithCache is called via execute
    executor.execute(context);

    // Then: Should list connections using OBO session
    verify(oboConnectionService).listConnections(isNull(), isNull());
    verify(context).setOutputVariable(eq("connections"), eq(userConnections));
  }

  @Test
  void doOboWithCache_withStatusFilter_shouldApplyStatusFilter() {
    // Given: Activity with OBO username and status filter
    String oboUsername = "obo.user";
    activity.setStatus("PENDING_INCOMING");
    activity.setUserIds(null);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.listConnections(ConnectionStatus.PENDING_INCOMING, null))
        .thenReturn(Collections.singletonList(userConnections.get(1)));

    // When: doOboWithCache is called via execute
    executor.execute(context);

    // Then: Should apply status filter
    verify(oboConnectionService).listConnections(eq(ConnectionStatus.PENDING_INCOMING), isNull());
    verify(context).setOutputVariable(eq("connections"), any(List.class));
  }

  @Test
  void doOboWithCache_withUserIdsFilter_shouldApplyUserIdsFilter() {
    // Given: Activity with OBO username and userIds filter
    String oboUsername = "obo.user";
    List<Long> userIds = Collections.singletonList(123456789L);
    activity.setStatus(null);
    activity.setUserIds(userIds);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.listConnections(null, userIds))
        .thenReturn(Collections.singletonList(userConnections.get(0)));

    // When: doOboWithCache is called via execute
    executor.execute(context);

    // Then: Should apply userIds filter
    verify(oboConnectionService).listConnections(isNull(), eq(userIds));
    verify(context).setOutputVariable(eq("connections"), any(List.class));
  }

  @Test
  void doOboWithCache_shouldReturnUserConnectionsList() {
    // Given: Activity with OBO username
    String oboUsername = "obo.user";
    activity.setStatus(null);
    activity.setUserIds(null);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.listConnections(null, null)).thenReturn(userConnections);

    // When: doOboWithCache is called via execute
    executor.execute(context);

    // Then: Should set output variable with returned user connections list
    verify(context).setOutputVariable(eq("connections"), eq(userConnections));
  }

  @Test
  void doOboWithCache_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Activity with OBO username
    String oboUsername = "obo.user";
    activity.setStatus(null);
    activity.setUserIds(null);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.listConnections(null, null)).thenReturn(userConnections);

    // When: Execute is called multiple times (which calls doOboWithCache)
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(oboConnectionService, times(2)).listConnections(isNull(), isNull());
    verify(context, times(2)).setOutputVariable(eq("connections"), any(List.class));
  }

  @Test
  void doOboWithCache_withDifferentOboSessions_shouldUseCorrectSession() {
    // Given: First call with OBO username
    String oboUsername = "obo.user";
    activity.setStatus(null);
    activity.setUserIds(null);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession1 = mock(AuthSession.class);
    OboServices oboServices1 = mock(OboServices.class);
    ConnectionService oboConnectionService1 = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession1);
    when(bdkGateway.obo(authSession1)).thenReturn(oboServices1);
    when(oboServices1.connections()).thenReturn(oboConnectionService1);
    when(oboConnectionService1.listConnections(null, null)).thenReturn(userConnections);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use the correct OBO session
    verify(oboConnectionService1).listConnections(isNull(), isNull());

    // Given: Now change to OBO user ID
    Long oboUserId = 987654321L;
    obo.setUsername(null);
    obo.setUserId(oboUserId);

    AuthSession authSession2 = mock(AuthSession.class);
    OboServices oboServices2 = mock(OboServices.class);
    ConnectionService oboConnectionService2 = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession2);
    when(bdkGateway.obo(authSession2)).thenReturn(oboServices2);
    when(oboServices2.connections()).thenReturn(oboConnectionService2);
    when(oboConnectionService2.listConnections(null, null)).thenReturn(userConnections);

    // When: Execute is called again
    executor.execute(context);

    // Then: Should use the new OBO session
    verify(oboConnectionService2).listConnections(isNull(), isNull());
  }
}
