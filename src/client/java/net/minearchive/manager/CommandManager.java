package net.minearchive.manager;

import com.mojang.brigadier.CommandDispatcher;
import net.minearchive.command.Command;
import net.minearchive.command.commands.ExampleCommand;
import net.minecraft.command.CommandSource;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandManager {
    public final CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();
    public final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        register(ExampleCommand.class);
    }

    public void register(Class<? extends Command> clazz) {
        try {
            Command command = clazz.getDeclaredConstructor().newInstance();
            commands.removeIf(exist -> exist.getName().equals(command.getName()));
            command.register(dispatcher);
            commands.add(command);
        } catch (Exception e) {
            LogManager.getLogger("Uzaware CommandManager").error("Error caused while initializing command", e);
        }
    }

    public Optional<Command> get(Class<? extends Command> clazz) {
        return commands.stream().filter(command -> command.getClass() == clazz).findFirst();
    }
}
