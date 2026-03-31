package school.faang.user_service.dto.mentorship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.RequestStatus;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class MentorshipRequestDto {
    private long id;
    private String description;
    private UserDto receiver;
    private UserDto requester;
    private RequestStatus status;
}
