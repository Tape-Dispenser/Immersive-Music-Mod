package net.tape.timm;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;

import net.tape.timm.command.*;

public class registerCommands {
    static void init(CommandDispatcher<FabricClientCommandSource> dispatch, CommandRegistryAccess cRA) {
        setSong.register(dispatch);
        nowPlaying.register(dispatch);
        stop.register(dispatch);
        play1.register(dispatch);
        play2.register(dispatch);
    }


}
