package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.gen.api.model.UserSystemInfo;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.GetUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetUserExecutorClaudeTest {

  private GetUserExecutor executor;
  private ActivityExecutorContext<GetUser> context;
  private GetUser activity;
  private BdkGateway bdkGateway;
  private UserService userService;
  private V2UserDetail userDetail;

  @BeforeEach
  void setUp() {
    executor = new GetUserExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new GetUser();
    bdkGateway = mock(BdkGateway.class);
    userService = mock(UserService.class);
    userDetail = new V2UserDetail();

    UserSystemInfo userSystemInfo = new UserSystemInfo();
    userSystemInfo.setId(12345L);
    userDetail.setUserSystemInfo(userSystemInfo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.users()).thenReturn(userService);
    when(userService.getUserDetail(12345L)).thenReturn(userDetail);
  }

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    GetUserExecutor newExecutor = new GetUserExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    GetUserExecutor newExecutor = new GetUserExecutor();

    // Then: Instance should be of GetUserExecutor type and implement ActivityExecutor
    assertThat(newExecutor).isInstanceOf(GetUserExecutor.class);
    assertThat(newExecutor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    GetUserExecutor executor1 = new GetUserExecutor();
    GetUserExecutor executor2 = new GetUserExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new GetUserExecutor())
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withValidUserId_shouldGetUserDetail() {
    // Given: A valid user ID
    activity.setUserId("12345");

    // When: Execute is called
    executor.execute(context);

    // Then: Should get user detail and set output variable
    verify(userService).getUserDetail(12345L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withStringUserId_shouldConvertToLong() {
    // Given: A user ID as string
    activity.setUserId("67890");
    when(userService.getUserDetail(67890L)).thenReturn(userDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should convert string to long and get user detail
    verify(userService).getUserDetail(67890L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withDifferentUserIds_shouldFetchCorrectUser() {
    // Given: Different user IDs
    activity.setUserId("11111");
    V2UserDetail user1 = new V2UserDetail();
    UserSystemInfo info1 = new UserSystemInfo();
    info1.setId(11111L);
    user1.setUserSystemInfo(info1);
    when(userService.getUserDetail(11111L)).thenReturn(user1);

    // When: Execute is called first time
    executor.execute(context);

    // Then: Should fetch first user
    verify(userService).getUserDetail(11111L);
    verify(context).setOutputVariable("user", user1);

    // Given: Second user ID
    activity.setUserId("22222");
    V2UserDetail user2 = new V2UserDetail();
    UserSystemInfo info2 = new UserSystemInfo();
    info2.setId(22222L);
    user2.setUserSystemInfo(info2);
    when(userService.getUserDetail(22222L)).thenReturn(user2);

    // When: Execute is called second time
    executor.execute(context);

    // Then: Should fetch second user
    verify(userService).getUserDetail(22222L);
    verify(context).setOutputVariable("user", user2);
  }

  @Test
  void execute_shouldNotThrowException() {
    // Given: Valid user ID
    activity.setUserId("12345");

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid user ID
    activity.setUserId("12345");

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(userService, org.mockito.Mockito.times(2)).getUserDetail(12345L);
    verify(context, org.mockito.Mockito.times(2)).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_shouldRetrieveActivityFromContext() {
    // Given: Valid user ID
    activity.setUserId("12345");

    // When: Execute is called
    executor.execute(context);

    // Then: Should retrieve activity from context
    verify(context).getActivity();
  }

  @Test
  void execute_shouldUseBdkGateway() {
    // Given: Valid user ID
    activity.setUserId("12345");

    // When: Execute is called
    executor.execute(context);

    // Then: Should use BDK gateway to access users service
    verify(context).bdk();
    verify(bdkGateway).users();
  }

  @Test
  void execute_shouldSetOutputVariableWithCorrectKey() {
    // Given: Valid user ID
    activity.setUserId("12345");

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with "user" key
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withLargeUserId_shouldHandleCorrectly() {
    // Given: A large user ID
    activity.setUserId("9999999999");
    V2UserDetail largeIdUser = new V2UserDetail();
    when(userService.getUserDetail(9999999999L)).thenReturn(largeIdUser);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should fetch user with large ID
    verify(userService).getUserDetail(9999999999L);
    verify(context).setOutputVariable("user", largeIdUser);
  }

  @Test
  void execute_withSmallUserId_shouldHandleCorrectly() {
    // Given: A small user ID
    activity.setUserId("1");
    V2UserDetail smallIdUser = new V2UserDetail();
    when(userService.getUserDetail(1L)).thenReturn(smallIdUser);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should fetch user with small ID
    verify(userService).getUserDetail(1L);
    verify(context).setOutputVariable("user", smallIdUser);
  }

  @Test
  void execute_withNullUserDetail_shouldStillSetOutputVariable() {
    // Given: User service returns null
    activity.setUserId("12345");
    when(userService.getUserDetail(12345L)).thenReturn(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should still set output variable with null value
    verify(context).setOutputVariable("user", null);
  }

  @Test
  void execute_ensuresCorrectExecutionFlow() {
    // Given: Valid user ID
    activity.setUserId("12345");

    // When: Execute is called
    executor.execute(context);

    // Then: Should follow correct execution flow: get activity -> get BDK -> get users -> get user detail -> set output
    verify(context).getActivity();
    verify(context).bdk();
    verify(bdkGateway).users();
    verify(userService).getUserDetail(12345L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withZeroUserId_shouldHandleCorrectly() {
    // Given: A zero user ID
    activity.setUserId("0");
    V2UserDetail zeroIdUser = new V2UserDetail();
    when(userService.getUserDetail(0L)).thenReturn(zeroIdUser);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should fetch user with zero ID
    verify(userService).getUserDetail(0L);
    verify(context).setOutputVariable("user", zeroIdUser);
  }

  @Test
  void execute_withPositiveUserId_shouldConvertCorrectly() {
    // Given: A positive user ID
    activity.setUserId("123456789");
    V2UserDetail positiveIdUser = new V2UserDetail();
    when(userService.getUserDetail(123456789L)).thenReturn(positiveIdUser);

    // When: Execute is called
    executor.execute(context);

    // Then: Should convert and use correct ID
    verify(userService).getUserDetail(123456789L);
    verify(context).setOutputVariable("user", positiveIdUser);
  }

  @Test
  void execute_withContextContainingGetUser_shouldDelegateCorrectly() {
    // Given: Context with GetUser activity
    activity.setUserId("12345");

    // When: Execute is called
    executor.execute(context);

    // Then: Should retrieve activity from context and get user details
    verify(context).getActivity();
    verify(context).bdk();
    verify(bdkGateway).users();
    verify(userService).getUserDetail(12345L);
  }
}
