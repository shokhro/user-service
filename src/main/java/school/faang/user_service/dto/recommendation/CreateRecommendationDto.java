package school.faang.user_service.dto.recommendation;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRecommendationDto {
    private Long receiverId;
    private String content;
}
