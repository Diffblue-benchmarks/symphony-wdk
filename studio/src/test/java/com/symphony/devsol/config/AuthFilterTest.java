package com.symphony.devsol.config;

import com.symphony.bdk.core.auth.jwt.UserClaim;
import com.symphony.devsol.client.ExtAppClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthFilterTest {

  @Mock
  private ExtAppClient extAppClient;

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private FilterChain filterChain;

  private AuthFilter authFilter;

  private static final String MANAGEMENT_TOKEN = "test-mgmt-token";

  @BeforeEach
  void setUp() {
    authFilter = new AuthFilter(extAppClient);
    ReflectionTestUtils.setField(authFilter, "managementToken", MANAGEMENT_TOKEN);
  }

  @Test
  void shouldSkipAuthenticationWhenRequestMethodIsOptions() throws Exception {
    when(request.getMethod()).thenReturn("OPTIONS");

    authFilter.doFilterInternal(request, response, filterChain);

    verify(request, never()).getHeader("Authorization");
    verify(request, never()).getHeader("X-Management-Token");
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void shouldSetMgmtTokenUsernameWhenManagementTokenHeaderMatches() throws Exception {
    when(request.getMethod()).thenReturn("POST");
    when(request.getHeader("X-Management-Token")).thenReturn(MANAGEMENT_TOKEN);
    when(request.getHeader("Authorization")).thenReturn(null);

    authFilter.doFilterInternal(request, response, filterChain);

    verify(request).setAttribute(eq("user"), any(UserClaim.class));
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void shouldSetWebhookUsernameWhenRequestUriMatchesExecPattern() throws Exception {
    when(request.getMethod()).thenReturn("POST");
    when(request.getHeader("X-Management-Token")).thenReturn("wrong-token");
    when(request.getRequestURI()).thenReturn("/v1/workflows/my-workflow-id/execute");
    when(request.getHeader("Authorization")).thenReturn(null);

    authFilter.doFilterInternal(request, response, filterChain);

    verify(request).setAttribute(eq("user"), any(UserClaim.class));
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void shouldValidateJwtAndSetUserClaimWhenValidAuthorizationHeaderProvided() throws Exception {
    String jwt = "valid-jwt-token";
    UserClaim userClaim = new UserClaim();
    userClaim.setUsername("testuser");

    when(request.getMethod()).thenReturn("GET");
    when(request.getHeader("X-Management-Token")).thenReturn(null);
    when(request.getRequestURI()).thenReturn("/api/some-endpoint");
    when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
    when(extAppClient.validate(jwt)).thenReturn(userClaim);

    authFilter.doFilterInternal(request, response, filterChain);

    verify(extAppClient).validate(jwt);
    verify(request).setAttribute(eq("user"), any(UserClaim.class));
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void shouldSendUnauthorizedErrorWhenJwtValidationFails() throws Exception {
    String jwt = "invalid-jwt-token";

    when(request.getMethod()).thenReturn("GET");
    when(request.getHeader("X-Management-Token")).thenReturn(null);
    when(request.getRequestURI()).thenReturn("/api/some-endpoint");
    when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
    when(extAppClient.validate(jwt)).thenThrow(new RuntimeException("Invalid token"));

    authFilter.doFilterInternal(request, response, filterChain);

    verify(response).sendError(SC_UNAUTHORIZED, "Invalid Credentials");
    verify(filterChain, never()).doFilter(request, response);
  }

  @Test
  void shouldNotSetUserAttributeWhenUserClaimUsernameIsNull() throws Exception {
    String jwt = "jwt-token";
    UserClaim userClaim = new UserClaim();
    userClaim.setUsername(null);

    when(request.getMethod()).thenReturn("GET");
    when(request.getHeader("X-Management-Token")).thenReturn(null);
    when(request.getRequestURI()).thenReturn("/api/some-endpoint");
    when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
    when(extAppClient.validate(jwt)).thenReturn(userClaim);

    authFilter.doFilterInternal(request, response, filterChain);

    verify(request, never()).setAttribute(eq("user"), any(UserClaim.class));
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void shouldHandleExecPatternWithHyphenatedWorkflowId() throws Exception {
    when(request.getMethod()).thenReturn("POST");
    when(request.getHeader("X-Management-Token")).thenReturn("wrong-token");
    when(request.getRequestURI()).thenReturn("/v1/workflows/workflow-with-hyphens/execute");
    when(request.getHeader("Authorization")).thenReturn(null);

    authFilter.doFilterInternal(request, response, filterChain);

    verify(request).setAttribute(eq("user"), any(UserClaim.class));
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void shouldSendUnauthorizedWhenAuthorizationHeaderIsNullAndNoOtherAuthProvided() throws Exception {
    when(request.getMethod()).thenReturn("GET");
    when(request.getHeader("X-Management-Token")).thenReturn(null);
    when(request.getRequestURI()).thenReturn("/api/some-endpoint");
    when(request.getHeader("Authorization")).thenReturn(null);

    authFilter.doFilterInternal(request, response, filterChain);

    verify(response).sendError(SC_UNAUTHORIZED, "Invalid Credentials");
    verify(filterChain, never()).doFilter(request, response);
  }
}
