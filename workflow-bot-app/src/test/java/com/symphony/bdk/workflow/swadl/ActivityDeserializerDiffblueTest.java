package com.symphony.bdk.workflow.swadl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.Impl;
import com.symphony.bdk.workflow.engine.camunda.CamundaExecutor;
import com.symphony.bdk.workflow.swadl.v1.Activity;
import java.io.IOException;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivityDeserializer.class})
@ExtendWith(SpringExtension.class)
class ActivityDeserializerDiffblueTest {
  @Autowired
  private ActivityDeserializer activityDeserializer;

  /**
   * Test new {@link ActivityDeserializer} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link ActivityDeserializer}
   */
  @Test
  @DisplayName("Test new ActivityDeserializer (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ActivityDeserializer.<init>()"})
  void testNewActivityDeserializer() {
    // Arrange and Act
    ActivityDeserializer actualActivityDeserializer = new ActivityDeserializer();

    // Assert
    assertNull(actualActivityDeserializer.getValueType());
    Class<Activity> expectedValueClass = Activity.class;
    assertEquals(expectedValueClass, actualActivityDeserializer.getValueClass());
  }

  /**
   * Test {@link ActivityDeserializer#deserialize(JsonParser, DeserializationContext)} with {@code p}, {@code ctxt}.
   * <ul>
   *   <li>Then throw {@link NoSuchElementException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityDeserializer#deserialize(JsonParser, DeserializationContext)}
   */
  @Test
  @DisplayName("Test deserialize(JsonParser, DeserializationContext) with 'p', 'ctxt'; then throw NoSuchElementException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Activity ActivityDeserializer.deserialize(JsonParser, DeserializationContext)"})
  void testDeserializeWithPCtxt_thenThrowNoSuchElementException() throws IOException {
    // Arrange
    new NoSuchElementException("foo");
    JsonParser p = mock(JsonParser.class);
    when(p.getReadCapabilities()).thenThrow(new NoSuchElementException("foo"));
    when(p.currentToken()).thenReturn(JsonToken.NOT_AVAILABLE);
    when(p.getCodec()).thenReturn(CamundaExecutor.OBJECT_MAPPER);

    // Act and Assert
    assertThrows(NoSuchElementException.class, () -> activityDeserializer.deserialize(p,
        new Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig()))));
    verify(p, atLeast(1)).currentToken();
    verify(p).getCodec();
    verify(p).getReadCapabilities();
  }
}
