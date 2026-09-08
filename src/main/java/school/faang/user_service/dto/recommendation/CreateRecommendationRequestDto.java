package school.faang.user_service.dto.recommendation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateRecommendationRequestDto {
    @NotBlank
    @Size(max = 300, message = "Belgilar 300 dan oshmasligi kerak")
    private String message;
    @NotNull
    private Long receiverId;

    private List<Long> skillIds;
}
