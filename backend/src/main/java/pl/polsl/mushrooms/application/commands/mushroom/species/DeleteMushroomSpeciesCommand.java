package pl.polsl.mushrooms.application.commands.mushroom.species;

import pl.polsl.mushrooms.application.commands.VoidCommand;

import javax.validation.constraints.NotNull;

/**
 * Created by pawel_zaqkxkn on 17.07.2017.
 */
public class DeleteMushroomSpeciesCommand implements VoidCommand {

    @NotNull
    private Long id;

    public DeleteMushroomSpeciesCommand() { }

    public DeleteMushroomSpeciesCommand(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}