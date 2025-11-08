package com.symphony.bdk.workflow.engine.camunda.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.StreamReadCapability;
import com.fasterxml.jackson.core.json.ReaderBasedJsonParser;
import com.fasterxml.jackson.core.util.JacksonFeatureSet;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.type.PlaceholderForType;
import com.fasterxml.jackson.databind.type.ResolvedRecursiveType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.util.AccessPattern;
import com.symphony.bdk.workflow.engine.camunda.CamundaExecutor;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EscapedJsonVariableDeserializerDiffblueTest {
  /**
   * Method under test:
   * {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  void testCreateContextual() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer = new EscapedJsonVariableDeserializer<>(
        containerType);
    DefaultDeserializationContext.Impl ctxt = new DefaultDeserializationContext.Impl(
        new BeanDeserializerFactory(new DeserializerFactoryConfig()));

    // Act
    JsonDeserializer<?> actualCreateContextualResult = escapedJsonVariableDeserializer.createContextual(ctxt,
        new BeanProperty.Bogus());

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
   * Method under test:
   * {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  void testCreateContextual2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer = new EscapedJsonVariableDeserializer<>(
        containerType);

    // Act and Assert
    assertSame(escapedJsonVariableDeserializer, escapedJsonVariableDeserializer.createContextual(
        new DefaultDeserializationContext.Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig())), null));
  }

  /**
   * Method under test:
   * {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  void testCreateContextual3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer = new EscapedJsonVariableDeserializer<>(
        containerType);
    DefaultDeserializationContext.Impl ctxt = new DefaultDeserializationContext.Impl(
        new BeanDeserializerFactory(mock(DeserializerFactoryConfig.class)));

    // Act
    JsonDeserializer<?> actualCreateContextualResult = escapedJsonVariableDeserializer.createContextual(ctxt,
        new BeanProperty.Bogus());

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
   * Method under test:
   * {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  void testCreateContextual4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer = new EscapedJsonVariableDeserializer<>(
        containerType);
    DefaultDeserializationContext.Impl ctxt = new DefaultDeserializationContext.Impl(
        new BeanDeserializerFactory(new DeserializerFactoryConfig()));
    BeanProperty property = mock(BeanProperty.class);
    when(property.getType()).thenReturn(new PlaceholderForType(1));

    // Act
    JsonDeserializer<?> actualCreateContextualResult = escapedJsonVariableDeserializer.createContextual(ctxt, property);

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
   * Method under test:
   * {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  void testCreateContextual5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer = new EscapedJsonVariableDeserializer<>(
        containerType);
    DefaultDeserializationContext.Impl ctxt = new DefaultDeserializationContext.Impl(
        new BeanDeserializerFactory(new DeserializerFactoryConfig()));
    BeanProperty property = mock(BeanProperty.class);
    Class<Object> erasedType = Object.class;
    when(property.getType()).thenReturn(new ResolvedRecursiveType(erasedType, TypeBindings.emptyBindings()));

    // Act
    JsonDeserializer<?> actualCreateContextualResult = escapedJsonVariableDeserializer.createContextual(ctxt, property);

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
   * Method under test:
   * {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  void testCreateContextual6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer = new EscapedJsonVariableDeserializer<>(
        containerType);
    DefaultDeserializationContext.Impl ctxt = new DefaultDeserializationContext.Impl(
        new BeanDeserializerFactory(new DeserializerFactoryConfig()));
    TypeBindings typeBindings = mock(TypeBindings.class);
    when(typeBindings.getTypeParameters()).thenReturn(new ArrayList<>());
    JavaType javaType = mock(JavaType.class);
    when(javaType.getBindings()).thenReturn(typeBindings);
    BeanProperty property = mock(BeanProperty.class);
    when(property.getType()).thenReturn(javaType);

    // Act
    JsonDeserializer<?> actualCreateContextualResult = escapedJsonVariableDeserializer.createContextual(ctxt, property);

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
   * Method under test:
   * {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  void testCreateContextual7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer = new EscapedJsonVariableDeserializer<>(
        containerType);
    DefaultDeserializationContext.Impl ctxt = new DefaultDeserializationContext.Impl(
        new BeanDeserializerFactory(new DeserializerFactoryConfig()));

    ArrayList<JavaType> javaTypeList = new ArrayList<>();
    javaTypeList.add(new PlaceholderForType(1));
    TypeBindings typeBindings = mock(TypeBindings.class);
    when(typeBindings.getTypeParameters()).thenReturn(javaTypeList);
    JavaType javaType = mock(JavaType.class);
    when(javaType.getBindings()).thenReturn(typeBindings);
    BeanProperty property = mock(BeanProperty.class);
    when(property.getType()).thenReturn(javaType);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> escapedJsonVariableDeserializer.createContextual(ctxt, property));
    verify(property).getType();
    verify(javaType).getBindings();
    verify(typeBindings).getTypeParameters();
  }

  /**
   * Method under test:
   * {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  void testCreateContextual8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer = new EscapedJsonVariableDeserializer<>(
        containerType);
    DefaultDeserializationContext.Impl ctxt = new DefaultDeserializationContext.Impl(
        new BeanDeserializerFactory(new DeserializerFactoryConfig()));

    ArrayList<JavaType> javaTypeList = new ArrayList<>();
    javaTypeList.add(new PlaceholderForType(1));
    javaTypeList.add(new PlaceholderForType(1));
    TypeBindings typeBindings = mock(TypeBindings.class);
    when(typeBindings.getTypeParameters()).thenReturn(javaTypeList);
    JavaType javaType = mock(JavaType.class);
    when(javaType.getBindings()).thenReturn(typeBindings);
    BeanProperty property = mock(BeanProperty.class);
    when(property.getType()).thenReturn(javaType);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> escapedJsonVariableDeserializer.createContextual(ctxt, property));
    verify(property).getType();
    verify(javaType).getBindings();
    verify(typeBindings).getTypeParameters();
  }

  /**
   * Method under test:
   * {@link EscapedJsonVariableDeserializer#createContextual(DeserializationContext, BeanProperty)}
   */
  @Test
  void testCreateContextual9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer = new EscapedJsonVariableDeserializer<>(
        containerType);
    DefaultDeserializationContext.Impl ctxt = new DefaultDeserializationContext.Impl(
        new BeanDeserializerFactory(new DeserializerFactoryConfig()));

    ArrayList<JavaType> javaTypeList = new ArrayList<>();
    javaTypeList.add(new PlaceholderForType(1));
    javaTypeList.add(new PlaceholderForType(1));
    javaTypeList.add(new PlaceholderForType(1));
    TypeBindings typeBindings = mock(TypeBindings.class);
    when(typeBindings.getTypeParameters()).thenReturn(javaTypeList);
    JavaType javaType = mock(JavaType.class);
    when(javaType.getBindings()).thenReturn(typeBindings);
    BeanProperty property = mock(BeanProperty.class);
    when(property.getType()).thenReturn(javaType);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> escapedJsonVariableDeserializer.createContextual(ctxt, property));
    verify(property).getType();
    verify(javaType).getBindings();
    verify(typeBindings).getTypeParameters();
  }

  /**
   * Method under test:
   * {@link EscapedJsonVariableDeserializer#deserialize(JsonParser, DeserializationContext)}
   */
  @Test
  void testDeserialize() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Class<Object> containerType = Object.class;
    EscapedJsonVariableDeserializer<Object> escapedJsonVariableDeserializer = new EscapedJsonVariableDeserializer<>(
        containerType);
    ReaderBasedJsonParser p = mock(ReaderBasedJsonParser.class);
    doNothing().when(p).clearCurrentToken();
    JacksonFeatureSet<StreamReadCapability> fromBitmaskResult = JacksonFeatureSet.fromBitmask(1);
    when(p.getReadCapabilities()).thenReturn(fromBitmaskResult);
    when(p.currentToken()).thenReturn(JsonToken.END_OBJECT);
    when(p.getCodec()).thenReturn(CamundaExecutor.OBJECT_MAPPER);
    doNothing().when(p).setCodec(Mockito.<ObjectCodec>any());
    p.setCodec(CamundaExecutor.OBJECT_MAPPER);

    // Act
    Object actualDeserializeResult = escapedJsonVariableDeserializer.deserialize(p,
        new DefaultDeserializationContext.Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig())));

    // Assert
    verify(p).clearCurrentToken();
    verify(p, atLeast(1)).currentToken();
    verify(p).getCodec();
    verify(p).getReadCapabilities();
    verify(p).setCodec(isA(ObjectCodec.class));
    assertNull(actualDeserializeResult);
  }

  /**
   * Method under test:
   * {@link EscapedJsonVariableDeserializer#EscapedJsonVariableDeserializer(Class)}
   */
  @Test
  void testNewEscapedJsonVariableDeserializer() {
    // Arrange
    Class<Object> containerType = Object.class;

    // Act
    EscapedJsonVariableDeserializer<Object> actualEscapedJsonVariableDeserializer = new EscapedJsonVariableDeserializer<>(
        containerType);

    // Assert
    assertNull(actualEscapedJsonVariableDeserializer.getDelegatee());
    assertNull(actualEscapedJsonVariableDeserializer.getObjectIdReader());
    assertNull(actualEscapedJsonVariableDeserializer.getEmptyValue());
    assertNull(actualEscapedJsonVariableDeserializer.getNullValue());
    assertNull(actualEscapedJsonVariableDeserializer.getKnownPropertyNames());
    assertEquals(AccessPattern.CONSTANT, actualEscapedJsonVariableDeserializer.getNullAccessPattern());
    assertEquals(AccessPattern.DYNAMIC, actualEscapedJsonVariableDeserializer.getEmptyAccessPattern());
    assertFalse(actualEscapedJsonVariableDeserializer.isCachable());
  }
}
