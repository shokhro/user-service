package school.faang.user_service.controller.recommedation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.RejectionDto;
import school.faang.user_service.dto.recommendation.CreateRecommendationRequestDto;
import school.faang.user_service.dto.recommendation.RecommendationRequestDto;
import school.faang.user_service.dto.recommendation.RecommendationRequestFilterDto;
import school.faang.user_service.service.recommendation.RecommendationRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecommendationRequestController {

    private final RecommendationRequestService recommendationRequestService;

    @PostMapping("/recommendationRequests")
    public RecommendationRequestDto create(
            @RequestBody CreateRecommendationRequestDto createRecomendationRequestDto){
        return recommendationRequestService.create(createRecomendationRequestDto);
    }

    @GetMapping("/recommendationRequests")
    public List<RecommendationRequestDto> getByFilters(
            @RequestBody RecommendationRequestFilterDto filterDto){
        return recommendationRequestService.getByFilters(filterDto);
    }

    @GetMapping("/recommendationRequests/{id}")
    public RecommendationRequestDto getById(@PathVariable Long id){
       return recommendationRequestService.getById(id);
    }

    @PostMapping("/recommendationRequests/accept/{id}")
    public void accept(@PathVariable Long id){
        recommendationRequestService.accept(id);
    }

    @PostMapping("/recommendationRequests/reject/{id}")
    public void reject(@PathVariable Long id,
                       @RequestBody RejectionDto rejectionDto){
        recommendationRequestService.reject(id, rejectionDto);
    }
}
