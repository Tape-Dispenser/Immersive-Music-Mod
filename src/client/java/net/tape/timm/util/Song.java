package net.tape.timm.util;

import net.minecraft.sound.SoundEvent;

public class Song {
    public SoundEvent soundEvent;
    public String playlist;
    public Song(SoundEvent se, String pl) {
        this.soundEvent = se;
        this.playlist = pl;
    }
}
