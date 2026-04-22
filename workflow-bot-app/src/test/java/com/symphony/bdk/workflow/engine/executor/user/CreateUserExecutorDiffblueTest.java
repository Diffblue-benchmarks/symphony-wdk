package com.symphony.bdk.workflow.engine.executor.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.gen.api.model.Feature;
import com.symphony.bdk.gen.api.model.UserStatus;
import com.symphony.bdk.gen.api.model.V2UserAttributes;
import com.symphony.bdk.gen.api.model.V2UserCreate;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.gen.api.model.V2UserKeyRequest;
import com.symphony.bdk.gen.api.model.UserSystemInfo;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser.Business;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser.Contact;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser.Key;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser.Keys;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateUserExecutorDiffblueTest {
  /**
   * Test {@link CreateUserExecutor#toUserAttributes(CreateUser)}.
   *
   * <ul>
   *   <li>Given {@code Display Name}.
   *   <li>Then return {@code Display Name}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#toUserAttributes(CreateUser)}
   */
  @Test
  @DisplayName(
      "Test toUserAttributes(CreateUser); given 'Display Name'; then return 'Display Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"V2UserAttributes CreateUserExecutor.toUserAttributes(CreateUser)"})
  void testToUserAttributes_givenDisplayName_thenReturnDisplayName() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    Key current = new Key();
    current.setAction("Action");
    current.setKey("Key");
    current.setExpiration(null);

    Key previous = new Key();
    previous.setAction("Action");
    previous.setKey("Key");
    previous.setExpiration(null);

    Keys keys = new Keys();
    keys.setCurrent(current);
    keys.setPrevious(previous);

    CreateUser createUser = mock(CreateUser.class);
    when(createUser.getKeys()).thenReturn(keys);
    when(createUser.getBusiness()).thenReturn(business);
    when(createUser.getContact()).thenReturn(contact);
    when(createUser.getDisplayName()).thenReturn("Display Name");
    when(createUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createUser.getFirstname()).thenReturn("Jane");
    when(createUser.getLastname()).thenReturn("Doe");
    when(createUser.getRecommendedLanguage()).thenReturn("en");
    when(createUser.getType()).thenReturn("Type");
    when(createUser.getUsername()).thenReturn("janedoe");

    // Act
    V2UserAttributes actualToUserAttributesResult = CreateUserExecutor.toUserAttributes(createUser);

    // Assert
    verify(createUser, atLeast(1)).getBusiness();
    verify(createUser, atLeast(1)).getContact();
    verify(createUser).getDisplayName();
    verify(createUser).getEmail();
    verify(createUser).getFirstname();
    verify(createUser, atLeast(1)).getKeys();
    verify(createUser).getLastname();
    verify(createUser).getRecommendedLanguage();
    verify(createUser).getType();
    verify(createUser).getUsername();
    assertEquals("Display Name", actualToUserAttributesResult.getDisplayName());
    assertEquals("Doe", actualToUserAttributesResult.getLastName());
    assertEquals("Jane", actualToUserAttributesResult.getFirstName());
    assertEquals("en", actualToUserAttributesResult.getRecommendedLanguage());
    assertEquals("jane.doe@example.org", actualToUserAttributesResult.getEmailAddress());
    assertEquals("janedoe", actualToUserAttributesResult.getUserName());
    assertNull(actualToUserAttributesResult.getAccountType());
  }

  /**
   * Test {@link CreateUserExecutor#toUserAttributes(CreateUser)}.
   *
   * <ul>
   *   <li>Given {@link CreateUser.Key} (default constructor) Expiration is {@code null}.
   *   <li>Then return PreviousKey is CurrentKey.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#toUserAttributes(CreateUser)}
   */
  @Test
  @DisplayName(
      "Test toUserAttributes(CreateUser); given Key (default constructor) Expiration is 'null'; then return PreviousKey is CurrentKey")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"V2UserAttributes CreateUserExecutor.toUserAttributes(CreateUser)"})
  void testToUserAttributes_givenKeyExpirationIsNull_thenReturnPreviousKeyIsCurrentKey() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Key current = new Key();
    current.setAction("Action");
    current.setKey("Key");
    current.setExpiration(null);

    Key previous = new Key();
    previous.setAction("Action");
    previous.setKey("Key");
    previous.setExpiration(null);

    Keys keys = new Keys();
    keys.setCurrent(current);
    keys.setPrevious(previous);

    CreateUser createUser = new CreateUser();
    createUser.setContact(contact);
    createUser.setBusiness(business);
    createUser.setKeys(keys);

    // Act
    V2UserAttributes actualToUserAttributesResult = CreateUserExecutor.toUserAttributes(createUser);

    // Assert
    V2UserKeyRequest currentKey = actualToUserAttributesResult.getCurrentKey();
    assertEquals("Action", currentKey.getAction());
    assertEquals("Key", currentKey.getKey());
    assertNull(currentKey.getExpirationDate());
    assertEquals(currentKey, actualToUserAttributesResult.getPreviousKey());
  }

  /**
   * Test {@link CreateUserExecutor#toUserAttributes(CreateUser)}.
   *
   * <ul>
   *   <li>Given {@link CreateUser.Keys} (default constructor) Current is {@code null}.
   *   <li>Then return PreviousKey Action is {@code Action}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#toUserAttributes(CreateUser)}
   */
  @Test
  @DisplayName(
      "Test toUserAttributes(CreateUser); given Keys (default constructor) Current is 'null'; then return PreviousKey Action is 'Action'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"V2UserAttributes CreateUserExecutor.toUserAttributes(CreateUser)"})
  void testToUserAttributes_givenKeysCurrentIsNull_thenReturnPreviousKeyActionIsAction() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Key previous = new Key();
    previous.setAction("Action");
    previous.setKey("Key");
    previous.setExpiration(null);

    Keys keys = new Keys();
    keys.setCurrent(null);
    keys.setPrevious(previous);

    CreateUser createUser = new CreateUser();
    createUser.setContact(contact);
    createUser.setBusiness(business);
    createUser.setKeys(keys);

    // Act
    V2UserAttributes actualToUserAttributesResult = CreateUserExecutor.toUserAttributes(createUser);

    // Assert
    V2UserKeyRequest previousKey = actualToUserAttributesResult.getPreviousKey();
    assertEquals("Action", previousKey.getAction());
    assertEquals("Key", previousKey.getKey());
    assertNull(actualToUserAttributesResult.getCurrentKey());
    assertNull(previousKey.getExpirationDate());
  }

  /**
   * Test {@link CreateUserExecutor#toUserAttributes(CreateUser)}.
   *
   * <ul>
   *   <li>Given {@link CreateUser.Keys} (default constructor) Previous is {@code null}.
   *   <li>Then return PreviousKey is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#toUserAttributes(CreateUser)}
   */
  @Test
  @DisplayName(
      "Test toUserAttributes(CreateUser); given Keys (default constructor) Previous is 'null'; then return PreviousKey is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"V2UserAttributes CreateUserExecutor.toUserAttributes(CreateUser)"})
  void testToUserAttributes_givenKeysPreviousIsNull_thenReturnPreviousKeyIsNull() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Key current = new Key();
    current.setAction("Action");
    current.setKey("Key");
    current.setExpiration(null);

    Keys keys = new Keys();
    keys.setCurrent(current);
    keys.setPrevious(null);

    CreateUser createUser = new CreateUser();
    createUser.setContact(contact);
    createUser.setBusiness(business);
    createUser.setKeys(keys);

    // Act
    V2UserAttributes actualToUserAttributesResult = CreateUserExecutor.toUserAttributes(createUser);

    // Assert
    V2UserKeyRequest currentKey = actualToUserAttributesResult.getCurrentKey();
    assertEquals("Action", currentKey.getAction());
    assertEquals("Key", currentKey.getKey());
    assertNull(actualToUserAttributesResult.getPreviousKey());
    assertNull(currentKey.getExpirationDate());
  }

  /**
   * Test {@link CreateUserExecutor#toUserAttributes(CreateUser)}.
   *
   * <ul>
   *   <li>When {@link CreateUser} (default constructor).
   *   <li>Then return CompanyName is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#toUserAttributes(CreateUser)}
   */
  @Test
  @DisplayName(
      "Test toUserAttributes(CreateUser); when CreateUser (default constructor); then return CompanyName is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"V2UserAttributes CreateUserExecutor.toUserAttributes(CreateUser)"})
  void testToUserAttributes_whenCreateUser_thenReturnCompanyNameIsNull() {
    // Arrange and Act
    V2UserAttributes actualToUserAttributesResult =
        CreateUserExecutor.toUserAttributes(new CreateUser());

    // Assert
    assertNull(actualToUserAttributesResult.getCompanyName());
    assertNull(actualToUserAttributesResult.getDepartment());
    assertNull(actualToUserAttributesResult.getDivision());
    assertNull(actualToUserAttributesResult.getJobFunction());
    assertNull(actualToUserAttributesResult.getLocation());
    assertNull(actualToUserAttributesResult.getMobilePhoneNumber());
    assertNull(actualToUserAttributesResult.getSmsNumber());
    assertNull(actualToUserAttributesResult.getTitle());
    assertNull(actualToUserAttributesResult.getTwoFactorAuthPhone());
    assertNull(actualToUserAttributesResult.getWorkPhoneNumber());
    assertNull(actualToUserAttributesResult.getAssetClasses());
    assertNull(actualToUserAttributesResult.getFunction());
    assertNull(actualToUserAttributesResult.getIndustries());
    assertNull(actualToUserAttributesResult.getInstrument());
    assertNull(actualToUserAttributesResult.getMarketCoverage());
    assertNull(actualToUserAttributesResult.getResponsibility());
  }

  /**
   * Test {@link CreateUserExecutor#toFeatures(Map)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code false}.
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#toFeatures(Map)}
   */
  @Test
  @DisplayName(
      "Test toFeatures(Map); given '42'; when HashMap() '42' is 'false'; then return size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List CreateUserExecutor.toFeatures(Map)"})
  void testToFeatures_given42_whenHashMap42IsFalse_thenReturnSizeIsTwo() {
    // Arrange
    HashMap<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("42", false);
    entitlements.put("Key", true);

    // Act
    List<Feature> actualToFeaturesResult = CreateUserExecutor.toFeatures(entitlements);

    // Assert
    assertEquals(2, actualToFeaturesResult.size());
    Feature getResult = actualToFeaturesResult.get(0);
    assertEquals("42", getResult.getEntitlment());
    Feature getResult2 = actualToFeaturesResult.get(1);
    assertEquals("Key", getResult2.getEntitlment());
    assertFalse(getResult.getEnabled());
    assertTrue(getResult2.getEnabled());
  }

  /**
   * Test {@link CreateUserExecutor#toFeatures(Map)}.
   *
   * <ul>
   *   <li>Given {@code Key}.
   *   <li>When {@link HashMap#HashMap()} {@code Key} is {@code true}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#toFeatures(Map)}
   */
  @Test
  @DisplayName(
      "Test toFeatures(Map); given 'Key'; when HashMap() 'Key' is 'true'; then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List CreateUserExecutor.toFeatures(Map)"})
  void testToFeatures_givenKey_whenHashMapKeyIsTrue_thenReturnSizeIsOne() {
    // Arrange
    HashMap<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("Key", true);

    // Act
    List<Feature> actualToFeaturesResult = CreateUserExecutor.toFeatures(entitlements);

    // Assert
    assertEquals(1, actualToFeaturesResult.size());
    Feature getResult = actualToFeaturesResult.get(0);
    assertEquals("Key", getResult.getEntitlment());
    assertTrue(getResult.getEnabled());
  }

  /**
   * Test {@link CreateUserExecutor#toFeatures(Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#toFeatures(Map)}
   */
  @Test
  @DisplayName("Test toFeatures(Map); when HashMap(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List CreateUserExecutor.toFeatures(Map)"})
  void testToFeatures_whenHashMap_thenReturnEmpty() {
    // Arrange and Act
    List<Feature> actualToFeaturesResult = CreateUserExecutor.toFeatures(new HashMap<>());

    // Assert
    assertTrue(actualToFeaturesResult.isEmpty());
  }

  /**
   * Test {@link CreateUserExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given a basic {@link CreateUser} with no entitlements and no status.
   *   <li>Then calls {@link UserService#getUserDetail(Long)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given CreateUser with no entitlements and no status; then calls getUserDetail(Long)")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void CreateUserExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenBasicCreateUser_thenCallsGetUserDetail() {
    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    UserSystemInfo userSystemInfo = mock(UserSystemInfo.class);
    when(userSystemInfo.getId()).thenReturn(42L);

    V2UserDetail createdUser = mock(V2UserDetail.class);
    when(createdUser.getUserSystemInfo()).thenReturn(userSystemInfo);

    UserService userService = mock(UserService.class);
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(createdUser);
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());

    SpringBdkGateway springBdkGateway =
        new SpringBdkGateway(
            mock(BdkConfig.class),
            mock(AuthenticatorFactory.class),
            mock(MessageService.class),
            mock(StreamService.class),
            userService,
            mock(ConnectionService.class),
            mock(SymphonyGroupService.class),
            mock(SessionService.class));

    CreateUser createUser = new CreateUser();

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.execute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(42L);
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
  }

  /**
   * Test {@link CreateUserExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given a {@link CreateUser} with entitlements and status set.
   *   <li>Then calls {@link UserService#updateFeatureEntitlements(Long, List)} and
   *       {@link UserService#updateStatus(Long, UserStatus)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given CreateUser with entitlements and status; then calls updateFeatureEntitlements and updateStatus")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void CreateUserExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenCreateUserWithEntitlementsAndStatus_thenCallsUpdateFeatureEntitlementsAndUpdateStatus() {
    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    UserSystemInfo userSystemInfo = mock(UserSystemInfo.class);
    when(userSystemInfo.getId()).thenReturn(42L);

    V2UserDetail createdUser = mock(V2UserDetail.class);
    when(createdUser.getUserSystemInfo()).thenReturn(userSystemInfo);

    UserService userService = mock(UserService.class);
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(createdUser);
    doNothing().when(userService).updateFeatureEntitlements(Mockito.<Long>any(), Mockito.<List>any());
    doNothing().when(userService).updateStatus(Mockito.<Long>any(), Mockito.<UserStatus>any());
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());

    SpringBdkGateway springBdkGateway =
        new SpringBdkGateway(
            mock(BdkConfig.class),
            mock(AuthenticatorFactory.class),
            mock(MessageService.class),
            mock(StreamService.class),
            userService,
            mock(ConnectionService.class),
            mock(SymphonyGroupService.class),
            mock(SessionService.class));

    HashMap<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);

    CreateUser createUser = new CreateUser();
    createUser.setEntitlements(entitlements);
    createUser.setStatus("ENABLED");

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.execute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).updateFeatureEntitlements(eq(42L), isA(List.class));
    verify(userService).updateStatus(eq(42L), isA(UserStatus.class));
    verify(userService).getUserDetail(42L);
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
  }
}
