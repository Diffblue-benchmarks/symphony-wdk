package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser;
import com.symphony.bdk.workflow.swadl.v1.activity.user.UpdateUser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserExecutorTest {

  @InjectMocks
  private UpdateUserExecutor executor;

  @Mock
  private ActivityExecutorContext<UpdateUser> context;

  @Mock
  private BdkGateway bdk;

  @Mock
  private UserService userService;

  private void setUpContextMocks(UpdateUser updateUser) {
    when(context.getActivity()).thenReturn(updateUser);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.users()).thenReturn(userService);
    when(userService.getUserDetail(anyLong())).thenReturn(mock(V2UserDetail.class));
  }

  @Test
  void shouldCallDoExecuteWhenExecuteIsCalled() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("123");
    setUpContextMocks(updateUser);

    executor.execute(context);

    verify(userService).getUserDetail(123L);
  }

  @Test
  void shouldUpdateUserWhenEmailIsSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("456");
    updateUser.setEmail("test@example.com");
    setUpContextMocks(updateUser);

    executor.doExecute(context);

    verify(userService).update(eq(456L), any());
  }

  @Test
  void shouldUpdateUserWhenFirstnameIsSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("400");
    updateUser.setFirstname("John");
    setUpContextMocks(updateUser);

    executor.doExecute(context);

    verify(userService).update(eq(400L), any());
  }

  @Test
  void shouldUpdateUserWhenLastnameIsSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("401");
    updateUser.setLastname("Doe");
    setUpContextMocks(updateUser);

    executor.doExecute(context);

    verify(userService).update(eq(401L), any());
  }

  @Test
  void shouldUpdateUserWhenDisplayNameIsSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("402");
    updateUser.setDisplayName("John Doe");
    setUpContextMocks(updateUser);

    executor.doExecute(context);

    verify(userService).update(eq(402L), any());
  }

  @Test
  void shouldUpdateUserWhenRecommendedLanguageIsSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("403");
    updateUser.setRecommendedLanguage("en");
    setUpContextMocks(updateUser);

    executor.doExecute(context);

    verify(userService).update(eq(403L), any());
  }

  @Test
  void shouldNotUpdateUserWhenNoUserFieldsAreSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("789");
    setUpContextMocks(updateUser);

    executor.doExecute(context);

    verify(userService, never()).update(anyLong(), any());
  }

  @Test
  void shouldUpdateEntitlementsWhenEntitlementsAreSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("100");
    updateUser.setEntitlements(Map.of("feature1", true));
    setUpContextMocks(updateUser);

    executor.doExecute(context);

    verify(userService).updateFeatureEntitlements(eq(100L), any());
  }

  @Test
  void shouldNotUpdateEntitlementsWhenEntitlementsAreEmpty() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("101");
    updateUser.setEntitlements(Collections.emptyMap());
    setUpContextMocks(updateUser);

    executor.doExecute(context);

    verify(userService, never()).updateFeatureEntitlements(anyLong(), any());
  }

  @Test
  void shouldUpdateStatusWhenStatusIsSet() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("200");
    updateUser.setStatus("ENABLED");
    setUpContextMocks(updateUser);

    executor.doExecute(context);

    verify(userService).updateStatus(eq(200L), any());
  }

  @Test
  void shouldNotUpdateStatusWhenStatusIsNull() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("201");
    setUpContextMocks(updateUser);

    executor.doExecute(context);

    verify(userService, never()).updateStatus(anyLong(), any());
  }

  @Test
  void shouldSetOutputVariableAfterDoExecute() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("300");
    V2UserDetail userDetail = mock(V2UserDetail.class);
    when(context.getActivity()).thenReturn(updateUser);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.users()).thenReturn(userService);
    when(userService.getUserDetail(300L)).thenReturn(userDetail);

    executor.doExecute(context);

    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void shouldNotUpdateUserWhenContactIsNull() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("500");
    updateUser.setContact(null);
    setUpContextMocks(updateUser);

    executor.doExecute(context);

    verify(userService, never()).update(anyLong(), any());
  }

  @Test
  void shouldNotUpdateUserWhenContactHasMissingFields() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("501");
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setSmsNumber("1234567890");
    updateUser.setContact(contact);
    setUpContextMocks(updateUser);

    executor.doExecute(context);

    verify(userService, never()).update(anyLong(), any());
  }

  @Test
  void shouldUpdateUserWhenContactHasAllFields() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("502");
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setSmsNumber("111");
    contact.setTwoFactorAuthNumber("222");
    contact.setMobilePhoneNumber("333");
    contact.setWorkPhoneNumber("444");
    updateUser.setContact(contact);
    setUpContextMocks(updateUser);

    executor.doExecute(context);

    verify(userService).update(eq(502L), any());
  }

  @Test
  void shouldNotUpdateUserWhenBusinessIsNull() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("600");
    updateUser.setBusiness(null);
    setUpContextMocks(updateUser);

    executor.doExecute(context);

    verify(userService, never()).update(anyLong(), any());
  }

  @Test
  void shouldNotUpdateUserWhenBusinessHasMissingFields() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("601");
    CreateUser.Business business = new CreateUser.Business();
    business.setDepartment("Engineering");
    updateUser.setBusiness(business);
    setUpContextMocks(updateUser);

    executor.doExecute(context);

    verify(userService, never()).update(anyLong(), any());
  }

  @Test
  void shouldUpdateUserWhenBusinessHasAllFields() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUserId("602");
    CreateUser.Business business = new CreateUser.Business();
    business.setDepartment("Engineering");
    business.setCompanyName("Acme");
    business.setAssetClasses(Collections.singletonList("Equity"));
    business.setDivision("R&D");
    business.setFunctions(Collections.singletonList("Dev"));
    business.setIndustries(Collections.singletonList("Tech"));
    business.setInstruments(Collections.singletonList("Stocks"));
    business.setLocation("NYC");
    business.setJobFunction("Engineer");
    business.setMarketCoverages(Collections.singletonList("EMEA"));
    business.setTitle("Senior Engineer");
    business.setResponsibilities(Collections.singletonList("Coding"));
    updateUser.setBusiness(business);
    setUpContextMocks(updateUser);

    executor.doExecute(context);

    verify(userService).update(eq(602L), any());
  }
}
