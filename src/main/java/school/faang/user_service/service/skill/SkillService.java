package school.faang.user_service.service.skill;

import org.springframework.stereotype.Service;
import school.faang.user_service.dto.skill.CreateSkillDto;
import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;

import java.util.List;


public interface SkillService {

    public SkillDto create(CreateSkillDto skillDto);

    public List<SkillDto> getByUserId(Long id);

    public List<SkillCandidateDto> getOfferedSkills(long userId);

    public void acquireSkillFromOffers(long skillId, long userId);
}
