package school.faang.user_service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public class SearchUserDto {
    private String aboutMePattern;
    private String city;
    private Integer experience;
}
