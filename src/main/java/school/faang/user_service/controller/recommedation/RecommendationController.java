package school.faang.user_service.controller.recommedation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.recommendation.CreateRecommendationDto;
import school.faang.user_service.dto.recommendation.RecommendationDto;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.service.recommendation.RecommendationService;

@RestController
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationDto create(CreateRecommendationDto recommendationDto){
        if (recommendationDto.getReceiverId() == null){
            throw new DataValidationException("receiverId must not be null");
        }
        if (recommendationDto.getContent().isBlank()){
            throw new DataValidationException("content should not be empty");
        }

        return recommendationService.create(recommendationDto);
    }
}
