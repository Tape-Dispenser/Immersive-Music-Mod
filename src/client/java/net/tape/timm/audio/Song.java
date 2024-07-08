package net.tape.timm.audio;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;

public class Song {


    private final String filePath;
    private final String songName;
    private final String author;

    public Song(String path, String name, String author) {
        this.filePath = path;
        this.songName = name;
        this.author = author;
    }

    public Song(SoundEvent sound, String name, String author) {
        this.songName = name;
        this.filePath = PositionedSoundInstance.music(sound).getSound().getLocation().getPath();
        this.author = author;
    }

    public String getAuthor() {return this.author;}

    public String getFilePath() {return this.filePath;}

    public String getSongName() {return this.songName;}

}
