package com.symphony.devsol.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(MockitoExtension.class)
class FilterConfigTest {

    @Mock
    private WdkFilter wdkFilter;

    @InjectMocks
    private FilterConfig filterConfig;

    @Test
    void wdkFilterRegistrationShouldConfigureFilterWithUrlPatternsAndOrder() {
        // Arrange
        // (no additional setup needed)

        // Act
        FilterRegistrationBean<OncePerRequestFilter> result = filterConfig.wdkFilterRegistration(wdkFilter);

        // Assert
        assertThat(result, is(notNullValue()));
        assertThat(result.getFilter(), is(wdkFilter));
        assertThat(result.getUrlPatterns(), contains("/v1/*"));
        assertThat(result.getOrder(), is(Ordered.HIGHEST_PRECEDENCE + 2));
    }
}
