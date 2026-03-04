package school.faang.user_service.service.recommendation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.recommendation.CreateRecommendationDto;
import school.faang.user_service.dto.recommendation.RecommendationDto;
import school.faang.user_service.entity.recommendation.Recommendation;
import school.faang.user_service.entity.user.User;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.EntityNotFoundException;
import school.faang.user_service.mapper.RecommendationMapper;
import school.faang.user_service.repository.recommendation.RecommendationRepository;
import school.faang.user_service.repository.user.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final RecommendationMapper recommendationMapper;
    private final UserRepository userRepository;
    private final UserContext userContext;

    @Override
    public RecommendationDto create(CreateRecommendationDto recommendationDto) {

        Long authorId = userContext.getUserId();
        Long receiverId = recommendationDto.getReceiverId();

        User author = findUserByUserId(authorId);
        User receiver = findUserByUserId(receiverId);

        if (authorId.equals(receiverId)) {
            throw new DataValidationException("You cannot recommend yourself");
        }

        recommendationRepository
                .findFirstByAuthorIdAndReceiverIdOrderByCreatedAtDesc(authorId, receiverId)
                .ifPresent(existing -> {
                    LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(6);

                    if (existing.getCreatedAt().isAfter(sixMonthsAgo)) {
                        throw new DataValidationException(
                                "Recommendation can be left only once in 6 months"
                        );
                    }
                });

        Recommendation recommendation = recommendationMapper.toRecommendation(recommendationDto);

        recommendation.setAuthor(author);
        recommendation.setReceiver(receiver);
        recommendation.setCreatedAt(LocalDateTime.now());

        Recommendation savedRecommendation = recommendationRepository.save(recommendation);

        return recommendationMapper.toRecommendationDto(savedRecommendation);
    }

    private User findUserByUserId(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID: " + userId + " does not exist"));
    }
}
