package net.minearchive.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;

public abstract class Command extends CommandBuilder {
    private final String name           = getAnnotation().name();
    private final String[] alias        = getAnnotation().alias();
    private final String description    = getAnnotation().description();

    protected static final int SUCCESS  = com.mojang.brigadier.Command.SINGLE_SUCCESS;

    public CommandInfo getAnnotation() {
        if (this.getClass().isAnnotationPresent(CommandInfo.class))
            return this.getClass().getAnnotation(CommandInfo.class);
        else throw new RuntimeException("CommandInfo Annotation is not found! Can't initialize command!");
    }

    public abstract void build(LiteralArgumentBuilder<CommandSource> builder);

    public void register(CommandDispatcher<CommandSource> dispatcher) {
        register_(dispatcher, name.toLowerCase());
        for (String s : alias) register_(dispatcher, s);
    }

    private void register_(CommandDispatcher<CommandSource> dispatcher, String name) {
        LiteralArgumentBuilder<CommandSource> builder = LiteralArgumentBuilder.literal(name);
        build(builder);
        dispatcher.register(builder);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
