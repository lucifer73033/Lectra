package Lectra;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@RestController
public class TestController {
    @GetMapping("/hello")
    String hello(){
        return "HEY";
    }

    @PostMapping("/test")
    String timeTest(@RequestParam Duration duration,@RequestParam Instant senderTime){
        String str=duration.toString()+"\t"+senderTime.toString();
        System.out.println(duration.get(ChronoUnit.SECONDS));
        return str;
    }
}
