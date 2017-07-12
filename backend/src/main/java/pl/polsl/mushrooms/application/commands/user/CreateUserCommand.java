package pl.polsl.mushrooms.application.commands.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import pl.polsl.mushrooms.application.commands.ReturningCommand;
import pl.polsl.mushrooms.application.enums.Gender;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by pawel_zaqkxkn on 26.03.2017.
 */
public class CreateUserCommand implements ReturningCommand<Long> {

    private String userName;

    @NotNull
    protected String username;

    @NotNull
    protected String email;

    @NotNull
    protected String password;

    private String firstName;
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date birthDate;

    private Gender gender;

    private CreateUserCommand() { }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
