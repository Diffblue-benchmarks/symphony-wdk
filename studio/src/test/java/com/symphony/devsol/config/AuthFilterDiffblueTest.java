package com.symphony.devsol.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.auth.exception.AuthInitializationException;
import com.symphony.bdk.core.auth.jwt.UserClaim;
import com.symphony.devsol.client.ExtAppClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(classes = {AuthFilter.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"wdk.properties.management-token=test-management-token"})
@WebAppConfiguration
class AuthFilterDiffblueTest {
  @Autowired private AuthFilter authFilter;

  @MockBean private ExtAppClient extAppClient;

  /**
   * Test {@link AuthFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given {@code OPTIONS}.
   *   <li>Then {@link MockHttpServletResponse} (default constructor) Status is two hundred.
   * </ul>
   *
   * <p>Method under test: {@link AuthFilter#doFilterInternal(HttpServletRequest,
   * HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given 'OPTIONS'; then MockHttpServletResponse (default constructor) Status is two hundred")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AuthFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenOptions_thenMockHttpServletResponseStatusIsTwoHundred()
      throws ServletException, IOException {
    // Arrange
    MockMultipartHttpServletRequest request =
        new MockMultipartHttpServletRequest(new MockServletContext());
    request.setMethod("OPTIONS");
    MockHttpServletResponse response = new MockHttpServletResponse();

    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    authFilter.doFilterInternal(request, response, chain);

    // Assert that nothing has changed
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    assertEquals(200, response.getStatus());
    assertFalse(response.isCommitted());
  }

  /**
   * Test {@link AuthFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given {@link ServletException#ServletException()}.
   *   <li>Then throw {@link ServletException}.
   * </ul>
   *
   * <p>Method under test: {@link AuthFilter#doFilterInternal(HttpServletRequest,
   * HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given ServletException(); then throw ServletException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AuthFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenServletException_thenThrowServletException()
      throws ServletException, IOException {
    // Arrange
    MockMultipartHttpServletRequest request =
        new MockMultipartHttpServletRequest(new MockServletContext());
    request.setMethod("OPTIONS");
    MockHttpServletResponse response = new MockHttpServletResponse();

    FilterChain chain = mock(FilterChain.class);
    doThrow(new ServletException())
        .when(chain)
        .doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act and Assert
    assertThrows(
        ServletException.class, () -> authFilter.doFilterInternal(request, response, chain));
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
  }

  /**
   * Test {@link AuthFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Then {@link MockHttpServletResponse} (default constructor) ErrorMessage is {@code Invalid
   *       Credentials}.
   * </ul>
   *
   * <p>Method under test: {@link AuthFilter#doFilterInternal(HttpServletRequest,
   * HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); then MockHttpServletResponse (default constructor) ErrorMessage is 'Invalid Credentials'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AuthFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_thenMockHttpServletResponseErrorMessageIsInvalidCredentials()
      throws ServletException, IOException {
    // Arrange
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    // Act
    authFilter.doFilterInternal(request, response, mock(FilterChain.class));

    // Assert
    assertEquals("Invalid Credentials", response.getErrorMessage());
    assertEquals(401, response.getStatus());
    assertTrue(response.isCommitted());
  }

  /**
   * Test {@link AuthFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given management token matches; then set username to {@code mgmt-token} and set user
   *       attribute on request.
   * </ul>
   *
   * <p>Method under test: {@link AuthFilter#doFilterInternal(HttpServletRequest,
   * HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given management token matches; then set username mgmt-token")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AuthFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenManagementTokenMatches_thenSetUsernameMgmtToken()
      throws ServletException, IOException {
    // Arrange
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("X-Management-Token", "test-management-token");
    MockHttpServletResponse response = new MockHttpServletResponse();
    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    authFilter.doFilterInternal(request, response, chain);

    // Assert
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    UserClaim userClaim = (UserClaim) request.getAttribute("user");
    assertNotNull(userClaim);
    assertEquals("mgmt-token", userClaim.getUsername());
    assertEquals(200, response.getStatus());
    assertFalse(response.isCommitted());
  }

  /**
   * Test {@link AuthFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given request URI matches exec pattern; then set username to {@code webhook} and set user
   *       attribute on request.
   * </ul>
   *
   * <p>Method under test: {@link AuthFilter#doFilterInternal(HttpServletRequest,
   * HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given exec pattern matches; then set username webhook")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AuthFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenExecPatternMatches_thenSetUsernameWebhook()
      throws ServletException, IOException {
    // Arrange
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setRequestURI("/v1/workflows/my-workflow/execute");
    MockHttpServletResponse response = new MockHttpServletResponse();
    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    authFilter.doFilterInternal(request, response, chain);

    // Assert
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    UserClaim userClaim = (UserClaim) request.getAttribute("user");
    assertNotNull(userClaim);
    assertEquals("webhook", userClaim.getUsername());
    assertEquals(200, response.getStatus());
    assertFalse(response.isCommitted());
  }

  /**
   * Test {@link AuthFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given a valid JWT token; then validate JWT via extAppClient and set user attribute on
   *       request.
   * </ul>
   *
   * <p>Method under test: {@link AuthFilter#doFilterInternal(HttpServletRequest,
   * HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given valid JWT; then validate and set user attribute")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AuthFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenValidJwt_thenValidateAndSetUserAttribute()
      throws ServletException, IOException, AuthInitializationException {
    // Arrange
    UserClaim userClaim = new UserClaim();
    userClaim.setUsername("janedoe");
    when(extAppClient.validate(Mockito.<String>any())).thenReturn(userClaim);

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", "Bearer valid.jwt.token");
    MockHttpServletResponse response = new MockHttpServletResponse();
    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    authFilter.doFilterInternal(request, response, chain);

    // Assert
    verify(extAppClient).validate("valid.jwt.token");
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    UserClaim actualUserClaim = (UserClaim) request.getAttribute("user");
    assertNotNull(actualUserClaim);
    assertEquals("janedoe", actualUserClaim.getUsername());
    assertEquals(200, response.getStatus());
    assertFalse(response.isCommitted());
  }
}
