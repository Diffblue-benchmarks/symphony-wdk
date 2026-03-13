package com.symphony.devsol.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WdkFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private WdkFilter wdkFilter;

    private static final String MONITORING_TOKEN = "test-monitoring-token";
    private static final String MANAGEMENT_TOKEN = "test-management-token";

    @BeforeEach
    void setUp() {
        wdkFilter = new WdkFilter();
        ReflectionTestUtils.setField(wdkFilter, "monitoringToken", MONITORING_TOKEN);
        ReflectionTestUtils.setField(wdkFilter, "managementToken", MANAGEMENT_TOKEN);
    }

    @Test
    void shouldWrapRequestWithMonitoringToken() throws ServletException, IOException {
        // Act
        wdkFilter.doFilterInternal(request, response, filterChain);

        // Assert
        ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        verify(filterChain).doFilter(requestCaptor.capture(), any(HttpServletResponse.class));

        HttpServletRequest wrappedRequest = requestCaptor.getValue();
        assertThat(wrappedRequest, is(notNullValue()));
        assertThat(wrappedRequest.getHeader("X-Monitoring-Token"), is(MONITORING_TOKEN));
    }

    @Test
    void shouldWrapRequestWithManagementToken() throws ServletException, IOException {
        // Act
        wdkFilter.doFilterInternal(request, response, filterChain);

        // Assert
        ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        verify(filterChain).doFilter(requestCaptor.capture(), any(HttpServletResponse.class));

        HttpServletRequest wrappedRequest = requestCaptor.getValue();
        assertThat(wrappedRequest, is(notNullValue()));
        assertThat(wrappedRequest.getHeader("X-Management-Token"), is(MANAGEMENT_TOKEN));
    }

    @Test
    void shouldPassThroughOtherHeaders() throws ServletException, IOException {
        // Arrange
        String otherHeaderName = "X-Other-Header";
        String otherHeaderValue = "other-value";
        when(request.getHeader(otherHeaderName)).thenReturn(otherHeaderValue);

        // Act
        wdkFilter.doFilterInternal(request, response, filterChain);

        // Assert
        ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        verify(filterChain).doFilter(requestCaptor.capture(), any(HttpServletResponse.class));

        HttpServletRequest wrappedRequest = requestCaptor.getValue();
        assertThat(wrappedRequest.getHeader(otherHeaderName), is(otherHeaderValue));
    }

    @Test
    void shouldInvokeFilterChainWithWrappedRequest() throws ServletException, IOException {
        // Act
        wdkFilter.doFilterInternal(request, response, filterChain);

        // Assert
        ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        verify(filterChain).doFilter(requestCaptor.capture(), any(HttpServletResponse.class));
        assertThat(requestCaptor.getValue(), is(notNullValue()));
    }
}
