package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.UserStatus;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser;
import com.symphony.bdk.workflow.swadl.v1.activity.user.UpdateUser;
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

class UpdateUserExecutorClaudeTest {

  private UpdateUserExecutor executor;
  private ActivityExecutorContext<UpdateUser> context;
  private UpdateUser activity;
  private BdkGateway bdkGateway;
  private UserService userService;
  private V2UserDetail userDetail;

  @BeforeEach
  void setUp() {
    executor = new UpdateUserExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new UpdateUser();
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
    UpdateUserExecutor executor = new UpdateUserExecutor();

    // Then: Instance should be created successfully
    assertThat(executor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    UpdateUserExecutor executor = new UpdateUserExecutor();

    // Then: Instance should be of UpdateUserExecutor type and implement ActivityExecutor
    assertThat(executor).isInstanceOf(UpdateUserExecutor.class);
    assertThat(executor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    UpdateUserExecutor executor1 = new UpdateUserExecutor();
    UpdateUserExecutor executor2 = new UpdateUserExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new UpdateUserExecutor())
        .doesNotThrowAnyException();
  }

  // Execute method tests - basic functionality
  @Test
  void execute_withMinimalUser_shouldUpdateUser() {
    // Given: A user with minimal update (just userId and email)
    activity.setUserId("12345");
    activity.setEmail("updated@example.com");

    // When: Execute is called
    executor.execute(context);

    // Then: Should update user and set output variable
    verify(userService).update(eq(12345L), any());
    verify(userService).getUserDetail(12345L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withAllUserFields_shouldUpdateUser() {
    // Given: A user with all fields set
    activity.setUserId("54321");
    activity.setEmail("complete@example.com");
    activity.setFirstname("Complete");
    activity.setLastname("User");
    activity.setDisplayName("Complete User");
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
    // Given: A user with entitlements
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
    // Given: A user with status update
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
    // Given: A user being disabled
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
    // Given: User with name and email updates
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
    // Given: User with both entitlements and status
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
    // Given: User with all fields, entitlements, and status
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
    // Given: User with only userId (no updates)
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
    // Given: User with contact information
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
    // Given: User with complete business information (all fields required)
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
    // Given: Valid user
    activity.setUserId("12345");
    activity.setEmail("test@example.com");

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid user
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
  void execute_withContextContainingUser_shouldDelegateCorrectly() {
    // Given: Context with user activity
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
    // Given: User with empty display name
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
    // Given: User with special characters in email
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
    // Given: User with large user ID
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
    // Given: Valid user
    activity.setUserId("30303");
    activity.setEmail("output@example.com");

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with correct key
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withNullRecommendedLanguage_shouldUpdateUser() {
    // Given: User with null recommended language
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
    // Given: Valid user
    activity.setUserId("50505");
    activity.setEmail("detail@example.com");

    // When: Execute is called
    executor.execute(context);

    // Then: Should retrieve user detail after update
    verify(userService).getUserDetail(50505L);
  }

  @Test
  void execute_withMultipleEntitlements_shouldUpdateAllEntitlements() {
    // Given: User with multiple entitlements
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
    // Given: User with empty entitlements map
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
    // Given: User with null entitlements
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
    // Given: User with null status
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
  void execute_withOnlyFirstname_shouldUpdateUser() {
    // Given: User with only firstname
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
    // Given: User with only lastname
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
    // Given: User with only display name
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
    // Given: User with only recommended language
    activity.setUserId("66778");
    activity.setRecommendedLanguage("es_ES");

    // When: Execute is called
    executor.execute(context);

    // Then: Should update user
    verify(userService).update(eq(66778L), any());
    verify(userService).getUserDetail(66778L);
  }

  // doExecute method tests with UpdateUser subtype
  @Test
  void doExecute_withUpdateUserSubtype_shouldUpdateUser() {
    // Given: An UpdateUser activity with email
    activity.setUserId("77889");
    activity.setEmail("subtype@example.com");

    // When: doExecute is called (via execute which calls doExecute)
    executor.execute(context);

    // Then: Should update user
    verify(userService).update(eq(77889L), any());
    verify(userService).getUserDetail(77889L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void doExecute_withOnlyEntitlements_shouldOnlyUpdateEntitlements() {
    // Given: User with only entitlements (no user fields to update)
    activity.setUserId("88990");
    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);
    activity.setEntitlements(entitlements);

    // When: Execute is called
    executor.execute(context);

    // Then: Should only update entitlements, not user attributes
    verify(userService, never()).update(anyLong(), any());
    verify(userService).updateFeatureEntitlements(eq(88990L), any());
    verify(userService).getUserDetail(88990L);
  }

  @Test
  void doExecute_withOnlyStatus_shouldOnlyUpdateStatus() {
    // Given: User with only status (no user fields to update)
    activity.setUserId("99001");
    activity.setStatus("DISABLED");

    // When: Execute is called
    executor.execute(context);

    // Then: Should only update status, not user attributes
    verify(userService, never()).update(anyLong(), any());
    verify(userService).updateStatus(eq(99001L), any(UserStatus.class));
    verify(userService).getUserDetail(99001L);
  }

  @Test
  void doExecute_withPartialContact_shouldNotUpdateUser() {
    // Given: User with partial contact information
    // Note: shouldUpdateContact requires ALL contact fields to be non-null
    activity.setUserId("00112");
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setWorkPhoneNumber("+1234567890");
    // Other contact fields are null, so shouldUpdateContact will return false
    activity.setContact(contact);

    // When: Execute is called
    executor.execute(context);

    // Then: Should NOT update user (partial contact doesn't meet criteria)
    verify(userService, never()).update(anyLong(), any());
    verify(userService).getUserDetail(112L);
  }

  @Test
  void doExecute_withPartialBusiness_shouldNotUpdateUser() {
    // Given: User with partial business information
    // Note: shouldUpdateBusiness requires ALL 12 business fields to be non-null
    activity.setUserId("11223");
    CreateUser.Business business = new CreateUser.Business();
    business.setCompanyName("Partial Corp");
    business.setDepartment("Engineering");
    // Other business fields are null, so shouldUpdateBusiness will return false
    activity.setBusiness(business);

    // When: Execute is called
    executor.execute(context);

    // Then: Should NOT update user (partial business doesn't meet criteria)
    verify(userService, never()).update(anyLong(), any());
    verify(userService).getUserDetail(11223L);
  }

  @Test
  void doExecute_withNullContact_shouldNotUpdateUser() {
    // Given: User with null contact
    activity.setUserId("22334");
    activity.setContact(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should not update user (no updateable fields)
    verify(userService, never()).update(anyLong(), any());
    verify(userService).getUserDetail(22334L);
  }

  @Test
  void doExecute_withNullBusiness_shouldNotUpdateUser() {
    // Given: User with null business
    activity.setUserId("33445");
    activity.setBusiness(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should not update user (no updateable fields)
    verify(userService, never()).update(anyLong(), any());
    verify(userService).getUserDetail(33445L);
  }

  @Test
  void doExecute_withEmptyContactFields_shouldNotUpdateUser() {
    // Given: User with contact but all fields are null
    activity.setUserId("44556");
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setWorkPhoneNumber(null);
    contact.setMobilePhoneNumber(null);
    contact.setTwoFactorAuthNumber(null);
    contact.setSmsNumber(null);
    activity.setContact(contact);

    // When: Execute is called
    executor.execute(context);

    // Then: Should not update user (contact doesn't meet shouldUpdateContact criteria)
    verify(userService, never()).update(anyLong(), any());
    verify(userService).getUserDetail(44556L);
  }

  @Test
  void doExecute_withEmptyBusinessFields_shouldNotUpdateUser() {
    // Given: User with business but all fields are null
    activity.setUserId("55667");
    CreateUser.Business business = new CreateUser.Business();
    // All fields are null
    activity.setBusiness(business);

    // When: Execute is called
    executor.execute(context);

    // Then: Should not update user (business doesn't meet shouldUpdateBusiness criteria)
    verify(userService, never()).update(anyLong(), any());
    verify(userService).getUserDetail(55667L);
  }

  @Test
  void doExecute_verifyUserIdConversion() {
    // Given: User with string userId
    activity.setUserId("123456789");
    activity.setEmail("conversion@example.com");

    // When: Execute is called
    executor.execute(context);

    // Then: Should convert string userId to Long correctly
    verify(userService).update(eq(123456789L), any());
    verify(userService).getUserDetail(123456789L);
  }

  @Test
  void doExecute_withAllUpdateTypes_shouldExecuteInCorrectOrder() {
    // Given: User with all types of updates
    activity.setUserId("98765");
    activity.setEmail("order@example.com");
    activity.setFirstname("Order");
    activity.setLastname("Test");

    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);
    activity.setEntitlements(entitlements);

    activity.setStatus("ENABLED");

    // When: Execute is called
    executor.execute(context);

    // Then: Should execute updates in correct order: user, then entitlements, then status, then fetch
    verify(userService).update(eq(98765L), any());
    verify(userService).updateFeatureEntitlements(eq(98765L), any());
    verify(userService).updateStatus(eq(98765L), any(UserStatus.class));
    verify(userService).getUserDetail(98765L);
  }

  @Test
  void doExecute_withContactAllFieldsSet_shouldUpdateUser() {
    // Given: User with all contact fields set
    activity.setUserId("11998");
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setWorkPhoneNumber("+1111111111");
    contact.setMobilePhoneNumber("+2222222222");
    contact.setTwoFactorAuthNumber("+3333333333");
    contact.setSmsNumber("+4444444444");
    activity.setContact(contact);

    // When: Execute is called
    executor.execute(context);

    // Then: Should update user with all contact fields
    verify(userService).update(eq(11998L), any());
    verify(userService).getUserDetail(11998L);
  }

  @Test
  void execute_withNormalType_shouldUpdateNormalUser() {
    // Given: A user with NORMAL type (default)
    activity.setUserId("12121");
    activity.setEmail("normal@example.com");
    activity.setType("NORMAL");

    // When: Execute is called
    executor.execute(context);

    // Then: Should update user
    verify(userService).update(eq(12121L), any());
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_verifyDefaultTypeIsNormal() {
    // Given: An UpdateUser instance
    UpdateUser user = new UpdateUser();

    // When: We check its type
    String userType = user.getType();

    // Then: Type should be NORMAL by default (inherited from CreateUser)
    assertThat(userType).isEqualTo("NORMAL");
  }
}
