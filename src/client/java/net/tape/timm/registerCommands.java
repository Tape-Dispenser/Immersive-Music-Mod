package net.tape.timm;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.tape.timm.command.*;
import net.minecraft.command.CommandRegistryAccess;
import net.tape.timm.command.openCfg;
import net.tape.timm.command.nowPlaying;

public class registerCommands {
    static void init(CommandDispatcher<FabricClientCommandSource> dispatch, CommandRegistryAccess cRA) {
        openCfg.register(dispatch);
        nowPlaying.register(dispatch);
        stop.register(dispatch);
        skip.register(dispatch);
        blockAtPlayer.register(dispatch);
    }


}
