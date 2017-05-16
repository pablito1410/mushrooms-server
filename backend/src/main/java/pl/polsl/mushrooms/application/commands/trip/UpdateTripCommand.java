package pl.polsl.mushrooms.application.commands.trip;

import pl.polsl.mushrooms.application.commands.VoidCommand;

import java.util.UUID;

/**
 * Created by pawel_zaqkxkn on 24.04.2017.
 */
public class UpdateTripCommand implements VoidCommand {

    private UUID tripId;
    private UUID userId;

    protected UpdateTripCommand() { }

    public UUID getTripId() {
        return tripId;
    }

    public UUID getUserId() {
        return userId;
    }
}