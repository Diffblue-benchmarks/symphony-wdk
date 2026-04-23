package com.symphony.devsol.config;

import com.symphony.bdk.core.auth.jwt.UserClaim;
import com.symphony.devsol.client.ExtAppClient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthFilterTest {

  @Mock
  private ExtAppClient extAppClient;

  @InjectMocks
  private AuthFilter authFilter;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(authFilter, "managementToken", "secret-token");
  }

  @Test
  void shouldPassThroughWhenOptionsRequest() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest("OPTIONS", "/v1/workflows");
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockFilterChain chain = new MockFilterChain();

    authFilter.doFilterInternal(request, response, chain);

    assertThat(chain.getRequest(), notNullValue());
    assertThat(request.getAttribute("user"), nullValue());
  }

  @Test
  void shouldSetMgmtTokenUserWhenManagementTokenMatches() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/workflows");
    request.addHeader("X-Management-Token", "secret-token");
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockFilterChain chain = new MockFilterChain();

    authFilter.doFilterInternal(request, response, chain);

    UserClaim user = (UserClaim) request.getAttribute("user");
    assertThat(user, notNullValue());
    assertThat(user.getUsername(), is("mgmt-token"));
    assertThat(chain.getRequest(), notNullValue());
  }

  @Test
  void shouldSetWebhookUserWhenUriMatchesExecPattern() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest("POST", "/v1/workflows/my-workflow/execute");
    request.addHeader("X-Management-Token", "wrong-token");
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockFilterChain chain = new MockFilterChain();

    authFilter.doFilterInternal(request, response, chain);

    UserClaim user = (UserClaim) request.getAttribute("user");
    assertThat(user, notNullValue());
    assertThat(user.getUsername(), is("webhook"));
    assertThat(chain.getRequest(), notNullValue());
  }

  @Test
  void shouldValidateJwtAndSetUserWhenAuthHeaderProvided() throws Exception {
    UserClaim userClaim = new UserClaim();
    userClaim.setUsername("john.doe");
    when(extAppClient.validate("valid-jwt")).thenReturn(userClaim);

    MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/some/path");
    request.addHeader("Authorization", "Bearer valid-jwt");
    request.addHeader("X-Management-Token", "wrong-token");
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockFilterChain chain = new MockFilterChain();

    authFilter.doFilterInternal(request, response, chain);

    verify(extAppClient).validate("valid-jwt");
    UserClaim user = (UserClaim) request.getAttribute("user");
    assertThat(user, notNullValue());
    assertThat(user.getUsername(), is("john.doe"));
    assertThat(chain.getRequest(), notNullValue());
  }

  @Test
  void shouldReturnUnauthorizedWhenJwtValidationFails() throws Exception {
    when(extAppClient.validate("bad-jwt")).thenThrow(new RuntimeException("invalid"));

    MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/some/path");
    request.addHeader("Authorization", "Bearer bad-jwt");
    request.addHeader("X-Management-Token", "wrong-token");
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockFilterChain chain = new MockFilterChain();

    authFilter.doFilterInternal(request, response, chain);

    assertThat(response.getStatus(), is(SC_UNAUTHORIZED));
    assertThat(chain.getRequest(), nullValue());
  }

  @Test
  void shouldNotSetUserAttributeWhenUsernameIsNull() throws Exception {
    UserClaim userClaim = new UserClaim();
    when(extAppClient.validate("valid-jwt")).thenReturn(userClaim);

    MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/some/path");
    request.addHeader("Authorization", "Bearer valid-jwt");
    request.addHeader("X-Management-Token", "wrong-token");
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockFilterChain chain = new MockFilterChain();

    authFilter.doFilterInternal(request, response, chain);

    assertThat(request.getAttribute("user"), nullValue());
    assertThat(chain.getRequest(), notNullValue());
  }
}
