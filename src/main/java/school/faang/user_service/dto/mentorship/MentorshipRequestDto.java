package school.faang.user_service.dto.mentorship;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.RequestStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MentorshipRequestDto {
    private long id;
    @NotBlank
    @Size(max = 256, message = "Belgilarsoni 256 dan oshmasligi kerak")
    private String description;
    private UserDto receiver;
    private UserDto requester;
    private RequestStatus status;
}
