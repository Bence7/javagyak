import com.epam.training.ticketservice.types.CustomDateTime;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomDateTimeTest {

    @Test
    void setDateTimeFromString_shouldSetDateTimeCorrectly() {
        // Arrange
        CustomDateTime customDateTime = new CustomDateTime();
        String dateTimeString = "2023-11-26 15:30";

        // Act
        customDateTime.setDateTimeFromString(dateTimeString);
        LocalDateTime actualDateTime = customDateTime.getDateTime();

        // Assert
        assertEquals(LocalDateTime.of(2023, 11, 26, 15, 30), actualDateTime);
    }

    @Test
    void formatLocalDateTimeToString_shouldFormatDateTimeCorrectly() {
        // Arrange
        CustomDateTime customDateTime = new CustomDateTime();
        LocalDateTime dateTime = LocalDateTime.of(2023, 11, 26, 15, 30);
        String expectedFormattedString = "2023-11-26 15:30";

        // Act
        String actualFormattedString = customDateTime.formatLocalDateTimeToString(dateTime);

        // Assert
        assertEquals(expectedFormattedString, actualFormattedString);
    }
}

