package school.faang.user_service.service.mentorship;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.mentorship.CreateMentorshipRequestDto;
import school.faang.user_service.dto.mentorship.MentorshipRequestDto;
import school.faang.user_service.entity.user.MentorshipRequest;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.mapper.MentorshipRequestMapper;
import school.faang.user_service.repository.mentorship.MentorshipRequestRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MentorshipRequestServiceImpl implements MentorshipRequestService {

    private final MentorshipRequestRepository mentorshipRequestRepository;
    private final MentorshipRequestMapper mentorshipRequestMapper;
    private final UserContext userContext;

    @Override
    public MentorshipRequestDto create(CreateMentorshipRequestDto mentorshipRequestDto) {
        long requesterId = userContext.getUserId();
        long receiverId = mentorshipRequestDto.getMentorId();

        if (requesterId == receiverId) {
            throw new DataValidationException(
                    "You cannot send mentorship request to yourself");
        }

        mentorshipRequestRepository
                .findLatestRequest(requesterId, receiverId)
                .ifPresent(request -> {
                    LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
                    if (request.getCreatedAt().isAfter(threeMonthsAgo)) {
                        throw new DataValidationException(
                                "You can send mentorship request only once in 3 months"
                        );
                    }
                });

        MentorshipRequest createdRequest = mentorshipRequestRepository
                .create(requesterId, receiverId, mentorshipRequestDto.getDescription());

        return mentorshipRequestMapper.toMentorshipRequestDto(createdRequest);
    }


}
