package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.auth.exception.AuthInitializationException;
import com.symphony.bdk.core.auth.impl.AuthSessionImpl;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SpringBdkGateway.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class SpringBdkGatewayDiffblueTest {
  @MockBean private AuthenticatorFactory authenticatorFactory;

  @MockBean private BdkConfig bdkConfig;

  @MockBean private ConnectionService connectionService;

  @MockBean private MessageService messageService;

  @MockBean private SessionService sessionService;

  @Autowired private SpringBdkGateway springBdkGateway;

  @MockBean private StreamService streamService;

  @MockBean private UserService userService;

  /**
   * Test {@link SpringBdkGateway#obo(AuthSession)} with {@code oboSession}.
   *
   * <ul>
   *   <li>Given {@link BdkConfig} {@link BdkConfig#getPod()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link SpringBdkGateway#obo(AuthSession)}
   */
  @Test
  @DisplayName(
      "Test obo(AuthSession) with 'oboSession'; given BdkConfig getPod() throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"com.symphony.bdk.core.OboServices SpringBdkGateway.obo(AuthSession)"})
  void testOboWithOboSession_givenBdkConfigGetPodThrowRuntimeException() {
    // Arrange
    when(bdkConfig.getPod()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo(new AuthSessionImpl(null)));
    verify(bdkConfig).getPod();
  }

  /**
   * Test {@link SpringBdkGateway#obo(Long)} with {@code userId}.
   *
   * <p>Method under test: {@link SpringBdkGateway#obo(Long)}
   */
  @Test
  @DisplayName("Test obo(Long) with 'userId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AuthSession SpringBdkGateway.obo(Long)"})
  void testOboWithUserId() throws AuthInitializationException {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator())
        .thenThrow(new AuthInitializationException("An error occurred"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo(1L));
    verify(authenticatorFactory).getOboAuthenticator();
    verify(bdkConfig).isOboConfigured();
  }

  /**
   * Test {@link SpringBdkGateway#obo(Long)} with {@code userId}.
   *
   * <p>Method under test: {@link SpringBdkGateway#obo(Long)}
   */
  @Test
  @DisplayName("Test obo(Long) with 'userId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AuthSession SpringBdkGateway.obo(Long)"})
  void testOboWithUserId2() throws AuthInitializationException {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo(1L));
    verify(authenticatorFactory).getOboAuthenticator();
    verify(bdkConfig).isOboConfigured();
  }

  /**
   * Test {@link SpringBdkGateway#obo(Long)} with {@code userId}.
   *
   * <ul>
   *   <li>Given {@link BdkConfig} {@link BdkConfig#isOboConfigured()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link SpringBdkGateway#obo(Long)}
   */
  @Test
  @DisplayName(
      "Test obo(Long) with 'userId'; given BdkConfig isOboConfigured() throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AuthSession SpringBdkGateway.obo(Long)"})
  void testOboWithUserId_givenBdkConfigIsOboConfiguredThrowRuntimeException() {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo(1L));
    verify(bdkConfig).isOboConfigured();
  }

  /**
   * Test {@link SpringBdkGateway#obo(String)} with {@code username}.
   *
   * <p>Method under test: {@link SpringBdkGateway#obo(String)}
   */
  @Test
  @DisplayName("Test obo(String) with 'username'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AuthSession SpringBdkGateway.obo(String)"})
  void testOboWithUsername() throws AuthInitializationException {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator())
        .thenThrow(new AuthInitializationException("An error occurred"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo("janedoe"));
    verify(authenticatorFactory).getOboAuthenticator();
    verify(bdkConfig).isOboConfigured();
  }

  /**
   * Test {@link SpringBdkGateway#obo(String)} with {@code username}.
   *
   * <p>Method under test: {@link SpringBdkGateway#obo(String)}
   */
  @Test
  @DisplayName("Test obo(String) with 'username'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AuthSession SpringBdkGateway.obo(String)"})
  void testOboWithUsername2() throws AuthInitializationException {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo("janedoe"));
    verify(authenticatorFactory).getOboAuthenticator();
    verify(bdkConfig).isOboConfigured();
  }

  /**
   * Test {@link SpringBdkGateway#obo(String)} with {@code username}.
   *
   * <ul>
   *   <li>Given {@link BdkConfig} {@link BdkConfig#isOboConfigured()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link SpringBdkGateway#obo(String)}
   */
  @Test
  @DisplayName(
      "Test obo(String) with 'username'; given BdkConfig isOboConfigured() throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AuthSession SpringBdkGateway.obo(String)"})
  void testOboWithUsername_givenBdkConfigIsOboConfiguredThrowRuntimeException() {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo("janedoe"));
    verify(bdkConfig).isOboConfigured();
  }

  /**
   * Test {@link SpringBdkGateway#groups()}.
   *
   * <p>Method under test: {@link SpringBdkGateway#groups()}
   */
  @Test
  @DisplayName("Test groups()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"SymphonyGroupService SpringBdkGateway.groups()"})
  void testGroups() {
    // Arrange, Act and Assert
    assertTrue(springBdkGateway.groups() instanceof SymphonyGroupService);
  }
}
