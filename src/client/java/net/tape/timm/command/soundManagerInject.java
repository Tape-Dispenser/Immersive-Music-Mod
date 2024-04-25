package net.tape.timm.command;


import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.text.Text;
import net.tape.timm.access.SoundManagerAccess;
import net.tape.timm.access.SoundSystemAccess;
import net.tape.timm.songControls;
import net.tape.timm.timmMain;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class soundManagerInject {

    // Credit to Earthcomputer for writing most of the command handling code
    // i couldn't have done command interface shit if it weren't for clientcommands being open source
    // earthcomputer a real one for that
    // https://github.com/Earthcomputer/clientcommands

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        // had to change the command name, /stop can't stop servers otherwise 🤣
        dispatcher.register(literal("timmtest").executes(ctx -> SoundManagerInjectTest(ctx.getSource())));
    }

    private static int SoundManagerInjectTest(FabricClientCommandSource source) {

        SoundSystem mcSoundSystem = ((SoundManagerAccess) timmMain.mc.getSoundManager()).getSoundSystem();
        String idk = ((SoundSystemAccess)mcSoundSystem).access();
        source.sendFeedback(Text.literal(idk));


        return Command.SINGLE_SUCCESS;


        /*
        SoundSystem mcSoundSystem = ((SoundManagerAccess)mc.getSoundManager()).getSoundSystem();
		((SoundSystemAccess)mcSoundSystem).access();
         */
    }

}
