package net.tape.timm;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.tape.timm.audio.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static net.tape.timm.timmMain.LOGGER;


public class songControls {

    public static MinecraftClient mc;
    public static Sound lastSound;

    public static Song lastSong;
    public static long timer;
    public static boolean inTimer;
    public static Random song_rng;
    public static boolean soundEngineStarted = false;


    public static void init() {
        mc = MinecraftClient.getInstance();
        lastSong = null;
        timer = 10;
        inTimer = true;
        song_rng = new Random();
    }

    public static void play(Song song) {
        if (song == null) {
            return;
        }

        if (nowPlaying() != null) {
            lastSound.stop();
        }

        lastSound = new Sound(song);
        lastSound.play();
        lastSong = song;
    }

    public static void stop() {
        if (nowPlaying() == null) {
            return;
        }
        lastSound.stop();
        // set timer and rng delay time
        inTimer = true;
        long x; // delay time
        if (lastSong == null) {
            x = 10;
        } else {
            // TODO: FIX THIS
            x = pickDelay(modConfig.minMenuDelay, modConfig.maxMenuDelay, song_rng);
        }
        timer = x;
        // debug logging
        if (modConfig.debugLogging) {
            LOGGER.info("ticks until next song: ".concat(String.valueOf(x)));
        }
    }

    public static void skip(Song next) {
        if (nowPlaying() != null) {
            stop();
        }
        if (inTimer) {
            inTimer = false;
            timer = 10;
        }
        play(next);
    }

    public static void skip() {
        Song song = SongSelector.pickSong(songControls.song_rng);
        if (song == null) {
            return;
        }
        if (inTimer) {
            inTimer = false;
        }
        play(song);
    }

    public static long pickDelay(long min, long max, Random rng) {
        if (min == max) {
            return min;
        } else if (max < min) {
            return rng.nextLong(max, min);
        } else {
            return rng.nextLong(min, max);
        }
    }

    public static Song nowPlaying() {
        if (lastSound == null) {
            return null;
        }

        if (lastSound.isPlaying()) {
            return lastSong;
        }

        return null;
    }
}
