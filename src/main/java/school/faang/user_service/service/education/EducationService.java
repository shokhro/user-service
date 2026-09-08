package school.faang.user_service.service.education;

import school.faang.user_service.dto.education.CreateEducationDto;
import school.faang.user_service.dto.education.EducationDto;

public interface EducationService {

    EducationDto addEducation(Long id, CreateEducationDto educationDto);
}
