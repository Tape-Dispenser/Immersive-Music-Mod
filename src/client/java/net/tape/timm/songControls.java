package net.tape.timm;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;

public class songControls {

    public static MinecraftClient mc;
    public static PositionedSoundInstance lastSoundInstance;

    public static SoundEvent song1;
    public static SoundEvent song2; // blur reference???????

    public static void play(SoundEvent song) {
        if (song != null) {
            if (nowPlaying() != null)
                mc.getSoundManager().stop(lastSoundInstance);
            lastSoundInstance = PositionedSoundInstance.music(song);
            mc.getSoundManager().play(lastSoundInstance);
        }
    }

    public static void stop() {
        if (nowPlaying() != null) {
            mc.getSoundManager().stop(lastSoundInstance);
        }
    }

    public static String nowPlaying() {
        if (mc.getSoundManager().isPlaying(lastSoundInstance)) {
            return lastSoundInstance.getSound().getIdentifier().toString();
        } else {
            return null;
        }
    }










}
