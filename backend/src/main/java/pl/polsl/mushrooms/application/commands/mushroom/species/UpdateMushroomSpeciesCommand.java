package pl.polsl.mushrooms.application.commands.mushroom.species;

import org.hibernate.validator.constraints.NotEmpty;
import pl.polsl.mushrooms.application.commands.ReturningCommand;
import pl.polsl.mushrooms.infrastructure.dto.MushroomSpeciesDto;
import javax.validation.constraints.NotNull;

public class UpdateMushroomSpeciesCommand implements ReturningCommand<MushroomSpeciesDto> {

    @NotNull
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private Long genusId;

    private byte[] examplePhoto;

    protected UpdateMushroomSpeciesCommand() { }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getGenusId() {
        return genusId;
    }

    public byte[] getExamplePhoto() {
        return examplePhoto;
    }
}
