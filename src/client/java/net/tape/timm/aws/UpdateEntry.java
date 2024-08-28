package net.tape.timm.aws;

import net.tape.timm.audio.Song;

import java.io.File;

public record UpdateEntry(Song song, File file) {
}
