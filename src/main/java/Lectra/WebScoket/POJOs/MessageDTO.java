package Lectra.WebScoket.POJOs;

public class MessageDTO {
    private String purpose;
    private String payload;
    private String userUUID;

    public MessageDTO(String purpose, String payload, String userUUID) {
        this.purpose = purpose;
        this.payload = payload;
        this.userUUID = userUUID;

    }

    public MessageDTO(MessageDTO messageDTO) {
        this.payload=messageDTO.getPayload();
        this.purpose= messageDTO.getPurpose();
        this.userUUID=messageDTO.getUserUUID();
    }

    public MessageDTO() {
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }
}
