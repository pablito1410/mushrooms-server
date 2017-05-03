package pl.polsl.mushrooms.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.mushrooms.application.commands.trip.CreateCommand;
import pl.polsl.mushrooms.application.commands.trip.DeleteCommand;
import pl.polsl.mushrooms.application.commands.trip.GetCommand;
import pl.polsl.mushrooms.application.commands.trip.UpdateCommand;
import pl.polsl.mushrooms.application.exceptions.EntityAlreadyExistException;
import pl.polsl.mushrooms.application.model.Trip;
import pl.polsl.mushrooms.infrastructure.commands.CommandGateway;

import java.util.UUID;

/**
 * Created by pawel_zaqkxkn on 24.04.2017.
 */
@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final CommandGateway commandGateway;

    @Autowired
    public TripController(final CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody CreateCommand command) {

        try {
            commandGateway.dispatch(command);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(EntityAlreadyExistException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody UpdateCommand command) {

        commandGateway.dispatch(command);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET, params = "id")
    public ResponseEntity<Trip> get(@RequestParam("id") String id) {

        final GetCommand command = new GetCommand(UUID.fromString(id));
        final Trip trip = commandGateway.dispatch(command);

        return new ResponseEntity<>(trip, HttpStatus.OK);
    }

    @RequestMapping(path = "/", method = RequestMethod.DELETE, params = "id")
    public ResponseEntity<Void> delete(@RequestParam("id") String id) {

        final DeleteCommand command = new DeleteCommand(UUID.fromString(id));
        commandGateway.dispatch(command);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
