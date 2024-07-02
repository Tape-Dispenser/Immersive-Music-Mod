package net.tape.timm;


import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.tape.timm.util.Song;

import java.util.Map;

public class registerResources {

    public static Map<String, Song> songList;

    public static final Identifier MY_SOUND_ID = new Identifier("timm:my_sound");

    public static void init() {
        Registry.register(Registries.SOUND_EVENT, registerResources.MY_SOUND_ID, SoundEvent.of(MY_SOUND_ID));

    }
}
