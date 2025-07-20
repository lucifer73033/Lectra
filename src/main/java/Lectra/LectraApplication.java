package Lectra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//TODO
// Write code for creating room, saving room and adding roles to each room.✅
// write code for sending receiving messages in said room✅
// write logic for sending state,seek time and retreiving correct seek time in case of delay✅
// write code for sending receiving play, pause,seek video events✅
// uploading downloading videos on s3✅
// handling large video files when uploading to s3
// creating vault type of feature for storing keys
// Doodle implementation
// AI features
// community features
// write the miscellaneous code for giving chat history, list of rooms and such features to make the site complete
//

@SpringBootApplication
public class LectraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LectraApplication.class, args);
	}

}
