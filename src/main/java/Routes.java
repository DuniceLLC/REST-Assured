
public final class Routes {

    // pages
    public String homePage = "https://news-feed.dunice-testing.com/";
    public String loginPage = "https://news-feed.dunice-testing.com/login" + System.getenv("INTERNSHIP_NGROK");
    public String registrationPage = "https://news-feed.dunice-testing.com/registration";

    // requests
    public String getNews = "https://news-feed.dunice-testing.com/api/v1/news?page=1&perPage=3";
    public static String login = "https://news-feed.dunice-testing.com/api/v1/auth/login";
    public static String registration = "https://news-feed.dunice-testing.com/api/v1/auth/register";
    public static String user = "https://news-feed.dunice-testing.com/api/v1/user";
    public static String news = "https://news-feed.dunice-testing.com/api/v1/news";
    public static String file = "https://news-feed.dunice-testing.com/api/v1/file/uploadFile";
    public static String getFile = "https://news-feed.dunice-testing.com/api/v1/file/";
}
