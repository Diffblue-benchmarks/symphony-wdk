package com.symphony.devsol.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WdkFilterTest {

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private FilterChain chain;

  private WdkFilter underTest;

  @BeforeEach
  void setUp() {
    underTest = new WdkFilter();
    ReflectionTestUtils.setField(underTest, "monitoringToken", "test-monitoring-token");
    ReflectionTestUtils.setField(underTest, "managementToken", "test-management-token");
  }

  @Test
  void shouldDoFilterWithWrappedRequest() throws ServletException, IOException {
    underTest.doFilterInternal(request, response, chain);

    ArgumentCaptor<HttpServletRequestWrapper> wrapperCaptor =
        ArgumentCaptor.forClass(HttpServletRequestWrapper.class);
    verify(chain).doFilter(wrapperCaptor.capture(), eq(response));
    assertThat(wrapperCaptor.getValue().getRequest(), equalTo(request));
  }

  @Test
  void shouldReturnMonitoringTokenForXMonitoringTokenHeader() throws ServletException, IOException {
    underTest.doFilterInternal(request, response, chain);

    ArgumentCaptor<HttpServletRequestWrapper> wrapperCaptor =
        ArgumentCaptor.forClass(HttpServletRequestWrapper.class);
    verify(chain).doFilter(wrapperCaptor.capture(), eq(response));
    assertThat(wrapperCaptor.getValue().getHeader("X-Monitoring-Token"), equalTo("test-monitoring-token"));
  }

  @Test
  void shouldReturnManagementTokenForXManagementTokenHeader() throws ServletException, IOException {
    underTest.doFilterInternal(request, response, chain);

    ArgumentCaptor<HttpServletRequestWrapper> wrapperCaptor =
        ArgumentCaptor.forClass(HttpServletRequestWrapper.class);
    verify(chain).doFilter(wrapperCaptor.capture(), eq(response));
    assertThat(wrapperCaptor.getValue().getHeader("X-Management-Token"), equalTo("test-management-token"));
  }

  @Test
  void shouldDelegateToOriginalRequestForOtherHeaders() throws ServletException, IOException {
    when(request.getHeader("Authorization")).thenReturn("Bearer some-token");

    underTest.doFilterInternal(request, response, chain);

    ArgumentCaptor<HttpServletRequestWrapper> wrapperCaptor =
        ArgumentCaptor.forClass(HttpServletRequestWrapper.class);
    verify(chain).doFilter(wrapperCaptor.capture(), eq(response));
    assertThat(wrapperCaptor.getValue().getHeader("Authorization"), equalTo("Bearer some-token"));
  }
}
