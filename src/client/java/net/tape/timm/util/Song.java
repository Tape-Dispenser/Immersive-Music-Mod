package net.tape.timm.util;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.tape.timm.timmMain;

public class Song {


    private final String filePath;
    private final String songName;
    private final SoundEvent soundEvent;
    private final boolean fromFile;
    private final String author;
    private final Identifier id;

    public Song(String path, String name, String author) {
        this.filePath = path;
        this.songName = name;
        this.soundEvent = null;
        this.fromFile = true;
        this.author = author;
        this.id = spoofId();
    }

    public Song(SoundEvent sound, String name, String author) {
        this.soundEvent = sound;
        this.songName = name;
        this.filePath = null;
        this.fromFile = false;
        this.author = author;
        this.id = soundEvent.getId();
    }

    public boolean isFile() {return this.fromFile;}

    public SoundEvent getSoundEvent() {return this.soundEvent;}

    public String getAuthor() {return this.author;}

    public String getFilePath() {return this.filePath;}

    public String getSongName() {return this.songName;}

    public Identifier getId() {return this.id;}


    private Identifier spoofId() {
        String[] parts = this.filePath.split("/");
        String path = parts[parts.length-1];
        // creates new Identifier "timm:file_name.ogg"
        return new Identifier("timm", path);
    }
}
