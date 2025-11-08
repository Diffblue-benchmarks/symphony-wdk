package com.symphony.bdk.workflow.management;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.UnsupportedEncodingException;
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
   * Method under test:
   * {@link BigStringCompressor#convertToDatabaseColumn(String)}
   */
  @Test
  void testConvertToDatabaseColumn() {
    // Arrange, Act and Assert
    assertArrayEquals(new byte[]{31, -117, '\b', 0, 0, 0, 0, 0, 0, -1, 's', ',', ')', ')', -54, 'L', '*', '-', 'I', 5,
        0, 'X', 'm', -117, 'x', '\t', 0, 0, 0}, bigStringCompressor.convertToDatabaseColumn("Attribute"));
  }

  /**
   * Method under test:
   * {@link BigStringCompressor#convertToEntityAttribute(byte[])}
   */
  @Test
  void testConvertToEntityAttribute() throws UnsupportedEncodingException {
    // Arrange, Act and Assert
    assertThrows(RuntimeException.class,
        () -> bigStringCompressor.convertToEntityAttribute("AXAXAXAX".getBytes("UTF-8")));
  }
}
