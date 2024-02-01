package net.tape.timm.command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.Sound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.tape.timm.songControls;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import net.minecraft.text.Text;
import net.tape.timm.timmMain;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class skip {

    // Credit to Earthcomputer for writing most of this code
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

        /*
        dispatcher.register(
                literal("s")
                    // samsaassaaswade thing with a different name
                    .executes(ctx -> skipNoSong(ctx.getSource()))
                    .then(argument("song", StringArgumentType.string()))
                        .executes(ctx -> skipWithSong(ctx.getSource(), StringArgumentType.getString(ctx, "song")))
        );

        */
    }

    private static int skipNoSong(FabricClientCommandSource source) {
        songControls.skip();
        source.sendFeedback(Text.translatable("commands.skip.success", songControls.lastSong.toString()));
        return Command.SINGLE_SUCCESS;
    }

    private static int skipWithSong(FabricClientCommandSource source, String song) {

        Identifier id = Identifier.tryParse(song);
        if (id != null && id.getClass().getTypeName().equals("SoundEvent")) {
            SoundEvent sound = SoundEvent.of(id);
            songControls.skip(sound);
            source.sendFeedback(Text.translatable("commands.skip.success", song));
            return Command.SINGLE_SUCCESS;
        } else {
            source.sendFeedback(Text.translatable("commands.skip.badId", song));
            return -1; // apparently there isn't a constant defined for command failure???
        }
    }

}