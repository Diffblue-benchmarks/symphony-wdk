package com.symphony.bdk.workflow.engine.executor.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.gen.api.model.Feature;
import com.symphony.bdk.gen.api.model.UserSystemInfo;
import com.symphony.bdk.gen.api.model.V2UserAttributes;
import com.symphony.bdk.gen.api.model.V2UserCreate;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateUserExecutorDiffblueTest {
  /**
   * Method under test:
   * {@link CreateUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());
    UserService userService = mock(UserService.class);
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
    SpringBdkGateway springBdkGateway = new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        mock(MessageService.class), mock(StreamService.class), userService, mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class));

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.bdk()).thenReturn(springBdkGateway);
    when(context.getActivity()).thenReturn(new CreateUser());

    // Act
    createUserExecutor.execute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
  }

  /**
   * Method under test:
   * {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}
   */
  @Test
  void testDoExecute() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());
    UserService userService = mock(UserService.class);
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
    SpringBdkGateway springBdkGateway = new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        mock(MessageService.class), mock(StreamService.class), userService, mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class));

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.bdk()).thenReturn(springBdkGateway);
    when(context.getActivity()).thenReturn(new CreateUser());

    // Act
    createUserExecutor.doExecute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
  }

  /**
   * Method under test: {@link CreateUserExecutor#toUserAttributes(CreateUser)}
   */
  @Test
  void testToUserAttributes() {
    // Arrange and Act
    V2UserAttributes actualToUserAttributesResult = CreateUserExecutor.toUserAttributes(new CreateUser());

    // Assert
    assertNull(actualToUserAttributesResult.getCurrentKey());
    assertNull(actualToUserAttributesResult.getPreviousKey());
    assertNull(actualToUserAttributesResult.getCompanyName());
    assertNull(actualToUserAttributesResult.getDepartment());
    assertNull(actualToUserAttributesResult.getDisplayName());
    assertNull(actualToUserAttributesResult.getDivision());
    assertNull(actualToUserAttributesResult.getEmailAddress());
    assertNull(actualToUserAttributesResult.getFirstName());
    assertNull(actualToUserAttributesResult.getJobFunction());
    assertNull(actualToUserAttributesResult.getLastName());
    assertNull(actualToUserAttributesResult.getLocation());
    assertNull(actualToUserAttributesResult.getMobilePhoneNumber());
    assertNull(actualToUserAttributesResult.getRecommendedLanguage());
    assertNull(actualToUserAttributesResult.getSmsNumber());
    assertNull(actualToUserAttributesResult.getTitle());
    assertNull(actualToUserAttributesResult.getTwoFactorAuthPhone());
    assertNull(actualToUserAttributesResult.getUserName());
    assertNull(actualToUserAttributesResult.getWorkPhoneNumber());
    assertNull(actualToUserAttributesResult.getAssetClasses());
    assertNull(actualToUserAttributesResult.getFunction());
    assertNull(actualToUserAttributesResult.getIndustries());
    assertNull(actualToUserAttributesResult.getInstrument());
    assertNull(actualToUserAttributesResult.getMarketCoverage());
    assertNull(actualToUserAttributesResult.getResponsibility());
    assertEquals(V2UserAttributes.AccountTypeEnum.NORMAL, actualToUserAttributesResult.getAccountType());
  }

  /**
   * Method under test: {@link CreateUserExecutor#toFeatures(Map)}
   */
  @Test
  void testToFeatures() {
    // Arrange and Act
    List<Feature> actualToFeaturesResult = CreateUserExecutor.toFeatures(new HashMap<>());

    // Assert
    assertTrue(actualToFeaturesResult.isEmpty());
  }

  /**
   * Method under test: {@link CreateUserExecutor#toFeatures(Map)}
   */
  @Test
  void testToFeatures2() {
    // Arrange
    HashMap<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("foo", true);

    // Act
    List<Feature> actualToFeaturesResult = CreateUserExecutor.toFeatures(entitlements);

    // Assert
    assertEquals(1, actualToFeaturesResult.size());
    Feature getResult = actualToFeaturesResult.get(0);
    assertEquals("foo", getResult.getEntitlment());
    assertTrue(getResult.getEnabled());
  }

  /**
   * Method under test: {@link CreateUserExecutor#toFeatures(Map)}
   */
  @Test
  void testToFeatures3() {
    // Arrange
    HashMap<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("42", false);
    entitlements.put("foo", true);

    // Act
    List<Feature> actualToFeaturesResult = CreateUserExecutor.toFeatures(entitlements);

    // Assert
    assertEquals(2, actualToFeaturesResult.size());
    Feature getResult = actualToFeaturesResult.get(1);
    assertEquals("42", getResult.getEntitlment());
    Feature getResult2 = actualToFeaturesResult.get(0);
    assertEquals("foo", getResult2.getEntitlment());
    assertFalse(getResult.getEnabled());
    assertTrue(getResult2.getEnabled());
  }

  /**
   * Method under test: {@link CreateUserExecutor#toFeatures(Map)}
   */
  @Test
  void testToFeatures4() {
    // Arrange
    HashMap<String, Boolean> entitlements = new HashMap<>();
    entitlements.computeIfPresent("foo", mock(BiFunction.class));
    entitlements.put("foo", true);

    // Act
    List<Feature> actualToFeaturesResult = CreateUserExecutor.toFeatures(entitlements);

    // Assert
    assertEquals(1, actualToFeaturesResult.size());
    Feature getResult = actualToFeaturesResult.get(0);
    assertEquals("foo", getResult.getEntitlment());
    assertTrue(getResult.getEnabled());
  }
}
