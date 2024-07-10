package net.tape.timm.command;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.sound.SoundEvent;
import net.tape.timm.audio.Song;
import net.tape.timm.audio.SongRegistry;
import net.tape.timm.songControls;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class skip {

    // Credit to Earthcomputer for writing most of the command handling code
    // I couldn't have done command interface shit if it weren't for clientcommands being open source
    // Earthcomputer a real one for that
    // https://github.com/Earthcomputer/clientcommands

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {

        dispatcher.register(literal("skip")
                    // literal skip with no arguments executes skipNoSong
            .executes(ctx -> {
                final FabricClientCommandSource src = ctx.getSource();
                return skipNoSong(src);
            }) // declare argument with name 'song' and type String
            .then(argument("song", StringArgumentType.string())
                .executes(ctx -> {
                    final FabricClientCommandSource src = ctx.getSource();
                    final String songName =  StringArgumentType.getString(ctx, "song");
                    return skipWithSong(src, songName);
                })
            ));
        
    }

    private static int skipNoSong(FabricClientCommandSource source) {
        songControls.skip();
        source.sendFeedback(Text.translatable("timm.commands.skip.success", songControls.lastSong.getSongName()));
        return Command.SINGLE_SUCCESS;
    }

    private static int skipWithSong(FabricClientCommandSource source, String songName) {

        Song song = null;
        song = SongRegistry.songFromID(songName);
        if (song == null) {
            song = SongRegistry.songFromName(songName);
        }
        if (song != null) {
            songControls.skip(song);
            source.sendFeedback(Text.translatable("timm.commands.skip.success", song.getSongName()));
            return Command.SINGLE_SUCCESS;
        } else {
            source.sendFeedback(Text.translatable("timm.commands.skip.badId", songName));
            return -1; // apparently there isn't a constant defined for command failure???
        }
    }
}
