package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4ConnectionAccepted;
import com.symphony.bdk.gen.api.model.V4ConnectionRequested;
import com.symphony.bdk.gen.api.model.V4InstantMessageCreated;
import com.symphony.bdk.gen.api.model.V4MessageSent;
import com.symphony.bdk.gen.api.model.V4RoomCreated;
import com.symphony.bdk.gen.api.model.V4RoomDeactivated;
import com.symphony.bdk.gen.api.model.V4RoomMemberDemotedFromOwner;
import com.symphony.bdk.gen.api.model.V4RoomMemberPromotedToOwner;
import com.symphony.bdk.gen.api.model.V4RoomReactivated;
import com.symphony.bdk.gen.api.model.V4RoomUpdated;
import com.symphony.bdk.gen.api.model.V4SharedPost;
import com.symphony.bdk.gen.api.model.V4SymphonyElementsAction;
import com.symphony.bdk.gen.api.model.V4UserJoinedRoom;
import com.symphony.bdk.gen.api.model.V4UserLeftRoom;
import com.symphony.bdk.gen.api.model.V4UserRequestedToJoinRoom;
import com.symphony.bdk.spring.events.RealTimeEvent;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for RealTimeEventProcessor.sourceType() method.
 */
class RealTimeEventProcessorClaude_sourceTypeTest {

  // Test event types for processors
  static class TestEvent1 {
  }

  static class TestEvent2 {
  }

  static class AnotherTestEvent {
  }

  // Abstract base class to properly implement the sourceType() method
  static abstract class AbstractTestProcessor<T> implements RealTimeEventProcessor<T> {
    @Override
    public void process(RealTimeEvent<T> event) {
      // No-op for testing
    }
  }

  // Concrete processor implementations for testing
  static class TestProcessor1 extends AbstractTestProcessor<TestEvent1> {
  }

  static class TestProcessor2 extends AbstractTestProcessor<TestEvent2> {
  }

  static class AnotherTestProcessor extends AbstractTestProcessor<AnotherTestEvent> {
  }

  // Concrete processor for V4MessageSent (real-world example)
  static class TestV4MessageSentProcessor extends AbstractTestProcessor<V4MessageSent> {
  }

  // Concrete processor for V4UserJoinedRoom (real-world example)
  static class TestV4UserJoinedRoomProcessor extends AbstractTestProcessor<V4UserJoinedRoom> {
  }

  // Concrete processor for V4RoomCreated (real-world example)
  static class TestV4RoomCreatedProcessor extends AbstractTestProcessor<V4RoomCreated> {
  }

  @Test
  void sourceType_withTestEvent1_shouldReturnTestEvent1Class() {
    // Given: A processor for TestEvent1
    RealTimeEventProcessor<TestEvent1> processor = new TestProcessor1();

    // When: sourceType is called
    Class<TestEvent1> result = processor.sourceType();

    // Then: Should return TestEvent1.class
    assertThat(result).isEqualTo(TestEvent1.class);
  }

  @Test
  void sourceType_withTestEvent2_shouldReturnTestEvent2Class() {
    // Given: A processor for TestEvent2
    RealTimeEventProcessor<TestEvent2> processor = new TestProcessor2();

    // When: sourceType is called
    Class<TestEvent2> result = processor.sourceType();

    // Then: Should return TestEvent2.class
    assertThat(result).isEqualTo(TestEvent2.class);
  }

  @Test
  void sourceType_withAnotherTestEvent_shouldReturnAnotherTestEventClass() {
    // Given: A processor for AnotherTestEvent
    RealTimeEventProcessor<AnotherTestEvent> processor = new AnotherTestProcessor();

    // When: sourceType is called
    Class<AnotherTestEvent> result = processor.sourceType();

    // Then: Should return AnotherTestEvent.class
    assertThat(result).isEqualTo(AnotherTestEvent.class);
  }

  @Test
  void sourceType_withV4MessageSent_shouldReturnV4MessageSentClass() {
    // Given: A processor for V4MessageSent
    RealTimeEventProcessor<V4MessageSent> processor = new TestV4MessageSentProcessor();

    // When: sourceType is called
    Class<V4MessageSent> result = processor.sourceType();

    // Then: Should return V4MessageSent.class
    assertThat(result).isEqualTo(V4MessageSent.class);
  }

