package school.faang.user_service.service.recommendation;

import school.faang.user_service.dto.recommendation.CreateRecommendationDto;
import school.faang.user_service.dto.recommendation.RecommendationDto;

public interface RecommendationService {

    public RecommendationDto create(CreateRecommendationDto recommendationDto);
}
