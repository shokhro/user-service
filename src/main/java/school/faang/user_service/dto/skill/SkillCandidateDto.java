package school.faang.user_service.dto.skill;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SkillCandidateDto {
    private SkillDto skill;
    private int offersAmount;
}
