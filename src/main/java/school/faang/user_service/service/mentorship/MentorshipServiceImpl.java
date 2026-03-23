package school.faang.user_service.service.mentorship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.user.User;
import school.faang.user_service.mapper.UserMapper;
import school.faang.user_service.repository.mentorship.MentorshipRepository;
import school.faang.user_service.validator.recommendation.MentorshipValidator;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MentorshipServiceImpl implements MentorshipService {

    private final MentorshipRepository mentorshipRepository;
    private final UserMapper userMapper;
    private final UserContext userContext;
    private final MentorshipValidator mentorshipValidator;

    @Override
    public void addMentorship(long mentorId, long menteeId) {
        log.info("Request to create mentorship | mentorId={} menteeId={}", mentorId, menteeId);
        mentorshipValidator.validateAddMentorship(menteeId, mentorId);

        User mentor = mentorshipRepository.getByIdOrThrow(mentorId);
        User mentee = mentorshipRepository.getByIdOrThrow(menteeId);

        boolean alreadyExists = mentee.getMentors()
                .stream()
                .anyMatch(m -> m.getId() == mentorId);

        if (!alreadyExists) {
            mentee.getMentors().add(mentor);
            mentorshipRepository.save(mentee);
            log.info("Mentorship successfully created | mentorId={} menteeId={}", mentorId, menteeId);
        }
    }

    @Override
    public List<UserDto> getMentees(long userId) {
        log.info("Fetching mentees | mentorId={}", userId);
        User mentor = mentorshipRepository.getByIdOrThrow(userId);

        List<UserDto> result = mentor.getMentees()
                .stream()
                .map(userMapper::toUserDto)
                .toList();

        log.info("Mentees fetched | mentorId={} count={}", userId, result.size());

        return result;
    }

    @Override
    public List<UserDto> getMentors(long userId) {
        log.info("Fetching mentors | menteeId={}", userId);
        User mentee = mentorshipRepository.getByIdOrThrow(userId);

        List<UserDto> result = mentee.getMentors()
                .stream()
                .map(userMapper::toUserDto)
                .toList();

        log.info("Mentors fetched | menteeId={} count={}", userId, result.size());

        return result;
    }

    @Override
    public void deleteMentorship(long menteeId, long mentorId) {
        long currentUserId = userContext.getUserId();

        log.info("Request to delete mentorship | mentorId={} menteeId={} requestedBy={}",
                mentorId, menteeId, currentUserId);

        mentorshipValidator.validateDelete(menteeId, mentorId, currentUserId);

        User mentee = mentorshipRepository.getByIdOrThrow(menteeId);
        mentee.getMentors().removeIf(m -> m.getId() == mentorId);
        mentorshipRepository.save(mentee);
        log.info("Mentorship deleted successfully | mentorId={} menteeId={}", mentorId, menteeId);
    }
}
