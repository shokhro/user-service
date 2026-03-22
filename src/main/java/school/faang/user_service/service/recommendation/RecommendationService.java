package school.faang.user_service.service.recommendation;

import school.faang.user_service.dto.recommendation.CreateRecommendationDto;
import school.faang.user_service.dto.recommendation.RecommendationDto;
import school.faang.user_service.dto.recommendation.RecommendationFilterDto;
import school.faang.user_service.dto.recommendation.UpdateRecommendationDto;

import java.util.List;

public interface RecommendationService {

    public RecommendationDto create(CreateRecommendationDto recommendationDto);

    public RecommendationDto update(Long recommendationId, UpdateRecommendationDto recommendationDto);

    public RecommendationDto delete(long recommendationId);

    public List<RecommendationDto> getByFilters(RecommendationFilterDto filters);
}
