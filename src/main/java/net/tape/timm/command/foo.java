package net.tape.timm.command;


import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import net.minecraft.text.Text;

import java.util.Collection;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;
import static net.minecraft.command.argument.GameProfileArgumentType.gameProfile;

public class foo {




    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("foo")
                .executes(ctx -> fooCommand(ctx.getSource()))

        );
    }

    private static int fooCommand(FabricClientCommandSource source) throws CommandSyntaxException {
        source.sendFeedback(Text.translatable("commands.foo.success", "bar"));
        return Command.SINGLE_SUCCESS;
    }

}