  @Test
  void sourceType_withV4UserJoinedRoom_shouldReturnV4UserJoinedRoomClass() {
    // Given: A processor for V4UserJoinedRoom
    RealTimeEventProcessor<V4UserJoinedRoom> processor = new TestV4UserJoinedRoomProcessor();

    // When: sourceType is called
    Class<V4UserJoinedRoom> result = processor.sourceType();

    // Then: Should return V4UserJoinedRoom.class
    assertThat(result).isEqualTo(V4UserJoinedRoom.class);
  }

  @Test
  void sourceType_withV4RoomCreated_shouldReturnV4RoomCreatedClass() {
    // Given: A processor for V4RoomCreated
    RealTimeEventProcessor<V4RoomCreated> processor = new TestV4RoomCreatedProcessor();

    // When: sourceType is called
    Class<V4RoomCreated> result = processor.sourceType();

    // Then: Should return V4RoomCreated.class
    assertThat(result).isEqualTo(V4RoomCreated.class);
  }

  @Test
  void sourceType_calledMultipleTimes_shouldReturnSameClass() {
    // Given: A processor for TestEvent1
    RealTimeEventProcessor<TestEvent1> processor = new TestProcessor1();

    // When: sourceType is called multiple times
    Class<TestEvent1> result1 = processor.sourceType();
    Class<TestEvent1> result2 = processor.sourceType();
    Class<TestEvent1> result3 = processor.sourceType();

    // Then: All results should be the same class object
    assertThat(result1).isSameAs(result2);
    assertThat(result2).isSameAs(result3);
    assertThat(result1).isEqualTo(TestEvent1.class);
  }

  @Test
  void sourceType_differentProcessorInstances_shouldReturnSameClass() {
    // Given: Two different instances of TestProcessor1
    RealTimeEventProcessor<TestEvent1> processor1 = new TestProcessor1();
    RealTimeEventProcessor<TestEvent1> processor2 = new TestProcessor1();

    // When: sourceType is called on both instances
    Class<TestEvent1> result1 = processor1.sourceType();
    Class<TestEvent1> result2 = processor2.sourceType();

    // Then: Both should return the same class object
    assertThat(result1).isSameAs(result2);
    assertThat(result1).isEqualTo(TestEvent1.class);
  }

  @Test
  void sourceType_differentProcessorTypes_shouldReturnDifferentClasses() {
    // Given: Different processor types
    RealTimeEventProcessor<TestEvent1> processor1 = new TestProcessor1();
    RealTimeEventProcessor<TestEvent2> processor2 = new TestProcessor2();

    // When: sourceType is called on both
    Class<TestEvent1> result1 = processor1.sourceType();
    Class<TestEvent2> result2 = processor2.sourceType();

    // Then: Should return different classes
    assertThat(result1).isNotEqualTo(result2);
    assertThat(result1).isEqualTo(TestEvent1.class);
    assertThat(result2).isEqualTo(TestEvent2.class);
  }

  @Test
  void sourceType_withRealWorldEvent_shouldReturnCorrectSimpleName() {
    // Given: A processor for V4MessageSent
    RealTimeEventProcessor<V4MessageSent> processor = new TestV4MessageSentProcessor();

    // When: sourceType is called and simple name is extracted
    String simpleName = processor.sourceType().getSimpleName();

    // Then: Simple name should match the event type
    assertThat(simpleName).isEqualTo("V4MessageSent");
  }

  @Test
  void sourceType_usedInMapKey_shouldProduceCorrectKey() {
    // Given: A processor for TestEvent1
    RealTimeEventProcessor<TestEvent1> processor = new TestProcessor1();

    // When: sourceType is used to create a map key (as in CamundaEngine)
    String key = processor.sourceType().getSimpleName();

    // Then: Key should be the simple class name
    assertThat(key).isEqualTo("TestEvent1");
  }

  @Test
  void sourceType_withMultipleRealEvents_shouldReturnCorrectClasses() {
    // Given: Processors for various real event types
    RealTimeEventProcessor<V4ConnectionAccepted> p1 = new AbstractTestProcessor<V4ConnectionAccepted>() {};
    RealTimeEventProcessor<V4ConnectionRequested> p2 = new AbstractTestProcessor<V4ConnectionRequested>() {};
    RealTimeEventProcessor<V4InstantMessageCreated> p3 = new AbstractTestProcessor<V4InstantMessageCreated>() {};
    RealTimeEventProcessor<V4RoomDeactivated> p4 = new AbstractTestProcessor<V4RoomDeactivated>() {};
    RealTimeEventProcessor<V4RoomReactivated> p5 = new AbstractTestProcessor<V4RoomReactivated>() {};

    // When: sourceType is called on each
    Class<V4ConnectionAccepted> result1 = p1.sourceType();
    Class<V4ConnectionRequested> result2 = p2.sourceType();
    Class<V4InstantMessageCreated> result3 = p3.sourceType();
    Class<V4RoomDeactivated> result4 = p4.sourceType();
    Class<V4RoomReactivated> result5 = p5.sourceType();

    // Then: Each should return the correct class
    assertThat(result1).isEqualTo(V4ConnectionAccepted.class);
    assertThat(result2).isEqualTo(V4ConnectionRequested.class);
    assertThat(result3).isEqualTo(V4InstantMessageCreated.class);
    assertThat(result4).isEqualTo(V4RoomDeactivated.class);
    assertThat(result5).isEqualTo(V4RoomReactivated.class);
  }

