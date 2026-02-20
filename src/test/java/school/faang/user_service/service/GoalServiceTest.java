package school.faang.user_service.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import school.faang.user_service.entity.goal.Goal;
import school.faang.user_service.repository.goal.GoalRepository;
import school.faang.user_service.service.goal.GoalService;

public class GoalServiceTest {

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private GoalService goalService;

    @Test
    public void testNullTitleIsInvalid(){
        Assert.assertThrows(IllegalAccessError.class,
                () -> goalService.save(null, "description", null)
        );
    }
    @Test
    public void testEmptyTitleIsInvalid(){
        Assert.assertThrows(IllegalAccessError.class,
                () -> goalService.save("   ", "description", null)
        );
    }

    @Test
    public void testGoalIsSaved(){
        Mockito.when(goalRepository.create(Mockito.anyString(), Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(new Goal());
        goalRepository.create("title", "description", null);
        Mockito.verify(goalRepository, Mockito.times(1))
                .create(Mockito.anyString(), Mockito.anyString(), Mockito.anyLong());
    }
}
