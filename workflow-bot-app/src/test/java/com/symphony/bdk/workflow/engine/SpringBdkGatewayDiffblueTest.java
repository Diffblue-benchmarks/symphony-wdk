package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.auth.exception.AuthInitializationException;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SpringBdkGateway.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SpringBdkGatewayDiffblueTest {
  @MockBean
  private AuthenticatorFactory authenticatorFactory;

  @MockBean
  private BdkConfig bdkConfig;

  @MockBean
  private ConnectionService connectionService;

  @MockBean
  private MessageService messageService;

  @MockBean
  private SessionService sessionService;

  @Autowired
  private SpringBdkGateway springBdkGateway;

  @MockBean
  private StreamService streamService;

  @MockBean
  private SymphonyGroupService symphonyGroupService;

  @MockBean
  private UserService userService;

  /**
   * Method under test: {@link SpringBdkGateway#obo(Long)}
   */
  @Test
  void testObo() throws AuthInitializationException {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenThrow(new AuthInitializationException("An error occurred"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo(1L));
    verify(authenticatorFactory).getOboAuthenticator();
    verify(bdkConfig).isOboConfigured();
  }

  /**
   * Method under test: {@link SpringBdkGateway#obo(Long)}
   */
  @Test
  void testObo2() throws AuthInitializationException {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo(1L));
    verify(authenticatorFactory).getOboAuthenticator();
    verify(bdkConfig).isOboConfigured();
  }

  /**
   * Method under test: {@link SpringBdkGateway#obo(Long)}
   */
  @Test
  void testObo3() {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenReturn(false);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo(1L));
    verify(bdkConfig).isOboConfigured();
  }

  /**
   * Method under test: {@link SpringBdkGateway#obo(String)}
   */
  @Test
  void testObo4() throws AuthInitializationException {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenThrow(new AuthInitializationException("An error occurred"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo("janedoe"));
    verify(authenticatorFactory).getOboAuthenticator();
    verify(bdkConfig).isOboConfigured();
  }

  /**
   * Method under test: {@link SpringBdkGateway#obo(String)}
   */
  @Test
  void testObo5() throws AuthInitializationException {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo("janedoe"));
    verify(authenticatorFactory).getOboAuthenticator();
    verify(bdkConfig).isOboConfigured();
  }

  /**
   * Method under test: {@link SpringBdkGateway#obo(String)}
   */
  @Test
  void testObo6() {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenReturn(false);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo("janedoe"));
    verify(bdkConfig).isOboConfigured();
  }
}
