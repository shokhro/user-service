package school.faang.user_service.service.user;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.dto.skill.SkillDto;
//import school.faang.user_service.dto.user.skill.SkillDto;
import school.faang.user_service.entity.user.Skill;
import school.faang.user_service.entity.user.User;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.mapper.SkillMapper;
import school.faang.user_service.repository.user.SkillRepository;
import school.faang.user_service.repository.user.UserRepository;
import school.faang.user_service.service.skill.SkillService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {

    @InjectMocks
    private SkillService skillService;
    @Mock
    private SkillRepository skillRepository;
    @Spy
    private SkillMapper skillMapper;
    @Mock
    private UserRepository userRepository;
    @Captor
    private ArgumentCaptor<Skill> captor;

//    @Test
//    public void testCreateWithBlankTitle(){
//        SkillDto skillDto = new SkillDto();
//        skillDto.setTitle(" ");
//        assertThrows(DataValidationException.class, () -> skillService.create(skillDto));
//    }
//
//    @Test
//    public void testCreateWithExistingTitle(){
//        SkillDto skillDto = prepareData(true);
//
//        assertThrows(DataValidationException.class, () -> skillService.create(skillDto));
//    }
//
//    @Test
//    public void testCreateSavesSkill(){
//        SkillDto skillDto = prepareData(false);
//        when(userRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(new User(), new User()));
//
//        SkillDto result = skillService.create(skillDto);
//
//        verify(skillRepository, times(1)).save(captor.capture());
//        Skill skill = captor.getValue();
//        assertEquals(skillDto.getUserIds(), skill.getUsers().stream().map(User::getId).toList());
//        assertEquals(skillDto.getTitle(), result.getTitle());
//    }

    private SkillDto prepareData(boolean existsByTitle){
        SkillDto skillDto = new SkillDto();
        skillDto.setTitle("title");
        when(skillRepository.existsByTitle(skillDto.getTitle())).thenReturn(existsByTitle);
        return skillDto;
    }
}
