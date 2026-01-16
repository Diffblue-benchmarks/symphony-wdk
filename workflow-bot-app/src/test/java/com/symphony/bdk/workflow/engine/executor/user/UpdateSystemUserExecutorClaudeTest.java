package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.UserStatus;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser;
import com.symphony.bdk.workflow.swadl.v1.activity.user.UpdateSystemUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateSystemUserExecutorClaudeTest {

  private UpdateSystemUserExecutor executor;
  private ActivityExecutorContext<UpdateSystemUser> context;
  private UpdateSystemUser activity;
  private BdkGateway bdkGateway;
  private UserService userService;
  private V2UserDetail userDetail;

  @BeforeEach
  void setUp() {
    executor = new UpdateSystemUserExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new UpdateSystemUser();
    bdkGateway = mock(BdkGateway.class);
    userService = mock(UserService.class);
    userDetail = new V2UserDetail();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.users()).thenReturn(userService);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);
  }

  // Constructor tests
  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    UpdateSystemUserExecutor executor = new UpdateSystemUserExecutor();

    // Then: Instance should be created successfully
    assertThat(executor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    UpdateSystemUserExecutor executor = new UpdateSystemUserExecutor();

    // Then: Instance should be of UpdateSystemUserExecutor type and implement ActivityExecutor
    assertThat(executor).isInstanceOf(UpdateSystemUserExecutor.class);
    assertThat(executor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    UpdateSystemUserExecutor executor1 = new UpdateSystemUserExecutor();
    UpdateSystemUserExecutor executor2 = new UpdateSystemUserExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new UpdateSystemUserExecutor())
        .doesNotThrowAnyException();
  }

  // Execute method tests - basic functionality
  @Test
  void execute_withMinimalSystemUser_shouldUpdateUser() {
    // Given: A system user with minimal update (just userId and email)
    activity.setUserId("12345");
    activity.setEmail("updated.system@example.com");

    // When: Execute is called
    executor.execute(context);

    // Then: Should update user and set output variable
    verify(userService).update(eq(12345L), any());
    verify(userService).getUserDetail(12345L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withAllUserFields_shouldUpdateUser() {
    // Given: A system user with all fields set
    activity.setUserId("54321");
    activity.setEmail("complete.system@example.com");
    activity.setFirstname("Complete");
    activity.setLastname("System");
    activity.setDisplayName("Complete System User");
    activity.setRecommendedLanguage("en_US");

    // When: Execute is called
    executor.execute(context);

    // Then: Should update user with all fields
    verify(userService).update(eq(54321L), any());
    verify(userService).getUserDetail(54321L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withEntitlements_shouldUpdateEntitlements() {
    // Given: A system user with entitlements
    activity.setUserId("11111");
    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);
    entitlements.put("isExternalIMEnabled", false);
    activity.setEntitlements(entitlements);

    // When: Execute is called
    executor.execute(context);

    // Then: Should update entitlements
    verify(userService).updateFeatureEntitlements(eq(11111L), any());
    verify(userService).getUserDetail(11111L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withStatus_shouldUpdateStatus() {
    // Given: A system user with status update
    activity.setUserId("22222");
    activity.setStatus("ENABLED");

    // When: Execute is called
    executor.execute(context);

    // Then: Should update status
    verify(userService).updateStatus(eq(22222L), any(UserStatus.class));
    verify(userService).getUserDetail(22222L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withDisabledStatus_shouldUpdateStatus() {
    // Given: A system user being disabled
    activity.setUserId("33333");
    activity.setStatus("DISABLED");

    // When: Execute is called
    executor.execute(context);

    // Then: Should update status to disabled
    verify(userService).updateStatus(eq(33333L), any(UserStatus.class));
    verify(userService).getUserDetail(33333L);
  }

  @Test
  void execute_withEmailFirstnameLastname_shouldUpdateUser() {
    // Given: System user with name and email updates
    activity.setUserId("44444");
    activity.setEmail("new.email@example.com");
    activity.setFirstname("NewFirst");
    activity.setLastname("NewLast");

    // When: Execute is called
    executor.execute(context);

    // Then: Should update user fields
    verify(userService).update(eq(44444L), any());
    verify(userService).getUserDetail(44444L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withEntitlementsAndStatus_shouldUpdateBoth() {
    // Given: System user with both entitlements and status
    activity.setUserId("55555");
    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);
    activity.setEntitlements(entitlements);
    activity.setStatus("ENABLED");

    // When: Execute is called
    executor.execute(context);

    // Then: Should update both entitlements and status
    verify(userService).updateFeatureEntitlements(eq(55555L), any());
    verify(userService).updateStatus(eq(55555L), any(UserStatus.class));
    verify(userService).getUserDetail(55555L);
  }

  @Test
  void execute_withAllFieldsAndEntitlementsAndStatus_shouldUpdateAll() {
    // Given: System user with all fields, entitlements, and status
    activity.setUserId("66666");
    activity.setEmail("all.fields@example.com");
    activity.setFirstname("All");
    activity.setLastname("Fields");
    activity.setDisplayName("All Fields User");
    activity.setRecommendedLanguage("fr_FR");

    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", false);
    entitlements.put("isExternalIMEnabled", true);
    activity.setEntitlements(entitlements);

    activity.setStatus("ENABLED");

    // When: Execute is called
    executor.execute(context);

    // Then: Should update user, entitlements, and status
    verify(userService).update(eq(66666L), any());
    verify(userService).updateFeatureEntitlements(eq(66666L), any());
    verify(userService).updateStatus(eq(66666L), any(UserStatus.class));
    verify(userService).getUserDetail(66666L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withOnlyUserId_shouldOnlyFetchUser() {
    // Given: System user with only userId (no updates)
    activity.setUserId("77777");

    // When: Execute is called
    executor.execute(context);

    // Then: Should only fetch user detail without updating
    verify(userService, never()).update(anyLong(), any());
    verify(userService, never()).updateFeatureEntitlements(anyLong(), any());
    verify(userService, never()).updateStatus(anyLong(), any());
    verify(userService).getUserDetail(77777L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withContact_shouldUpdateUser() {
    // Given: System user with contact information
    activity.setUserId("88888");
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setWorkPhoneNumber("+1234567890");
    contact.setMobilePhoneNumber("+0987654321");
    contact.setTwoFactorAuthNumber("+1111111111");
    contact.setSmsNumber("+2222222222");
    activity.setContact(contact);

    // When: Execute is called
    executor.execute(context);

    // Then: Should update user with contact info
    verify(userService).update(eq(88888L), any());
    verify(userService).getUserDetail(88888L);
  }

  @Test
  void execute_withBusiness_shouldUpdateUser() {
    // Given: System user with complete business information (all fields required)
    activity.setUserId("99999");
    CreateUser.Business business = new CreateUser.Business();
    business.setCompanyName("Acme Corp");
    business.setDepartment("IT");
    business.setDivision("Engineering");
    business.setTitle("Senior Engineer");
    business.setLocation("New York");
    business.setJobFunction("Development");
    business.setAssetClasses(Arrays.asList("Equity"));
    business.setIndustries(Arrays.asList("Technology"));
    business.setFunctions(Arrays.asList("Engineering"));
    business.setMarketCoverages(Arrays.asList("Americas"));
    business.setResponsibilities(Arrays.asList("Development"));
    business.setInstruments(Arrays.asList("Options"));
    activity.setBusiness(business);

    // When: Execute is called
    executor.execute(context);

    // Then: Should update user with business info
    verify(userService).update(eq(99999L), any());
    verify(userService).getUserDetail(99999L);
  }

  @Test
  void execute_shouldNotThrowException() {
    // Given: Valid system user
    activity.setUserId("12345");
    activity.setEmail("test@example.com");

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid system user
    activity.setUserId("11111");
    activity.setEmail("multi@example.com");

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(userService, times(2)).update(eq(11111L), any());
    verify(userService, times(2)).getUserDetail(11111L);
  }

  @Test
  void execute_verifySystemTypeIsSet() {
    // Given: An UpdateSystemUser instance
    UpdateSystemUser systemUser = new UpdateSystemUser();

    // When: We check its type
    String userType = systemUser.getType();

    // Then: Type should be SYSTEM
    assertThat(userType).isEqualTo("SYSTEM");
  }

  @Test
  void execute_withContextContainingSystemUser_shouldDelegateCorrectly() {
    // Given: Context with system user activity
    activity.setUserId("12345");
    activity.setEmail("delegate@example.com");

    // When: Execute is called
    executor.execute(context);

    // Then: Should retrieve activity from context and update user
    verify(context).getActivity();
    verify(context).bdk();
    verify(bdkGateway).users();
    verify(userService).update(eq(12345L), any());
  }

  @Test
  void execute_withEmptyDisplayName_shouldUpdateUser() {
    // Given: System user with empty display name
    activity.setUserId("10101");
    activity.setEmail("empty@example.com");
    activity.setDisplayName("");

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should update user
    verify(userService).update(eq(10101L), any());
  }

  @Test
  void execute_withSpecialCharactersInEmail_shouldHandleCorrectly() {
    // Given: System user with special characters in email
    activity.setUserId("20202");
    activity.setEmail("special+user@example.com");

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should update user
    verify(userService).update(eq(20202L), any());
  }

  @Test
  void execute_withLargeUserId_shouldHandleCorrectly() {
    // Given: System user with large user ID
    activity.setUserId("9999999999");
    activity.setEmail("large@example.com");

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should update user
    verify(userService).update(eq(9999999999L), any());
  }

  @Test
  void execute_ensuresOutputVariableIsSet() {
    // Given: Valid system user
    activity.setUserId("30303");
    activity.setEmail("output@example.com");

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with correct key
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withNullRecommendedLanguage_shouldUpdateUser() {
    // Given: System user with null recommended language
    activity.setUserId("40404");
    activity.setEmail("null.lang@example.com");
    activity.setRecommendedLanguage(null);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should update user
    verify(userService).update(eq(40404L), any());
  }

  @Test
  void execute_retrievesUserDetailAfterUpdate() {
    // Given: Valid system user
    activity.setUserId("50505");
    activity.setEmail("detail@example.com");

    // When: Execute is called
    executor.execute(context);

    // Then: Should retrieve user detail after update
    verify(userService).getUserDetail(50505L);
  }

  @Test
  void execute_withMultipleEntitlements_shouldUpdateAllEntitlements() {
    // Given: System user with multiple entitlements
    activity.setUserId("60606");
    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);
    entitlements.put("isExternalIMEnabled", false);
    entitlements.put("canUpdateAvatar", true);
    entitlements.put("isShareFileEnabled", false);
    activity.setEntitlements(entitlements);

    // When: Execute is called
    executor.execute(context);

    // Then: Should update all entitlements
    verify(userService).updateFeatureEntitlements(eq(60606L), any());
    verify(userService).getUserDetail(60606L);
  }

  @Test
  void execute_withEmptyEntitlementsMap_shouldNotUpdateEntitlements() {
    // Given: System user with empty entitlements map
    activity.setUserId("70707");
    activity.setEmail("empty.entitlements@example.com");
    activity.setEntitlements(new HashMap<>());

    // When: Execute is called
    executor.execute(context);

    // Then: Should not call updateFeatureEntitlements
    verify(userService, never()).updateFeatureEntitlements(anyLong(), any());
    verify(userService).update(eq(70707L), any());
    verify(userService).getUserDetail(70707L);
  }

  @Test
  void execute_withNullEntitlements_shouldNotUpdateEntitlements() {
    // Given: System user with null entitlements
    activity.setUserId("80808");
    activity.setEmail("null.entitlements@example.com");
    activity.setEntitlements(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should not call updateFeatureEntitlements
    verify(userService, never()).updateFeatureEntitlements(anyLong(), any());
    verify(userService).update(eq(80808L), any());
  }

  @Test
  void execute_withNullStatus_shouldNotUpdateStatus() {
    // Given: System user with null status
    activity.setUserId("90909");
    activity.setEmail("null.status@example.com");
    activity.setStatus(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should not call updateStatus
    verify(userService, never()).updateStatus(anyLong(), any());
    verify(userService).update(eq(90909L), any());
  }

  @Test
  void execute_delegatesToUpdateUserExecutor() {
    // Given: A system user with updates
    activity.setUserId("11223");
    activity.setEmail("delegate.test@example.com");
    activity.setFirstname("Delegate");
    activity.setLastname("Test");

    // When: Execute is called
    executor.execute(context);

    // Then: Should delegate to UpdateUserExecutor which calls the same services
    verify(userService).update(eq(11223L), any());
    verify(userService).getUserDetail(11223L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withOnlyFirstname_shouldUpdateUser() {
    // Given: System user with only firstname
    activity.setUserId("33445");
    activity.setFirstname("OnlyFirst");

    // When: Execute is called
    executor.execute(context);

    // Then: Should update user
    verify(userService).update(eq(33445L), any());
    verify(userService).getUserDetail(33445L);
  }

  @Test
  void execute_withOnlyLastname_shouldUpdateUser() {
    // Given: System user with only lastname
    activity.setUserId("44556");
    activity.setLastname("OnlyLast");

    // When: Execute is called
    executor.execute(context);

    // Then: Should update user
    verify(userService).update(eq(44556L), any());
    verify(userService).getUserDetail(44556L);
  }

  @Test
  void execute_withOnlyDisplayName_shouldUpdateUser() {
    // Given: System user with only display name
    activity.setUserId("55667");
    activity.setDisplayName("Only Display Name");

    // When: Execute is called
    executor.execute(context);

    // Then: Should update user
    verify(userService).update(eq(55667L), any());
    verify(userService).getUserDetail(55667L);
  }

  @Test
  void execute_withOnlyRecommendedLanguage_shouldUpdateUser() {
    // Given: System user with only recommended language
    activity.setUserId("66778");
    activity.setRecommendedLanguage("es_ES");

    // When: Execute is called
    executor.execute(context);

    // Then: Should update user
    verify(userService).update(eq(66778L), any());
    verify(userService).getUserDetail(66778L);
  }
}
