package school.faang.user_service.controller.recommedation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.recommendation.CreateRecommendationDto;
import school.faang.user_service.dto.recommendation.RecommendationDto;
import school.faang.user_service.dto.recommendation.RecommendationFilterDto;
import school.faang.user_service.dto.recommendation.UpdateRecommendationDto;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.service.recommendation.RecommendationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationDto create(CreateRecommendationDto recommendationDto) {
        if (recommendationDto.getReceiverId() == null) {
            throw new DataValidationException("receiverId must not be null");
        }
        if (recommendationDto.getContent().isBlank()) {
            throw new DataValidationException("content should not be empty");
        }

        return recommendationService.create(recommendationDto);
    }

    public RecommendationDto update(long recommendationId, UpdateRecommendationDto recommendationDto) {
        if (recommendationDto.getContent() == null || recommendationDto.getContent().isBlank()) {
            throw new DataValidationException("content should not be empty");
        }

        return recommendationService.update(recommendationId, recommendationDto);
    }

    public RecommendationDto delete(long recommendationId) {
        return recommendationService.delete(recommendationId);
    }

    public List<RecommendationDto> getByFilters(RecommendationFilterDto filter) {
        return recommendationService.getByFilters(filter);
    }
}
