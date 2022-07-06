import io.swagger.client.ApiException;
import io.swagger.client.api.PremiumUsersApi;
import io.swagger.client.model.Playlists;
import io.swagger.client.model.PlaylistsBody;
import io.swagger.client.model.Tracks;

import java.util.List;
import java.util.Scanner;

public class Menu {
    public static PremiumUsersApi premiumUsersApi;
    public static List<String> friends;
    public static Playlists friendPlaylists;
    public static List<String> friendsRequests;
    public static long start = 0;
    public static Tracks tracks;
    public static String allTracks = "";
    public static String normalTracks = "";
    public static boolean isFirstCall = true;

    public static void userMenuProcess() {
        Scanner input = new Scanner(System.in);
        System.out.println("1-Profile\n2-Tracks\n3-Playlists\n4-Upgrade to premium\n5-Logout");
        int choice = input.nextInt();
        if (choice == 1) {
            Profile.profile();
        } else if (choice == 2) {
            long currentTime = System.currentTimeMillis() / 1000;

                try {
                    if (currentTime - start > 20 || isFirstCall) {
                        start = System.currentTimeMillis() / 1000;
                        isFirstCall = false;
                        tracks = Main.usersApi.getTracksInfo();
                        for (io.swagger.client.model.Track track : tracks) {
                            if (!track.isIsPremium()) {
                                normalTracks += track.toString();
                            }
                        }
                    }
                    System.out.println(normalTracks);
                    userMenuProcess();
                } catch (ApiException apiException) {
                    isFirstCall = false;
                    System.out.println(apiException.getResponseBody());
                }
        } else if (choice == 3) {
            Playlist.playlistProcess();
        } else if (choice == 4) {
            try {
                Main.usersApi.upgradeToPremium();
                System.out.println("You have upgraded to premium");
                premiumUsersApi = new PremiumUsersApi(Main.defaultClient);
                premiumMenu();
            } catch (ApiException apiException) {
                System.out.println(apiException.getResponseBody());
                userMenuProcess();
            }
        } else if (choice == 5) {
            Main.welcome();
        } else {
            System.out.println("Invalid choice please try again");
            userMenuProcess();
        }
    }

    public static void premiumMenu() {
        Scanner input = new Scanner(System.in);
        System.out.println("1-Profile\n2-Tracks\n3-Playlists\n4-Friends\n5-Logout");
        int choice = input.nextInt();
        if (choice == 1) {
            Profile.profile();
        } else if (choice == 2) {
            long currentTime = System.currentTimeMillis() / 1000;
                try {
                    if (currentTime - start > 20 || isFirstCall) {
                        allTracks = Main.usersApi.getTracksInfo().toString();
                        start = System.currentTimeMillis() / 1000;
                        isFirstCall = false;
                    }
                    System.out.println(allTracks);
                    userMenuProcess();
                } catch (ApiException apiException) {
                    System.out.println(apiException.getResponseBody());
                    userMenuProcess();
                }
            } else if (choice == 3) {
            Playlist.playlistProcess();
        } else if (choice == 4) {
            friendProcess();
        } else if (choice == 5) {
            Main.welcome();
        } else {
            System.out.println("Invalid choice please try again");
            premiumMenu();
        }
    }

    public static void friendProcess() {
        Scanner input = new Scanner(System.in);
        System.out.println("1-List of friends\n2-Add friend\n3-Friends requests\n4-Friends playlists");
        int choice2 = input.nextInt();
        if (choice2 == 0) {
            premiumMenu();
        } else if (choice2 == 1) {
            long currentTime = System.currentTimeMillis() / 1000;
                try {
                    if (currentTime - start > 20 || isFirstCall) {
                        friends = premiumUsersApi.getFriends();
                        start = System.currentTimeMillis() / 1000;
                        isFirstCall = false;
                    }
                    System.out.println(friends);
                    friendProcess();
                } catch (ApiException apiException) {
                    System.out.println(apiException.getResponseBody());
                    friendProcess();
                }
            }
        else if (choice2 == 2) {
            try {
                System.out.println("Enter the username of the friend you want to add");
                String friendUsername = input.next();
                premiumUsersApi.addFriend(friendUsername);
                System.out.println("You have added " + friendUsername + " as a friend");
                friendProcess();
            } catch (ApiException apiException) {
            System.out.println(apiException.getResponseBody());
            friendProcess();
        }
        }
        else if (choice2 == 3) {
            long currentTime = System.currentTimeMillis() / 1000;
            try {
                if (currentTime - start > 20 || isFirstCall) {
                friendsRequests = premiumUsersApi.getFriendRequests();
                isFirstCall = false;
                start = System.currentTimeMillis() / 1000;
                }
                    System.out.println(friendsRequests);
                    System.out.println("Press 1 if you want to accept friend request or 0 to go back");
                    int income = input.nextInt();
                    if (income == 1) {
                        System.out.println("Enter friend username that you want to accept");
                        String userName = input.next();
                        premiumUsersApi.addFriend(userName);
                        System.out.println("Friend added");
                        friendProcess();
                    } else if (income == 0) {
                        friendProcess();
                    } else {
                        System.out.println("Invalid choice");
                        premiumMenu();
                    }
                } catch (ApiException apiException) {
                    System.out.println(apiException.getResponseBody());
                    friendProcess();
                }
        }
                else if (choice2 == 4) {
                long currentTime = System.currentTimeMillis() / 1000;
                try {
                        if (currentTime - start > 20 || isFirstCall) {
                            System.out.println("Enter friend username that you want to see playlists");
                            String userName = input.next();
                            isFirstCall = false;
                            start = System.currentTimeMillis() / 1000;
                            friendPlaylists = premiumUsersApi.getFriendPlaylists(userName);
                        }
                        else System.out.println("Sorry, you have to wait for 20 seconds to see new playlists");
                        System.out.println(friendPlaylists);
                        friendProcess();
                    } catch(ApiException apiException){
                        System.out.println(apiException.getResponseBody());
                        friendProcess();
                    }
                }else {
                System.out.println("Invalid choice please try again");
                premiumMenu();
            }
    }
}

