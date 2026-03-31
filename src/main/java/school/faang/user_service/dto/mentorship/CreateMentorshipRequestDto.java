package school.faang.user_service.dto.mentorship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateMentorshipRequestDto {
    private String description;
    private long mentorId;
}
