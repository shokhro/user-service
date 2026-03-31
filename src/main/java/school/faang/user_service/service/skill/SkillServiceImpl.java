package school.faang.user_service.service.skill;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import school.faang.user_service.dto.skill.CreateSkillDto;
import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.user.Skill;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.mapper.SkillMapper;
import school.faang.user_service.repository.recommendation.SkillOfferRepository;
import school.faang.user_service.repository.user.SkillRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    private final SkillOfferRepository skillOfferRepository;

    @Override
    @Transactional
    public SkillDto create(CreateSkillDto skillDto) {
        if (skillRepository.existsByTitle(skillDto.getTitle())) {
            throw new DataValidationException("Skill with title "
                    + skillDto.getTitle() + " already exists");
        }
        Skill skillEntity = skillMapper.toEntity(skillDto);
        log.info("Skill created" + skillEntity.getTitle());
        return skillMapper.toDto(skillRepository.save(skillEntity));
    }

    @Override
    public List<SkillDto> getByUserId(Long id) {
        List<Skill> skills = skillRepository.findAllByUserId(id);
        return skillMapper.toDto(skills);
    }

    @Override
    public List<SkillCandidateDto> getOfferedSkills(long userId) {
        List<Skill> offeredSkills = skillRepository.findSkillsOfferedToUser(userId);

        return offeredSkills.stream()
                .map(skill -> {
                    int offersAmount = skillOfferRepository.countAllOffersOfSkill(skill.getId(), userId);
                    SkillDto skillDto = skillMapper.toDto(skill);
                    return new SkillCandidateDto(skillDto, offersAmount);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void acquireSkillFromOffers(long skillId, long userId) {
        skillRepository.assignSkillToUser(skillId, userId);
    }
}














