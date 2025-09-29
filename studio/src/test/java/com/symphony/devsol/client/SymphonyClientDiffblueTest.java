package com.symphony.devsol.client;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.symphony.bdk.app.spring.exception.GlobalControllerExceptionHandler;
import com.symphony.bdk.core.auth.jwt.UserClaim;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.UserSearchQuery;
import com.symphony.bdk.gen.api.model.UserV2;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

@ContextConfiguration(classes = {SymphonyClient.class, GlobalControllerExceptionHandler.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class SymphonyClientDiffblueTest {
  @Autowired private GlobalControllerExceptionHandler globalControllerExceptionHandler;

  @MockBean private SessionService sessionService;

  @Autowired private SymphonyClient symphonyClient;

  @MockBean private UserService userService;

  /**
   * Test {@link SymphonyClient#getAppId()}.
   *
   * <ul>
   *   <li>Then content contentType {@code application/problem+json}.
   * </ul>
   *
   * <p>Method under test: {@link SymphonyClient#getAppId()}
   */
  @Test
  @DisplayName("Test getAppId(); then content contentType 'application/problem+json'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Map SymphonyClient.getAppId()"})
  void testGetAppId_thenContentContentTypeApplicationProblemJson() throws Exception {
    // Arrange
    when(sessionService.getSession()).thenThrow(new ResponseStatusException(HttpStatus.OK));

    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bdk/v1/app/info");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/problem+json"))
        .andExpect(
            content()
                .string(
                    "{\"type\":\"about:blank\",\"title\":\"OK\",\"status\":200,\"instance\":\"/bdk/v1/app/info\"}"));
  }

  /**
   * Test {@link SymphonyClient#getProfile(UserClaim)}.
   *
   * <p>Method under test: {@link SymphonyClient#getProfile(UserClaim)}
   */
  @Test
  @DisplayName("Test getProfile(UserClaim)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"com.symphony.devsol.model.wdk.Profile SymphonyClient.getProfile(UserClaim)"})
  void testGetProfile() throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/symphony/profile");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(status().is(400))
        .andExpect(content().contentType("application/problem+json"))
        .andExpect(
            content()
                .string(
                    "{\"type\":\"about:blank\",\"title\":\"Bad Request\",\"status\":400,\"instance\":\"/symphony/profile\"}"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUser(String)} with {@code q}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link UserV2} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SymphonyClient#getSymphonyUser(String)}
   */
  @Test
  @DisplayName(
      "Test getSymphonyUser(String) with 'q'; given ArrayList() add UserV2 (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List SymphonyClient.getSymphonyUser(String)"})
  void testGetSymphonyUserWithQ_givenArrayListAddUserV2() throws Exception {
    // Arrange
    ArrayList<UserV2> userV2List = new ArrayList<>();
    userV2List.add(new UserV2());
    when(userService.searchUsers(Mockito.<UserSearchQuery>any(), Mockito.<Boolean>any()))
        .thenReturn(userV2List);

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/symphony/user").param("q", "foo");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string("[]"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUser(String)} with {@code q}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link UserV2} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SymphonyClient#getSymphonyUser(String)}
   */
  @Test
  @DisplayName(
      "Test getSymphonyUser(String) with 'q'; given ArrayList() add UserV2 (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List SymphonyClient.getSymphonyUser(String)"})
  void testGetSymphonyUserWithQ_givenArrayListAddUserV22() throws Exception {
    // Arrange
    ArrayList<UserV2> userV2List = new ArrayList<>();
    userV2List.add(new UserV2());
    userV2List.add(new UserV2());
    when(userService.searchUsers(Mockito.<UserSearchQuery>any(), Mockito.<Boolean>any()))
        .thenReturn(userV2List);

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/symphony/user").param("q", "foo");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string("[]"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUser(String)} with {@code q}.
   *
   * <ul>
   *   <li>Then content contentType {@code application/json}.
   * </ul>
   *
   * <p>Method under test: {@link SymphonyClient#getSymphonyUser(String)}
   */
  @Test
  @DisplayName("Test getSymphonyUser(String) with 'q'; then content contentType 'application/json'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List SymphonyClient.getSymphonyUser(String)"})
  void testGetSymphonyUserWithQ_thenContentContentTypeApplicationJson() throws Exception {
    // Arrange
    when(userService.searchUsers(Mockito.<UserSearchQuery>any(), Mockito.<Boolean>any()))
        .thenReturn(new ArrayList<>());

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/symphony/user").param("q", "foo");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string("[]"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUser(String)} with {@code q}.
   *
   * <ul>
   *   <li>Then content contentType {@code application/problem+json}.
   * </ul>
   *
   * <p>Method under test: {@link SymphonyClient#getSymphonyUser(String)}
   */
  @Test
  @DisplayName(
      "Test getSymphonyUser(String) with 'q'; then content contentType 'application/problem+json'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List SymphonyClient.getSymphonyUser(String)"})
  void testGetSymphonyUserWithQ_thenContentContentTypeApplicationProblemJson() throws Exception {
    // Arrange
    when(userService.searchUsers(Mockito.<UserSearchQuery>any(), Mockito.<Boolean>any()))
        .thenThrow(new ResponseStatusException(HttpStatus.OK));

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/symphony/user").param("q", "foo");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/problem+json"))
        .andExpect(
            content()
                .string(
                    "{\"type\":\"about:blank\",\"title\":\"OK\",\"status\":200,\"instance\":\"/symphony/user\"}"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUser(long)} with {@code userId}.
   *
   * <p>Method under test: {@link SymphonyClient#getSymphonyUser(long)}
   */
  @Test
  @DisplayName("Test getSymphonyUser(long) with 'userId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.devsol.model.wdk.SimpleUser SymphonyClient.getSymphonyUser(long)"
  })
  void testGetSymphonyUserWithUserId() throws Exception {
    // Arrange
    when(userService.listUsersByIds(Mockito.<List<Long>>any()))
        .thenThrow(new ResponseStatusException(HttpStatus.OK));

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/symphony/user/{userId}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/problem+json"))
        .andExpect(
            content()
                .string(
                    "{\"type\":\"about:blank\",\"title\":\"OK\",\"status\":200,\"instance\":\"/symphony/user/1\"}"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUser(long)} with {@code userId}.
   *
   * <ul>
   *   <li>Then content contentType {@code application/json}.
   * </ul>
   *
   * <p>Method under test: {@link SymphonyClient#getSymphonyUser(long)}
   */
  @Test
  @DisplayName(
      "Test getSymphonyUser(long) with 'userId'; then content contentType 'application/json'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.devsol.model.wdk.SimpleUser SymphonyClient.getSymphonyUser(long)"
  })
  void testGetSymphonyUserWithUserId_thenContentContentTypeApplicationJson() throws Exception {
    // Arrange
    ArrayList<UserV2> userV2List = new ArrayList<>();
    userV2List.add(new UserV2());
    when(userService.listUsersByIds(Mockito.<List<Long>>any())).thenReturn(userV2List);

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/symphony/user/{userId}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string("{\"id\":null,\"displayName\":null}"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUser(long)} with {@code userId}.
   *
   * <ul>
   *   <li>Then status {@link StatusResultMatchers#isNotFound()}.
   * </ul>
   *
   * <p>Method under test: {@link SymphonyClient#getSymphonyUser(long)}
   */
  @Test
  @DisplayName("Test getSymphonyUser(long) with 'userId'; then status isNotFound()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.devsol.model.wdk.SimpleUser SymphonyClient.getSymphonyUser(long)"
  })
  void testGetSymphonyUserWithUserId_thenStatusIsNotFound() throws Exception {
    // Arrange
    when(userService.listUsersByIds(Mockito.<List<Long>>any())).thenReturn(new ArrayList<>());

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/symphony/user/{userId}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isNotFound())
        .andExpect(content().contentType("application/problem+json"))
        .andExpect(
            content()
                .string(
                    "{\"type\":\"about:blank\",\"title\":\"Not Found\",\"status\":404,\"detail\":\"No such user\",\"instance\":\"/symphony"
                        + "/user/1\"}"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUsers(List)}.
   *
   * <ul>
   *   <li>Then content contentType {@code application/json}.
   * </ul>
   *
   * <p>Method under test: {@link SymphonyClient#getSymphonyUsers(List)}
   */
  @Test
  @DisplayName("Test getSymphonyUsers(List); then content contentType 'application/json'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Map SymphonyClient.getSymphonyUsers(List)"})
  void testGetSymphonyUsers_thenContentContentTypeApplicationJson() throws Exception {
    // Arrange
    when(userService.listUsersByIds(Mockito.<List<Long>>any())).thenReturn(new ArrayList<>());

    MockHttpServletRequestBuilder contentTypeResult =
        MockMvcRequestBuilders.post("/symphony/users").contentType(MediaType.APPLICATION_JSON);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    MockHttpServletRequestBuilder requestBuilder =
        contentTypeResult.content(jsonMapper.writeValueAsString(new ArrayList<>()));

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string("{}"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUsers(List)}.
   *
   * <ul>
   *   <li>Then content contentType {@code application/problem+json}.
   * </ul>
   *
   * <p>Method under test: {@link SymphonyClient#getSymphonyUsers(List)}
   */
  @Test
  @DisplayName("Test getSymphonyUsers(List); then content contentType 'application/problem+json'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Map SymphonyClient.getSymphonyUsers(List)"})
  void testGetSymphonyUsers_thenContentContentTypeApplicationProblemJson() throws Exception {
    // Arrange
    when(userService.listUsersByIds(Mockito.<List<Long>>any()))
        .thenThrow(new ResponseStatusException(HttpStatus.OK));

    MockHttpServletRequestBuilder contentTypeResult =
        MockMvcRequestBuilders.post("/symphony/users").contentType(MediaType.APPLICATION_JSON);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    MockHttpServletRequestBuilder requestBuilder =
        contentTypeResult.content(jsonMapper.writeValueAsString(new ArrayList<>()));

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/problem+json"))
        .andExpect(
            content()
                .string(
                    "{\"type\":\"about:blank\",\"title\":\"OK\",\"status\":200,\"instance\":\"/symphony/users\"}"));
  }
}
