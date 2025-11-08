package com.symphony.devsol.config;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(classes = {WdkFilter.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
class WdkFilterDiffblueTest {
  @Autowired
  private WdkFilter wdkFilter;

  /**
   * Test {@link WdkFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}.
   * <ul>
   *   <li>Then throw {@link ServletException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WdkFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName("Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); then throw ServletException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WdkFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"})
  void testDoFilterInternal_thenThrowServletException() throws ServletException, IOException {
    // Arrange
    MockHttpServletRequest request = new MockHttpServletRequest();
    Response response = new Response();
    FilterChain chain = mock(FilterChain.class);
    doThrow(new ServletException("An error occurred")).when(chain)
        .doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act and Assert
    assertThrows(ServletException.class, () -> wdkFilter.doFilterInternal(request, response, chain));
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
  }

  /**
   * Test {@link WdkFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}.
   * <ul>
   *   <li>When {@link FilterChain} {@link FilterChain#doFilter(ServletRequest, ServletResponse)} does nothing.</li>
   *   <li>Then calls {@link FilterChain#doFilter(ServletRequest, ServletResponse)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WdkFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName("Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); when FilterChain doFilter(ServletRequest, ServletResponse) does nothing; then calls doFilter(ServletRequest, ServletResponse)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WdkFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"})
  void testDoFilterInternal_whenFilterChainDoFilterDoesNothing_thenCallsDoFilter()
      throws ServletException, IOException {
    // Arrange
    MockHttpServletRequest request = new MockHttpServletRequest();
    Response response = new Response();
    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    wdkFilter.doFilterInternal(request, response, chain);

    // Assert
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
  }
}
