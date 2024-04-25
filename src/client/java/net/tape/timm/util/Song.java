package net.tape.timm.util;

import net.minecraft.sound.SoundEvent;

public class Song {

    private final String playlist;
    private final String filePath;
    private final String songName;
    private final SoundEvent soundEvent;
    private final boolean fromFile;

    public Song(String path, String pl, String name, SoundEvent soundEvent) {
        this.filePath = path;
        this.playlist = pl;
        this.songName = name;
        this.soundEvent = null;
        this.fromFile = true;
    }

    public Song(SoundEvent sound, String pl, String name) {
        this.soundEvent = sound;
        this.playlist = pl;
        this.songName = name;
        this.filePath = null;
        this.fromFile = false;
    }

    public boolean isFile() {return this.fromFile;}

    public SoundEvent getSoundEvent() {return this.soundEvent;}

    public String getFilePath() {return this.filePath;}

    public String getPlaylist() {return this.playlist;}

    public String getSongName() {return this.songName;}
}
