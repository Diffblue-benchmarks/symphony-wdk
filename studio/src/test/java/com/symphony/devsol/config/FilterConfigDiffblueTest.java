package com.symphony.devsol.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.core.auth.impl.ExtensionAppAuthenticatorCertImpl;
import com.symphony.bdk.core.config.model.BdkRetryConfig;
import com.symphony.devsol.client.ExtAppClient;
import java.util.Collection;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.web.filter.OncePerRequestFilter;

class FilterConfigDiffblueTest {
  /**
   * Method under test: {@link FilterConfig#authFilterRegistration(AuthFilter)}
   */
  @Test
  void testAuthFilterRegistration() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    FilterConfig filterConfig = new FilterConfig();
    AuthFilter filter = new AuthFilter(
        new ExtAppClient(new ExtensionAppAuthenticatorCertImpl(mock(BdkRetryConfig.class), "42", null)));

    // Act
    FilterRegistrationBean<OncePerRequestFilter> actualAuthFilterRegistrationResult = filterConfig
        .authFilterRegistration(filter);

    // Assert
    Collection<String> servletNames = actualAuthFilterRegistrationResult.getServletNames();
    assertTrue(servletNames instanceof Set);
    Collection<ServletRegistrationBean<?>> servletRegistrationBeans = actualAuthFilterRegistrationResult
        .getServletRegistrationBeans();
    assertTrue(servletRegistrationBeans instanceof Set);
    Collection<String> urlPatterns = actualAuthFilterRegistrationResult.getUrlPatterns();
    assertEquals(3, urlPatterns.size());
    assertTrue(urlPatterns instanceof Set);
    assertEquals("authFilter", actualAuthFilterRegistrationResult.getFilterName());
    assertFalse(actualAuthFilterRegistrationResult.isMatchAfter());
    assertTrue(urlPatterns.contains("/gallery/*"));
    assertTrue(urlPatterns.contains("/symphony/*"));
    assertTrue(urlPatterns.contains("/v1/*"));
    assertTrue(servletNames.isEmpty());
    assertTrue(servletRegistrationBeans.isEmpty());
    assertTrue(actualAuthFilterRegistrationResult.getInitParameters().isEmpty());
    assertTrue(actualAuthFilterRegistrationResult.isAsyncSupported());
    assertTrue(actualAuthFilterRegistrationResult.isEnabled());
    assertEquals(Integer.MIN_VALUE, actualAuthFilterRegistrationResult.getOrder());
    assertSame(filter, actualAuthFilterRegistrationResult.getFilter());
  }

  /**
   * Method under test: {@link FilterConfig#wdkFilterRegistration(WdkFilter)}
   */
  @Test
  void testWdkFilterRegistration() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    FilterConfig filterConfig = new FilterConfig();
    WdkFilter filter = new WdkFilter();

    // Act
    FilterRegistrationBean<OncePerRequestFilter> actualWdkFilterRegistrationResult = filterConfig
        .wdkFilterRegistration(filter);

    // Assert
    Collection<String> servletNames = actualWdkFilterRegistrationResult.getServletNames();
    assertTrue(servletNames instanceof Set);
    Collection<ServletRegistrationBean<?>> servletRegistrationBeans = actualWdkFilterRegistrationResult
        .getServletRegistrationBeans();
    assertTrue(servletRegistrationBeans instanceof Set);
    Collection<String> urlPatterns = actualWdkFilterRegistrationResult.getUrlPatterns();
    assertEquals(1, urlPatterns.size());
    assertTrue(urlPatterns instanceof Set);
    assertEquals("wdkFilter", actualWdkFilterRegistrationResult.getFilterName());
    assertEquals(-2147483646, actualWdkFilterRegistrationResult.getOrder());
    assertFalse(actualWdkFilterRegistrationResult.isMatchAfter());
    assertTrue(urlPatterns.contains("/v1/*"));
    assertTrue(servletNames.isEmpty());
    assertTrue(servletRegistrationBeans.isEmpty());
    assertTrue(actualWdkFilterRegistrationResult.getInitParameters().isEmpty());
    assertTrue(actualWdkFilterRegistrationResult.isAsyncSupported());
    assertTrue(actualWdkFilterRegistrationResult.isEnabled());
    assertSame(filter, actualWdkFilterRegistrationResult.getFilter());
  }

  /**
   * Method under test: {@link FilterConfig#wdkFilterRegistration(WdkFilter)}
   */
  @Test
  void testWdkFilterRegistration2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WdkFilter filter = mock(WdkFilter.class);

    // Act
    FilterRegistrationBean<OncePerRequestFilter> actualWdkFilterRegistrationResult = (new FilterConfig())
        .wdkFilterRegistration(filter);

    // Assert
    Collection<String> servletNames = actualWdkFilterRegistrationResult.getServletNames();
    assertTrue(servletNames instanceof Set);
    Collection<ServletRegistrationBean<?>> servletRegistrationBeans = actualWdkFilterRegistrationResult
        .getServletRegistrationBeans();
    assertTrue(servletRegistrationBeans instanceof Set);
    Collection<String> urlPatterns = actualWdkFilterRegistrationResult.getUrlPatterns();
    assertEquals(1, urlPatterns.size());
    assertTrue(urlPatterns instanceof Set);
    assertEquals("wdkFilter", actualWdkFilterRegistrationResult.getFilterName());
    assertEquals(-2147483646, actualWdkFilterRegistrationResult.getOrder());
    assertFalse(actualWdkFilterRegistrationResult.isMatchAfter());
    assertTrue(urlPatterns.contains("/v1/*"));
    assertTrue(servletNames.isEmpty());
    assertTrue(servletRegistrationBeans.isEmpty());
    assertTrue(actualWdkFilterRegistrationResult.getInitParameters().isEmpty());
    assertTrue(actualWdkFilterRegistrationResult.isAsyncSupported());
    assertTrue(actualWdkFilterRegistrationResult.isEnabled());
    assertSame(filter, actualWdkFilterRegistrationResult.getFilter());
  }
}
