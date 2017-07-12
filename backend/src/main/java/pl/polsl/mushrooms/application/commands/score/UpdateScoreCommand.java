package pl.polsl.mushrooms.application.commands.score;

import pl.polsl.mushrooms.application.commands.VoidCommand;

import javax.validation.constraints.NotNull;

/**
 * Created by pawel_zaqkxkn on 12.07.2017.
 */
public class UpdateScoreCommand implements VoidCommand {

    private String userName;

    @NotNull
    private Long id;

    @NotNull
    private Integer value;

    protected UpdateScoreCommand() { }

    public String getUserName() {
        return userName;
    }

    public Long getId() {
        return id;
    }

    public Integer getValue() {
        return value;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
