package school.faang.user_service.dto.recommendation;

import lombok.Getter;
import lombok.Setter;
import school.faang.user_service.entity.RequestStatus;

@Getter
@Setter
public class RecommendationRequestFilterDto {
    private Long requesterId;
    private Long receiverId;
    private String messageContains;   //xabarda mavjud
    private RequestStatus status;
}
