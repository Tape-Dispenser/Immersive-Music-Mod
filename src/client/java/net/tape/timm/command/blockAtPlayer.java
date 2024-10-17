package net.tape.timm.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.tape.timm.audio.Song;
import net.tape.timm.songControls;
import net.tape.timm.timmMain;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class blockAtPlayer {
    // Credit to Earthcomputer for writing most of the command handling code
    // i couldn't have done command interface shit if it weren't for clientcommands being open source
    // earthcomputer a real one for that
    // https://github.com/Earthcomputer/clientcommands

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("bap").executes(ctx -> printBlock(ctx.getSource())));
        dispatcher.register(literal("timm:blockAtPlayer").executes(ctx -> printBlock(ctx.getSource())));
    }

    private static int printBlock(FabricClientCommandSource source) {
        assert timmMain.mc.player != null;
        String blockString = timmMain.mc.player.getBlockStateAtPos().getBlock().toString();
        timmMain.LOGGER.info(blockString);
        return Command.SINGLE_SUCCESS;


    }
}
