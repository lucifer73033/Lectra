package Lectra.Room;

import Lectra.Room.POJOs.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface RoomRepo extends MongoRepository<Room,UUID> {
}
