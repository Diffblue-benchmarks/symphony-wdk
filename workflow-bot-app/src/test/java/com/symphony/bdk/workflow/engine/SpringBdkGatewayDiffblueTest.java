package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.auth.exception.AuthInitializationException;
import com.symphony.bdk.core.auth.impl.AuthSessionImpl;
import com.symphony.bdk.core.config.model.BdkAgentConfig;
import com.symphony.bdk.core.config.model.BdkCertificateConfig;
import com.symphony.bdk.core.config.model.BdkClientConfig;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.config.model.BdkRetryConfig;
import com.symphony.bdk.core.config.model.BdkSslConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.message.OboMessageService;
import com.symphony.bdk.core.service.presence.PresenceService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.template.freemarker.FreeMarkerEngine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
   * Test {@link SpringBdkGateway#obo(AuthSession)} with {@code oboSession}.
   * <ul>
   *   <li>Given {@link BdkConfig} {@link BdkConfig#isCommonJwtEnabled()} return
   * {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SpringBdkGateway#obo(AuthSession)}
   */
  @Test
  @DisplayName("Test obo(AuthSession) with 'oboSession'; given BdkConfig isCommonJwtEnabled() return 'false'")
  void testOboWithOboSession_givenBdkConfigIsCommonJwtEnabledReturnFalse() {
    // Arrange
    BdkSslConfig bdkSslConfig = mock(BdkSslConfig.class);
    when(bdkSslConfig.getCertificateConfig()).thenReturn(new BdkCertificateConfig("", "iloveyou"));
    doNothing().when(bdkSslConfig).setTrustStore(Mockito.<BdkCertificateConfig>any());
    doNothing().when(bdkSslConfig).setTrustStorePassword(Mockito.<String>any());
    doNothing().when(bdkSslConfig).setTrustStorePath(Mockito.<String>any());
    bdkSslConfig.setTrustStore(new BdkCertificateConfig("Path", "iloveyou"));
    bdkSslConfig.setTrustStorePassword("iloveyou");
    bdkSslConfig.setTrustStorePath("Trust Store Path");
    when(bdkConfig.isCommonJwtEnabled()).thenReturn(false);
    when(bdkConfig.getRetry()).thenReturn(new BdkRetryConfig());
    when(bdkConfig.getAgent()).thenReturn(new BdkAgentConfig(new BdkConfig()));
    when(bdkConfig.getSsl()).thenReturn(bdkSslConfig);
    when(bdkConfig.getPod()).thenReturn(new BdkClientConfig(new BdkConfig()));

    // Act
    OboServices actualOboResult = springBdkGateway.obo(new AuthSessionImpl(null));

    // Assert
    verify(bdkConfig, atLeast(1)).getAgent();
    verify(bdkConfig).getPod();
    verify(bdkConfig, atLeast(1)).getRetry();
    verify(bdkConfig, atLeast(1)).getSsl();
    verify(bdkConfig).isCommonJwtEnabled();
    verify(bdkSslConfig, atLeast(1)).getCertificateConfig();
    verify(bdkSslConfig).setTrustStore(isA(BdkCertificateConfig.class));
    verify(bdkSslConfig).setTrustStorePassword(eq("iloveyou"));
    verify(bdkSslConfig).setTrustStorePath(eq("Trust Store Path"));
    assertTrue(actualOboResult.connections() instanceof ConnectionService);
    OboMessageService messagesResult = actualOboResult.messages();
    assertTrue(messagesResult instanceof MessageService);
    assertTrue(actualOboResult.presences() instanceof PresenceService);
    assertTrue(actualOboResult.sessions() instanceof SessionService);
    assertTrue(actualOboResult.streams() instanceof StreamService);
    assertTrue(actualOboResult.users() instanceof UserService);
    assertTrue(messagesResult.templates() instanceof FreeMarkerEngine);
  }

  /**
   * Test {@link SpringBdkGateway#obo(AuthSession)} with {@code oboSession}.
   * <ul>
   *   <li>Given {@link BdkConfig} {@link BdkConfig#isOboConfigured()} return
   * {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SpringBdkGateway#obo(AuthSession)}
   */
  @Test
  @DisplayName("Test obo(AuthSession) with 'oboSession'; given BdkConfig isOboConfigured() return 'false'")
  void testOboWithOboSession_givenBdkConfigIsOboConfiguredReturnFalse() {
    // Arrange
    BdkSslConfig bdkSslConfig = mock(BdkSslConfig.class);
    when(bdkSslConfig.getCertificateConfig()).thenReturn(new BdkCertificateConfig("", "iloveyou"));
    doNothing().when(bdkSslConfig).setTrustStore(Mockito.<BdkCertificateConfig>any());
    doNothing().when(bdkSslConfig).setTrustStorePassword(Mockito.<String>any());
    doNothing().when(bdkSslConfig).setTrustStorePath(Mockito.<String>any());
    bdkSslConfig.setTrustStore(new BdkCertificateConfig("Path", "iloveyou"));
    bdkSslConfig.setTrustStorePassword("iloveyou");
    bdkSslConfig.setTrustStorePath("Trust Store Path");
    when(bdkConfig.isOboConfigured()).thenReturn(false);
    when(bdkConfig.isCommonJwtEnabled()).thenReturn(true);
    when(bdkConfig.getRetry()).thenReturn(new BdkRetryConfig());
    when(bdkConfig.getAgent()).thenReturn(new BdkAgentConfig(new BdkConfig()));
    when(bdkConfig.getSsl()).thenReturn(bdkSslConfig);
    when(bdkConfig.getPod()).thenReturn(new BdkClientConfig(new BdkConfig()));

    // Act
    OboServices actualOboResult = springBdkGateway.obo(new AuthSessionImpl(null));

    // Assert
    verify(bdkConfig, atLeast(1)).getAgent();
    verify(bdkConfig).getPod();
    verify(bdkConfig, atLeast(1)).getRetry();
    verify(bdkConfig, atLeast(1)).getSsl();
    verify(bdkConfig).isCommonJwtEnabled();
    verify(bdkConfig).isOboConfigured();
    verify(bdkSslConfig, atLeast(1)).getCertificateConfig();
    verify(bdkSslConfig).setTrustStore(isA(BdkCertificateConfig.class));
    verify(bdkSslConfig).setTrustStorePassword(eq("iloveyou"));
    verify(bdkSslConfig).setTrustStorePath(eq("Trust Store Path"));
    assertTrue(actualOboResult.connections() instanceof ConnectionService);
    OboMessageService messagesResult = actualOboResult.messages();
    assertTrue(messagesResult instanceof MessageService);
    assertTrue(actualOboResult.presences() instanceof PresenceService);
    assertTrue(actualOboResult.sessions() instanceof SessionService);
    assertTrue(actualOboResult.streams() instanceof StreamService);
    assertTrue(actualOboResult.users() instanceof UserService);
    assertTrue(messagesResult.templates() instanceof FreeMarkerEngine);
  }

  /**
   * Test {@link SpringBdkGateway#obo(AuthSession)} with {@code oboSession}.
   * <ul>
   *   <li>Given {@link BdkConfig} {@link BdkConfig#isOboConfigured()} throw
   * {@link RuntimeException#RuntimeException(String)} with {@code /pod}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SpringBdkGateway#obo(AuthSession)}
   */
  @Test
  @DisplayName("Test obo(AuthSession) with 'oboSession'; given BdkConfig isOboConfigured() throw RuntimeException(String) with '/pod'")
  void testOboWithOboSession_givenBdkConfigIsOboConfiguredThrowRuntimeExceptionWithPod() {
    // Arrange
    BdkSslConfig bdkSslConfig = mock(BdkSslConfig.class);
    when(bdkSslConfig.getCertificateConfig()).thenReturn(new BdkCertificateConfig("", "iloveyou"));
    doNothing().when(bdkSslConfig).setTrustStore(Mockito.<BdkCertificateConfig>any());
    doNothing().when(bdkSslConfig).setTrustStorePassword(Mockito.<String>any());
    doNothing().when(bdkSslConfig).setTrustStorePath(Mockito.<String>any());
    bdkSslConfig.setTrustStore(new BdkCertificateConfig("Path", "iloveyou"));
    bdkSslConfig.setTrustStorePassword("iloveyou");
    bdkSslConfig.setTrustStorePath("Trust Store Path");
    when(bdkConfig.isOboConfigured()).thenThrow(new RuntimeException("/pod"));
    when(bdkConfig.isCommonJwtEnabled()).thenReturn(true);
    when(bdkConfig.getRetry()).thenReturn(new BdkRetryConfig());
    when(bdkConfig.getAgent()).thenReturn(new BdkAgentConfig(new BdkConfig()));
    when(bdkConfig.getSsl()).thenReturn(bdkSslConfig);
    when(bdkConfig.getPod()).thenReturn(new BdkClientConfig(new BdkConfig()));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo(new AuthSessionImpl(null)));
    verify(bdkConfig, atLeast(1)).getAgent();
    verify(bdkConfig).getPod();
    verify(bdkConfig).getRetry();
    verify(bdkConfig, atLeast(1)).getSsl();
    verify(bdkConfig).isCommonJwtEnabled();
    verify(bdkConfig).isOboConfigured();
    verify(bdkSslConfig, atLeast(1)).getCertificateConfig();
    verify(bdkSslConfig).setTrustStore(isA(BdkCertificateConfig.class));
    verify(bdkSslConfig).setTrustStorePassword(eq("iloveyou"));
    verify(bdkSslConfig).setTrustStorePath(eq("Trust Store Path"));
  }

  /**
   * Test {@link SpringBdkGateway#obo(Long)} with {@code userId}.
   * <p>
   * Method under test: {@link SpringBdkGateway#obo(Long)}
   */
  @Test
  @DisplayName("Test obo(Long) with 'userId'")
  void testOboWithUserId() throws AuthInitializationException {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenThrow(new AuthInitializationException("An error occurred"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo(1L));
    verify(authenticatorFactory).getOboAuthenticator();
    verify(bdkConfig).isOboConfigured();
  }

  /**
   * Test {@link SpringBdkGateway#obo(Long)} with {@code userId}.
   * <p>
   * Method under test: {@link SpringBdkGateway#obo(Long)}
   */
  @Test
  @DisplayName("Test obo(Long) with 'userId'")
  void testOboWithUserId2() throws AuthInitializationException {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo(1L));
    verify(authenticatorFactory).getOboAuthenticator();
    verify(bdkConfig).isOboConfigured();
  }

  /**
   * Test {@link SpringBdkGateway#obo(Long)} with {@code userId}.
   * <ul>
   *   <li>Given {@link BdkConfig} {@link BdkConfig#isOboConfigured()} return
   * {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SpringBdkGateway#obo(Long)}
   */
  @Test
  @DisplayName("Test obo(Long) with 'userId'; given BdkConfig isOboConfigured() return 'false'")
  void testOboWithUserId_givenBdkConfigIsOboConfiguredReturnFalse() {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenReturn(false);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo(1L));
    verify(bdkConfig).isOboConfigured();
  }

  /**
   * Test {@link SpringBdkGateway#obo(String)} with {@code username}.
   * <p>
   * Method under test: {@link SpringBdkGateway#obo(String)}
   */
  @Test
  @DisplayName("Test obo(String) with 'username'")
  void testOboWithUsername() throws AuthInitializationException {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenThrow(new AuthInitializationException("An error occurred"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo("janedoe"));
    verify(authenticatorFactory).getOboAuthenticator();
    verify(bdkConfig).isOboConfigured();
  }

  /**
   * Test {@link SpringBdkGateway#obo(String)} with {@code username}.
   * <p>
   * Method under test: {@link SpringBdkGateway#obo(String)}
   */
  @Test
  @DisplayName("Test obo(String) with 'username'")
  void testOboWithUsername2() throws AuthInitializationException {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenReturn(true);
    when(authenticatorFactory.getOboAuthenticator()).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo("janedoe"));
    verify(authenticatorFactory).getOboAuthenticator();
    verify(bdkConfig).isOboConfigured();
  }

  /**
   * Test {@link SpringBdkGateway#obo(String)} with {@code username}.
   * <ul>
   *   <li>Given {@link BdkConfig} {@link BdkConfig#isOboConfigured()} return
   * {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SpringBdkGateway#obo(String)}
   */
  @Test
  @DisplayName("Test obo(String) with 'username'; given BdkConfig isOboConfigured() return 'false'")
  void testOboWithUsername_givenBdkConfigIsOboConfiguredReturnFalse() {
    // Arrange
    when(bdkConfig.isOboConfigured()).thenReturn(false);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> springBdkGateway.obo("janedoe"));
    verify(bdkConfig).isOboConfigured();
  }
}
