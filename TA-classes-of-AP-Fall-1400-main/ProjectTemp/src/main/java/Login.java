import io.swagger.client.ApiException;
import io.swagger.client.api.PremiumUsersApi;
import io.swagger.client.auth.OAuth;
import io.swagger.client.model.AuthLoginBody;

import java.util.Scanner;

public class Login {
    public static void loginProcess() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter username");
        String username = input.next();
        System.out.println("Enter password");
        String password = input.next();

        String token = "";
        try {
            AuthLoginBody authLoginBody = new AuthLoginBody();
            authLoginBody.setUsername(username);
            authLoginBody.setPassword(password);
            token = (Main.authApi.login(authLoginBody).getToken());
            Main.currentUser = new User(username,password);
            System.out.println("You Successfully logged in");
            Main.currentUser.token = token;
            Main.defaultClient.setAccessToken(Main.currentUser.token);
            OAuth bearerAuth = (OAuth) Main.defaultClient.getAuthentication("bearerAuth");
            bearerAuth.setAccessToken(Main.currentUser.token);
            if(Main.usersApi.getProfileInfo().toString().contains("premiumUntil: null")) {
                Menu.userMenuProcess();
            }
            else {
                Menu.premiumUsersApi = new PremiumUsersApi(Main.defaultClient);
                Menu.premiumMenu();
            }
            Main.currentUser = new User(username, password);
            Main.currentUser.token = token;
        } catch (ApiException apiException) {
            String errorResponse = apiException.getResponseBody();
            System.out.println(errorResponse);
            if (errorResponse.contains("invalid username or password")) {
                loginProcess();
            }
        }
    }

}
