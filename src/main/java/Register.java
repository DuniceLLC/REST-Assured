import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Register {
    String avatar;
    String email;
    String name;
    String password;
    String role;

    public Register(String avatar, String email, String name, String password, String role) {
        this.avatar = avatar;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }
}



