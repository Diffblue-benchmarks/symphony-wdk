package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.UserStatus;
import com.symphony.bdk.gen.api.model.UserSystemInfo;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateSystemUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateUserExecutorClaude_doExecuteTest {

  private CreateUserExecutor executor;
  private ActivityExecutorContext<CreateUser> contextCreateUser;
  private ActivityExecutorContext<CreateSystemUser> contextCreateSystemUser;
  private CreateUser createUser;
  private CreateSystemUser createSystemUser;
  private BdkGateway bdkGateway;
  private UserService userService;
  private V2UserDetail userDetail;

  @BeforeEach
  void setUp() {
    executor = new CreateUserExecutor();
    contextCreateUser = mock(ActivityExecutorContext.class);
    contextCreateSystemUser = mock(ActivityExecutorContext.class);
    createUser = new CreateUser();
    createSystemUser = new CreateSystemUser();
    bdkGateway = mock(BdkGateway.class);
    userService = mock(UserService.class);
    userDetail = new V2UserDetail();

    UserSystemInfo userSystemInfo = new UserSystemInfo();
    userSystemInfo.setId(12345L);
    userDetail.setUserSystemInfo(userSystemInfo);

    when(contextCreateUser.getActivity()).thenReturn(createUser);
    when(contextCreateUser.bdk()).thenReturn(bdkGateway);
    when(contextCreateSystemUser.getActivity()).thenReturn(createSystemUser);
    when(contextCreateSystemUser.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.users()).thenReturn(userService);
    when(userService.create(any())).thenReturn(userDetail);
    when(userService.getUserDetail(any())).thenReturn(userDetail);
  }

  @Test
  void doExecute_withCreateUser_shouldCreateNormalUser() {
    // Given: A CreateUser instance with minimal fields
    createUser.setEmail("normal@example.com");
    createUser.setUsername("normaluser");
    createUser.setFirstname("Normal");
    createUser.setLastname("User");

    // When: doExecute is called
    executor.doExecute(contextCreateUser);

    // Then: Should create user and set output variable
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
    verify(contextCreateUser).setOutputVariable("user", userDetail);
  }

  @Test
  void doExecute_withCreateSystemUser_shouldCreateSystemUser() {
    // Given: A CreateSystemUser instance (subtype of CreateUser)
    createSystemUser.setEmail("system@example.com");
    createSystemUser.setUsername("systemuser");
    createSystemUser.setFirstname("System");
    createSystemUser.setLastname("User");

    // When: doExecute is called with CreateSystemUser context
    executor.doExecute(contextCreateSystemUser);

    // Then: Should create system user and set output variable
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
    verify(contextCreateSystemUser).setOutputVariable("user", userDetail);
  }

  @Test
  void doExecute_withCreateUserAndEntitlements_shouldUpdateEntitlements() {
    // Given: CreateUser with entitlements
    createUser.setEmail("entitled@example.com");
    createUser.setUsername("entitleduser");
    createUser.setFirstname("Entitled");
    createUser.setLastname("User");

    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);
    createUser.setEntitlements(entitlements);

    // When: doExecute is called
    executor.doExecute(contextCreateUser);

    // Then: Should update entitlements
    verify(userService).create(any());
    verify(userService).updateFeatureEntitlements(eq(12345L), anyList());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateSystemUserAndEntitlements_shouldUpdateEntitlements() {
    // Given: CreateSystemUser with entitlements
    createSystemUser.setEmail("system.entitled@example.com");
    createSystemUser.setUsername("systementitleduser");
    createSystemUser.setFirstname("SystemEntitled");
    createSystemUser.setLastname("User");

    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("isExternalRoomEnabled", false);
    createSystemUser.setEntitlements(entitlements);

    // When: doExecute is called with CreateSystemUser
    executor.doExecute(contextCreateSystemUser);

    // Then: Should update entitlements for system user
    verify(userService).create(any());
    verify(userService).updateFeatureEntitlements(eq(12345L), anyList());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateUserAndStatus_shouldUpdateStatus() {
    // Given: CreateUser with status
    createUser.setEmail("status@example.com");
    createUser.setUsername("statususer");
    createUser.setFirstname("Status");
    createUser.setLastname("User");
    createUser.setStatus("ENABLED");

    // When: doExecute is called
    executor.doExecute(contextCreateUser);

    // Then: Should update status
    verify(userService).create(any());
    verify(userService).updateStatus(eq(12345L), any(UserStatus.class));
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateSystemUserAndStatus_shouldUpdateStatus() {
    // Given: CreateSystemUser with status
    createSystemUser.setEmail("system.status@example.com");
    createSystemUser.setUsername("systemstatususer");
    createSystemUser.setFirstname("SystemStatus");
    createSystemUser.setLastname("User");
    createSystemUser.setStatus("DISABLED");

    // When: doExecute is called with CreateSystemUser
    executor.doExecute(contextCreateSystemUser);

    // Then: Should update status for system user
    verify(userService).create(any());
    verify(userService).updateStatus(eq(12345L), any(UserStatus.class));
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateUserWithoutEntitlements_shouldNotUpdateEntitlements() {
    // Given: CreateUser without entitlements
    createUser.setEmail("noentitle@example.com");
    createUser.setUsername("noentitleuser");
    createUser.setFirstname("NoEntitle");
    createUser.setLastname("User");
    createUser.setEntitlements(null);

    // When: doExecute is called
    executor.doExecute(contextCreateUser);

    // Then: Should not update entitlements
    verify(userService).create(any());
    verify(userService, never()).updateFeatureEntitlements(anyLong(), anyList());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateSystemUserWithoutEntitlements_shouldNotUpdateEntitlements() {
    // Given: CreateSystemUser without entitlements
    createSystemUser.setEmail("system.noentitle@example.com");
    createSystemUser.setUsername("systemnoentitleuser");
    createSystemUser.setFirstname("SystemNoEntitle");
    createSystemUser.setLastname("User");
    createSystemUser.setEntitlements(null);

    // When: doExecute is called with CreateSystemUser
    executor.doExecute(contextCreateSystemUser);

    // Then: Should not update entitlements for system user
    verify(userService).create(any());
    verify(userService, never()).updateFeatureEntitlements(anyLong(), anyList());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateUserWithoutStatus_shouldNotUpdateStatus() {
    // Given: CreateUser without status
    createUser.setEmail("nostatus@example.com");
    createUser.setUsername("nostatususer");
    createUser.setFirstname("NoStatus");
    createUser.setLastname("User");
    createUser.setStatus(null);

    // When: doExecute is called
    executor.doExecute(contextCreateUser);

    // Then: Should not update status
    verify(userService).create(any());
    verify(userService, never()).updateStatus(anyLong(), any(UserStatus.class));
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateSystemUserWithoutStatus_shouldNotUpdateStatus() {
    // Given: CreateSystemUser without status
    createSystemUser.setEmail("system.nostatus@example.com");
    createSystemUser.setUsername("systemnostatususer");
    createSystemUser.setFirstname("SystemNoStatus");
    createSystemUser.setLastname("User");
    createSystemUser.setStatus(null);

    // When: doExecute is called with CreateSystemUser
    executor.doExecute(contextCreateSystemUser);

    // Then: Should not update status for system user
    verify(userService).create(any());
    verify(userService, never()).updateStatus(anyLong(), any(UserStatus.class));
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateUserWithPassword_shouldCreateUserWithPassword() {
    // Given: CreateUser with password
    createUser.setEmail("password@example.com");
    createUser.setUsername("passworduser");
    createUser.setFirstname("Password");
    createUser.setLastname("User");

    CreateUser.Password password = new CreateUser.Password();
    password.setHashedPassword("hashedpwd");
    password.setHashedSalt("salt123");
    createUser.setPassword(password);

    // When: doExecute is called
    executor.doExecute(contextCreateUser);

    // Then: Should create user with password
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateSystemUserWithPassword_shouldCreateUserWithPassword() {
    // Given: CreateSystemUser with password
    createSystemUser.setEmail("system.password@example.com");
    createSystemUser.setUsername("systempassworduser");
    createSystemUser.setFirstname("SystemPassword");
    createSystemUser.setLastname("User");

    CreateUser.Password password = new CreateUser.Password();
    password.setHashedPassword("hashedpwd");
    password.setHashedSalt("salt123");
    createSystemUser.setPassword(password);

    // When: doExecute is called with CreateSystemUser
    executor.doExecute(contextCreateSystemUser);

    // Then: Should create system user with password
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateUserAndRoles_shouldCreateUserWithRoles() {
    // Given: CreateUser with roles
    createUser.setEmail("roles@example.com");
    createUser.setUsername("rolesuser");
    createUser.setFirstname("Roles");
    createUser.setLastname("User");
    createUser.setRoles(Arrays.asList("INDIVIDUAL", "CONTENT_MANAGEMENT"));

    // When: doExecute is called
    executor.doExecute(contextCreateUser);

    // Then: Should create user with roles
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateSystemUserAndRoles_shouldCreateUserWithRoles() {
    // Given: CreateSystemUser with roles
    createSystemUser.setEmail("system.roles@example.com");
    createSystemUser.setUsername("systemrolesuser");
    createSystemUser.setFirstname("SystemRoles");
    createSystemUser.setLastname("User");
    createSystemUser.setRoles(Arrays.asList("SUPER_ADMINISTRATOR"));

    // When: doExecute is called with CreateSystemUser
    executor.doExecute(contextCreateSystemUser);

    // Then: Should create system user with roles
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateUserAndContact_shouldCreateUserWithContact() {
    // Given: CreateUser with contact information
    createUser.setEmail("contact@example.com");
    createUser.setUsername("contactuser");
    createUser.setFirstname("Contact");
    createUser.setLastname("User");

    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setWorkPhoneNumber("+1234567890");
    contact.setMobilePhoneNumber("+0987654321");
    createUser.setContact(contact);

    // When: doExecute is called
    executor.doExecute(contextCreateUser);

    // Then: Should create user with contact
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateSystemUserAndContact_shouldCreateUserWithContact() {
    // Given: CreateSystemUser with contact information
    createSystemUser.setEmail("system.contact@example.com");
    createSystemUser.setUsername("systemcontactuser");
    createSystemUser.setFirstname("SystemContact");
    createSystemUser.setLastname("User");

    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setWorkPhoneNumber("+1111111111");
    createSystemUser.setContact(contact);

    // When: doExecute is called with CreateSystemUser
    executor.doExecute(contextCreateSystemUser);

    // Then: Should create system user with contact
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateUserAndBusiness_shouldCreateUserWithBusiness() {
    // Given: CreateUser with business information
    createUser.setEmail("business@example.com");
    createUser.setUsername("businessuser");
    createUser.setFirstname("Business");
    createUser.setLastname("User");

    CreateUser.Business business = new CreateUser.Business();
    business.setCompanyName("Acme Corp");
    business.setDepartment("Engineering");
    createUser.setBusiness(business);

    // When: doExecute is called
    executor.doExecute(contextCreateUser);

    // Then: Should create user with business info
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateSystemUserAndBusiness_shouldCreateUserWithBusiness() {
    // Given: CreateSystemUser with business information
    createSystemUser.setEmail("system.business@example.com");
    createSystemUser.setUsername("systembusinessuser");
    createSystemUser.setFirstname("SystemBusiness");
    createSystemUser.setLastname("User");

    CreateUser.Business business = new CreateUser.Business();
    business.setCompanyName("System Corp");
    business.setDepartment("IT");
    createSystemUser.setBusiness(business);

    // When: doExecute is called with CreateSystemUser
    executor.doExecute(contextCreateSystemUser);

    // Then: Should create system user with business info
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateUserAndKeys_shouldCreateUserWithKeys() {
    // Given: CreateUser with keys
    createUser.setEmail("keys@example.com");
    createUser.setUsername("keysuser");
    createUser.setFirstname("Keys");
    createUser.setLastname("User");

    CreateUser.Keys keys = new CreateUser.Keys();
    CreateUser.Key currentKey = new CreateUser.Key();
    currentKey.setKey("keyvalue");
    currentKey.setAction("SAVE");
    currentKey.setExpiration("2025-12-31T23:59:59Z");
    keys.setCurrent(currentKey);
    createUser.setKeys(keys);

    // When: doExecute is called
    executor.doExecute(contextCreateUser);

    // Then: Should create user with keys
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateSystemUserAndKeys_shouldCreateUserWithKeys() {
    // Given: CreateSystemUser with keys
    createSystemUser.setEmail("system.keys@example.com");
    createSystemUser.setUsername("systemkeysuser");
    createSystemUser.setFirstname("SystemKeys");
    createSystemUser.setLastname("User");

    CreateUser.Keys keys = new CreateUser.Keys();
    CreateUser.Key currentKey = new CreateUser.Key();
    currentKey.setKey("systemkeyvalue");
    currentKey.setAction("SAVE");
    currentKey.setExpiration("2025-12-31T23:59:59Z");
    keys.setCurrent(currentKey);
    createSystemUser.setKeys(keys);

    // When: doExecute is called with CreateSystemUser
    executor.doExecute(contextCreateSystemUser);

    // Then: Should create system user with keys
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateUserAndAllFields_shouldCreateCompleteUser() {
    // Given: CreateUser with all fields
    createUser.setEmail("complete@example.com");
    createUser.setUsername("completeuser");
    createUser.setFirstname("Complete");
    createUser.setLastname("User");
    createUser.setDisplayName("Complete User Display");
    createUser.setRecommendedLanguage("en_US");
    createUser.setStatus("ENABLED");

    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);
    createUser.setEntitlements(entitlements);

    CreateUser.Password password = new CreateUser.Password();
    password.setHashedPassword("hashedpwd");
    createUser.setPassword(password);

    createUser.setRoles(Arrays.asList("INDIVIDUAL"));

    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setWorkPhoneNumber("+1234567890");
    createUser.setContact(contact);

    CreateUser.Business business = new CreateUser.Business();
    business.setCompanyName("Acme Corp");
    createUser.setBusiness(business);

    // When: doExecute is called
    executor.doExecute(contextCreateUser);

    // Then: Should create complete user with all updates
    verify(userService).create(any());
    verify(userService).updateFeatureEntitlements(eq(12345L), anyList());
    verify(userService).updateStatus(eq(12345L), any(UserStatus.class));
    verify(userService).getUserDetail(12345L);
    verify(contextCreateUser).setOutputVariable("user", userDetail);
  }

  @Test
  void doExecute_withCreateSystemUserAndAllFields_shouldCreateCompleteSystemUser() {
    // Given: CreateSystemUser with all fields
    createSystemUser.setEmail("system.complete@example.com");
    createSystemUser.setUsername("systemcompleteuser");
    createSystemUser.setFirstname("SystemComplete");
    createSystemUser.setLastname("User");
    createSystemUser.setDisplayName("System Complete User Display");
    createSystemUser.setRecommendedLanguage("fr_FR");
    createSystemUser.setStatus("ENABLED");

    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("isExternalRoomEnabled", false);
    createSystemUser.setEntitlements(entitlements);

    CreateUser.Password password = new CreateUser.Password();
    password.setHashedPassword("systemhashedpwd");
    createSystemUser.setPassword(password);

    createSystemUser.setRoles(Arrays.asList("SUPER_ADMINISTRATOR"));

    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setWorkPhoneNumber("+9999999999");
    createSystemUser.setContact(contact);

    CreateUser.Business business = new CreateUser.Business();
    business.setCompanyName("System Corp");
    createSystemUser.setBusiness(business);

    // When: doExecute is called with CreateSystemUser
    executor.doExecute(contextCreateSystemUser);

    // Then: Should create complete system user with all updates
    verify(userService).create(any());
    verify(userService).updateFeatureEntitlements(eq(12345L), anyList());
    verify(userService).updateStatus(eq(12345L), any(UserStatus.class));
    verify(userService).getUserDetail(12345L);
    verify(contextCreateSystemUser).setOutputVariable("user", userDetail);
  }

  @Test
  void doExecute_withCreateUserShouldNotThrowException() {
    // Given: Valid CreateUser
    createUser.setEmail("test@example.com");
    createUser.setUsername("testuser");
    createUser.setFirstname("Test");
    createUser.setLastname("User");

    // When/Then: doExecute should not throw exception
    assertThatCode(() -> executor.doExecute(contextCreateUser))
        .doesNotThrowAnyException();
  }

  @Test
  void doExecute_withCreateSystemUserShouldNotThrowException() {
    // Given: Valid CreateSystemUser
    createSystemUser.setEmail("system.test@example.com");
    createSystemUser.setUsername("systemtestuser");
    createSystemUser.setFirstname("SystemTest");
    createSystemUser.setLastname("User");

    // When/Then: doExecute should not throw exception
    assertThatCode(() -> executor.doExecute(contextCreateSystemUser))
        .doesNotThrowAnyException();
  }

  @Test
  void doExecute_calledMultipleTimesWithCreateUser_shouldWorkCorrectly() {
    // Given: Valid CreateUser
    createUser.setEmail("multi@example.com");
    createUser.setUsername("multiuser");
    createUser.setFirstname("Multi");
    createUser.setLastname("User");

    // When: doExecute is called multiple times
    executor.doExecute(contextCreateUser);
    executor.doExecute(contextCreateUser);

    // Then: Should work correctly both times
    verify(userService, org.mockito.Mockito.times(2)).create(any());
    verify(userService, org.mockito.Mockito.times(2)).getUserDetail(12345L);
  }

  @Test
  void doExecute_calledMultipleTimesWithCreateSystemUser_shouldWorkCorrectly() {
    // Given: Valid CreateSystemUser
    createSystemUser.setEmail("system.multi@example.com");
    createSystemUser.setUsername("systemmultiuser");
    createSystemUser.setFirstname("SystemMulti");
    createSystemUser.setLastname("User");

    // When: doExecute is called multiple times with CreateSystemUser
    executor.doExecute(contextCreateSystemUser);
    executor.doExecute(contextCreateSystemUser);

    // Then: Should work correctly both times
    verify(userService, org.mockito.Mockito.times(2)).create(any());
    verify(userService, org.mockito.Mockito.times(2)).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateUserAndEmptyEntitlements_shouldNotUpdateEntitlements() {
    // Given: CreateUser with empty entitlements map
    createUser.setEmail("emptyentitle@example.com");
    createUser.setUsername("emptyentitleuser");
    createUser.setFirstname("EmptyEntitle");
    createUser.setLastname("User");
    createUser.setEntitlements(new HashMap<>());

    // When: doExecute is called
    executor.doExecute(contextCreateUser);

    // Then: Should not update entitlements
    verify(userService).create(any());
    verify(userService, never()).updateFeatureEntitlements(anyLong(), anyList());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withCreateSystemUserAndEmptyEntitlements_shouldNotUpdateEntitlements() {
    // Given: CreateSystemUser with empty entitlements map
    createSystemUser.setEmail("system.emptyentitle@example.com");
    createSystemUser.setUsername("systememptyentitleuser");
    createSystemUser.setFirstname("SystemEmptyEntitle");
    createSystemUser.setLastname("User");
    createSystemUser.setEntitlements(new HashMap<>());

    // When: doExecute is called with CreateSystemUser
    executor.doExecute(contextCreateSystemUser);

    // Then: Should not update entitlements
    verify(userService).create(any());
    verify(userService, never()).updateFeatureEntitlements(anyLong(), anyList());
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_ensuresOutputVariableIsSetForCreateUser() {
    // Given: Valid CreateUser
    createUser.setEmail("output@example.com");
    createUser.setUsername("outputuser");
    createUser.setFirstname("Output");
    createUser.setLastname("User");

    // When: doExecute is called
    executor.doExecute(contextCreateUser);

    // Then: Should set output variable with correct key
    verify(contextCreateUser).setOutputVariable("user", userDetail);
  }

  @Test
  void doExecute_ensuresOutputVariableIsSetForCreateSystemUser() {
    // Given: Valid CreateSystemUser
    createSystemUser.setEmail("system.output@example.com");
    createSystemUser.setUsername("systemoutputuser");
    createSystemUser.setFirstname("SystemOutput");
    createSystemUser.setLastname("User");

    // When: doExecute is called with CreateSystemUser
    executor.doExecute(contextCreateSystemUser);

    // Then: Should set output variable with correct key
    verify(contextCreateSystemUser).setOutputVariable("user", userDetail);
  }

  @Test
  void doExecute_retrievesUserDetailAfterCreationForCreateUser() {
    // Given: Valid CreateUser
    createUser.setEmail("detail@example.com");
    createUser.setUsername("detailuser");
    createUser.setFirstname("Detail");
    createUser.setLastname("User");

    // When: doExecute is called
    executor.doExecute(contextCreateUser);

    // Then: Should retrieve user detail after creation
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_retrievesUserDetailAfterCreationForCreateSystemUser() {
    // Given: Valid CreateSystemUser
    createSystemUser.setEmail("system.detail@example.com");
    createSystemUser.setUsername("systemdetailuser");
    createSystemUser.setFirstname("SystemDetail");
    createSystemUser.setLastname("User");

    // When: doExecute is called with CreateSystemUser
    executor.doExecute(contextCreateSystemUser);

    // Then: Should retrieve user detail after creation
    verify(userService).getUserDetail(12345L);
  }

  @Test
  void doExecute_withContextContainingCreateUser_shouldDelegateCorrectly() {
    // Given: Context with CreateUser activity
    createUser.setEmail("delegate@example.com");
    createUser.setUsername("delegateuser");
    createUser.setFirstname("Delegate");
    createUser.setLastname("User");

    // When: doExecute is called
    executor.doExecute(contextCreateUser);

    // Then: Should retrieve activity from context and create user
    verify(contextCreateUser).getActivity();
    verify(contextCreateUser).bdk();
    verify(bdkGateway).users();
    verify(userService).create(any());
  }

  @Test
  void doExecute_withContextContainingCreateSystemUser_shouldDelegateCorrectly() {
    // Given: Context with CreateSystemUser activity
    createSystemUser.setEmail("system.delegate@example.com");
    createSystemUser.setUsername("systemdelegateuser");
    createSystemUser.setFirstname("SystemDelegate");
    createSystemUser.setLastname("User");

    // When: doExecute is called with CreateSystemUser
    executor.doExecute(contextCreateSystemUser);

    // Then: Should retrieve activity from context and create system user
    verify(contextCreateSystemUser).getActivity();
    verify(contextCreateSystemUser).bdk();
    verify(bdkGateway).users();
    verify(userService).create(any());
  }
}
