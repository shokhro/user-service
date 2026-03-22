package school.faang.user_service.controller.mentorship;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.service.mentorship.MentorshipServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mentorship")
public class MentorshipController {

    private final MentorshipServiceImpl mentorshipService;

    public void addMentorship(long mentorId, long menteeId) {
        mentorshipService.addMentorship(mentorId, menteeId);
    }
}
