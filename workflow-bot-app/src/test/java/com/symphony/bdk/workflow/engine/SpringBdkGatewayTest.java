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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpringBdkGatewayTest {

  private SpringBdkGateway gateway;
  private BdkConfig config;
  private AuthenticatorFactory authenticatorFactory;
  private MessageService messageService;
  private StreamService streamService;
  private UserService userService;
  private ConnectionService connectionService;
  private SymphonyGroupService groupService;
  private SessionService sessionService;

  @BeforeEach
  void setUp() {
    config = mock(BdkConfig.class);
    authenticatorFactory = mock(AuthenticatorFactory.class);
    messageService = mock(MessageService.class);
    streamService = mock(StreamService.class);
    userService = mock(UserService.class);
    connectionService = mock(ConnectionService.class);
    groupService = mock(SymphonyGroupService.class);
    sessionService = mock(SessionService.class);

    gateway = new SpringBdkGateway(config, authenticatorFactory, messageService,
        streamService, userService, connectionService, groupService, sessionService);
  }

  @Test
  void shouldInitializeWithAllDependencies() {
    assertThat(gateway).isNotNull();
    assertThat(gateway.messages()).isSameAs(messageService);
    assertThat(gateway.streams()).isSameAs(streamService);
    assertThat(gateway.users()).isSameAs(userService);
    assertThat(gateway.connections()).isSameAs(connectionService);
    assertThat(gateway.groups()).isSameAs(groupService);
    assertThat(gateway.session()).isSameAs(sessionService);
  }

  @Test
  void shouldReturnMessageService() {
    assertThat(gateway.messages()).isSameAs(messageService);
  }

  @Test
  void shouldReturnStreamService() {
    assertThat(gateway.streams()).isSameAs(streamService);
  }

  @Test
  void shouldReturnUserService() {
    assertThat(gateway.users()).isSameAs(userService);
  }

  @Test
  void shouldReturnConnectionService() {
    assertThat(gateway.connections()).isSameAs(connectionService);
  }

  @Test
  void shouldReturnSessionService() {
    assertThat(gateway.session()).isSameAs(sessionService);
  }

  @Test
  void shouldReturnGroupService() {
    assertThat(gateway.groups()).isSameAs(groupService);
  }

  @Test
  void shouldAuthenticateOboByUsername() throws AuthInitializationException, AuthUnauthorizedException {
    String username = "testuser";
    AuthSession authSession = mock(AuthSession.class);
    OboAuthenticator oboAuthenticator = mock(OboAuthenticator.class);

    when(config.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername(username)).thenReturn(authSession);

    AuthSession result = gateway.obo(username);

    assertThat(result).isSameAs(authSession);
  }

  @Test
  void shouldThrowRuntimeExceptionWhenOboNotConfiguredForUsername() {
    String username = "testuser";

    when(config.isOboConfigured()).thenReturn(false);

    assertThatThrownBy(() -> gateway.obo(username))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("At least OBO username or userid should be configured.");
  }

  @Test
  void shouldThrowRuntimeExceptionWhenAuthInitializationFailsForUsername() throws AuthInitializationException, AuthUnauthorizedException {
    String username = "testuser";
    OboAuthenticator oboAuthenticator = mock(OboAuthenticator.class);

    when(config.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername(username)).thenThrow(new RuntimeException(new AuthInitializationException("Init error")));

    assertThatThrownBy(() -> gateway.obo(username))
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  void shouldThrowRuntimeExceptionWhenAuthUnauthorizedForUsername() throws AuthInitializationException, AuthUnauthorizedException {
    String username = "testuser";
    OboAuthenticator oboAuthenticator = mock(OboAuthenticator.class);

    when(config.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername(username)).thenThrow(new RuntimeException(new AuthUnauthorizedException("Unauthorized")));

    assertThatThrownBy(() -> gateway.obo(username))
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  void shouldAuthenticateOboByUserId() throws AuthInitializationException, AuthUnauthorizedException {
    Long userId = 123456L;
    AuthSession authSession = mock(AuthSession.class);
    OboAuthenticator oboAuthenticator = mock(OboAuthenticator.class);

    when(config.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(userId)).thenReturn(authSession);

    AuthSession result = gateway.obo(userId);

    assertThat(result).isSameAs(authSession);
  }

  @Test
  void shouldThrowRuntimeExceptionWhenOboNotConfiguredForUserId() {
    Long userId = 123456L;

    when(config.isOboConfigured()).thenReturn(false);

    assertThatThrownBy(() -> gateway.obo(userId))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("At least OBO username or userid should be configured.");
  }

  @Test
  void shouldThrowRuntimeExceptionWhenAuthInitializationFailsForUserId() throws AuthInitializationException, AuthUnauthorizedException {
    Long userId = 123456L;
    OboAuthenticator oboAuthenticator = mock(OboAuthenticator.class);

    when(config.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(userId)).thenThrow(new RuntimeException(new AuthInitializationException("Init error")));

    assertThatThrownBy(() -> gateway.obo(userId))
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  void shouldThrowRuntimeExceptionWhenAuthUnauthorizedForUserId() throws AuthInitializationException, AuthUnauthorizedException {
    Long userId = 123456L;
    OboAuthenticator oboAuthenticator = mock(OboAuthenticator.class);

    when(config.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(userId)).thenThrow(new RuntimeException(new AuthUnauthorizedException("Unauthorized")));

    assertThatThrownBy(() -> gateway.obo(userId))
        .isInstanceOf(RuntimeException.class);
  }
}
