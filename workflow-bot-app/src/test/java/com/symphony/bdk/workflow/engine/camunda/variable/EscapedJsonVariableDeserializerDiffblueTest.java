package com.symphony.bdk.workflow.engine.camunda.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.BeanProperty.Bogus;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.Impl;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.type.PlaceholderForType;
import com.fasterxml.jackson.databind.type.ResolvedRecursiveType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.util.AccessPattern;
import com.symphony.bdk.workflow.engine.camunda.CamundaExecutor;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class EscapedJsonVariableDeserializerDiffblueTest {
  /**
   * Test {@link EscapedJsonVariableDeserializer#EscapedJsonVariableDeserializer(Class)}.
   *
   * <p>Method under test: {@link
   * EscapedJsonVariableDeserializer#EscapedJsonVariableDeserializer(Class)}
   */
  @Test
  @DisplayName("Test new EscapedJsonVariableDeserializer(Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EscapedJsonVariableDeserializer.<init>(Class)"})
  void testNewEscapedJsonVariableDeserializer() {
    // Arrange
    Class<Object> containerType = Object.class;

    // Act
    EscapedJsonVariableDeserializer<Object> actualEscapedJsonVariableDeserializer =
        new EscapedJsonVariableDeserializer<>(containerType);

    // Assert
    assertNull(actualEscapedJsonVariableDeserializer.getDelegatee());
    assertNull(actualEscapedJsonVariableDeserializer.getObjectIdReader());
    assertNull(actualEscapedJsonVariableDeserializer.getEmptyValue());
    assertNull(actualEscapedJsonVariableDeserializer.getNullValue());
    assertNull(actualEscapedJsonVariableDeserializer.getKnownPropertyNames());
    assertEquals(
        AccessPattern.CONSTANT, actualEscapedJsonVariableDeserializer.getNullAccessPattern());
    assertEquals(
        AccessPattern.DYNAMIC, actualEscapedJsonVariableDeserializer.getEmptyAccessPattern());
    assertFalse(actualEscapedJsonVariableDeserializer.isCachable());
  }

  /**
   * Test {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext,
   * BeanProperty)}.
   *
   * <p>Method under test: {@link
   * EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  @DisplayName("Test createContextual(DeserializationContext, BeanProperty)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JsonDeserializer EscapedJsonVariableDeserializer.createContextual(DeserializationContext, BeanProperty)"
  })
  void testCreateContextual() {
    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer =
        new EscapedJsonVariableDeserializer<>(containerType);

    // Act
    JsonDeserializer<?> actualCreateContextualResult =
        escapedJsonVariableDeserializer.createContextual(
            new Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig())), null);

    // Assert
    assertSame(escapedJsonVariableDeserializer, actualCreateContextualResult);
  }

  /**
   * Test {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext,
   * BeanProperty)}.
   *
   * <p>Method under test: {@link
   * EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  @DisplayName("Test createContextual(DeserializationContext, BeanProperty)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JsonDeserializer EscapedJsonVariableDeserializer.createContextual(DeserializationContext, BeanProperty)"
  })
  void testCreateContextual2() {
    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer =
        new EscapedJsonVariableDeserializer<>(containerType);
    Impl ctxt = new Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig()));

    Bogus property = mock(Bogus.class);
    Class<Object> erasedType = Object.class;
    when(property.getType())
        .thenReturn(new ResolvedRecursiveType(erasedType, TypeBindings.emptyBindings()));

    // Act
    JsonDeserializer<?> actualCreateContextualResult =
        escapedJsonVariableDeserializer.createContextual(ctxt, property);

    // Assert
    verify(property).getType();
    assertTrue(actualCreateContextualResult instanceof EscapedJsonVariableDeserializer);
    assertNull(actualCreateContextualResult.getDelegatee());
    assertNull(actualCreateContextualResult.getObjectIdReader());
    assertNull(actualCreateContextualResult.getEmptyValue());
    assertNull(actualCreateContextualResult.getNullValue());
    assertNull(actualCreateContextualResult.getKnownPropertyNames());
    assertEquals(AccessPattern.CONSTANT, actualCreateContextualResult.getNullAccessPattern());
    assertEquals(AccessPattern.DYNAMIC, actualCreateContextualResult.getEmptyAccessPattern());
    assertFalse(actualCreateContextualResult.isCachable());
  }

  /**
   * Test {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext,
   * BeanProperty)}.
   *
   * <ul>
   *   <li>Given {@link PlaceholderForType#PlaceholderForType(int)} with ordinal is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  @DisplayName(
      "Test createContextual(DeserializationContext, BeanProperty); given PlaceholderForType(int) with ordinal is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JsonDeserializer EscapedJsonVariableDeserializer.createContextual(DeserializationContext, BeanProperty)"
  })
  void testCreateContextual_givenPlaceholderForTypeWithOrdinalIsOne() {
    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer =
        new EscapedJsonVariableDeserializer<>(containerType);
    Impl ctxt = new Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig()));

    Bogus property = mock(Bogus.class);
    when(property.getType()).thenReturn(new PlaceholderForType(1));

    // Act
    JsonDeserializer<?> actualCreateContextualResult =
        escapedJsonVariableDeserializer.createContextual(ctxt, property);

    // Assert
    verify(property).getType();
    assertTrue(actualCreateContextualResult instanceof EscapedJsonVariableDeserializer);
    assertNull(actualCreateContextualResult.getDelegatee());
    assertNull(actualCreateContextualResult.getObjectIdReader());
    assertNull(actualCreateContextualResult.getEmptyValue());
    assertNull(actualCreateContextualResult.getNullValue());
    assertNull(actualCreateContextualResult.getKnownPropertyNames());
    assertEquals(AccessPattern.CONSTANT, actualCreateContextualResult.getNullAccessPattern());
    assertEquals(AccessPattern.DYNAMIC, actualCreateContextualResult.getEmptyAccessPattern());
    assertFalse(actualCreateContextualResult.isCachable());
  }

  /**
   * Test {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext,
   * BeanProperty)}.
   *
   * <ul>
   *   <li>Then calls {@link JavaType#getBindings()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  @DisplayName(
      "Test createContextual(DeserializationContext, BeanProperty); then calls getBindings()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JsonDeserializer EscapedJsonVariableDeserializer.createContextual(DeserializationContext, BeanProperty)"
  })
  void testCreateContextual_thenCallsGetBindings() {
    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer =
        new EscapedJsonVariableDeserializer<>(containerType);
    Impl ctxt = new Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig()));

    TypeBindings typeBindings = mock(TypeBindings.class);
    when(typeBindings.getTypeParameters()).thenReturn(new ArrayList<>());

    JavaType javaType = mock(JavaType.class);
    when(javaType.getBindings()).thenReturn(typeBindings);

    Bogus property = mock(Bogus.class);
    when(property.getType()).thenReturn(javaType);

    // Act
    JsonDeserializer<?> actualCreateContextualResult =
        escapedJsonVariableDeserializer.createContextual(ctxt, property);

    // Assert
    verify(property).getType();
    verify(javaType).getBindings();
    verify(typeBindings).getTypeParameters();
    assertTrue(actualCreateContextualResult instanceof EscapedJsonVariableDeserializer);
    assertNull(actualCreateContextualResult.getDelegatee());
    assertNull(actualCreateContextualResult.getObjectIdReader());
    assertNull(actualCreateContextualResult.getEmptyValue());
    assertNull(actualCreateContextualResult.getNullValue());
    assertNull(actualCreateContextualResult.getKnownPropertyNames());
    assertEquals(AccessPattern.CONSTANT, actualCreateContextualResult.getNullAccessPattern());
    assertEquals(AccessPattern.DYNAMIC, actualCreateContextualResult.getEmptyAccessPattern());
    assertFalse(actualCreateContextualResult.isCachable());
  }

  /**
   * Test {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext,
   * BeanProperty)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  @DisplayName(
      "Test createContextual(DeserializationContext, BeanProperty); then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JsonDeserializer EscapedJsonVariableDeserializer.createContextual(DeserializationContext, BeanProperty)"
  })
  void testCreateContextual_thenThrowIllegalArgumentException() {
    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer =
        new EscapedJsonVariableDeserializer<>(containerType);
    Impl ctxt = new Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig()));

    ArrayList<JavaType> javaTypeList = new ArrayList<>();
    javaTypeList.add(new PlaceholderForType(1));

    TypeBindings typeBindings = mock(TypeBindings.class);
    when(typeBindings.getTypeParameters()).thenReturn(javaTypeList);

    JavaType javaType = mock(JavaType.class);
    when(javaType.getBindings()).thenReturn(typeBindings);

    Bogus property = mock(Bogus.class);
    when(property.getType()).thenReturn(javaType);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> escapedJsonVariableDeserializer.createContextual(ctxt, property));
    verify(property).getType();
    verify(javaType).getBindings();
    verify(typeBindings).getTypeParameters();
  }

  /**
   * Test {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext,
   * BeanProperty)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  @DisplayName(
      "Test createContextual(DeserializationContext, BeanProperty); then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JsonDeserializer EscapedJsonVariableDeserializer.createContextual(DeserializationContext, BeanProperty)"
  })
  void testCreateContextual_thenThrowIllegalArgumentException2() {
    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer =
        new EscapedJsonVariableDeserializer<>(containerType);
    Impl ctxt = new Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig()));

    ArrayList<JavaType> javaTypeList = new ArrayList<>();
    javaTypeList.add(new PlaceholderForType(1));
    javaTypeList.add(new PlaceholderForType(1));

    TypeBindings typeBindings = mock(TypeBindings.class);
    when(typeBindings.getTypeParameters()).thenReturn(javaTypeList);

    JavaType javaType = mock(JavaType.class);
    when(javaType.getBindings()).thenReturn(typeBindings);

    Bogus property = mock(Bogus.class);
    when(property.getType()).thenReturn(javaType);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> escapedJsonVariableDeserializer.createContextual(ctxt, property));
    verify(property).getType();
    verify(javaType).getBindings();
    verify(typeBindings).getTypeParameters();
  }

  /**
   * Test {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext,
   * BeanProperty)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  @DisplayName(
      "Test createContextual(DeserializationContext, BeanProperty); then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JsonDeserializer EscapedJsonVariableDeserializer.createContextual(DeserializationContext, BeanProperty)"
  })
  void testCreateContextual_thenThrowIllegalArgumentException3() {
    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer =
        new EscapedJsonVariableDeserializer<>(containerType);
    Impl ctxt = new Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig()));

    ArrayList<JavaType> javaTypeList = new ArrayList<>();
    javaTypeList.add(new PlaceholderForType(1));
    javaTypeList.add(new PlaceholderForType(1));
    javaTypeList.add(new PlaceholderForType(1));

    TypeBindings typeBindings = mock(TypeBindings.class);
    when(typeBindings.getTypeParameters()).thenReturn(javaTypeList);

    JavaType javaType = mock(JavaType.class);
    when(javaType.getBindings()).thenReturn(typeBindings);

    Bogus property = mock(Bogus.class);
    when(property.getType()).thenReturn(javaType);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> escapedJsonVariableDeserializer.createContextual(ctxt, property));
    verify(property).getType();
    verify(javaType).getBindings();
    verify(typeBindings).getTypeParameters();
  }

  /**
   * Test {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext,
   * BeanProperty)}.
   *
   * <ul>
   *   <li>When {@link Bogus} (default constructor).
   *   <li>Then return {@link EscapedJsonVariableDeserializer}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  @DisplayName(
      "Test createContextual(DeserializationContext, BeanProperty); when Bogus (default constructor); then return EscapedJsonVariableDeserializer")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "JsonDeserializer EscapedJsonVariableDeserializer.createContextual(DeserializationContext, BeanProperty)"
  })
  void testCreateContextual_whenBogus_thenReturnEscapedJsonVariableDeserializer() {
    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer =
        new EscapedJsonVariableDeserializer<>(containerType);
    Impl ctxt = new Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig()));

    // Act
    JsonDeserializer<?> actualCreateContextualResult =
        escapedJsonVariableDeserializer.createContextual(ctxt, new Bogus());

    // Assert
    assertTrue(actualCreateContextualResult instanceof EscapedJsonVariableDeserializer);
    assertNull(actualCreateContextualResult.getDelegatee());
    assertNull(actualCreateContextualResult.getObjectIdReader());
    assertNull(actualCreateContextualResult.getEmptyValue());
    assertNull(actualCreateContextualResult.getNullValue());
    assertNull(actualCreateContextualResult.getKnownPropertyNames());
    assertEquals(AccessPattern.CONSTANT, actualCreateContextualResult.getNullAccessPattern());
    assertEquals(AccessPattern.DYNAMIC, actualCreateContextualResult.getEmptyAccessPattern());
    assertFalse(actualCreateContextualResult.isCachable());
  }

  /**
   * Test {@link EscapedJsonVariableDeserializer#deserialize(JsonParser, DeserializationContext)}
   * with {@code p}, {@code ctxt}.
   *
   * <p>Method under test: {@link EscapedJsonVariableDeserializer#deserialize(JsonParser,
   * DeserializationContext)}
   */
  @Test
  @DisplayName("Test deserialize(JsonParser, DeserializationContext) with 'p', 'ctxt'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object EscapedJsonVariableDeserializer.deserialize(JsonParser, DeserializationContext)"
  })
  void testDeserializeWithPCtxt() throws IOException {
    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer =
        new EscapedJsonVariableDeserializer<>(containerType);
    DoubleNode n = DoubleNode.valueOf(10.0d);
    TreeTraversingParser p = new TreeTraversingParser(n, CamundaExecutor.OBJECT_MAPPER);

    // Act
    Object actualDeserializeResult =
        escapedJsonVariableDeserializer.deserialize(
            p, new Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig())));

    // Assert
    assertEquals(10, p.getIntValue());
    assertEquals(10.0d, p.getDoubleValue());
    assertEquals(10.0d, ((Double) p.getNumberValueDeferred()).doubleValue());
    assertEquals(10.0d, ((Double) actualDeserializeResult).doubleValue());
    assertEquals(10.0d, p.getNumberValueExact().doubleValue());
    assertEquals(10.0d, p.getNumberValue().doubleValue());
    assertEquals(10.0f, p.getFloatValue());
    assertEquals(10L, p.getLongValue());
    assertEquals((short) 10, p.getShortValue());
    assertEquals(NumberType.DOUBLE, p.getNumberType());
    assertEquals(JsonToken.VALUE_NUMBER_FLOAT, p.getLastClearedToken());
    assertEquals(new BigDecimal("10.0"), p.getDecimalValue());
    assertEquals('\n', p.getByteValue());
  }
}
