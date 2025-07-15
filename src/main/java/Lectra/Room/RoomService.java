package Lectra.Room;

import Lectra.Room.POJOs.Room;
import Lectra.UserAuth.POJOs.LectraUser;
import Lectra.UserAuth.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class RoomService {
    private final RoomRepo roomRepo;
//    private final UserRepo userRepo;

    public RoomService(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
//        this.userRepo = userRepo;
    }
    Optional<String> createRoom(String name){
        UUID uuid= UUID.randomUUID();
        roomRepo.save(new Room(uuid.toString(),name, SecurityContextHolder.getContext().getAuthentication().getName(), List.of(SecurityContextHolder.getContext().getAuthentication().getName()), new Date()));
        return Optional.of(uuid.toString());
    }
}
