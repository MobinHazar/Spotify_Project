import io.swagger.client.ApiException;

import java.util.Scanner;

public class Profile {
    public static String profile="";
    public static long currentTime;
    public static void profile() {
        Scanner input = new Scanner(System.in);
         currentTime = System.currentTimeMillis() / 1000;
        if (currentTime - Menu.start > 20 || Menu.isFirstCall) {
            try {
                profile = Main.usersApi.getProfileInfo().toString();
                System.out.println(profile);
                Menu.start = System.currentTimeMillis() / 1000;
                System.out.println("Enter 0 at any page to go back");
                int exit = input.nextInt();
                while (exit != 0) {
                    exit = input.nextInt();
                }
                Menu.isFirstCall = false;
                Menu.userMenuProcess();
            } catch (ApiException apiException) {
                System.out.println(apiException.getResponseBody());
                Menu.isFirstCall = false;
            }
        } else {
            //cached data
            System.out.println(profile);
        }
         currentTime = System.currentTimeMillis() / 1000;
        if (currentTime - Menu.start > 20 || Menu.isFirstCall) {
            try {
                profile = Main.usersApi.getProfileInfo().toString();
                System.out.println(profile);
                Menu.start = System.currentTimeMillis() / 1000;
                System.out.println("Enter 0 at any page to go back");
                int exit = input.nextInt();
                while (exit != 0) {
                    exit = input.nextInt();
                }
                Menu.isFirstCall = false;
                Menu.userMenuProcess();
            } catch (ApiException apiException) {
                System.out.println(apiException.getResponseBody());
                Menu.isFirstCall = false;
            }
        } else {
            //cached data
            System.out.println(profile);
        }
    }
}