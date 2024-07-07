package net.tape.timm.audio;


import net.tape.timm.timmMain;
import org.lwjgl.openal.*;


public class AudioManager {

    private static long audioContext;
    private static long audioDevice;

    protected static boolean started = false;


    public static void init() {
        // initialize audio device
        String defaultDeviceName = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
        audioDevice = ALC10.alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};
        audioContext = ALC10.alcCreateContext(audioDevice, attributes);
        ALC10.alcMakeContextCurrent(audioContext);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

        if (alCapabilities.OpenAL10) {
            started = true;
            timmMain.LOGGER.info(String.format("Started OpenAL10 on device %s", defaultDeviceName));
        } else {
            timmMain.LOGGER.warn("Failed to start OpenAL, game will run with no audio");
        }
    }

    public static void stop() {
        ALC10.alcDestroyContext(audioContext);
        ALC10.alcCloseDevice(audioDevice);
    }
}
