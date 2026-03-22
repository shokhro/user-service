package school.faang.user_service.service.goal;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.repository.goal.GoalRepository;

@ExtendWith(MockitoExtension.class)
public class GoalServiceTest {

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private GoalService goalService;

    @Test
    public void testNullTitleIsInvalid(){
        Assert.assertThrows(
                IllegalArgumentException.class,
                () -> goalService.save(null, "description", null)
        );
    }

    @Test
    public void testEmptyTitleIsInvalid(){
        Assert.assertThrows(
                IllegalArgumentException.class,
                () -> goalService.save("   ", "description", null)
        );
    }

    @Test
    public void testSavedGoal(){
        goalService.save("title", "description", null);
        Mockito.verify(goalRepository, Mockito.times(1))
                .create("title", "description", null);
    }


}
