package net.tape.timm;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.util.*;


public class songControls {

    public static MinecraftClient mc;
    public static Map<String, String[]> bp = biomePlaylists.biomePlaylists;
    public static PositionedSoundInstance lastSong;
    public static long timer;
    public static boolean inTimer;
    public static Random song_rng;


    public static void init() {
        mc = MinecraftClient.getInstance();
        lastSong = null;
        timer = 1;
        inTimer = true;
        song_rng = new Random();
    }

    public static void play(SoundEvent song) {
        if (song != null) {
            if (nowPlaying() != null)
                mc.getSoundManager().stop(lastSong);
            lastSong = PositionedSoundInstance.music(song);
            mc.getSoundManager().play(lastSong);
        }
    }

    public static void stop() {
        if (nowPlaying() != null) {
            mc.getSoundManager().stop(lastSong);
        }
    }

    public static void skip(SoundEvent next) {
        if (nowPlaying() != null) {stop();}
        play(next);
    }

    public static void skip() {
        play(pickSong());
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

    public static SoundEvent pickSong() {
        String playlistName = "menu";

        if (mc.world != null) {
            assert mc.player != null;
            Optional<RegistryKey<Biome>> temp = mc.world.getBiome(mc.player.getBlockPos()).getKey();
            if (temp.isPresent()) {
                playlistName = temp.get().getValue().toString();
                timmMain.LOGGER.info(playlistName);
                timer = pickDelay(modConfig.minSongDelay, modConfig.maxSongDelay, song_rng);
            }
        } else {
            playlistName = "menu";
            timer = pickDelay(modConfig.minMenuDelay, modConfig.maxMenuDelay, song_rng);
        }
        inTimer = true;


        List<String> playlist;
        try {
            playlist = Arrays.asList(bp.get(playlistName));
        } catch (NullPointerException e) {
            playlist = Arrays.asList(bp.get("fallback"));
        }


        if (playlist.isEmpty()) {
            playlist = Arrays.asList(bp.get("fallback"));
        }


        int index = Math.abs(song_rng.nextInt() % playlist.size());
        String songName = playlist.get(index);

        try {
            return new SoundEvent(Identifier.tryParse(songName));
        } catch (NullPointerException e) {
            timmMain.LOGGER.error(String.format("failed to find %s", songName));
            throw new RuntimeException(e);
        }

    }

    public static String nowPlaying() {
        if (mc.getSoundManager().isPlaying(lastSong)) {
            return lastSong.getSound().getIdentifier().toString();
        } else {
            return null;
        }
    }










}
