package net.tape.timm;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.tape.timm.command.*;
import net.tape.timm.command.openCfg;
import net.tape.timm.command.nowPlaying;

public class registerCommands {
    public static void init(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        openCfg.register(dispatcher);
        nowPlaying.register(dispatcher);
        stop.register(dispatcher);
        skip.register(dispatcher);
    }


}
