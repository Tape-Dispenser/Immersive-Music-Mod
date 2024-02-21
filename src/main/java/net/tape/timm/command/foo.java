package net.tape.timm.command;


import net.minecraft.client.MinecraftClient;
import net.tape.timm.gui.configScreen;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import net.minecraft.text.Text;
import net.tape.timm.timmMain;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;


public class foo {

    static MinecraftClient mc = timmMain.mc;
    // Credit to Earthcomputer for writing most of the command handling code
    // i couldn't have done command interface shit if it weren't for clientcommands being open source
    // earthcomputer a real one for that
    // https://github.com/Earthcomputer/clientcommands

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("foo")
                .executes(ctx -> fooCommand(ctx.getSource()))

        );
    }

    private static int fooCommand(FabricClientCommandSource source) throws CommandSyntaxException {
        source.sendFeedback(Text.translatable("timm.commands.foo", "bar"));
        configScreen cfg = new configScreen();
        timmMain.LOGGER.info(Thread.currentThread().getName());
        mc.send(() -> mc.setScreen(cfg));
        return Command.SINGLE_SUCCESS;
    }

}



