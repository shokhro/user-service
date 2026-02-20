package school.faang.user_service.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserDto {
    private final Long id;
    private final String username;
    private final String email;
    private final String phone;
    private final String aboutMe;
}
