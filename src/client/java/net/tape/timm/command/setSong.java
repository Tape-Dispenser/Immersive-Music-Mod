package net.tape.timm.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.tape.timm.songControls;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class setSong {

    // Credit to Earthcomputer for writing most of the command handling code
    // I couldn't have done command interface shit if it weren't for clientcommands being open source
    // Earthcomputer a real one for that
    // https://github.com/Earthcomputer/clientcommands

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {

        dispatcher.register(literal("setsong")
            // declare argument with name 'num' and type integer with a minimum of 1 and maximum of 2     
            .then(argument("num", IntegerArgumentType.integer(1,2))
            // declare argument with name 'song' and type String
                .then(argument("song", StringArgumentType.string())
                    .executes(ctx -> {
                        final FabricClientCommandSource src = ctx.getSource();
                        final String songName =  StringArgumentType.getString(ctx, "song");
                        final int songNum = IntegerArgumentType.getInteger(ctx, "num");
                        return setSongCommand(src, songNum, songName);
                    })
                )
            )
        );
        
    }
    

    private static int setSongCommand(FabricClientCommandSource source, int num, String song) {

        Identifier id;
        try {
            id = new Identifier(song);
        } catch (InvalidIdentifierException e) {
            source.sendFeedback(Text.translatable("timm.commands.setSong.badID", song));
            return -1; // apparently there isn't a constant defined for command failure???
        }

            SoundEvent sound = SoundEvent.of(id);

            switch (num) {
                case 1:
                    songControls.song1 = sound;
                    source.sendFeedback(Text.translatable("timm.commands.setSong.success", 1, song));
                    return Command.SINGLE_SUCCESS;
                case 2:
                    songControls.song2 = sound;
                    source.sendFeedback(Text.translatable("timm.commands.setSong.success", 2, song));
                    return Command.SINGLE_SUCCESS;
                default:
                    source.sendFeedback(Text.translatable("timm.commands.setSong.badNum", num));
                    return -1;
            }
    }
}
