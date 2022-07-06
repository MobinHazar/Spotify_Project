import io.swagger.client.ApiException;
import io.swagger.client.auth.OAuth;
import io.swagger.client.model.AuthSignupBody;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Singup {
    public static void singupProcess() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter username");
        String username = input.next();
        System.out.println("Enter password");
        String password = input.next();
        boolean flag = true;
        while (flag) {
            Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
            Matcher m = p.matcher(password);
            if (m.find()) {
                flag = false;
            } else {
                System.out.println("Password must contain at least one lowercase letter, one uppercase letter, one digit and must be at least 8 characters long; please try again");
                System.out.println("Enter password");
                password = input.next();
            }
        }
        String token = "";
        try {
            AuthSignupBody authSignupBody = new AuthSignupBody();
            authSignupBody.setUsername(username);
            authSignupBody.setPassword(password);
            token = Main.authApi.signUp(authSignupBody).getToken();
            Main.currentUser = new User(username, password);
            System.out.println("You Successfully logged in");
            Main.currentUser.token = token;
            Main.defaultClient.setAccessToken(Main.currentUser.token);
            OAuth bearerAuth = (OAuth) Main.defaultClient.getAuthentication("bearerAuth");
            bearerAuth.setAccessToken(Main.currentUser.token);
            Menu.userMenuProcess();
            Main.currentUser = new User(username, password);
            Main.currentUser.token = token;
        } catch (ApiException apiException) {
            System.out.println("This username is already taken plz try another one");
            singupProcess();
        }
    }
}
