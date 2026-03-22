package school.faang.user_service.validator.recommendation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import school.faang.user_service.entity.recommendation.Recommendation;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.repository.recommendation.RecommendationRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RecommendationValidator {

    private final RecommendationRepository recommendationRepository;

    @Value("${recommendation.max-months}")
    private int maxMonths;

    public void validateCreate(Long authorId, Long receiverId){
        if (authorId.equals(receiverId)) {
            throw new DataValidationException("You cannot recommend yourself");
        }

        recommendationRepository
                .findFirstByAuthorIdAndReceiverIdOrderByCreatedAtDesc(authorId, receiverId)
                .ifPresent(existing -> {
                    LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(maxMonths);

                    if (existing.getCreatedAt().isAfter(sixMonthsAgo)) {
                        throw new DataValidationException(
                                "Recommendation can be left only once in" + maxMonths + " months"
                        );
                    }
                });
    }

    public void validateUpdate(Recommendation recommendation, Long currentUserId){
        if (!recommendation.getAuthor().getId().equals(currentUserId)){
            throw new DataValidationException("You can only update your own recommendations");
        }
    }

    public void validateDelete(Recommendation recommendation, long contextUserId){
        if (!recommendation.getAuthor().getId().equals(contextUserId)){
            throw new DataValidationException("You can only delete your own recommendations");
        }
    }
}
