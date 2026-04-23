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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpringBdkGatewayTest {

  @Mock
  private BdkConfig config;
  @Mock
  private AuthenticatorFactory authenticatorFactory;
  @Mock
  private MessageService messageService;
  @Mock
  private StreamService streamService;
  @Mock
  private UserService userService;
  @Mock
  private ConnectionService connectionService;
  @Mock
  private SymphonyGroupService groupService;
  @Mock
  private SessionService sessionService;

  private SpringBdkGateway gateway;

  @BeforeEach
  void setUp() {
    gateway = new SpringBdkGateway(config, authenticatorFactory, messageService, streamService,
        userService, connectionService, groupService, sessionService);
  }

  @Test
  void shouldReturnMessageServiceWhenCallingMessages() {
    assertThat(gateway.messages()).isEqualTo(messageService);
  }

  @Test
  void shouldReturnStreamServiceWhenCallingStreams() {
    assertThat(gateway.streams()).isEqualTo(streamService);
  }

  @Test
  void shouldReturnUserServiceWhenCallingUsers() {
    assertThat(gateway.users()).isEqualTo(userService);
  }

  @Test
  void shouldReturnConnectionServiceWhenCallingConnections() {
    assertThat(gateway.connections()).isEqualTo(connectionService);
  }

  @Test
  void shouldReturnSessionServiceWhenCallingSession() {
    assertThat(gateway.session()).isEqualTo(sessionService);
  }

  @Test
  void shouldReturnGroupServiceWhenCallingGroups() {
    assertThat(gateway.groups()).isEqualTo(groupService);
  }

  @Test
  @org.junit.jupiter.api.Disabled("OboServices requires real BdkConfig values to instantiate API clients")
  void shouldReturnOboServicesWhenCallingOboWithAuthSession() {
    AuthSession authSession = mock(AuthSession.class);

    OboServices result = gateway.obo(authSession);

    assertThat(result).isNotNull();
  }

  @Test
  void shouldAuthenticateByUsernameWhenOboConfigured() throws AuthInitializationException, AuthUnauthorizedException {
    OboAuthenticator oboAuthenticator = mock(OboAuthenticator.class);
    AuthSession authSession = mock(AuthSession.class);
    when(config.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername("user1")).thenReturn(authSession);

    AuthSession result = gateway.obo("user1");

    assertThat(result).isEqualTo(authSession);
  }

  @Test
  void shouldThrowRuntimeExceptionWhenOboNotConfiguredForUsername() {
    when(config.isOboConfigured()).thenReturn(false);

    assertThatThrownBy(() -> gateway.obo("user1"))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("At least OBO username or userid should be configured.");
  }

  @Test
  void shouldWrapExceptionWhenAuthUnauthorizedForUsername() throws AuthInitializationException, AuthUnauthorizedException {
    OboAuthenticator oboAuthenticator = mock(OboAuthenticator.class);
    when(config.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUsername("user1")).thenThrow(new AuthUnauthorizedException("unauthorized"));

    assertThatThrownBy(() -> gateway.obo("user1"))
        .isInstanceOf(RuntimeException.class)
        .hasCauseInstanceOf(AuthUnauthorizedException.class);
  }

  @Test
  void shouldAuthenticateByUserIdWhenOboConfigured() throws AuthInitializationException, AuthUnauthorizedException {
    OboAuthenticator oboAuthenticator = mock(OboAuthenticator.class);
    AuthSession authSession = mock(AuthSession.class);
    when(config.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(123L)).thenReturn(authSession);

    AuthSession result = gateway.obo(123L);

    assertThat(result).isEqualTo(authSession);
  }

  @Test
  void shouldThrowRuntimeExceptionWhenOboNotConfiguredForUserId() {
    when(config.isOboConfigured()).thenReturn(false);

    assertThatThrownBy(() -> gateway.obo(123L))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("At least OBO username or userid should be configured.");
  }

  @Test
  void shouldWrapExceptionWhenAuthUnauthorizedForUserId() throws AuthInitializationException, AuthUnauthorizedException {
    OboAuthenticator oboAuthenticator = mock(OboAuthenticator.class);
    when(config.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenReturn(oboAuthenticator);
    when(oboAuthenticator.authenticateByUserId(123L)).thenThrow(new AuthUnauthorizedException("unauthorized"));

    assertThatThrownBy(() -> gateway.obo(123L))
        .isInstanceOf(RuntimeException.class)
        .hasCauseInstanceOf(AuthUnauthorizedException.class);
  }
}
