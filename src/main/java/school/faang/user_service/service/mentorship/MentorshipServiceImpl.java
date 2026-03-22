package school.faang.user_service.service.mentorship;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.user.User;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.repository.mentorship.MentorshipRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MentorshipServiceImpl implements MentorshipService{

    private final MentorshipRepository mentorshipRepository;

    @Override
    public void addMentorship(long mentorId, long menteeId) {
        if (mentorId == menteeId){
            throw new DataValidationException("User cannot be mentor for himself");
        }

        User mentor = mentorshipRepository.getByIdOrThrow(mentorId);
        User mentee = mentorshipRepository.getByIdOrThrow(menteeId);

        boolean alreadyExists = mentee.getMentors()
                .stream()
                .anyMatch(m -> m.getId() == mentorId);

        if (!alreadyExists){
            mentee.getMentors().add(mentor);
            mentorshipRepository.save(mentee);
        }
    }

    @Override
    public List<UserDto> getMentees(long userId) {
        return null;
    }
}
