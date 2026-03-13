package com.symphony.bdk.workflow.swadl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.symphony.bdk.workflow.DoSomething;
import com.symphony.bdk.workflow.swadl.v1.Activity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActivityDeserializerTest {

  private ObjectMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(Activity.class, new ActivityDeserializer());
    mapper.registerModule(module);
  }

  @Test
  void constructor_shouldCreateInstanceWithActivityClass() {
    // Arrange & Act
    ActivityDeserializer deserializer = new ActivityDeserializer();

    // Assert
    assertThat(deserializer).isNotNull();
  }

  @Test
  void deserialize_shouldDeserializeValidActivity() throws JsonProcessingException {
    // Arrange
    String json = "{\"do-something\": {\"id\": \"test-id\", \"myParameter\": \"test-value\"}}";

    // Act
    Activity activity = mapper.readValue(json, Activity.class);

    // Assert
    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getImplementation()).isInstanceOf(DoSomething.class);
    DoSomething doSomething = (DoSomething) activity.getImplementation();
    assertThat(doSomething.getId()).isEqualTo("test-id");
    assertThat(doSomething.getMyParameter()).isEqualTo("test-value");
  }

  @Test
  void deserialize_shouldHandleActivityWithKebabCaseName() throws JsonProcessingException {
    // Arrange
    String json = "{\"do-something\": {\"id\": \"act1\"}}";

    // Act
    Activity activity = mapper.readValue(json, Activity.class);

    // Assert
    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isInstanceOf(DoSomething.class);
  }

  @Test
  void deserialize_shouldThrowExceptionForUnknownActivity() {
    // Arrange
    String json = "{\"unknown-activity\": {\"id\": \"test\"}}";

    // Act & Assert
    assertThatThrownBy(() -> mapper.readValue(json, Activity.class))
        .isInstanceOf(JsonMappingException.class)
        .hasMessageContaining("Could not find an activity named: unknown-activity");
  }

  @Test
  void deserialize_shouldThrowExceptionForEmptyActivity() {
    // Arrange
    String json = "{}";

    // Act & Assert
    assertThatThrownBy(() -> mapper.readValue(json, Activity.class))
        .isInstanceOf(JsonMappingException.class)
        .hasMessageContaining("No activity defined");
  }

  @Test
  void deserialize_shouldHandleActivityWithMinimalFields() throws JsonProcessingException {
    // Arrange
    String json = "{\"do-something\": {}}";

    // Act
    Activity activity = mapper.readValue(json, Activity.class);

    // Assert
    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isInstanceOf(DoSomething.class);
    DoSomething doSomething = (DoSomething) activity.getImplementation();
    assertThat(doSomething.getId()).isNull();
    assertThat(doSomething.getMyParameter()).isNull();
  }

  @Test
  void deserialize_shouldHandleActivityWithMultipleHyphens() throws JsonProcessingException {
    // Arrange
    String json = "{\"do-something\": {\"id\": \"multi-hyphen-test\"}}";

    // Act
    Activity activity = mapper.readValue(json, Activity.class);

    // Assert
    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isInstanceOf(DoSomething.class);
  }

  @Test
  void classNameMatches_shouldMatchKebabCaseToClassName() throws Exception {
    // Arrange
    ActivityDeserializer deserializer = new ActivityDeserializer();
    java.lang.reflect.Method method = ActivityDeserializer.class
        .getDeclaredMethod("classNameMatches", String.class, Class.class);
    method.setAccessible(true);

    // Act
    boolean matches = (boolean) method.invoke(deserializer, "do-something", DoSomething.class);

    // Assert
    assertThat(matches).isTrue();
  }

  @Test
  void classNameMatches_shouldNotMatchDifferentNames() throws Exception {
    // Arrange
    ActivityDeserializer deserializer = new ActivityDeserializer();
    java.lang.reflect.Method method = ActivityDeserializer.class
        .getDeclaredMethod("classNameMatches", String.class, Class.class);
    method.setAccessible(true);

    // Act
    boolean matches = (boolean) method.invoke(deserializer, "other-activity", DoSomething.class);

    // Assert
    assertThat(matches).isFalse();
  }

  @Test
  void classNameMatches_shouldHandleSingleWordWithoutHyphens() throws Exception {
    // Arrange
    ActivityDeserializer deserializer = new ActivityDeserializer();
    java.lang.reflect.Method method = ActivityDeserializer.class
        .getDeclaredMethod("classNameMatches", String.class, Class.class);
    method.setAccessible(true);

    // Act
    boolean matches = (boolean) method.invoke(deserializer, "dosomething", DoSomething.class);

    // Assert
    assertThat(matches).isTrue();
  }

  @Test
  void deserialize_shouldThrowExceptionForMultipleMatchingActivities() {
    // Arrange
    String json = "{\"duplicate-custom-activity\": {\"id\": \"test\"}}";

    // Act & Assert
    assertThatThrownBy(() -> mapper.readValue(json, Activity.class))
        .isInstanceOf(JsonMappingException.class)
        .hasMessageContaining("Found multiple activity types")
        .hasMessageContaining("duplicate-custom-activity");
  }
}
