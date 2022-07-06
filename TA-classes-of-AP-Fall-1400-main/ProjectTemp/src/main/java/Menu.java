import io.swagger.client.ApiException;
import io.swagger.client.model.Playlists;
import io.swagger.client.model.PlaylistsBody;

import java.util.Scanner;

public class Menu {
    public static long start = 0;
    public static String allTracks;
    public static Playlists playlists;
    public static boolean isFirstCall = true;
    public static String profile="";
    public static void userMenuProcess() {
        Scanner input = new Scanner(System.in);
        System.out.println("1-Profile\n2-Tracks\n3-Playlists\n4-Upgrade to premium\n5-Logout");
        int choice = input.nextInt();
        if (choice == 1) {
            long currentTime = System.currentTimeMillis() / 1000;
            if (currentTime - start > 20 || isFirstCall) {
                try {
                    profile = Main.usersApi.getProfileInfo().toString();
                    System.out.println(profile);
                    start = System.currentTimeMillis() / 1000;
                    System.out.println("Enter 0 at any page to go back");
                    int exit = input.nextInt();
                    while (exit != 0) {
                        exit = input.nextInt();
                    }
                    isFirstCall = false;
                    userMenuProcess();
                } catch (ApiException apiException) {
                    System.out.println(apiException.getResponseBody());
                    isFirstCall = false;
                }
            } else {
                //cached data
                System.out.println(profile);
            }
        } else if (choice == 2) {
            long currentTime = System.currentTimeMillis() / 1000;
            if (currentTime - start > 20 || isFirstCall) {
                try {
                    allTracks = Main.usersApi.getTracksInfo().toString();
                    System.out.println(allTracks);
                    start = System.currentTimeMillis() / 1000;
                    isFirstCall = false;
                    System.out.println("Enter 0 at any page to go back");
                    int exit = input.nextInt();
                    while (exit != 0) {
                        exit = input.nextInt();
                    }
                    userMenuProcess();
                } catch (ApiException apiException) {
                    isFirstCall = false;
                    System.out.println(apiException.getResponseBody());
                }
            } else {
                System.out.println(allTracks);
            }
        } else if (choice == 3) {
            playlistProcess();
        }
    }
    public static void playlistProcess(){
        Scanner input = new Scanner(System.in);
        long currentTime = System.currentTimeMillis() / 1000;
        if (currentTime - start > 20 || isFirstCall) {
            try {
                playlists = Main.usersApi.getPlaylistsInfo();
                System.out.println(playlists);
                start = System.currentTimeMillis() / 1000;
                System.out.println("Enter 0 at any page to go back\n1- Create Playlist\n2- Add Track to Playlist\n3- Remove Track from Playlist\n4- Delete Playlist");
                int choice2 = input.nextInt();
                if (choice2 == 0){userMenuProcess();}
                else if (choice2 == 1) {
                        PlaylistsBody playlistsBody = new PlaylistsBody();
                        System.out.println("Enter Playlist Name");
                        String name = input.next();
                        playlistsBody.setName(name);
                        int id = Main.usersApi.createPlaylist(playlistsBody).getId();
                        System.out.println("Playlist created successfully with id: " + id);
                        playlistProcess();
                } else if (choice2 == 2) {
                        System.out.println("Enter Playlist ID");
                        int playlistId = input.nextInt();
                        System.out.println("Enter Track ID");
                        String trackId = input.next();
                        Main.usersApi.addTrackToPlaylist(playlistId, trackId);
                    System.out.println("Track added successfully");
                        playlistProcess();

                } else if (choice2 == 3) {
                    System.out.println("Enter Playlist ID");
                    int playlistId = input.nextInt();
                    System.out.println("Enter Track ID");
                    String trackId = input.next();
                    Main.usersApi.removeTrackFromPlaylist(playlistId, trackId);
                    System.out.println("Track removed successfully");
                    playlistProcess();
                } else if (choice2 == 4) {
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
        } else {
            System.out.println(playlists);
        }

    }
}

