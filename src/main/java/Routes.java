public class Routes {
    private final static String baseURL = "https://news-feed.dunice-testing.com/api/v1/";

    // pages
    public String loginPage = "https://news-feed.dunice-testing.com/login" + System.getenv("INTERNSHIP_NGROK");

    // requests
    public static String login = baseURL + "auth/login";
    public static String registration = baseURL + "auth/register";
    public static String user = baseURL + "user";
    public static String news = baseURL + "news";
    public static String file = baseURL + "file/uploadFile";
    public static String getFile = baseURL + "file/";
}
