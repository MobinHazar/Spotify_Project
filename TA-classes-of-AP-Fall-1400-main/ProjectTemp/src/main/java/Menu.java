import io.swagger.client.ApiException;
import io.swagger.client.api.PremiumUsersApi;
import io.swagger.client.model.Playlists;
import io.swagger.client.model.PlaylistsBody;
import io.swagger.client.model.Tracks;

import java.util.Scanner;

public class Menu {
    public static PremiumUsersApi premiumUsersApi;
    public static long start = 0;
    public static Tracks tracks;
    public static String allTracks="";
    public static boolean isFirstCall = true;
    public static void userMenuProcess() {
        Scanner input = new Scanner(System.in);
        System.out.println("1-Profile\n2-Tracks\n3-Playlists\n4-Upgrade to premium\n5-Logout");
        int choice = input.nextInt();
        if (choice == 1) {
            Profile.profile();
        } else if (choice == 2) {
            long currentTime = System.currentTimeMillis() / 1000;
            if (currentTime - start > 20 || isFirstCall) {
                try {
                    tracks = Main.usersApi.getTracksInfo();
                    for (io.swagger.client.model.Track track : tracks) {
                        if (!track.isIsPremium()) {
                            allTracks += track.toString();
                        }
                    }
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
            Playlist.playlistProcess();
        }
        else if(choice == 4){
            try {
                Main.usersApi.upgradeToPremium();
                System.out.println("You have upgraded to premium");
                premiumUsersApi = new PremiumUsersApi(Main.defaultClient);
                premiumMenu();
            } catch (ApiException apiException) {
                System.out.println(apiException.getResponseBody());
                userMenuProcess();
            }
        }
    }
    public static void premiumMenu(){
        Scanner input = new Scanner(System.in);
        System.out.println("1-Profile\n2-Tracks\n3-Playlists\n4-Friends\n5-Logout");
        int choice = input.nextInt();
        if (choice == 1) {
            Profile.profile();
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
            Playlist.playlistProcess();
        }
        else if(choice == 4){
            System.out.println("1-List of friends\n2-Friends requests\n3-Add friends\n4-Friends playlists");
            int choice2 = input.nextInt();
            if (choice2==0){
                premiumMenu();
            }
            else if(choice2==1){
                try {
                    System.out.println(premiumUsersApi.getFriends());
                } catch (ApiException apiException) {
                    System.out.println(apiException.getResponseBody());
                premiumMenu();
                }
            }
            else if(choice2==2){
                try {
                    System.out.println(premiumUsersApi.getFriendRequests());
                    premiumMenu();
                } catch (ApiException apiException) {
                    System.out.println(apiException.getResponseBody());
                    premiumMenu();
                }
            }
            else if(choice2==3){
                try {
                    System.out.println("Enter friend username that you want to accept");
                    String userName = input.next();
                    System.out.println(premiumUsersApi.addFriend(userName));
                    System.out.println("Friend added");
                    System.out.println("Enter 0 at any page to go back");
                    int exit = input.nextInt();
                    while (exit != 0) {
                        exit = input.nextInt();
                    }
                    premiumMenu();
                } catch (ApiException apiException) {
                    System.out.println(apiException.getResponseBody());
                    premiumMenu();
                }
            }
            else if(choice2==4){
                try {
                    System.out.println("Enter friend username that you want to see playlists");
                    String userName = input.next();
                    System.out.println(premiumUsersApi.getFriendPlaylists(userName));
                    int exit = input.nextInt();
                    while (exit != 0) {
                        exit = input.nextInt();
                    }
                    premiumMenu();
                } catch (ApiException apiException) {
                    System.out.println(apiException.getResponseBody());
                    premiumMenu();
                }
            }
        }

    }

}

