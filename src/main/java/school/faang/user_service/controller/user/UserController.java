package school.faang.user_service.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import school.faang.user_service.dto.user.CreateUserDto;
import school.faang.user_service.dto.user.UpdateUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.service.user.UserService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{id}")
    public UserDto getUser(@PathVariable long id) {
        return null;
    }

    @PostMapping("/users")
    public UserDto create(@RequestBody CreateUserDto userDto) {
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

    @PutMapping("/users/{userId}")
    public UserDto update(@PathVariable long userId,
                          @RequestBody UpdateUserDto userDto) {
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

    @PostMapping("/users/{id}/avatar")
    public void uploadAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        System.out.println("File name: " + file.getOriginalFilename());
    }
}
























