package school.faang.user_service.controller.skill;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.skill.CreateSkillDto;
import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.service.skill.SkillService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;
    private final UserContext userContext;

    public SkillDto create(CreateSkillDto skillDto) {
        if (skillDto.getTitle() == null || skillDto.getTitle().isBlank()) {
            throw new DataValidationException("Title should be present");
        }
        return skillService.create(skillDto);
    }

    public List<SkillDto> getByUserId(Long userId) {
        return skillService.getByUserId(userId);
    }

    public List<SkillCandidateDto> getOfferedSkills() {
        long userId = userContext.getUserId();
        return skillService.getOfferedSkills(userId);
    }
}
