package net.tape.timm.access;

import net.tape.timm.audio.Song;

public interface SoundSystemAccess {

    String access();

    void play(Song song);
}
