import io.swagger.client.ApiException;

import java.util.Scanner;

public class Menu {
    public static long start = 0;
    public static String allTracks;
    public static boolean isFirstCall = true;
    public static void userMenuProcess() {
        Scanner input = new Scanner(System.in);
        System.out.println("1-Profile\n2-Tracks");
        int choice = input.nextInt();
        if (choice == 1) {
            long currentTime = System.currentTimeMillis() / 1000;
            if (currentTime - start > 20 || isFirstCall) {
                try {
                    System.out.println(Main.usersApi.getProfileInfo().toString());
                    start = System.currentTimeMillis() / 1000;
                    int exit = input.nextInt();
                    isFirstCall = false;
                    userMenuProcess();
                } catch (ApiException apiException) {
                    System.out.println(apiException.getResponseBody());
                    isFirstCall = false;
                }
            } else {
                //cached data
                System.out.println();
            }
        } else if (choice == 2) {
            long currentTime = System.currentTimeMillis() / 1000;
            if (currentTime - start > 20) {
                try {
                    allTracks = Main.usersApi.getTracksInfo().toString();
                    System.out.println(allTracks);
                    start = System.currentTimeMillis() / 1000;
                } catch (ApiException apiException) {
                    System.out.println(apiException.getResponseBody());
                }
            } else {
                System.out.println(allTracks);
            }
        }
    }
}
