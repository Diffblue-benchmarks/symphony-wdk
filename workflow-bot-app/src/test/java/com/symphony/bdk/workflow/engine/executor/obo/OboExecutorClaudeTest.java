package com.symphony.bdk.workflow.engine.executor.obo;

import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.OboActivity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

class OboExecutorClaudeTest {

  private TestOboExecutor executor;
  private ActivityExecutorContext<TestOboActivity> context;
  private TestOboActivity activity;
  private BdkGateway bdkGateway;
  private AuthSession authSession;

  @BeforeEach
  void setUp() {
    executor = new TestOboExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new TestOboActivity();
    bdkGateway = mock(BdkGateway.class);
    authSession = mock(AuthSession.class);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    TestOboExecutor newExecutor = new TestOboExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    TestOboExecutor newExecutor = new TestOboExecutor();

    // Then: Instance should be of correct type
    assertThat(newExecutor).isInstanceOf(TestOboExecutor.class);
    assertThat(newExecutor).isInstanceOf(OboExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    TestOboExecutor executor1 = new TestOboExecutor();
    TestOboExecutor executor2 = new TestOboExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new TestOboExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for isObo method - returns false cases

  @Test
  void isObo_withNullObo_shouldReturnFalse() {
    // Given: Activity with null OBO
    activity.setObo(null);

    // When: isObo is called
    boolean result = executor.isObo(activity);

    // Then: Should return false
    assertThat(result).isFalse();
  }

  @Test
  void isObo_withEmptyObo_shouldReturnFalse() {
    // Given: Activity with empty OBO (both username and userId are null)
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(null);
    activity.setObo(obo);

    // When: isObo is called
    boolean result = executor.isObo(activity);

    // Then: Should return false
    assertThat(result).isFalse();
  }

  @Test
  void isObo_withOnlyUsernameNull_shouldReturnFalse() {
    // Given: Activity with OBO having only username as null
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(null);
    activity.setObo(obo);

    // When: isObo is called
    boolean result = executor.isObo(activity);

    // Then: Should return false
    assertThat(result).isFalse();
  }

  // Tests for isObo method - returns true cases

  @Test
  void isObo_withUsername_shouldReturnTrue() {
    // Given: Activity with OBO username
    Obo obo = new Obo();
    obo.setUsername("obo.user");
    obo.setUserId(null);
    activity.setObo(obo);

    // When: isObo is called
    boolean result = executor.isObo(activity);

    // Then: Should return true
    assertThat(result).isTrue();
  }

  @Test
  void isObo_withUserId_shouldReturnTrue() {
    // Given: Activity with OBO user ID
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(123456789L);
    activity.setObo(obo);

    // When: isObo is called
    boolean result = executor.isObo(activity);

    // Then: Should return true
    assertThat(result).isTrue();
  }

  @Test
  void isObo_withBothUsernameAndUserId_shouldReturnTrue() {
    // Given: Activity with both OBO username and user ID
    Obo obo = new Obo();
    obo.setUsername("obo.user");
    obo.setUserId(123456789L);
    activity.setObo(obo);

    // When: isObo is called
    boolean result = executor.isObo(activity);

    // Then: Should return true
    assertThat(result).isTrue();
  }

  @Test
  void isObo_withDifferentUsernames_shouldReturnTrue() {
    // Given: Different usernames to test
    String[] usernames = {"user1", "user.name", "obo_user", "test@example.com"};

    for (String username : usernames) {
      Obo obo = new Obo();
      obo.setUsername(username);
      activity.setObo(obo);

      // When: isObo is called
      boolean result = executor.isObo(activity);

      // Then: Should return true for all usernames
      assertThat(result).isTrue();
    }
  }

  @Test
  void isObo_withDifferentUserIds_shouldReturnTrue() {
    // Given: Different user IDs to test
    Long[] userIds = {1L, 123456789L, 9223372036854775807L}; // Including max long value

    for (Long userId : userIds) {
      Obo obo = new Obo();
      obo.setUserId(userId);
      activity.setObo(obo);

      // When: isObo is called
      boolean result = executor.isObo(activity);

      // Then: Should return true for all user IDs
      assertThat(result).isTrue();
    }
  }

  @Test
  void isObo_withEmptyStringUsername_shouldReturnTrue() {
    // Given: Activity with empty string username (not null)
    Obo obo = new Obo();
    obo.setUsername("");
    activity.setObo(obo);

    // When: isObo is called
    boolean result = executor.isObo(activity);

    // Then: Should return true (empty string is not null)
    assertThat(result).isTrue();
  }

  @Test
  void isObo_calledMultipleTimes_shouldReturnConsistentResults() {
    // Given: Activity with OBO username
    Obo obo = new Obo();
    obo.setUsername("obo.user");
    activity.setObo(obo);

    // When: isObo is called multiple times
    boolean result1 = executor.isObo(activity);
    boolean result2 = executor.isObo(activity);
    boolean result3 = executor.isObo(activity);

    // Then: Should return consistent results
    assertThat(result1).isTrue();
    assertThat(result2).isTrue();
    assertThat(result3).isTrue();
  }

  // Tests for getOboAuthSession method - with username

  @Test
  void getOboAuthSession_withUsername_shouldReturnAuthSession() {
    // Given: Activity with OBO username
    String username = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(username);
    activity.setObo(obo);

    when(bdkGateway.obo(username)).thenReturn(authSession);

    // When: getOboAuthSession is called
    AuthSession result = executor.getOboAuthSession(context);

    // Then: Should return the auth session
    assertThat(result).isEqualTo(authSession);
    verify(bdkGateway).obo(username);
  }

  @Test
  void getOboAuthSession_withUsername_shouldCallBdkGatewayWithCorrectUsername() {
    // Given: Activity with OBO username
    String username = "test.user";
    Obo obo = new Obo();
    obo.setUsername(username);
    activity.setObo(obo);

    when(bdkGateway.obo(username)).thenReturn(authSession);

    // When: getOboAuthSession is called
    executor.getOboAuthSession(context);

    // Then: Should call bdkGateway.obo with the correct username
    verify(bdkGateway).obo(username);
    verify(bdkGateway, times(0)).obo((Long) null);
  }

  @Test
  void getOboAuthSession_withDifferentUsernames_shouldUseCorrectUsername() {
    // Given: Different usernames to test
    String[] usernames = {"user1", "user.name", "obo_user"};

    for (String username : usernames) {
      Obo obo = new Obo();
      obo.setUsername(username);
      activity.setObo(obo);

      when(bdkGateway.obo(username)).thenReturn(authSession);

      // When: getOboAuthSession is called
      AuthSession result = executor.getOboAuthSession(context);

      // Then: Should use the correct username
      assertThat(result).isEqualTo(authSession);
      verify(bdkGateway).obo(username);
    }
  }

  // Tests for getOboAuthSession method - with userId

  @Test
  void getOboAuthSession_withUserId_shouldReturnAuthSession() {
    // Given: Activity with OBO user ID (no username)
    Long userId = 123456789L;
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(userId);
    activity.setObo(obo);

    when(bdkGateway.obo(userId)).thenReturn(authSession);

    // When: getOboAuthSession is called
    AuthSession result = executor.getOboAuthSession(context);

    // Then: Should return the auth session
    assertThat(result).isEqualTo(authSession);
    verify(bdkGateway).obo(userId);
  }

  @Test
  void getOboAuthSession_withUserId_shouldCallBdkGatewayWithCorrectUserId() {
    // Given: Activity with OBO user ID
    Long userId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(userId);
    activity.setObo(obo);

    when(bdkGateway.obo(userId)).thenReturn(authSession);

    // When: getOboAuthSession is called
    executor.getOboAuthSession(context);

    // Then: Should call bdkGateway.obo with the correct user ID
    verify(bdkGateway).obo(userId);
    verify(bdkGateway, times(0)).obo((String) null);
  }

  @Test
  void getOboAuthSession_withDifferentUserIds_shouldUseCorrectUserId() {
    // Given: Different user IDs to test
    Long[] userIds = {1L, 123456789L, 9223372036854775807L}; // Including max long value

    for (Long userId : userIds) {
      Obo obo = new Obo();
      obo.setUsername(null);
      obo.setUserId(userId);
      activity.setObo(obo);

      when(bdkGateway.obo(userId)).thenReturn(authSession);

      // When: getOboAuthSession is called
      AuthSession result = executor.getOboAuthSession(context);

      // Then: Should use the correct user ID
      assertThat(result).isEqualTo(authSession);
      verify(bdkGateway).obo(userId);
    }
  }

  // Tests for getOboAuthSession method - preference logic

  @Test
  void getOboAuthSession_withBothUsernameAndUserId_shouldPreferUsername() {
    // Given: Activity with both OBO username and user ID
    String username = "obo.user";
    Long userId = 123456789L;
    Obo obo = new Obo();
    obo.setUsername(username);
    obo.setUserId(userId);
    activity.setObo(obo);

    when(bdkGateway.obo(username)).thenReturn(authSession);

    // When: getOboAuthSession is called
    AuthSession result = executor.getOboAuthSession(context);

    // Then: Should prefer username over user ID
    assertThat(result).isEqualTo(authSession);
    verify(bdkGateway).obo(username);
    verify(bdkGateway, times(0)).obo(userId);
  }

  @Test
  void getOboAuthSession_withEmptyStringUsername_shouldUseUsername() {
    // Given: Activity with empty string username (not null) and user ID
    String username = "";
    Long userId = 123456789L;
    Obo obo = new Obo();
    obo.setUsername(username);
    obo.setUserId(userId);
    activity.setObo(obo);

    when(bdkGateway.obo(username)).thenReturn(authSession);

    // When: getOboAuthSession is called
    AuthSession result = executor.getOboAuthSession(context);

    // Then: Should use username (even if empty) over user ID
    assertThat(result).isEqualTo(authSession);
    verify(bdkGateway).obo(username);
    verify(bdkGateway, times(0)).obo(userId);
  }

  // Tests for getOboAuthSession method - multiple calls

  @Test
  void getOboAuthSession_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Activity with OBO username
    String username = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(username);
    activity.setObo(obo);

    when(bdkGateway.obo(username)).thenReturn(authSession);

    // When: getOboAuthSession is called multiple times
    AuthSession result1 = executor.getOboAuthSession(context);
    AuthSession result2 = executor.getOboAuthSession(context);

    // Then: Should work correctly both times
    assertThat(result1).isEqualTo(authSession);
    assertThat(result2).isEqualTo(authSession);
    verify(bdkGateway, times(2)).obo(username);
  }

  @Test
  void getOboAuthSession_withChangingActivity_shouldUseUpdatedValues() {
    // Given: Activity with OBO username
    String username1 = "user1";
    Obo obo1 = new Obo();
    obo1.setUsername(username1);
    activity.setObo(obo1);

    AuthSession authSession1 = mock(AuthSession.class);
    when(bdkGateway.obo(username1)).thenReturn(authSession1);

    // When: getOboAuthSession is called
    AuthSession result1 = executor.getOboAuthSession(context);

    // Then: Should use first username
    assertThat(result1).isEqualTo(authSession1);
    verify(bdkGateway).obo(username1);

    // Given: Change to different username
    String username2 = "user2";
    Obo obo2 = new Obo();
    obo2.setUsername(username2);
    activity.setObo(obo2);

    AuthSession authSession2 = mock(AuthSession.class);
    when(bdkGateway.obo(username2)).thenReturn(authSession2);

    // When: getOboAuthSession is called again
    AuthSession result2 = executor.getOboAuthSession(context);

    // Then: Should use second username
    assertThat(result2).isEqualTo(authSession2);
    verify(bdkGateway).obo(username2);
  }

  // Test concrete implementation of doOboWithCache

  @Test
  void doOboWithCache_shouldBeCallableAndReturnResult() {
    // Given: A test executor with doOboWithCache implemented
    TestOboExecutor testExecutor = new TestOboExecutor();

    // When: doOboWithCache is called
    String result = testExecutor.doOboWithCache(context);

    // Then: Should return the expected result
    assertThat(result).isEqualTo("test-result");
  }

  @Test
  void doOboWithCache_shouldHandleExceptions() {
    // Given: A test executor that throws exceptions
    TestOboExecutorWithException exceptionExecutor = new TestOboExecutorWithException();

    // When/Then: Calling doOboWithCache should throw the expected exception
    assertThatCode(() -> exceptionExecutor.doOboWithCache(context))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("Test exception");
  }

  // Helper classes for testing the abstract OboExecutor

  /**
   * Concrete implementation of OboExecutor for testing purposes.
   */
  private static class TestOboExecutor extends OboExecutor<TestOboActivity, String> {
    @Override
    protected String doOboWithCache(ActivityExecutorContext<TestOboActivity> execution) {
      return "test-result";
    }
  }

  /**
   * Concrete implementation that throws an exception for testing error handling.
   */
  private static class TestOboExecutorWithException extends OboExecutor<TestOboActivity, String> {
    @Override
    protected String doOboWithCache(ActivityExecutorContext<TestOboActivity> execution) throws Exception {
      throw new RuntimeException("Test exception");
    }
  }

  /**
   * Test implementation of OboActivity.
   */
  private static class TestOboActivity extends OboActivity {
    // No additional fields needed for testing
  }
}
