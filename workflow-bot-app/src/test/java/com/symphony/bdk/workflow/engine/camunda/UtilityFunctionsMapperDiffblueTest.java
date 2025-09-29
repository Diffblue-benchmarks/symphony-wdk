package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UtilityFunctionsMapper.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class UtilityFunctionsMapperDiffblueTest {
  @MockBean private SecretKeeper secretKeeper;

  @MockBean private SessionService sessionService;

  @MockBean private SharedDataStore sharedDataStore;

  @Autowired private UtilityFunctionsMapper utilityFunctionsMapper;

  /**
   * Test {@link UtilityFunctionsMapper#UtilityFunctionsMapper(SessionService, SharedDataStore,
   * SecretKeeper)}.
   *
   * <ul>
   *   <li>Then return resolveFunction {@code Prefix} and {@code Local Name} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#UtilityFunctionsMapper(SessionService,
   * SharedDataStore, SecretKeeper)}
   */
  @Test
  @DisplayName(
      "Test new UtilityFunctionsMapper(SessionService, SharedDataStore, SecretKeeper); then return resolveFunction 'Prefix' and 'Local Name' is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UtilityFunctionsMapper.<init>(SessionService, SharedDataStore, SecretKeeper)"
  })
  void testNewUtilityFunctionsMapper_thenReturnResolveFunctionPrefixAndLocalNameIsNull() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    SessionApi sessionApi = new SessionApi(null);
    SessionService sessionService =
        new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());
    DefaultSharedDataStore sharedDataStore =
        new DefaultSharedDataStore(mock(SharedDataRepository.class));
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    // Act
    UtilityFunctionsMapper actualUtilityFunctionsMapper =
        new UtilityFunctionsMapper(sessionService, sharedDataStore, secretKeeper);

    // Assert
    assertNull(actualUtilityFunctionsMapper.resolveFunction("Prefix", "Local Name"));
  }

  /**
   * Test {@link UtilityFunctionsMapper#resolveFunction(String, String)}.
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#resolveFunction(String, String)}
   */
  @Test
  @DisplayName("Test resolveFunction(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.reflect.Method UtilityFunctionsMapper.resolveFunction(String, String)"
  })
  void testResolveFunction() {
    // Arrange, Act and Assert
    assertNull(utilityFunctionsMapper.resolveFunction("Prefix", "Local Name"));
  }

  /**
   * Test {@link UtilityFunctionsMapper#json(String)}.
   *
   * <ul>
   *   <li>When {@code 42String}.
   *   <li>Then return {@code 42String}.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#json(String)}
   */
  @Test
  @DisplayName("Test json(String); when '42String'; then return '42String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object UtilityFunctionsMapper.json(String)"})
  void testJson_when42String_thenReturn42String() {
    // Arrange, Act and Assert
    assertEquals("42String", UtilityFunctionsMapper.json("42String"));
  }

  /**
   * Test {@link UtilityFunctionsMapper#json(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return intValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#json(String)}
   */
  @Test
  @DisplayName("Test json(String); when '42'; then return intValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object UtilityFunctionsMapper.json(String)"})
  void testJson_when42_thenReturnIntValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42, ((Integer) UtilityFunctionsMapper.json("42")).intValue());
  }

  /**
   * Test {@link UtilityFunctionsMapper#json(String)}.
   *
   * <ul>
   *   <li>When {@code 4242}.
   *   <li>Then return intValue is {@code 4242}.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#json(String)}
   */
  @Test
  @DisplayName("Test json(String); when '4242'; then return intValue is '4242'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object UtilityFunctionsMapper.json(String)"})
  void testJson_when4242_thenReturnIntValueIs4242() {
    // Arrange, Act and Assert
    assertEquals(4242, ((Integer) UtilityFunctionsMapper.json("4242")).intValue());
  }

  /**
   * Test {@link UtilityFunctionsMapper#json(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#json(String)}
   */
  @Test
  @DisplayName("Test json(String); when empty string; then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object UtilityFunctionsMapper.json(String)"})
  void testJson_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", UtilityFunctionsMapper.json(""));
  }

  /**
   * Test {@link UtilityFunctionsMapper#json(String)}.
   *
   * <ul>
   *   <li>When {@code String}.
   *   <li>Then return {@code String}.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#json(String)}
   */
  @Test
  @DisplayName("Test json(String); when 'String'; then return 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object UtilityFunctionsMapper.json(String)"})
  void testJson_whenString_thenReturnString() {
    // Arrange, Act and Assert
    assertEquals("String", UtilityFunctionsMapper.json("String"));
  }

  /**
   * Test {@link UtilityFunctionsMapper#readShared(String, String)}.
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#readShared(String, String)}
   */
  @Test
  @DisplayName("Test readShared(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object UtilityFunctionsMapper.readShared(String, String)"})
  void testReadShared() {
    // Arrange, Act and Assert
    assertNull(UtilityFunctionsMapper.readShared("Namespace", "Key"));
  }

  /**
   * Test {@link UtilityFunctionsMapper#escape(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#escape(String)}
   */
  @Test
  @DisplayName("Test escape(String); when empty string; then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String UtilityFunctionsMapper.escape(String)"})
  void testEscape_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", UtilityFunctionsMapper.escape(""));
  }

  /**
   * Test {@link UtilityFunctionsMapper#escape(String)}.
   *
   * <ul>
   *   <li>When {@code foo}.
   *   <li>Then return {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#escape(String)}
   */
  @Test
  @DisplayName("Test escape(String); when 'foo'; then return 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String UtilityFunctionsMapper.escape(String)"})
  void testEscape_whenFoo_thenReturnFoo() {
    // Arrange, Act and Assert
    assertEquals("foo", UtilityFunctionsMapper.escape("foo"));
  }

  /**
   * Test {@link UtilityFunctionsMapper#escape(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#escape(String)}
   */
  @Test
  @DisplayName("Test escape(String); when 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String UtilityFunctionsMapper.escape(String)"})
  void testEscape_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(UtilityFunctionsMapper.escape(null));
  }

  /**
   * Test {@link UtilityFunctionsMapper#mentions(Object)}.
   *
   * <ul>
   *   <li>When {@link EventHolder#EventHolder()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#mentions(Object)}
   */
  @Test
  @DisplayName("Test mentions(Object); when EventHolder(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List UtilityFunctionsMapper.mentions(Object)"})
  void testMentions_whenEventHolder_thenReturnEmpty() throws MessageParserException {
    // Arrange and Act
    List<Long> actualMentionsResult = UtilityFunctionsMapper.mentions(new EventHolder<>());

    // Assert
    assertTrue(actualMentionsResult.isEmpty());
  }

  /**
   * Test {@link UtilityFunctionsMapper#mentions(Object)}.
   *
   * <ul>
   *   <li>When {@code Event}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#mentions(Object)}
   */
  @Test
  @DisplayName("Test mentions(Object); when 'Event'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List UtilityFunctionsMapper.mentions(Object)"})
  void testMentions_whenEvent_thenReturnEmpty() throws MessageParserException {
    // Arrange and Act
    List<Long> actualMentionsResult = UtilityFunctionsMapper.mentions("Event");

    // Assert
    assertTrue(actualMentionsResult.isEmpty());
  }

  /**
   * Test {@link UtilityFunctionsMapper#hashTags(Object)}.
   *
   * <ul>
   *   <li>When {@link EventHolder#EventHolder()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#hashTags(Object)}
   */
  @Test
  @DisplayName("Test hashTags(Object); when EventHolder(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List UtilityFunctionsMapper.hashTags(Object)"})
  void testHashTags_whenEventHolder_thenReturnEmpty() throws MessageParserException {
    // Arrange and Act
    List<String> actualHashTagsResult = UtilityFunctionsMapper.hashTags(new EventHolder<>());

    // Assert
    assertTrue(actualHashTagsResult.isEmpty());
  }

  /**
   * Test {@link UtilityFunctionsMapper#hashTags(Object)}.
   *
   * <ul>
   *   <li>When {@code Event}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#hashTags(Object)}
   */
  @Test
  @DisplayName("Test hashTags(Object); when 'Event'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List UtilityFunctionsMapper.hashTags(Object)"})
  void testHashTags_whenEvent_thenReturnEmpty() throws MessageParserException {
    // Arrange and Act
    List<String> actualHashTagsResult = UtilityFunctionsMapper.hashTags("Event");

    // Assert
    assertTrue(actualHashTagsResult.isEmpty());
  }

  /**
   * Test {@link UtilityFunctionsMapper#cashTags(Object)}.
   *
   * <ul>
   *   <li>When {@link EventHolder#EventHolder()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#cashTags(Object)}
   */
  @Test
  @DisplayName("Test cashTags(Object); when EventHolder(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List UtilityFunctionsMapper.cashTags(Object)"})
  void testCashTags_whenEventHolder_thenReturnEmpty() throws MessageParserException {
    // Arrange and Act
    List<String> actualCashTagsResult = UtilityFunctionsMapper.cashTags(new EventHolder<>());

    // Assert
    assertTrue(actualCashTagsResult.isEmpty());
  }

  /**
   * Test {@link UtilityFunctionsMapper#cashTags(Object)}.
   *
   * <ul>
   *   <li>When {@code Event}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#cashTags(Object)}
   */
  @Test
  @DisplayName("Test cashTags(Object); when 'Event'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List UtilityFunctionsMapper.cashTags(Object)"})
  void testCashTags_whenEvent_thenReturnEmpty() throws MessageParserException {
    // Arrange and Act
    List<String> actualCashTagsResult = UtilityFunctionsMapper.cashTags("Event");

    // Assert
    assertTrue(actualCashTagsResult.isEmpty());
  }

  /**
   * Test {@link UtilityFunctionsMapper#emojis(Object)}.
   *
   * <ul>
   *   <li>When {@link EventHolder#EventHolder()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#emojis(Object)}
   */
  @Test
  @DisplayName("Test emojis(Object); when EventHolder(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map UtilityFunctionsMapper.emojis(Object)"})
  void testEmojis_whenEventHolder_thenReturnEmpty() throws MessageParserException {
    // Arrange and Act
    Map<String, String> actualEmojisResult = UtilityFunctionsMapper.emojis(new EventHolder<>());

    // Assert
    assertTrue(actualEmojisResult.isEmpty());
  }

  /**
   * Test {@link UtilityFunctionsMapper#emojis(Object)}.
   *
   * <ul>
   *   <li>When {@code Event}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link UtilityFunctionsMapper#emojis(Object)}
   */
  @Test
  @DisplayName("Test emojis(Object); when 'Event'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map UtilityFunctionsMapper.emojis(Object)"})
  void testEmojis_whenEvent_thenReturnEmpty() throws MessageParserException {
    // Arrange and Act
    Map<String, String> actualEmojisResult = UtilityFunctionsMapper.emojis("Event");

    // Assert
    assertTrue(actualEmojisResult.isEmpty());
  }
}
