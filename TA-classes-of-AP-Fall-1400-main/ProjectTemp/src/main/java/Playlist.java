import io.swagger.client.model.Track;

public class Playlist {
    public String name;
    public Track[] tracks;

    public Playlist(String name, Track[] tracks) {
        this.name = name;
        this.tracks = tracks;
    }
}