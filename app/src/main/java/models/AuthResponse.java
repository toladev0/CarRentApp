package models;

public class AuthResponse {
    public String access_token;
    public String refresh_token;
    public User user;

    public static class User {
        public String id;
        public String name;
        public String email;
    }
}
