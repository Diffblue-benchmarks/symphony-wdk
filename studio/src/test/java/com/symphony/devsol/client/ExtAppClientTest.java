package com.symphony.devsol.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.auth.ExtensionAppAuthenticator;
import com.symphony.bdk.core.auth.exception.AuthInitializationException;
import com.symphony.bdk.core.auth.jwt.UserClaim;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExtAppClientTest {

  @Mock
  private ExtensionAppAuthenticator extAppAuth;

  @InjectMocks
  private ExtAppClient underTest;

  @Test
  void shouldReturnUserClaimWhenValidateIsCalledWithJwt() throws AuthInitializationException {
    String jwt = "some.jwt.token";
    UserClaim userClaim = new UserClaim();
    when(extAppAuth.validateJwt(jwt)).thenReturn(userClaim);

    UserClaim result = underTest.validate(jwt);

    assertThat(result, is(userClaim));
  }
}
