package school.faang.user_service.controller.user;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import school.faang.user_service.dto.user.CreateUserDto;
import school.faang.user_service.dto.user.UpdateUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.service.user.UserService;

@Component
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    public UserDto create(CreateUserDto userDto) {
        if (userDto.getUsername() == null || userDto.getUsername().isBlank()) {
            throw new DataValidationException("Username should be present!");
        }
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new DataValidationException("Email should be present!");
        }
        if (userDto.getPassword() == null || userDto.getPassword().isBlank()) {
            throw new DataValidationException("Password should be present!");
        }
        if (userDto.getUsername() == null) {
            throw new DataValidationException("Username should be present!");
        }
        return userService.create(userDto);
    }

    public UserDto update(long userId, UpdateUserDto userDto){
        if (userDto.getUsername() == null || userDto.getUsername().isBlank()) {
            throw new DataValidationException("Username should be present!");
        }
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new DataValidationException("Email should be present!");
        }
        if (userDto.getCountryId() == null) {
            throw new DataValidationException("Country should be present!");
        }
        return userService.update(userId, userDto);
    }

//    public UserDto create(CreateUserDto userDto) {
//        validateString(userDto.username(), "username");
//        validateString(userDto.email(), "email");
//        validateString(userDto.password(), "password");
//        validateNotNull(userDto.countryId(), "country");
//        return userService.create(userDto);
//    }
//
//    public UserDto update(long userId, UpdateUserDto userDto) {
//        validateString(userDto.username(), "username");
//        validateString(userDto.email(), "email");
//        validateNotNull(userDto.countryId(), "country");
//        return userService.update(userId, userDto);
//    }
//
//    public UserDto getById(long userId) {
//        return userService.getById(userId);
//    }
//
//    private void validateString(String value, String paramName) {
//        if (StringUtils.isNotBlank(value)) {
//            throw new DataValidationException(paramName + " should be present!");
//        }
//    }
//
//    private void validateNotNull(Object value, String paramName) {
//        if (value == null) {
//            throw new DataValidationException(paramName + " should be present!");
//        }
//    }
}
