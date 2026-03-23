package school.faang.user_service.dto.skill;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SkillCandidateDto {
    private SkillDto skill;
    private int offersAmount;
}
