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

class SpringBdkGatewayClaude_sessionTest {

  private SpringBdkGateway gateway;
  private SessionService sessionService;

  @BeforeEach
  void setUp() {
    // Given: A SpringBdkGateway with mocked dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    sessionService = Mockito.mock(SessionService.class);

    gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);
  }

  // Tests for session() method

  @Test
  void session_shouldReturnNonNullSessionService() {
    // When: Calling session()
    SessionService result = gateway.session();

    // Then: The result should not be null
    assertThat(result).isNotNull();
  }

  @Test
  void session_shouldReturnSessionService() {
    // When: Calling session()
    SessionService result = gateway.session();

    // Then: The result should be an instance of SessionService
    assertThat(result).isInstanceOf(SessionService.class);
  }

  @Test
  void session_shouldReturnSameInstanceAsConstructorParameter() {
    // When: Calling session()
    SessionService result = gateway.session();

    // Then: The result should be the same instance that was passed to the constructor
    assertThat(result).isSameAs(sessionService);
  }

  @Test
  void session_shouldReturnConsistentResult() {
    // When: Calling session() multiple times
    SessionService result1 = gateway.session();
    SessionService result2 = gateway.session();
    SessionService result3 = gateway.session();

    // Then: All results should be the same instance
    assertThat(result1).isSameAs(result2);
    assertThat(result2).isSameAs(result3);
    assertThat(result1).isSameAs(result3);
  }

  @Test
  void session_shouldReturnSessionServiceWithCorrectIdentity() {
    // When: Calling session()
    SessionService result = gateway.session();

    // Then: The result should have the same identity hash code as the original
    assertThat(System.identityHashCode(result))
        .isEqualTo(System.identityHashCode(sessionService));
  }

  @Test
  void session_shouldNotReturnNull() {
    // When: Calling session()
    SessionService result = gateway.session();

    // Then: The result should not be equal to null
    assertThat(result).isNotEqualTo(null);
  }

  @Test
  void session_shouldReturnSameReferenceOnSubsequentCalls() {
    // When: Calling session() twice and comparing references
    SessionService firstCall = gateway.session();
    SessionService secondCall = gateway.session();

    // Then: Both calls should return the exact same reference
    assertThat(firstCall == secondCall).isTrue();
  }

  @Test
  void session_shouldMaintainReferenceAcrossMultipleCalls() {
    // When: Calling session() multiple times in succession
    for (int i = 0; i < 10; i++) {
      SessionService result = gateway.session();

      // Then: Each call should return the same instance
      assertThat(result).isSameAs(sessionService);
    }
  }

  @Test
  void session_shouldReturnDifferentInstancesForDifferentGateways() {
    // Given: A second SpringBdkGateway with a different SessionService
    SessionService sessionService2 = Mockito.mock(SessionService.class);
    SpringBdkGateway gateway2 = new SpringBdkGateway(
        new BdkConfig(),
        Mockito.mock(AuthenticatorFactory.class),
        Mockito.mock(MessageService.class),
        Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class),
        Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class),
        sessionService2
    );

    // When: Calling session() on both gateways
    SessionService result1 = gateway.session();
    SessionService result2 = gateway2.session();

    // Then: The results should be different instances
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void session_shouldReturnServiceThatEqualsOriginal() {
    // When: Calling session()
    SessionService result = gateway.session();

    // Then: The result should be equal to the original service
    assertThat(result).isEqualTo(sessionService);
  }

  @Test
  void session_shouldReturnServiceWithSameToString() {
    // When: Calling session() and converting to string
    String resultString = gateway.session().toString();
    String originalString = sessionService.toString();

    // Then: The toString values should be identical
    assertThat(resultString).isEqualTo(originalString);
  }

  @Test
  void session_shouldReturnServiceWithSameHashCode() {
    // When: Calling session() and getting hash code
    int resultHashCode = gateway.session().hashCode();
    int originalHashCode = sessionService.hashCode();

    // Then: The hash codes should be identical
    assertThat(resultHashCode).isEqualTo(originalHashCode);
  }

  @Test
  void session_shouldNotModifyInternalState() {
    // Given: Initial call to session()
    SessionService firstResult = gateway.session();

    // When: Calling session() again after the first call
    SessionService secondResult = gateway.session();

    // Then: The method should not have modified any internal state
    assertThat(firstResult).isSameAs(secondResult);
    assertThat(gateway.session()).isSameAs(sessionService);
  }

  @Test
  void session_shouldReturnExpectedTypeImplementation() {
    // When: Calling session()
    Object result = gateway.session();

    // Then: The result should be assignable to SessionService interface
    assertThat(SessionService.class.isAssignableFrom(result.getClass())).isTrue();
  }

  @Test
  void session_shouldNotCreateNewInstance() {
    // Given: The original sessionService mock
    SessionService original = sessionService;

    // When: Calling session()
    SessionService result = gateway.session();

    // Then: No new instance should have been created
    assertThat(result).isSameAs(original);
    assertThat(result == original).isTrue();
  }
}
