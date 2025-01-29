package com.symphony.bdk.workflow.engine.executor.obo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.gen.api.model.UserConnection;
import com.symphony.bdk.workflow.engine.executor.connection.AcceptConnectionExecutor;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.OboActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.AcceptConnection;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.Connection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AcceptConnectionExecutor.class})
@ExtendWith(SpringExtension.class)
class OboExecutorDiffblueTest {
  @Autowired
  private OboExecutor<AcceptConnection, UserConnection> oboExecutor;

  /**
   * Test {@link OboExecutor#isObo(OboActivity)}.
   * <ul>
   *   <li>Given {@link Obo} (default constructor) UserId is {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OboExecutor#isObo(OboActivity)}
   */
  @Test
  @DisplayName("Test isObo(OboActivity); given Obo (default constructor) UserId is 'null'; then return 'false'")
  void testIsObo_givenOboUserIdIsNull_thenReturnFalse() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(null);
    obo.setUsername(null);
    AcceptConnection acceptConnection = mock(AcceptConnection.class);
    when(acceptConnection.getObo()).thenReturn(obo);

    // Act
    boolean actualIsOboResult = oboExecutor.isObo(acceptConnection);

    // Assert
    verify(acceptConnection, atLeast(1)).getObo();
    assertFalse(actualIsOboResult);
  }

  /**
   * Test {@link OboExecutor#isObo(OboActivity)}.
   * <ul>
   *   <li>Given {@link Obo} (default constructor) UserId is one.</li>
   *   <li>When {@link AcceptConnection} {@link Connection#getObo()} return
   * {@link Obo} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OboExecutor#isObo(OboActivity)}
   */
  @Test
  @DisplayName("Test isObo(OboActivity); given Obo (default constructor) UserId is one; when AcceptConnection getObo() return Obo (default constructor); then return 'true'")
  void testIsObo_givenOboUserIdIsOne_whenAcceptConnectionGetOboReturnObo_thenReturnTrue() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername(null);
    AcceptConnection acceptConnection = mock(AcceptConnection.class);
    when(acceptConnection.getObo()).thenReturn(obo);

    // Act
    boolean actualIsOboResult = oboExecutor.isObo(acceptConnection);

    // Assert
    verify(acceptConnection, atLeast(1)).getObo();
    assertTrue(actualIsOboResult);
  }

  /**
   * Test {@link OboExecutor#isObo(OboActivity)}.
   * <ul>
   *   <li>Given {@link Obo} (default constructor) Username is {@code janedoe}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OboExecutor#isObo(OboActivity)}
   */
  @Test
  @DisplayName("Test isObo(OboActivity); given Obo (default constructor) Username is 'janedoe'; then return 'true'")
  void testIsObo_givenOboUsernameIsJanedoe_thenReturnTrue() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");
    AcceptConnection acceptConnection = mock(AcceptConnection.class);
    when(acceptConnection.getObo()).thenReturn(obo);

    // Act
    boolean actualIsOboResult = oboExecutor.isObo(acceptConnection);

    // Assert
    verify(acceptConnection, atLeast(1)).getObo();
    assertTrue(actualIsOboResult);
  }

  /**
   * Test {@link OboExecutor#isObo(OboActivity)}.
   * <ul>
   *   <li>When {@link AcceptConnection} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OboExecutor#isObo(OboActivity)}
   */
  @Test
  @DisplayName("Test isObo(OboActivity); when AcceptConnection (default constructor); then return 'false'")
  void testIsObo_whenAcceptConnection_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(oboExecutor.isObo(new AcceptConnection()));
  }
}
