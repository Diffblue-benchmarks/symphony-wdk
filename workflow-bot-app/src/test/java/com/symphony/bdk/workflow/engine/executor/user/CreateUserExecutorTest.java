package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.Feature;
import com.symphony.bdk.gen.api.model.V2UserAttributes;
import com.symphony.bdk.gen.api.model.V2UserCreate;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.gen.api.model.UserSystemInfo;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateUserExecutorTest {

  @Test
  void shouldExecuteCreateUserWithMinimalFields() {
    CreateUserExecutor executor = new CreateUserExecutor();

    CreateUser activity = new CreateUser();
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");
    activity.setDisplayName("Test User");

    UserSystemInfo sysInfo = new UserSystemInfo();
    sysInfo.setId(123L);

    V2UserDetail createdUser = new V2UserDetail();
    createdUser.setUserSystemInfo(sysInfo);

    V2UserDetail fetchedUser = new V2UserDetail();
    fetchedUser.setUserSystemInfo(sysInfo);

    UserService userService = mock(UserService.class);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(123L)).thenReturn(fetchedUser);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.users()).thenReturn(userService);

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    executor.execute(context);

    verify(userService).create(any(V2UserCreate.class));
    verify(userService, never()).updateFeatureEntitlements(any(), any());
    verify(userService, never()).updateStatus(any(), any());
    verify(userService).getUserDetail(123L);
    verify(context).setOutputVariable("user", fetchedUser);
  }

  @Test
  void shouldExecuteCreateUserWithEntitlements() {
    CreateUserExecutor executor = new CreateUserExecutor();

    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);

    CreateUser activity = new CreateUser();
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");
    activity.setDisplayName("Test User");
    activity.setEntitlements(entitlements);

    UserSystemInfo sysInfo = new UserSystemInfo();
    sysInfo.setId(456L);

    V2UserDetail createdUser = new V2UserDetail();
    createdUser.setUserSystemInfo(sysInfo);

    V2UserDetail fetchedUser = new V2UserDetail();
    fetchedUser.setUserSystemInfo(sysInfo);

    UserService userService = mock(UserService.class);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(456L)).thenReturn(fetchedUser);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.users()).thenReturn(userService);

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    executor.execute(context);

    verify(userService).updateFeatureEntitlements(eq(456L), any());
    verify(context).setOutputVariable("user", fetchedUser);
  }

  @Test
  void shouldExecuteCreateUserWithStatus() {
    CreateUserExecutor executor = new CreateUserExecutor();

    CreateUser activity = new CreateUser();
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");
    activity.setDisplayName("Test User");
    activity.setStatus("ENABLED");

    UserSystemInfo sysInfo = new UserSystemInfo();
    sysInfo.setId(789L);

    V2UserDetail createdUser = new V2UserDetail();
    createdUser.setUserSystemInfo(sysInfo);

    V2UserDetail fetchedUser = new V2UserDetail();
    fetchedUser.setUserSystemInfo(sysInfo);

    UserService userService = mock(UserService.class);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(789L)).thenReturn(fetchedUser);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.users()).thenReturn(userService);

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    executor.execute(context);

    verify(userService).updateStatus(eq(789L), any());
    verify(context).setOutputVariable("user", fetchedUser);
  }

  @Test
  void shouldConvertToUserAttributesWithBasicFields() {
    CreateUser createUser = new CreateUser();
    createUser.setType("NORMAL");
    createUser.setEmail("user@test.com");
    createUser.setUsername("username1");
    createUser.setFirstname("First");
    createUser.setLastname("Last");
    createUser.setDisplayName("First Last");
    createUser.setRecommendedLanguage("en-US");

    V2UserAttributes attributes = CreateUserExecutor.toUserAttributes(createUser);

    assertThat(attributes.getEmailAddress()).isEqualTo("user@test.com");
    assertThat(attributes.getUserName()).isEqualTo("username1");
    assertThat(attributes.getFirstName()).isEqualTo("First");
    assertThat(attributes.getLastName()).isEqualTo("Last");
    assertThat(attributes.getDisplayName()).isEqualTo("First Last");
    assertThat(attributes.getRecommendedLanguage()).isEqualTo("en-US");
  }

  @Test
  void shouldConvertToUserAttributesWithContact() {
    CreateUser createUser = new CreateUser();
    createUser.setEmail("user@test.com");
    createUser.setUsername("username1");
    createUser.setFirstname("First");
    createUser.setLastname("Last");
    createUser.setDisplayName("First Last");

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
    createUser.setEmail("user@test.com");
    createUser.setUsername("username1");
    createUser.setFirstname("First");
    createUser.setLastname("Last");
    createUser.setDisplayName("First Last");

    CreateUser.Business business = new CreateUser.Business();
    business.setLocation("NYC");
    business.setDepartment("Engineering");
    business.setDivision("Platform");
    business.setCompanyName("Acme");
    business.setJobFunction("Developer");
    business.setTitle("Senior");
    business.setAssetClasses(Arrays.asList("Equity"));
    business.setFunctions(Arrays.asList("Trading"));
    business.setIndustries(Arrays.asList("Finance"));
    business.setInstruments(Arrays.asList("Bonds"));
    business.setResponsibilities(Arrays.asList("Architecture"));
    business.setMarketCoverages(Arrays.asList("EMEA"));
    createUser.setBusiness(business);

    V2UserAttributes attributes = CreateUserExecutor.toUserAttributes(createUser);

    assertThat(attributes.getLocation()).isEqualTo("NYC");
    assertThat(attributes.getDepartment()).isEqualTo("Engineering");
    assertThat(attributes.getDivision()).isEqualTo("Platform");
    assertThat(attributes.getCompanyName()).isEqualTo("Acme");
    assertThat(attributes.getJobFunction()).isEqualTo("Developer");
    assertThat(attributes.getTitle()).isEqualTo("Senior");
    assertThat(attributes.getAssetClasses()).containsExactly("Equity");
    assertThat(attributes.getFunction()).containsExactly("Trading");
    assertThat(attributes.getIndustries()).containsExactly("Finance");
    assertThat(attributes.getInstrument()).containsExactly("Bonds");
    assertThat(attributes.getResponsibility()).containsExactly("Architecture");
    assertThat(attributes.getMarketCoverage()).containsExactly("EMEA");
  }

  @Test
  void shouldConvertToUserAttributesWithKeys() {
    CreateUser createUser = new CreateUser();
    createUser.setEmail("user@test.com");
    createUser.setUsername("username1");
    createUser.setFirstname("First");
    createUser.setLastname("Last");
    createUser.setDisplayName("First Last");

    CreateUser.Key currentKey = new CreateUser.Key();
    currentKey.setKey("currentKeyValue");
    currentKey.setAction("SAVE");

    CreateUser.Key previousKey = new CreateUser.Key();
    previousKey.setKey("previousKeyValue");
    previousKey.setAction("REVOKE");

    CreateUser.Keys keys = new CreateUser.Keys();
    keys.setCurrent(currentKey);
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
  void shouldConvertEntitlementsToFeatures() {
    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);
    entitlements.put("canUpdateAvatar", false);

    List<Feature> features = CreateUserExecutor.toFeatures(entitlements);

    assertThat(features).hasSize(2);
    assertThat(features).anyMatch(f -> "canCreatePublicRoom".equals(f.getEntitlment()) && Boolean.TRUE.equals(f.getEnabled()));
    assertThat(features).anyMatch(f -> "canUpdateAvatar".equals(f.getEntitlment()) && Boolean.FALSE.equals(f.getEnabled()));
  }

  @Test
  void shouldExecuteCreateUserWithPassword() {
    CreateUserExecutor executor = new CreateUserExecutor();

    CreateUser.Password password = new CreateUser.Password();
    password.setHashedPassword("hash123");
    password.setHashedSalt("salt123");
    password.setHashedKmPassword("kmhash123");
    password.setHashedKmSalt("kmsalt123");

    CreateUser activity = new CreateUser();
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");
    activity.setDisplayName("Test User");
    activity.setPassword(password);
    activity.setRoles(Arrays.asList("USER"));

    UserSystemInfo sysInfo = new UserSystemInfo();
    sysInfo.setId(999L);

    V2UserDetail createdUser = new V2UserDetail();
    createdUser.setUserSystemInfo(sysInfo);

    V2UserDetail fetchedUser = new V2UserDetail();
    fetchedUser.setUserSystemInfo(sysInfo);

    UserService userService = mock(UserService.class);
    when(userService.create(any(V2UserCreate.class))).thenReturn(createdUser);
    when(userService.getUserDetail(999L)).thenReturn(fetchedUser);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.users()).thenReturn(userService);

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    executor.execute(context);

    verify(userService).create(any(V2UserCreate.class));
    verify(context).setOutputVariable("user", fetchedUser);
  }
}
