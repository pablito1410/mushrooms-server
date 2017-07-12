package pl.polsl.mushrooms.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.mushrooms.application.commands.notification.DeleteNotificationCommand;
import pl.polsl.mushrooms.application.services.projections.NotificationProjectionService;
import pl.polsl.mushrooms.infrastructure.commands.CommandGateway;
import pl.polsl.mushrooms.infrastructure.dto.NotificationDto;

import java.util.Set;

/**
 * Created by chythe on 2017-07-02.
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final CommandGateway commandGateway;
    private final NotificationProjectionService notificationProjectionService;

    @Autowired
    public NotificationController(final CommandGateway commandGateway,
                                  final NotificationProjectionService notificationProjectionService) {
        this.commandGateway = commandGateway;
        this.notificationProjectionService = notificationProjectionService;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<NotificationDto> getById(
            @PathVariable(name = "id") long id) {
        final NotificationDto notification = notificationProjectionService.findOne(id);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Set<NotificationDto>> getAll() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final Set<NotificationDto> notifications = notificationProjectionService.findAll(username);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @RequestMapping(path = "/", method = RequestMethod.DELETE, params = "id")
    public ResponseEntity<Void> delete(@RequestParam("id") long id) {
        final DeleteNotificationCommand command = new DeleteNotificationCommand();
        command.setId(id);
        command.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        commandGateway.dispatch(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
