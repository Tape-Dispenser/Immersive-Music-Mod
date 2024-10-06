package net.tape.timm.aws;

import net.tape.timm.audio.Song;

import java.io.File;

public class UpdateEntry {
    private final Song song;
    private final File file;
    private boolean enabled;
    public UpdateEntry(Song song, File file, boolean enabled) {
        this.song = song;
        this.file = file;
        this.enabled = enabled;
    }

    public UpdateEntry(Song song, File file) {
        this.song = song;
        this.file = file;
        this.enabled = true;
    }

    public Song getSong() {
        return this.song;
    }

    public File getFile() {
        return this.file;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
