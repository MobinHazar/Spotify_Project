import io.swagger.client.ApiException;
import io.swagger.client.model.Playlists;
import io.swagger.client.model.PlaylistsBody;
import io.swagger.client.model.Track;

import java.util.Scanner;

public class Playlist {
    public static Playlists playlists;
    public static void playlistProcess(){
        Scanner input = new Scanner(System.in);
        long currentTime = System.currentTimeMillis() / 1000;
            try {
                if (currentTime - Menu.start > 20 || Menu.isFirstCall) {
                    playlists = Main.usersApi.getPlaylistsInfo();
                }
                System.out.println(playlists);
                Menu.start = System.currentTimeMillis() / 1000;
                System.out.println("Enter 0 at any page to go back\n1- Create Playlist\n2- Add Track to Playlist\n3- Remove Track from Playlist\n4- Delete Playlist");
                int choice = input.nextInt();
                if (choice == 0){
                    Menu.userMenuProcess();}
                else if (choice == 1) {
                    PlaylistsBody playlistsBody = new PlaylistsBody();
                    System.out.println("Enter Playlist Name");
                    String name = input.next();
                    playlistsBody.setName(name);
                    int id = Main.usersApi.createPlaylist(playlistsBody).getId();
                    System.out.println("Playlist created successfully with id: " + id);
                    playlistProcess();
                } else if (choice == 2) {
                    System.out.println("Enter Playlist ID");
                    int playlistId = input.nextInt();
                    System.out.println("Enter Track ID");
                    String trackId = input.next();
                    Main.usersApi.addTrackToPlaylist(playlistId, trackId);
                    System.out.println("Track added successfully");
                    playlistProcess();

                } else if (choice == 3) {
                    System.out.println("Enter Playlist ID");
                    int playlistId = input.nextInt();
                    System.out.println("Enter Track ID");
                    String trackId = input.next();
                    Main.usersApi.removeTrackFromPlaylist(playlistId, trackId);
                    System.out.println("Track removed successfully");
                    playlistProcess();
                } else if (choice == 4) {
                    System.out.println("Enter Playlist ID");
                    int playlistId = input.nextInt();
                    Main.usersApi.deletePlaylist(playlistId);
                    System.out.println("Playlist deleted successfully");
                    playlistProcess();
                }
            } catch (ApiException apiException) {
                System.out.println(apiException.getResponseBody());
                playlistProcess();
            }
        }
    }