package pl.polsl.mushrooms.application.services;

import pl.polsl.mushrooms.application.commands.score.AddScoreCommand;
import pl.polsl.mushrooms.application.commands.score.DeleteScoreCommand;
import pl.polsl.mushrooms.application.commands.score.UpdateScoreCommand;
import pl.polsl.mushrooms.application.dao.DiscoveryDao;
import pl.polsl.mushrooms.application.dao.ScoreDao;
import pl.polsl.mushrooms.application.dao.UserDao;
import pl.polsl.mushrooms.application.model.Discovery;
import pl.polsl.mushrooms.application.model.Mushroomer;
import pl.polsl.mushrooms.application.model.Score;

import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by pawel_zaqkxkn on 26.06.2017.
 */
public class ScoreServiceImpl implements ScoreService {


    private final ScoreDao scoreDao;
    private final UserDao userDao;
    private final DiscoveryDao discoveryDao;

    public ScoreServiceImpl(ScoreDao scoreDao, UserDao userDao, DiscoveryDao discoveryDao) {
        this.scoreDao = scoreDao;
        this.userDao = userDao;
        this.discoveryDao = discoveryDao;
    }

    @Override
    public Long handle(AddScoreCommand command) {
        final Mushroomer currentUser = (Mushroomer) Optional.ofNullable(
                userDao.findOneByUsername(command.getUserName()))
                    .orElseThrow(NotFoundException::new);

        final Discovery discovery = Optional.ofNullable(
                discoveryDao.findDiscovery(command.getDiscoveryId()))
                    .orElseThrow(NotFoundException::new);

        final Score score = new Score(
                command.getValue(),
                LocalDateTime.now(),
                discovery,
                currentUser);

        return scoreDao.save(score).getId();
    }

    @Override
    public void handle(UpdateScoreCommand command) {
        final Mushroomer currentUser = (Mushroomer) Optional.ofNullable(
                userDao.findOneByUsername(command.getUserName()))
                    .orElseThrow(NotFoundException::new);

        final Score score = scoreDao.findOne(command.getId());

        if (score.getMushroomer().equals(currentUser)) {
            score.setValue(command.getValue());
        } else {
            throw new IllegalStateException("Cannot update score of other user!");
        }
    }

    @Override
    public void handle(DeleteScoreCommand command) {
        final Mushroomer currentUser = (Mushroomer) Optional.ofNullable(
                userDao.findOneByUsername(command.getUserName()))
                .orElseThrow(NotFoundException::new);

        final Score score = scoreDao.findOne(command.getId());

        if (score.getMushroomer().equals(currentUser)) {
            scoreDao.delete(score);
        } else {
            throw new IllegalStateException("Cannot delete score of other user!");
        }
    }
}
