import io.swagger.client.ApiException;
import io.swagger.client.model.Playlists;

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
        }
        else if (choice == 3){
            long currentTime = System.currentTimeMillis() / 1000;
            if (currentTime - start > 20 || isFirstCall) {
                try {
                    playlists = Main.usersApi.getPlaylistsInfo();
            System.out.println(playlists);
        } catch (ApiException apiException) {
            System.out.println(apiException.getResponseBody());
        }
            }
        }
    }
}
