package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.core.retry.RetryWithRecoveryBuilder;
import com.symphony.bdk.core.service.message.exception.MessageParserException;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.gen.api.SessionApi;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.gen.api.model.V4MessageSent;
import com.symphony.bdk.workflow.engine.executor.EventHolder;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SharedDataStore;
import com.symphony.bdk.workflow.engine.secret.DefaultSecretKeeper;
import com.symphony.bdk.workflow.engine.secret.SecretCryptVault;
import com.symphony.bdk.workflow.engine.secret.SecretRepository;
import com.symphony.bdk.workflow.engine.shared.DefaultSharedDataStore;
import com.symphony.bdk.workflow.engine.shared.SharedDataRepository;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DisabledInAotMode
@ContextConfiguration(classes = {UtilityFunctionsMapper.class})
@ExtendWith(SpringExtension.class)
class UtilityFunctionsMapperDiffblueTest {
  @MockBean
  private SecretKeeper secretKeeper;

  @MockBean
  private SessionService sessionService;

  @MockBean
  private SharedDataStore sharedDataStore;

  @Autowired
  private UtilityFunctionsMapper utilityFunctionsMapper;

  /**
   * Method under test:
   * {@link UtilityFunctionsMapper#resolveFunction(String, String)}
   */
  @Test
  void testResolveFunction() {
    // Arrange, Act and Assert
    assertNull(utilityFunctionsMapper.resolveFunction("Prefix", "Local Name"));
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#json(String)}
   */
  @Test
  void testJson() {
    // Arrange, Act and Assert
    assertEquals("String", UtilityFunctionsMapper.json("String"));
    assertEquals("", UtilityFunctionsMapper.json(""));
    assertEquals("42String", UtilityFunctionsMapper.json("42String"));
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#secret(String)}
   */
  @Test
  void testSecret() {
    // Arrange, Act and Assert
    assertNull(UtilityFunctionsMapper.secret("Key"));
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#readShared(String, String)}
   */
  @Test
  void testReadShared() {
    // Arrange, Act and Assert
    assertNull(UtilityFunctionsMapper.readShared("Namespace", "Key"));
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#escape(String)}
   */
  @Test
  void testEscape() {
    // Arrange, Act and Assert
    assertEquals("foo", UtilityFunctionsMapper.escape("foo"));
    assertNull(UtilityFunctionsMapper.escape(null));
    assertEquals("", UtilityFunctionsMapper.escape(""));
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#mentions(Object)}
   */
  @Test
  void testMentions() throws MessageParserException {
    // Arrange and Act
    List<Long> actualMentionsResult = UtilityFunctionsMapper.mentions("Event");

    // Assert
    assertTrue(actualMentionsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#mentions(Object)}
   */
  @Test
  void testMentions2() throws MessageParserException {
    // Arrange and Act
    List<Long> actualMentionsResult = UtilityFunctionsMapper.mentions(new EventHolder<>());

    // Assert
    assertTrue(actualMentionsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#mentions(Object)}
   */
  @Test
  void testMentions3() throws MessageParserException {
    // Arrange
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn("Source");

    // Act
    List<Long> actualMentionsResult = UtilityFunctionsMapper.mentions(eventHolder);

    // Assert
    verify(eventHolder).getSource();
    assertTrue(actualMentionsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#mentions(Object)}
   */
  @Test
  void testMentions4() throws MessageParserException {
    // Arrange
    V4MessageSent v4MessageSent = mock(V4MessageSent.class);
    when(v4MessageSent.getMessage()).thenReturn(new V4Message());
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn(v4MessageSent);

    // Act
    List<Long> actualMentionsResult = UtilityFunctionsMapper.mentions(eventHolder);

    // Assert
    verify(v4MessageSent).getMessage();
    verify(eventHolder, atLeast(1)).getSource();
    assertTrue(actualMentionsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#mentions(Object)}
   */
  @Test
  void testMentions5() throws MessageParserException {
    // Arrange
    V4Message v4Message = mock(V4Message.class);
    when(v4Message.getData()).thenReturn("42");
    V4MessageSent v4MessageSent = mock(V4MessageSent.class);
    when(v4MessageSent.getMessage()).thenReturn(v4Message);
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn(v4MessageSent);

    // Act
    List<Long> actualMentionsResult = UtilityFunctionsMapper.mentions(eventHolder);

    // Assert
    verify(v4Message).getData();
    verify(v4MessageSent).getMessage();
    verify(eventHolder, atLeast(1)).getSource();
    assertTrue(actualMentionsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#mentions(Object)}
   */
  @Test
  void testMentions6() throws MessageParserException {
    // Arrange
    V4Message v4Message = mock(V4Message.class);
    when(v4Message.getData()).thenReturn("");
    V4MessageSent v4MessageSent = mock(V4MessageSent.class);
    when(v4MessageSent.getMessage()).thenReturn(v4Message);
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn(v4MessageSent);

    // Act
    List<Long> actualMentionsResult = UtilityFunctionsMapper.mentions(eventHolder);

    // Assert
    verify(v4Message).getData();
    verify(v4MessageSent).getMessage();
    verify(eventHolder, atLeast(1)).getSource();
    assertTrue(actualMentionsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#hashTags(Object)}
   */
  @Test
  void testHashTags() throws MessageParserException {
    // Arrange and Act
    List<String> actualHashTagsResult = UtilityFunctionsMapper.hashTags("Event");

    // Assert
    assertTrue(actualHashTagsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#hashTags(Object)}
   */
  @Test
  void testHashTags2() throws MessageParserException {
    // Arrange and Act
    List<String> actualHashTagsResult = UtilityFunctionsMapper.hashTags(new EventHolder<>());

    // Assert
    assertTrue(actualHashTagsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#hashTags(Object)}
   */
  @Test
  void testHashTags3() throws MessageParserException {
    // Arrange
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn("Source");

    // Act
    List<String> actualHashTagsResult = UtilityFunctionsMapper.hashTags(eventHolder);

    // Assert
    verify(eventHolder).getSource();
    assertTrue(actualHashTagsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#hashTags(Object)}
   */
  @Test
  void testHashTags4() throws MessageParserException {
    // Arrange
    V4MessageSent v4MessageSent = mock(V4MessageSent.class);
    when(v4MessageSent.getMessage()).thenReturn(new V4Message());
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn(v4MessageSent);

    // Act
    List<String> actualHashTagsResult = UtilityFunctionsMapper.hashTags(eventHolder);

    // Assert
    verify(v4MessageSent).getMessage();
    verify(eventHolder, atLeast(1)).getSource();
    assertTrue(actualHashTagsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#hashTags(Object)}
   */
  @Test
  void testHashTags5() throws MessageParserException {
    // Arrange
    V4Message v4Message = mock(V4Message.class);
    when(v4Message.getData()).thenReturn("42");
    V4MessageSent v4MessageSent = mock(V4MessageSent.class);
    when(v4MessageSent.getMessage()).thenReturn(v4Message);
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn(v4MessageSent);

    // Act
    List<String> actualHashTagsResult = UtilityFunctionsMapper.hashTags(eventHolder);

    // Assert
    verify(v4Message).getData();
    verify(v4MessageSent).getMessage();
    verify(eventHolder, atLeast(1)).getSource();
    assertTrue(actualHashTagsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#hashTags(Object)}
   */
  @Test
  void testHashTags6() throws MessageParserException {
    // Arrange
    V4Message v4Message = mock(V4Message.class);
    when(v4Message.getData()).thenReturn("");
    V4MessageSent v4MessageSent = mock(V4MessageSent.class);
    when(v4MessageSent.getMessage()).thenReturn(v4Message);
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn(v4MessageSent);

    // Act
    List<String> actualHashTagsResult = UtilityFunctionsMapper.hashTags(eventHolder);

    // Assert
    verify(v4Message).getData();
    verify(v4MessageSent).getMessage();
    verify(eventHolder, atLeast(1)).getSource();
    assertTrue(actualHashTagsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#cashTags(Object)}
   */
  @Test
  void testCashTags() throws MessageParserException {
    // Arrange and Act
    List<String> actualCashTagsResult = UtilityFunctionsMapper.cashTags("Event");

    // Assert
    assertTrue(actualCashTagsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#cashTags(Object)}
   */
  @Test
  void testCashTags2() throws MessageParserException {
    // Arrange and Act
    List<String> actualCashTagsResult = UtilityFunctionsMapper.cashTags(new EventHolder<>());

    // Assert
    assertTrue(actualCashTagsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#cashTags(Object)}
   */
  @Test
  void testCashTags3() throws MessageParserException {
    // Arrange
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn("Source");

    // Act
    List<String> actualCashTagsResult = UtilityFunctionsMapper.cashTags(eventHolder);

    // Assert
    verify(eventHolder).getSource();
    assertTrue(actualCashTagsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#cashTags(Object)}
   */
  @Test
  void testCashTags4() throws MessageParserException {
    // Arrange
    V4MessageSent v4MessageSent = mock(V4MessageSent.class);
    when(v4MessageSent.getMessage()).thenReturn(new V4Message());
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn(v4MessageSent);

    // Act
    List<String> actualCashTagsResult = UtilityFunctionsMapper.cashTags(eventHolder);

    // Assert
    verify(v4MessageSent).getMessage();
    verify(eventHolder, atLeast(1)).getSource();
    assertTrue(actualCashTagsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#cashTags(Object)}
   */
  @Test
  void testCashTags5() throws MessageParserException {
    // Arrange
    V4Message v4Message = mock(V4Message.class);
    when(v4Message.getData()).thenReturn("42");
    V4MessageSent v4MessageSent = mock(V4MessageSent.class);
    when(v4MessageSent.getMessage()).thenReturn(v4Message);
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn(v4MessageSent);

    // Act
    List<String> actualCashTagsResult = UtilityFunctionsMapper.cashTags(eventHolder);

    // Assert
    verify(v4Message).getData();
    verify(v4MessageSent).getMessage();
    verify(eventHolder, atLeast(1)).getSource();
    assertTrue(actualCashTagsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#cashTags(Object)}
   */
  @Test
  void testCashTags6() throws MessageParserException {
    // Arrange
    V4Message v4Message = mock(V4Message.class);
    when(v4Message.getData()).thenReturn("");
    V4MessageSent v4MessageSent = mock(V4MessageSent.class);
    when(v4MessageSent.getMessage()).thenReturn(v4Message);
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn(v4MessageSent);

    // Act
    List<String> actualCashTagsResult = UtilityFunctionsMapper.cashTags(eventHolder);

    // Assert
    verify(v4Message).getData();
    verify(v4MessageSent).getMessage();
    verify(eventHolder, atLeast(1)).getSource();
    assertTrue(actualCashTagsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#cashTags(Object)}
   */
  @Test
  void testCashTags7() throws MessageParserException {
    // Arrange
    V4Message v4Message = new V4Message();
    v4Message.data("42");
    V4MessageSent v4MessageSent = mock(V4MessageSent.class);
    when(v4MessageSent.getMessage()).thenReturn(v4Message);
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn(v4MessageSent);

    // Act
    List<String> actualCashTagsResult = UtilityFunctionsMapper.cashTags(eventHolder);

    // Assert
    verify(v4MessageSent).getMessage();
    verify(eventHolder, atLeast(1)).getSource();
    assertTrue(actualCashTagsResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#emojis(Object)}
   */
  @Test
  void testEmojis() throws MessageParserException {
    // Arrange and Act
    Map<String, String> actualEmojisResult = UtilityFunctionsMapper.emojis("Event");

    // Assert
    assertTrue(actualEmojisResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#emojis(Object)}
   */
  @Test
  void testEmojis2() throws MessageParserException {
    // Arrange and Act
    Map<String, String> actualEmojisResult = UtilityFunctionsMapper.emojis(new EventHolder<>());

    // Assert
    assertTrue(actualEmojisResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#emojis(Object)}
   */
  @Test
  void testEmojis3() throws MessageParserException {
    // Arrange
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn("Source");

    // Act
    Map<String, String> actualEmojisResult = UtilityFunctionsMapper.emojis(eventHolder);

    // Assert
    verify(eventHolder).getSource();
    assertTrue(actualEmojisResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#emojis(Object)}
   */
  @Test
  void testEmojis4() throws MessageParserException {
    // Arrange
    V4MessageSent v4MessageSent = mock(V4MessageSent.class);
    when(v4MessageSent.getMessage()).thenReturn(new V4Message());
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn(v4MessageSent);

    // Act
    Map<String, String> actualEmojisResult = UtilityFunctionsMapper.emojis(eventHolder);

    // Assert
    verify(v4MessageSent).getMessage();
    verify(eventHolder, atLeast(1)).getSource();
    assertTrue(actualEmojisResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#emojis(Object)}
   */
  @Test
  void testEmojis5() throws MessageParserException {
    // Arrange
    V4Message v4Message = mock(V4Message.class);
    when(v4Message.getData()).thenReturn("42");
    V4MessageSent v4MessageSent = mock(V4MessageSent.class);
    when(v4MessageSent.getMessage()).thenReturn(v4Message);
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn(v4MessageSent);

    // Act
    Map<String, String> actualEmojisResult = UtilityFunctionsMapper.emojis(eventHolder);

    // Assert
    verify(v4Message).getData();
    verify(v4MessageSent).getMessage();
    verify(eventHolder, atLeast(1)).getSource();
    assertTrue(actualEmojisResult.isEmpty());
  }

  /**
   * Method under test: {@link UtilityFunctionsMapper#emojis(Object)}
   */
  @Test
  void testEmojis6() throws MessageParserException {
    // Arrange
    V4Message v4Message = mock(V4Message.class);
    when(v4Message.getData()).thenReturn("");
    V4MessageSent v4MessageSent = mock(V4MessageSent.class);
    when(v4MessageSent.getMessage()).thenReturn(v4Message);
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenReturn(v4MessageSent);

    // Act
    Map<String, String> actualEmojisResult = UtilityFunctionsMapper.emojis(eventHolder);

    // Assert
    verify(v4Message).getData();
    verify(v4MessageSent).getMessage();
    verify(eventHolder, atLeast(1)).getSource();
    assertTrue(actualEmojisResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link UtilityFunctionsMapper#UtilityFunctionsMapper(SessionService, SharedDataStore, SecretKeeper)}
   */
  @Test
  void testNewUtilityFunctionsMapper() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    SessionApi sessionApi = new SessionApi(null);
    SessionService sessionService = new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());

    DefaultSharedDataStore sharedDataStore = new DefaultSharedDataStore(mock(SharedDataRepository.class));
    SecretRepository repository = mock(SecretRepository.class);

    // Act and Assert
    assertNull((new UtilityFunctionsMapper(sessionService, sharedDataStore,
        new DefaultSecretKeeper(repository, new SecretCryptVault()))).resolveFunction("Prefix", "Local Name"));
  }
}
