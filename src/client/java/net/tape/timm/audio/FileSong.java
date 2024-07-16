package net.tape.timm.audio;


import net.minecraft.client.input.Input;
import net.tape.timm.timmMain;

import java.io.*;

public class FileSong extends Song {

    public FileSong(String filePath, String songName, String author) {
        super(filePath, songName, author);
    }

    @Override
    public ByteArrayOutputStream loadByteStream() {
        try(InputStream inputStream = new FileInputStream(this.getPathOrId());) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
            return outputStream;
        } catch (IOException e) {
            timmMain.LOGGER.warn(String.format("failed to load song '%s'", this.getPathOrId()), e);
            return null;
        }
    }
}
