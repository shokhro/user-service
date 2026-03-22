package school.faang.user_service.service.recommendation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.recommendation.CreateRecommendationDto;
import school.faang.user_service.dto.recommendation.RecommendationDto;
import school.faang.user_service.dto.recommendation.RecommendationFilterDto;
import school.faang.user_service.dto.recommendation.UpdateRecommendationDto;
import school.faang.user_service.entity.recommendation.Recommendation;
import school.faang.user_service.entity.user.User;
import school.faang.user_service.exception.EntityNotFoundException;
import school.faang.user_service.mapper.RecommendationMapper;
import school.faang.user_service.repository.recommendation.RecommendationRepository;
import school.faang.user_service.repository.user.UserRepository;
import school.faang.user_service.validator.recommendation.RecommendationValidator;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final RecommendationMapper recommendationMapper;
    private final RecommendationValidator recommendationValidator;
    private final UserRepository userRepository;
    private final UserContext userContext;

    @Override
    public RecommendationDto create(CreateRecommendationDto recommendationDto) {
        Long authorId = userContext.getUserId();
        Long receiverId = recommendationDto.getReceiverId();
        log.info("Creating recommendation | authorId={} receiverId={}", authorId, receiverId);

        recommendationValidator.validateCreate(authorId, receiverId);

        User author = findUserByUserId(authorId);
        User receiver = findUserByUserId(receiverId);

        Recommendation recommendation = recommendationMapper.toRecommendation(recommendationDto);
        recommendation.setAuthor(author);
        recommendation.setReceiver(receiver);
        recommendation.setCreatedAt(LocalDateTime.now());
        Recommendation savedRecommendation = recommendationRepository.save(recommendation);
        log.info("Recommendation created successfully | id={}", savedRecommendation.getId());
        return recommendationMapper.toRecommendationDto(savedRecommendation);
    }

    @Override
    public RecommendationDto update(Long recommendationId, UpdateRecommendationDto recommendationDto) {
        log.info("Updating recommendation | recommendationId={}", recommendationId);
        Long currentUserId = userContext.getUserId();

        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new EntityNotFoundException("Recommendation not found"));

        recommendationValidator.validateUpdate(recommendation, currentUserId);

        recommendation.setContent(recommendationDto.getContent());
        Recommendation savedRecommendation = recommendationRepository.save(recommendation);
        log.info("Recommendation updated successfully | recommendationId={}", savedRecommendation.getId());
        return recommendationMapper.toRecommendationDto(savedRecommendation);
    }

    @Override
    public RecommendationDto delete(long recommendationId) {
        long contextUserId = userContext.getUserId();
        log.info("Deleting recommendation | recommendationId={} userId={}",
                recommendationId, contextUserId);

        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new EntityNotFoundException("Recommendation not found"));

        recommendationValidator.validateDelete(recommendation, contextUserId);

        recommendationRepository.deleteById(recommendationId);
        log.info("Recommendation deleted successfully | recommendationId={}", recommendationId);
        return recommendationMapper.toRecommendationDto(recommendation);
    }

    @Override
    public List<RecommendationDto> getByFilters(RecommendationFilterDto filters) {
        log.info("Filtering started | filters={}", filters);

        List<Recommendation> recommendations = recommendationRepository.findAll();
        List<RecommendationDto> result =  recommendations.stream()
                .filter(r -> filters.getAuthorId() == null ||
                        r.getAuthor().getId().equals(filters.getAuthorId()))
                .filter(r -> filters.getReceiverId() == null ||
                        r.getReceiver().getId().equals(filters.getReceiverId()))
                .filter(r -> filters.getContentContains() == null ||
                        r.getContent().contains(filters.getContentContains()))
                .map(recommendationMapper::toRecommendationDto)
                .toList();

        log.info("Filtering completed | count={}", result.size());

        return result;
    }


    private User findUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID: " + userId + " does not exist"));
    }
}











