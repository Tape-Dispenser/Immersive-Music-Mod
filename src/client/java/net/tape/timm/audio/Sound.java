package net.tape.timm.audio;

import net.minecraft.client.sound.OggAudioStream;
import net.tape.timm.timmMain;

import javax.sound.sampled.AudioFormat;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.*;

public class Sound {
    private int bufferId;
    private int sourceId;

    private final String filePath;

    private boolean isPlaying = false;

    public Sound(String filepath) {
        this.filePath = filepath;



        // TODO: Make some way to load ogg data without stb vorbis (cringe)
        // TODO: Maybe i can do it the same way minecraft does

        ByteBuffer audioData = null;
        AudioFormat metaData = null;
        try (InputStream inputStream = new FileInputStream(filepath);){
            try (OggAudioStream oggAudioStream = new OggAudioStream(inputStream);){
                audioData = oggAudioStream.getBuffer();
                metaData = oggAudioStream.getFormat();
            }
        } catch (IOException e) {
            timmMain.LOGGER.warn(String.format("Error loading file '%s'", filepath), e);
        }

        if (audioData == null || metaData == null) {
            return;
        }

        // retrieve extra information stored in buffers by stb
        int channels = metaData.getChannels();
        int sampleRate = (int) metaData.getSampleRate();

        // debug
        timmMain.LOGGER.info(String.format("file '%s' has %d channels and a sample rate of %d", filepath, channels, sampleRate));

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

    public String getFilePath() {
        return this.filePath;
    }

    public boolean isPlaying() {
        int state = alGetSourcei(sourceId, AL_SOURCE_STATE);
        if (state == AL_STOPPED) {
            isPlaying = false;
        }

        return isPlaying;
    }
}
