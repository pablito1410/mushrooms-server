package pl.polsl.mushrooms.infrastructure.dao;

import org.hibernate.cfg.NotYetImplementedException;
import pl.polsl.mushrooms.application.dao.NotificationProjectionDao;
import pl.polsl.mushrooms.application.model.Notification;
import pl.polsl.mushrooms.infrastructure.dto.NotificationDto;
import pl.polsl.mushrooms.infrastructure.mapper.EntityMapper;
import pl.polsl.mushrooms.infrastructure.repositories.NotificationRepository;
import pl.polsl.mushrooms.infrastructure.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;


public class NotificationProjectionDaoImpl implements NotificationProjectionDao {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final EntityMapper entityMapper;

    public NotificationProjectionDaoImpl(
            final NotificationRepository notificationRepository,
            final UserRepository userRepository,
            final EntityMapper entityMapper) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
    }

//    @Override
//    public Set<NotificationDto> findAll(long userId) {
//        throw new NotYetImplementedException(); // TODO
//    }

    @Override
    public Set<NotificationDto> findAll() {
        throw new NotYetImplementedException(); // TODO
    }

    @Override
    public NotificationDto findOne(long notificationId) {
        final Optional<Notification> notification = Optional.ofNullable(notificationRepository.findOne(notificationId));
        if (notification.isPresent()) {
            return entityMapper.map(notification.get());
        } else {
            throw new EntityNotFoundException("Notification not found");
        }
    }

    @Override
    public Set<NotificationDto> search(String value) {
        throw new NotYetImplementedException(); // TODO
    }
}
