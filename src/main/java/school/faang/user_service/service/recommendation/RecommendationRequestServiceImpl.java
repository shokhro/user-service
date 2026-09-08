package school.faang.user_service.service.recommendation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.RejectionDto;
import school.faang.user_service.dto.recommendation.CreateRecommendationRequestDto;
import school.faang.user_service.dto.recommendation.RecommendationRequestDto;
import school.faang.user_service.dto.recommendation.RecommendationRequestFilterDto;
import school.faang.user_service.entity.RequestStatus;
import school.faang.user_service.entity.recommendation.RecommendationRequest;
import school.faang.user_service.entity.user.User;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.EntityNotFoundException;
import school.faang.user_service.exception.ForbiddenException;
import school.faang.user_service.mapper.RecommendationRequestMapper;
import school.faang.user_service.repository.recommendation.RecommendationRequestRepository;
import school.faang.user_service.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationRequestServiceImpl implements RecommendationRequestService {

    private final UserContext userContext;
    private final RecommendationRequestRepository recommendationRequestRepository;
    private final UserRepository userRepository;
    private final RecommendationRequestMapper recommendationRequestMapper;

    @Value("${recommendation.request.interval-months:6}")
    private int intervalMonths;

    @Override
    public RecommendationRequestDto create(CreateRecommendationRequestDto recommendationRequestDto) {
        long requesterId = userContext.getUserId();

        //O'zi-o'ziga surov yubormasligi kerak
        if (requesterId == recommendationRequestDto.getReceiverId()) {
            throw new DataValidationException("O'zingizdan tavsiya so'rashingiz mumkun emas");
        }

        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new EntityNotFoundException("Foydalanuvchi topilmadi"));
        User receiver = userRepository.findById(recommendationRequestDto.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Qabul qiluvchi topilmadi"));

        // Oxirgi 6 oy ichida shu ikki foydalanuvchi orasida so'rov bo'lganmi tekshiramiz
        recommendationRequestRepository
                .findLatestPendingRequest(requesterId, recommendationRequestDto.getReceiverId())
                .ifPresent(last -> {
                    if (last.getCreatedAt().isAfter(LocalDateTime.now().minusMonths(intervalMonths))) {
                        throw new DataValidationException(
                                "So'rovni faqat " + intervalMonths + " oyda bir marta yuborish mumkin");
                    }
                });

        RecommendationRequest recommendationRequest
                = recommendationRequestMapper.toRecommendationRequest(recommendationRequestDto);
        recommendationRequest.setRequester(requester);
        recommendationRequest.setReceiver(receiver);
        recommendationRequest.setStatus(RequestStatus.PENDING);

        RecommendationRequest savedRecommendationRequest
                = recommendationRequestRepository.save(recommendationRequest);
        log.info("RecommendationRequest saved, Id: {}", savedRecommendationRequest.getId());
        return recommendationRequestMapper.toRecommendationRequestDto(savedRecommendationRequest);
    }

    @Override
    public List<RecommendationRequestDto> getByFilters(RecommendationRequestFilterDto filterDto) {
        log.info("Filtering started | filters: {}", filterDto);
        List<RecommendationRequest> requests = recommendationRequestRepository.findAll();
        List<RecommendationRequestDto> result = requests.stream()
                .filter(r -> filterDto.getRequesterId() == null
                        || Objects.equals(r.getRequester().getId(), filterDto.getRequesterId()))
                .filter(r -> filterDto.getReceiverId() == null
                        || Objects.equals(r.getReceiver().getId(), filterDto.getReceiverId()))
                .filter(r -> filterDto.getMessageContains() == null
                        || (r.getMessage() != null && r.getMessage().contains(filterDto.getMessageContains())))
                .filter(r -> filterDto.getStatus() == null
                        || r.getStatus().equals(filterDto.getStatus()))
                .map(recommendationRequestMapper::toRecommendationRequestDto)
                .collect(Collectors.toList());
        log.info("Filtering completed | count: {}", result.size());
        return result;
    }

    @Override
    public RecommendationRequestDto getById(Long id) {
        log.info("Recommantion Request qidirilmoqda, id={}", id);
        RecommendationRequest recommendationRequest = recommendationRequestRepository.getByIdOrThrow(id);
        return recommendationRequestMapper.toRecommendationRequestDto(recommendationRequest);
    }

    public void accept(long id) {
        long userId = userContext.getUserId();
        RecommendationRequest request = recommendationRequestRepository.getByIdOrThrow(id);

        // Faqat receiver qabul qilishi mumkin — huquq bo'lmasa ForbiddenException
        if (!Objects.equals(request.getReceiver().getId(), userId)) {
            throw new ForbiddenException("So'rovni faqat unga yo'naltirilgan foydalanuvchi qabul qila oladi");
        }
        // Status noto'g'ri bo'lsa — bu biznes shart buzilishi, DataValidationException
        if (!request.getStatus().equals(RequestStatus.PENDING)) {
            throw new DataValidationException("Faqat PENDING statusidagi so'rovni qabul qilish mumkin");
        }

        request.setStatus(RequestStatus.ACCEPTED);
        recommendationRequestRepository.save(request);
        log.info("RecommendationRequest id={} qabul qilindi", id);
    }

    @Override
    public void reject(long id, RejectionDto rejectionDto) {
        long userId = userContext.getUserId();
        RecommendationRequest request = recommendationRequestRepository.getByIdOrThrow(id);

        if (!Objects.equals(request.getReceiver().getId(), userId)) {
            throw new ForbiddenException("So'rovni faqat unga yo'naltirilgan foydalanuvchi rad eta oladi");
        }
        if (!request.getStatus().equals(RequestStatus.PENDING)) {
            throw new DataValidationException("Faqat PENDING statusidagi so'rovni rad etish mumkin");
        }

        request.setStatus(RequestStatus.REJECTED);
        request.setRejectionReason(rejectionDto.getReason());
        recommendationRequestRepository.save(request);
        log.info("RecommendationRequest id={} rad etildi", id);
    }
}






















