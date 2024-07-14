package net.tape.timm.audio;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.tape.timm.timmMain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResourceSong extends Song{
    public ResourceSong(String id, String songName, String author) {
        super(id, songName, author);
    }

    @Override
    public ByteArrayOutputStream loadByteStream() {
        Identifier id = Identifier.tryParse(this.pathOrId);
        try(InputStream inputStream = timmMain.mc.getResourceManager().open(id)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
            return outputStream;
        } catch (IOException ignored) {}

        // if directly loading didn't work, try loading from a sound set
        WeightedSoundSet soundSet = PositionedSoundInstance.music(SoundEvent.of(id)).getSoundSet(timmMain.mc.getSoundManager());
        if (soundSet == null) {
            timmMain.LOGGER.warn(String.format("failed to load input stream from song '%s'", this.pathOrId));
            return null;
        }

        Identifier id2 = soundSet.getSound(Random.create()).getLocation();
        try(InputStream inputStream = timmMain.mc.getResourceManager().open(id2)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
            return outputStream;
        } catch (IOException e) {
            timmMain.LOGGER.warn(String.format("failed to load input stream from song '%s'", id2.toString()), e);
            return null;
        }
    }
}
