package Lectra.WebScoket.POJOs;

import java.time.Instant;

public class VideoSyncDTO {
    private boolean state;
    private double timestamp;
    private Instant senderTime;
    private String userUUID;

    public VideoSyncDTO() {
    }

    public VideoSyncDTO(boolean state, double timestamp, Instant senderTime, String userUUID) {
        this.state = state;
        this.timestamp = timestamp;
        this.senderTime = senderTime;
        this.userUUID = userUUID;
    }

    public VideoSyncDTO(VideoSyncDTO videoSyncDTO){
        this.senderTime=videoSyncDTO.getSenderTime();
        this.timestamp=videoSyncDTO.getTimestamp();
        this.state=videoSyncDTO.isState();
        this.userUUID=videoSyncDTO.getUserUUID();
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public Instant getSenderTime() {
        return senderTime;
    }

    public void setSenderTime(Instant senderTime) {
        this.senderTime = senderTime;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }
}
