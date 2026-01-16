package com.symphony.bdk.workflow.engine.executor.connection;

import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.gen.api.model.UserConnection;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.GetConnection;
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

class GetConnectionExecutorClaudeTest {

  private GetConnectionExecutor executor;
  private ActivityExecutorContext<GetConnection> context;
  private GetConnection activity;
  private BdkGateway bdkGateway;
  private ConnectionService connectionService;
  private UserConnection userConnection;

  @BeforeEach
  void setUp() {
    executor = new GetConnectionExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new GetConnection();
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
    GetConnectionExecutor newExecutor = new GetConnectionExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    GetConnectionExecutor newExecutor = new GetConnectionExecutor();

    // Then: Instance should be of GetConnectionExecutor type and implement ActivityExecutor
    assertThat(newExecutor).isInstanceOf(GetConnectionExecutor.class);
    assertThat(newExecutor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    GetConnectionExecutor executor1 = new GetConnectionExecutor();
    GetConnectionExecutor executor2 = new GetConnectionExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new GetConnectionExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - non-OBO scenarios

  @Test
  void execute_withValidUserId_shouldGetConnectionAndSetOutputVariable() {
    // Given: A valid user ID without OBO
    String userId = "123456789";
    activity.setUserId(userId);
    activity.setObo(null);

    userConnection.setUserId(Long.parseLong(userId));
    userConnection.setStatus(UserConnection.StatusEnum.ACCEPTED);

    when(connectionService.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Connection should be retrieved and output variable set
    verify(connectionService).getConnection(Long.parseLong(userId));
    verify(context).setOutputVariable(eq("connection"), eq(userConnection));
  }

  @Test
  void execute_withDifferentUserId_shouldUseCorrectUserId() {
    // Given: A different user ID
    String userId = "987654321";
    activity.setUserId(userId);
    activity.setObo(null);

    when(connectionService.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use the correct user ID
    verify(connectionService).getConnection(987654321L);
    verify(context).setOutputVariable(eq("connection"), any(UserConnection.class));
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid setup
    String userId = "123456789";
    activity.setUserId(userId);
    activity.setObo(null);

    when(connectionService.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(connectionService, times(2)).getConnection(Long.parseLong(userId));
    verify(context, times(2)).setOutputVariable(eq("connection"), any(UserConnection.class));
  }

  @Test
  void execute_withLargeUserId_shouldHandleCorrectly() {
    // Given: A large user ID
    String userId = "9223372036854775807"; // Max long value
    activity.setUserId(userId);
    activity.setObo(null);

    when(connectionService.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should complete without exception
    verify(connectionService).getConnection(Long.parseLong(userId));
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
    when(oboConnectionService.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO session
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway).obo(authSession);
    verify(oboConnectionService).getConnection(Long.parseLong(userId));
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
    when(oboConnectionService.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Non-OBO connection service should not be called
    verify(connectionService, times(0)).getConnection(anyLong());
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
    when(oboConnectionService.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO session
    verify(bdkGateway).obo(oboUserId);
    verify(bdkGateway).obo(authSession);
    verify(oboConnectionService).getConnection(Long.parseLong(userId));
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
    when(oboConnectionService.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Should prefer username over user ID
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway, times(0)).obo(oboUserId);
    verify(oboConnectionService).getConnection(Long.parseLong(userId));
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

    when(connectionService.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-OBO connection (since OBO is effectively null)
    verify(connectionService).getConnection(Long.parseLong(userId));
    verify(context).setOutputVariable(eq("connection"), eq(userConnection));
  }

  @Test
  void execute_withNullObo_shouldUseNonOboConnection() {
    // Given: Activity without OBO
    String userId = "123456789";
    activity.setUserId(userId);
    activity.setObo(null);

    when(connectionService.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-OBO connection
    verify(connectionService).getConnection(Long.parseLong(userId));
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

    when(connectionService.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with the returned connection
    verify(context).setOutputVariable(eq("connection"), eq(userConnection));
  }

  @Test
  void execute_withMultipleConnectionStatuses_shouldHandleAll() {
    // Given: Testing different connection statuses
    String userId = "123456789";
    activity.setUserId(userId);
    activity.setObo(null);

    UserConnection.StatusEnum[] statuses = {
        UserConnection.StatusEnum.ACCEPTED,
        UserConnection.StatusEnum.PENDING_INCOMING,
        UserConnection.StatusEnum.PENDING_OUTGOING,
        UserConnection.StatusEnum.REJECTED
    };

    for (UserConnection.StatusEnum status : statuses) {
      UserConnection connection = new UserConnection();
      connection.setStatus(status);
      when(connectionService.getConnection(Long.parseLong(userId))).thenReturn(connection);

      // When: Execute is called
      executor.execute(context);

      // Then: Should set output variable with the connection having the correct status
      verify(context).setOutputVariable(eq("connection"), eq(connection));
    }
  }

  // Tests for doOboWithCache method

  @Test
  void doOboWithCache_withOboUsername_shouldGetConnection() {
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
    when(oboConnectionService.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: doOboWithCache is called via execute
    executor.execute(context);

    // Then: Should get connection using OBO session
    verify(oboConnectionService).getConnection(Long.parseLong(userId));
    verify(context).setOutputVariable(eq("connection"), eq(userConnection));
  }

  @Test
  void doOboWithCache_withOboUserId_shouldGetConnection() {
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
    when(oboConnectionService.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: doOboWithCache is called via execute
    executor.execute(context);

    // Then: Should get connection using OBO session
    verify(oboConnectionService).getConnection(Long.parseLong(userId));
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
    userConnection.setStatus(UserConnection.StatusEnum.ACCEPTED);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

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
    when(oboConnectionService.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called multiple times (which calls doOboWithCache)
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(oboConnectionService, times(2)).getConnection(Long.parseLong(userId));
    verify(context, times(2)).setOutputVariable(eq("connection"), any(UserConnection.class));
  }

  @Test
  void doOboWithCache_withDifferentOboSessions_shouldUseCorrectSession() {
    // Given: First call with OBO username
    String userId = "123456789";
    String oboUsername = "obo.user";
    activity.setUserId(userId);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession1 = mock(AuthSession.class);
    OboServices oboServices1 = mock(OboServices.class);
    ConnectionService oboConnectionService1 = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession1);
    when(bdkGateway.obo(authSession1)).thenReturn(oboServices1);
    when(oboServices1.connections()).thenReturn(oboConnectionService1);
    when(oboConnectionService1.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use the correct OBO session
    verify(oboConnectionService1).getConnection(Long.parseLong(userId));

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
    when(oboConnectionService2.getConnection(Long.parseLong(userId))).thenReturn(userConnection);

    // When: Execute is called again
    executor.execute(context);

    // Then: Should use the new OBO session
    verify(oboConnectionService2).getConnection(Long.parseLong(userId));
  }
}
