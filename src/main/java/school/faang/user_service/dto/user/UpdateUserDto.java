package school.faang.user_service.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateUserDto {
    private  String username;
    private  String email;
    private  String phone;
    private  String aboutMe;
    private  Long countryId;
    private  String city;
}
