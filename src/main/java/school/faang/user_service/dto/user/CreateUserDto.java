package school.faang.user_service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {

    private String username;
    private String email;
    private String password;
    private Long countryId;
}
