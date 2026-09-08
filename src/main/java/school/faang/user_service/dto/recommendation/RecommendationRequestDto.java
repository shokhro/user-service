package school.faang.user_service.dto.recommendation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.RequestStatus;

@Getter
@Setter
@AllArgsConstructor
public class RecommendationRequestDto {
    private Long id;
    private String message;
    private UserDto requester;
    private UserDto receiver;
    private RequestStatus status;
}
