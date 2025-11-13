package com.symphony.devsol.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.auth.impl.ExtensionAppAuthenticatorCertImpl;
import com.symphony.bdk.core.config.model.BdkRetryConfig;
import com.symphony.devsol.client.ExtAppClient;
import java.util.Collection;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.web.filter.OncePerRequestFilter;

class FilterConfigDiffblueTest {
  /**
   * Test {@link FilterConfig#authFilterRegistration(AuthFilter)}.
   *
   * <ul>
   *   <li>Then Filter return {@link AuthFilter}.
   * </ul>
   *
   * <p>Method under test: {@link FilterConfig#authFilterRegistration(AuthFilter)}
   */
  @Test
  @DisplayName("Test authFilterRegistration(AuthFilter); then Filter return AuthFilter")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"FilterRegistrationBean FilterConfig.authFilterRegistration(AuthFilter)"})
  void testAuthFilterRegistration_thenFilterReturnAuthFilter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    FilterConfig filterConfig = new FilterConfig();
    ExtensionAppAuthenticatorCertImpl extAppAuth =
        new ExtensionAppAuthenticatorCertImpl(new BdkRetryConfig(), "42", null);
    AuthFilter filter = new AuthFilter(new ExtAppClient(extAppAuth));

    // Act
    FilterRegistrationBean<OncePerRequestFilter> actualAuthFilterRegistrationResult =
        filterConfig.authFilterRegistration(filter);

    // Assert
    OncePerRequestFilter filter2 = actualAuthFilterRegistrationResult.getFilter();
    assertTrue(filter2 instanceof AuthFilter);
    Collection<String> servletNames = actualAuthFilterRegistrationResult.getServletNames();
    assertTrue(servletNames instanceof Set);
    Collection<ServletRegistrationBean<?>> servletRegistrationBeans =
        actualAuthFilterRegistrationResult.getServletRegistrationBeans();
    assertTrue(servletRegistrationBeans instanceof Set);
    Collection<String> urlPatterns = actualAuthFilterRegistrationResult.getUrlPatterns();
    assertEquals(3, urlPatterns.size());
    assertTrue(urlPatterns instanceof Set);
    assertEquals("authFilter", actualAuthFilterRegistrationResult.getFilterName());
    assertFalse(actualAuthFilterRegistrationResult.isMatchAfter());
    assertTrue(servletNames.isEmpty());
    assertTrue(servletRegistrationBeans.isEmpty());
    assertTrue(actualAuthFilterRegistrationResult.getInitParameters().isEmpty());
    assertTrue(actualAuthFilterRegistrationResult.isAsyncSupported());
    assertTrue(actualAuthFilterRegistrationResult.isEnabled());
    assertEquals(Integer.MIN_VALUE, actualAuthFilterRegistrationResult.getOrder());
    assertSame(filter, filter2);
  }

  /**
   * Test {@link FilterConfig#wdkFilterRegistration(WdkFilter)}.
   *
   * <ul>
   *   <li>When {@link WdkFilter} (default constructor).
   *   <li>Then ServletNames return {@link Set}.
   * </ul>
   *
   * <p>Method under test: {@link FilterConfig#wdkFilterRegistration(WdkFilter)}
   */
  @Test
  @DisplayName(
      "Test wdkFilterRegistration(WdkFilter); when WdkFilter (default constructor); then ServletNames return Set")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"FilterRegistrationBean FilterConfig.wdkFilterRegistration(WdkFilter)"})
  void testWdkFilterRegistration_whenWdkFilter_thenServletNamesReturnSet() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    FilterConfig filterConfig = new FilterConfig();
    WdkFilter filter = new WdkFilter();

    // Act
    FilterRegistrationBean<OncePerRequestFilter> actualWdkFilterRegistrationResult =
        filterConfig.wdkFilterRegistration(filter);

    // Assert
    Collection<String> servletNames = actualWdkFilterRegistrationResult.getServletNames();
    assertTrue(servletNames instanceof Set);
    Collection<ServletRegistrationBean<?>> servletRegistrationBeans =
        actualWdkFilterRegistrationResult.getServletRegistrationBeans();
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
