package pl.polsl.mushrooms.application.dao;

import pl.polsl.mushrooms.application.model.User;
import pl.polsl.mushrooms.application.model.UsersUsers;
import pl.polsl.mushrooms.application.model.UsersUsersId;

/**
 * Created by pawel_zaqkxkn on 26.03.2017.
 */
public interface UserDao {
    User save(User user);

    User findOne(long id);

    User findUserByEmail(String email);

    void delete(long id);

    User findOneByUsername(String username);

    UsersUsers findRelationship(UsersUsersId usersUsersId);
}
