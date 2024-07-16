package net.tape.timm.audio;

import net.minecraft.client.sound.OggAudioStream;
import net.tape.timm.modConfig;
import net.tape.timm.timmMain;

import javax.sound.sampled.AudioFormat;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.*;

public class Sound {
    private int bufferId;
    private int sourceId;


    private boolean isPlaying = false;

    public Sound(Song song) {

        ByteBuffer audioData = null;
        AudioFormat metaData = null;
        try(InputStream inputStream = new ByteArrayInputStream(song.loadByteStream().toByteArray());) {
            try (OggAudioStream oggAudioStream = new OggAudioStream(inputStream);){
                audioData = oggAudioStream.getBuffer();
                metaData = oggAudioStream.getFormat();
            }
        } catch (Exception e) {
            timmMain.LOGGER.warn("Error loading sound data from buffer", e);
        }

        if (audioData == null || metaData == null) {
            timmMain.LOGGER.warn("Invalid file type passed (maybe)");
            return;
        }

        if (modConfig.debugLogging) {
            timmMain.LOGGER.info(String.format("Now playing: '%s' by '%s'", song.getSongName(), song.getAuthor()));
        }

        // retrieve extra information
        int channels = metaData.getChannels();
        int sampleRate = (int) metaData.getSampleRate();

        // find correct OpenAL format
        int format = -1;
        if (channels == 1) {
            format = AL_FORMAT_MONO16;
        } else if (channels == 2) {
            format = AL_FORMAT_STEREO16;
        }

        bufferId = alGenBuffers();
        alBufferData(bufferId, format, audioData, sampleRate);

        // generate source
        sourceId = alGenSources();

        alSourcei(sourceId, AL_BUFFER, bufferId);
        alSourcei(sourceId, AL_LOOPING, 0); // disable looping
        alSourcei(sourceId, AL_POSITION, 0); // set cursor to start
        alSourcef(sourceId, AL_GAIN, 0.3f); // TODO: get minecraft audio slider data here
    }

    public void delete() {
        alDeleteSources(sourceId);
        alDeleteBuffers(bufferId);
    }

    public void play() {
        int state = alGetSourcei(sourceId, AL_SOURCE_STATE);
        if (state == AL_STOPPED) {
            isPlaying = false;
            alSourcei(sourceId, AL_POSITION, 0);
        }

        if (!isPlaying) {
            alSourcePlay(sourceId);
            isPlaying = true;
        }
    }

    public void stop() {
        if (isPlaying) {
            alSourceStop(sourceId);
            isPlaying = false;
        }
    }

    public boolean isPlaying() {
        int state = alGetSourcei(sourceId, AL_SOURCE_STATE);
        if (state == AL_STOPPED) {
            isPlaying = false;
        }

        return isPlaying;
    }
}
