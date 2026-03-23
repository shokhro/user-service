package school.faang.user_service.validator.recommendation;

import org.springframework.stereotype.Component;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.ForbiddenException;

@Component
public class MentorshipValidator {

    public void validateDelete(long menteeId, long mentorId, long currentUserId) {
        if (currentUserId != menteeId && currentUserId != mentorId) {
            throw new ForbiddenException("Only mentor or mentee can delete this relationship");
        }
    }

    public void validateAddMentorship(long menteeId, long mentorId) {
        if (mentorId == menteeId) {
            throw new DataValidationException("User cannot be mentor for himself");
        }
    }
}
