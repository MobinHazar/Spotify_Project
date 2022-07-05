import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.Configuration;
import io.swagger.client.api.AuthApi;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.api.UsersApi;
import io.swagger.client.auth.ApiKeyAuth;
import io.swagger.client.auth.OAuth;
import io.swagger.client.model.AuthLoginBody;
import io.swagger.client.model.AuthSignupBody;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static final String MY_API_KEY = "5f718eaba2e175cde156d51671bf000969d0bad1";
    public static ApiClient defaultClient;
    public static DefaultApi defaultApi;
    public static AuthApi authApi;
    public static UsersApi usersApi;
    public static User currentUser;
    public static long start = 0;
    public static String allTracks;
    public static boolean isFirstCall = true;

    public static void authAPIKey() {
        defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth ApiKeyAuth = (ApiKeyAuth) defaultClient.getAuthentication("ApiKeyAuth");
        ApiKeyAuth.setApiKey(MY_API_KEY);
        defaultApi = new DefaultApi();
        authApi = new AuthApi(defaultClient);
        usersApi = new UsersApi(defaultClient);
    }

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
            token = (authApi.login(authLoginBody).getToken());
            currentUser = new User(username,password);
            System.out.println("You Successfully logged in");
            currentUser.token = token;
            defaultClient.setAccessToken(currentUser.token);
            OAuth bearerAuth = (OAuth) defaultClient.getAuthentication("bearerAuth");
            bearerAuth.setAccessToken(currentUser.token);

            userMenuProcess();
            currentUser = new User(username, password);
            currentUser.token = token;
        } catch (ApiException apiException) {
            String errorResponse = apiException.getResponseBody();
            System.out.println(errorResponse);
            if (errorResponse.contains("invalid username or password")) {
                loginProcess();
            }
        }
    }
    public static void singupProcess(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter username");
        String username = input.next();
        System.out.println("Enter password");
        String password = input.next();
        boolean flag = true;
        while (flag){
            Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
            Matcher m = p.matcher(password);
            if(m.find()){
                flag = false;
            }
            else{
                System.out.println("Password must contain at least one letter and one number");
                System.out.println("Enter password");
                password = input.next();
            }
        }
        String token = "";
        try {
            AuthSignupBody authSignupBody = new AuthSignupBody();
            authSignupBody.setUsername(username);
            authSignupBody.setPassword(password);
            token = authApi.signUp(authSignupBody).getToken();
            currentUser = new User(username,password);
            System.out.println("You Successfully logged in");
            currentUser.token = token;
            defaultClient.setAccessToken(currentUser.token);
            OAuth bearerAuth = (OAuth) defaultClient.getAuthentication("bearerAuth");
            bearerAuth.setAccessToken(currentUser.token);
            userMenuProcess();
            currentUser = new User(username, password);
            currentUser.token = token;
        } catch (ApiException apiException) {
            System.out.println("This username is already taken plz try another one");
            singupProcess();
        }
    }

    public static void userMenuProcess() {
        Scanner input = new Scanner(System.in);
        System.out.println("1-Profile\n2-Tracks");
        int choice = input.nextInt();
        if (choice == 1) {
            long currentTime = System.currentTimeMillis() / 1000;
            if (currentTime - start > 20 || isFirstCall) {
                try {
                    System.out.println(usersApi.getProfileInfo().toString());
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
                    allTracks = usersApi.getTracksInfo().toString();
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


    public static void main(String[] args) {
        authAPIKey();
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome\n1-Login\n2-Signup");

        int choice = input.nextInt();
        if (choice == 1) loginProcess();
        else if (choice == 2) singupProcess();

    }
}
