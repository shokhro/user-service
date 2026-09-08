package school.faang.user_service.service.recommendation;


import school.faang.user_service.dto.RejectionDto;
import school.faang.user_service.dto.recommendation.CreateRecommendationRequestDto;
import school.faang.user_service.dto.recommendation.RecommendationRequestDto;
import school.faang.user_service.dto.recommendation.RecommendationRequestFilterDto;

import java.util.List;

public interface RecommendationRequestService {

    RecommendationRequestDto create(CreateRecommendationRequestDto recommendationRequestDto);

    List<RecommendationRequestDto> getByFilters(RecommendationRequestFilterDto filterDto);

    RecommendationRequestDto getById(Long id);

    void accept(long id);

    void reject(long id, RejectionDto rejectionDto);

}
