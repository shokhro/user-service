package school.faang.user_service.validator.recommendation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import school.faang.user_service.entity.recommendation.Recommendation;
import school.faang.user_service.entity.user.User;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.repository.recommendation.RecommendationRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RecommendationValidatorTest {

    @InjectMocks
    private RecommendationValidator recommendationValidator;

    @Mock
    private RecommendationRepository recommendationRepository;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(recommendationValidator, "maxMonths", 6);
        // ← "maxMonths = 6" deb qo'lda set qilamiz
    }

    @Test
    public void testValidateCreateWhenAuthorIdAndReceiverIdAreSomeShouldExceptionThrows(){
        //arrange
        long authorId = 1L;
        long receiverId = 1L;
        //act and assert
        Assertions.assertThrows(DataValidationException.class, () ->
                recommendationValidator.validateCreate(authorId, receiverId));
    }

    @Test
    public void testValidateCreateWhenRecommendationTooEarlyShouldThrowException(){
        //arrange
        long authorId = 2L;
        long receiverId = 1L;

        Recommendation recommendation = new Recommendation();
        recommendation.setCreatedAt(LocalDateTime.now().minusMonths(2));

        Mockito.when(recommendationRepository
                .findFirstByAuthorIdAndReceiverIdOrderByCreatedAtDesc(authorId, receiverId))
                .thenReturn(Optional.of(recommendation));

        //act and assert
        Assertions.assertThrows(DataValidationException.class, () -> {
            recommendationValidator.validateCreate(authorId, receiverId);
        });
    }

    @Test
    public void testValidateCreateWhenSixMonthsPassedShouldNotThrowException(){
        //arrange
        long authorId = 2L;
        long receiverId = 1L;

        Recommendation recommendation = new Recommendation();
        recommendation.setCreatedAt(LocalDateTime.now().minusMonths(8));

        Mockito.when(recommendationRepository
                        .findFirstByAuthorIdAndReceiverIdOrderByCreatedAtDesc(authorId, receiverId))
                .thenReturn(Optional.of(recommendation));

        //act and assert
        Assertions.assertDoesNotThrow(() -> {
            recommendationValidator.validateCreate(authorId, receiverId);
        });
    }

    @Test
    public void testValidateUpdateWhenCannotUpdateSomeoneElsesRecommendationShouldThrowException(){
        Recommendation recommendation = prepareData();

        Assertions.assertThrows(DataValidationException.class, () -> {
            recommendationValidator.validateUpdate(recommendation, 2L);
        });
    }

    @Test
    public void testValidateUpdateWhenAuthorUpdatesOwnRecommendationShouldNotThrowException(){
        Recommendation recommendation = prepareData();

        Assertions.assertDoesNotThrow(() -> {
            recommendationValidator.validateUpdate(recommendation, 1L);
        });
    }

    @Test
    public void testValidateDeleteWhenCannotDeleteSomeoneElsesRecommendationShouldThrowException(){
        Recommendation recommendation = prepareData();

        Assertions.assertThrows(DataValidationException.class, () -> {
            recommendationValidator.validateDelete(recommendation, 2L);
        });
    }

    @Test
    public void testValidateDeleteWhenAuthorDeletesOwnRecommendationShouldNotThrowException(){
        Recommendation recommendation = prepareData();

        Assertions.assertDoesNotThrow(() -> {
            recommendationValidator.validateDelete(recommendation, 1L);
        });
    }

    private Recommendation prepareData(){
        User user = new User();
        user.setId(1L);
        Recommendation recommendation = new Recommendation();
        recommendation.setAuthor(user);
        return recommendation;
    }

}


















