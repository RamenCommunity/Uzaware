package net.minearchive.command.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minearchive.command.Command;
import net.minearchive.command.CommandInfo;
import net.minecraft.command.CommandSource;

@CommandInfo(name = "Example", alias = { "ex" })
public class ExampleCommand extends Command {

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(
                argument("test1", IntegerArgumentType.integer()).executes(c -> {
                    System.out.println("test1");
                    return SUCCESS;
                }));

    }
}