  @Test
  void sourceType_withAllSymphonyEventTypes_shouldReturnCorrectClasses() {
    // Given: Processors for all Symphony event types
    RealTimeEventProcessor<V4RoomUpdated> p1 = new AbstractTestProcessor<V4RoomUpdated>() {};
    RealTimeEventProcessor<V4SharedPost> p2 = new AbstractTestProcessor<V4SharedPost>() {};
    RealTimeEventProcessor<V4SymphonyElementsAction> p3 = new AbstractTestProcessor<V4SymphonyElementsAction>() {};
    RealTimeEventProcessor<V4UserLeftRoom> p4 = new AbstractTestProcessor<V4UserLeftRoom>() {};
    RealTimeEventProcessor<V4UserRequestedToJoinRoom> p5 = new AbstractTestProcessor<V4UserRequestedToJoinRoom>() {};
    RealTimeEventProcessor<V4RoomMemberPromotedToOwner> p6 = new AbstractTestProcessor<V4RoomMemberPromotedToOwner>() {};
    RealTimeEventProcessor<V4RoomMemberDemotedFromOwner> p7 = new AbstractTestProcessor<V4RoomMemberDemotedFromOwner>() {};

    // When: sourceType is called on each
    Class<V4RoomUpdated> result1 = p1.sourceType();
    Class<V4SharedPost> result2 = p2.sourceType();
    Class<V4SymphonyElementsAction> result3 = p3.sourceType();
    Class<V4UserLeftRoom> result4 = p4.sourceType();
    Class<V4UserRequestedToJoinRoom> result5 = p5.sourceType();
    Class<V4RoomMemberPromotedToOwner> result6 = p6.sourceType();
    Class<V4RoomMemberDemotedFromOwner> result7 = p7.sourceType();

    // Then: Each should return the correct class
    assertThat(result1).isEqualTo(V4RoomUpdated.class);
    assertThat(result2).isEqualTo(V4SharedPost.class);
    assertThat(result3).isEqualTo(V4SymphonyElementsAction.class);
    assertThat(result4).isEqualTo(V4UserLeftRoom.class);
    assertThat(result5).isEqualTo(V4UserRequestedToJoinRoom.class);
    assertThat(result6).isEqualTo(V4RoomMemberPromotedToOwner.class);
    assertThat(result7).isEqualTo(V4RoomMemberDemotedFromOwner.class);
  }

  @Test
  void sourceType_returnedClass_shouldBeAssignableToGenericType() {
    // Given: A processor for TestEvent1
    RealTimeEventProcessor<TestEvent1> processor = new TestProcessor1();

    // When: sourceType is called
    Class<TestEvent1> result = processor.sourceType();

    // Then: The returned class should be assignable from instances of TestEvent1
    assertThat(result.isAssignableFrom(TestEvent1.class)).isTrue();
  }

  @Test
  void sourceType_returnedClass_shouldHaveCorrectPackage() {
    // Given: A processor for V4MessageSent
    RealTimeEventProcessor<V4MessageSent> processor = new TestV4MessageSentProcessor();

    // When: sourceType is called
    Class<V4MessageSent> result = processor.sourceType();

    // Then: The package should match the event type's package
    assertThat(result.getPackage().getName()).isEqualTo("com.symphony.bdk.gen.api.model");
  }

  @Test
  void sourceType_returnedClass_canBeUsedToCreateInstance() throws Exception {
    // Given: A processor for TestEvent1
    RealTimeEventProcessor<TestEvent1> processor = new TestProcessor1();

    // When: sourceType is called and used to create an instance
    Class<TestEvent1> clazz = processor.sourceType();
    TestEvent1 instance = clazz.getDeclaredConstructor().newInstance();

    // Then: Instance should be created successfully
    assertThat(instance).isNotNull();
    assertThat(instance).isInstanceOf(TestEvent1.class);
  }
}
