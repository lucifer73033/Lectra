package Lectra.WebScoket;

import Lectra.WebScoket.POJOs.MessageDTO;
import Lectra.WebScoket.POJOs.VideoSyncDTO;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.sql.SQLOutput;

@Controller
public class WebSocketController {

    @MessageMapping("/room/chat/{roomId}")
    @SendTo("/topic/room/chat/{roomId}")
    public MessageDTO message(@DestinationVariable String roomId, MessageDTO message){
        return new MessageDTO(message);
    }

    @MessageMapping("/room/video/{roomId}")
    @SendTo("/topic/room/video/{roomId}")
    public VideoSyncDTO sync(@DestinationVariable String roomId, VideoSyncDTO videoSyncDTO){
//        System.out.println("INTERCEPTED");
//        System.out.println(videoSyncDTO.getSenderTime());
//        System.out.println(videoSyncDTO.getTimestamp());
//        System.out.println(videoSyncDTO.isState());
        return new VideoSyncDTO(videoSyncDTO);
    }

}
