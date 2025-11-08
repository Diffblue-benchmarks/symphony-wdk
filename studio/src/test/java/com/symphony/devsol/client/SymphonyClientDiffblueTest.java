package com.symphony.devsol.client;

import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

@ContextConfiguration(classes = {SymphonyClient.class, GlobalControllerExceptionHandler.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SymphonyClientDiffblueTest {
  @Autowired
  private GlobalControllerExceptionHandler globalControllerExceptionHandler;

  @MockBean
  private SessionService sessionService;

  @Autowired
  private SymphonyClient symphonyClient;

  @MockBean
  private UserService userService;

  /**
   * Test {@link SymphonyClient#getAppId()}.
   * <p>
   * Method under test: {@link SymphonyClient#getAppId()}
   */
  @Test
  @DisplayName("Test getAppId()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.Map SymphonyClient.getAppId()"})
  void testGetAppId() throws Exception {
    // Arrange
    UserV2 userV2 = new UserV2();
    userV2.displayName("appId");
    when(sessionService.getSession()).thenReturn(userV2);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bdk/v1/app/info");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("{\"name\":\"appId\",\"appId\":\"${bdk.app.appId}\"}"));
  }

  /**
   * Test {@link SymphonyClient#getProfile(UserClaim)}.
   * <p>
   * Method under test: {@link SymphonyClient#getProfile(UserClaim)}
   */
  @Test
  @DisplayName("Test getProfile(UserClaim)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"com.symphony.devsol.model.wdk.Profile SymphonyClient.getProfile(UserClaim)"})
  void testGetProfile() throws Exception {
    // Arrange
    UserClaim userClaim = new UserClaim();
    userClaim.setAvatarSmallUrl("https://example.org/example");
    userClaim.setAvatarUrl("https://example.org/example");
    userClaim.setCompany("Company");
    userClaim.setCompanyId("42");
    userClaim.setDisplayName("Display Name");
    userClaim.setEmailAddress("42 Main St");
    userClaim.setFirstName("Jane");
    userClaim.setId(1L);
    userClaim.setLastName("Doe");
    userClaim.setLocation("Location");
    userClaim.setTitle("Dr");
    userClaim.setUsername("janedoe");
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/symphony/profile")
        .param("user", String.valueOf(userClaim));

    // Act
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder);

    // Assert
    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
        .andExpect(MockMvcResultMatchers.content().contentType("application/problem+json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"type\":\"about:blank\",\"title\":\"Bad Request\",\"status\":400,\"instance\":\"/symphony/profile\"}"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUser(String)} with {@code q}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link UserV2} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link SymphonyClient#getSymphonyUser(String)}
   */
  @Test
  @DisplayName("Test getSymphonyUser(String) with 'q'; given ArrayList() add UserV2 (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List SymphonyClient.getSymphonyUser(String)"})
  void testGetSymphonyUserWithQ_givenArrayListAddUserV2() throws Exception {
    // Arrange
    ArrayList<UserV2> userV2List = new ArrayList<>();
    userV2List.add(new UserV2());
    when(userService.searchUsers(Mockito.<UserSearchQuery>any(), Mockito.<Boolean>any())).thenReturn(userV2List);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/symphony/user").param("q", "foo");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUser(String)} with {@code q}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link UserV2} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link SymphonyClient#getSymphonyUser(String)}
   */
  @Test
  @DisplayName("Test getSymphonyUser(String) with 'q'; given ArrayList() add UserV2 (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List SymphonyClient.getSymphonyUser(String)"})
  void testGetSymphonyUserWithQ_givenArrayListAddUserV22() throws Exception {
    // Arrange
    ArrayList<UserV2> userV2List = new ArrayList<>();
    userV2List.add(new UserV2());
    userV2List.add(new UserV2());
    when(userService.searchUsers(Mockito.<UserSearchQuery>any(), Mockito.<Boolean>any())).thenReturn(userV2List);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/symphony/user").param("q", "foo");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUser(String)} with {@code q}.
   * <ul>
   *   <li>Then content contentType {@code application/json}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SymphonyClient#getSymphonyUser(String)}
   */
  @Test
  @DisplayName("Test getSymphonyUser(String) with 'q'; then content contentType 'application/json'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List SymphonyClient.getSymphonyUser(String)"})
  void testGetSymphonyUserWithQ_thenContentContentTypeApplicationJson() throws Exception {
    // Arrange
    when(userService.searchUsers(Mockito.<UserSearchQuery>any(), Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/symphony/user").param("q", "foo");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUser(String)} with {@code q}.
   * <ul>
   *   <li>Then content contentType {@code application/problem+json}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SymphonyClient#getSymphonyUser(String)}
   */
  @Test
  @DisplayName("Test getSymphonyUser(String) with 'q'; then content contentType 'application/problem+json'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List SymphonyClient.getSymphonyUser(String)"})
  void testGetSymphonyUserWithQ_thenContentContentTypeApplicationProblemJson() throws Exception {
    // Arrange
    when(userService.searchUsers(Mockito.<UserSearchQuery>any(), Mockito.<Boolean>any()))
        .thenThrow(new ResponseStatusException(HttpStatusCode.valueOf(200)));
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/symphony/user").param("q", "foo");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/problem+json"))
        .andExpect(MockMvcResultMatchers.content()
            .string("{\"type\":\"about:blank\",\"title\":\"OK\",\"status\":200,\"instance\":\"/symphony/user\"}"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUser(long)} with {@code userId}.
   * <p>
   * Method under test: {@link SymphonyClient#getSymphonyUser(long)}
   */
  @Test
  @DisplayName("Test getSymphonyUser(long) with 'userId'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"com.symphony.devsol.model.wdk.SimpleUser SymphonyClient.getSymphonyUser(long)"})
  void testGetSymphonyUserWithUserId() throws Exception {
    // Arrange
    when(userService.listUsersByIds(Mockito.<List<Long>>any()))
        .thenThrow(new ResponseStatusException(HttpStatusCode.valueOf(200)));
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/symphony/user/{userId}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/problem+json"))
        .andExpect(MockMvcResultMatchers.content()
            .string("{\"type\":\"about:blank\",\"title\":\"OK\",\"status\":200,\"instance\":\"/symphony/user/1\"}"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUser(long)} with {@code userId}.
   * <ul>
   *   <li>Then content contentType {@code application/json}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SymphonyClient#getSymphonyUser(long)}
   */
  @Test
  @DisplayName("Test getSymphonyUser(long) with 'userId'; then content contentType 'application/json'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"com.symphony.devsol.model.wdk.SimpleUser SymphonyClient.getSymphonyUser(long)"})
  void testGetSymphonyUserWithUserId_thenContentContentTypeApplicationJson() throws Exception {
    // Arrange
    ArrayList<UserV2> userV2List = new ArrayList<>();
    userV2List.add(new UserV2());
    when(userService.listUsersByIds(Mockito.<List<Long>>any())).thenReturn(userV2List);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/symphony/user/{userId}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("{\"id\":null,\"displayName\":null}"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUser(long)} with {@code userId}.
   * <ul>
   *   <li>Then status {@link StatusResultMatchers#isNotFound()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SymphonyClient#getSymphonyUser(long)}
   */
  @Test
  @DisplayName("Test getSymphonyUser(long) with 'userId'; then status isNotFound()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"com.symphony.devsol.model.wdk.SimpleUser SymphonyClient.getSymphonyUser(long)"})
  void testGetSymphonyUserWithUserId_thenStatusIsNotFound() throws Exception {
    // Arrange
    when(userService.listUsersByIds(Mockito.<List<Long>>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/symphony/user/{userId}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.content().contentType("application/problem+json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"type\":\"about:blank\",\"title\":\"Not Found\",\"status\":404,\"detail\":\"No such user\",\"instance\":\"/symphony"
                    + "/user/1\"}"));
  }

  /**
   * Test {@link SymphonyClient#getSymphonyUsers(List)}.
   * <p>
   * Method under test: {@link SymphonyClient#getSymphonyUsers(List)}
   */
  @Test
  @DisplayName("Test getSymphonyUsers(List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.Map SymphonyClient.getSymphonyUsers(List)"})
  void testGetSymphonyUsers() throws Exception {
    // Arrange
    when(userService.listUsersByIds(Mockito.<List<Long>>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/symphony/users")
        .contentType(MediaType.APPLICATION_JSON);

    ObjectMapper objectMapper = new ObjectMapper();
    MockHttpServletRequestBuilder requestBuilder = contentTypeResult
        .content(objectMapper.writeValueAsString(new ArrayList<>()));

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .setControllerAdvice(globalControllerExceptionHandler)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("{}"));
  }
}
