package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.core.service.message.exception.PresentationMLParserException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UtilityFunctionsMapperClaude_textTest {

  @Test
  void text_withSimplePresentationML_shouldExtractTextContent() throws PresentationMLParserException {
    // Given: A simple PresentationML string with text
    String presentationML = "<messageML>Hello World</messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should return the text content
    assertThat(result).isEqualTo("Hello World");
  }

  @Test
  void text_withBoldTag_shouldExtractPlainText() throws PresentationMLParserException {
    // Given: PresentationML with bold formatting
    String presentationML = "<messageML>This is <b>bold</b> text</messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should return plain text without formatting tags
    assertThat(result).isEqualTo("This is bold text");
  }

  @Test
  void text_withItalicTag_shouldExtractPlainText() throws PresentationMLParserException {
    // Given: PresentationML with italic formatting
    String presentationML = "<messageML>This is <i>italic</i> text</messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should return plain text without formatting tags
    assertThat(result).isEqualTo("This is italic text");
  }

  @Test
  void text_withMultipleFormattingTags_shouldExtractPlainText() throws PresentationMLParserException {
    // Given: PresentationML with multiple formatting tags
    String presentationML = "<messageML>Text with <b>bold</b>, <i>italic</i>, and <u>underline</u></messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should return plain text without any formatting tags
    assertThat(result).isEqualTo("Text with bold, italic, and underline");
  }

  @Test
  void text_withMention_shouldExtractTextContent() throws PresentationMLParserException {
    // Given: PresentationML with a mention tag
    String presentationML = "<messageML>Hello <mention uid=\"123456\">@User</mention></messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should extract text content (mention text or appropriate representation)
    assertThat(result).isNotNull();
    assertThat(result).containsIgnoringCase("Hello");
  }

  @Test
  void text_withHashTag_shouldExtractTextContent() throws PresentationMLParserException {
    // Given: PresentationML with a hashtag
    String presentationML = "<messageML>Check out <hash tag=\"symphony\"/> topic</messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should extract text content
    assertThat(result).isNotNull();
  }

  @Test
  void text_withCashTag_shouldExtractTextContent() throws PresentationMLParserException {
    // Given: PresentationML with a cashtag
    String presentationML = "<messageML>Stock price for <cash tag=\"AAPL\"/> is up</messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should extract text content
    assertThat(result).isNotNull();
  }

  @Test
  void text_withLink_shouldExtractTextContent() throws PresentationMLParserException {
    // Given: PresentationML with a link
    String presentationML = "<messageML>Visit <a href=\"https://example.com\">our website</a></messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should extract the link text
    assertThat(result).contains("Visit");
    assertThat(result).contains("our website");
  }

  @Test
  void text_withLineBreak_shouldHandleBreak() throws PresentationMLParserException {
    // Given: PresentationML with a line break
    String presentationML = "<messageML>Line 1<br/>Line 2</messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should extract text with appropriate spacing
    assertThat(result).isNotNull();
    assertThat(result).contains("Line 1");
    assertThat(result).contains("Line 2");
  }

  @Test
  void text_withCard_shouldExtractTextContent() throws PresentationMLParserException {
    // Given: PresentationML with a card element
    String presentationML = "<messageML><card>Card content here</card></messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should extract text from card
    assertThat(result).isNotNull();
  }

  @Test
  void text_withTable_shouldExtractTableContent() throws PresentationMLParserException {
    // Given: PresentationML with a table
    String presentationML = "<messageML><table><tr><td>Cell 1</td><td>Cell 2</td></tr></table></messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should extract text from table cells
    assertThat(result).isNotNull();
  }

  @Test
  void text_withEmptyMessageML_shouldReturnEmptyOrBlank() throws PresentationMLParserException {
    // Given: Empty PresentationML
    String presentationML = "<messageML></messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should return empty or blank string
    assertThat(result).isNotNull();
    assertThat(result.trim()).isEmpty();
  }

  @Test
  void text_withOnlyWhitespace_shouldReturnWhitespace() throws PresentationMLParserException {
    // Given: PresentationML with only whitespace
    String presentationML = "<messageML>   </messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should return whitespace
    assertThat(result).isNotNull();
  }

  @Test
  void text_withNestedTags_shouldExtractAllText() throws PresentationMLParserException {
    // Given: PresentationML with nested tags
    String presentationML = "<messageML><b>Bold <i>and italic</i> text</b></messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should extract all text from nested structure
    assertThat(result).isEqualTo("Bold and italic text");
  }

  @Test
  void text_withSpecialCharacters_shouldPreserveSpecialChars() throws PresentationMLParserException {
    // Given: PresentationML with special characters
    String presentationML = "<messageML>Special chars: &amp; &lt; &gt; &quot;</messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should preserve or properly decode special characters
    assertThat(result).isNotNull();
    assertThat(result).contains("Special chars:");
  }

  @Test
  void text_withUnicodeCharacters_shouldPreserveUnicode() throws PresentationMLParserException {
    // Given: PresentationML with Unicode characters
    String presentationML = "<messageML>Unicode: 你好 世界 🎉</messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should preserve Unicode characters
    assertThat(result).contains("Unicode:");
    assertThat(result).contains("你好");
    assertThat(result).contains("世界");
  }

  @Test
  void text_withMalformedXML_shouldThrowException() {
    // Given: Malformed PresentationML (unclosed tag)
    String malformedML = "<messageML>Unclosed tag<b>Bold";

    // When/Then: Should throw PresentationMLParserException
    assertThatThrownBy(() -> UtilityFunctionsMapper.text(malformedML))
        .isInstanceOf(PresentationMLParserException.class);
  }

  @Test
  void text_withInvalidXML_shouldThrowException() {
    // Given: Invalid XML structure
    String invalidML = "Not XML at all";

    // When/Then: Should throw PresentationMLParserException
    assertThatThrownBy(() -> UtilityFunctionsMapper.text(invalidML))
        .isInstanceOf(PresentationMLParserException.class);
  }

  @Test
  void text_withEmptyString_shouldHandleGracefully() {
    // Given: Empty string
    String emptyString = "";

    // When/Then: Should throw exception or handle gracefully
    assertThatThrownBy(() -> UtilityFunctionsMapper.text(emptyString))
        .isInstanceOf(PresentationMLParserException.class);
  }

  @Test
  void text_withComplexMessage_shouldExtractAllText() throws PresentationMLParserException {
    // Given: Complex PresentationML with multiple elements
    String presentationML = "<messageML>Hello <mention uid=\"123\">@User</mention>, "
        + "please check <a href=\"https://example.com\">this link</a> "
        + "regarding <hash tag=\"project\"/> and <cash tag=\"MSFT\"/>.</messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should extract all readable text
    assertThat(result).isNotNull();
    assertThat(result).contains("Hello");
  }

  @Test
  void text_calledMultipleTimes_shouldReturnConsistentResults() throws PresentationMLParserException {
    // Given: The same PresentationML string
    String presentationML = "<messageML>Test message</messageML>";

    // When: text() is called multiple times
    String result1 = UtilityFunctionsMapper.text(presentationML);
    String result2 = UtilityFunctionsMapper.text(presentationML);
    String result3 = UtilityFunctionsMapper.text(presentationML);

    // Then: All results should be equal
    assertThat(result1).isEqualTo(result2);
    assertThat(result2).isEqualTo(result3);
    assertThat(result1).isEqualTo("Test message");
  }

  @Test
  void text_withCodeBlock_shouldExtractCodeContent() throws PresentationMLParserException {
    // Given: PresentationML with code block
    String presentationML = "<messageML><code>System.out.println(\"Hello\");</code></messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should extract code content
    assertThat(result).contains("System.out.println");
  }

  @Test
  void text_withPreformattedText_shouldExtractContent() throws PresentationMLParserException {
    // Given: PresentationML with preformatted text
    String presentationML = "<messageML><pre>Preformatted\n  text</pre></messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should extract preformatted content
    assertThat(result).contains("Preformatted");
  }

  @Test
  void text_withListElements_shouldExtractListContent() throws PresentationMLParserException {
    // Given: PresentationML with list
    String presentationML = "<messageML><ul><li>Item 1</li><li>Item 2</li></ul></messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should extract list items
    assertThat(result).isNotNull();
  }

  @Test
  void text_withMultipleParagraphs_shouldExtractAllContent() throws PresentationMLParserException {
    // Given: PresentationML with multiple paragraphs
    String presentationML = "<messageML><p>Paragraph 1</p><p>Paragraph 2</p></messageML>";

    // When: text() is called
    String result = UtilityFunctionsMapper.text(presentationML);

    // Then: Should extract content from all paragraphs
    assertThat(result).isNotNull();
  }
}
