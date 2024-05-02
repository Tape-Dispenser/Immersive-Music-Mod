package net.tape.timm.util;

import net.minecraft.sound.SoundEvent;

public class Song {


    private final String filePath;
    private final String songName;
    private final SoundEvent soundEvent;
    private final boolean fromFile;
    private final String author;

    public Song(String path, String name, String author) {
        this.filePath = path;
        this.songName = name;
        this.soundEvent = null;
        this.fromFile = true;
        this.author = author;
    }

    public Song(SoundEvent sound, String name, String author) {
        this.soundEvent = sound;
        this.songName = name;
        this.filePath = null;
        this.fromFile = false;
        this.author = author;
    }

    public boolean isFile() {return this.fromFile;}

    public SoundEvent getSoundEvent() {return this.soundEvent;}

    public String getAuthor() {return this.author;}

    public String getFilePath() {return this.filePath;}

    public String getSongName() {return this.songName;}
}
