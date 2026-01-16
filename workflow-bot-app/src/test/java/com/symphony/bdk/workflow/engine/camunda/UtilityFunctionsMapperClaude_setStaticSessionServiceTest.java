package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.gen.api.model.UserV2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class UtilityFunctionsMapperClaude_setStaticSessionServiceTest {

  private SessionService sessionService;
  private SessionService anotherSessionService;

  @BeforeEach
  void setUp() {
    sessionService = mock(SessionService.class);
    anotherSessionService = mock(SessionService.class);
  }

  @AfterEach
  void tearDown() {
    // Clean up static state to avoid test pollution
    UtilityFunctionsMapper.setStaticSessionService(null);
  }

  @Test
  void setStaticSessionService_shouldMakeSessionServiceAvailableToSessionMethod() {
    // Given: A SessionService that returns a specific user
    UserV2 expectedUser = new UserV2();
    expectedUser.setId(12345L);
    when(sessionService.getSession()).thenReturn(expectedUser);

    // When: setStaticSessionService is called
    UtilityFunctionsMapper.setStaticSessionService(sessionService);

    // Then: The session() method should use the set service
    UserV2 actualUser = UtilityFunctionsMapper.session();
    assertThat(actualUser).isSameAs(expectedUser);
    verify(sessionService).getSession();
  }

  @Test
  void setStaticSessionService_calledMultipleTimes_shouldUpdateToLatestService() {
    // Given: Two different SessionServices
    UserV2 firstUser = new UserV2();
    firstUser.setId(111L);
    when(sessionService.getSession()).thenReturn(firstUser);

    UserV2 secondUser = new UserV2();
    secondUser.setId(222L);
    when(anotherSessionService.getSession()).thenReturn(secondUser);

    // When: setStaticSessionService is called twice with different services
    UtilityFunctionsMapper.setStaticSessionService(sessionService);
    UtilityFunctionsMapper.setStaticSessionService(anotherSessionService);

    // Then: The session() method should use the latest service
    UserV2 actualUser = UtilityFunctionsMapper.session();
    assertThat(actualUser).isSameAs(secondUser);
    verify(anotherSessionService).getSession();
  }

  @Test
  void setStaticSessionService_withNull_shouldAllowNullSessionService() {
    // When: setStaticSessionService is called with null
    UtilityFunctionsMapper.setStaticSessionService(null);

    // Then: The method should complete without throwing an exception
    // This is a valid state (though session() would fail if called)
  }

  @Test
  void setStaticSessionService_shouldReplaceExistingService() {
    // Given: An initial SessionService is set
    UserV2 firstUser = new UserV2();
    firstUser.setId(333L);
    when(sessionService.getSession()).thenReturn(firstUser);
    UtilityFunctionsMapper.setStaticSessionService(sessionService);

    // When: A new SessionService is set
    UserV2 secondUser = new UserV2();
    secondUser.setId(444L);
    when(anotherSessionService.getSession()).thenReturn(secondUser);
    UtilityFunctionsMapper.setStaticSessionService(anotherSessionService);

    // Then: Only the new service should be used, not the old one
    UserV2 actualUser = UtilityFunctionsMapper.session();
    assertThat(actualUser).isSameAs(secondUser);
    assertThat(actualUser.getId()).isEqualTo(444L);
  }

  @Test
  void setStaticSessionService_shouldWorkWithDifferentUserData() {
    // Given: A SessionService returning a user with specific attributes
    UserV2 user = new UserV2();
    user.setId(555L);
    user.setUsername("testuser");
    user.setDisplayName("Test User");
    when(sessionService.getSession()).thenReturn(user);

    // When: setStaticSessionService is called
    UtilityFunctionsMapper.setStaticSessionService(sessionService);

    // Then: The session() method should return the user with all attributes
    UserV2 actualUser = UtilityFunctionsMapper.session();
    assertThat(actualUser.getId()).isEqualTo(555L);
    assertThat(actualUser.getUsername()).isEqualTo("testuser");
    assertThat(actualUser.getDisplayName()).isEqualTo("Test User");
  }

  @Test
  void setStaticSessionService_shouldPersistAcrossMultipleSessionCalls() {
    // Given: A SessionService is set
    UserV2 user = new UserV2();
    user.setId(666L);
    when(sessionService.getSession()).thenReturn(user);
    UtilityFunctionsMapper.setStaticSessionService(sessionService);

    // When: session() is called multiple times
    UserV2 firstCall = UtilityFunctionsMapper.session();
    UserV2 secondCall = UtilityFunctionsMapper.session();
    UserV2 thirdCall = UtilityFunctionsMapper.session();

    // Then: All calls should use the same SessionService
    assertThat(firstCall).isSameAs(user);
    assertThat(secondCall).isSameAs(user);
    assertThat(thirdCall).isSameAs(user);
    verify(sessionService, times(3)).getSession();
  }

  @Test
  void setStaticSessionService_shouldWorkInConjunctionWithConstructor() {
    // Given: A SessionService
    UserV2 user = new UserV2();
    user.setId(777L);
    when(sessionService.getSession()).thenReturn(user);

    // When: UtilityFunctionsMapper constructor is called (which calls setStaticSessionService internally)
    new UtilityFunctionsMapper(sessionService, null, null);

    // Then: The session() method should work with the service set by the constructor
    UserV2 actualUser = UtilityFunctionsMapper.session();
    assertThat(actualUser).isSameAs(user);
    verify(sessionService).getSession();
  }

  @Test
  void setStaticSessionService_afterConstructor_shouldOverrideConstructorSetService() {
    // Given: UtilityFunctionsMapper constructor is called with first service
    UserV2 firstUser = new UserV2();
    firstUser.setId(888L);
    when(sessionService.getSession()).thenReturn(firstUser);
    new UtilityFunctionsMapper(sessionService, null, null);

    // When: setStaticSessionService is called with a different service
    UserV2 secondUser = new UserV2();
    secondUser.setId(999L);
    when(anotherSessionService.getSession()).thenReturn(secondUser);
    UtilityFunctionsMapper.setStaticSessionService(anotherSessionService);

    // Then: The latest service should be used
    UserV2 actualUser = UtilityFunctionsMapper.session();
    assertThat(actualUser).isSameAs(secondUser);
    assertThat(actualUser.getId()).isEqualTo(999L);
  }
}
