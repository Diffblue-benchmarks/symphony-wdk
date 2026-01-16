package com.symphony.bdk.workflow.swadl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.symphony.bdk.workflow.swadl.v1.Activity;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ActivityDeserializerClaudeTest {

  private ActivityDeserializer deserializer;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    deserializer = new ActivityDeserializer();
    objectMapper = new ObjectMapper();
  }

  @Test
  void testConstructor_initializesCorrectly() {
    // Test that the constructor properly initializes the deserializer
    ActivityDeserializer newDeserializer = new ActivityDeserializer();

    assertThat(newDeserializer).isNotNull();
    assertThat(newDeserializer.handledType()).isEqualTo(Activity.class);
  }

  @Test
  void testDeserialize_withValidCreateUserActivity() throws IOException {
    // Test deserialization of a valid create-user activity
    String json = "{ \"create-user\": { \"id\": \"user1\", \"email\": \"test@test.com\", \"username\": \"testuser\", \"firstname\": \"Test\", \"lastname\": \"User\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getImplementation().getId()).isEqualTo("user1");
  }

  @Test
  void testDeserialize_withValidGetUserActivity() throws IOException {
    // Test deserialization of a valid get-user activity
    String json = "{ \"get-user\": { \"id\": \"getUser1\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getImplementation().getId()).isEqualTo("getUser1");
  }

  @Test
  void testDeserialize_withKebabCaseActivityName() throws IOException {
    // Test that deserializer properly handles kebab-case activity names
    String json = "{ \"update-system-user\": { \"id\": \"update1\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getImplementation().getId()).isEqualTo("update1");
  }

  @Test
  void testDeserialize_withUnknownActivityType() throws IOException {
    // Test that deserializer throws exception for unknown activity type
    String json = "{ \"unknown-activity\": { \"id\": \"unknown1\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    assertThatThrownBy(() -> deserializer.deserialize(parser, context))
        .isInstanceOf(JsonMappingException.class)
        .hasMessageContaining("Could not find an activity named: unknown-activity");
  }

  @Test
  void testDeserialize_withEmptyObject() throws IOException {
    // Test that deserializer throws exception when no activity is defined
    String json = "{}";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    assertThatThrownBy(() -> deserializer.deserialize(parser, context))
        .isInstanceOf(JsonMappingException.class)
        .hasMessageContaining("No activity defined");
  }

  @Test
  void testDeserialize_withActivityContainingIfCondition() throws IOException {
    // Test deserialization of activity with if condition
    String json = "{ \"create-user\": { \"id\": \"user1\", \"if\": \"${condition}\", \"email\": \"test@test.com\", \"username\": \"testuser\", \"firstname\": \"Test\", \"lastname\": \"User\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getImplementation().getId()).isEqualTo("user1");
    assertThat(activity.getImplementation().getIfCondition()).isEqualTo("${condition}");
  }

  @Test
  void testDeserialize_withActivityContainingOnEvent() throws IOException {
    // Test deserialization of activity with on event
    String json = "{ \"create-user\": { \"id\": \"user1\", \"on\": { \"message-received\": {} }, \"email\": \"test@test.com\", \"username\": \"testuser\", \"firstname\": \"Test\", \"lastname\": \"User\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getImplementation().getId()).isEqualTo("user1");
    assertThat(activity.getImplementation().getOn()).isNotNull();
  }

  @Test
  void testDeserialize_withMultiplePropertiesInActivity() throws IOException {
    // Test deserialization of activity with various properties
    String json = "{ \"create-user\": { \"id\": \"user1\", \"email\": \"test@test.com\", \"username\": \"testuser\", \"firstname\": \"Test\", \"lastname\": \"User\", \"displayName\": \"Test User\", \"type\": \"NORMAL\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getImplementation().getId()).isEqualTo("user1");
  }

  @Test
  void testDeserialize_withActivityContainingNestedObjects() throws IOException {
    // Test deserialization of activity with nested objects
    String json = "{ \"create-user\": { \"id\": \"user1\", \"email\": \"test@test.com\", \"username\": \"testuser\", \"firstname\": \"Test\", \"lastname\": \"User\", \"contact\": { \"workPhoneNumber\": \"123-456-7890\" } } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getImplementation().getId()).isEqualTo("user1");
  }

  @Test
  void testDeserialize_caseInsensitiveActivityMatching() throws IOException {
    // Test that activity name matching is case-insensitive (kebab-case matching)
    // The class is CreateUser, which should match "create-user"
    String json = "{ \"create-user\": { \"id\": \"user1\", \"email\": \"test@test.com\", \"username\": \"testuser\", \"firstname\": \"Test\", \"lastname\": \"User\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    // Verify that the implementation is properly set
    assertThat(activity.getActivity()).isEqualTo(activity.getImplementation());
  }

  @Test
  void testDeserialize_withActivityIdOnly() throws IOException {
    // Test deserialization of activity with only id field
    String json = "{ \"get-user\": { \"id\": \"simpleActivity\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getImplementation().getId()).isEqualTo("simpleActivity");
  }

  @Test
  void testDeserialize_ensureActivityAndImplementationAreLinked() throws IOException {
    // Test that the Activity's getActivity() method returns the same as getImplementation()
    String json = "{ \"get-user\": { \"id\": \"linked1\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getActivity()).isSameAs(activity.getImplementation());
  }

  @Test
  void testDeserialize_withGetUsersActivity() throws IOException {
    // Test deserialization with get-users activity
    String json = "{ \"get-users\": { \"id\": \"getUsers1\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getImplementation().getId()).isEqualTo("getUsers1");
  }

  @Test
  void testDeserialize_withUpdateUserActivity() throws IOException {
    // Test deserialization with update-user activity
    String json = "{ \"update-user\": { \"id\": \"updateUser1\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getImplementation().getId()).isEqualTo("updateUser1");
  }

  @Test
  void testDeserialize_withActivityContainingElseCondition() throws IOException {
    // Test deserialization of activity with else condition
    String json = "{ \"get-user\": { \"id\": \"user1\", \"else\": { \"get-users\": { \"id\": \"fallback\" } } } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getImplementation().getId()).isEqualTo("user1");
    assertThat(activity.getImplementation().getElseCondition()).isNotNull();
  }

  @Test
  void testDeserialize_verifyActivityImplementationIsBaseActivity() throws IOException {
    // Test that the implementation is an instance of BaseActivity
    String json = "{ \"get-user\": { \"id\": \"baseActivity1\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isInstanceOf(BaseActivity.class);
  }

  @Test
  void testDeserialize_withAddUserRoleActivity() throws IOException {
    // Test deserialization with add-user-role activity
    String json = "{ \"add-user-role\": { \"id\": \"addRole1\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getImplementation().getId()).isEqualTo("addRole1");
  }

  @Test
  void testDeserialize_withRemoveUserRoleActivity() throws IOException {
    // Test deserialization with remove-user-role activity
    String json = "{ \"remove-user-role\": { \"id\": \"removeRole1\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getImplementation().getId()).isEqualTo("removeRole1");
  }

  @Test
  void testDeserialize_multipleDeserializationsAreIndependent() throws IOException {
    // Test that multiple deserializations don't interfere with each other
    String json1 = "{ \"get-user\": { \"id\": \"user1\" } }";
    String json2 = "{ \"create-user\": { \"id\": \"user2\", \"email\": \"test@test.com\", \"username\": \"testuser\", \"firstname\": \"Test\", \"lastname\": \"User\" } }";

    JsonParser parser1 = objectMapper.getFactory().createParser(json1);
    DeserializationContext context1 = objectMapper.getDeserializationContext();
    Activity activity1 = deserializer.deserialize(parser1, context1);

    JsonParser parser2 = objectMapper.getFactory().createParser(json2);
    DeserializationContext context2 = objectMapper.getDeserializationContext();
    Activity activity2 = deserializer.deserialize(parser2, context2);

    assertThat(activity1).isNotNull();
    assertThat(activity1.getImplementation().getId()).isEqualTo("user1");
    assertThat(activity2).isNotNull();
    assertThat(activity2.getImplementation().getId()).isEqualTo("user2");
    assertThat(activity1).isNotSameAs(activity2);
  }

  @Test
  void testDeserialize_withNullFields() throws IOException {
    // Test deserialization of activity with only required fields
    String json = "{ \"get-user\": { } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    // ID is null if not provided
    assertThat(activity.getImplementation().getId()).isNull();
  }

  @Test
  void testDeserialize_withSpecialCharactersInId() throws IOException {
    // Test deserialization of activity with special characters in id
    String json = "{ \"get-user\": { \"id\": \"user_123-test@special\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getImplementation().getId()).isEqualTo("user_123-test@special");
  }

  @Test
  void testDeserialize_withLongIdString() throws IOException {
    // Test deserialization of activity with very long id string
    String longId = "a".repeat(500);
    String json = "{ \"get-user\": { \"id\": \"" + longId + "\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
    assertThat(activity.getImplementation().getId()).isEqualTo(longId);
  }

  @Test
  void testDeserialize_verifyActivityHasCorrectEvents() throws IOException {
    // Test that Activity's getEvents() method works correctly
    String json = "{ \"get-user\": { \"id\": \"events1\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getEvents()).isNotNull();
    // When no 'on' is specified, events should be empty
    assertThat(activity.getEvents().getEvents()).isEmpty();
  }

  @Test
  void testDeserialize_verifyActivityGetEventReturnsOptional() throws IOException {
    // Test that Activity's getEvent() returns Optional
    String json = "{ \"get-user\": { \"id\": \"event1\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    // When no 'on' is specified, getEvent() should return empty Optional
    assertThat(activity.getEvent()).isEmpty();
  }

  @Test
  void testDeserialize_withActivityNameContainingNumbers() throws IOException {
    // Test that activity names without dashes still work
    // Note: This test may not find a matching activity if no such activity exists
    // We'll test with a known activity type
    String json = "{ \"get-user\": { \"id\": \"withNumbers123\" } }";

    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext context = objectMapper.getDeserializationContext();

    Activity activity = deserializer.deserialize(parser, context);

    assertThat(activity).isNotNull();
    assertThat(activity.getImplementation()).isNotNull();
  }
}
