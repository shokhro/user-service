package school.faang.user_service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {

    private String username;
    private String email;
    private String password;
    private Long countryId;
}
