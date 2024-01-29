package net.tape.timm.command;


import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import net.minecraft.text.Text;
import net.tape.timm.songControls;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class nowPlaying {

    // Credit to Earthcomputer for writing most of this code
    // i couldn't have done command interface shit if it weren't for clientcommands being open source
    // earthcomputer a real one for that
    // https://github.com/Earthcomputer/clientcommands




    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("nowplaying").executes(ctx -> printNowPlaying(ctx.getSource())));
        dispatcher.register(literal("np").executes(ctx -> printNowPlaying(ctx.getSource())));
    }

    private static int printNowPlaying(FabricClientCommandSource source) {
        String song = songControls.currentlyPlaying.getId().toString();

        source.sendFeedback(Text.translatable("commands.nowPlaying.success", song));
        return Command.SINGLE_SUCCESS;
    }

}
