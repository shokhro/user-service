package school.faang.user_service.controller.mentorship;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.mentorship.CreateMentorshipRequestDto;
import school.faang.user_service.dto.mentorship.MentorshipRequestDto;
import school.faang.user_service.service.mentorship.MentorshipRequestServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentorship-requests")
public class MentorshipRequestController {

    private final MentorshipRequestServiceImpl mentorshipRequestService;

    @PostMapping
    public MentorshipRequestDto create(@RequestBody CreateMentorshipRequestDto mentorshipRequest) {
        return mentorshipRequestService.create(mentorshipRequest);
    }


}
















