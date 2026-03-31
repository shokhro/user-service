package school.faang.user_service.validator.mentorship;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.ForbiddenException;

@ExtendWith(MockitoExtension.class)
public class MentorshipValidatorTest {

    @InjectMocks
    private MentorshipValidator mentorshipValidator;

    @Test
    public void testValidateDeleteWhenNotMentorOrMentee_shouldThrowException() {
        long menteeId = 1L;
        long mentorId = 2L;
        long currenUserId = 3L;

        Assertions.assertThrows(ForbiddenException.class, () ->
                mentorshipValidator.validateDelete(menteeId, mentorId, currenUserId)
        );
    }

    @Test
    public void testValidateDeleteWhenMentor_shouldNotThrowException() {
        long menteeId = 1L;
        long mentorId = 2L;
        long currenUserId = 2L;

        Assertions.assertDoesNotThrow(() ->
                mentorshipValidator.validateDelete(menteeId, mentorId, currenUserId)
        );
    }

    @Test
    public void testValidateDeleteWhenMentee_shouldNotThrowException() {
        long menteeId = 1L;
        long mentorId = 2L;
        long currenUserId = 1L;

        Assertions.assertDoesNotThrow(() ->
                mentorshipValidator.validateDelete(menteeId, mentorId, currenUserId)
        );
    }

    @Test
    public void testValidateAddMentorshipWhenEquals_shouldThrowsException() {
        long mentorId = 1L;
        long menteeId = 1L;

        Assertions.assertThrows(DataValidationException.class, () ->
                mentorshipValidator.validateAddMentorship(menteeId, mentorId));
    }

    @Test
    public void testValidateAddMentorshipWhenNoEquals_shouldNoThrowsException() {
        long mentorId = 1L;
        long menteeId = 2L;

        Assertions.assertDoesNotThrow(() ->
                mentorshipValidator.validateAddMentorship(menteeId, mentorId));
    }
}
