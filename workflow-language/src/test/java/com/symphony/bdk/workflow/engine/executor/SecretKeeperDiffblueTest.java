package com.symphony.bdk.workflow.engine.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper.SecretMetadata;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SecretKeeperDiffblueTest {
  /**
   * Test SecretMetadata {@link SecretMetadata#equals(Object)}, and {@link SecretMetadata#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SecretMetadata#equals(Object)}
   *   <li>{@link SecretMetadata#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test SecretMetadata equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SecretMetadata.equals(Object)", "int SecretMetadata.hashCode()"})
  void testSecretMetadataEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    SecretMetadata secretMetadata = new SecretMetadata();
    SecretMetadata secretMetadata2 = new SecretMetadata();

    // Act and Assert
    assertEquals(secretMetadata, secretMetadata2);
    int expectedHashCodeResult = secretMetadata.hashCode();
    assertEquals(expectedHashCodeResult, secretMetadata2.hashCode());
  }

  /**
   * Test SecretMetadata {@link SecretMetadata#equals(Object)}, and {@link SecretMetadata#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SecretMetadata#equals(Object)}
   *   <li>{@link SecretMetadata#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test SecretMetadata equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SecretMetadata.equals(Object)", "int SecretMetadata.hashCode()"})
  void testSecretMetadataEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    SecretMetadata secretMetadata = new SecretMetadata("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY",
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    SecretMetadata secretMetadata2 = new SecretMetadata("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY",
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertEquals(secretMetadata, secretMetadata2);
    int expectedHashCodeResult = secretMetadata.hashCode();
    assertEquals(expectedHashCodeResult, secretMetadata2.hashCode());
  }

  /**
   * Test SecretMetadata {@link SecretMetadata#equals(Object)}, and {@link SecretMetadata#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SecretMetadata#equals(Object)}
   *   <li>{@link SecretMetadata#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test SecretMetadata equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SecretMetadata.equals(Object)", "int SecretMetadata.hashCode()"})
  void testSecretMetadataEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    SecretMetadata secretMetadata = new SecretMetadata();

    // Act and Assert
    assertEquals(secretMetadata, secretMetadata);
    int expectedHashCodeResult = secretMetadata.hashCode();
    assertEquals(expectedHashCodeResult, secretMetadata.hashCode());
  }

  /**
   * Test SecretMetadata {@link SecretMetadata#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecretMetadata#equals(Object)}
   */
  @Test
  @DisplayName("Test SecretMetadata equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SecretMetadata.equals(Object)", "int SecretMetadata.hashCode()"})
  void testSecretMetadataEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SecretMetadata secretMetadata = new SecretMetadata("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY",
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertNotEquals(secretMetadata, new SecretMetadata());
  }

  /**
   * Test SecretMetadata {@link SecretMetadata#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecretMetadata#equals(Object)}
   */
  @Test
  @DisplayName("Test SecretMetadata equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SecretMetadata.equals(Object)", "int SecretMetadata.hashCode()"})
  void testSecretMetadataEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SecretMetadata secretMetadata = new SecretMetadata();

    // Act and Assert
    assertNotEquals(secretMetadata, new SecretMetadata("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY",
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
  }

  /**
   * Test SecretMetadata {@link SecretMetadata#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecretMetadata#equals(Object)}
   */
  @Test
  @DisplayName("Test SecretMetadata equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SecretMetadata.equals(Object)", "int SecretMetadata.hashCode()"})
  void testSecretMetadataEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    SecretMetadata secretMetadata = new SecretMetadata();
    secretMetadata.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertNotEquals(secretMetadata, new SecretMetadata());
  }

  /**
   * Test SecretMetadata {@link SecretMetadata#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecretMetadata#equals(Object)}
   */
  @Test
  @DisplayName("Test SecretMetadata equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SecretMetadata.equals(Object)", "int SecretMetadata.hashCode()"})
  void testSecretMetadataEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    SecretMetadata secretMetadata = new SecretMetadata();

    SecretMetadata secretMetadata2 = new SecretMetadata();
    secretMetadata2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertNotEquals(secretMetadata, secretMetadata2);
  }

  /**
   * Test SecretMetadata {@link SecretMetadata#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecretMetadata#equals(Object)}
   */
  @Test
  @DisplayName("Test SecretMetadata equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SecretMetadata.equals(Object)", "int SecretMetadata.hashCode()"})
  void testSecretMetadataEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SecretMetadata(), null);
  }

  /**
   * Test SecretMetadata {@link SecretMetadata#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecretMetadata#equals(Object)}
   */
  @Test
  @DisplayName("Test SecretMetadata equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SecretMetadata.equals(Object)", "int SecretMetadata.hashCode()"})
  void testSecretMetadataEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SecretMetadata(), "Different type to SecretMetadata");
  }

  /**
   * Test SecretMetadata getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SecretMetadata#SecretMetadata()}
   *   <li>{@link SecretMetadata#setCreatedAt(Instant)}
   *   <li>{@link SecretMetadata#setSecretKey(String)}
   *   <li>{@link SecretMetadata#toString()}
   *   <li>{@link SecretMetadata#getCreatedAt()}
   *   <li>{@link SecretMetadata#getSecretKey()}
   * </ul>
   */
  @Test
  @DisplayName("Test SecretMetadata getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SecretMetadata.<init>()", "void SecretMetadata.<init>(String, Instant)",
      "Instant SecretMetadata.getCreatedAt()", "String SecretMetadata.getSecretKey()",
      "void SecretMetadata.setCreatedAt(Instant)", "void SecretMetadata.setSecretKey(String)",
      "String SecretMetadata.toString()"})
  void testSecretMetadataGettersAndSetters() {
    // Arrange and Act
    SecretMetadata actualSecretMetadata = new SecretMetadata();
    actualSecretMetadata.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualSecretMetadata.setSecretKey("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
    String actualToStringResult = actualSecretMetadata.toString();
    Instant actualCreatedAt = actualSecretMetadata.getCreatedAt();

    // Assert
    assertEquals("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY", actualSecretMetadata.getSecretKey());
    assertEquals(
        "SecretKeeper.SecretMetadata(secretKey=EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY, createdAt=1970-01-01T00"
            + ":00:00Z)",
        actualToStringResult);
    assertSame(actualCreatedAt.EPOCH, actualCreatedAt);
  }

  /**
   * Test SecretMetadata getters and setters.
   * <ul>
   *   <li>When {@code EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SecretMetadata#SecretMetadata(String, Instant)}
   *   <li>{@link SecretMetadata#setCreatedAt(Instant)}
   *   <li>{@link SecretMetadata#setSecretKey(String)}
   *   <li>{@link SecretMetadata#toString()}
   *   <li>{@link SecretMetadata#getCreatedAt()}
   *   <li>{@link SecretMetadata#getSecretKey()}
   * </ul>
   */
  @Test
  @DisplayName("Test SecretMetadata getters and setters; when 'EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SecretMetadata.<init>()", "void SecretMetadata.<init>(String, Instant)",
      "Instant SecretMetadata.getCreatedAt()", "String SecretMetadata.getSecretKey()",
      "void SecretMetadata.setCreatedAt(Instant)", "void SecretMetadata.setSecretKey(String)",
      "String SecretMetadata.toString()"})
  void testSecretMetadataGettersAndSetters_whenEXAMPLEKEYwjalrXUtnFEMIK7mdengBPxRfiCY() {
    // Arrange and Act
    SecretMetadata actualSecretMetadata = new SecretMetadata("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY",
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualSecretMetadata.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualSecretMetadata.setSecretKey("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
    String actualToStringResult = actualSecretMetadata.toString();
    Instant actualCreatedAt = actualSecretMetadata.getCreatedAt();

    // Assert
    assertEquals("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY", actualSecretMetadata.getSecretKey());
    assertEquals(
        "SecretKeeper.SecretMetadata(secretKey=EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY, createdAt=1970-01-01T00"
            + ":00:00Z)",
        actualToStringResult);
    assertSame(actualCreatedAt.EPOCH, actualCreatedAt);
  }
}
