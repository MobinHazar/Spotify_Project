public class User {
    public String username;
    public String password;
    public Playlist[] playlists;
    public String token;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
public class PremiumUser extends User {
    public String[] friends;
    public PremiumUser(String username, String password) {
        super(username, password);
    }
}

