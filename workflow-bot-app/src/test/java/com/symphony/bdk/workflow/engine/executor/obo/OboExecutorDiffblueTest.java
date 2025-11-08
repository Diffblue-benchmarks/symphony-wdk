package com.symphony.bdk.workflow.engine.executor.obo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.gen.api.model.UserConnection;
import com.symphony.bdk.workflow.engine.executor.connection.AcceptConnectionExecutor;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.OboActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.AcceptConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
   *   <li>When {@link AcceptConnection} (default constructor) Obo is {@link Obo} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OboExecutor#isObo(OboActivity)}
   */
  @Test
  @DisplayName("Test isObo(OboActivity); given Obo (default constructor) UserId is 'null'; when AcceptConnection (default constructor) Obo is Obo (default constructor); then return 'false'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link Obo} (default constructor) Username is {@code janedoe}.</li>
   *   <li>When {@link AcceptConnection} (default constructor) Obo is {@link Obo} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OboExecutor#isObo(OboActivity)}
   */
  @Test
  @DisplayName("Test isObo(OboActivity); given Obo (default constructor) Username is 'janedoe'; when AcceptConnection (default constructor) Obo is Obo (default constructor); then return 'true'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link Obo} (default constructor) Username is {@code null}.</li>
   *   <li>When {@link AcceptConnection} (default constructor) Obo is {@link Obo} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OboExecutor#isObo(OboActivity)}
   */
  @Test
  @DisplayName("Test isObo(OboActivity); given Obo (default constructor) Username is 'null'; when AcceptConnection (default constructor) Obo is Obo (default constructor); then return 'true'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>When {@link AcceptConnection} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link OboExecutor#isObo(OboActivity)}
   */
  @Test
  @DisplayName("Test isObo(OboActivity); when AcceptConnection (default constructor); then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean OboExecutor.isObo(OboActivity)"})
  void testIsObo_whenAcceptConnection_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(oboExecutor.isObo(new AcceptConnection()));
  }
}
