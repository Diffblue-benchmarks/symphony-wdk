package com.symphony.bdk.workflow.engine.executor.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
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
import com.symphony.bdk.gen.api.model.UserSystemInfo;
import com.symphony.bdk.gen.api.model.V2UserCreate;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateSystemUser;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser;
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
   * Test {@link CreateUserExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link CreateSystemUser} {@link CreateSystemUser#getStatus()} return {@code null}.
   *   <li>Then calls {@link UserService#create(V2UserCreate)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given CreateSystemUser getStatus() return 'null'; then calls create(V2UserCreate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateUserExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenCreateSystemUserGetStatusReturnNull_thenCallsCreate() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());

    UserService userService = mock(UserService.class);
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
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

    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getStatus()).thenReturn(null);
    when(createSystemUser.getEntitlements()).thenReturn(null);
    when(createSystemUser.getBusiness()).thenReturn(null);
    when(createSystemUser.getContact()).thenReturn(null);
    when(createSystemUser.getKeys()).thenReturn(null);
    when(createSystemUser.getPassword()).thenReturn(null);
    when(createSystemUser.getDisplayName()).thenReturn("Display Name");
    when(createSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createSystemUser.getFirstname()).thenReturn("Jane");
    when(createSystemUser.getLastname()).thenReturn("Doe");
    when(createSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getUsername()).thenReturn("janedoe");
    when(createSystemUser.getRoles()).thenReturn(new ArrayList<>());

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.execute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(createSystemUser).getBusiness();
    verify(createSystemUser).getContact();
    verify(createSystemUser).getDisplayName();
    verify(createSystemUser).getEmail();
    verify(createSystemUser).getEntitlements();
    verify(createSystemUser).getFirstname();
    verify(createSystemUser).getKeys();
    verify(createSystemUser).getLastname();
    verify(createSystemUser).getPassword();
    verify(createSystemUser).getRecommendedLanguage();
    verify(createSystemUser).getRoles();
    verify(createSystemUser).getStatus();
    verify(createSystemUser).getType();
    verify(createSystemUser).getUsername();
  }

  /**
   * Test {@link CreateUserExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link CreateSystemUser} {@link CreateSystemUser#getStatus()} return {@code null}.
   *   <li>Then calls {@link UserService#create(V2UserCreate)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given CreateSystemUser getStatus() return 'null'; then calls create(V2UserCreate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateUserExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenCreateSystemUserGetStatusReturnNull_thenCallsCreate2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());

    UserService userService = mock(UserService.class);
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
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

    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getStatus()).thenReturn(null);
    when(createSystemUser.getEntitlements()).thenReturn(null);
    when(createSystemUser.getBusiness()).thenReturn(null);
    when(createSystemUser.getContact()).thenReturn(null);
    when(createSystemUser.getKeys()).thenReturn(null);
    when(createSystemUser.getPassword()).thenReturn(null);
    when(createSystemUser.getDisplayName()).thenReturn("Display Name");
    when(createSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createSystemUser.getFirstname()).thenReturn("Jane");
    when(createSystemUser.getLastname()).thenReturn("Doe");
    when(createSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getUsername()).thenReturn("janedoe");
    when(createSystemUser.getRoles()).thenReturn(new ArrayList<>());

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.execute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(createSystemUser).getBusiness();
    verify(createSystemUser).getContact();
    verify(createSystemUser).getDisplayName();
    verify(createSystemUser).getEmail();
    verify(createSystemUser).getEntitlements();
    verify(createSystemUser).getFirstname();
    verify(createSystemUser).getKeys();
    verify(createSystemUser).getLastname();
    verify(createSystemUser).getPassword();
    verify(createSystemUser).getRecommendedLanguage();
    verify(createSystemUser).getRoles();
    verify(createSystemUser).getStatus();
    verify(createSystemUser).getType();
    verify(createSystemUser).getUsername();
  }

  /**
   * Test {@link CreateUserExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code NORMAL} is {@code false}.
   *   <li>Then calls {@link UserService#updateFeatureEntitlements(Long, List)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given HashMap() 'NORMAL' is 'false'; then calls updateFeatureEntitlements(Long, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateUserExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenHashMapNormalIsFalse_thenCallsUpdateFeatureEntitlements() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());

    UserService userService = mock(UserService.class);
    doNothing()
        .when(userService)
        .updateFeatureEntitlements(Mockito.<Long>any(), Mockito.<List<Feature>>any());
    doNothing().when(userService).updateStatus(Mockito.<Long>any(), Mockito.<UserStatus>any());
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
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
    stringResultBooleanMap.put("Creating user", true);

    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getStatus()).thenReturn("Status");
    when(createSystemUser.getEntitlements()).thenReturn(stringResultBooleanMap);
    when(createSystemUser.getBusiness()).thenReturn(null);
    when(createSystemUser.getContact()).thenReturn(null);
    when(createSystemUser.getKeys()).thenReturn(null);
    when(createSystemUser.getPassword()).thenReturn(null);
    when(createSystemUser.getDisplayName()).thenReturn("Display Name");
    when(createSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createSystemUser.getFirstname()).thenReturn("Jane");
    when(createSystemUser.getLastname()).thenReturn("Doe");
    when(createSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getUsername()).thenReturn("janedoe");
    when(createSystemUser.getRoles()).thenReturn(new ArrayList<>());

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.execute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(userService).updateFeatureEntitlements(isNull(), isA(List.class));
    verify(userService).updateStatus(isNull(), isA(UserStatus.class));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(createSystemUser).getBusiness();
    verify(createSystemUser).getContact();
    verify(createSystemUser).getDisplayName();
    verify(createSystemUser).getEmail();
    verify(createSystemUser, atLeast(1)).getEntitlements();
    verify(createSystemUser).getFirstname();
    verify(createSystemUser).getKeys();
    verify(createSystemUser).getLastname();
    verify(createSystemUser).getPassword();
    verify(createSystemUser).getRecommendedLanguage();
    verify(createSystemUser).getRoles();
    verify(createSystemUser, atLeast(1)).getStatus();
    verify(createSystemUser).getType();
    verify(createSystemUser).getUsername();
  }

  /**
   * Test {@link CreateUserExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code NORMAL} is {@code false}.
   *   <li>Then calls {@link UserService#updateFeatureEntitlements(Long, List)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given HashMap() 'NORMAL' is 'false'; then calls updateFeatureEntitlements(Long, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateUserExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenHashMapNormalIsFalse_thenCallsUpdateFeatureEntitlements2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());

    UserService userService = mock(UserService.class);
    doNothing()
        .when(userService)
        .updateFeatureEntitlements(Mockito.<Long>any(), Mockito.<List<Feature>>any());
    doNothing().when(userService).updateStatus(Mockito.<Long>any(), Mockito.<UserStatus>any());
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
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
    stringResultBooleanMap.put("Creating user", true);

    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getStatus()).thenReturn("Status");
    when(createSystemUser.getEntitlements()).thenReturn(stringResultBooleanMap);
    when(createSystemUser.getBusiness()).thenReturn(null);
    when(createSystemUser.getContact()).thenReturn(null);
    when(createSystemUser.getKeys()).thenReturn(null);
    when(createSystemUser.getPassword()).thenReturn(null);
    when(createSystemUser.getDisplayName()).thenReturn("Display Name");
    when(createSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createSystemUser.getFirstname()).thenReturn("Jane");
    when(createSystemUser.getLastname()).thenReturn("Doe");
    when(createSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getUsername()).thenReturn("janedoe");
    when(createSystemUser.getRoles()).thenReturn(new ArrayList<>());

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.execute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(userService).updateFeatureEntitlements(isNull(), isA(List.class));
    verify(userService).updateStatus(isNull(), isA(UserStatus.class));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(createSystemUser).getBusiness();
    verify(createSystemUser).getContact();
    verify(createSystemUser).getDisplayName();
    verify(createSystemUser).getEmail();
    verify(createSystemUser, atLeast(1)).getEntitlements();
    verify(createSystemUser).getFirstname();
    verify(createSystemUser).getKeys();
    verify(createSystemUser).getLastname();
    verify(createSystemUser).getPassword();
    verify(createSystemUser).getRecommendedLanguage();
    verify(createSystemUser).getRoles();
    verify(createSystemUser, atLeast(1)).getStatus();
    verify(createSystemUser).getType();
    verify(createSystemUser).getUsername();
  }

  /**
   * Test {@link CreateUserExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UserService} {@link UserService#updateStatus(Long, UserStatus)} does
   *       nothing.
   *   <li>Then calls {@link UserService#updateStatus(Long, UserStatus)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UserService updateStatus(Long, UserStatus) does nothing; then calls updateStatus(Long, UserStatus)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateUserExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUserServiceUpdateStatusDoesNothing_thenCallsUpdateStatus() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());

    UserService userService = mock(UserService.class);
    doNothing().when(userService).updateStatus(Mockito.<Long>any(), Mockito.<UserStatus>any());
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
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

    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getStatus()).thenReturn("Status");
    when(createSystemUser.getEntitlements()).thenReturn(new HashMap<>());
    when(createSystemUser.getBusiness()).thenReturn(null);
    when(createSystemUser.getContact()).thenReturn(null);
    when(createSystemUser.getKeys()).thenReturn(null);
    when(createSystemUser.getPassword()).thenReturn(null);
    when(createSystemUser.getDisplayName()).thenReturn("Display Name");
    when(createSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createSystemUser.getFirstname()).thenReturn("Jane");
    when(createSystemUser.getLastname()).thenReturn("Doe");
    when(createSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getUsername()).thenReturn("janedoe");
    when(createSystemUser.getRoles()).thenReturn(new ArrayList<>());

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.execute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(userService).updateStatus(isNull(), isA(UserStatus.class));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(createSystemUser).getBusiness();
    verify(createSystemUser).getContact();
    verify(createSystemUser).getDisplayName();
    verify(createSystemUser).getEmail();
    verify(createSystemUser, atLeast(1)).getEntitlements();
    verify(createSystemUser).getFirstname();
    verify(createSystemUser).getKeys();
    verify(createSystemUser).getLastname();
    verify(createSystemUser).getPassword();
    verify(createSystemUser).getRecommendedLanguage();
    verify(createSystemUser).getRoles();
    verify(createSystemUser, atLeast(1)).getStatus();
    verify(createSystemUser).getType();
    verify(createSystemUser).getUsername();
  }

  /**
   * Test {@link CreateUserExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UserService} {@link UserService#updateStatus(Long, UserStatus)} does
   *       nothing.
   *   <li>Then calls {@link UserService#updateStatus(Long, UserStatus)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UserService updateStatus(Long, UserStatus) does nothing; then calls updateStatus(Long, UserStatus)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateUserExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUserServiceUpdateStatusDoesNothing_thenCallsUpdateStatus2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());

    UserService userService = mock(UserService.class);
    doNothing().when(userService).updateStatus(Mockito.<Long>any(), Mockito.<UserStatus>any());
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
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

    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getStatus()).thenReturn("Status");
    when(createSystemUser.getEntitlements()).thenReturn(new HashMap<>());
    when(createSystemUser.getBusiness()).thenReturn(null);
    when(createSystemUser.getContact()).thenReturn(null);
    when(createSystemUser.getKeys()).thenReturn(null);
    when(createSystemUser.getPassword()).thenReturn(null);
    when(createSystemUser.getDisplayName()).thenReturn("Display Name");
    when(createSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createSystemUser.getFirstname()).thenReturn("Jane");
    when(createSystemUser.getLastname()).thenReturn("Doe");
    when(createSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getUsername()).thenReturn("janedoe");
    when(createSystemUser.getRoles()).thenReturn(new ArrayList<>());

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.execute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(userService).updateStatus(isNull(), isA(UserStatus.class));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(createSystemUser).getBusiness();
    verify(createSystemUser).getContact();
    verify(createSystemUser).getDisplayName();
    verify(createSystemUser).getEmail();
    verify(createSystemUser, atLeast(1)).getEntitlements();
    verify(createSystemUser).getFirstname();
    verify(createSystemUser).getKeys();
    verify(createSystemUser).getLastname();
    verify(createSystemUser).getPassword();
    verify(createSystemUser).getRecommendedLanguage();
    verify(createSystemUser).getRoles();
    verify(createSystemUser, atLeast(1)).getStatus();
    verify(createSystemUser).getType();
    verify(createSystemUser).getUsername();
  }

  /**
   * Test {@link CreateUserExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then calls {@link UserService#updateFeatureEntitlements(Long, List)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); then calls updateFeatureEntitlements(Long, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateUserExecutor.execute(ActivityExecutorContext)"})
  void testExecute_thenCallsUpdateFeatureEntitlements() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());

    UserService userService = mock(UserService.class);
    doNothing()
        .when(userService)
        .updateFeatureEntitlements(Mockito.<Long>any(), Mockito.<List<Feature>>any());
    doNothing().when(userService).updateStatus(Mockito.<Long>any(), Mockito.<UserStatus>any());
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
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
    stringResultBooleanMap.put("Creating user", true);

    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getStatus()).thenReturn("Status");
    when(createSystemUser.getEntitlements()).thenReturn(stringResultBooleanMap);
    when(createSystemUser.getBusiness()).thenReturn(null);
    when(createSystemUser.getContact()).thenReturn(null);
    when(createSystemUser.getKeys()).thenReturn(null);
    when(createSystemUser.getPassword()).thenReturn(null);
    when(createSystemUser.getDisplayName()).thenReturn("Display Name");
    when(createSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createSystemUser.getFirstname()).thenReturn("Jane");
    when(createSystemUser.getLastname()).thenReturn("Doe");
    when(createSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getUsername()).thenReturn("janedoe");
    when(createSystemUser.getRoles()).thenReturn(new ArrayList<>());

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.execute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(userService).updateFeatureEntitlements(isNull(), isA(List.class));
    verify(userService).updateStatus(isNull(), isA(UserStatus.class));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(createSystemUser).getBusiness();
    verify(createSystemUser).getContact();
    verify(createSystemUser).getDisplayName();
    verify(createSystemUser).getEmail();
    verify(createSystemUser, atLeast(1)).getEntitlements();
    verify(createSystemUser).getFirstname();
    verify(createSystemUser).getKeys();
    verify(createSystemUser).getLastname();
    verify(createSystemUser).getPassword();
    verify(createSystemUser).getRecommendedLanguage();
    verify(createSystemUser).getRoles();
    verify(createSystemUser, atLeast(1)).getStatus();
    verify(createSystemUser).getType();
    verify(createSystemUser).getUsername();
  }

  /**
   * Test {@link CreateUserExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then calls {@link UserService#updateFeatureEntitlements(Long, List)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); then calls updateFeatureEntitlements(Long, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateUserExecutor.execute(ActivityExecutorContext)"})
  void testExecute_thenCallsUpdateFeatureEntitlements2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());

    UserService userService = mock(UserService.class);
    doNothing()
        .when(userService)
        .updateFeatureEntitlements(Mockito.<Long>any(), Mockito.<List<Feature>>any());
    doNothing().when(userService).updateStatus(Mockito.<Long>any(), Mockito.<UserStatus>any());
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
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
    stringResultBooleanMap.put("Creating user", true);

    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getStatus()).thenReturn("Status");
    when(createSystemUser.getEntitlements()).thenReturn(stringResultBooleanMap);
    when(createSystemUser.getBusiness()).thenReturn(null);
    when(createSystemUser.getContact()).thenReturn(null);
    when(createSystemUser.getKeys()).thenReturn(null);
    when(createSystemUser.getPassword()).thenReturn(null);
    when(createSystemUser.getDisplayName()).thenReturn("Display Name");
    when(createSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createSystemUser.getFirstname()).thenReturn("Jane");
    when(createSystemUser.getLastname()).thenReturn("Doe");
    when(createSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getUsername()).thenReturn("janedoe");
    when(createSystemUser.getRoles()).thenReturn(new ArrayList<>());

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.execute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(userService).updateFeatureEntitlements(isNull(), isA(List.class));
    verify(userService).updateStatus(isNull(), isA(UserStatus.class));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(createSystemUser).getBusiness();
    verify(createSystemUser).getContact();
    verify(createSystemUser).getDisplayName();
    verify(createSystemUser).getEmail();
    verify(createSystemUser, atLeast(1)).getEntitlements();
    verify(createSystemUser).getFirstname();
    verify(createSystemUser).getKeys();
    verify(createSystemUser).getLastname();
    verify(createSystemUser).getPassword();
    verify(createSystemUser).getRecommendedLanguage();
    verify(createSystemUser).getRoles();
    verify(createSystemUser, atLeast(1)).getStatus();
    verify(createSystemUser).getType();
    verify(createSystemUser).getUsername();
  }

  /**
   * Test {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link CreateSystemUser} {@link CreateSystemUser#getStatus()} return {@code null}.
   *   <li>Then calls {@link UserService#create(V2UserCreate)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doExecute(ActivityExecutorContext); given CreateSystemUser getStatus() return 'null'; then calls create(V2UserCreate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateUserExecutor.doExecute(ActivityExecutorContext)"})
  void testDoExecute_givenCreateSystemUserGetStatusReturnNull_thenCallsCreate() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());

    UserService userService = mock(UserService.class);
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
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

    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getStatus()).thenReturn(null);
    when(createSystemUser.getEntitlements()).thenReturn(null);
    when(createSystemUser.getBusiness()).thenReturn(null);
    when(createSystemUser.getContact()).thenReturn(null);
    when(createSystemUser.getKeys()).thenReturn(null);
    when(createSystemUser.getPassword()).thenReturn(null);
    when(createSystemUser.getDisplayName()).thenReturn("Display Name");
    when(createSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createSystemUser.getFirstname()).thenReturn("Jane");
    when(createSystemUser.getLastname()).thenReturn("Doe");
    when(createSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getUsername()).thenReturn("janedoe");
    when(createSystemUser.getRoles()).thenReturn(new ArrayList<>());

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.doExecute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(createSystemUser).getBusiness();
    verify(createSystemUser).getContact();
    verify(createSystemUser).getDisplayName();
    verify(createSystemUser).getEmail();
    verify(createSystemUser).getEntitlements();
    verify(createSystemUser).getFirstname();
    verify(createSystemUser).getKeys();
    verify(createSystemUser).getLastname();
    verify(createSystemUser).getPassword();
    verify(createSystemUser).getRecommendedLanguage();
    verify(createSystemUser).getRoles();
    verify(createSystemUser).getStatus();
    verify(createSystemUser).getType();
    verify(createSystemUser).getUsername();
  }

  /**
   * Test {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link CreateSystemUser} {@link CreateSystemUser#getStatus()} return {@code null}.
   *   <li>Then calls {@link UserService#create(V2UserCreate)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doExecute(ActivityExecutorContext); given CreateSystemUser getStatus() return 'null'; then calls create(V2UserCreate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateUserExecutor.doExecute(ActivityExecutorContext)"})
  void testDoExecute_givenCreateSystemUserGetStatusReturnNull_thenCallsCreate2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());

    UserService userService = mock(UserService.class);
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
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

    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getStatus()).thenReturn(null);
    when(createSystemUser.getEntitlements()).thenReturn(null);
    when(createSystemUser.getBusiness()).thenReturn(null);
    when(createSystemUser.getContact()).thenReturn(null);
    when(createSystemUser.getKeys()).thenReturn(null);
    when(createSystemUser.getPassword()).thenReturn(null);
    when(createSystemUser.getDisplayName()).thenReturn("Display Name");
    when(createSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createSystemUser.getFirstname()).thenReturn("Jane");
    when(createSystemUser.getLastname()).thenReturn("Doe");
    when(createSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getUsername()).thenReturn("janedoe");
    when(createSystemUser.getRoles()).thenReturn(new ArrayList<>());

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.doExecute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(createSystemUser).getBusiness();
    verify(createSystemUser).getContact();
    verify(createSystemUser).getDisplayName();
    verify(createSystemUser).getEmail();
    verify(createSystemUser).getEntitlements();
    verify(createSystemUser).getFirstname();
    verify(createSystemUser).getKeys();
    verify(createSystemUser).getLastname();
    verify(createSystemUser).getPassword();
    verify(createSystemUser).getRecommendedLanguage();
    verify(createSystemUser).getRoles();
    verify(createSystemUser).getStatus();
    verify(createSystemUser).getType();
    verify(createSystemUser).getUsername();
  }

  /**
   * Test {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code NORMAL} is {@code false}.
   *   <li>Then calls {@link UserService#updateFeatureEntitlements(Long, List)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doExecute(ActivityExecutorContext); given HashMap() 'NORMAL' is 'false'; then calls updateFeatureEntitlements(Long, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateUserExecutor.doExecute(ActivityExecutorContext)"})
  void testDoExecute_givenHashMapNormalIsFalse_thenCallsUpdateFeatureEntitlements() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());

    UserService userService = mock(UserService.class);
    doNothing()
        .when(userService)
        .updateFeatureEntitlements(Mockito.<Long>any(), Mockito.<List<Feature>>any());
    doNothing().when(userService).updateStatus(Mockito.<Long>any(), Mockito.<UserStatus>any());
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
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
    stringResultBooleanMap.put("Creating user", true);

    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getStatus()).thenReturn("Status");
    when(createSystemUser.getEntitlements()).thenReturn(stringResultBooleanMap);
    when(createSystemUser.getBusiness()).thenReturn(null);
    when(createSystemUser.getContact()).thenReturn(null);
    when(createSystemUser.getKeys()).thenReturn(null);
    when(createSystemUser.getPassword()).thenReturn(null);
    when(createSystemUser.getDisplayName()).thenReturn("Display Name");
    when(createSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createSystemUser.getFirstname()).thenReturn("Jane");
    when(createSystemUser.getLastname()).thenReturn("Doe");
    when(createSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getUsername()).thenReturn("janedoe");
    when(createSystemUser.getRoles()).thenReturn(new ArrayList<>());

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.doExecute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(userService).updateFeatureEntitlements(isNull(), isA(List.class));
    verify(userService).updateStatus(isNull(), isA(UserStatus.class));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(createSystemUser).getBusiness();
    verify(createSystemUser).getContact();
    verify(createSystemUser).getDisplayName();
    verify(createSystemUser).getEmail();
    verify(createSystemUser, atLeast(1)).getEntitlements();
    verify(createSystemUser).getFirstname();
    verify(createSystemUser).getKeys();
    verify(createSystemUser).getLastname();
    verify(createSystemUser).getPassword();
    verify(createSystemUser).getRecommendedLanguage();
    verify(createSystemUser).getRoles();
    verify(createSystemUser, atLeast(1)).getStatus();
    verify(createSystemUser).getType();
    verify(createSystemUser).getUsername();
  }

  /**
   * Test {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code NORMAL} is {@code false}.
   *   <li>Then calls {@link UserService#updateFeatureEntitlements(Long, List)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doExecute(ActivityExecutorContext); given HashMap() 'NORMAL' is 'false'; then calls updateFeatureEntitlements(Long, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateUserExecutor.doExecute(ActivityExecutorContext)"})
  void testDoExecute_givenHashMapNormalIsFalse_thenCallsUpdateFeatureEntitlements2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());

    UserService userService = mock(UserService.class);
    doNothing()
        .when(userService)
        .updateFeatureEntitlements(Mockito.<Long>any(), Mockito.<List<Feature>>any());
    doNothing().when(userService).updateStatus(Mockito.<Long>any(), Mockito.<UserStatus>any());
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
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
    stringResultBooleanMap.put("Creating user", true);

    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getStatus()).thenReturn("Status");
    when(createSystemUser.getEntitlements()).thenReturn(stringResultBooleanMap);
    when(createSystemUser.getBusiness()).thenReturn(null);
    when(createSystemUser.getContact()).thenReturn(null);
    when(createSystemUser.getKeys()).thenReturn(null);
    when(createSystemUser.getPassword()).thenReturn(null);
    when(createSystemUser.getDisplayName()).thenReturn("Display Name");
    when(createSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createSystemUser.getFirstname()).thenReturn("Jane");
    when(createSystemUser.getLastname()).thenReturn("Doe");
    when(createSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getUsername()).thenReturn("janedoe");
    when(createSystemUser.getRoles()).thenReturn(new ArrayList<>());

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.doExecute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(userService).updateFeatureEntitlements(isNull(), isA(List.class));
    verify(userService).updateStatus(isNull(), isA(UserStatus.class));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(createSystemUser).getBusiness();
    verify(createSystemUser).getContact();
    verify(createSystemUser).getDisplayName();
    verify(createSystemUser).getEmail();
    verify(createSystemUser, atLeast(1)).getEntitlements();
    verify(createSystemUser).getFirstname();
    verify(createSystemUser).getKeys();
    verify(createSystemUser).getLastname();
    verify(createSystemUser).getPassword();
    verify(createSystemUser).getRecommendedLanguage();
    verify(createSystemUser).getRoles();
    verify(createSystemUser, atLeast(1)).getStatus();
    verify(createSystemUser).getType();
    verify(createSystemUser).getUsername();
  }

  /**
   * Test {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UserService} {@link UserService#updateStatus(Long, UserStatus)} does
   *       nothing.
   *   <li>Then calls {@link UserService#updateStatus(Long, UserStatus)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doExecute(ActivityExecutorContext); given UserService updateStatus(Long, UserStatus) does nothing; then calls updateStatus(Long, UserStatus)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateUserExecutor.doExecute(ActivityExecutorContext)"})
  void testDoExecute_givenUserServiceUpdateStatusDoesNothing_thenCallsUpdateStatus() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());

    UserService userService = mock(UserService.class);
    doNothing().when(userService).updateStatus(Mockito.<Long>any(), Mockito.<UserStatus>any());
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
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

    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getStatus()).thenReturn("Status");
    when(createSystemUser.getEntitlements()).thenReturn(new HashMap<>());
    when(createSystemUser.getBusiness()).thenReturn(null);
    when(createSystemUser.getContact()).thenReturn(null);
    when(createSystemUser.getKeys()).thenReturn(null);
    when(createSystemUser.getPassword()).thenReturn(null);
    when(createSystemUser.getDisplayName()).thenReturn("Display Name");
    when(createSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createSystemUser.getFirstname()).thenReturn("Jane");
    when(createSystemUser.getLastname()).thenReturn("Doe");
    when(createSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getUsername()).thenReturn("janedoe");
    when(createSystemUser.getRoles()).thenReturn(new ArrayList<>());

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.doExecute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(userService).updateStatus(isNull(), isA(UserStatus.class));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(createSystemUser).getBusiness();
    verify(createSystemUser).getContact();
    verify(createSystemUser).getDisplayName();
    verify(createSystemUser).getEmail();
    verify(createSystemUser, atLeast(1)).getEntitlements();
    verify(createSystemUser).getFirstname();
    verify(createSystemUser).getKeys();
    verify(createSystemUser).getLastname();
    verify(createSystemUser).getPassword();
    verify(createSystemUser).getRecommendedLanguage();
    verify(createSystemUser).getRoles();
    verify(createSystemUser, atLeast(1)).getStatus();
    verify(createSystemUser).getType();
    verify(createSystemUser).getUsername();
  }

  /**
   * Test {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UserService} {@link UserService#updateStatus(Long, UserStatus)} does
   *       nothing.
   *   <li>Then calls {@link UserService#updateStatus(Long, UserStatus)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doExecute(ActivityExecutorContext); given UserService updateStatus(Long, UserStatus) does nothing; then calls updateStatus(Long, UserStatus)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateUserExecutor.doExecute(ActivityExecutorContext)"})
  void testDoExecute_givenUserServiceUpdateStatusDoesNothing_thenCallsUpdateStatus2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());

    UserService userService = mock(UserService.class);
    doNothing().when(userService).updateStatus(Mockito.<Long>any(), Mockito.<UserStatus>any());
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
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

    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getStatus()).thenReturn("Status");
    when(createSystemUser.getEntitlements()).thenReturn(new HashMap<>());
    when(createSystemUser.getBusiness()).thenReturn(null);
    when(createSystemUser.getContact()).thenReturn(null);
    when(createSystemUser.getKeys()).thenReturn(null);
    when(createSystemUser.getPassword()).thenReturn(null);
    when(createSystemUser.getDisplayName()).thenReturn("Display Name");
    when(createSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createSystemUser.getFirstname()).thenReturn("Jane");
    when(createSystemUser.getLastname()).thenReturn("Doe");
    when(createSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getUsername()).thenReturn("janedoe");
    when(createSystemUser.getRoles()).thenReturn(new ArrayList<>());

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.doExecute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(userService).updateStatus(isNull(), isA(UserStatus.class));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(createSystemUser).getBusiness();
    verify(createSystemUser).getContact();
    verify(createSystemUser).getDisplayName();
    verify(createSystemUser).getEmail();
    verify(createSystemUser, atLeast(1)).getEntitlements();
    verify(createSystemUser).getFirstname();
    verify(createSystemUser).getKeys();
    verify(createSystemUser).getLastname();
    verify(createSystemUser).getPassword();
    verify(createSystemUser).getRecommendedLanguage();
    verify(createSystemUser).getRoles();
    verify(createSystemUser, atLeast(1)).getStatus();
    verify(createSystemUser).getType();
    verify(createSystemUser).getUsername();
  }

  /**
   * Test {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then calls {@link UserService#updateFeatureEntitlements(Long, List)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doExecute(ActivityExecutorContext); then calls updateFeatureEntitlements(Long, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateUserExecutor.doExecute(ActivityExecutorContext)"})
  void testDoExecute_thenCallsUpdateFeatureEntitlements() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());

    UserService userService = mock(UserService.class);
    doNothing()
        .when(userService)
        .updateFeatureEntitlements(Mockito.<Long>any(), Mockito.<List<Feature>>any());
    doNothing().when(userService).updateStatus(Mockito.<Long>any(), Mockito.<UserStatus>any());
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
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
    stringResultBooleanMap.put("Creating user", true);

    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getStatus()).thenReturn("Status");
    when(createSystemUser.getEntitlements()).thenReturn(stringResultBooleanMap);
    when(createSystemUser.getBusiness()).thenReturn(null);
    when(createSystemUser.getContact()).thenReturn(null);
    when(createSystemUser.getKeys()).thenReturn(null);
    when(createSystemUser.getPassword()).thenReturn(null);
    when(createSystemUser.getDisplayName()).thenReturn("Display Name");
    when(createSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createSystemUser.getFirstname()).thenReturn("Jane");
    when(createSystemUser.getLastname()).thenReturn("Doe");
    when(createSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getUsername()).thenReturn("janedoe");
    when(createSystemUser.getRoles()).thenReturn(new ArrayList<>());

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.doExecute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(userService).updateFeatureEntitlements(isNull(), isA(List.class));
    verify(userService).updateStatus(isNull(), isA(UserStatus.class));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(createSystemUser).getBusiness();
    verify(createSystemUser).getContact();
    verify(createSystemUser).getDisplayName();
    verify(createSystemUser).getEmail();
    verify(createSystemUser, atLeast(1)).getEntitlements();
    verify(createSystemUser).getFirstname();
    verify(createSystemUser).getKeys();
    verify(createSystemUser).getLastname();
    verify(createSystemUser).getPassword();
    verify(createSystemUser).getRecommendedLanguage();
    verify(createSystemUser).getRoles();
    verify(createSystemUser, atLeast(1)).getStatus();
    verify(createSystemUser).getType();
    verify(createSystemUser).getUsername();
  }

  /**
   * Test {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then calls {@link UserService#updateFeatureEntitlements(Long, List)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#doExecute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doExecute(ActivityExecutorContext); then calls updateFeatureEntitlements(Long, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateUserExecutor.doExecute(ActivityExecutorContext)"})
  void testDoExecute_thenCallsUpdateFeatureEntitlements2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateUserExecutor createUserExecutor = new CreateUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());

    UserService userService = mock(UserService.class);
    doNothing()
        .when(userService)
        .updateFeatureEntitlements(Mockito.<Long>any(), Mockito.<List<Feature>>any());
    doNothing().when(userService).updateStatus(Mockito.<Long>any(), Mockito.<UserStatus>any());
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
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
    stringResultBooleanMap.put("Creating user", true);

    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getStatus()).thenReturn("Status");
    when(createSystemUser.getEntitlements()).thenReturn(stringResultBooleanMap);
    when(createSystemUser.getBusiness()).thenReturn(null);
    when(createSystemUser.getContact()).thenReturn(null);
    when(createSystemUser.getKeys()).thenReturn(null);
    when(createSystemUser.getPassword()).thenReturn(null);
    when(createSystemUser.getDisplayName()).thenReturn("Display Name");
    when(createSystemUser.getEmail()).thenReturn("jane.doe@example.org");
    when(createSystemUser.getFirstname()).thenReturn("Jane");
    when(createSystemUser.getLastname()).thenReturn("Doe");
    when(createSystemUser.getRecommendedLanguage()).thenReturn("en");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getUsername()).thenReturn("janedoe");
    when(createSystemUser.getRoles()).thenReturn(new ArrayList<>());

    ActivityExecutorContext<CreateUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(createSystemUser);
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createUserExecutor.doExecute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(userService).updateFeatureEntitlements(isNull(), isA(List.class));
    verify(userService).updateStatus(isNull(), isA(UserStatus.class));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(createSystemUser).getBusiness();
    verify(createSystemUser).getContact();
    verify(createSystemUser).getDisplayName();
    verify(createSystemUser).getEmail();
    verify(createSystemUser, atLeast(1)).getEntitlements();
    verify(createSystemUser).getFirstname();
    verify(createSystemUser).getKeys();
    verify(createSystemUser).getLastname();
    verify(createSystemUser).getPassword();
    verify(createSystemUser).getRecommendedLanguage();
    verify(createSystemUser).getRoles();
    verify(createSystemUser, atLeast(1)).getStatus();
    verify(createSystemUser).getType();
    verify(createSystemUser).getUsername();
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
  void testToFeatures_given42_whenHashMap42IsFalse_thenReturnSizeIsTwo2() {
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
   * Test {@link CreateUserExecutor#toFeatures(Map)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code true}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#toFeatures(Map)}
   */
  @Test
  @DisplayName(
      "Test toFeatures(Map); given 'foo'; when HashMap() 'foo' is 'true'; then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List CreateUserExecutor.toFeatures(Map)"})
  void testToFeatures_givenFoo_whenHashMapFooIsTrue_thenReturnSizeIsOne() {
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
   * Test {@link CreateUserExecutor#toFeatures(Map)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code true}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link CreateUserExecutor#toFeatures(Map)}
   */
  @Test
  @DisplayName(
      "Test toFeatures(Map); given 'foo'; when HashMap() 'foo' is 'true'; then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List CreateUserExecutor.toFeatures(Map)"})
  void testToFeatures_givenFoo_whenHashMapFooIsTrue_thenReturnSizeIsOne2() {
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
  void testToFeatures_whenHashMap_thenReturnEmpty2() {
    // Arrange and Act
    List<Feature> actualToFeaturesResult = CreateUserExecutor.toFeatures(new HashMap<>());

    // Assert
    assertTrue(actualToFeaturesResult.isEmpty());
  }
}
