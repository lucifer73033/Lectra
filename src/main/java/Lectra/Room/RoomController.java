package Lectra.Room;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

@RestController
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/room/create")
    ResponseEntity<?> createRoom(String name){
        Optional<String> uuid=roomService.createRoom(name);
        return uuid.isPresent()?ResponseEntity.status(200).body(uuid.get()):ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
