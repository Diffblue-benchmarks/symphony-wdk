package com.symphony.bdk.workflow.swadl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.symphony.bdk.workflow.engine.camunda.CamundaExecutor;
import com.symphony.bdk.workflow.swadl.v1.Activity;
import java.io.IOException;
import java.util.NoSuchElementException;
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
   * Method under test:
   * {@link ActivityDeserializer#deserialize(JsonParser, DeserializationContext)}
   */
  @Test
  void testDeserialize() throws IOException {
    // Arrange
    new NoSuchElementException("foo");
    JsonParser p = mock(JsonParser.class);
    when(p.getReadCapabilities()).thenThrow(new NoSuchElementException("foo"));
    when(p.currentToken()).thenReturn(JsonToken.NOT_AVAILABLE);
    when(p.getCodec()).thenReturn(CamundaExecutor.OBJECT_MAPPER);

    // Act and Assert
    assertThrows(NoSuchElementException.class, () -> activityDeserializer.deserialize(p,
        new DefaultDeserializationContext.Impl(new BeanDeserializerFactory(new DeserializerFactoryConfig()))));
    verify(p, atLeast(1)).currentToken();
    verify(p).getCodec();
    verify(p).getReadCapabilities();
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link ActivityDeserializer}
   */
  @Test
  void testNewActivityDeserializer() {
    // Arrange and Act
    ActivityDeserializer actualActivityDeserializer = new ActivityDeserializer();

    // Assert
    assertNull(actualActivityDeserializer.getValueType());
    Class<Activity> expectedValueClass = Activity.class;
    assertEquals(expectedValueClass, actualActivityDeserializer.getValueClass());
  }
}
