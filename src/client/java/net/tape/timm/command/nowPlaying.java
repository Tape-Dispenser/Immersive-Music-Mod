package net.tape.timm.command;
import net.minecraft.text.TranslatableText;
import net.tape.timm.songControls;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.*;

public class nowPlaying {

    // Credit to Earthcomputer for writing most of the command handling code
    // i couldn't have done command interface shit if it weren't for clientcommands being open source
    // earthcomputer a real one for that
    // https://github.com/Earthcomputer/clientcommands




    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("nowplaying").executes(ctx -> printNowPlaying(ctx.getSource())));
        dispatcher.register(literal("np").executes(ctx -> printNowPlaying(ctx.getSource())));
    }

    private static int printNowPlaying(FabricClientCommandSource source) {
        if (songControls.nowPlaying() != null) {
            String song = songControls.nowPlaying();
            source.sendFeedback(new TranslatableText("timm.commands.nowPlaying.song", song));
        } else {
            source.sendFeedback(new TranslatableText("timm.commands.nowPlaying.null"));
        }

        return Command.SINGLE_SUCCESS;


    }

}
