package pl.polsl.mushrooms.infrastructure.commands;

import pl.polsl.mushrooms.application.commands.ReturningCommand;
import pl.polsl.mushrooms.application.commands.VoidCommand;


public class CommandGatewayImpl implements CommandGateway {


    private final CommandHandlerRegistry registry;

    public CommandGatewayImpl(CommandHandlerRegistry registry) {

        this.registry = registry;
    }

    @Override
    public <R> R dispatch(ReturningCommand<R> command) {
        return registry.get(command).handle(command);
    }

    @Override
    public void dispatch(VoidCommand command) {
        registry.get(command).handle(command);
    }
}
