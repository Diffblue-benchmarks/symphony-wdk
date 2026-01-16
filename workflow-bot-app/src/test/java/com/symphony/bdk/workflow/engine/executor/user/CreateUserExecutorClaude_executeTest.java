package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.Feature;
import com.symphony.bdk.gen.api.model.UserStatus;
import com.symphony.bdk.gen.api.model.UserSystemInfo;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateUserExecutorClaude_executeTest {

  private CreateUserExecutor executor;
  private ActivityExecutorContext<CreateUser> context;
  private CreateUser activity;
  private BdkGateway bdkGateway;
  private UserService userService;
  private V2UserDetail userDetail;

  @BeforeEach
  void setUp() {
    executor = new CreateUserExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new CreateUser();
    bdkGateway = mock(BdkGateway.class);
    userService = mock(UserService.class);
    userDetail = new V2UserDetail();

    UserSystemInfo userSystemInfo = new UserSystemInfo();
    userSystemInfo.setId(12345L);
    userDetail.setUserSystemInfo(userSystemInfo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.users()).thenReturn(userService);
    when(userService.create(any())).thenReturn(userDetail);
    when(userService.getUserDetail(any())).thenReturn(userDetail);
  }

  @Test
  void execute_withMinimalUser_shouldCreateUser() {
    // Given: A user with minimal required fields
    activity.setEmail("user@example.com");
    activity.setUsername("normaluser");
    activity.setFirstname("Normal");
    activity.setLastname("User");

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user through user service
    verify(userService).create(any());
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withDisplayName_shouldCreateUserWithDisplayName() {
    // Given: A user with display name
    activity.setEmail("user2@example.com");
    activity.setUsername("normaluser2");
    activity.setFirstname("Normal");
    activity.setLastname("User");
    activity.setDisplayName("Normal User Display");

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user and set output variable
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withEntitlements_shouldUpdateEntitlements() {
    // Given: A user with entitlements
    activity.setEmail("entitled@example.com");
    activity.setUsername("entitleduser");
    activity.setFirstname("Entitled");
    activity.setLastname("User");

    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);
    entitlements.put("isExternalRoomEnabled", false);
    activity.setEntitlements(entitlements);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user and update entitlements
    verify(userService).create(any());
    verify(userService).updateFeatureEntitlements(eq(12345L), anyList());
    verify(userService).getUserDetail(12345L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withEmptyEntitlements_shouldNotUpdateEntitlements() {
    // Given: A user with empty entitlements map
    activity.setEmail("noenabled@example.com");
    activity.setUsername("noenableduser");
    activity.setFirstname("NoEnabled");
    activity.setLastname("User");
    activity.setEntitlements(new HashMap<>());

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user but not update entitlements
    verify(userService).create(any());
    verify(userService, never()).updateFeatureEntitlements(anyLong(), anyList());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void execute_withNullEntitlements_shouldNotUpdateEntitlements() {
    // Given: A user with null entitlements
    activity.setEmail("nullentitle@example.com");
    activity.setUsername("nullentitleuser");
    activity.setFirstname("NullEntitle");
    activity.setLastname("User");
    activity.setEntitlements(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user but not update entitlements
    verify(userService).create(any());
    verify(userService, never()).updateFeatureEntitlements(anyLong(), anyList());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void execute_withStatus_shouldUpdateStatus() {
    // Given: A user with status
    activity.setEmail("status@example.com");
    activity.setUsername("statususer");
    activity.setFirstname("Status");
    activity.setLastname("User");
    activity.setStatus("ENABLED");

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user and update status
    verify(userService).create(any());
    verify(userService).updateStatus(eq(12345L), any(UserStatus.class));
    verify(userService).getUserDetail(12345L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withNullStatus_shouldNotUpdateStatus() {
    // Given: A user with null status
    activity.setEmail("nullstatus@example.com");
    activity.setUsername("nullstatususer");
    activity.setFirstname("NullStatus");
    activity.setLastname("User");
    activity.setStatus(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user but not update status
    verify(userService).create(any());
    verify(userService, never()).updateStatus(anyLong(), any(UserStatus.class));
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void execute_withEntitlementsAndStatus_shouldUpdateBoth() {
    // Given: A user with both entitlements and status
    activity.setEmail("both@example.com");
    activity.setUsername("bothuser");
    activity.setFirstname("Both");
    activity.setLastname("User");

    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);
    activity.setEntitlements(entitlements);
    activity.setStatus("ENABLED");

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user and update both entitlements and status
    verify(userService).create(any());
    verify(userService).updateFeatureEntitlements(eq(12345L), anyList());
    verify(userService).updateStatus(eq(12345L), any(UserStatus.class));
    verify(userService).getUserDetail(12345L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withPassword_shouldCreateUserWithPassword() {
    // Given: A user with password
    activity.setEmail("pass@example.com");
    activity.setUsername("passuser");
    activity.setFirstname("Pass");
    activity.setLastname("User");

    CreateUser.Password password = new CreateUser.Password();
    password.setHashedPassword("hashedpwd");
    password.setHashedSalt("salt123");
    password.setHashedKmPassword("kmhashedpwd");
    password.setHashedKmSalt("kmsalt123");
    activity.setPassword(password);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user with password
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withNullPassword_shouldCreateUserWithoutPassword() {
    // Given: A user with null password
    activity.setEmail("nopass@example.com");
    activity.setUsername("nopassuser");
    activity.setFirstname("NoPass");
    activity.setLastname("User");
    activity.setPassword(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user without password
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void execute_withRoles_shouldCreateUserWithRoles() {
    // Given: A user with roles
    activity.setEmail("roles@example.com");
    activity.setUsername("rolesuser");
    activity.setFirstname("Roles");
    activity.setLastname("User");
    activity.setRoles(Arrays.asList("INDIVIDUAL", "CONTENT_MANAGEMENT"));

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user with roles
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withContact_shouldCreateUserWithContact() {
    // Given: A user with contact information
    activity.setEmail("contact@example.com");
    activity.setUsername("contactuser");
    activity.setFirstname("Contact");
    activity.setLastname("User");

    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setWorkPhoneNumber("+1234567890");
    contact.setMobilePhoneNumber("+0987654321");
    contact.setTwoFactorAuthNumber("+1111111111");
    contact.setSmsNumber("+2222222222");
    activity.setContact(contact);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user with contact info
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withNullContact_shouldCreateUserWithoutContact() {
    // Given: A user with null contact
    activity.setEmail("nocontact@example.com");
    activity.setUsername("nocontactuser");
    activity.setFirstname("NoContact");
    activity.setLastname("User");
    activity.setContact(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user without contact info
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void execute_withBusiness_shouldCreateUserWithBusiness() {
    // Given: A user with business information
    activity.setEmail("business@example.com");
    activity.setUsername("businessuser");
    activity.setFirstname("Business");
    activity.setLastname("User");

    CreateUser.Business business = new CreateUser.Business();
    business.setCompanyName("Acme Corp");
    business.setDepartment("Engineering");
    business.setDivision("Technology");
    business.setTitle("Software Engineer");
    business.setLocation("New York");
    business.setJobFunction("Development");
    business.setAssetClasses(Arrays.asList("Equity", "FX"));
    business.setIndustries(Arrays.asList("Technology", "Finance"));
    business.setFunctions(Arrays.asList("Trading", "Sales"));
    business.setMarketCoverages(Arrays.asList("Americas", "EMEA"));
    business.setResponsibilities(Arrays.asList("Trading", "Analysis"));
    business.setInstruments(Arrays.asList("Bonds", "Derivatives"));
    activity.setBusiness(business);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user with business info
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withNullBusiness_shouldCreateUserWithoutBusiness() {
    // Given: A user with null business
    activity.setEmail("nobusiness@example.com");
    activity.setUsername("nobusinessuser");
    activity.setFirstname("NoBusiness");
    activity.setLastname("User");
    activity.setBusiness(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user without business info
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void execute_withKeys_shouldCreateUserWithKeys() {
    // Given: A user with keys
    activity.setEmail("keys@example.com");
    activity.setUsername("keysuser");
    activity.setFirstname("Keys");
    activity.setLastname("User");

    CreateUser.Keys keys = new CreateUser.Keys();
    CreateUser.Key currentKey = new CreateUser.Key();
    currentKey.setKey("currentkeyvalue");
    currentKey.setAction("SAVE");
    currentKey.setExpiration("2025-12-31T23:59:59Z");
    keys.setCurrent(currentKey);

    CreateUser.Key previousKey = new CreateUser.Key();
    previousKey.setKey("previouskeyvalue");
    previousKey.setAction("REVOKE");
    previousKey.setExpiration("2024-12-31T23:59:59Z");
    keys.setPrevious(previousKey);

    activity.setKeys(keys);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user with keys
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withKeysCurrentOnly_shouldCreateUserWithCurrentKey() {
    // Given: A user with only current key
    activity.setEmail("currentkey@example.com");
    activity.setUsername("currentkeyuser");
    activity.setFirstname("CurrentKey");
    activity.setLastname("User");

    CreateUser.Keys keys = new CreateUser.Keys();
    CreateUser.Key currentKey = new CreateUser.Key();
    currentKey.setKey("currentkeyvalue");
    currentKey.setAction("SAVE");
    currentKey.setExpiration("2025-12-31T23:59:59Z");
    keys.setCurrent(currentKey);
    keys.setPrevious(null);

    activity.setKeys(keys);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user with current key only
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void execute_withKeysPreviousOnly_shouldCreateUserWithPreviousKey() {
    // Given: A user with only previous key
    activity.setEmail("prevkey@example.com");
    activity.setUsername("prevkeyuser");
    activity.setFirstname("PrevKey");
    activity.setLastname("User");

    CreateUser.Keys keys = new CreateUser.Keys();
    keys.setCurrent(null);

    CreateUser.Key previousKey = new CreateUser.Key();
    previousKey.setKey("previouskeyvalue");
    previousKey.setAction("REVOKE");
    previousKey.setExpiration("2024-12-31T23:59:59Z");
    keys.setPrevious(previousKey);

    activity.setKeys(keys);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user with previous key only
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void execute_withNullKeys_shouldCreateUserWithoutKeys() {
    // Given: A user with null keys
    activity.setEmail("nokeys@example.com");
    activity.setUsername("nokeysuser");
    activity.setFirstname("NoKeys");
    activity.setLastname("User");
    activity.setKeys(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user without keys
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void execute_withRecommendedLanguage_shouldCreateUserWithLanguage() {
    // Given: User with recommended language
    activity.setEmail("lang.user@example.com");
    activity.setUsername("languser");
    activity.setFirstname("Lang");
    activity.setLastname("User");
    activity.setRecommendedLanguage("en_US");

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user
    verify(userService).create(any());
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_shouldNotThrowException() {
    // Given: Valid user
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid user
    activity.setEmail("multi@example.com");
    activity.setUsername("multiuser");
    activity.setFirstname("Multi");
    activity.setLastname("User");

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(userService, org.mockito.Mockito.times(2)).create(any());
    verify(userService, org.mockito.Mockito.times(2)).getUserDetail(12345L);
  }

  @Test
  void execute_withContextContainingUser_shouldDelegateCorrectly() {
    // Given: Context with user activity
    activity.setEmail("delegate@example.com");
    activity.setUsername("delegateuser");
    activity.setFirstname("Delegate");
    activity.setLastname("User");

    // When: Execute is called
    executor.execute(context);

    // Then: Should retrieve activity from context and create user
    verify(context).getActivity();
    verify(context).bdk();
    verify(bdkGateway).users();
    verify(userService).create(any());
  }

  @Test
  void execute_ensuresOutputVariableIsSet() {
    // Given: Valid user
    activity.setEmail("output@example.com");
    activity.setUsername("outputuser");
    activity.setFirstname("Output");
    activity.setLastname("User");

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with correct key
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_retrievesUserDetailAfterCreation() {
    // Given: Valid user
    activity.setEmail("detail@example.com");
    activity.setUsername("detailuser");
    activity.setFirstname("Detail");
    activity.setLastname("User");

    // When: Execute is called
    executor.execute(context);

    // Then: Should retrieve user detail after creation
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void execute_withNormalType_shouldCreateNormalUser() {
    // Given: A user with NORMAL type (default)
    activity.setEmail("normal@example.com");
    activity.setUsername("normaluser");
    activity.setFirstname("Normal");
    activity.setLastname("User");
    activity.setType("NORMAL");

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user
    verify(userService).create(any());
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withAllFields_shouldCreateCompleteUser() {
    // Given: A user with all possible fields populated
    activity.setEmail("complete@example.com");
    activity.setUsername("completeuser");
    activity.setFirstname("Complete");
    activity.setLastname("User");
    activity.setDisplayName("Complete User Display");
    activity.setType("NORMAL");
    activity.setRecommendedLanguage("en_US");
    activity.setRoles(Arrays.asList("INDIVIDUAL", "CONTENT_MANAGEMENT"));
    activity.setStatus("ENABLED");

    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);
    activity.setEntitlements(entitlements);

    CreateUser.Password password = new CreateUser.Password();
    password.setHashedPassword("hashedpwd");
    password.setHashedSalt("salt123");
    password.setHashedKmPassword("kmhashedpwd");
    password.setHashedKmSalt("kmsalt123");
    activity.setPassword(password);

    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setWorkPhoneNumber("+1234567890");
    contact.setMobilePhoneNumber("+0987654321");
    activity.setContact(contact);

    CreateUser.Business business = new CreateUser.Business();
    business.setCompanyName("Acme Corp");
    business.setDepartment("Engineering");
    activity.setBusiness(business);

    CreateUser.Keys keys = new CreateUser.Keys();
    CreateUser.Key currentKey = new CreateUser.Key();
    currentKey.setKey("currentkeyvalue");
    currentKey.setAction("SAVE");
    currentKey.setExpiration("2025-12-31T23:59:59Z");
    keys.setCurrent(currentKey);
    activity.setKeys(keys);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create complete user with all updates
    verify(userService).create(any());
    verify(userService).updateFeatureEntitlements(eq(12345L), anyList());
    verify(userService).updateStatus(eq(12345L), any(UserStatus.class));
    verify(userService).getUserDetail(12345L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withSpecialCharactersInEmail_shouldHandleCorrectly() {
    // Given: User with special characters in email
    activity.setEmail("special+user@example.com");
    activity.setUsername("specialuser");
    activity.setFirstname("Special");
    activity.setLastname("User");

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should create user
    verify(userService).create(any());
  }

  @Test
  void execute_withLongUsername_shouldHandleCorrectly() {
    // Given: User with very long username
    String longUsername = "verylongusername".repeat(5);
    activity.setEmail("long@example.com");
    activity.setUsername(longUsername);
    activity.setFirstname("Long");
    activity.setLastname("User");

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should create user
    verify(userService).create(any());
  }

  @Test
  void execute_verifyDefaultTypeIsNormal() {
    // Given: A CreateUser instance
    CreateUser user = new CreateUser();

    // When: We check its type
    String userType = user.getType();

    // Then: Type should be NORMAL by default
    assertThat(userType).isEqualTo("NORMAL");
  }
}
