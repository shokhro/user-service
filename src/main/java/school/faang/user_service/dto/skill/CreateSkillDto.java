package school.faang.user_service.dto.skill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Getter
@RequiredArgsConstructor
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public class CreateSkillDto {
    private String title;
}
