package com.symphony.bdk.workflow.engine;

import com.symphony.bdk.core.auth.AuthenticatorFactory;
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

class SpringBdkGatewayClaude_connectionsTest {

  private SpringBdkGateway gateway;
  private ConnectionService connectionService;

  @BeforeEach
  void setUp() {
    // Given: A SpringBdkGateway with mocked dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);
  }

  // Tests for connections() method

  @Test
  void connections_shouldReturnNonNullConnectionService() {
    // When: Calling connections()
    ConnectionService result = gateway.connections();

    // Then: The result should not be null
    assertThat(result).isNotNull();
  }

  @Test
  void connections_shouldReturnConnectionService() {
    // When: Calling connections()
    ConnectionService result = gateway.connections();

    // Then: The result should be an instance of ConnectionService
    assertThat(result).isInstanceOf(ConnectionService.class);
  }

  @Test
  void connections_shouldReturnSameInstanceAsConstructorParameter() {
    // When: Calling connections()
    ConnectionService result = gateway.connections();

    // Then: The result should be the same instance that was passed to the constructor
    assertThat(result).isSameAs(connectionService);
  }

  @Test
  void connections_shouldReturnConsistentResult() {
    // When: Calling connections() multiple times
    ConnectionService result1 = gateway.connections();
    ConnectionService result2 = gateway.connections();
    ConnectionService result3 = gateway.connections();

    // Then: All results should be the same instance
    assertThat(result1).isSameAs(result2);
    assertThat(result2).isSameAs(result3);
    assertThat(result1).isSameAs(result3);
  }

  @Test
  void connections_shouldReturnConnectionServiceWithCorrectIdentity() {
    // When: Calling connections()
    ConnectionService result = gateway.connections();

    // Then: The result should have the same identity hash code as the original
    assertThat(System.identityHashCode(result))
        .isEqualTo(System.identityHashCode(connectionService));
  }

  @Test
  void connections_shouldNotReturnNull() {
    // When: Calling connections()
    ConnectionService result = gateway.connections();

    // Then: The result should not be equal to null
    assertThat(result).isNotEqualTo(null);
  }

  @Test
  void connections_shouldReturnSameReferenceOnSubsequentCalls() {
    // When: Calling connections() twice and comparing references
    ConnectionService firstCall = gateway.connections();
    ConnectionService secondCall = gateway.connections();

    // Then: Both calls should return the exact same reference
    assertThat(firstCall == secondCall).isTrue();
  }

  @Test
  void connections_shouldMaintainReferenceAcrossMultipleCalls() {
    // When: Calling connections() multiple times in succession
    for (int i = 0; i < 10; i++) {
      ConnectionService result = gateway.connections();

      // Then: Each call should return the same instance
      assertThat(result).isSameAs(connectionService);
    }
  }

  @Test
  void connections_shouldReturnDifferentInstancesForDifferentGateways() {
    // Given: A second SpringBdkGateway with a different ConnectionService
    ConnectionService connectionService2 = Mockito.mock(ConnectionService.class);
    SpringBdkGateway gateway2 = new SpringBdkGateway(
        new BdkConfig(),
        Mockito.mock(AuthenticatorFactory.class),
        Mockito.mock(MessageService.class),
        Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class),
        connectionService2,
        Mockito.mock(SymphonyGroupService.class),
        Mockito.mock(SessionService.class)
    );

    // When: Calling connections() on both gateways
    ConnectionService result1 = gateway.connections();
    ConnectionService result2 = gateway2.connections();

    // Then: The results should be different instances
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void connections_shouldReturnServiceThatEqualsOriginal() {
    // When: Calling connections()
    ConnectionService result = gateway.connections();

    // Then: The result should be equal to the original service
    assertThat(result).isEqualTo(connectionService);
  }

  @Test
  void connections_shouldReturnServiceWithSameToString() {
    // When: Calling connections() and converting to string
    String resultString = gateway.connections().toString();
    String originalString = connectionService.toString();

    // Then: The toString values should be identical
    assertThat(resultString).isEqualTo(originalString);
  }

  @Test
  void connections_shouldReturnServiceWithSameHashCode() {
    // When: Calling connections() and getting hash code
    int resultHashCode = gateway.connections().hashCode();
    int originalHashCode = connectionService.hashCode();

    // Then: The hash codes should be identical
    assertThat(resultHashCode).isEqualTo(originalHashCode);
  }

  @Test
  void connections_shouldNotModifyInternalState() {
    // Given: Initial call to connections()
    ConnectionService firstResult = gateway.connections();

    // When: Calling connections() again after the first call
    ConnectionService secondResult = gateway.connections();

    // Then: The method should not have modified any internal state
    assertThat(firstResult).isSameAs(secondResult);
    assertThat(gateway.connections()).isSameAs(connectionService);
  }

  @Test
  void connections_shouldReturnExpectedTypeImplementation() {
    // When: Calling connections()
    Object result = gateway.connections();

    // Then: The result should be assignable to ConnectionService interface
    assertThat(ConnectionService.class.isAssignableFrom(result.getClass())).isTrue();
  }

  @Test
  void connections_shouldNotCreateNewInstance() {
    // Given: The original connectionService mock
    ConnectionService original = connectionService;

    // When: Calling connections()
    ConnectionService result = gateway.connections();

    // Then: No new instance should have been created
    assertThat(result).isSameAs(original);
    assertThat(result == original).isTrue();
  }
}
