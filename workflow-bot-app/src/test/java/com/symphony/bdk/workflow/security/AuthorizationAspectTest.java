package com.symphony.bdk.workflow.security;

import com.symphony.bdk.workflow.api.v1.WorkflowsApi;
import com.symphony.bdk.workflow.api.v1.WorkflowsMgtApi;
import com.symphony.bdk.workflow.configuration.WorkflowBotConfiguration;
import com.symphony.bdk.workflow.exception.UnauthorizedException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationAspectTest {

  @Mock
  private WorkflowBotConfiguration workflowBotConfiguration;

  @InjectMocks
  private AuthorizationAspect authorizationAspect;

  @AfterEach
  void tearDown() {
    RequestContextHolder.resetRequestAttributes();
  }

  private void setUpRequestWithHeader(String headerKey, String headerValue) {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(headerKey, headerValue);
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
  }

  @Test
  void shouldPassWhenManagementTokenIsValid() {
    String token = "valid-management-token";
    setUpRequestWithHeader(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY, token);
    when(workflowBotConfiguration.getManagementToken()).thenReturn(token);

    Authorized authorized = mock(Authorized.class);
    when(authorized.headerTokenKey()).thenReturn(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);

    assertThatCode(() -> authorizationAspect.authorizationCheck(authorized)).doesNotThrowAnyException();
  }

  @Test
  void shouldThrowWhenManagementTokenIsInvalid() {
    setUpRequestWithHeader(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY, "wrong-token");
    when(workflowBotConfiguration.getManagementToken()).thenReturn("correct-token");

    Authorized authorized = mock(Authorized.class);
    when(authorized.headerTokenKey()).thenReturn(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class);
  }

  @Test
  void shouldThrowWhenManagementTokenIsBlank() {
    setUpRequestWithHeader(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY, "some-token");
    when(workflowBotConfiguration.getManagementToken()).thenReturn("");

    Authorized authorized = mock(Authorized.class);
    when(authorized.headerTokenKey()).thenReturn(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class);
  }

  @Test
  void shouldPassWhenMonitoringTokenIsValid() {
    String token = "valid-monitoring-token";
    setUpRequestWithHeader(WorkflowsApi.X_MONITORING_TOKEN_KEY, token);
    when(workflowBotConfiguration.getMonitoringToken()).thenReturn(token);

    Authorized authorized = mock(Authorized.class);
    when(authorized.headerTokenKey()).thenReturn(WorkflowsApi.X_MONITORING_TOKEN_KEY);

    assertThatCode(() -> authorizationAspect.authorizationCheck(authorized)).doesNotThrowAnyException();
  }

  @Test
  void shouldThrowWhenMonitoringTokenIsInvalid() {
    setUpRequestWithHeader(WorkflowsApi.X_MONITORING_TOKEN_KEY, "wrong-token");
    when(workflowBotConfiguration.getMonitoringToken()).thenReturn("correct-monitoring-token");

    Authorized authorized = mock(Authorized.class);
    when(authorized.headerTokenKey()).thenReturn(WorkflowsApi.X_MONITORING_TOKEN_KEY);

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class);
  }

  @Test
  void shouldThrowWhenHeaderKeyIsUnknown() {
    setUpRequestWithHeader("X-Unknown-Header", "some-token");

    Authorized authorized = mock(Authorized.class);
    when(authorized.headerTokenKey()).thenReturn("X-Unknown-Header");

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class);
  }
}
