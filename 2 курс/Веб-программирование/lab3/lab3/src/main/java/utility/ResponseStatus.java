package utility;

public enum ResponseStatus {
    ERROR ("error"),
    SUCCESS ("success");

    private String status;
    ResponseStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return this.status;
    }
}
