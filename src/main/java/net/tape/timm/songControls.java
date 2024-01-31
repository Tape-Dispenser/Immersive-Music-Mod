package net.tape.timm;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.util.*;


public class songControls {

    public static MinecraftClient mc;
    public static Map<String, String[]> bp = biomePlaylists.biomePlaylists;
    public static PositionedSoundInstance lastSong;
    public static Integer timer;
    public static boolean inTimer;
    public static Random song_rng;


    public static void init() {
        mc = MinecraftClient.getInstance();
        lastSong = null;
        timer = 1;
        inTimer = true;
        song_rng = new Random();
    }

    public static void play(SoundEvent songToPlay) {
        // literally just the vanilla playSong function but it won't be called by vanilla minecraft...
        if (songToPlay != null) {
            if (nowPlaying() != null)
                mc.getSoundManager().stop(lastSong);
            lastSong = PositionedSoundInstance.music(songToPlay);
            mc.getSoundManager().play(lastSong);
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
                timer = song_rng.nextInt(modConfig.minSongDelay, modConfig.maxSongDelay);
            }
        } else {
            timer = song_rng.nextInt(modConfig.minMenuDelay, modConfig.maxMenuDelay);
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
            return SoundEvent.of(Identifier.tryParse(songName));
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
