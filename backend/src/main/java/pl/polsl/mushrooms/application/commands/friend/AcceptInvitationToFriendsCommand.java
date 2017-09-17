package pl.polsl.mushrooms.application.commands.friend;

import pl.polsl.mushrooms.application.commands.VoidCommand;

import javax.validation.constraints.NotNull;

/**
 * Created by pawel_zaqkxkn on 28.06.2017.
 */
public class AcceptInvitationToFriendsCommand implements VoidCommand {

    private String userName;

    @NotNull
    private Long friendId;

    protected AcceptInvitationToFriendsCommand() { }

    public Long getFriendId() {
        return friendId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    void setFriendId(final Long friendId) {
        this.friendId = friendId;
    }
}
