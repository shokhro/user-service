package school.faang.user_service.controller.skill;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.skill.CreateSkillDto;
import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.service.skill.SkillService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SkillController {

    private final SkillService skillService;
    private final UserContext userContext;

    @PostMapping("/skills")
    public SkillDto create(@Valid @RequestBody CreateSkillDto skillDto) {
        return skillService.create(skillDto);
    }

    @GetMapping("/skills/{id}")
    public List<SkillDto> getByUserId(@PathVariable Long id) {
        return skillService.getByUserId(id);
    }

    @GetMapping("/skills/offered/{id}")
    public List<SkillCandidateDto> getOfferedSkills(@PathVariable long id) {
        return skillService.getOfferedSkills(id);
    }

    @GetMapping("/skills/offered")
    public List<SkillCandidateDto> getOfferedSkills() {
        long userId = userContext.getUserId();
        return skillService.getOfferedSkills(userId);
    }

    @PostMapping("skills/{id}")
    public void acquireSkillFromOffers(@PathVariable long id){
        long userId = userContext.getUserId();
        skillService.acquireSkillFromOffers(id, userId);
    }

}











