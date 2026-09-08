package school.faang.user_service.dto.skill;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Getter
@Setter
@RequiredArgsConstructor
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public class CreateSkillDto {

    @NotBlank(message = "Title bo'sh bo'lmasligi kerak.")
    @Size(max = 56, message = "Belgilar soni 56 tadan oshmasligi kerak.")
    private String title;
}
