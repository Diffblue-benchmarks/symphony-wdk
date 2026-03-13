package com.symphony.bdk.workflow.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UnauthorizedExceptionTest {

    @Test
    void constructorWithMessageShouldCreateException() {
        // Arrange
        String message = "Unauthorized access";

        // Act
        UnauthorizedException exception = new UnauthorizedException(message);

        // Assert
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructorWithNullMessageShouldCreateException() {
        // Arrange
        String message = null;

        // Act
        UnauthorizedException exception = new UnauthorizedException(message);

        // Assert
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }
}
