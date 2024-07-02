package net.tape.timm.access;

import com.google.common.collect.Maps;
import net.tape.timm.util.Song;

import java.util.Map;

public interface SoundSystemAccess {

    String access();

    void play(Song song);
}
