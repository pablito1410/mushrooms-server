package pl.polsl.mushrooms.infrastructure.dao;

import pl.polsl.mushrooms.application.dao.TripProjectionDao;
import pl.polsl.mushrooms.application.model.*;
import pl.polsl.mushrooms.infrastructure.dto.MushroomerDto;
import pl.polsl.mushrooms.infrastructure.dto.TripDto;
import pl.polsl.mushrooms.infrastructure.mapper.EntityMapper;
import pl.polsl.mushrooms.infrastructure.repositories.TripRepository;
import pl.polsl.mushrooms.infrastructure.repositories.UserRepository;
import pl.polsl.mushrooms.infrastructure.repositories.UsersTripsRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class TripProjectionDaoImpl implements TripProjectionDao {


    private final TripRepository tripRepository;
    private final UsersTripsRepository usersTripsRepository;
    private final UserRepository userRepository;
    private final EntityMapper entityMapper;

    public TripProjectionDaoImpl(
            final TripRepository tripRepository,
            final UsersTripsRepository usersTripsRepository,
            final UserRepository userRepository,
            final EntityMapper entityMapper) {
        this.tripRepository = tripRepository;
        this.usersTripsRepository = usersTripsRepository;
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Set<TripDto> findAll() {
        final List<Trip> trips = tripRepository.findAll();
        return entityMapper.map(trips);
}

    @Override
    public TripDto findOne(long id) {
        final Trip trip = tripRepository.findOne(id);
        return entityMapper.map(trip);
    }

    @Override
    public Set<TripDto> search(String value) {
        final Set<Trip> trips = tripRepository.findByPlaceIgnoreCaseContaining(value);
        return entityMapper.map(trips);
    }

    @Override
    public Set<MushroomerDto> findParticipants(final long id) {
        final Set<UsersTrips> ids = usersTripsRepository.findByUsersTripsId_trip_idAndDateTimeIsNotNull(id);
        final Collection<Mushroomer> users = ids
            .stream()
            .map(p -> (Mushroomer)userRepository.findOne(p.getUserId()))
            .collect(Collectors.toList());
        return entityMapper.map(users);
    }

    @Override
    public Set<TripDto> findAll(final User user) {
        final List<Trip> trips = tripRepository.findAllAndAccepted(user.getId());
        return entityMapper.map(trips);
    }

    @Override
    public Set<TripDto> findRequests(final User user) {
        final List<Trip> trips = tripRepository.findRequests(user.getId());
        return entityMapper.map(trips);
    }

    @Override
    public Set<MushroomerDto> findInvited(final long tripId) {
        final Set<UsersTrips> ids = usersTripsRepository.findByUsersTripsId_trip_idAndDateTimeIsNull(tripId);
        final Collection<Mushroomer> users = ids
                .stream()
                .map(p -> (Mushroomer)userRepository.findOne(p.getUserId()))
                .collect(Collectors.toList());
        return entityMapper.map(users);
    }

    @Override
    public UsersTrips findUserTrip(final User user, final Trip trip) {
        return usersTripsRepository.findOne(new UsersTripsId(user, trip));
    }
}
