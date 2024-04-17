package net.tape.timm.command;


import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.tape.timm.songControls;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class play1 {

    // Credit to Earthcomputer for writing most of the command handling code
    // i couldn't have done command interface shit if it weren't for clientcommands being open source
    // earthcomputer a real one for that
    // https://github.com/Earthcomputer/clientcommands

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        // had to change the command name, /stop can't stop servers otherwise 🤣
        dispatcher.register(literal("play1").executes(ctx -> playSong1(ctx.getSource())));
    }

    private static int playSong1(FabricClientCommandSource source) {


        songControls.play(songControls.song1);
        source.sendFeedback(Text.translatable("timm.commands.play1", songControls.nowPlaying()));


        return Command.SINGLE_SUCCESS;


    }

}
