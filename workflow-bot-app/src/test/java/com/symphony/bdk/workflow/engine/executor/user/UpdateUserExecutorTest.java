package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.UserStatus;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser;
import com.symphony.bdk.workflow.swadl.v1.activity.user.UpdateUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserExecutorTest {

  @Mock
  private ActivityExecutorContext<UpdateUser> context;

  @Mock
  private BdkGateway bdkGateway;

  @Mock
  private UserService userService;

  @Mock
  private V2UserDetail userDetail;

  private UpdateUserExecutor executor;

  @BeforeEach
  void setUp() {
    executor = new UpdateUserExecutor();
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.users()).thenReturn(userService);
  }

  @Test
  void shouldExecuteDelegateToDoExecute() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void shouldUpdateUserWhenEmailIsSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    updateUser.setEmail("test@example.com");
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService).update(eq(123456L), any());
    verify(userService).getUserDetail(123456L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void shouldUpdateUserWhenFirstnameIsSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    updateUser.setFirstname("John");
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService).update(eq(123456L), any());
    verify(userService).getUserDetail(123456L);
  }

  @Test
  void shouldUpdateUserWhenLastnameIsSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    updateUser.setLastname("Doe");
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService).update(eq(123456L), any());
  }

  @Test
  void shouldUpdateUserWhenDisplayNameIsSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    updateUser.setDisplayName("John Doe");
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService).update(eq(123456L), any());
  }

  @Test
  void shouldUpdateUserWhenRecommendedLanguageIsSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    updateUser.setRecommendedLanguage("en");
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService).update(eq(123456L), any());
  }

  @Test
  void shouldUpdateUserWhenContactIsSetWithAllFields() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setSmsNumber("+1234567890");
    contact.setTwoFactorAuthNumber("+1234567890");
    contact.setMobilePhoneNumber("+1234567890");
    contact.setWorkPhoneNumber("+1234567890");
    updateUser.setContact(contact);
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService).update(eq(123456L), any());
  }

  @Test
  void shouldNotUpdateUserWhenContactHasPartialFields() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setSmsNumber("+1234567890");
    updateUser.setContact(contact);
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService, never()).update(anyLong(), any());
  }

  @Test
  void shouldUpdateUserWhenBusinessIsSetWithAllFields() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    CreateUser.Business business = new CreateUser.Business();
    business.setDepartment("Engineering");
    business.setCompanyName("Company");
    business.setAssetClasses(List.of("Asset"));
    business.setDivision("Division");
    business.setFunctions(List.of("Function"));
    business.setIndustries(List.of("Industry"));
    business.setInstruments(List.of("Instrument"));
    business.setLocation("Location");
    business.setJobFunction("Developer");
    business.setMarketCoverages(List.of("Market"));
    business.setTitle("Senior Developer");
    business.setResponsibilities(List.of("Responsibility"));
    updateUser.setBusiness(business);
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService).update(eq(123456L), any());
  }

  @Test
  void shouldNotUpdateUserWhenBusinessHasPartialFields() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    CreateUser.Business business = new CreateUser.Business();
    business.setDepartment("Engineering");
    updateUser.setBusiness(business);
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService, never()).update(anyLong(), any());
  }

  @Test
  void shouldUpdateEntitlementsWhenEntitlementsAreSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);
    updateUser.setEntitlements(entitlements);
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService).updateFeatureEntitlements(eq(123456L), any());
    verify(userService).getUserDetail(123456L);
  }

  @Test
  void shouldNotUpdateEntitlementsWhenEntitlementsAreEmpty() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    updateUser.setEntitlements(new HashMap<>());
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService, never()).updateFeatureEntitlements(anyLong(), any());
  }

  @Test
  void shouldUpdateStatusWhenStatusIsSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    updateUser.setStatus("ENABLED");
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService).updateStatus(eq(123456L), any(UserStatus.class));
    verify(userService).getUserDetail(123456L);
  }

  @Test
  void shouldNotUpdateStatusWhenStatusIsNull() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService, never()).updateStatus(anyLong(), any());
  }

  @Test
  void shouldUpdateAllWhenAllFieldsAreSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    updateUser.setEmail("test@example.com");
    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);
    updateUser.setEntitlements(entitlements);
    updateUser.setStatus("ENABLED");
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService).update(eq(123456L), any());
    verify(userService).updateFeatureEntitlements(eq(123456L), any());
    verify(userService).updateStatus(eq(123456L), any(UserStatus.class));
    verify(userService).getUserDetail(123456L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void shouldReturnFalseWhenShouldUpdateUserWithNoFieldsSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService, never()).update(anyLong(), any());
    verify(userService).getUserDetail(123456L);
  }

  @Test
  void shouldReturnFalseWhenShouldUpdateContactWithNullContact() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService, never()).update(anyLong(), any());
  }

  @Test
  void shouldReturnTrueWhenShouldUpdateContactWithAllFieldsSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setSmsNumber("+1234567890");
    contact.setTwoFactorAuthNumber("+1234567890");
    contact.setMobilePhoneNumber("+1234567890");
    contact.setWorkPhoneNumber("+1234567890");
    updateUser.setContact(contact);
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService).update(eq(123456L), any());
  }

  @Test
  void shouldReturnFalseWhenShouldUpdateBusinessWithNullBusiness() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService, never()).update(anyLong(), any());
  }

  @Test
  void shouldReturnTrueWhenShouldUpdateBusinessWithAllFieldsSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123456");
    CreateUser.Business business = new CreateUser.Business();
    business.setDepartment("Engineering");
    business.setCompanyName("Company");
    business.setAssetClasses(List.of("Asset"));
    business.setDivision("Division");
    business.setFunctions(List.of("Function"));
    business.setIndustries(List.of("Industry"));
    business.setInstruments(List.of("Instrument"));
    business.setLocation("Location");
    business.setJobFunction("Developer");
    business.setMarketCoverages(List.of("Market"));
    business.setTitle("Senior Developer");
    business.setResponsibilities(List.of("Responsibility"));
    updateUser.setBusiness(business);
    when(context.getActivity()).thenReturn(updateUser);
    when(userService.getUserDetail(anyLong())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService).update(eq(123456L), any());
  }
}
