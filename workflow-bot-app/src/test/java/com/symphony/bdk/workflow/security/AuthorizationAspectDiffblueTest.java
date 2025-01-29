package com.symphony.bdk.workflow.security;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.configuration.WorkflowBotConfiguration;
import com.symphony.bdk.workflow.exception.UnauthorizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthorizationAspect.class, WorkflowBotConfiguration.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AuthorizationAspectDiffblueTest {
  @Autowired
  private AuthorizationAspect authorizationAspect;

  @MockBean
  private Authorized authorized;

  /**
   * Test {@link AuthorizationAspect#authorizationCheck(Authorized)}.
   * <ul>
   *   <li>Then throw {@link UnauthorizedException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AuthorizationAspect#authorizationCheck(Authorized)}
   */
  @Test
  @DisplayName("Test authorizationCheck(Authorized); then throw UnauthorizedException")
  void testAuthorizationCheck_thenThrowUnauthorizedException() {
    // Arrange
    when(authorized.headerTokenKey()).thenThrow(new UnauthorizedException("An error occurred"));

    // Act and Assert
    assertThrows(UnauthorizedException.class, () -> authorizationAspect.authorizationCheck(authorized));
    verify(authorized).headerTokenKey();
  }
}
