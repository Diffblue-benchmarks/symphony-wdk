package com.symphony.devsol.client;

import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.symphony.bdk.core.auth.jwt.UserClaim;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.UserSearchQuery;
import com.symphony.bdk.gen.api.model.UserV2;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

@ContextConfiguration(classes = {SymphonyClient.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SymphonyClientDiffblueTest {
  @MockBean
  private SessionService sessionService;

  @Autowired
  private SymphonyClient symphonyClient;

  @MockBean
  private UserService userService;

  /**
   * Method under test: {@link SymphonyClient#getAppId()}
   */
  @Test
  void testGetAppId() throws Exception {
    // Arrange
    UserV2 userV2 = new UserV2();
    userV2.displayName("appId");
    when(sessionService.getSession()).thenReturn(userV2);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bdk/v1/app/info");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("{\"name\":\"appId\",\"appId\":\"${bdk.app.appId}\"}"));
  }

  /**
   * Method under test: {@link SymphonyClient#getProfile(UserClaim)}
   */
  @Test
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
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(symphonyClient).build().perform(requestBuilder);

    // Assert
    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
  }

  /**
   * Method under test: {@link SymphonyClient#getSymphonyUser(long)}
   */
  @Test
  void testGetSymphonyUser() throws Exception {
    // Arrange
    when(userService.listUsersByIds(Mockito.<List<Long>>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/symphony/user/{userId}", 1L);

    // Act
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(symphonyClient).build().perform(requestBuilder);

    // Assert
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  /**
   * Method under test: {@link SymphonyClient#getSymphonyUser(long)}
   */
  @Test
  void testGetSymphonyUser2() throws Exception {
    // Arrange
    ArrayList<UserV2> userV2List = new ArrayList<>();
    userV2List.add(new UserV2());
    when(userService.listUsersByIds(Mockito.<List<Long>>any())).thenReturn(userV2List);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/symphony/user/{userId}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("{\"id\":null,\"displayName\":null}"));
  }

  /**
   * Method under test: {@link SymphonyClient#getSymphonyUser(long)}
   */
  @Test
  void testGetSymphonyUser3() throws Exception {
    // Arrange
    when(userService.listUsersByIds(Mockito.<List<Long>>any()))
        .thenThrow(new ResponseStatusException(HttpStatusCode.valueOf(200)));
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/symphony/user/{userId}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  /**
   * Method under test: {@link SymphonyClient#getSymphonyUser(String)}
   */
  @Test
  void testGetSymphonyUser4() throws Exception {
    // Arrange
    when(userService.searchUsers(Mockito.<UserSearchQuery>any(), Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/symphony/user").param("q", "foo");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Method under test: {@link SymphonyClient#getSymphonyUser(String)}
   */
  @Test
  void testGetSymphonyUser5() throws Exception {
    // Arrange
    ArrayList<UserV2> userV2List = new ArrayList<>();
    userV2List.add(new UserV2());
    when(userService.searchUsers(Mockito.<UserSearchQuery>any(), Mockito.<Boolean>any())).thenReturn(userV2List);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/symphony/user").param("q", "foo");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Method under test: {@link SymphonyClient#getSymphonyUser(String)}
   */
  @Test
  void testGetSymphonyUser6() throws Exception {
    // Arrange
    ArrayList<UserV2> userV2List = new ArrayList<>();
    userV2List.add(new UserV2());
    userV2List.add(new UserV2());
    when(userService.searchUsers(Mockito.<UserSearchQuery>any(), Mockito.<Boolean>any())).thenReturn(userV2List);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/symphony/user").param("q", "foo");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Method under test: {@link SymphonyClient#getSymphonyUser(String)}
   */
  @Test
  void testGetSymphonyUser7() throws Exception {
    // Arrange
    when(userService.searchUsers(Mockito.<UserSearchQuery>any(), Mockito.<Boolean>any()))
        .thenThrow(new ResponseStatusException(HttpStatusCode.valueOf(200)));
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/symphony/user").param("q", "foo");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(symphonyClient)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  /**
   * Method under test: {@link SymphonyClient#getSymphonyUsers(List)}
   */
  @Test
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
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("{}"));
  }
}
