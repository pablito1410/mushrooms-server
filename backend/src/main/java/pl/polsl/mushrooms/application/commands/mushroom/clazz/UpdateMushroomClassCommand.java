package pl.polsl.mushrooms.application.commands.mushroom.clazz;

import org.hibernate.validator.constraints.NotEmpty;
import pl.polsl.mushrooms.application.commands.ReturningCommand;
import pl.polsl.mushrooms.infrastructure.dto.MushroomClassDto;
import javax.validation.constraints.NotNull;

public class UpdateMushroomClassCommand implements ReturningCommand<MushroomClassDto> {

    @NotNull
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    protected UpdateMushroomClassCommand() { }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
