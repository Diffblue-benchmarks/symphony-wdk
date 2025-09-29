package com.symphony.devsol.config;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.devsol.client.ExtAppClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(classes = {AuthFilter.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class AuthFilterDiffblueTest {
  @Autowired private AuthFilter authFilter;

  @MockBean private ExtAppClient extAppClient;

  /**
   * Test {@link AuthFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given {@code OPTIONS}.
   *   <li>Then calls {@link FilterChain#doFilter(ServletRequest, ServletResponse)}.
   * </ul>
   *
   * <p>Method under test: {@link AuthFilter#doFilterInternal(HttpServletRequest,
   * HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given 'OPTIONS'; then calls doFilter(ServletRequest, ServletResponse)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AuthFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenOptions_thenCallsDoFilter() throws ServletException, IOException {
    // Arrange
    MockHttpServletRequest request = new MockHttpServletRequest(new MockServletContext());
    request.setMethod("OPTIONS");
    Response response = new Response();

    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    authFilter.doFilterInternal(request, response, chain);

    // Assert
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
  }

  /**
   * Test {@link AuthFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given {@link RuntimeException#RuntimeException()}.
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link AuthFilter#doFilterInternal(HttpServletRequest,
   * HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given RuntimeException(); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AuthFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenRuntimeException_thenThrowRuntimeException()
      throws ServletException, IOException {
    // Arrange
    HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
    when(request.getHeader(Mockito.<String>any())).thenThrow(new RuntimeException());
    when(request.getMethod()).thenReturn("https://example.org/example");
    HttpServletRequestWrapper request2 = new HttpServletRequestWrapper(request);

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> authFilter.doFilterInternal(request2, new Response(), mock(FilterChain.class)));
    verify(request).getHeader("Authorization");
    verify(request).getMethod();
  }

  /**
   * Test {@link AuthFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Then throw {@link ServletException}.
   * </ul>
   *
   * <p>Method under test: {@link AuthFilter#doFilterInternal(HttpServletRequest,
   * HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); then throw ServletException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AuthFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_thenThrowServletException() throws ServletException, IOException {
    // Arrange
    MockHttpServletRequest request = new MockHttpServletRequest(new MockServletContext());
    request.setMethod("OPTIONS");
    Response response = new Response();

    FilterChain chain = mock(FilterChain.class);
    doThrow(new ServletException("An error occurred"))
        .when(chain)
        .doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act and Assert
    assertThrows(
        ServletException.class, () -> authFilter.doFilterInternal(request, response, chain));
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
  }
}
