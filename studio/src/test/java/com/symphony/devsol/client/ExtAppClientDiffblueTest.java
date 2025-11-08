package com.symphony.devsol.client;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.auth.ExtensionAppAuthenticator;
import com.symphony.bdk.core.auth.exception.AuthInitializationException;
import com.symphony.bdk.core.auth.jwt.UserClaim;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ExtAppClient.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ExtAppClientDiffblueTest {
  @Autowired
  private ExtAppClient extAppClient;

  @MockBean
  private ExtensionAppAuthenticator extensionAppAuthenticator;

  /**
   * Test {@link ExtAppClient#validate(String)}.
   * <ul>
   *   <li>Then return {@link UserClaim} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtAppClient#validate(String)}
   */
  @Test
  @DisplayName("Test validate(String); then return UserClaim (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"UserClaim ExtAppClient.validate(String)"})
  void testValidate_thenReturnUserClaim() throws AuthInitializationException {
    // Arrange
    UserClaim userClaim = new UserClaim();
    userClaim.setAvatarSmallUrl("https://example.org/example");
    userClaim.setAvatarUrl("https://example.org/example");
    userClaim.setCompany("Company");
    userClaim.setCompanyId("42");
    userClaim.setDisplayName("Display Name");
    userClaim.setEmailAddress("42 Main St");
    userClaim.setFirstName("Jane");
    userClaim.setId(1L);
    userClaim.setLastName("Doe");
    userClaim.setLocation("Location");
    userClaim.setTitle("Dr");
    userClaim.setUsername("janedoe");
    when(extensionAppAuthenticator.validateJwt(Mockito.<String>any())).thenReturn(userClaim);

    // Act
    UserClaim actualValidateResult = extAppClient.validate("Jwt");

    // Assert
    verify(extensionAppAuthenticator).validateJwt(eq("Jwt"));
    assertSame(userClaim, actualValidateResult);
  }

  /**
   * Test {@link ExtAppClient#validate(String)}.
   * <ul>
   *   <li>Then throw {@link AuthInitializationException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtAppClient#validate(String)}
   */
  @Test
  @DisplayName("Test validate(String); then throw AuthInitializationException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"UserClaim ExtAppClient.validate(String)"})
  void testValidate_thenThrowAuthInitializationException() throws AuthInitializationException {
    // Arrange
    when(extensionAppAuthenticator.validateJwt(Mockito.<String>any()))
        .thenThrow(new AuthInitializationException("An error occurred"));

    // Act and Assert
    assertThrows(AuthInitializationException.class, () -> extAppClient.validate("Jwt"));
    verify(extensionAppAuthenticator).validateJwt(eq("Jwt"));
  }
}
