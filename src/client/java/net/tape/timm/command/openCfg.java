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


public class openCfg {

    static MinecraftClient mc = timmMain.mc;
    // Credit to Earthcomputer for writing most of the command handling code
    // i couldn't have done command interface shit if it weren't for clientcommands being open source
    // earthcomputer a real one for that
    // https://github.com/Earthcomputer/clientcommands

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("timm:cfg").executes(ctx -> openConfigMenu(ctx.getSource())));
        dispatcher.register(literal("timm:config").executes(ctx -> openConfigMenu(ctx.getSource())));
    }

    private static int openConfigMenu(FabricClientCommandSource source) throws CommandSyntaxException {
        configScreen cfg = new configScreen(mc.currentScreen);
        timmMain.LOGGER.info(Thread.currentThread().getName());
        mc.send(() -> mc.setScreen(cfg));
        return Command.SINGLE_SUCCESS;
    }
}



