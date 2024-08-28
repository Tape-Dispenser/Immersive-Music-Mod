package net.tape.timm.audio;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public abstract class Song {


    private final String pathOrId;
    private final String songName;
    private final String author;
    private final boolean isFile;

    protected Song(String pathOrId, String songName, String author, boolean isFile) {
        this.pathOrId = pathOrId;
        this.songName = songName;
        this.author = author;
        this.isFile = isFile;
    }

    public abstract ByteArrayOutputStream loadByteStream();

    public String getAuthor() {return this.author;}

    public String getPathOrId() {return this.pathOrId;}

    public String getSongName() {return this.songName;}

    public boolean isFile() {return this.isFile;}

}
