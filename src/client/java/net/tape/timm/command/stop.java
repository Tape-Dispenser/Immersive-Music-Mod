package net.tape.timm.command;


import net.tape.timm.songControls;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class stop {

    // Credit to Earthcomputer for writing most of the command handling code
    // i couldn't have done command interface shit if it weren't for clientcommands being open source
    // earthcomputer a real one for that
    // https://github.com/Earthcomputer/clientcommands

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("stop").executes(ctx -> stopSong(ctx.getSource())));
    }

    private static int stopSong(FabricClientCommandSource source) {

        songControls.stop();
        source.sendFeedback(Text.translatable("timm.commands.stop"));


        return Command.SINGLE_SUCCESS;


    }

}
