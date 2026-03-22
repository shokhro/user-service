package school.faang.user_service.service.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import school.faang.user_service.dto.user.SearchUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.user.User;
import school.faang.user_service.filter.user.UserFilter;
import school.faang.user_service.mapper.UserMapper;
import school.faang.user_service.repository.user.UserRepository;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Spy
    private UserMapper userMapper;
    @Mock
    private UserFilter userFilter1;
    @Mock
    private UserFilter userFilter2;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(
                userRepository,
                null,
                userMapper,
                null,
                List.of(userFilter1, userFilter2));
    }

    @Test
    public void testGetUsers() {
        SearchUserDto searchUserDto = new SearchUserDto();

        when(userRepository.findAll())
                .thenReturn(List.of(
                        User.builder().city("Canada").experience(8).build(),
                        User.builder().city("Italia").experience(2).build(),
                        User.builder().city("Francia").experience(8).build()));

        when(userFilter1.isApplicable(any())).thenReturn(true);
        when(userFilter2.isApplicable(any())).thenReturn(true);

        when(userFilter1.apply(any(), any())).
                thenAnswer((Answer<Stream<User>>) invocation -> {
                    Stream<User> stream = invocation.getArgument(0);
                    return stream.filter(user -> user.getExperience().equals(8));
                });

        when(userFilter2.apply(any(), any())).
                thenAnswer((Answer<Stream<User>>) invocation -> {
                    Stream<User> stream = invocation.getArgument(0);
                    return stream.filter(user -> user.getCity().equals("Francia"));
                });

        List<UserDto> users = userService.getUsers(searchUserDto);

        Assertions.assertEquals(1, users.size());
    }
}


















