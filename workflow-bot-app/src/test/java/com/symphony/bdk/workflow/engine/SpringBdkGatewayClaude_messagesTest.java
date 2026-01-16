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

class SpringBdkGatewayClaude_messagesTest {

  private SpringBdkGateway gateway;
  private MessageService messageService;

  @BeforeEach
  void setUp() {
    // Given: A SpringBdkGateway with mocked dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);
  }

  // Tests for messages() method

  @Test
  void messages_shouldReturnNonNullMessageService() {
    // When: Calling messages()
    MessageService result = gateway.messages();

    // Then: The result should not be null
    assertThat(result).isNotNull();
  }

  @Test
  void messages_shouldReturnMessageService() {
    // When: Calling messages()
    MessageService result = gateway.messages();

    // Then: The result should be an instance of MessageService
    assertThat(result).isInstanceOf(MessageService.class);
  }

  @Test
  void messages_shouldReturnSameInstanceAsConstructorParameter() {
    // When: Calling messages()
    MessageService result = gateway.messages();

    // Then: The result should be the same instance that was passed to the constructor
    assertThat(result).isSameAs(messageService);
  }

  @Test
  void messages_shouldReturnConsistentResult() {
    // When: Calling messages() multiple times
    MessageService result1 = gateway.messages();
    MessageService result2 = gateway.messages();
    MessageService result3 = gateway.messages();

    // Then: All results should be the same instance
    assertThat(result1).isSameAs(result2);
    assertThat(result2).isSameAs(result3);
    assertThat(result1).isSameAs(result3);
  }

  @Test
  void messages_shouldReturnMessageServiceWithCorrectIdentity() {
    // When: Calling messages()
    MessageService result = gateway.messages();

    // Then: The result should have the same identity hash code as the original
    assertThat(System.identityHashCode(result))
        .isEqualTo(System.identityHashCode(messageService));
  }

  @Test
  void messages_shouldNotReturnNull() {
    // When: Calling messages()
    MessageService result = gateway.messages();

    // Then: The result should not be equal to null
    assertThat(result).isNotEqualTo(null);
  }

  @Test
  void messages_shouldReturnSameReferenceOnSubsequentCalls() {
    // When: Calling messages() twice and comparing references
    MessageService firstCall = gateway.messages();
    MessageService secondCall = gateway.messages();

    // Then: Both calls should return the exact same reference
    assertThat(firstCall == secondCall).isTrue();
  }

  @Test
  void messages_shouldMaintainReferenceAcrossMultipleCalls() {
    // When: Calling messages() multiple times in succession
    for (int i = 0; i < 10; i++) {
      MessageService result = gateway.messages();

      // Then: Each call should return the same instance
      assertThat(result).isSameAs(messageService);
    }
  }

  @Test
  void messages_shouldReturnDifferentInstancesForDifferentGateways() {
    // Given: A second SpringBdkGateway with a different MessageService
    MessageService messageService2 = Mockito.mock(MessageService.class);
    SpringBdkGateway gateway2 = new SpringBdkGateway(
        new BdkConfig(),
        Mockito.mock(AuthenticatorFactory.class),
        messageService2,
        Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class),
        Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class),
        Mockito.mock(SessionService.class)
    );

    // When: Calling messages() on both gateways
    MessageService result1 = gateway.messages();
    MessageService result2 = gateway2.messages();

    // Then: The results should be different instances
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void messages_shouldReturnServiceThatEqualsOriginal() {
    // When: Calling messages()
    MessageService result = gateway.messages();

    // Then: The result should be equal to the original service
    assertThat(result).isEqualTo(messageService);
  }

  @Test
  void messages_shouldReturnServiceWithSameToString() {
    // When: Calling messages() and converting to string
    String resultString = gateway.messages().toString();
    String originalString = messageService.toString();

    // Then: The toString values should be identical
    assertThat(resultString).isEqualTo(originalString);
  }

  @Test
  void messages_shouldReturnServiceWithSameHashCode() {
    // When: Calling messages() and getting hash code
    int resultHashCode = gateway.messages().hashCode();
    int originalHashCode = messageService.hashCode();

    // Then: The hash codes should be identical
    assertThat(resultHashCode).isEqualTo(originalHashCode);
  }

  @Test
  void messages_shouldNotModifyInternalState() {
    // Given: Initial call to messages()
    MessageService firstResult = gateway.messages();

    // When: Calling messages() again after the first call
    MessageService secondResult = gateway.messages();

    // Then: The method should not have modified any internal state
    assertThat(firstResult).isSameAs(secondResult);
    assertThat(gateway.messages()).isSameAs(messageService);
  }

  @Test
  void messages_shouldReturnExpectedTypeImplementation() {
    // When: Calling messages()
    Object result = gateway.messages();

    // Then: The result should be assignable to MessageService interface
    assertThat(MessageService.class.isAssignableFrom(result.getClass())).isTrue();
  }

  @Test
  void messages_shouldNotCreateNewInstance() {
    // Given: The original messageService mock
    MessageService original = messageService;

    // When: Calling messages()
    MessageService result = gateway.messages();

    // Then: No new instance should have been created
    assertThat(result).isSameAs(original);
    assertThat(result == original).isTrue();
  }
}
