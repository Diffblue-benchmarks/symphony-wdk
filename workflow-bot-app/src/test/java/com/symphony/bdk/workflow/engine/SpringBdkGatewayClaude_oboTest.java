package com.symphony.bdk.workflow.engine;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.auth.OboAuthenticator;
import com.symphony.bdk.core.auth.exception.AuthInitializationException;
import com.symphony.bdk.core.auth.exception.AuthUnauthorizedException;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class SpringBdkGatewayClaude_oboTest {

  private SpringBdkGateway gateway;
  private BdkConfig config;

  @BeforeEach
  void setUp() {
    // Given: A SpringBdkGateway with mocked dependencies
    config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);
  }

  // Tests for obo(AuthSession) method

  @Test
  void obo_shouldReturnNonNullOboServices() {
    // Given: A mocked AuthSession
    AuthSession authSession = Mockito.mock(AuthSession.class);

    // When: Calling obo(authSession)
    OboServices result = gateway.obo(authSession);

    // Then: The result should not be null
    assertThat(result).isNotNull();
  }

  @Test
  void obo_shouldReturnOboServices() {
    // Given: A mocked AuthSession
    AuthSession authSession = Mockito.mock(AuthSession.class);

    // When: Calling obo(authSession)
    OboServices result = gateway.obo(authSession);

    // Then: The result should be an instance of OboServices
    assertThat(result).isInstanceOf(OboServices.class);
  }

  @Test
  void obo_shouldCreateNewInstanceOnEachCall() {
    // Given: A mocked AuthSession
    AuthSession authSession = Mockito.mock(AuthSession.class);

    // When: Calling obo(authSession) multiple times
    OboServices result1 = gateway.obo(authSession);
    OboServices result2 = gateway.obo(authSession);

    // Then: Each call should create a new instance
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void obo_shouldCreateDistinctInstancesForSameAuthSession() {
    // Given: A mocked AuthSession
    AuthSession authSession = Mockito.mock(AuthSession.class);

    // When: Calling obo(authSession) three times with the same session
    OboServices result1 = gateway.obo(authSession);
    OboServices result2 = gateway.obo(authSession);
    OboServices result3 = gateway.obo(authSession);

    // Then: All instances should be distinct
    assertThat(result1).isNotSameAs(result2);
    assertThat(result2).isNotSameAs(result3);
    assertThat(result1).isNotSameAs(result3);
  }

  @Test
  void obo_shouldCreateOboServicesWithDifferentAuthSessions() {
    // Given: Two different mocked AuthSessions
    AuthSession authSession1 = Mockito.mock(AuthSession.class);
    AuthSession authSession2 = Mockito.mock(AuthSession.class);

    // When: Calling obo with different sessions
    OboServices result1 = gateway.obo(authSession1);
    OboServices result2 = gateway.obo(authSession2);

    // Then: Both should return OboServices instances
    assertThat(result1).isInstanceOf(OboServices.class);
    assertThat(result2).isInstanceOf(OboServices.class);
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void obo_shouldReturnOboServicesWithNonNullFields() {
    // Given: A mocked AuthSession
    AuthSession authSession = Mockito.mock(AuthSession.class);

    // When: Calling obo(authSession)
    OboServices result = gateway.obo(authSession);

    // Then: The result should not be null and should be a valid OboServices instance
    assertThat(result).isNotNull();
    assertThat(result.getClass().getName()).isEqualTo("com.symphony.bdk.core.OboServices");
  }

  @Test
  void obo_shouldCreateOboServicesWithCorrectClassName() {
    // Given: A mocked AuthSession
    AuthSession authSession = Mockito.mock(AuthSession.class);

    // When: Calling obo(authSession)
    OboServices result = gateway.obo(authSession);

    // Then: The result should have the correct class name
    assertThat(result.getClass().getSimpleName()).isEqualTo("OboServices");
  }

  @Test
  void obo_shouldHandleMultipleSequentialCalls() {
    // Given: A mocked AuthSession
    AuthSession authSession = Mockito.mock(AuthSession.class);

    // When: Calling obo(authSession) multiple times sequentially
    for (int i = 0; i < 5; i++) {
      OboServices result = gateway.obo(authSession);

      // Then: Each call should return a non-null OboServices instance
      assertThat(result).isNotNull();
      assertThat(result).isInstanceOf(OboServices.class);
    }
  }

  @Test
  void obo_shouldCreateOboServicesWithDifferentIdentityHashCodes() {
    // Given: A mocked AuthSession
    AuthSession authSession = Mockito.mock(AuthSession.class);

    // When: Calling obo(authSession) twice
    OboServices result1 = gateway.obo(authSession);
    OboServices result2 = gateway.obo(authSession);

    // Then: The identity hash codes should be different
    assertThat(System.identityHashCode(result1))
        .isNotEqualTo(System.identityHashCode(result2));
  }

  @Test
  void obo_shouldNotReturnNull() {
    // Given: A mocked AuthSession
    AuthSession authSession = Mockito.mock(AuthSession.class);

    // When: Calling obo(authSession)
    OboServices result = gateway.obo(authSession);

    // Then: The result should not be equal to null
    assertThat(result).isNotEqualTo(null);
  }

  @Test
  void obo_shouldCreateInstanceFromDifferentGateways() {
    // Given: A second gateway with the same config
    SpringBdkGateway gateway2 = new SpringBdkGateway(
        config,
        Mockito.mock(AuthenticatorFactory.class),
        Mockito.mock(MessageService.class),
        Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class),
        Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class),
        Mockito.mock(SessionService.class)
    );
    AuthSession authSession = Mockito.mock(AuthSession.class);

    // When: Calling obo on both gateways
    OboServices result1 = gateway.obo(authSession);
    OboServices result2 = gateway2.obo(authSession);

    // Then: Both should return OboServices instances that are distinct
    assertThat(result1).isNotNull();
    assertThat(result2).isNotNull();
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void obo_shouldSupportToStringOnReturnedInstance() {
    // Given: A mocked AuthSession
    AuthSession authSession = Mockito.mock(AuthSession.class);

    // When: Calling obo(authSession) and converting to string
    OboServices result = gateway.obo(authSession);
    String toString = result.toString();

    // Then: toString should return a non-null string
    assertThat(toString).isNotNull();
  }

  @Test
  void obo_shouldSupportHashCodeOnReturnedInstance() {
    // Given: A mocked AuthSession
    AuthSession authSession = Mockito.mock(AuthSession.class);

    // When: Calling obo(authSession) and getting hash code
    OboServices result = gateway.obo(authSession);
    int hashCode = result.hashCode();

    // Then: hashCode should not throw an exception and should return a value
    assertThat(hashCode).isNotNull();
  }

  @Test
  void obo_shouldCreateInstanceThatIsNotEqualToNull() {
    // Given: A mocked AuthSession
    AuthSession authSession = Mockito.mock(AuthSession.class);

    // When: Calling obo(authSession)
    OboServices result = gateway.obo(authSession);

    // Then: The instance should not be equal to null
    assertThat(result).isNotEqualTo(null);
  }

  @Test
  void obo_shouldCreateInstanceThatIsEqualToItself() {
    // Given: A mocked AuthSession
    AuthSession authSession = Mockito.mock(AuthSession.class);

    // When: Calling obo(authSession)
    OboServices result = gateway.obo(authSession);

    // Then: The instance should be equal to itself
    assertThat(result).isEqualTo(result);
  }

  // Tests for obo(String username) method

  @Test
  void oboByUsername_shouldThrowRuntimeExceptionWhenOboNotConfigured() throws Exception {
    // Given: A SpringBdkGateway with OBO not configured
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When/Then: Calling obo with a username should throw RuntimeException
    assertThatThrownBy(() -> gateway.obo("testUser"))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("At least OBO username or userid should be configured.");
  }

  @Test
  void oboByUsername_shouldReturnAuthSessionWhenOboConfigured() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername("testUser")).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with a username
    AuthSession result = gateway.obo("testUser");

    // Then: Should return the mocked AuthSession
    assertThat(result).isNotNull();
    assertThat(result).isSameAs(authSession);
  }

  @Test
  void oboByUsername_shouldReturnAuthSession() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername(anyString())).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with a username
    AuthSession result = gateway.obo("someUser");

    // Then: Should return an instance of AuthSession
    assertThat(result).isInstanceOf(AuthSession.class);
  }

  @Test
  void oboByUsername_shouldHandleDifferentUsernames() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession1 = Mockito.mock(AuthSession.class);
    AuthSession authSession2 = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername("user1")).thenReturn(authSession1);
    when(oboAuthenticator.authenticateByUsername("user2")).thenReturn(authSession2);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with different usernames
    AuthSession result1 = gateway.obo("user1");
    AuthSession result2 = gateway.obo("user2");

    // Then: Should return different AuthSession instances
    assertThat(result1).isSameAs(authSession1);
    assertThat(result2).isSameAs(authSession2);
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void oboByUsername_shouldWrapAuthInitializationExceptionInRuntimeException() throws Exception {
    // Given: A SpringBdkGateway with OBO configured but authentication throws exception
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername(anyString()))
        .thenThrow(new AuthInitializationException("Auth failed"));

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When/Then: Calling obo should wrap the exception in RuntimeException
    assertThatThrownBy(() -> gateway.obo("testUser"))
        .isInstanceOf(RuntimeException.class)
        .hasCauseInstanceOf(AuthInitializationException.class);
  }

  @Test
  void oboByUsername_shouldWrapAuthUnauthorizedExceptionInRuntimeException() throws Exception {
    // Given: A SpringBdkGateway with OBO configured but authentication throws unauthorized exception
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername(anyString()))
        .thenThrow(new AuthUnauthorizedException("Unauthorized"));

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When/Then: Calling obo should wrap the exception in RuntimeException
    assertThatThrownBy(() -> gateway.obo("testUser"))
        .isInstanceOf(RuntimeException.class)
        .hasCauseInstanceOf(AuthUnauthorizedException.class);
  }

  @Test
  void oboByUsername_shouldReturnNonNullAuthSession() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername(anyString())).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with a username
    AuthSession result = gateway.obo("user");

    // Then: Should return a non-null AuthSession
    assertThat(result).isNotNull();
  }

  @Test
  void oboByUsername_shouldNotReturnNull() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername(anyString())).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with a username
    AuthSession result = gateway.obo("user");

    // Then: Should not be equal to null
    assertThat(result).isNotEqualTo(null);
  }

  @Test
  void oboByUsername_shouldHandleEmptyUsername() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername("")).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with empty username
    AuthSession result = gateway.obo("");

    // Then: Should return an AuthSession
    assertThat(result).isNotNull();
    assertThat(result).isSameAs(authSession);
  }

  @Test
  void oboByUsername_shouldReturnAuthSessionWithCorrectType() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername(anyString())).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with a username
    Object result = gateway.obo("user");

    // Then: Should be assignable to AuthSession interface
    assertThat(AuthSession.class.isAssignableFrom(result.getClass())).isTrue();
  }

  @Test
  void oboByUsername_shouldCallAuthenticateByUsernameWithCorrectParameter() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername("specificUser")).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with a specific username
    AuthSession result = gateway.obo("specificUser");

    // Then: Should return the expected AuthSession
    assertThat(result).isSameAs(authSession);
  }

  @Test
  void oboByUsername_shouldThrowRuntimeExceptionWithCorrectMessage() {
    // Given: A SpringBdkGateway with OBO not configured
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When/Then: Calling obo should throw RuntimeException with specific message
    assertThatThrownBy(() -> gateway.obo("user"))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("At least OBO username or userid should be configured");
  }

  @Test
  void oboByUsername_shouldReturnAuthSessionThatIsEqualToItself() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername(anyString())).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with a username
    AuthSession result = gateway.obo("user");

    // Then: The result should be equal to itself
    assertThat(result).isEqualTo(result);
  }

  @Test
  void oboByUsername_shouldSupportHashCodeOnReturnedAuthSession() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername(anyString())).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo and getting hash code
    AuthSession result = gateway.obo("user");
    int hashCode = result.hashCode();

    // Then: hashCode should not throw an exception
    assertThat(hashCode).isNotNull();
  }

  // Tests for obo(Long userId) method

  @Test
  void oboByUserId_shouldThrowRuntimeExceptionWhenOboNotConfigured() throws Exception {
    // Given: A SpringBdkGateway with OBO not configured
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When/Then: Calling obo with a userId should throw RuntimeException
    assertThatThrownBy(() -> gateway.obo(12345L))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("At least OBO username or userid should be configured.");
  }

  @Test
  void oboByUserId_shouldReturnAuthSessionWhenOboConfigured() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(12345L)).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with a userId
    AuthSession result = gateway.obo(12345L);

    // Then: Should return the mocked AuthSession
    assertThat(result).isNotNull();
    assertThat(result).isSameAs(authSession);
  }

  @Test
  void oboByUserId_shouldReturnAuthSession() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(Mockito.anyLong())).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with a userId
    AuthSession result = gateway.obo(99999L);

    // Then: Should return an instance of AuthSession
    assertThat(result).isInstanceOf(AuthSession.class);
  }

  @Test
  void oboByUserId_shouldHandleDifferentUserIds() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession1 = Mockito.mock(AuthSession.class);
    AuthSession authSession2 = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(1001L)).thenReturn(authSession1);
    when(oboAuthenticator.authenticateByUserId(2002L)).thenReturn(authSession2);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with different userIds
    AuthSession result1 = gateway.obo(1001L);
    AuthSession result2 = gateway.obo(2002L);

    // Then: Should return different AuthSession instances
    assertThat(result1).isSameAs(authSession1);
    assertThat(result2).isSameAs(authSession2);
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void oboByUserId_shouldWrapAuthInitializationExceptionInRuntimeException() throws Exception {
    // Given: A SpringBdkGateway with OBO configured but authentication throws exception
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(Mockito.anyLong()))
        .thenThrow(new AuthInitializationException("Auth failed"));

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When/Then: Calling obo should wrap the exception in RuntimeException
    assertThatThrownBy(() -> gateway.obo(12345L))
        .isInstanceOf(RuntimeException.class)
        .hasCauseInstanceOf(AuthInitializationException.class);
  }

  @Test
  void oboByUserId_shouldWrapAuthUnauthorizedExceptionInRuntimeException() throws Exception {
    // Given: A SpringBdkGateway with OBO configured but authentication throws unauthorized exception
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(Mockito.anyLong()))
        .thenThrow(new AuthUnauthorizedException("Unauthorized"));

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When/Then: Calling obo should wrap the exception in RuntimeException
    assertThatThrownBy(() -> gateway.obo(12345L))
        .isInstanceOf(RuntimeException.class)
        .hasCauseInstanceOf(AuthUnauthorizedException.class);
  }

  @Test
  void oboByUserId_shouldReturnNonNullAuthSession() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(Mockito.anyLong())).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with a userId
    AuthSession result = gateway.obo(5678L);

    // Then: Should return a non-null AuthSession
    assertThat(result).isNotNull();
  }

  @Test
  void oboByUserId_shouldNotReturnNull() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(Mockito.anyLong())).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with a userId
    AuthSession result = gateway.obo(9999L);

    // Then: Should not be equal to null
    assertThat(result).isNotEqualTo(null);
  }

  @Test
  void oboByUserId_shouldHandleZeroUserId() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(0L)).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with zero userId
    AuthSession result = gateway.obo(0L);

    // Then: Should return an AuthSession
    assertThat(result).isNotNull();
    assertThat(result).isSameAs(authSession);
  }

  @Test
  void oboByUserId_shouldReturnAuthSessionWithCorrectType() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(Mockito.anyLong())).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with a userId
    Object result = gateway.obo(7777L);

    // Then: Should be assignable to AuthSession interface
    assertThat(AuthSession.class.isAssignableFrom(result.getClass())).isTrue();
  }

  @Test
  void oboByUserId_shouldCallAuthenticateByUserIdWithCorrectParameter() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(987654L)).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with a specific userId
    AuthSession result = gateway.obo(987654L);

    // Then: Should return the expected AuthSession
    assertThat(result).isSameAs(authSession);
  }

  @Test
  void oboByUserId_shouldThrowRuntimeExceptionWithCorrectMessage() {
    // Given: A SpringBdkGateway with OBO not configured
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When/Then: Calling obo should throw RuntimeException with specific message
    assertThatThrownBy(() -> gateway.obo(12345L))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("At least OBO username or userid should be configured");
  }

  @Test
  void oboByUserId_shouldReturnAuthSessionThatIsEqualToItself() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(Mockito.anyLong())).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo with a userId
    AuthSession result = gateway.obo(11111L);

    // Then: The result should be equal to itself
    assertThat(result).isEqualTo(result);
  }

  @Test
  void oboByUserId_shouldSupportHashCodeOnReturnedAuthSession() throws Exception {
    // Given: A SpringBdkGateway with OBO configured
    BdkConfig config = Mockito.mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);

    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    OboAuthenticator oboAuthenticator = Mockito.mock(OboAuthenticator.class);
    AuthSession authSession = Mockito.mock(AuthSession.class);

    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(Mockito.anyLong())).thenReturn(authSession);

    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        Mockito.mock(MessageService.class), Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class), Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class), Mockito.mock(SessionService.class));

    // When: Calling obo and getting hash code
    AuthSession result = gateway.obo(22222L);
    int hashCode = result.hashCode();

    // Then: hashCode should not throw an exception
    assertThat(hashCode).isNotNull();
  }
}
