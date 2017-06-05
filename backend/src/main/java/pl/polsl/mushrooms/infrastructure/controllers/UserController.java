package pl.polsl.mushrooms.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.mushrooms.application.commands.user.CreateUserCommand;
import pl.polsl.mushrooms.application.commands.user.UpdateProfileImageCommand;
import pl.polsl.mushrooms.application.commands.user.UpdateUserCommand;
import pl.polsl.mushrooms.application.dao.ProjectionDao;
import pl.polsl.mushrooms.application.services.projections.UserProjectionService;
import pl.polsl.mushrooms.infrastructure.commands.CommandGateway;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

/**
 * Created by pawel_zaqkxkn on 31.03.2017.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserProjectionService userProjectionService;
    private final CommandGateway commandGateway;


    @Autowired
    public UserController(
            UserProjectionService userProjectionService, CommandGateway commandGateway) {
        this.userProjectionService = userProjectionService;
        this.commandGateway = commandGateway;
    }

    // TODO PK walidacja przy rejestracji
//    @InitBinder("form")
//    public void initBinder(WebDataBinder binder) {
//        binder.addValidators(userValidationService);
//    }

    /**
     * CREATE
     * @param command
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> create(@RequestBody CreateUserCommand command) {
        final long id = commandGateway.dispatch(command);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    /**
     * READ
     * @param id
     * @return
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getById(
            @PathVariable(name = "id") long id,
            @RequestParam(value = "projection", required = false, defaultValue = "FULL") ProjectionDao.Projection projection) {
        final Map<String, Object> user = userProjectionService.findOne(id, projection);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * READ
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Map<String,Object>>> getAll(
            @RequestParam(value = "projection", required = false, defaultValue = "FULL") ProjectionDao.Projection projection) {
        final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        final List<Map<String,Object>> users = userProjectionService.findAll(userName, projection);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> search(
            @RequestParam(value = "value") String value) {
//        final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        final List<Map<String, Object>> users = userProjectionService.search(value, ProjectionDao.Projection.FULL);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * UPDATE
     * @param command
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> update(
            @RequestBody UpdateUserCommand command) {
        commandGateway.dispatch(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * DELETE
     * @param id
     * @return
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(name = "id") long id) {
//        final DeleteUserCommand command = new DeleteUserCommand(id);
//        commandGateway.dispatch(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // TODO PK Nie usuwać! Przyda się w przyszłości
    @RequestMapping(path = "image", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void image(@RequestParam("files") UpdateProfileImageCommand image) {
        try {
            final File file = new File("C:/Users/pawel_zaqkxkn/Desktop/drive-download-20170604T205910Z-001/" + image.getOriginalFilename());
            file.createNewFile();
            Files.write(file.toPath(), image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
