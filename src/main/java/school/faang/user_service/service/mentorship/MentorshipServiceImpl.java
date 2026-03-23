package school.faang.user_service.service.mentorship;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.user.User;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.mapper.UserMapper;
import school.faang.user_service.repository.mentorship.MentorshipRepository;
import school.faang.user_service.validator.recommendation.MentorshipValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorshipServiceImpl implements MentorshipService{

    private final MentorshipRepository mentorshipRepository;
    private final UserMapper userMapper;
    private final UserContext userContext;
    private final MentorshipValidator mentorshipValidator;

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
        User mentor = mentorshipRepository.getByIdOrThrow(userId);

        return mentor.getMentees()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public List<UserDto> getMentors(long userId) {
        User mentee = mentorshipRepository.getByIdOrThrow(userId);

        return mentee.getMentors()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public void deleteMentorship(long menteeId, long mentorId) {
        long currentUserId = userContext.getUserId();
        mentorshipValidator.validateDelete(menteeId, mentorId, currentUserId);

        User mentee = mentorshipRepository.getByIdOrThrow(menteeId);
        mentee.getMentors().removeIf(m -> m.getId() == mentorId);
        mentorshipRepository.save(mentee);
    }
}
