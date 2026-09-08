package school.faang.user_service.controller.recommedation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/api/v1")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @PostMapping("/recommendations")
    public RecommendationDto create(@RequestBody CreateRecommendationDto recommendationDto) {
        if (recommendationDto.getReceiverId() == null) {
            throw new DataValidationException("receiverId must not be null");
        }
        if (recommendationDto.getContent().isBlank()) {
            throw new DataValidationException("content should not be empty");
        }

        return recommendationService.create(recommendationDto);
    }

    @PutMapping("/recommendations/{recommendationId}")
    public RecommendationDto update(@PathVariable long recommendationId,
                                    @RequestBody UpdateRecommendationDto recommendationDto) {
        if (recommendationDto.getContent() == null || recommendationDto.getContent().isBlank()) {
            throw new DataValidationException("content should not be empty");
        }

        return recommendationService.update(recommendationId, recommendationDto);
    }

    @DeleteMapping("/recommendations/{recommendationId}")
    public RecommendationDto delete(@PathVariable long recommendationId) {
        return recommendationService.delete(recommendationId);
    }

    @GetMapping("/recommendations")
    public List<RecommendationDto> getByFilters(@RequestBody RecommendationFilterDto filter) {
        return recommendationService.getByFilters(filter);
    }
}
