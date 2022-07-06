import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.Configuration;
import io.swagger.client.api.AuthApi;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.api.UsersApi;
import io.swagger.client.auth.ApiKeyAuth;
import io.swagger.client.auth.OAuth;
import io.swagger.client.model.AuthLoginBody;
import java.util.Scanner;


public class Main {
    public static final String MY_API_KEY = "5f718eaba2e175cde156d51671bf000969d0bad1";
    public static ApiClient defaultClient;
    public static DefaultApi defaultApi;
    public static AuthApi authApi;
    public static UsersApi usersApi;
    public static User currentUser;

    public static void authAPIKey() {
        defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth ApiKeyAuth = (ApiKeyAuth) defaultClient.getAuthentication("ApiKeyAuth");
        ApiKeyAuth.setApiKey(MY_API_KEY);
        defaultApi = new DefaultApi();
        authApi = new AuthApi(defaultClient);
        usersApi = new UsersApi(defaultClient);
    }

    public static void main(String[] args) {
        authAPIKey();
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome\n1-Login\n2-Signup");

        int choice = input.nextInt();
        if (choice == 1) Login.loginProcess();
        else if (choice == 2) Singup.singupProcess();

    }
}
