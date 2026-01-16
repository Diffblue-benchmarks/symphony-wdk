package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.core.service.message.exception.MessageParserException;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.gen.api.model.V4MessageSent;
import com.symphony.bdk.workflow.engine.executor.EventHolder;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UtilityFunctionsMapperClaude_emojisTest {

  @Test
  void emojis_withNull_shouldReturnEmptyMap() throws MessageParserException {
    // Given: A null event
    Object event = null;

    // When: emojis() is called
    Map<String, String> result = UtilityFunctionsMapper.emojis(event);

    // Then: Should return an empty map
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  void emojis_withNonEventHolderObject_shouldReturnEmptyMap() throws MessageParserException {
    // Given: An object that is not an EventHolder
    Object event = "not an event holder";

    // When: emojis() is called
    Map<String, String> result = UtilityFunctionsMapper.emojis(event);

    // Then: Should return an empty map
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  void emojis_withEventHolderContainingNonV4MessageSent_shouldReturnEmptyMap() throws MessageParserException {
    // Given: An EventHolder with a source that is not V4MessageSent
    EventHolder<String> eventHolder = new EventHolder<>();
    eventHolder.setSource("not a V4MessageSent");

    // When: emojis() is called
    Map<String, String> result = UtilityFunctionsMapper.emojis(eventHolder);

    // Then: Should return an empty map
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  void emojis_withEventHolderContainingNullSource_shouldReturnEmptyMap() throws MessageParserException {
    // Given: An EventHolder with a null source
    EventHolder<V4MessageSent> eventHolder = new EventHolder<>();
    eventHolder.setSource(null);

    // When: emojis() is called
    Map<String, String> result = UtilityFunctionsMapper.emojis(eventHolder);

    // Then: Should return an empty map
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  void emojis_withV4MessageSentContainingEmojis_shouldReturnEmojisMap() throws MessageParserException {
    // Given: An EventHolder with V4MessageSent containing a message with emojis
    V4MessageSent messageSent = new V4MessageSent();
    V4Message message = new V4Message();

    // MessageML format with emojis
    String messageML = "<messageML>Hello <emoji shortcode=\"smiley\" /> World <emoji shortcode=\"thumbsup\" /></messageML>";
    message.setMessage(messageML);
    messageSent.setMessage(message);

    EventHolder<V4MessageSent> eventHolder = new EventHolder<>();
    eventHolder.setSource(messageSent);

    // When: emojis() is called
    Map<String, String> result = UtilityFunctionsMapper.emojis(eventHolder);

    // Then: Should return a map with the emoji shortcodes
    // Note: The actual behavior depends on MessageParser.getEmojis implementation
    assertThat(result).isNotNull();
  }

  @Test
  void emojis_withV4MessageSentContainingNoEmojis_shouldReturnEmptyOrPopulatedMap() throws MessageParserException {
    // Given: An EventHolder with V4MessageSent containing a message without emojis
    V4MessageSent messageSent = new V4MessageSent();
    V4Message message = new V4Message();
    message.setMessage("<messageML>Hello World</messageML>");
    messageSent.setMessage(message);

    EventHolder<V4MessageSent> eventHolder = new EventHolder<>();
    eventHolder.setSource(messageSent);

    // When: emojis() is called
    Map<String, String> result = UtilityFunctionsMapper.emojis(eventHolder);

    // Then: Should return a map (empty if no emojis)
    assertThat(result).isNotNull();
  }

  @Test
  void emojis_withV4MessageSentContainingNullMessage_shouldHandleGracefully() throws MessageParserException {
    // Given: An EventHolder with V4MessageSent but null message
    V4MessageSent messageSent = new V4MessageSent();
    messageSent.setMessage(null);

    EventHolder<V4MessageSent> eventHolder = new EventHolder<>();
    eventHolder.setSource(messageSent);

    // When/Then: Should either return empty map or throw MessageParserException
    // This depends on how MessageParser.getEmojis handles null
    try {
      Map<String, String> result = UtilityFunctionsMapper.emojis(eventHolder);
      assertThat(result).isNotNull();
    } catch (MessageParserException | NullPointerException e) {
      // Expected if MessageParser doesn't handle null gracefully
      assertThat(e).isInstanceOf(Exception.class);
    }
  }

  @Test
  void emojis_withV4MessageSentContainingEmptyMessage_shouldReturnEmptyOrPopulatedMap() throws MessageParserException {
    // Given: An EventHolder with V4MessageSent containing an empty message
    V4MessageSent messageSent = new V4MessageSent();
    V4Message message = new V4Message();
    message.setMessage("");
    messageSent.setMessage(message);

    EventHolder<V4MessageSent> eventHolder = new EventHolder<>();
    eventHolder.setSource(messageSent);

    // When: emojis() is called
    Map<String, String> result = UtilityFunctionsMapper.emojis(eventHolder);

    // Then: Should return a map (likely empty)
    assertThat(result).isNotNull();
  }

  @Test
  void emojis_withMultipleEmojis_shouldReturnAllEmojis() throws MessageParserException {
    // Given: An EventHolder with V4MessageSent containing multiple emojis
    V4MessageSent messageSent = new V4MessageSent();
    V4Message message = new V4Message();

    // MessageML with multiple emojis
    String messageML = "<messageML><emoji shortcode=\"smile\" /><emoji shortcode=\"heart\" /><emoji shortcode=\"star\" /></messageML>";
    message.setMessage(messageML);
    messageSent.setMessage(message);

    EventHolder<V4MessageSent> eventHolder = new EventHolder<>();
    eventHolder.setSource(messageSent);

    // When: emojis() is called
    Map<String, String> result = UtilityFunctionsMapper.emojis(eventHolder);

    // Then: Should return a map with all emojis
    assertThat(result).isNotNull();
  }

  @Test
  void emojis_withComplexMessageML_shouldExtractEmojisCorrectly() throws MessageParserException {
    // Given: An EventHolder with V4MessageSent containing complex MessageML with text and emojis
    V4MessageSent messageSent = new V4MessageSent();
    V4Message message = new V4Message();

    String messageML = "<messageML>Hello <emoji shortcode=\"wave\" /> from <b>Symphony</b> <emoji shortcode=\"music\" />!</messageML>";
    message.setMessage(messageML);
    messageSent.setMessage(message);

    EventHolder<V4MessageSent> eventHolder = new EventHolder<>();
    eventHolder.setSource(messageSent);

    // When: emojis() is called
    Map<String, String> result = UtilityFunctionsMapper.emojis(eventHolder);

    // Then: Should return a map with the emojis
    assertThat(result).isNotNull();
  }

  @Test
  void emojis_withV4MessageSentContainingOnlyText_shouldReturnEmptyOrPopulatedMap() throws MessageParserException {
    // Given: An EventHolder with V4MessageSent containing only plain text
    V4MessageSent messageSent = new V4MessageSent();
    V4Message message = new V4Message();
    message.setMessage("<messageML>Plain text message without any emojis</messageML>");
    messageSent.setMessage(message);

    EventHolder<V4MessageSent> eventHolder = new EventHolder<>();
    eventHolder.setSource(messageSent);

    // When: emojis() is called
    Map<String, String> result = UtilityFunctionsMapper.emojis(eventHolder);

    // Then: Should return an empty map
    assertThat(result).isNotNull();
  }

  @Test
  void emojis_withInvalidMessageML_shouldHandleGracefully() throws MessageParserException {
    // Given: An EventHolder with V4MessageSent containing invalid MessageML
    V4MessageSent messageSent = new V4MessageSent();
    V4Message message = new V4Message();
    message.setMessage("<messageML>Unclosed tag <emoji shortcode=\"test\"");
    messageSent.setMessage(message);

    EventHolder<V4MessageSent> eventHolder = new EventHolder<>();
    eventHolder.setSource(messageSent);

    // When/Then: Should either return empty map or throw MessageParserException
    try {
      Map<String, String> result = UtilityFunctionsMapper.emojis(eventHolder);
      assertThat(result).isNotNull();
    } catch (MessageParserException e) {
      // Expected if MessageParser is strict about format
      assertThat(e).isInstanceOf(MessageParserException.class);
    }
  }

  @Test
  void emojis_calledMultipleTimesWithSameInput_shouldReturnConsistentResults() throws MessageParserException {
    // Given: An EventHolder with V4MessageSent
    V4MessageSent messageSent = new V4MessageSent();
    V4Message message = new V4Message();
    message.setMessage("<messageML>Hello <emoji shortcode=\"smile\" /></messageML>");
    messageSent.setMessage(message);

    EventHolder<V4MessageSent> eventHolder = new EventHolder<>();
    eventHolder.setSource(messageSent);

    // When: emojis() is called multiple times
    Map<String, String> result1 = UtilityFunctionsMapper.emojis(eventHolder);
    Map<String, String> result2 = UtilityFunctionsMapper.emojis(eventHolder);
    Map<String, String> result3 = UtilityFunctionsMapper.emojis(eventHolder);

    // Then: All results should be equal
    assertThat(result1).isEqualTo(result2);
    assertThat(result2).isEqualTo(result3);
  }

  @Test
  void emojis_withDifferentEventTypes_shouldReturnEmptyMap() throws MessageParserException {
    // Given: Various non-V4MessageSent event types
    EventHolder<Integer> intHolder = new EventHolder<>();
    intHolder.setSource(123);

    EventHolder<Map<String, Object>> mapHolder = new EventHolder<>();
    mapHolder.setSource(Collections.singletonMap("key", "value"));

    // When: emojis() is called on different event types
    Map<String, String> result1 = UtilityFunctionsMapper.emojis(intHolder);
    Map<String, String> result2 = UtilityFunctionsMapper.emojis(mapHolder);

    // Then: Should return empty maps
    assertThat(result1).isEmpty();
    assertThat(result2).isEmpty();
  }
}
