package pl.polsl.mushrooms.application.commands.comment;

import pl.polsl.mushrooms.application.commands.VoidCommand;

import javax.validation.constraints.NotNull;

public class DeleteCommentCommand implements VoidCommand {

    private String userName;

    @NotNull
    private long id;

    protected DeleteCommentCommand() { }

    public DeleteCommentCommand(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}