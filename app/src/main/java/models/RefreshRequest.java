package models;

public class RefreshRequest {
    String refresh_token;

    public RefreshRequest(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}