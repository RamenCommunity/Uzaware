package net.minearchive.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.command.CommandSource;

public class CommandBuilder {
    protected static <T> RequiredArgumentBuilder<CommandSource, T> argument(final String name, final ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    protected static RequiredArgumentBuilder<CommandSource, Integer> integer(String name, Command<CommandSource> context) {
        return argument(name, IntegerArgumentType.integer()).executes(context);
    }

    /**
     *  String -> "'quoted phrase'"
     *  <br>
     *  Example -> ["Hello", " ", "World!, " ", "What", " ", "are", " ", "you", " ", "doing?"]
     */
    protected static RequiredArgumentBuilder<CommandSource, String> string(String name, Command<CommandSource> context) {
        return argument(name, StringArgumentType.string()).executes(context);
    }

    /**
     *  Word -> "words_with_underscores"
     *  <br>
     *  Example -> "Hello_world!_What_are_you_doing?"
     */
    protected static RequiredArgumentBuilder<CommandSource, String> word(String name, Command<CommandSource> context) {
        return argument(name, StringArgumentType.word()).executes(context);
    }

    /**
     *  GreedyString -> "words with spaces"
     *  <br>
     *  Example -> "Hello world! What are you doing?"
     */
    protected static RequiredArgumentBuilder<CommandSource, String> greedyString(String name, Command<CommandSource> context) {
        return argument(name, StringArgumentType.greedyString()).executes(context);
    }
}
