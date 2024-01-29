package net.tape.timm;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.timm.command.*;
import net.minecraft.command.CommandRegistryAccess;
import net.tape.timm.command.foo;
import net.tape.timm.command.nowPlaying;

public class registerCommands {
    static void init(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess cRA) {
        foo.register(dispatcher);
        nowPlaying.register(dispatcher);
    }


}
