package com.symphony.bdk.workflow.engine.executor.connection;

import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.RemoveConnection;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

class RemoveConnectionExecutorClaudeTest {

  private RemoveConnectionExecutor executor;
  private ActivityExecutorContext<RemoveConnection> context;
  private RemoveConnection activity;
  private BdkGateway bdkGateway;
  private ConnectionService connectionService;

  @BeforeEach
  void setUp() {
    executor = new RemoveConnectionExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new RemoveConnection();
    bdkGateway = mock(BdkGateway.class);
    connectionService = mock(ConnectionService.class);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.connections()).thenReturn(connectionService);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    RemoveConnectionExecutor newExecutor = new RemoveConnectionExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    RemoveConnectionExecutor newExecutor = new RemoveConnectionExecutor();

    // Then: Instance should be of RemoveConnectionExecutor type and implement ActivityExecutor
    assertThat(newExecutor).isInstanceOf(RemoveConnectionExecutor.class);
    assertThat(newExecutor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    RemoveConnectionExecutor executor1 = new RemoveConnectionExecutor();
    RemoveConnectionExecutor executor2 = new RemoveConnectionExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new RemoveConnectionExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - non-OBO scenarios

  @Test
  void execute_withValidUserId_shouldRemoveConnection() {
    // Given: A valid user ID without OBO
    String userId = "123456789";
    activity.setUserId(userId);
    activity.setObo(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Connection should be removed
    verify(connectionService).removeConnection(Long.parseLong(userId));
  }

  @Test
  void execute_withDifferentUserId_shouldUseCorrectUserId() {
    // Given: A different user ID
    String userId = "987654321";
    activity.setUserId(userId);
    activity.setObo(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use the correct user ID
    verify(connectionService).removeConnection(987654321L);
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid setup
    String userId = "123456789";
    activity.setUserId(userId);
    activity.setObo(null);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(connectionService, times(2)).removeConnection(Long.parseLong(userId));
  }

  @Test
  void execute_withLargeUserId_shouldHandleCorrectly() {
    // Given: A large user ID
    String userId = "9223372036854775807"; // Max long value
    activity.setUserId(userId);
    activity.setObo(null);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should complete without exception
    verify(connectionService).removeConnection(Long.parseLong(userId));
  }

  @Test
  void execute_withSmallUserId_shouldHandleCorrectly() {
    // Given: A small user ID
    String userId = "1";
    activity.setUserId(userId);
    activity.setObo(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should complete without exception
    verify(connectionService).removeConnection(1L);
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

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO session
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway).obo(authSession);
    verify(oboConnectionService).removeConnection(Long.parseLong(userId));
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

    // When: Execute is called
    executor.execute(context);

    // Then: Non-OBO connection service should not be called
    verify(connectionService, times(0)).removeConnection(anyLong());
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

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO session
    verify(bdkGateway).obo(oboUserId);
    verify(bdkGateway).obo(authSession);
    verify(oboConnectionService).removeConnection(Long.parseLong(userId));
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

    // When: Execute is called
    executor.execute(context);

    // Then: Should prefer username over user ID
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway, times(0)).obo(oboUserId);
    verify(oboConnectionService).removeConnection(Long.parseLong(userId));
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

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-OBO connection (since OBO is effectively null)
    verify(connectionService).removeConnection(Long.parseLong(userId));
  }

  @Test
  void execute_withNullObo_shouldUseNonOboConnection() {
    // Given: Activity without OBO
    String userId = "123456789";
    activity.setUserId(userId);
    activity.setObo(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-OBO connection
    verify(connectionService).removeConnection(Long.parseLong(userId));
  }

  // Tests for doOboWithCache method

  @Test
  void doOboWithCache_withOboUsername_shouldRemoveConnection() {
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

    // When: doOboWithCache is called via execute
    executor.execute(context);

    // Then: Should remove connection using OBO session
    verify(oboConnectionService).removeConnection(Long.parseLong(userId));
  }

  @Test
  void doOboWithCache_withOboUserId_shouldRemoveConnection() {
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

    // When: doOboWithCache is called via execute
    executor.execute(context);

    // Then: Should remove connection using OBO session
    verify(oboConnectionService).removeConnection(Long.parseLong(userId));
  }

  @Test
  void doOboWithCache_shouldReturnNull() {
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

    // When: doOboWithCache is called via execute (returns null)
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should complete without exception
    verify(oboConnectionService).removeConnection(Long.parseLong(userId));
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

    // When: Execute is called multiple times (which calls doOboWithCache)
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(oboConnectionService, times(2)).removeConnection(Long.parseLong(userId));
  }

  @Test
  void doOboWithCache_withDifferentUserIds_shouldRemoveCorrectConnections() {
    // Given: Activity with OBO username and a specific user ID
    String userId1 = "111111111";
    String oboUsername = "obo.user";
    activity.setUserId(userId1);

    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);

    // When: Execute is called
    executor.execute(context);

    // Then: Should remove connection for first user ID
    verify(oboConnectionService).removeConnection(111111111L);

    // Given: Change the user ID
    String userId2 = "222222222";
    activity.setUserId(userId2);

    // When: Execute is called again
    executor.execute(context);

    // Then: Should remove connection for second user ID
    verify(oboConnectionService).removeConnection(222222222L);
  }
}
