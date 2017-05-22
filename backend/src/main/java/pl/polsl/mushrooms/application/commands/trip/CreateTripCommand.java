package pl.polsl.mushrooms.application.commands.trip;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.polsl.mushrooms.application.commands.ReturningCommand;
import pl.polsl.mushrooms.infrastructure.controllers.LocalDateTimeDeserializer;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by pawel_zaqkxkn on 24.04.2017.
 */
public class CreateTripCommand implements ReturningCommand<Long> {

    @NotNull
    private long userId;

    @NotNull
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;

    @NotNull
    private String place;


    protected CreateTripCommand() { }

    public String getPlace() {
        return place;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
