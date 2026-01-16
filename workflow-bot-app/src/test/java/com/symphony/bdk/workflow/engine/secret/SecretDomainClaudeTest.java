package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SecretDomainClaudeTest {

  @Test
  void testConstructor_withRefAndSecret() {
    // Test that constructor properly initializes ref and secret fields
    String ref = "testRef";
    String secret = "encryptedSecret";

    SecretDomain secretDomain = new SecretDomain(ref, secret);

    assertThat(secretDomain).isNotNull();
    assertThat(secretDomain.getRef()).isEqualTo(ref);
    assertThat(secretDomain.getSecret()).isEqualTo(secret);
    assertThat(secretDomain.getId()).isNull();
    assertThat(secretDomain.getCreatedAt()).isNull();
  }

  @Test
  void testConstructor_withNullRef() {
    // Test that constructor handles null ref
    String secret = "encryptedSecret";

    SecretDomain secretDomain = new SecretDomain(null, secret);

    assertThat(secretDomain).isNotNull();
    assertThat(secretDomain.getRef()).isNull();
    assertThat(secretDomain.getSecret()).isEqualTo(secret);
  }

  @Test
  void testConstructor_withNullSecret() {
    // Test that constructor handles null secret
    String ref = "testRef";

    SecretDomain secretDomain = new SecretDomain(ref, null);

    assertThat(secretDomain).isNotNull();
    assertThat(secretDomain.getRef()).isEqualTo(ref);
    assertThat(secretDomain.getSecret()).isNull();
  }

  @Test
  void testConstructor_withBothNull() {
    // Test that constructor handles both null values
    SecretDomain secretDomain = new SecretDomain(null, null);

    assertThat(secretDomain).isNotNull();
    assertThat(secretDomain.getRef()).isNull();
    assertThat(secretDomain.getSecret()).isNull();
  }

  @Test
  void testConstructor_withEmptyStrings() {
    // Test that constructor handles empty strings
    String ref = "";
    String secret = "";

    SecretDomain secretDomain = new SecretDomain(ref, secret);

    assertThat(secretDomain).isNotNull();
    assertThat(secretDomain.getRef()).isEqualTo(ref);
    assertThat(secretDomain.getSecret()).isEqualTo(secret);
  }

  @Test
  void testConstructor_multipleInstances() {
    // Test that multiple instances can be created independently
    String ref1 = "ref1";
    String secret1 = "secret1";
    String ref2 = "ref2";
    String secret2 = "secret2";

    SecretDomain domain1 = new SecretDomain(ref1, secret1);
    SecretDomain domain2 = new SecretDomain(ref2, secret2);

    assertThat(domain1).isNotNull();
    assertThat(domain2).isNotNull();
    assertThat(domain1).isNotSameAs(domain2);
    assertThat(domain1.getRef()).isEqualTo(ref1);
    assertThat(domain2.getRef()).isEqualTo(ref2);
    assertThat(domain1.getSecret()).isEqualTo(secret1);
    assertThat(domain2.getSecret()).isEqualTo(secret2);
  }

  @Test
  void testOnCreate_setsCreatedAtTimestamp() {
    // Test that onCreate sets createdAt to current timestamp
    SecretDomain secretDomain = new SecretDomain("testRef", "testSecret");

    assertThat(secretDomain.getCreatedAt()).isNull();

    long beforeCall = System.currentTimeMillis();
    secretDomain.onCreate();
    long afterCall = System.currentTimeMillis();

    assertThat(secretDomain.getCreatedAt()).isNotNull();
    assertThat(secretDomain.getCreatedAt()).isGreaterThanOrEqualTo(beforeCall);
    assertThat(secretDomain.getCreatedAt()).isLessThanOrEqualTo(afterCall);
  }

  @Test
  void testOnCreate_multipleCalls() {
    // Test that onCreate can be called multiple times and updates timestamp
    SecretDomain secretDomain = new SecretDomain("testRef", "testSecret");

    secretDomain.onCreate();
    Long firstTimestamp = secretDomain.getCreatedAt();

    try {
      // Small delay to ensure different timestamp
      Thread.sleep(10);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    secretDomain.onCreate();
    Long secondTimestamp = secretDomain.getCreatedAt();

    assertThat(firstTimestamp).isNotNull();
    assertThat(secondTimestamp).isNotNull();
    assertThat(secondTimestamp).isGreaterThanOrEqualTo(firstTimestamp);
  }

  @Test
  void testOnCreate_doesNotAffectOtherFields() {
    // Test that onCreate only sets createdAt and doesn't affect other fields
    String ref = "testRef";
    String secret = "testSecret";
    SecretDomain secretDomain = new SecretDomain(ref, secret);

    secretDomain.onCreate();

    assertThat(secretDomain.getRef()).isEqualTo(ref);
    assertThat(secretDomain.getSecret()).isEqualTo(secret);
    assertThat(secretDomain.getId()).isNull();
    assertThat(secretDomain.getCreatedAt()).isNotNull();
  }

  @Test
  void testOnCreate_onNoArgsConstructorInstance() {
    // Test that onCreate works on instance created with no-args constructor
    SecretDomain secretDomain = new SecretDomain();

    assertThat(secretDomain.getCreatedAt()).isNull();

    long beforeCall = System.currentTimeMillis();
    secretDomain.onCreate();
    long afterCall = System.currentTimeMillis();

    assertThat(secretDomain.getCreatedAt()).isNotNull();
    assertThat(secretDomain.getCreatedAt()).isGreaterThanOrEqualTo(beforeCall);
    assertThat(secretDomain.getCreatedAt()).isLessThanOrEqualTo(afterCall);
  }

  @Test
  void testConstructor_withLongRefAndSecret() {
    // Test that constructor handles longer strings
    String ref = "123456789012345"; // Max length is 15 according to @Column annotation
    String secret = "thisIsAVeryLongEncryptedSecretStringThatCouldContainBase64OrOtherEncodedData";

    SecretDomain secretDomain = new SecretDomain(ref, secret);

    assertThat(secretDomain).isNotNull();
    assertThat(secretDomain.getRef()).isEqualTo(ref);
    assertThat(secretDomain.getSecret()).isEqualTo(secret);
  }

  @Test
  void testNoArgsConstructor_initializesWithNulls() {
    // Test that no-args constructor initializes all fields to null
    SecretDomain secretDomain = new SecretDomain();

    assertThat(secretDomain).isNotNull();
    assertThat(secretDomain.getId()).isNull();
    assertThat(secretDomain.getRef()).isNull();
    assertThat(secretDomain.getSecret()).isNull();
    assertThat(secretDomain.getCreatedAt()).isNull();
  }

  @Test
  void testSettersAndGetters_ref() {
    // Test that Lombok's @Data generates working setters and getters for ref
    SecretDomain secretDomain = new SecretDomain();
    String ref = "newRef";

    secretDomain.setRef(ref);

    assertThat(secretDomain.getRef()).isEqualTo(ref);
  }

  @Test
  void testSettersAndGetters_secret() {
    // Test that Lombok's @Data generates working setters and getters for secret
    SecretDomain secretDomain = new SecretDomain();
    String secret = "newSecret";

    secretDomain.setSecret(secret);

    assertThat(secretDomain.getSecret()).isEqualTo(secret);
  }

  @Test
  void testSettersAndGetters_id() {
    // Test that Lombok's @Data generates working setters and getters for id
    SecretDomain secretDomain = new SecretDomain();
    String id = "test-uuid";

    secretDomain.setId(id);

    assertThat(secretDomain.getId()).isEqualTo(id);
  }

  @Test
  void testSettersAndGetters_createdAt() {
    // Test that Lombok's @Data generates working setters and getters for createdAt
    SecretDomain secretDomain = new SecretDomain();
    Long timestamp = 1234567890L;

    secretDomain.setCreatedAt(timestamp);

    assertThat(secretDomain.getCreatedAt()).isEqualTo(timestamp);
  }

  @Test
  void testEquals_sameValues() {
    // Test that Lombok's @Data generates working equals for same values
    SecretDomain domain1 = new SecretDomain("ref", "secret");
    SecretDomain domain2 = new SecretDomain("ref", "secret");
    domain1.setId("id1");
    domain2.setId("id1");
    domain1.setCreatedAt(12345L);
    domain2.setCreatedAt(12345L);

    assertThat(domain1).isEqualTo(domain2);
  }

  @Test
  void testEquals_differentValues() {
    // Test that Lombok's @Data generates working equals for different values
    SecretDomain domain1 = new SecretDomain("ref1", "secret1");
    SecretDomain domain2 = new SecretDomain("ref2", "secret2");

    assertThat(domain1).isNotEqualTo(domain2);
  }

  @Test
  void testHashCode_sameValues() {
    // Test that Lombok's @Data generates working hashCode for same values
    SecretDomain domain1 = new SecretDomain("ref", "secret");
    SecretDomain domain2 = new SecretDomain("ref", "secret");
    domain1.setId("id1");
    domain2.setId("id1");
    domain1.setCreatedAt(12345L);
    domain2.setCreatedAt(12345L);

    assertThat(domain1.hashCode()).isEqualTo(domain2.hashCode());
  }

  @Test
  void testToString_containsFields() {
    // Test that Lombok's @Data generates working toString
    SecretDomain secretDomain = new SecretDomain("testRef", "testSecret");
    secretDomain.setId("test-id");
    secretDomain.setCreatedAt(12345L);

    String toString = secretDomain.toString();

    assertThat(toString).contains("testRef");
    assertThat(toString).contains("testSecret");
    assertThat(toString).contains("test-id");
    assertThat(toString).contains("12345");
  }
}
