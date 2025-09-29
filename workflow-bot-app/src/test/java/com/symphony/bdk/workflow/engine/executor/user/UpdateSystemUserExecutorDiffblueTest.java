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
import com.symphony.bdk.gen.api.model.Feature;
import com.symphony.bdk.gen.api.model.UserStatus;
import com.symphony.bdk.gen.api.model.V2UserAttributes;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.user.UpdateSystemUser;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UpdateSystemUserExecutorDiffblueTest {
  /**
   * Test {@link UpdateSystemUserExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code NORMAL} is {@code false}.
   *   <li>Then calls {@link UserService#updateFeatureEntitlements(Long, List)}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateSystemUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given HashMap() 'NORMAL' is 'false'; then calls updateFeatureEntitlements(Long, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateSystemUserExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenHashMapNormalIsFalse_thenCallsUpdateFeatureEntitlements() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    UpdateSystemUserExecutor updateSystemUserExecutor = new UpdateSystemUserExecutor();

    UserService userService = mock(UserService.class);
    doNothing()
        .when(userService)
        .updateFeatureEntitlements(Mockito.<Long>any(), Mockito.<List<Feature>>any());
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

    HashMap<String, Boolean> stringResultBooleanMap = new HashMap<>();
    stringResultBooleanMap.put("NORMAL", false);
    stringResultBooleanMap.put("Updating user {}", true);

    UpdateSystemUser updateSystemUser = mock(UpdateSystemUser.class);
    when(updateSystemUser.getKeys()).thenReturn(null);
    when(updateSystemUser.getType()).thenReturn("Type");
    when(updateSystemUser.getUsername()).thenReturn("janedoe");
    when(updateSystemUser.getBusiness()).thenReturn(null);
    when(updateSystemUser.getContact()).thenReturn(null);
    when(updateSystemUser.getDisplayName()).thenReturn("Display Name");
    when(updateSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(updateSystemUser.getFirstname()).thenReturn("Jane");
    when(updateSystemUser.getLastname()).thenReturn("Doe");
    when(updateSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(updateSystemUser.getStatus()).thenReturn("Status");
    when(updateSystemUser.getEntitlements()).thenReturn(stringResultBooleanMap);
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
    verify(userService).updateFeatureEntitlements(eq(42L), isA(List.class));
    verify(userService).updateStatus(eq(42L), isA(UserStatus.class));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(updateSystemUser).getBusiness();
    verify(updateSystemUser).getContact();
    verify(updateSystemUser).getDisplayName();
    verify(updateSystemUser, atLeast(1)).getEmail();
    verify(updateSystemUser, atLeast(1)).getEntitlements();
    verify(updateSystemUser).getFirstname();
    verify(updateSystemUser).getKeys();
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
   *   <li>Given {@link UpdateSystemUser} {@link UpdateSystemUser#getKeys()} return {@code null}.
   *   <li>Then calls {@link UserService#getUserDetail(Long)}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateSystemUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateSystemUser getKeys() return 'null'; then calls getUserDetail(Long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateSystemUserExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateSystemUserGetKeysReturnNull_thenCallsGetUserDetail() {
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

    UpdateSystemUser updateSystemUser = mock(UpdateSystemUser.class);
    when(updateSystemUser.getKeys()).thenReturn(null);
    when(updateSystemUser.getType()).thenReturn("Type");
    when(updateSystemUser.getUsername()).thenReturn("janedoe");
    when(updateSystemUser.getBusiness()).thenReturn(null);
    when(updateSystemUser.getContact()).thenReturn(null);
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
    verify(updateSystemUser).getBusiness();
    verify(updateSystemUser).getContact();
    verify(updateSystemUser).getDisplayName();
    verify(updateSystemUser, atLeast(1)).getEmail();
    verify(updateSystemUser, atLeast(1)).getEntitlements();
    verify(updateSystemUser).getFirstname();
    verify(updateSystemUser).getKeys();
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
   *   <li>Then calls {@link UserService#updateFeatureEntitlements(Long, List)}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateSystemUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); then calls updateFeatureEntitlements(Long, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateSystemUserExecutor.execute(ActivityExecutorContext)"})
  void testExecute_thenCallsUpdateFeatureEntitlements() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    UpdateSystemUserExecutor updateSystemUserExecutor = new UpdateSystemUserExecutor();

    UserService userService = mock(UserService.class);
    doNothing()
        .when(userService)
        .updateFeatureEntitlements(Mockito.<Long>any(), Mockito.<List<Feature>>any());
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

    HashMap<String, Boolean> stringResultBooleanMap = new HashMap<>();
    stringResultBooleanMap.put("Updating user {}", true);

    UpdateSystemUser updateSystemUser = mock(UpdateSystemUser.class);
    when(updateSystemUser.getKeys()).thenReturn(null);
    when(updateSystemUser.getType()).thenReturn("Type");
    when(updateSystemUser.getUsername()).thenReturn("janedoe");
    when(updateSystemUser.getBusiness()).thenReturn(null);
    when(updateSystemUser.getContact()).thenReturn(null);
    when(updateSystemUser.getDisplayName()).thenReturn("Display Name");
    when(updateSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(updateSystemUser.getFirstname()).thenReturn("Jane");
    when(updateSystemUser.getLastname()).thenReturn("Doe");
    when(updateSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(updateSystemUser.getStatus()).thenReturn("Status");
    when(updateSystemUser.getEntitlements()).thenReturn(stringResultBooleanMap);
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
    verify(userService).updateFeatureEntitlements(eq(42L), isA(List.class));
    verify(userService).updateStatus(eq(42L), isA(UserStatus.class));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(updateSystemUser).getBusiness();
    verify(updateSystemUser).getContact();
    verify(updateSystemUser).getDisplayName();
    verify(updateSystemUser, atLeast(1)).getEmail();
    verify(updateSystemUser, atLeast(1)).getEntitlements();
    verify(updateSystemUser).getFirstname();
    verify(updateSystemUser).getKeys();
    verify(updateSystemUser).getLastname();
    verify(updateSystemUser).getRecommendedLanguage();
    verify(updateSystemUser, atLeast(1)).getStatus();
    verify(updateSystemUser).getType();
    verify(updateSystemUser).getUsername();
    verify(updateSystemUser).getUserId();
  }
}
