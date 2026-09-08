package school.faang.user_service.dto.mentorship;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMentorshipRequestDto {
    @NotBlank
    @Size(max = 256, message = "Belgilarsoni 256 dan oshmasligi kerak")
    private String description;
    private long mentorId;
}
