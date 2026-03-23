package school.faang.user_service.controller.mentorship;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.service.mentorship.MentorshipService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mentorship")
public class MentorshipController {

    private final MentorshipService mentorshipService;

    @PostMapping("/add/{mentorId}/{menteeId}")
    public void addMentorship(@PathVariable long mentorId,
                              @PathVariable long menteeId) {
        mentorshipService.addMentorship(mentorId, menteeId);
    }

    @GetMapping("/mentees/{userId}")
    public List<UserDto> getMentees(@PathVariable long userId) {
        return mentorshipService.getMentees(userId);
    }

    @GetMapping("/mentors/{userId}")
    public List<UserDto> getMentors(@PathVariable long userId) {
        return mentorshipService.getMentors(userId);
    }

    @DeleteMapping("/{menteeId}/{mentorId}")
    public void deleteMentorship(@PathVariable long menteeId,
                                 @PathVariable long mentorId) {
        mentorshipService.deleteMentorship(menteeId, mentorId);
    }
}
