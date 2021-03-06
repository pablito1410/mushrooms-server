package pl.polsl.mushrooms.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.mushrooms.application.commands.trip.*;
import pl.polsl.mushrooms.application.services.projections.TripProjectionService;
import pl.polsl.mushrooms.infrastructure.commands.CommandGateway;
import pl.polsl.mushrooms.infrastructure.dto.MushroomerDto;
import pl.polsl.mushrooms.infrastructure.dto.TripDto;

import java.time.LocalDateTime;
import java.util.Set;


@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final CommandGateway commandGateway;
    private final TripProjectionService tripProjectionService;

    @Autowired
    public TripController(final CommandGateway commandGateway, final TripProjectionService tripProjectionService) {
        this.commandGateway = commandGateway;
        this.tripProjectionService = tripProjectionService;
    }

    /**
     *  CREATE
     * @param command
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> create(@RequestBody CreateTripCommand command) {
        command.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        final long id = commandGateway.dispatch(command);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    /**
     * UPDATE
     * @param command
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody UpdateTripCommand command) {
        command.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        commandGateway.dispatch(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET BY ID
     * @param id
     * @return
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<TripDto> getById(
            @PathVariable(name = "id") long id) {
        final TripDto trip = tripProjectionService.findOne(id);
        return new ResponseEntity<>(trip, HttpStatus.OK);
    }

    /**
     *
     * @param id - id of the trip
     * @return participants of the trip
     */
    @RequestMapping(path = "/participants/{id}", method = RequestMethod.GET)
    public ResponseEntity<Set<MushroomerDto>> getParticipants(
            @PathVariable(name = "id") long id) {
        final Set<MushroomerDto> participants = tripProjectionService.findParticipants(id);
        return new ResponseEntity<>(participants, HttpStatus.OK);
    }

    /**
     *
     * @param id - id o the trip
     * @return invited users to the trip
     */
    @RequestMapping(path = "/invited/{id}", method = RequestMethod.GET)
    public ResponseEntity<Set<MushroomerDto>> getTripRequests(
            @PathVariable(name = "id") long id) {
        final Set<MushroomerDto> invited = tripProjectionService.findInvited(id);
        return new ResponseEntity<>(invited, HttpStatus.OK);
    }

    /**
     * READ
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Set<TripDto>> getAll() {
        final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        final Set<TripDto> trips = tripProjectionService.findAll(userName);
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    /**
     * DELETE
     * @param id
     * @return
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(name = "id") long id) {
        final DeleteTripCommand command = new DeleteTripCommand(id);
        command.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        commandGateway.dispatch(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Adds user to the trip
     * @param command
     * @return
     */
    @RequestMapping(path = "/join", method = RequestMethod.POST)
    public ResponseEntity<Void> joinTrip(@RequestBody JoinTripCommand command) {
        command.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        commandGateway.dispatch(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Search trip by place
     * @param value
     * @return
     */
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ResponseEntity<Set<TripDto>> search(@RequestParam(value = "value") String value) {
        final Set<TripDto> users = tripProjectionService.search(value);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Invites the user to the trip
     * @param command
     * @return
     */
    @RequestMapping(path = "/invite", method = RequestMethod.POST)
    public ResponseEntity<Void> invite(@RequestBody InviteToTripCommand command) {
        command.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        commandGateway.dispatch(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @return requests to the trips of the user
     */
    @RequestMapping(path = "/requests", method = RequestMethod.GET)
    public ResponseEntity<Set<TripDto>> getRequests() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final Set<TripDto> invitations = tripProjectionService.findRequests(username);
        return new ResponseEntity<>(invitations, HttpStatus.OK);
    }

    @RequestMapping(path = "/accept-date-time/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<LocalDateTime> acceptDateTime(@PathVariable(name = "id") long tripId) {
        final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        final LocalDateTime dateTime = tripProjectionService.acceptDateTime(userName, tripId);
        return new ResponseEntity<>(dateTime, HttpStatus.OK);
    }

}
