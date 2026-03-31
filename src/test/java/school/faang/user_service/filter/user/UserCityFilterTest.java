package school.faang.user_service.filter.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import school.faang.user_service.dto.user.SearchUserDto;
import school.faang.user_service.entity.user.User;

import java.util.List;
import java.util.stream.Stream;

public class UserCityFilterTest {

    private final UserCityFilter userCityFilter = new UserCityFilter();

    @Test
    public void testIsApplicable_whenCorrectDataShouldReturnBoolean() {
        SearchUserDto searchUserDto = new SearchUserDto();
        searchUserDto.setCity("Tashkent");
        boolean result = userCityFilter.isApplicable(searchUserDto);
        Assertions.assertTrue(result);
    }

    @Test
    public void testIsApplicable_whenCityIsNullShouldReturnFalse() {
        SearchUserDto searchUserDto = new SearchUserDto();
        boolean result = userCityFilter.isApplicable(searchUserDto);
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsApplicable_whenEmptyCityIsBlankShouldReturnFalse() {
        SearchUserDto searchUserDto = new SearchUserDto();
        searchUserDto.setCity("");
        boolean result = userCityFilter.isApplicable(searchUserDto);
        Assertions.assertTrue(result);
    }

    @Test
    public void testApply_whenCityMatchesShouldReturnFilteredUsers() {
        //Arrange
        SearchUserDto searchUserDto = new SearchUserDto();
        searchUserDto.setCity("Mexico");

        Stream<User> users = Stream.of(
                User.builder().city("Canada").build(),
                User.builder().city("Mexico").build()
        );
        //Act
        List<User> result = userCityFilter.apply(users, searchUserDto).toList();
        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Mexico", result.get(0).getCity());
    }

    @Test
    public void testApply_whenCityNoMatchesShouldReturnEmptyList() {
        //Arrange
        SearchUserDto searchUserDto = new SearchUserDto();
        searchUserDto.setCity("China");

        Stream<User> users = Stream.of(
                User.builder().city("Canada").build(),
                User.builder().city("Mexico").build()
        );
        //Act
        List<User> result = userCityFilter.apply(users, searchUserDto).toList();
        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }


}
