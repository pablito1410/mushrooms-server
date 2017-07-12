package pl.polsl.mushrooms.application.commands.score;

import pl.polsl.mushrooms.application.commands.VoidCommand;

import javax.validation.constraints.NotNull;

/**
 * Created by pawel_zaqkxkn on 12.07.2017.
 */
public class DeleteScoreCommand implements VoidCommand {

    private String userName;

    @NotNull
    private Long id;

    public DeleteScoreCommand() { }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
