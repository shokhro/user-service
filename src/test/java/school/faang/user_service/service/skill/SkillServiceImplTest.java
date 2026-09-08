package school.faang.user_service.service.skill;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.dto.skill.CreateSkillDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.user.Skill;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.mapper.SkillMapper;
import school.faang.user_service.repository.recommendation.SkillOfferRepository;
import school.faang.user_service.repository.user.SkillRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SkillServiceImplTest {

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private SkillOfferRepository skillOfferRepository;

    @Mock
    private SkillMapper skillMapper;

    @InjectMocks
    private SkillServiceImpl skillService;

    @Test
    public void create_existSkill_returnsSkillDto(){
        CreateSkillDto skillDto = new CreateSkillDto();
        skillDto.setTitle("Tez yugurish");

        Skill skill = new Skill();
        skill.setId(1L);
        skill.setTitle("Tez yugurish");

        SkillDto resultSkillDto = new SkillDto();
        resultSkillDto.setId(1L);
        resultSkillDto.setTitle("Tez yugurish");

        when(skillRepository.existsByTitle(any(String.class)))
                .thenReturn(false);
        when(skillMapper.toEntity(skillDto)).thenReturn(skill);
        when(skillRepository.save(any(Skill.class))).thenReturn(skill);
        when(skillMapper.toDto(skill)).thenReturn(resultSkillDto);

        SkillDto result = skillService.create(skillDto);

        assertEquals("Tez yugurish", result.getTitle());
        assertEquals(1L, result.getId());
        verify(skillRepository).save(skill);
    }

    @Test
    public void create_notExists_returnsException(){
        CreateSkillDto skillDto = new CreateSkillDto();
        skillDto.setTitle("Tez yugurish");

        when(skillRepository.existsByTitle(any(String.class))).thenReturn(true);

        Assertions.assertThrows(DataValidationException.class,
                () -> skillService.create(skillDto));

    }

    
}




















