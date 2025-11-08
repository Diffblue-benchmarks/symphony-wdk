package com.symphony.bdk.workflow.management;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BigStringCompressor.class})
@ExtendWith(SpringExtension.class)
class BigStringCompressorDiffblueTest {
  @Autowired
  private BigStringCompressor bigStringCompressor;

  /**
   * Test {@link BigStringCompressor#convertToDatabaseColumn(String)} with {@code String}.
   * <p>
   * Method under test: {@link BigStringCompressor#convertToDatabaseColumn(String)}
   */
  @Test
  @DisplayName("Test convertToDatabaseColumn(String) with 'String'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"byte[] BigStringCompressor.convertToDatabaseColumn(String)"})
  void testConvertToDatabaseColumnWithString() {
    // Arrange, Act and Assert
    assertArrayEquals(new byte[]{31, -117, '\b', 0, 0, 0, 0, 0, 0, -1, 's', ',', ')', ')', -54, 'L', '*', '-', 'I', 5,
        0, 'X', 'm', -117, 'x', '\t', 0, 0, 0}, bigStringCompressor.convertToDatabaseColumn("Attribute"));
  }

  /**
   * Test {@link BigStringCompressor#convertToEntityAttribute(byte[])} with {@code byte[]}.
   * <p>
   * Method under test: {@link BigStringCompressor#convertToEntityAttribute(byte[])}
   */
  @Test
  @DisplayName("Test convertToEntityAttribute(byte[]) with 'byte[]'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BigStringCompressor.convertToEntityAttribute(byte[])"})
  void testConvertToEntityAttributeWithByte() throws UnsupportedEncodingException {
    // Arrange, Act and Assert
    assertThrows(RuntimeException.class,
        () -> bigStringCompressor.convertToEntityAttribute("AXAXAXAX".getBytes("UTF-8")));
  }
}
