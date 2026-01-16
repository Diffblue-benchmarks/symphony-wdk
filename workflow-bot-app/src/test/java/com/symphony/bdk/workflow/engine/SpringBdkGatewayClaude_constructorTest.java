package com.symphony.bdk.workflow.engine;

import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class SpringBdkGatewayClaude_constructorTest {

  // Tests for <init>(BdkConfig, AuthenticatorFactory, MessageService, StreamService, UserService, ConnectionService, SymphonyGroupService, SessionService) constructor

  @Test
  void constructor_shouldCreateNonNullInstance() {
    // Given: All required dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    // When: Creating a new SpringBdkGateway instance
    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);

    // Then: The instance should not be null
    assertThat(gateway).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfSpringBdkGateway() {
    // Given: All required dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    // When: Creating a new SpringBdkGateway instance
    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);

    // Then: The instance should be of type SpringBdkGateway
    assertThat(gateway).isInstanceOf(SpringBdkGateway.class);
  }

  @Test
  void constructor_shouldCreateInstanceOfBdkGateway() {
    // Given: All required dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    // When: Creating a new SpringBdkGateway instance
    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);

    // Then: The instance should implement BdkGateway interface
    assertThat(gateway).isInstanceOf(BdkGateway.class);
  }

  @Test
  void constructor_shouldInitializeMessageService() {
    // Given: All required dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    // When: Creating a new SpringBdkGateway instance
    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);

    // Then: The messages() method should return the provided MessageService
    assertThat(gateway.messages()).isSameAs(messageService);
  }

  @Test
  void constructor_shouldInitializeStreamService() {
    // Given: All required dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    // When: Creating a new SpringBdkGateway instance
    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);

    // Then: The streams() method should return the provided StreamService
    assertThat(gateway.streams()).isSameAs(streamService);
  }

  @Test
  void constructor_shouldInitializeUserService() {
    // Given: All required dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    // When: Creating a new SpringBdkGateway instance
    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);

    // Then: The users() method should return the provided UserService
    assertThat(gateway.users()).isSameAs(userService);
  }

  @Test
  void constructor_shouldInitializeConnectionService() {
    // Given: All required dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    // When: Creating a new SpringBdkGateway instance
    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);

    // Then: The connections() method should return the provided ConnectionService
    assertThat(gateway.connections()).isSameAs(connectionService);
  }

  @Test
  void constructor_shouldInitializeGroupService() {
    // Given: All required dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    // When: Creating a new SpringBdkGateway instance
    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);

    // Then: The groups() method should return the provided SymphonyGroupService
    assertThat(gateway.groups()).isSameAs(groupService);
  }

  @Test
  void constructor_shouldInitializeSessionService() {
    // Given: All required dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    // When: Creating a new SpringBdkGateway instance
    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);

    // Then: The session() method should return the provided SessionService
    assertThat(gateway.session()).isSameAs(sessionService);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // Given: All required dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    // When: Creating two SpringBdkGateway instances
    SpringBdkGateway gateway1 = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);
    SpringBdkGateway gateway2 = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);

    // Then: They should be different instances
    assertThat(gateway1).isNotSameAs(gateway2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // Given: All required dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    // When/Then: Creating a new instance should not throw any exception
    assertThatCode(() -> new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService))
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_shouldAcceptNullAuthenticatorFactory() {
    // Given: All required dependencies except AuthenticatorFactory is null
    BdkConfig config = new BdkConfig();
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    // When: Creating a new SpringBdkGateway instance with null AuthenticatorFactory
    SpringBdkGateway gateway = new SpringBdkGateway(config, null,
        messageService, streamService, userService, connectionService, groupService, sessionService);

    // Then: The instance should be created successfully
    assertThat(gateway).isNotNull();
  }

  @Test
  void constructor_shouldPreserveServiceReferences() {
    // Given: All required dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    // When: Creating a new SpringBdkGateway instance
    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);

    // Then: All services should be accessible and maintain their references
    assertThat(gateway.messages()).isSameAs(messageService);
    assertThat(gateway.streams()).isSameAs(streamService);
    assertThat(gateway.users()).isSameAs(userService);
    assertThat(gateway.connections()).isSameAs(connectionService);
    assertThat(gateway.groups()).isSameAs(groupService);
    assertThat(gateway.session()).isSameAs(sessionService);
  }

  @Test
  void constructor_shouldCreateInstanceWithNonNullClassName() {
    // Given: All required dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    // When: Creating a new SpringBdkGateway instance
    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);

    // Then: The class name should be correct
    assertThat(gateway.getClass().getName())
        .isEqualTo("com.symphony.bdk.workflow.engine.SpringBdkGateway");
  }

  @Test
  void constructor_shouldCreateInstanceWithSimpleClassName() {
    // Given: All required dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    // When: Creating a new SpringBdkGateway instance
    SpringBdkGateway gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);

    // Then: The simple class name should be "SpringBdkGateway"
    assertThat(gateway.getClass().getSimpleName())
        .isEqualTo("SpringBdkGateway");
  }

  @Test
  void constructor_shouldAllowMultipleInstantiationsWithDifferentServices() {
    // Given: Different sets of dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);

    MessageService messageService1 = Mockito.mock(MessageService.class);
    StreamService streamService1 = Mockito.mock(StreamService.class);
    UserService userService1 = Mockito.mock(UserService.class);
    ConnectionService connectionService1 = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService1 = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService1 = Mockito.mock(SessionService.class);

    MessageService messageService2 = Mockito.mock(MessageService.class);
    StreamService streamService2 = Mockito.mock(StreamService.class);
    UserService userService2 = Mockito.mock(UserService.class);
    ConnectionService connectionService2 = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService2 = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService2 = Mockito.mock(SessionService.class);

    // When: Creating two SpringBdkGateway instances with different services
    SpringBdkGateway gateway1 = new SpringBdkGateway(config, authenticatorFactory,
        messageService1, streamService1, userService1, connectionService1, groupService1, sessionService1);
    SpringBdkGateway gateway2 = new SpringBdkGateway(config, authenticatorFactory,
        messageService2, streamService2, userService2, connectionService2, groupService2, sessionService2);

    // Then: Each gateway should have its own service references
    assertThat(gateway1.messages()).isSameAs(messageService1);
    assertThat(gateway2.messages()).isSameAs(messageService2);
    assertThat(gateway1.messages()).isNotSameAs(gateway2.messages());
  }
}
