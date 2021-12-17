public final class Routes {

    // pages
    public String homePage = "https://news-feed.dunice-testing.com/";
    public String loginPage = "https://news-feed.dunice-testing.com/login" + System.getenv("INTERNSHIP_NGROK");
    public String registrationPage = "https://news-feed.dunice-testing.com/registration";

    // requests
    public String getNews = "https://news-feed.dunice-testing.com/api/v1/news?page=1&perPage=3";
    public String postLogin = "https://news-feed.dunice-testing.com/api/v1/auth/login";
    public String postRegistration = "https://news-feed.dunice-testing.com/api/v1/auth/register";
    public String deleteDeleteUser = "https://news-feed.dunice-testing.com/api/v1/user";
}
