package com.symphony.bdk.workflow.engine.executor.obo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.auth.impl.AuthSessionImpl;
import com.symphony.bdk.gen.api.model.UserConnection;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.connection.AcceptConnectionExecutor;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.OboActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.AcceptConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AcceptConnectionExecutor.class})
@ExtendWith(SpringExtension.class)
class OboExecutorDiffblueTest {
  @Autowired private OboExecutor<AcceptConnection, UserConnection> oboExecutor;

  /**
   * Test {@link OboExecutor#isObo(OboActivity)}.
   *
   * <ul>
   *   <li>Given {@link Obo} (default constructor) UserId is {@code null}.
   *   <li>When {@link AcceptConnection} (default constructor) Obo is {@link Obo} (default
   *       constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link OboExecutor#isObo(OboActivity)}
   */
  @Test
  @DisplayName(
      "Test isObo(OboActivity); given Obo (default constructor) UserId is 'null'; when AcceptConnection (default constructor) Obo is Obo (default constructor); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean OboExecutor.isObo(OboActivity)"})
  void testIsObo_givenOboUserIdIsNull_whenAcceptConnectionOboIsObo_thenReturnFalse() {
    // Arrange
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(null);

    AcceptConnection acceptConnection = new AcceptConnection();
    acceptConnection.setObo(obo);

    // Act and Assert
    assertFalse(oboExecutor.isObo(acceptConnection));
  }

  /**
   * Test {@link OboExecutor#isObo(OboActivity)}.
   *
   * <ul>
   *   <li>Given {@link Obo} (default constructor) Username is {@code janedoe}.
   *   <li>When {@link AcceptConnection} (default constructor) Obo is {@link Obo} (default
   *       constructor).
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link OboExecutor#isObo(OboActivity)}
   */
  @Test
  @DisplayName(
      "Test isObo(OboActivity); given Obo (default constructor) Username is 'janedoe'; when AcceptConnection (default constructor) Obo is Obo (default constructor); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean OboExecutor.isObo(OboActivity)"})
  void testIsObo_givenOboUsernameIsJanedoe_whenAcceptConnectionOboIsObo_thenReturnTrue() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");

    AcceptConnection acceptConnection = new AcceptConnection();
    acceptConnection.setObo(obo);

    // Act and Assert
    assertTrue(oboExecutor.isObo(acceptConnection));
  }

  /**
   * Test {@link OboExecutor#isObo(OboActivity)}.
   *
   * <ul>
   *   <li>Given {@link Obo} (default constructor) Username is {@code null}.
   *   <li>When {@link AcceptConnection} (default constructor) Obo is {@link Obo} (default
   *       constructor).
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link OboExecutor#isObo(OboActivity)}
   */
  @Test
  @DisplayName(
      "Test isObo(OboActivity); given Obo (default constructor) Username is 'null'; when AcceptConnection (default constructor) Obo is Obo (default constructor); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean OboExecutor.isObo(OboActivity)"})
  void testIsObo_givenOboUsernameIsNull_whenAcceptConnectionOboIsObo_thenReturnTrue() {
    // Arrange
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(1L);

    AcceptConnection acceptConnection = new AcceptConnection();
    acceptConnection.setObo(obo);

    // Act and Assert
    assertTrue(oboExecutor.isObo(acceptConnection));
  }

  /**
   * Test {@link OboExecutor#isObo(OboActivity)}.
   *
   * <ul>
   *   <li>When {@link AcceptConnection} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link OboExecutor#isObo(OboActivity)}
   */
  @Test
  @DisplayName(
      "Test isObo(OboActivity); when AcceptConnection (default constructor); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean OboExecutor.isObo(OboActivity)"})
  void testIsObo_whenAcceptConnection_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(oboExecutor.isObo(new AcceptConnection()));
  }

  /**
   * Test {@link OboExecutor#getOboAuthSession(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then return {@link AuthSessionImpl#AuthSessionImpl(AbstractBotAuthenticator)} with
   *       authenticator is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link OboExecutor#getOboAuthSession(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test getOboAuthSession(ActivityExecutorContext); then return AuthSessionImpl(AbstractBotAuthenticator) with authenticator is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AuthSession OboExecutor.getOboAuthSession(ActivityExecutorContext)"})
  void testGetOboAuthSession_thenReturnAuthSessionImplWithAuthenticatorIsNull() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    AcceptConnectionExecutor acceptConnectionExecutor = new AcceptConnectionExecutor();

    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername(null);

    AcceptConnection acceptConnection = new AcceptConnection();
    acceptConnection.setObo(obo);

    SpringBdkGateway springBdkGateway = mock(SpringBdkGateway.class);
    AuthSessionImpl authSessionImpl = new AuthSessionImpl(null);
    when(springBdkGateway.obo(Mockito.<Long>any())).thenReturn(authSessionImpl);

    ActivityExecutorContext<AcceptConnection> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(acceptConnection);

    // Act
    AuthSession actualOboAuthSession = acceptConnectionExecutor.getOboAuthSession(execution);

    // Assert
    verify(springBdkGateway).obo(1L);
    verify(execution).bdk();
    verify(execution).getActivity();
    assertSame(authSessionImpl, actualOboAuthSession);
  }
}
