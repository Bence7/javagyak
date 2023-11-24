package prompt;

import com.epam.training.ticketservice.prompt.CustomPrompt;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class CustomPromptTest {
    CustomPrompt underTest;

    @Test
    public void testShouldReturnCorrectPromp() {
        underTest = new CustomPrompt();
        AttributedString attributedString = underTest.getPrompt();
        Assertions.assertEquals(attributedString, new AttributedString("Ticket service>"));
    }
}
