package school.faang.user_service.service.recommendation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.recommendation.CreateRecommendationDto;
import school.faang.user_service.dto.recommendation.RecommendationDto;
import school.faang.user_service.dto.recommendation.UpdateRecommendationDto;
import school.faang.user_service.entity.recommendation.Recommendation;
import school.faang.user_service.entity.user.User;
import school.faang.user_service.mapper.RecommendationMapper;
import school.faang.user_service.repository.recommendation.RecommendationRepository;
import school.faang.user_service.repository.user.UserRepository;
import school.faang.user_service.validator.recommendation.RecommendationValidator;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceImplTest {

    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    @Mock
    private RecommendationRepository recommendationRepository;
    @Mock
    private RecommendationMapper recommendationMapper;
    @Mock
    private RecommendationValidator recommendationValidator;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserContext userContext;

    @Test
    public void testCreate_whenValidDataShouldReturnRecommendationDto() {
        CreateRecommendationDto createRecommendationDto = new CreateRecommendationDto();
        createRecommendationDto.setReceiverId(1L);
        when(userContext.getUserId()).thenReturn(2L);

        User author = new User();
        author.setId(2L);
        User receiver = new User();
        receiver.setId(1L);
        when(userRepository.findById(2L)).thenReturn(Optional.of(author));
        when(userRepository.findById(1L)).thenReturn(Optional.of(receiver));

        Recommendation recommendation = new Recommendation();
        when(recommendationMapper.toRecommendation(createRecommendationDto)).thenReturn(recommendation);

        Recommendation savedRecommendation = new Recommendation();
        savedRecommendation.setId(1L);
        when(recommendationRepository.save(any())).thenReturn(savedRecommendation);

        RecommendationDto recommendationDto = new RecommendationDto();
        recommendationDto.setId(1L);
        when(recommendationMapper.toRecommendationDto(any())).thenReturn(recommendationDto);

        RecommendationDto result = recommendationService.create(createRecommendationDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        verify(recommendationRepository).save(any());
        verify(recommendationValidator).validateCreate(2L, 1L);
    }

    @Test
    public void testUpdate_whenValidDataShouldReturnRecommendationDto() {
        UpdateRecommendationDto updateRecommendationDto = new UpdateRecommendationDto();
        updateRecommendationDto.setContent("Yaxshi rassom");
        long recommendationId = 1L;

        when(userContext.getUserId()).thenReturn(2L);

        Recommendation recommendation = new Recommendation();
        when(recommendationRepository.findById(recommendationId)).thenReturn(Optional.of(recommendation));

        Recommendation savedRecommendation = new Recommendation();
        savedRecommendation.setContent("Yaxshi dasturchi");
        savedRecommendation.setId(1L);

        when(recommendationRepository.save(recommendation)).thenReturn(savedRecommendation);

        RecommendationDto recommendationDto = new RecommendationDto();
        when(recommendationMapper.toRecommendationDto(savedRecommendation)).thenReturn(recommendationDto);

        RecommendationDto result = recommendationService.update(1L, updateRecommendationDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(recommendationDto, result);
        verify(recommendationRepository).save(recommendation);
        verify(recommendationValidator).validateUpdate(recommendation, 2L);
    }

    @Test
    public void testDelete_whenValidDataShouldReturnRecommendationDto() {
        //Arrange
        when(userContext.getUserId()).thenReturn(1L);

        Recommendation recommendation = new Recommendation();
        recommendation.setId(1L);
        long recommendationId = recommendation.getId();
        when(recommendationRepository.findById(recommendationId)).thenReturn(Optional.of(recommendation));

        RecommendationDto recommendationDto = new RecommendationDto();
        recommendationDto.setId(1L);
        when(recommendationMapper.toRecommendationDto(recommendation)).thenReturn(recommendationDto);

        //Act
        RecommendationDto result = recommendationService.delete(recommendationId);

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getId());
        verify(recommendationMapper).toRecommendationDto(recommendation);
        verify(recommendationRepository).deleteById(recommendationId);
        verify(recommendationValidator).validateDelete(recommendation, 1L);
    }
}






















