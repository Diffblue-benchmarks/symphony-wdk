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
   * Method under test: {@link OboExecutor#isObo(OboActivity)}
   */
  @Test
  void testIsObo() {
    // Arrange, Act and Assert
    assertFalse(oboExecutor.isObo(new AcceptConnection()));
  }

  /**
   * Method under test: {@link OboExecutor#isObo(OboActivity)}
   */
  @Test
  void testIsObo2() {
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
   * Method under test: {@link OboExecutor#isObo(OboActivity)}
   */
  @Test
  void testIsObo3() {
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
   * Method under test: {@link OboExecutor#isObo(OboActivity)}
   */
  @Test
  void testIsObo4() {
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
}
