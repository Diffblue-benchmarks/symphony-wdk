package com.symphony.bdk.workflow.engine.executor.connection;

import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.gen.api.model.UserConnection;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.RejectConnection;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

class RejectConnectionExecutorClaudeTest {

  private RejectConnectionExecutor executor;
  private ActivityExecutorContext<RejectConnection> context;
  private RejectConnection activity;
  private BdkGateway bdkGateway;
  private ConnectionService connectionService;
  private UserConnection userConnection;

  @BeforeEach
  void setUp() {
    executor = new RejectConnectionExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new RejectConnection();
    bdkGateway = mock(BdkGateway.class);
    connectionService = mock(ConnectionService.class);
    userConnection = new UserConnection();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.connections()).thenReturn(connectionService);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    RejectConnectionExecutor newExecutor = new RejectConnectionExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    RejectConnectionExecutor newExecutor = new RejectConnectionExecutor();

    // Then: Instance should be of RejectConnectionExecutor type and implement ActivityExecutor
    assertThat(newExecutor).isInstanceOf(RejectConnectionExecutor.class);
    assertThat(newExecutor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    RejectConnectionExecutor executor1 = new RejectConnectionExecutor();
    RejectConnectionExecutor executor2 = new RejectConnectionExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new RejectConnectionExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - non-OBO scenarios

  @Test
  void execute_withValidUserId_shouldRejectConnectionAndSetOutputVariable() {
    // Given: A valid user ID without OBO
    String userId = "123456789";
    activity.setUserId(userId);
    activity.setObo(null);

    userConnection.setUserId(Long.parseLong(userId));
    userConnection.setStatus(UserConnection.StatusEnum.REJECTED);

    when(connectionService.rejectConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Connection should be rejected and output variable set
    verify(connectionService).rejectConnection(Long.parseLong(userId));
    verify(context).setOutputVariable(eq("connection"), eq(userConnection));
  }

  @Test
  void execute_withDifferentUserId_shouldUseCorrectUserId() {
    // Given: A different user ID
    String userId = "987654321";
    activity.setUserId(userId);
    activity.setObo(null);

    when(connectionService.rejectConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use the correct user ID
    verify(connectionService).rejectConnection(987654321L);
    verify(context).setOutputVariable(eq("connection"), any(UserConnection.class));
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid setup
    String userId = "123456789";
    activity.setUserId(userId);
    activity.setObo(null);

    when(connectionService.rejectConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(connectionService, times(2)).rejectConnection(Long.parseLong(userId));
    verify(context, times(2)).setOutputVariable(eq("connection"), any(UserConnection.class));
  }

  @Test
  void execute_withLargeUserId_shouldHandleCorrectly() {
    // Given: A large user ID
    String userId = "9223372036854775807"; // Max long value
    activity.setUserId(userId);
    activity.setObo(null);

    when(connectionService.rejectConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should complete without exception
    verify(connectionService).rejectConnection(Long.parseLong(userId));
    verify(context).setOutputVariable(eq("connection"), any(UserConnection.class));
  }

  // Tests for execute method - OBO scenarios with username

  @Test
  void execute_withOboUsername_shouldUseOboSession() {
    // Given: Activity with OBO username
    String userId = "123456789";
    String oboUsername = "obo.user";
    activity.setUserId(userId);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.rejectConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO session
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway).obo(authSession);
    verify(oboConnectionService).rejectConnection(Long.parseLong(userId));
    verify(context).setOutputVariable(eq("connection"), eq(userConnection));
  }

  @Test
  void execute_withOboUsername_shouldNotCallNonOboConnection() {
    // Given: Activity with OBO username
    String userId = "123456789";
    String oboUsername = "obo.user";
    activity.setUserId(userId);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.rejectConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Non-OBO connection service should not be called
    verify(connectionService, times(0)).rejectConnection(anyLong());
  }

  // Tests for execute method - OBO scenarios with userId

  @Test
  void execute_withOboUserId_shouldUseOboSession() {
    // Given: Activity with OBO user ID
    String userId = "123456789";
    Long oboUserId = 987654321L;
    activity.setUserId(userId);

    Obo obo = new Obo();
    obo.setUserId(oboUserId);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.rejectConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO session
    verify(bdkGateway).obo(oboUserId);
    verify(bdkGateway).obo(authSession);
    verify(oboConnectionService).rejectConnection(Long.parseLong(userId));
    verify(context).setOutputVariable(eq("connection"), eq(userConnection));
  }

  @Test
  void execute_withOboUserIdAndUsername_shouldPreferUsername() {
    // Given: Activity with both OBO username and user ID (username should be preferred)
    String userId = "123456789";
    String oboUsername = "obo.user";
    Long oboUserId = 987654321L;
    activity.setUserId(userId);

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
    when(oboConnectionService.rejectConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Should prefer username over user ID
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway, times(0)).obo(oboUserId);
    verify(oboConnectionService).rejectConnection(Long.parseLong(userId));
    verify(context).setOutputVariable(eq("connection"), eq(userConnection));
  }

  // Tests for execute method - edge cases

  @Test
  void execute_withEmptyOboObject_shouldUseNonOboConnection() {
    // Given: Activity with empty OBO object (both username and userId are null)
    String userId = "123456789";
    activity.setUserId(userId);

    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(null);
    activity.setObo(obo);

    when(connectionService.rejectConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-OBO connection (since OBO is effectively null)
    verify(connectionService).rejectConnection(Long.parseLong(userId));
    verify(context).setOutputVariable(eq("connection"), eq(userConnection));
  }

  @Test
  void execute_withNullObo_shouldUseNonOboConnection() {
    // Given: Activity without OBO
    String userId = "123456789";
    activity.setUserId(userId);
    activity.setObo(null);

    when(connectionService.rejectConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-OBO connection
    verify(connectionService).rejectConnection(Long.parseLong(userId));
    verify(context).setOutputVariable(eq("connection"), eq(userConnection));
  }

  @Test
  void execute_withDifferentUserConnectionStatus_shouldSetCorrectOutputVariable() {
    // Given: User connection with pending status
    String userId = "123456789";
    activity.setUserId(userId);
    activity.setObo(null);

    userConnection.setUserId(Long.parseLong(userId));
    userConnection.setStatus(UserConnection.StatusEnum.PENDING_INCOMING);

    when(connectionService.rejectConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with the returned connection
    verify(context).setOutputVariable(eq("connection"), eq(userConnection));
  }

  // Tests for doOboWithCache method

  @Test
  void doOboWithCache_withOboUsername_shouldRejectConnection() {
    // Given: Activity with OBO username
    String userId = "123456789";
    String oboUsername = "obo.user";
    activity.setUserId(userId);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.rejectConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: doOboWithCache is called via execute
    executor.execute(context);

    // Then: Should reject connection using OBO session
    verify(oboConnectionService).rejectConnection(Long.parseLong(userId));
    verify(context).setOutputVariable(eq("connection"), eq(userConnection));
  }

  @Test
  void doOboWithCache_withOboUserId_shouldRejectConnection() {
    // Given: Activity with OBO user ID
    String userId = "123456789";
    Long oboUserId = 987654321L;
    activity.setUserId(userId);

    Obo obo = new Obo();
    obo.setUserId(oboUserId);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.rejectConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: doOboWithCache is called via execute
    executor.execute(context);

    // Then: Should reject connection using OBO session
    verify(oboConnectionService).rejectConnection(Long.parseLong(userId));
    verify(context).setOutputVariable(eq("connection"), eq(userConnection));
  }

  @Test
  void doOboWithCache_shouldReturnUserConnection() {
    // Given: Activity with OBO username
    String userId = "123456789";
    String oboUsername = "obo.user";
    activity.setUserId(userId);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    userConnection.setUserId(Long.parseLong(userId));
    userConnection.setStatus(UserConnection.StatusEnum.REJECTED);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.rejectConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: doOboWithCache is called via execute
    executor.execute(context);

    // Then: Should set output variable with returned user connection
    verify(context).setOutputVariable(eq("connection"), eq(userConnection));
  }

  @Test
  void doOboWithCache_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Activity with OBO username
    String userId = "123456789";
    String oboUsername = "obo.user";
    activity.setUserId(userId);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.rejectConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called multiple times (which calls doOboWithCache)
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(oboConnectionService, times(2)).rejectConnection(Long.parseLong(userId));
    verify(context, times(2)).setOutputVariable(eq("connection"), any(UserConnection.class));
  }
}
