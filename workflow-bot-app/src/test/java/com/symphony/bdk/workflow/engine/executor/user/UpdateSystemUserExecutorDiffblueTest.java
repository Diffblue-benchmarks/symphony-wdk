package com.symphony.bdk.workflow.engine.executor.user;

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
import com.symphony.bdk.gen.api.model.UserStatus;
import com.symphony.bdk.gen.api.model.V2UserAttributes;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser.Business;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser.Contact;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser.Key;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser.Keys;
import com.symphony.bdk.workflow.swadl.v1.activity.user.UpdateSystemUser;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UpdateSystemUserExecutorDiffblueTest {
  /**
   * Test {@link UpdateSystemUserExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link CreateUser.Keys} (default constructor) Current is {@code null}.
   *   <li>Then calls {@link UserService#getUserDetail(Long)}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateSystemUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given Keys (default constructor) Current is 'null'; then calls getUserDetail(Long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateSystemUserExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenKeysCurrentIsNull_thenCallsGetUserDetail() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    UpdateSystemUserExecutor updateSystemUserExecutor = new UpdateSystemUserExecutor();

    UserService userService = mock(UserService.class);
    when(userService.update(Mockito.<Long>any(), Mockito.<V2UserAttributes>any()))
        .thenReturn(new V2UserDetail());
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

    Key previous = new Key();
    previous.setAction("Action");
    previous.setExpiration(null);
    previous.setKey("Key");

    Keys keys = new Keys();
    keys.setPrevious(previous);
    keys.setCurrent(null);

    UpdateSystemUser updateSystemUser = mock(UpdateSystemUser.class);
    when(updateSystemUser.getKeys()).thenReturn(keys);
    when(updateSystemUser.getType()).thenReturn("Type");
    when(updateSystemUser.getUsername()).thenReturn("janedoe");
    when(updateSystemUser.getBusiness()).thenReturn(business);
    when(updateSystemUser.getContact()).thenReturn(contact);
    when(updateSystemUser.getDisplayName()).thenReturn("Display Name");
    when(updateSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(updateSystemUser.getFirstname()).thenReturn("Jane");
    when(updateSystemUser.getLastname()).thenReturn("Doe");
    when(updateSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(updateSystemUser.getStatus()).thenReturn("Status");
    when(updateSystemUser.getEntitlements()).thenReturn(new HashMap<>());
    when(updateSystemUser.getUserId()).thenReturn("42");

    ActivityExecutorContext<UpdateSystemUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(updateSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    updateSystemUserExecutor.execute(context);

    // Assert
    verify(userService).getUserDetail(42L);
    verify(userService).update(eq(42L), isA(V2UserAttributes.class));
    verify(userService).updateStatus(eq(42L), isA(UserStatus.class));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(updateSystemUser, atLeast(1)).getBusiness();
    verify(updateSystemUser, atLeast(1)).getContact();
    verify(updateSystemUser).getDisplayName();
    verify(updateSystemUser, atLeast(1)).getEmail();
    verify(updateSystemUser, atLeast(1)).getEntitlements();
    verify(updateSystemUser).getFirstname();
    verify(updateSystemUser, atLeast(1)).getKeys();
    verify(updateSystemUser).getLastname();
    verify(updateSystemUser).getRecommendedLanguage();
    verify(updateSystemUser, atLeast(1)).getStatus();
    verify(updateSystemUser).getType();
    verify(updateSystemUser).getUsername();
    verify(updateSystemUser).getUserId();
  }

  /**
   * Test {@link UpdateSystemUserExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link CreateUser.Keys} (default constructor) Previous is {@code null}.
   *   <li>Then calls {@link UserService#getUserDetail(Long)}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateSystemUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given Keys (default constructor) Previous is 'null'; then calls getUserDetail(Long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateSystemUserExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenKeysPreviousIsNull_thenCallsGetUserDetail() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    UpdateSystemUserExecutor updateSystemUserExecutor = new UpdateSystemUserExecutor();

    UserService userService = mock(UserService.class);
    when(userService.update(Mockito.<Long>any(), Mockito.<V2UserAttributes>any()))
        .thenReturn(new V2UserDetail());
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
    current.setExpiration(null);
    current.setKey("Key");

    Keys keys = new Keys();
    keys.setCurrent(current);
    keys.setPrevious(null);

    UpdateSystemUser updateSystemUser = mock(UpdateSystemUser.class);
    when(updateSystemUser.getKeys()).thenReturn(keys);
    when(updateSystemUser.getType()).thenReturn("Type");
    when(updateSystemUser.getUsername()).thenReturn("janedoe");
    when(updateSystemUser.getBusiness()).thenReturn(business);
    when(updateSystemUser.getContact()).thenReturn(contact);
    when(updateSystemUser.getDisplayName()).thenReturn("Display Name");
    when(updateSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(updateSystemUser.getFirstname()).thenReturn("Jane");
    when(updateSystemUser.getLastname()).thenReturn("Doe");
    when(updateSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(updateSystemUser.getStatus()).thenReturn("Status");
    when(updateSystemUser.getEntitlements()).thenReturn(new HashMap<>());
    when(updateSystemUser.getUserId()).thenReturn("42");

    ActivityExecutorContext<UpdateSystemUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(updateSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    updateSystemUserExecutor.execute(context);

    // Assert
    verify(userService).getUserDetail(42L);
    verify(userService).update(eq(42L), isA(V2UserAttributes.class));
    verify(userService).updateStatus(eq(42L), isA(UserStatus.class));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(updateSystemUser, atLeast(1)).getBusiness();
    verify(updateSystemUser, atLeast(1)).getContact();
    verify(updateSystemUser).getDisplayName();
    verify(updateSystemUser, atLeast(1)).getEmail();
    verify(updateSystemUser, atLeast(1)).getEntitlements();
    verify(updateSystemUser).getFirstname();
    verify(updateSystemUser, atLeast(1)).getKeys();
    verify(updateSystemUser).getLastname();
    verify(updateSystemUser).getRecommendedLanguage();
    verify(updateSystemUser, atLeast(1)).getStatus();
    verify(updateSystemUser).getType();
    verify(updateSystemUser).getUsername();
    verify(updateSystemUser).getUserId();
  }
}
