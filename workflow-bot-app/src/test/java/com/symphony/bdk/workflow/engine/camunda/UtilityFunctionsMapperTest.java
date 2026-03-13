package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.core.service.message.exception.MessageParserException;
import com.symphony.bdk.core.service.message.exception.PresentationMLParserException;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.gen.api.model.UserV2;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.gen.api.model.V4MessageSent;
import com.symphony.bdk.workflow.engine.executor.EventHolder;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SharedDataStore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UtilityFunctionsMapperTest {

  private SessionService sessionService;
  private SharedDataStore sharedDataStore;
  private SecretKeeper secretKeeper;
  private UtilityFunctionsMapper mapper;

  @BeforeEach
  void setUp() {
    sessionService = mock(SessionService.class);
    sharedDataStore = mock(SharedDataStore.class);
    secretKeeper = mock(SecretKeeper.class);
    mapper = new UtilityFunctionsMapper(sessionService, sharedDataStore, secretKeeper);
  }

  @Test
  void shouldSetStaticSessionService() {
    SessionService newSessionService = mock(SessionService.class);
    UserV2 mockUser = mock(UserV2.class);
    when(newSessionService.getSession()).thenReturn(mockUser);

    UtilityFunctionsMapper.setStaticSessionService(newSessionService);

    assertThat(UtilityFunctionsMapper.session()).isSameAs(mockUser);
  }

  @Test
  void shouldSetSharedStateService() {
    SharedDataStore newSharedDataStore = mock(SharedDataStore.class);

    UtilityFunctionsMapper.setSharedStateService(newSharedDataStore);

    assertThat(newSharedDataStore).isNotNull();
  }

  @Test
  void shouldSetSecretKeeper() {
    SecretKeeper newSecretKeeper = mock(SecretKeeper.class);

    UtilityFunctionsMapper.setSecretKeeper(newSecretKeeper);

    assertThat(newSecretKeeper).isNotNull();
  }

  @Test
  void shouldInitializeWithDependencies() {
    SessionService mockSessionService = mock(SessionService.class);
    SharedDataStore mockSharedDataStore = mock(SharedDataStore.class);
    SecretKeeper mockSecretKeeper = mock(SecretKeeper.class);

    UtilityFunctionsMapper newMapper = new UtilityFunctionsMapper(mockSessionService, mockSharedDataStore, mockSecretKeeper);

    assertThat(newMapper).isNotNull();
  }

  @Test
  void shouldResolveFunction() {
    Method method = mapper.resolveFunction(UtilityFunctionsMapper.WDK_PREFIX, UtilityFunctionsMapper.TEXT);

    assertThat(method).isNotNull();
    assertThat(method.getName()).isEqualTo(UtilityFunctionsMapper.TEXT);
  }

  @Test
  void shouldGetSession() {
    UserV2 mockUser = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(mockUser);

    UserV2 result = UtilityFunctionsMapper.session();

    assertThat(result).isSameAs(mockUser);
    verify(sessionService).getSession();
  }

  @Test
  void shouldParseValidJsonString() {
    String jsonString = "{\"key\":\"value\"}";

    Object result = UtilityFunctionsMapper.json(jsonString);

    assertThat(result).isInstanceOf(Map.class);
    @SuppressWarnings("unchecked")
    Map<String, Object> resultMap = (Map<String, Object>) result;
    assertThat(resultMap.get("key")).isEqualTo("value");
  }

  @Test
  void shouldReturnOriginalStringForInvalidJson() {
    String invalidJson = "not a json";

    Object result = UtilityFunctionsMapper.json(invalidJson);

    assertThat(result).isEqualTo(invalidJson);
  }

  @Test
  void shouldGetSecret() {
    String key = "secretKey";
    String secretValue = "secretValue";
    when(secretKeeper.get(key)).thenReturn(secretValue);

    String result = UtilityFunctionsMapper.secret(key);

    assertThat(result).isEqualTo(secretValue);
    verify(secretKeeper).get(key);
  }

  @Test
  void shouldReadSharedData() {
    String namespace = "testNamespace";
    String key = "testKey";
    Object expectedValue = "testValue";
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put(key, expectedValue);
    when(sharedDataStore.getNamespaceData(namespace)).thenReturn(namespaceData);

    Object result = UtilityFunctionsMapper.readShared(namespace, key);

    assertThat(result).isEqualTo(expectedValue);
    verify(sharedDataStore).getNamespaceData(namespace);
  }

  @Test
  void shouldWriteSharedData() {
    String namespace = "testNamespace";
    String key = "testKey";
    Object value = "testValue";

    UtilityFunctionsMapper.writeShared(namespace, key, value);

    verify(sharedDataStore).putNamespaceData(namespace, key, value);
  }

  @Test
  void shouldExtractTextFromPresentationML() throws PresentationMLParserException {
    String presentationMl = "<messageML>Hello World</messageML>";

    String result = UtilityFunctionsMapper.text(presentationMl);

    assertThat(result).isNotNull();
  }

  @Test
  void shouldEscapeString() {
    String input = "test\"string";

    String result = UtilityFunctionsMapper.escape(input);

    assertThat(result).contains("\\\"");
  }

  @Test
  void shouldReturnNullWhenEscapingNullString() {
    String result = UtilityFunctionsMapper.escape(null);

    assertThat(result).isNull();
  }

  @Test
  void shouldExtractMentionsFromEventHolder() throws MessageParserException {
    EventHolder<?> eventHolder = mock(EventHolder.class);
    V4MessageSent messageSent = mock(V4MessageSent.class);
    V4Message message = mock(V4Message.class);
    when(eventHolder.getSource()).thenReturn(messageSent);
    when(messageSent.getMessage()).thenReturn(message);
    when(message.getMessage()).thenReturn("<messageML><mention uid=\"123\"/></messageML>");

    List<Long> result = UtilityFunctionsMapper.mentions(eventHolder);

    assertThat(result).isNotNull();
  }

  @Test
  void shouldReturnEmptyListWhenEventIsNotEventHolder() throws MessageParserException {
    String notAnEventHolder = "not an event holder";

    List<Long> result = UtilityFunctionsMapper.mentions(notAnEventHolder);

    assertThat(result).isEmpty();
  }

  @Test
  void shouldExtractHashTagsFromEventHolder() throws MessageParserException {
    EventHolder<?> eventHolder = mock(EventHolder.class);
    V4MessageSent messageSent = mock(V4MessageSent.class);
    V4Message message = mock(V4Message.class);
    when(eventHolder.getSource()).thenReturn(messageSent);
    when(messageSent.getMessage()).thenReturn(message);
    when(message.getMessage()).thenReturn("<messageML><hash tag=\"test\"/></messageML>");

    List<String> result = UtilityFunctionsMapper.hashTags(eventHolder);

    assertThat(result).isNotNull();
  }

  @Test
  void shouldReturnEmptyListWhenEventIsNotEventHolderForHashTags() throws MessageParserException {
    String notAnEventHolder = "not an event holder";

    List<String> result = UtilityFunctionsMapper.hashTags(notAnEventHolder);

    assertThat(result).isEmpty();
  }

  @Test
  void shouldExtractCashTagsFromEventHolder() throws MessageParserException {
    EventHolder<?> eventHolder = mock(EventHolder.class);
    V4MessageSent messageSent = mock(V4MessageSent.class);
    V4Message message = mock(V4Message.class);
    when(eventHolder.getSource()).thenReturn(messageSent);
    when(messageSent.getMessage()).thenReturn(message);
    when(message.getMessage()).thenReturn("<messageML><cash tag=\"AAPL\"/></messageML>");

    List<String> result = UtilityFunctionsMapper.cashTags(eventHolder);

    assertThat(result).isNotNull();
  }

  @Test
  void shouldReturnEmptyListWhenEventIsNotEventHolderForCashTags() throws MessageParserException {
    String notAnEventHolder = "not an event holder";

    List<String> result = UtilityFunctionsMapper.cashTags(notAnEventHolder);

    assertThat(result).isEmpty();
  }

  @Test
  void shouldExtractEmojisFromEventHolder() throws MessageParserException {
    EventHolder<?> eventHolder = mock(EventHolder.class);
    V4MessageSent messageSent = mock(V4MessageSent.class);
    V4Message message = mock(V4Message.class);
    when(eventHolder.getSource()).thenReturn(messageSent);
    when(messageSent.getMessage()).thenReturn(message);
    when(message.getMessage()).thenReturn("<messageML>Hello \uD83D\uDE00</messageML>");

    Map<String, String> result = UtilityFunctionsMapper.emojis(eventHolder);

    assertThat(result).isNotNull();
  }

  @Test
  void shouldReturnEmptyMapWhenEventIsNotEventHolderForEmojis() throws MessageParserException {
    String notAnEventHolder = "not an event holder";

    Map<String, String> result = UtilityFunctionsMapper.emojis(notAnEventHolder);

    assertThat(result).isEmpty();
  }
}
