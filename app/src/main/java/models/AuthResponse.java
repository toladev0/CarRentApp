package models;

public class AuthResponse {
    public String access_token;
    public String refresh_token;
    public User user;

    public static class User {
        public String id;
        public String email;
        public UserMetadata user_metadata;
    }

    public static class UserMetadata {
        public String name;
    }
}
