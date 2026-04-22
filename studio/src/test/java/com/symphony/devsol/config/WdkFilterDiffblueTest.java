package com.symphony.devsol.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WdkFilter.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
@TestPropertySource(
    properties = {
      "wdk.properties.monitoring-token=test-monitoring-token",
      "wdk.properties.management-token=test-management-token"
    })
class WdkFilterDiffblueTest {
  @Autowired private WdkFilter wdkFilter;

  /**
   * Test {@link WdkFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Then {@link FilterChain} doFilter with wrapped request and response.
   * </ul>
   *
   * <p>Method under test: {@link WdkFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); then chain doFilter called")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void WdkFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_thenChainDoFilterCalled() throws ServletException, IOException {
    // Arrange
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    wdkFilter.doFilterInternal(request, response, chain);

    // Assert
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
  }

  /**
   * Test {@link WdkFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given {@link ServletException#ServletException()}.
   *   <li>Then throw {@link ServletException}.
   * </ul>
   *
   * <p>Method under test: {@link WdkFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given ServletException(); then throw ServletException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void WdkFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenServletException_thenThrowServletException()
      throws ServletException, IOException {
    // Arrange
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    FilterChain chain = mock(FilterChain.class);
    doThrow(new ServletException())
        .when(chain)
        .doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act and Assert
    assertThrows(
        ServletException.class, () -> wdkFilter.doFilterInternal(request, response, chain));
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
  }

  /**
   * Test {@link WdkFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)} wrapper for X-Monitoring-Token header.
   *
   * <ul>
   *   <li>When getHeader called with {@code X-Monitoring-Token}.
   *   <li>Then return configured monitoring token.
   * </ul>
   *
   * <p>Method under test: inner class created by {@code wdkTokenWrapper}
   */
  @Test
  @DisplayName(
      "Test wdkTokenWrapper(HttpServletRequest); when getHeader X-Monitoring-Token; then return monitoringToken")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HttpServletRequestWrapper WdkFilter.wdkTokenWrapper(HttpServletRequest)"
  })
  void testWdkTokenWrapper_whenXMonitoringToken_thenReturnMonitoringToken()
      throws ServletException, IOException {
    // Arrange
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    ArgumentCaptor<ServletRequest> captor = ArgumentCaptor.forClass(ServletRequest.class);
    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(captor.capture(), Mockito.<ServletResponse>any());

    // Act
    wdkFilter.doFilterInternal(request, response, chain);

    // Assert
    HttpServletRequest wrappedRequest = (HttpServletRequest) captor.getValue();
    assertEquals("test-monitoring-token", wrappedRequest.getHeader("X-Monitoring-Token"));
  }

  /**
   * Test {@link WdkFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)} wrapper for X-Management-Token header.
   *
   * <ul>
   *   <li>When getHeader called with {@code X-Management-Token}.
   *   <li>Then return configured management token.
   * </ul>
   *
   * <p>Method under test: inner class created by {@code wdkTokenWrapper}
   */
  @Test
  @DisplayName(
      "Test wdkTokenWrapper(HttpServletRequest); when getHeader X-Management-Token; then return managementToken")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HttpServletRequestWrapper WdkFilter.wdkTokenWrapper(HttpServletRequest)"
  })
  void testWdkTokenWrapper_whenXManagementToken_thenReturnManagementToken()
      throws ServletException, IOException {
    // Arrange
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    ArgumentCaptor<ServletRequest> captor = ArgumentCaptor.forClass(ServletRequest.class);
    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(captor.capture(), Mockito.<ServletResponse>any());

    // Act
    wdkFilter.doFilterInternal(request, response, chain);

    // Assert
    HttpServletRequest wrappedRequest = (HttpServletRequest) captor.getValue();
    assertEquals("test-management-token", wrappedRequest.getHeader("X-Management-Token"));
  }

  /**
   * Test {@link WdkFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)} wrapper for other headers.
   *
   * <ul>
   *   <li>When getHeader called with another header name.
   *   <li>Then delegate to super (return null for MockHttpServletRequest with no headers set).
   * </ul>
   *
   * <p>Method under test: inner class created by {@code wdkTokenWrapper}
   */
  @Test
  @DisplayName(
      "Test wdkTokenWrapper(HttpServletRequest); when getHeader other header; then delegateToSuper")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HttpServletRequestWrapper WdkFilter.wdkTokenWrapper(HttpServletRequest)"
  })
  void testWdkTokenWrapper_whenOtherHeader_thenDelegateToSuper()
      throws ServletException, IOException {
    // Arrange
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("X-Custom-Header", "custom-value");
    MockHttpServletResponse response = new MockHttpServletResponse();
    ArgumentCaptor<ServletRequest> captor = ArgumentCaptor.forClass(ServletRequest.class);
    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(captor.capture(), Mockito.<ServletResponse>any());

    // Act
    wdkFilter.doFilterInternal(request, response, chain);

    // Assert
    HttpServletRequest wrappedRequest = (HttpServletRequest) captor.getValue();
    assertEquals("custom-value", wrappedRequest.getHeader("X-Custom-Header"));
  }
}
