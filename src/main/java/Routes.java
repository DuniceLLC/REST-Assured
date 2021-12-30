import lombok.Value;

@Value
public class Routes {

    // requests
    String login = System.getProperty("BASE_URL") + "auth/login";
    String registration = System.getProperty("BASE_URL") + "auth/register";
    String user = System.getProperty("BASE_URL") + "user";
    String news = System.getProperty("BASE_URL") + "news";
    String file = System.getProperty("BASE_URL") + "file/uploadFile";
    String getFile = System.getProperty("BASE_URL") + "file/";

}
