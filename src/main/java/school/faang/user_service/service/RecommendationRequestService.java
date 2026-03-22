package school.faang.user_service.service;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class RecommendationRequestDto{}

public class RecommendationRequestService {

    private void doSomethingImportant(){
        List<Circle> circles = new ArrayList<>();
        Map<String, Circle> map = circles.stream()
                .collect(Collectors.toMap(
                        circle -> circle.getColor(),
                        circle -> circle
                ));
    }

    @Getter
    private static class Circle {
        private List<Square> square;
        private String color;
    }

    private static class Square{
        private String color;
    }

    public void doSomething(RecommendationRequestDto req){
        System.out.println(req);
    }
}
