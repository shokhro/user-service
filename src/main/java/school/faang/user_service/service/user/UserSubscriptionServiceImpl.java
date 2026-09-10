package school.faang.user_service.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.faang.user_service.repository.user.SubscriptionRepository;

@Service
@RequiredArgsConstructor
public class UserSubscriptionServiceImpl {

    private final SubscriptionRepository subscriptionRepository;

    public void followUser(long followerId, long followeeId) {
        subscriptionRepository.followUser(followerId, followeeId);
    }

}
