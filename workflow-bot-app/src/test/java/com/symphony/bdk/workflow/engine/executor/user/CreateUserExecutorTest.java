package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.Feature;
import com.symphony.bdk.gen.api.model.UserStatus;
import com.symphony.bdk.gen.api.model.V2UserAttributes;
import com.symphony.bdk.gen.api.model.V2UserCreate;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateUserExecutorTest {

  private CreateUserExecutor executor;
  private ActivityExecutorContext<CreateUser> context;
  private BdkGateway bdkGateway;
  private UserService userService;
  private CreateUser activity;

  @BeforeEach
  void setUp() {
    executor = new CreateUserExecutor();
    context = mock(ActivityExecutorContext.class);
    bdkGateway = mock(BdkGateway.class);
    userService = mock(UserService.class);
    activity = new CreateUser();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.users()).thenReturn(userService);
  }

  @Test
  void shouldExecuteWithMinimalFields() {
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");

    V2UserDetail createdUser = createUserDetail(12345L);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(12345L)).thenReturn(createdUser);

    executor.execute(context);

    verify(userService).create(any(V2UserCreate.class));
    verify(context).setOutputVariable("user", createdUser);
  }

  @Test
  void shouldExecuteWithPassword() {
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");

    CreateUser.Password password = new CreateUser.Password();
    password.setHashedPassword("hashed123");
    password.setHashedSalt("salt123");
    password.setHashedKmPassword("kmhashed123");
    password.setHashedKmSalt("kmsalt123");
    activity.setPassword(password);

    V2UserDetail createdUser = createUserDetail(12346L);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(12346L)).thenReturn(createdUser);

    executor.execute(context);

    verify(userService).create(any(V2UserCreate.class));
    verify(context).setOutputVariable("user", createdUser);
  }

  @Test
  void shouldExecuteWithEntitlements() {
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");

    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreateMessage", true);
    entitlements.put("canCreateRoom", false);
    activity.setEntitlements(entitlements);

    V2UserDetail createdUser = createUserDetail(12347L);
    V2UserDetail updatedUser = createUserDetail(12347L);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(12347L)).thenReturn(updatedUser);

    executor.execute(context);

    verify(userService).updateFeatureEntitlements(eq(12347L), any(List.class));
    verify(context).setOutputVariable("user", updatedUser);
  }

  @Test
  void shouldExecuteWithStatus() {
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");
    activity.setStatus("ENABLED");

    V2UserDetail createdUser = createUserDetail(12348L);
    V2UserDetail updatedUser = createUserDetail(12348L);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(12348L)).thenReturn(updatedUser);

    executor.execute(context);

    verify(userService).updateStatus(eq(12348L), any(UserStatus.class));
    verify(context).setOutputVariable("user", updatedUser);
  }

  @Test
  void shouldExecuteWithContactInfo() {
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");

    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setWorkPhoneNumber("+1234567890");
    contact.setMobilePhoneNumber("+0987654321");
    contact.setTwoFactorAuthNumber("+1111111111");
    contact.setSmsNumber("+2222222222");
    activity.setContact(contact);

    V2UserDetail createdUser = createUserDetail(12349L);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(12349L)).thenReturn(createdUser);

    executor.execute(context);

    verify(userService).create(any(V2UserCreate.class));
    verify(context).setOutputVariable("user", createdUser);
  }

  @Test
  void shouldExecuteWithBusinessInfo() {
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");

    CreateUser.Business business = new CreateUser.Business();
    business.setCompanyName("Test Corp");
    business.setDepartment("Engineering");
    business.setDivision("Tech");
    business.setTitle("Developer");
    business.setLocation("New York");
    business.setJobFunction("Software Development");

    List<String> assetClasses = new ArrayList<>();
    assetClasses.add("Equity");
    business.setAssetClasses(assetClasses);

    List<String> industries = new ArrayList<>();
    industries.add("Technology");
    business.setIndustries(industries);

    List<String> functions = new ArrayList<>();
    functions.add("Engineering");
    business.setFunctions(functions);

    List<String> marketCoverages = new ArrayList<>();
    marketCoverages.add("Americas");
    business.setMarketCoverages(marketCoverages);

    List<String> responsibilities = new ArrayList<>();
    responsibilities.add("Development");
    business.setResponsibilities(responsibilities);

    List<String> instruments = new ArrayList<>();
    instruments.add("Stocks");
    business.setInstruments(instruments);

    activity.setBusiness(business);

    V2UserDetail createdUser = createUserDetail(12350L);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(12350L)).thenReturn(createdUser);

    executor.execute(context);

    verify(userService).create(any(V2UserCreate.class));
    verify(context).setOutputVariable("user", createdUser);
  }

  @Test
  void shouldExecuteWithCurrentKey() {
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");

    CreateUser.Keys keys = new CreateUser.Keys();
    CreateUser.Key currentKey = new CreateUser.Key();
    currentKey.setKey("currentKeyValue");
    currentKey.setAction("SAVE");
    currentKey.setExpiration("2025-12-31T23:59:59Z");
    keys.setCurrent(currentKey);
    activity.setKeys(keys);

    V2UserDetail createdUser = createUserDetail(12351L);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(12351L)).thenReturn(createdUser);

    executor.execute(context);

    verify(userService).create(any(V2UserCreate.class));
    verify(context).setOutputVariable("user", createdUser);
  }

  @Test
  void shouldExecuteWithPreviousKey() {
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");

    CreateUser.Keys keys = new CreateUser.Keys();
    CreateUser.Key previousKey = new CreateUser.Key();
    previousKey.setKey("previousKeyValue");
    previousKey.setAction("REVOKE");
    previousKey.setExpiration("2024-12-31T23:59:59Z");
    keys.setPrevious(previousKey);
    activity.setKeys(keys);

    V2UserDetail createdUser = createUserDetail(12352L);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(12352L)).thenReturn(createdUser);

    executor.execute(context);

    verify(userService).create(any(V2UserCreate.class));
    verify(context).setOutputVariable("user", createdUser);
  }

  @Test
  void shouldExecuteWithBothKeys() {
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");

    CreateUser.Keys keys = new CreateUser.Keys();

    CreateUser.Key currentKey = new CreateUser.Key();
    currentKey.setKey("currentKeyValue");
    currentKey.setAction("SAVE");
    currentKey.setExpiration("2025-12-31T23:59:59Z");
    keys.setCurrent(currentKey);

    CreateUser.Key previousKey = new CreateUser.Key();
    previousKey.setKey("previousKeyValue");
    previousKey.setAction("REVOKE");
    previousKey.setExpiration("2024-12-31T23:59:59Z");
    keys.setPrevious(previousKey);

    activity.setKeys(keys);

    V2UserDetail createdUser = createUserDetail(12353L);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(12353L)).thenReturn(createdUser);

    executor.execute(context);

    verify(userService).create(any(V2UserCreate.class));
    verify(context).setOutputVariable("user", createdUser);
  }

  @Test
  void shouldExecuteWithRoles() {
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");

    List<String> roles = new ArrayList<>();
    roles.add("ADMIN");
    roles.add("USER");
    activity.setRoles(roles);

    V2UserDetail createdUser = createUserDetail(12354L);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(12354L)).thenReturn(createdUser);

    executor.execute(context);

    verify(userService).create(any(V2UserCreate.class));
    verify(context).setOutputVariable("user", createdUser);
  }

  @Test
  void shouldExecuteWithAllOptionalFields() {
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");
    activity.setDisplayName("Test Display User");
    activity.setRecommendedLanguage("en-US");
    activity.setType("SYSTEM");

    CreateUser.Password password = new CreateUser.Password();
    password.setHashedPassword("hashed");
    password.setHashedSalt("salt");
    password.setHashedKmPassword("kmhashed");
    password.setHashedKmSalt("kmsalt");
    activity.setPassword(password);

    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreateMessage", true);
    activity.setEntitlements(entitlements);

    activity.setStatus("ENABLED");

    V2UserDetail createdUser = createUserDetail(12355L);
    V2UserDetail updatedUser = createUserDetail(12355L);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(12355L)).thenReturn(updatedUser);

    executor.execute(context);

    verify(userService).create(any(V2UserCreate.class));
    verify(userService).updateFeatureEntitlements(eq(12355L), any(List.class));
    verify(userService).updateStatus(eq(12355L), any(UserStatus.class));
    verify(context).setOutputVariable("user", updatedUser);
  }

  @Test
  void shouldNotUpdateEntitlementsWhenEmpty() {
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");
    activity.setEntitlements(new HashMap<>());

    V2UserDetail createdUser = createUserDetail(12356L);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(12356L)).thenReturn(createdUser);

    executor.execute(context);

    verify(userService, never()).updateFeatureEntitlements(anyLong(), any(List.class));
    verify(context).setOutputVariable("user", createdUser);
  }

  @Test
  void shouldNotUpdateStatusWhenNull() {
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");

    V2UserDetail createdUser = createUserDetail(12357L);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(12357L)).thenReturn(createdUser);

    executor.execute(context);

    verify(userService, never()).updateStatus(anyLong(), any(UserStatus.class));
    verify(context).setOutputVariable("user", createdUser);
  }

  @Test
  void shouldConvertEntitlementsToFeatures() {
    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreateMessage", true);
    entitlements.put("canCreateRoom", false);
    entitlements.put("canManageUsers", true);

    List<Feature> features = CreateUserExecutor.toFeatures(entitlements);

    assertThat(features).hasSize(3);
    assertThat(features).extracting(Feature::getEntitlment)
        .containsExactlyInAnyOrder("canCreateMessage", "canCreateRoom", "canManageUsers");
  }

  @Test
  void shouldConvertToUserAttributes() {
    CreateUser createUser = new CreateUser();
    createUser.setType("SYSTEM");
    createUser.setEmail("test@example.com");
    createUser.setUsername("testuser");
    createUser.setFirstname("Test");
    createUser.setLastname("User");
    createUser.setDisplayName("Test User");
    createUser.setRecommendedLanguage("en-US");

    V2UserAttributes attributes = CreateUserExecutor.toUserAttributes(createUser);

    assertThat(attributes.getAccountType()).isEqualTo(V2UserAttributes.AccountTypeEnum.SYSTEM);
    assertThat(attributes.getEmailAddress()).isEqualTo("test@example.com");
    assertThat(attributes.getUserName()).isEqualTo("testuser");
    assertThat(attributes.getFirstName()).isEqualTo("Test");
    assertThat(attributes.getLastName()).isEqualTo("User");
    assertThat(attributes.getDisplayName()).isEqualTo("Test User");
    assertThat(attributes.getRecommendedLanguage()).isEqualTo("en-US");
  }

  @Test
  void shouldConvertToUserAttributesWithContact() {
    CreateUser createUser = new CreateUser();
    createUser.setType("NORMAL");
    createUser.setEmail("test@example.com");
    createUser.setUsername("testuser");
    createUser.setFirstname("Test");
    createUser.setLastname("User");

    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setWorkPhoneNumber("+1234567890");
    contact.setMobilePhoneNumber("+0987654321");
    contact.setTwoFactorAuthNumber("+1111111111");
    contact.setSmsNumber("+2222222222");
    createUser.setContact(contact);

    V2UserAttributes attributes = CreateUserExecutor.toUserAttributes(createUser);

    assertThat(attributes.getWorkPhoneNumber()).isEqualTo("+1234567890");
    assertThat(attributes.getMobilePhoneNumber()).isEqualTo("+0987654321");
    assertThat(attributes.getTwoFactorAuthPhone()).isEqualTo("+1111111111");
    assertThat(attributes.getSmsNumber()).isEqualTo("+2222222222");
  }

  @Test
  void shouldConvertToUserAttributesWithBusiness() {
    CreateUser createUser = new CreateUser();
    createUser.setType("NORMAL");
    createUser.setEmail("test@example.com");
    createUser.setUsername("testuser");
    createUser.setFirstname("Test");
    createUser.setLastname("User");

    CreateUser.Business business = new CreateUser.Business();
    business.setCompanyName("Test Corp");
    business.setDepartment("Engineering");
    business.setDivision("Tech");
    business.setTitle("Developer");
    business.setLocation("New York");
    business.setJobFunction("Software Development");

    List<String> assetClasses = new ArrayList<>();
    assetClasses.add("Equity");
    business.setAssetClasses(assetClasses);

    List<String> industries = new ArrayList<>();
    industries.add("Technology");
    business.setIndustries(industries);

    List<String> functions = new ArrayList<>();
    functions.add("Engineering");
    business.setFunctions(functions);

    List<String> marketCoverages = new ArrayList<>();
    marketCoverages.add("Americas");
    business.setMarketCoverages(marketCoverages);

    List<String> responsibilities = new ArrayList<>();
    responsibilities.add("Development");
    business.setResponsibilities(responsibilities);

    List<String> instruments = new ArrayList<>();
    instruments.add("Stocks");
    business.setInstruments(instruments);

    createUser.setBusiness(business);

    V2UserAttributes attributes = CreateUserExecutor.toUserAttributes(createUser);

    assertThat(attributes.getCompanyName()).isEqualTo("Test Corp");
    assertThat(attributes.getDepartment()).isEqualTo("Engineering");
    assertThat(attributes.getDivision()).isEqualTo("Tech");
    assertThat(attributes.getTitle()).isEqualTo("Developer");
    assertThat(attributes.getLocation()).isEqualTo("New York");
    assertThat(attributes.getJobFunction()).isEqualTo("Software Development");
    assertThat(attributes.getAssetClasses()).containsExactly("Equity");
    assertThat(attributes.getFunction()).containsExactly("Engineering");
    assertThat(attributes.getIndustries()).containsExactly("Technology");
    assertThat(attributes.getInstrument()).containsExactly("Stocks");
    assertThat(attributes.getResponsibility()).containsExactly("Development");
    assertThat(attributes.getMarketCoverage()).containsExactly("Americas");
  }

  @Test
  void shouldConvertToUserAttributesWithKeys() {
    CreateUser createUser = new CreateUser();
    createUser.setType("NORMAL");
    createUser.setEmail("test@example.com");
    createUser.setUsername("testuser");
    createUser.setFirstname("Test");
    createUser.setLastname("User");

    CreateUser.Keys keys = new CreateUser.Keys();
    CreateUser.Key currentKey = new CreateUser.Key();
    currentKey.setKey("currentKeyValue");
    currentKey.setAction("SAVE");
    currentKey.setExpiration("2025-12-31T23:59:59Z");
    keys.setCurrent(currentKey);

    CreateUser.Key previousKey = new CreateUser.Key();
    previousKey.setKey("previousKeyValue");
    previousKey.setAction("REVOKE");
    previousKey.setExpiration("2024-12-31T23:59:59Z");
    keys.setPrevious(previousKey);

    createUser.setKeys(keys);

    V2UserAttributes attributes = CreateUserExecutor.toUserAttributes(createUser);

    assertThat(attributes.getCurrentKey()).isNotNull();
    assertThat(attributes.getCurrentKey().getKey()).isEqualTo("currentKeyValue");
    assertThat(attributes.getCurrentKey().getAction()).isEqualTo("SAVE");

    assertThat(attributes.getPreviousKey()).isNotNull();
    assertThat(attributes.getPreviousKey().getKey()).isEqualTo("previousKeyValue");
    assertThat(attributes.getPreviousKey().getAction()).isEqualTo("REVOKE");
  }

  @Test
  void shouldConvertToUserAttributesWithNullContact() {
    CreateUser createUser = new CreateUser();
    createUser.setType("NORMAL");
    createUser.setEmail("test@example.com");
    createUser.setUsername("testuser");
    createUser.setFirstname("Test");
    createUser.setLastname("User");

    V2UserAttributes attributes = CreateUserExecutor.toUserAttributes(createUser);

    assertThat(attributes.getWorkPhoneNumber()).isNull();
    assertThat(attributes.getMobilePhoneNumber()).isNull();
    assertThat(attributes.getTwoFactorAuthPhone()).isNull();
    assertThat(attributes.getSmsNumber()).isNull();
  }

  @Test
  void shouldConvertToUserAttributesWithNullBusiness() {
    CreateUser createUser = new CreateUser();
    createUser.setType("NORMAL");
    createUser.setEmail("test@example.com");
    createUser.setUsername("testuser");
    createUser.setFirstname("Test");
    createUser.setLastname("User");

    V2UserAttributes attributes = CreateUserExecutor.toUserAttributes(createUser);

    assertThat(attributes.getCompanyName()).isNull();
    assertThat(attributes.getDepartment()).isNull();
    assertThat(attributes.getDivision()).isNull();
    assertThat(attributes.getTitle()).isNull();
    assertThat(attributes.getLocation()).isNull();
    assertThat(attributes.getJobFunction()).isNull();
  }

  @Test
  void shouldConvertToUserAttributesWithNullKeys() {
    CreateUser createUser = new CreateUser();
    createUser.setType("NORMAL");
    createUser.setEmail("test@example.com");
    createUser.setUsername("testuser");
    createUser.setFirstname("Test");
    createUser.setLastname("User");

    V2UserAttributes attributes = CreateUserExecutor.toUserAttributes(createUser);

    assertThat(attributes.getCurrentKey()).isNull();
    assertThat(attributes.getPreviousKey()).isNull();
  }

  private V2UserDetail createUserDetail(Long userId) {
    V2UserDetail userDetail = mock(V2UserDetail.class, org.mockito.Answers.RETURNS_DEEP_STUBS);
    when(userDetail.getUserSystemInfo().getId()).thenReturn(userId);
    return userDetail;
  }
}
