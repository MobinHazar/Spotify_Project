import io.swagger.client.model.Track;

public class Playlist {
    public String id;
    public String name;
    public Track[] tracks;

    public Playlist(String id, String name, Track[] tracks) {
        this.id = id;
        this.name = name;
        this.tracks = tracks;
    }
}