package com.epam.training.ticketservice.prompt;

import org.jline.utils.AttributedString;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomPromptTest {

    @InjectMocks
    private CustomPrompt customPrompt;

    public CustomPromptTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPrompt_ShouldReturnExpectedPrompt() {
        // Arrange
        String expectedPrompt = "Ticket service>";

        // Act
        AttributedString result = customPrompt.getPrompt();

        // Assert
        assertEquals(expectedPrompt, result.toString());
    }
}
