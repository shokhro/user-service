package school.faang.user_service.dto.recommendation;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRecommendationDto {
    private Long receiverId;
    private String content;
}
