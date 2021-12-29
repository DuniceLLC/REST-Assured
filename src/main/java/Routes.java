public class Routes {
    private final static String BASE_URL = "https://news-feed.dunice-testing.com/api/v1/";

    // pages
    public String loginPage = "https://news-feed.dunice-testing.com/login" + System.getenv("INTERNSHIP_NGROK");

    // requests
    public static String login = BASE_URL + "auth/login";
    public static String registration = BASE_URL + "auth/register";
    public static String user = BASE_URL + "user";
    public static String news = BASE_URL + "news";
    public static String file = BASE_URL + "file/uploadFile";
    public static String getFile = BASE_URL + "file/";
}
