package com.symphony.bdk.workflow.swadl.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessingMessageToSwadlErrorDiffblueTest {
  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given ArrayList() add valueOf ten; then calls iterator()")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenArrayListAddValueOfTen_thenCallsIterator() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);

    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);
    Optional<Integer> ofResult = Optional.of(42);
    when(yamlJsonPointer.getLine(Mockito.<JsonPointer>any())).thenReturn(ofResult);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(anyInt())).thenThrow(new RuntimeException());
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.has(Mockito.<String>any())).thenReturn(true);
    when(arrayNode2.at(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    when(validationError.asJson()).thenReturn(arrayNode2);
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(arrayNode2, atLeast(1)).at(Mockito.<String>any());
    verify(arrayNode2).has("reports");
    verify(arrayNode).iterator();
    verify(arrayNode2).get(0);
    verify(arrayNode2).get("reports");
    verify(validationError).asJson();
    verify(validationError, atLeast(1)).getMessage();
    verify(yamlJsonPointer).getLine(isA(JsonPointer.class));
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#at(String)} return {@link
   *       ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given ArrayNode at(String) return ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenArrayNodeAtReturnArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);

    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);
    Optional<Integer> ofResult = Optional.of(42);
    when(yamlJsonPointer.getLine(Mockito.<JsonPointer>any())).thenReturn(ofResult);

    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(anyInt())).thenThrow(new RuntimeException());
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.has(Mockito.<String>any())).thenReturn(true);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode2.at(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    when(validationError.asJson()).thenReturn(arrayNode2);
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(arrayNode2, atLeast(1)).at(Mockito.<String>any());
    verify(arrayNode2).has("reports");
    verify(arrayNode).iterator();
    verify(arrayNode2).get(0);
    verify(arrayNode2).get("reports");
    verify(validationError).asJson();
    verify(validationError, atLeast(1)).getMessage();
    verify(yamlJsonPointer).getLine(isA(JsonPointer.class));
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#at(String)} return {@code null}.
   *   <li>When {@link YamlJsonPointer}.
   *   <li>Then calls {@link ArrayNode#at(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given ArrayNode at(String) return 'null'; when YamlJsonPointer; then calls at(String)")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenArrayNodeAtReturnNull_whenYamlJsonPointer_thenCallsAt() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.at(Mockito.<String>any())).thenReturn(null);

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    when(validationError.asJson()).thenReturn(arrayNode);
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(arrayNode).at("/instance/pointer");
    verify(validationError).asJson();
    verify(validationError).getMessage();
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return {@link
   *       ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given ArrayNode get(String) return ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenArrayNodeGetReturnArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);

    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);
    Optional<Integer> ofResult = Optional.of(42);
    when(yamlJsonPointer.getLine(Mockito.<JsonPointer>any())).thenReturn(ofResult);

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(anyInt())).thenThrow(new RuntimeException());
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));
    when(arrayNode.has(Mockito.<String>any())).thenReturn(true);
    when(arrayNode.at(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    when(validationError.asJson()).thenReturn(arrayNode);
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(arrayNode, atLeast(1)).at(Mockito.<String>any());
    verify(arrayNode).has("reports");
    verify(arrayNode).get(0);
    verify(arrayNode).get("reports");
    verify(validationError).asJson();
    verify(validationError, atLeast(1)).getMessage();
    verify(yamlJsonPointer).getLine(isA(JsonPointer.class));
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return {@code null}.
   *   <li>When {@link YamlJsonPointer}.
   *   <li>Then calls {@link ArrayNode#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given ArrayNode get(String) return 'null'; when YamlJsonPointer; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenArrayNodeGetReturnNull_whenYamlJsonPointer_thenCallsGet() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(null);
    when(arrayNode.has(Mockito.<String>any())).thenReturn(true);
    when(arrayNode.at(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    when(validationError.asJson()).thenReturn(arrayNode);
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(arrayNode, atLeast(1)).at(Mockito.<String>any());
    verify(arrayNode).has("reports");
    verify(arrayNode).get("reports");
    verify(validationError).asJson();
    verify(validationError).getMessage();
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>Then calls {@link ArrayNode#get(int)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given ArrayNode get(String) return valueOf ten; then calls get(int)")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenArrayNodeGetReturnValueOfTen_thenCallsGet() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);

    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);
    Optional<Integer> ofResult = Optional.of(42);
    when(yamlJsonPointer.getLine(Mockito.<JsonPointer>any())).thenReturn(ofResult);

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(anyInt())).thenThrow(new RuntimeException());
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    when(arrayNode.has(Mockito.<String>any())).thenReturn(true);
    when(arrayNode.at(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    when(validationError.asJson()).thenReturn(arrayNode);
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(arrayNode, atLeast(1)).at(Mockito.<String>any());
    verify(arrayNode).has("reports");
    verify(arrayNode).get(0);
    verify(arrayNode).get("reports");
    verify(validationError).asJson();
    verify(validationError, atLeast(1)).getMessage();
    verify(yamlJsonPointer).getLine(isA(JsonPointer.class));
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(int)} return valueOf ten.
   *   <li>Then return LineNumber is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given ArrayNode get(int) return valueOf ten; then return LineNumber is forty-two")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenArrayNodeGetReturnValueOfTen_thenReturnLineNumberIsFortyTwo() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);

    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);
    Optional<Integer> ofResult = Optional.of(42);
    when(yamlJsonPointer.getLine(Mockito.<JsonPointer>any())).thenReturn(ofResult);

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(anyInt())).thenReturn(DoubleNode.valueOf(10.0d));
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    when(arrayNode.has(Mockito.<String>any())).thenReturn(true);
    when(arrayNode.at(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    when(validationError.asJson()).thenReturn(arrayNode);
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(arrayNode, atLeast(1)).at(Mockito.<String>any());
    verify(arrayNode).has("reports");
    verify(arrayNode).get(0);
    verify(arrayNode).get("reports");
    verify(validationError).asJson();
    verify(validationError).getMessage();
    verify(yamlJsonPointer).getLine(isA(JsonPointer.class));
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(42, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>When {@link YamlJsonPointer}.
   *   <li>Then calls {@link ArrayNode#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given ArrayNode get(String) return valueOf ten; when YamlJsonPointer; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenArrayNodeGetReturnValueOfTen_whenYamlJsonPointer_thenCallsGet() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    when(arrayNode.has(Mockito.<String>any())).thenReturn(true);
    when(arrayNode.at(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    when(validationError.asJson()).thenReturn(arrayNode);
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(arrayNode).at("/instance/pointer");
    verify(arrayNode).has("reports");
    verify(arrayNode).get("reports");
    verify(validationError).asJson();
    verify(validationError).getMessage();
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} throw {@link
   *       RuntimeException#RuntimeException()}.
   *   <li>When {@link YamlJsonPointer}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given ArrayNode get(String) throw RuntimeException(); when YamlJsonPointer")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenArrayNodeGetThrowRuntimeException_whenYamlJsonPointer() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenThrow(new RuntimeException());
    when(arrayNode.has(Mockito.<String>any())).thenReturn(true);
    when(arrayNode.at(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    when(validationError.asJson()).thenReturn(arrayNode);
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(arrayNode).at("/instance/pointer");
    verify(arrayNode).has("reports");
    verify(arrayNode).get("reports");
    verify(validationError).asJson();
    verify(validationError).getMessage();
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#has(String)} throw {@link
   *       RuntimeException#RuntimeException()}.
   *   <li>When {@link YamlJsonPointer}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given ArrayNode has(String) throw RuntimeException(); when YamlJsonPointer")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenArrayNodeHasThrowRuntimeException_whenYamlJsonPointer() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.has(Mockito.<String>any())).thenThrow(new RuntimeException());
    when(arrayNode.at(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    when(validationError.asJson()).thenReturn(arrayNode);
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(arrayNode).at("/instance/pointer");
    verify(arrayNode).has("reports");
    verify(validationError).asJson();
    verify(validationError).getMessage();
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isMissingNode()} return {@code true}.
   *   <li>Then calls {@link ArrayNode#isMissingNode()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given ArrayNode isMissingNode() return 'true'; then calls isMissingNode()")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenArrayNodeIsMissingNodeReturnTrue_thenCallsIsMissingNode() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);

    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);
    Optional<Integer> ofResult = Optional.of(42);
    when(yamlJsonPointer.getLine(Mockito.<JsonPointer>any())).thenReturn(ofResult);

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isMissingNode()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("");

    ArrayNode arrayNode2 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(anyInt())).thenThrow(new RuntimeException());
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.has(Mockito.<String>any())).thenReturn(true);
    when(arrayNode3.at(Mockito.<String>any())).thenReturn(arrayNode);

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    when(validationError.asJson()).thenReturn(arrayNode3);
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(arrayNode3, atLeast(1)).at(Mockito.<String>any());
    verify(arrayNode3).has("reports");
    verify(arrayNode).isMissingNode();
    verify(arrayNode2).iterator();
    verify(arrayNode3).get(0);
    verify(arrayNode3).get("reports");
    verify(arrayNode).asText();
    verify(validationError).asJson();
    verify(validationError, atLeast(1)).getMessage();
    verify(yamlJsonPointer).getLine(isA(JsonPointer.class));
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isMissingNode()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given ArrayNode isMissingNode() throw RuntimeException()")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenArrayNodeIsMissingNodeThrowRuntimeException() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isMissingNode()).thenThrow(new RuntimeException());
    when(arrayNode.asText()).thenReturn("");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.at(Mockito.<String>any())).thenReturn(arrayNode);

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    when(validationError.asJson()).thenReturn(arrayNode2);
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(arrayNode2, atLeast(1)).at(Mockito.<String>any());
    verify(arrayNode).isMissingNode();
    verify(arrayNode).asText();
    verify(validationError).asJson();
    verify(validationError).getMessage();
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#iterator()} return {@link ArrayList#ArrayList()}
   *       iterator.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given ArrayNode iterator() return ArrayList() iterator; then calls iterator()")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenArrayNodeIteratorReturnArrayListIterator_thenCallsIterator() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);

    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);
    Optional<Integer> ofResult = Optional.of(42);
    when(yamlJsonPointer.getLine(Mockito.<JsonPointer>any())).thenReturn(ofResult);

    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(anyInt())).thenThrow(new RuntimeException());
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.has(Mockito.<String>any())).thenReturn(true);
    when(arrayNode2.at(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    when(validationError.asJson()).thenReturn(arrayNode2);
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(arrayNode2, atLeast(1)).at(Mockito.<String>any());
    verify(arrayNode2).has("reports");
    verify(arrayNode).iterator();
    verify(arrayNode2).get(0);
    verify(arrayNode2).get("reports");
    verify(validationError).asJson();
    verify(validationError, atLeast(1)).getMessage();
    verify(yamlJsonPointer).getLine(isA(JsonPointer.class));
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);

    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);
    Optional<Integer> ofResult = Optional.of(42);
    when(yamlJsonPointer.getLine(Mockito.<JsonPointer>any())).thenReturn(ofResult);

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(validationError.asJson()).thenReturn(new ArrayNode(nf));
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(validationError).asJson();
    verify(validationError).getMessage();
    verify(yamlJsonPointer).getLine(isA(JsonPointer.class));
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(42, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@code message}.
   *   <li>Then return Message is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given 'message'; then return Message is 'null'")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenMessage_thenReturnMessageIsNull() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(new StringReader("foo"));

    ProcessingMessage validationError = new ProcessingMessage();
    validationError.put("message", DoubleNode.valueOf(10.0d));

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    assertNull(actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ProcessingMessage} {@link ProcessingMessage#asJson()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given 'null'; when ProcessingMessage asJson() return 'null'")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenNull_whenProcessingMessageAsJsonReturnNull() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    when(validationError.asJson()).thenReturn(null);
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(validationError).asJson();
    verify(validationError).getMessage();
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link YamlJsonPointer} {@link YamlJsonPointer#getLine(JsonPointer)} return {@code
   *       null}.
   *   <li>Then calls {@link YamlJsonPointer#getLine(JsonPointer)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given 'null'; when YamlJsonPointer getLine(JsonPointer) return 'null'; then calls getLine(JsonPointer)")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenNull_whenYamlJsonPointerGetLineReturnNull_thenCallsGetLine() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);

    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);
    when(yamlJsonPointer.getLine(Mockito.<JsonPointer>any())).thenReturn(null);

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    when(validationError.asJson()).thenReturn(DoubleNode.valueOf(10.0d));
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(validationError).asJson();
    verify(validationError).getMessage();
    verify(yamlJsonPointer).getLine(isA(JsonPointer.class));
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given of forty-two.
   *   <li>When {@link ProcessingMessage} (default constructor).
   *   <li>Then return Message is {@code (no message)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given of forty-two; when ProcessingMessage (default constructor); then return Message is '(no message)'")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenOfFortyTwo_whenProcessingMessage_thenReturnMessageIsNoMessage() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);

    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);
    Optional<Integer> ofResult = Optional.of(42);
    when(yamlJsonPointer.getLine(Mockito.<JsonPointer>any())).thenReturn(ofResult);

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, new ProcessingMessage());

    // Assert
    verify(yamlJsonPointer).getLine(isA(JsonPointer.class));
    assertEquals("(no message)", actualConvertResult.getMessage());
    assertEquals(42, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When {@link ProcessingMessage} {@link ProcessingMessage#asJson()} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given valueOf ten; when ProcessingMessage asJson() return valueOf ten")
  @Tag("MaintainedByDiffblue")
  void testConvert_givenValueOfTen_whenProcessingMessageAsJsonReturnValueOfTen() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);

    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);
    Optional<Integer> ofResult = Optional.of(42);
    when(yamlJsonPointer.getLine(Mockito.<JsonPointer>any())).thenReturn(ofResult);

    ProcessingMessage validationError = mock(ProcessingMessage.class);
    when(validationError.asJson()).thenReturn(DoubleNode.valueOf(10.0d));
    when(validationError.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, validationError);

    // Assert
    verify(validationError).asJson();
    verify(validationError).getMessage();
    verify(yamlJsonPointer).getLine(isA(JsonPointer.class));
    assertEquals("Not all who wander are lost", actualConvertResult.getMessage());
    assertEquals(42, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Message is {@code (no message)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); when 'null'; then return Message is '(no message)'")
  @Tag("MaintainedByDiffblue")
  void testConvert_whenNull_thenReturnMessageIsNoMessage() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, null, new ProcessingMessage());

    // Assert
    assertEquals("(no message)", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}.
   *
   * <ul>
   *   <li>When {@link StringReader#StringReader(String)} with {@code foo}.
   *   <li>Then return Message is {@code (no message)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer,
   * ProcessingMessage)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); when StringReader(String) with 'foo'; then return Message is '(no message)'")
  @Tag("MaintainedByDiffblue")
  void testConvert_whenStringReaderWithFoo_thenReturnMessageIsNoMessage() {
    // Arrange
    DoubleNode yamlTree = DoubleNode.valueOf(10.0d);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(new StringReader("foo"));

    // Act
    SwadlError actualConvertResult =
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, new ProcessingMessage());

    // Assert
    assertEquals("(no message)", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }
}
