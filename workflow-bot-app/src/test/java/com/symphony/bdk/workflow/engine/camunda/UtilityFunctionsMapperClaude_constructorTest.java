package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.gen.api.model.UserV2;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SharedDataStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UtilityFunctionsMapperClaude_constructorTest {

  private SessionService sessionService;
  private SharedDataStore sharedDataStore;
  private SecretKeeper secretKeeper;

  @BeforeEach
  void setUp() {
    sessionService = mock(SessionService.class);
    sharedDataStore = mock(SharedDataStore.class);
    secretKeeper = mock(SecretKeeper.class);
  }

  @AfterEach
  void tearDown() {
    // Clean up static state to avoid test pollution
    UtilityFunctionsMapper.setStaticSessionService(null);
    UtilityFunctionsMapper.setSharedStateService(null);
    UtilityFunctionsMapper.setSecretKeeper(null);
  }

  @Test
  void constructor_shouldCreateInstanceWithAllParameters() {
    // When: Constructor is called with all parameters
    UtilityFunctionsMapper mapper = new UtilityFunctionsMapper(sessionService, sharedDataStore, secretKeeper);

    // Then: An instance should be created
    assertThat(mapper).isNotNull();
    assertThat(mapper).isInstanceOf(UtilityFunctionsMapper.class);
  }

  @Test
  void constructor_shouldSetStaticSessionService() {
    // Given: A SessionService that returns a specific user
    UserV2 expectedUser = new UserV2();
    expectedUser.setId(12345L);
    when(sessionService.getSession()).thenReturn(expectedUser);

    // When: Constructor is called
    new UtilityFunctionsMapper(sessionService, sharedDataStore, secretKeeper);

    // Then: The static session service should be set and usable via session()
    UserV2 actualUser = UtilityFunctionsMapper.session();
    assertThat(actualUser).isSameAs(expectedUser);
  }

  @Test
  void constructor_shouldSetSharedDataStore() {
    // Given: A SharedDataStore with test data
    Map<String, Object> testData = new HashMap<>();
    testData.put("key", "value");
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(testData);

    // When: Constructor is called
    new UtilityFunctionsMapper(sessionService, sharedDataStore, secretKeeper);

    // Then: The static shared data store should be set and usable via readShared()
    Object actualValue = UtilityFunctionsMapper.readShared("ns", "key");
    assertThat(actualValue).isEqualTo("value");
  }

  @Test
  void constructor_shouldSetSecretKeeper() {
    // Given: A SecretKeeper with test secrets
    when(secretKeeper.get("apiKey")).thenReturn("secret-value");

    // When: Constructor is called
    new UtilityFunctionsMapper(sessionService, sharedDataStore, secretKeeper);

    // Then: The static secret keeper should be set and usable via secret()
    String actualSecret = UtilityFunctionsMapper.secret("apiKey");
    assertThat(actualSecret).isEqualTo("secret-value");
  }

  @Test
  void constructor_shouldSetAllThreeStaticFields() {
    // Given: All three services with test data
    UserV2 user = new UserV2();
    user.setId(999L);
    when(sessionService.getSession()).thenReturn(user);

    Map<String, Object> sharedData = new HashMap<>();
    sharedData.put("sharedKey", "sharedValue");
    when(sharedDataStore.getNamespaceData("namespace")).thenReturn(sharedData);

    when(secretKeeper.get("secretKey")).thenReturn("secretValue");

    // When: Constructor is called
    new UtilityFunctionsMapper(sessionService, sharedDataStore, secretKeeper);

    // Then: All three static fields should be set and usable
    UserV2 actualUser = UtilityFunctionsMapper.session();
    Object actualShared = UtilityFunctionsMapper.readShared("namespace", "sharedKey");
    String actualSecret = UtilityFunctionsMapper.secret("secretKey");

    assertThat(actualUser.getId()).isEqualTo(999L);
    assertThat(actualShared).isEqualTo("sharedValue");
    assertThat(actualSecret).isEqualTo("secretValue");
  }

  @Test
  void constructor_withNullSessionService_shouldAcceptNull() {
    // When: Constructor is called with null SessionService
    UtilityFunctionsMapper mapper = new UtilityFunctionsMapper(null, sharedDataStore, secretKeeper);

    // Then: Instance should be created successfully
    assertThat(mapper).isNotNull();
  }

  @Test
  void constructor_withNullSharedDataStore_shouldAcceptNull() {
    // When: Constructor is called with null SharedDataStore
    UtilityFunctionsMapper mapper = new UtilityFunctionsMapper(sessionService, null, secretKeeper);

    // Then: Instance should be created successfully
    assertThat(mapper).isNotNull();
  }

  @Test
  void constructor_withNullSecretKeeper_shouldAcceptNull() {
    // When: Constructor is called with null SecretKeeper
    UtilityFunctionsMapper mapper = new UtilityFunctionsMapper(sessionService, sharedDataStore, null);

    // Then: Instance should be created successfully
    assertThat(mapper).isNotNull();
  }

  @Test
  void constructor_withAllNullParameters_shouldAcceptAllNulls() {
    // When: Constructor is called with all null parameters
    UtilityFunctionsMapper mapper = new UtilityFunctionsMapper(null, null, null);

    // Then: Instance should be created successfully
    assertThat(mapper).isNotNull();
  }

  @Test
  void constructor_calledMultipleTimes_shouldUpdateStaticFields() {
    // Given: First set of services
    UserV2 firstUser = new UserV2();
    firstUser.setId(111L);
    SessionService firstSessionService = mock(SessionService.class);
    when(firstSessionService.getSession()).thenReturn(firstUser);

    // When: Constructor is called first time
    new UtilityFunctionsMapper(firstSessionService, sharedDataStore, secretKeeper);

    // Then: First service should be in use
    UserV2 firstResult = UtilityFunctionsMapper.session();
    assertThat(firstResult.getId()).isEqualTo(111L);

    // Given: Second set of services
    UserV2 secondUser = new UserV2();
    secondUser.setId(222L);
    SessionService secondSessionService = mock(SessionService.class);
    when(secondSessionService.getSession()).thenReturn(secondUser);

    // When: Constructor is called second time
    new UtilityFunctionsMapper(secondSessionService, sharedDataStore, secretKeeper);

    // Then: Second service should now be in use
    UserV2 secondResult = UtilityFunctionsMapper.session();
    assertThat(secondResult.getId()).isEqualTo(222L);
  }

  @Test
  void constructor_shouldCreateMultipleIndependentInstances() {
    // When: Constructor is called multiple times
    UtilityFunctionsMapper mapper1 = new UtilityFunctionsMapper(sessionService, sharedDataStore, secretKeeper);
    UtilityFunctionsMapper mapper2 = new UtilityFunctionsMapper(sessionService, sharedDataStore, secretKeeper);
    UtilityFunctionsMapper mapper3 = new UtilityFunctionsMapper(sessionService, sharedDataStore, secretKeeper);

    // Then: Each call should create a distinct instance
    assertThat(mapper1).isNotNull();
    assertThat(mapper2).isNotNull();
    assertThat(mapper3).isNotNull();
    assertThat(mapper1).isNotSameAs(mapper2);
    assertThat(mapper2).isNotSameAs(mapper3);
    assertThat(mapper1).isNotSameAs(mapper3);
  }

  @Test
  void constructor_shouldExtendFunctionMapper() {
    // When: Constructor is called
    UtilityFunctionsMapper mapper = new UtilityFunctionsMapper(sessionService, sharedDataStore, secretKeeper);

    // Then: Instance should be an instance of FunctionMapper
    assertThat(mapper).isInstanceOf(org.camunda.bpm.impl.juel.jakarta.el.FunctionMapper.class);
  }

  @Test
  void constructor_withDifferentSessionServices_shouldReplaceStaticField() {
    // Given: Two different SessionServices
    UserV2 user1 = new UserV2();
    user1.setId(100L);
    SessionService service1 = mock(SessionService.class);
    when(service1.getSession()).thenReturn(user1);

    UserV2 user2 = new UserV2();
    user2.setId(200L);
    SessionService service2 = mock(SessionService.class);
    when(service2.getSession()).thenReturn(user2);

    // When: Constructor is called with first service
    new UtilityFunctionsMapper(service1, sharedDataStore, secretKeeper);

    // Then: First service should be active
    assertThat(UtilityFunctionsMapper.session().getId()).isEqualTo(100L);

    // When: Constructor is called with second service
    new UtilityFunctionsMapper(service2, sharedDataStore, secretKeeper);

    // Then: Second service should now be active
    assertThat(UtilityFunctionsMapper.session().getId()).isEqualTo(200L);
  }

  @Test
  void constructor_withDifferentSharedDataStores_shouldReplaceStaticField() {
    // Given: Two different SharedDataStores
    Map<String, Object> data1 = new HashMap<>();
    data1.put("key", "value1");
    SharedDataStore store1 = mock(SharedDataStore.class);
    when(store1.getNamespaceData("ns")).thenReturn(data1);

    Map<String, Object> data2 = new HashMap<>();
    data2.put("key", "value2");
    SharedDataStore store2 = mock(SharedDataStore.class);
    when(store2.getNamespaceData("ns")).thenReturn(data2);

    // When: Constructor is called with first store
    new UtilityFunctionsMapper(sessionService, store1, secretKeeper);

    // Then: First store should be active
    assertThat(UtilityFunctionsMapper.readShared("ns", "key")).isEqualTo("value1");

    // When: Constructor is called with second store
    new UtilityFunctionsMapper(sessionService, store2, secretKeeper);

    // Then: Second store should now be active
    assertThat(UtilityFunctionsMapper.readShared("ns", "key")).isEqualTo("value2");
  }

  @Test
  void constructor_withDifferentSecretKeepers_shouldReplaceStaticField() {
    // Given: Two different SecretKeepers
    SecretKeeper keeper1 = mock(SecretKeeper.class);
    when(keeper1.get("key")).thenReturn("secret1");

    SecretKeeper keeper2 = mock(SecretKeeper.class);
    when(keeper2.get("key")).thenReturn("secret2");

    // When: Constructor is called with first keeper
    new UtilityFunctionsMapper(sessionService, sharedDataStore, keeper1);

    // Then: First keeper should be active
    assertThat(UtilityFunctionsMapper.secret("key")).isEqualTo("secret1");

    // When: Constructor is called with second keeper
    new UtilityFunctionsMapper(sessionService, sharedDataStore, keeper2);

    // Then: Second keeper should now be active
    assertThat(UtilityFunctionsMapper.secret("key")).isEqualTo("secret2");
  }

  @Test
  void constructor_shouldInitializeWithMixOfNullAndNonNullParameters() {
    // Given: Only SessionService is provided
    UserV2 user = new UserV2();
    user.setId(555L);
    when(sessionService.getSession()).thenReturn(user);

    // When: Constructor is called with some null parameters
    UtilityFunctionsMapper mapper = new UtilityFunctionsMapper(sessionService, null, null);

    // Then: Instance should be created and SessionService should work
    assertThat(mapper).isNotNull();
    assertThat(UtilityFunctionsMapper.session().getId()).isEqualTo(555L);
  }

  @Test
  void constructor_shouldWorkWithRealWorldScenario() {
    // Given: Complete setup with realistic data
    UserV2 botUser = new UserV2();
    botUser.setId(987654321L);
    botUser.setUsername("bot-user");
    botUser.setDisplayName("Workflow Bot");
    when(sessionService.getSession()).thenReturn(botUser);

    Map<String, Object> workflowData = new HashMap<>();
    workflowData.put("workflowId", "wf-123");
    workflowData.put("status", "running");
    when(sharedDataStore.getNamespaceData("workflow")).thenReturn(workflowData);

    when(secretKeeper.get("apiToken")).thenReturn("Bearer abc123xyz");
    when(secretKeeper.get("dbPassword")).thenReturn("securePass123!");

    // When: Constructor is called with complete setup
    UtilityFunctionsMapper mapper = new UtilityFunctionsMapper(sessionService, sharedDataStore, secretKeeper);

    // Then: All functionality should work correctly
    assertThat(mapper).isNotNull();

    UserV2 session = UtilityFunctionsMapper.session();
    assertThat(session.getId()).isEqualTo(987654321L);
    assertThat(session.getUsername()).isEqualTo("bot-user");

    Object workflowId = UtilityFunctionsMapper.readShared("workflow", "workflowId");
    Object status = UtilityFunctionsMapper.readShared("workflow", "status");
    assertThat(workflowId).isEqualTo("wf-123");
    assertThat(status).isEqualTo("running");

    String apiToken = UtilityFunctionsMapper.secret("apiToken");
    String dbPassword = UtilityFunctionsMapper.secret("dbPassword");
    assertThat(apiToken).isEqualTo("Bearer abc123xyz");
    assertThat(dbPassword).isEqualTo("securePass123!");
  }
}
