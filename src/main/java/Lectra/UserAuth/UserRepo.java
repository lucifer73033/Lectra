package Lectra.UserAuth;

import Lectra.UserAuth.POJOs.LectraUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepo extends MongoRepository<LectraUser,String> {

    public List<String> findRolesById(String id);
}
