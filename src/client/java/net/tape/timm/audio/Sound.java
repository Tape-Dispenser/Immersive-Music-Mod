package net.tape.timm.audio;

import net.tape.timm.timmMain;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.libc.LibCStdlib.free;

public class Sound {
    private int bufferId;
    private int sourceId;

    private final String filePath;

    private boolean isPlaying = false;

    public Sound(String filepath) {
        this.filePath = filepath;

        // allocate space for return information from stb
        stackPush();
        IntBuffer channelsBuffer = stackMallocInt(1);
        stackPush();
        IntBuffer sampleRateBuffer = stackMallocInt(1);

        // load file with stb
        ShortBuffer rawAudioBuffer = stb_vorbis_decode_filename(filepath, channelsBuffer, sampleRateBuffer);

        if (rawAudioBuffer == null) {
            timmMain.LOGGER.warn(String.format("Could not load sound '%s'!", filepath));
            stackPop();
            stackPop();
            return;
        }

        // retrieve extra information stored in buffers by stb
        int channels = channelsBuffer.get();
        int sampleRate = sampleRateBuffer.get();
        // free memory on stack
        stackPop();
        stackPop();

        // find correct OpenAL format
        int format = -1;
        if (channels == 1) {
            format = AL_FORMAT_MONO16;
        } else if (channels == 2) {
            format = AL_FORMAT_STEREO16;
        }

        bufferId = alGenBuffers();
        alBufferData(bufferId, format, rawAudioBuffer, sampleRate);

        // generate source
        sourceId = alGenSources();

        alSourcei(sourceId, AL_BUFFER, bufferId);
        alSourcei(sourceId, AL_LOOPING, 0); // disable looping
        alSourcei(sourceId, AL_POSITION, 0); // set cursor to start
        alSourcef(sourceId, AL_GAIN, 0.3f); // TODO: get minecraft audio slider data here

        free(rawAudioBuffer);
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
