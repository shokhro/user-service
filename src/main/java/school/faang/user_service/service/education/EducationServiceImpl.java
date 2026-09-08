package school.faang.user_service.service.education;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import school.faang.user_service.dto.education.CreateEducationDto;
import school.faang.user_service.dto.education.EducationDto;
import school.faang.user_service.entity.user.Education;
import school.faang.user_service.entity.user.User;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.mapper.EducationMapper;
import school.faang.user_service.repository.user.EducationRepository;
import school.faang.user_service.repository.user.UserRepository;

import java.time.LocalDateTime;
@Slf4j
@Service
@AllArgsConstructor
public class EducationServiceImpl implements EducationService{

    private final UserRepository userRepository;
    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;

    @Override
    public EducationDto addEducation(Long id, CreateEducationDto educationDto) {
        Integer yearFrom = educationDto.getYearFrom();
        if (yearFrom > LocalDateTime.now().getYear())
            throw new DataValidationException("O'rganish vaqti hozirgi vaqtdan oldin bulolmaydi");
        User user = userRepository.getByIdOrThrow(id);
        Education education = educationMapper.toEntity(educationDto);
        education.setUser(user);
        educationRepository.save(education);
        log.info("Education saved");
        return educationMapper.toDto(education);
    }
}
