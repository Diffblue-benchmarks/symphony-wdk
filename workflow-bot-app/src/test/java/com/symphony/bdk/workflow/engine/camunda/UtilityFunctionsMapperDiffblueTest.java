package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.core.retry.RetryWithRecoveryBuilder;
import com.symphony.bdk.core.service.message.exception.MessageParserException;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.gen.api.SessionApi;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UtilityFunctionsMapper.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UtilityFunctionsMapperDiffblueTest {
  @Autowired
  private UtilityFunctionsMapper utilityFunctionsMapper;

  @MockBean
  private SecretKeeper secretKeeper;

  @MockBean
  private SessionService sessionService;

  @MockBean
  private SharedDataStore sharedDataStore;

  /**
   * Test
   * {@link UtilityFunctionsMapper#UtilityFunctionsMapper(SessionService, SharedDataStore, SecretKeeper)}.
   * <ul>
   *   <li>Then return resolveFunction {@code Prefix} and {@code Local Name} is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UtilityFunctionsMapper#UtilityFunctionsMapper(SessionService, SharedDataStore, SecretKeeper)}
   */
  @Test
  @DisplayName("Test new UtilityFunctionsMapper(SessionService, SharedDataStore, SecretKeeper); then return resolveFunction 'Prefix' and 'Local Name' is 'null'")
  void testNewUtilityFunctionsMapper_thenReturnResolveFunctionPrefixAndLocalNameIsNull() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    SessionApi sessionApi = new SessionApi(null);
    SessionService sessionService = new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());

    DefaultSharedDataStore sharedDataStore = new DefaultSharedDataStore(mock(SharedDataRepository.class));
    SecretRepository repository = mock(SecretRepository.class);

    // Act and Assert
    assertNull((new UtilityFunctionsMapper(sessionService, sharedDataStore,
        new DefaultSecretKeeper(repository, new SecretCryptVault()))).resolveFunction("Prefix", "Local Name"));
  }

  /**
   * Test {@link UtilityFunctionsMapper#resolveFunction(String, String)}.
   * <p>
   * Method under test:
   * {@link UtilityFunctionsMapper#resolveFunction(String, String)}
   */
  @Test
  @DisplayName("Test resolveFunction(String, String)")
  void testResolveFunction() {
    // Arrange, Act and Assert
    assertNull(utilityFunctionsMapper.resolveFunction("Prefix", "Local Name"));
  }

  /**
   * Test {@link UtilityFunctionsMapper#session()}.
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#session()}
   */
  @Test
  @DisplayName("Test session()")
  void testSession() {
    // Arrange, Act and Assert
    assertNull(UtilityFunctionsMapper.session());
  }

  /**
   * Test {@link UtilityFunctionsMapper#json(String)}.
   * <ul>
   *   <li>Then return
   * {@code com.symphony.bdk.workflow.engine.executor.EventHolder}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#json(String)}
   */
  @Test
  @DisplayName("Test json(String); then return 'com.symphony.bdk.workflow.engine.executor.EventHolder'")
  void testJson_thenReturnComSymphonyBdkWorkflowEngineExecutorEventHolder() {
    // Arrange, Act and Assert
    assertEquals("com.symphony.bdk.workflow.engine.executor.EventHolder",
        UtilityFunctionsMapper.json("com.symphony.bdk.workflow.engine.executor.EventHolder"));
  }

  /**
   * Test {@link UtilityFunctionsMapper#json(String)}.
   * <ul>
   *   <li>When {@code 42String}.</li>
   *   <li>Then return {@code 42String}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#json(String)}
   */
  @Test
  @DisplayName("Test json(String); when '42String'; then return '42String'")
  void testJson_when42String_thenReturn42String() {
    // Arrange, Act and Assert
    assertEquals("42String", UtilityFunctionsMapper.json("42String"));
  }

  /**
   * Test {@link UtilityFunctionsMapper#json(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return intValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#json(String)}
   */
  @Test
  @DisplayName("Test json(String); when '42'; then return intValue is forty-two")
  void testJson_when42_thenReturnIntValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42, ((Integer) UtilityFunctionsMapper.json("42")).intValue());
  }

  /**
   * Test {@link UtilityFunctionsMapper#json(String)}.
   * <ul>
   *   <li>When {@code 4242}.</li>
   *   <li>Then return intValue is {@code 4242}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#json(String)}
   */
  @Test
  @DisplayName("Test json(String); when '4242'; then return intValue is '4242'")
  void testJson_when4242_thenReturnIntValueIs4242() {
    // Arrange, Act and Assert
    assertEquals(4242, ((Integer) UtilityFunctionsMapper.json("4242")).intValue());
  }

  /**
   * Test {@link UtilityFunctionsMapper#json(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#json(String)}
   */
  @Test
  @DisplayName("Test json(String); when empty string; then return empty string")
  void testJson_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", UtilityFunctionsMapper.json(""));
  }

  /**
   * Test {@link UtilityFunctionsMapper#json(String)}.
   * <ul>
   *   <li>When {@code String}.</li>
   *   <li>Then return {@code String}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#json(String)}
   */
  @Test
  @DisplayName("Test json(String); when 'String'; then return 'String'")
  void testJson_whenString_thenReturnString() {
    // Arrange, Act and Assert
    assertEquals("String", UtilityFunctionsMapper.json("String"));
  }

  /**
   * Test {@link UtilityFunctionsMapper#secret(String)}.
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#secret(String)}
   */
  @Test
  @DisplayName("Test secret(String)")
  void testSecret() {
    // Arrange, Act and Assert
    assertNull(UtilityFunctionsMapper.secret("Key"));
  }

  /**
   * Test {@link UtilityFunctionsMapper#readShared(String, String)}.
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#readShared(String, String)}
   */
  @Test
  @DisplayName("Test readShared(String, String)")
  void testReadShared() {
    // Arrange, Act and Assert
    assertNull(UtilityFunctionsMapper.readShared("Namespace", "Key"));
  }

  /**
   * Test {@link UtilityFunctionsMapper#escape(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#escape(String)}
   */
  @Test
  @DisplayName("Test escape(String); when empty string; then return empty string")
  void testEscape_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", UtilityFunctionsMapper.escape(""));
  }

  /**
   * Test {@link UtilityFunctionsMapper#escape(String)}.
   * <ul>
   *   <li>When {@code foo}.</li>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#escape(String)}
   */
  @Test
  @DisplayName("Test escape(String); when 'foo'; then return 'foo'")
  void testEscape_whenFoo_thenReturnFoo() {
    // Arrange, Act and Assert
    assertEquals("foo", UtilityFunctionsMapper.escape("foo"));
  }

  /**
   * Test {@link UtilityFunctionsMapper#escape(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#escape(String)}
   */
  @Test
  @DisplayName("Test escape(String); when 'null'; then return 'null'")
  void testEscape_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(UtilityFunctionsMapper.escape(null));
  }

  /**
   * Test {@link UtilityFunctionsMapper#mentions(Object)}.
   * <ul>
   *   <li>When {@code Event}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#mentions(Object)}
   */
  @Test
  @DisplayName("Test mentions(Object); when 'Event'")
  void testMentions_whenEvent() throws MessageParserException {
    // Arrange and Act
    List<Long> actualMentionsResult = UtilityFunctionsMapper.mentions("Event");

    // Assert
    assertTrue(actualMentionsResult.isEmpty());
  }

  /**
   * Test {@link UtilityFunctionsMapper#mentions(Object)}.
   * <ul>
   *   <li>When {@link EventHolder#EventHolder()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#mentions(Object)}
   */
  @Test
  @DisplayName("Test mentions(Object); when EventHolder()")
  void testMentions_whenEventHolder() throws MessageParserException {
    // Arrange and Act
    List<Long> actualMentionsResult = UtilityFunctionsMapper.mentions(new EventHolder<>());

    // Assert
    assertTrue(actualMentionsResult.isEmpty());
  }

  /**
   * Test {@link UtilityFunctionsMapper#hashTags(Object)}.
   * <ul>
   *   <li>When {@code Event}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#hashTags(Object)}
   */
  @Test
  @DisplayName("Test hashTags(Object); when 'Event'")
  void testHashTags_whenEvent() throws MessageParserException {
    // Arrange and Act
    List<String> actualHashTagsResult = UtilityFunctionsMapper.hashTags("Event");

    // Assert
    assertTrue(actualHashTagsResult.isEmpty());
  }

  /**
   * Test {@link UtilityFunctionsMapper#hashTags(Object)}.
   * <ul>
   *   <li>When {@link EventHolder#EventHolder()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#hashTags(Object)}
   */
  @Test
  @DisplayName("Test hashTags(Object); when EventHolder()")
  void testHashTags_whenEventHolder() throws MessageParserException {
    // Arrange and Act
    List<String> actualHashTagsResult = UtilityFunctionsMapper.hashTags(new EventHolder<>());

    // Assert
    assertTrue(actualHashTagsResult.isEmpty());
  }

  /**
   * Test {@link UtilityFunctionsMapper#cashTags(Object)}.
   * <ul>
   *   <li>When {@code Event}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#cashTags(Object)}
   */
  @Test
  @DisplayName("Test cashTags(Object); when 'Event'")
  void testCashTags_whenEvent() throws MessageParserException {
    // Arrange and Act
    List<String> actualCashTagsResult = UtilityFunctionsMapper.cashTags("Event");

    // Assert
    assertTrue(actualCashTagsResult.isEmpty());
  }

  /**
   * Test {@link UtilityFunctionsMapper#cashTags(Object)}.
   * <ul>
   *   <li>When {@link EventHolder#EventHolder()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#cashTags(Object)}
   */
  @Test
  @DisplayName("Test cashTags(Object); when EventHolder()")
  void testCashTags_whenEventHolder() throws MessageParserException {
    // Arrange and Act
    List<String> actualCashTagsResult = UtilityFunctionsMapper.cashTags(new EventHolder<>());

    // Assert
    assertTrue(actualCashTagsResult.isEmpty());
  }

  /**
   * Test {@link UtilityFunctionsMapper#emojis(Object)}.
   * <ul>
   *   <li>When {@code Event}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#emojis(Object)}
   */
  @Test
  @DisplayName("Test emojis(Object); when 'Event'")
  void testEmojis_whenEvent() throws MessageParserException {
    // Arrange and Act
    Map<String, String> actualEmojisResult = UtilityFunctionsMapper.emojis("Event");

    // Assert
    assertTrue(actualEmojisResult.isEmpty());
  }

  /**
   * Test {@link UtilityFunctionsMapper#emojis(Object)}.
   * <ul>
   *   <li>When {@link EventHolder#EventHolder()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UtilityFunctionsMapper#emojis(Object)}
   */
  @Test
  @DisplayName("Test emojis(Object); when EventHolder()")
  void testEmojis_whenEventHolder() throws MessageParserException {
    // Arrange and Act
    Map<String, String> actualEmojisResult = UtilityFunctionsMapper.emojis(new EventHolder<>());

    // Assert
    assertTrue(actualEmojisResult.isEmpty());
  }
}
