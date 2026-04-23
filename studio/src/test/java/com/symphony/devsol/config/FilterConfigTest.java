package com.symphony.devsol.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockMakers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

@ExtendWith(MockitoExtension.class)
class FilterConfigTest {

  @InjectMocks
  private FilterConfig filterConfig;

  @Mock
  private AuthFilter authFilter;

  private AccessFilter accessFilter;

  @Mock
  private WdkFilter wdkFilter;

  @BeforeEach
  void setUp() {
    accessFilter = mock(AccessFilter.class, withSettings().mockMaker(MockMakers.SUBCLASS));
  }

  @Test
  void authFilterRegistrationShouldReturnBeanWithCorrectUrlPatternsAndOrder() {
    FilterRegistrationBean<?> registration = filterConfig.authFilterRegistration(authFilter);

    assertThat(registration, is(notNullValue()));
    assertThat(registration.getFilter(), is(sameInstance(authFilter)));
    assertThat(registration.getUrlPatterns(), contains("/v1/*", "/gallery/*", "/symphony/*"));
    assertThat(registration.getOrder(), is(equalTo(Ordered.HIGHEST_PRECEDENCE)));
  }

  @Test
  void accessFilterRegistrationShouldReturnBeanWithCorrectUrlPatternsAndOrder() {
    FilterRegistrationBean<?> registration = filterConfig.accessFilterRegistration(accessFilter);

    assertThat(registration, is(notNullValue()));
    assertThat(registration.getFilter(), is(sameInstance(accessFilter)));
    assertThat(registration.getUrlPatterns(), contains("/v1/workflows", "/v1/workflows/*"));
    assertThat(registration.getOrder(), is(equalTo(Ordered.HIGHEST_PRECEDENCE + 1)));
  }

  @Test
  void wdkFilterRegistrationShouldReturnBeanWithCorrectUrlPatternsAndOrder() {
    FilterRegistrationBean<?> registration = filterConfig.wdkFilterRegistration(wdkFilter);

    assertThat(registration, is(notNullValue()));
    assertThat(registration.getFilter(), is(sameInstance(wdkFilter)));
    assertThat(registration.getUrlPatterns(), contains("/v1/*"));
    assertThat(registration.getOrder(), is(equalTo(Ordered.HIGHEST_PRECEDENCE + 2)));
  }
}
