package pl.polsl.mushrooms.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.mushrooms.application.commands.user.*;
import pl.polsl.mushrooms.application.dao.UserProjectionDao;
import pl.polsl.mushrooms.application.model.User;
import pl.polsl.mushrooms.application.services.projections.UserProjectionService;
import pl.polsl.mushrooms.infrastructure.commands.CommandGateway;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

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
    public ResponseEntity<UUID> create(@RequestBody CreateCommand command) {
        final UUID id = commandGateway.dispatch(command);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    /**
     * READ
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, params = "id")
    public ResponseEntity<User> getById(@RequestParam("id") String id) {
        final GetCommand command = new GetCommand(UUID.fromString(id));
        final User userProfile = commandGateway.dispatch(command);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getById(
            @PathVariable(name = "id") UUID id,
            @RequestParam(value = "projection", required = false, defaultValue = "FULL") UserProjectionDao.Projection projection) {
        final Map<String, Object> user = userProjectionService.findOne(id, projection);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * READ
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> getAll() {

        // TODO projection
        final GetAllUsersCommand command = new GetAllUsersCommand();
        final Collection<User> users = commandGateway.dispatch(command);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * UPDATE
     * @param command
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody UpdateCommand command) {
        commandGateway.dispatch(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * DELETE
     * @param id
     * @return
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(name = "id") UUID id) {

        final DeleteCommand command = new DeleteCommand(id);
        commandGateway.dispatch(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // TODO PK Nie usuwać! Przyda się w przyszłości
//    @RequestMapping(path = "store-image", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public StoreImageResponse receiveImage(
//            @RequestParam("file") List<MultipartFile> files, @RequestParam("info") String info) {
//
//        ImageData imageData = new ImageData(files, info);
//        StoreImageResponse response = imageService.storeImage(imageData);
//
//        return response;
//    }

}
