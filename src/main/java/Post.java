import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Post {
    String description;
    String image;
    String[] tags;
    String title;

    public Post(String description, String image, String[] tags, String title) {
        this.description = description;
        this.image = image;
        this.tags = tags;
        this.title = title;
    }
}
