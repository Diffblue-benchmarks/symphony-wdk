package com.symphony.devsol.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.auth.ExtensionAppAuthenticator;
import com.symphony.bdk.core.auth.exception.AuthInitializationException;
import com.symphony.bdk.core.auth.jwt.UserClaim;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExtAppClientTest {

  private ExtAppClient client;
  private ExtensionAppAuthenticator extAppAuth;

  @BeforeEach
  void setUp() {
    extAppAuth = mock(ExtensionAppAuthenticator.class);
    client = new ExtAppClient(extAppAuth);
  }

  @Test
  void shouldValidateJwtSuccessfully() throws AuthInitializationException {
    String jwt = "valid.jwt.token";
    UserClaim expectedClaim = mock(UserClaim.class);

    when(extAppAuth.validateJwt(jwt)).thenReturn(expectedClaim);

    UserClaim result = client.validate(jwt);

    assertThat(result, is(expectedClaim));
    verify(extAppAuth).validateJwt(jwt);
  }

  @Test
  void shouldThrowAuthInitializationExceptionWhenValidationFails() throws AuthInitializationException {
    String jwt = "invalid.jwt.token";
    AuthInitializationException exception = new AuthInitializationException("Invalid JWT");

    when(extAppAuth.validateJwt(jwt)).thenThrow(exception);

    AuthInitializationException thrown = assertThrows(AuthInitializationException.class, () -> {
      client.validate(jwt);
    });

    assertThat(thrown.getMessage(), is("Invalid JWT"));
  }
}
